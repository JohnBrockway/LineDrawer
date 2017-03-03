import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JComponent;

public class CanvasView extends JComponent implements View {

	private Model model;

	private int originalWidth;
	private int originalHeight;
	private int count = 0;

	public CanvasView(Model model) {
		this.model = model;
		model.addView(this);

		originalWidth = 0;
		originalHeight = 0;

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				model.addStroke(model.getColor());
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int x = (int) (e.getX() * ((double)originalWidth/getWidth()));
				int y = (int) (e.getY() * ((double)originalHeight/getHeight()));
				model.addPoint(x, y);
				repaint();
			}
		});
	}

	@Override
	public void update() {
		repaint();
	}

	public void animate() {
		boolean animating = false;
		Graphics gr = this.getGraphics();
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.scale((double)this.getWidth()/originalWidth, (double)this.getHeight()/originalHeight);
		int curStroke = 0;
		for (Stroke s : model.getStrokes()) {
			g.setColor(s.getColor());
			g.setStroke(new BasicStroke(s.getThickness()));
			List<Point> points = s.getPoints();
			int [] xs = new int [points.size()];
			int [] ys = new int [points.size()];
			int index = 0;
			for (Point p : points) {
				xs[index] = p.getX();
				ys[index] = p.getY();
				index++;
				if (curStroke + 1 > model.getCurStroke() && index > model.getCurPoint()) {
					animating = true;
				}
				if (animating) {
					model.setCurPoint(model.getCurPoint() + 1);
					g.drawPolyline(xs, ys, index);
					model.notifyViews();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (!animating) {
				g.drawPolyline(xs, ys, index - 1);
			}
			curStroke++;
		}
	}

	public void paintComponent(Graphics gr) {
		if (count == 0) { // first time drawing, record original size of window
			originalWidth = this.getWidth();
		originalHeight = this.getHeight();
		count++;
		}
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.scale((double)this.getWidth()/originalWidth, (double)this.getHeight()/originalHeight);
		int curStroke = 0;
		for (Stroke s : model.getStrokes()) {
			g.setColor(s.getColor());
			g.setStroke(new BasicStroke(s.getThickness()));
			List<Point> points = s.getPoints();
			int [] xs = new int [points.size()];
			int [] ys = new int [points.size()];
			int index = 0;
			for (Point p : points) {
				xs[index] = p.getX();
				ys[index] = p.getY();
				index++;
				if (curStroke + 1 > model.getCurStroke() && index > model.getCurPoint()) {
					break;
				}
			}
			g.drawPolyline(xs, ys, index - 1);
			curStroke++;
			if (curStroke > model.getCurStroke()) break;
		}
	}

}
