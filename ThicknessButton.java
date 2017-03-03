import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class ThicknessButton extends JButton {

	private int thickness;
	
	private boolean selected;
	
	public ThicknessButton(int thickness, boolean selected) {
		this.thickness = thickness;
		this.selected = selected;
	}
	
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setStroke(new BasicStroke(thickness));
		if (selected) {
			g.setColor(Color.gray);
		}
		else {
			g.setColor(Color.white);
		}
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setStroke(new BasicStroke(thickness));
		g.setColor(Color.BLACK);
		g.drawLine(10, (this.getHeight()/2), this.getWidth() - 10, (this.getHeight()/2));
	}
	
	public int getThickness() {
		return thickness;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
