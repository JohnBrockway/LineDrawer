import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class IconButton extends JButton {

	private int type;

	public IconButton (int type) {
		this.type = type;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (type == 0) {
			g.setColor(Color.green);
			int [] xPoints = {getWidth()/3, 2*getWidth()/3, getWidth()/3};
			int [] yPoints = {getHeight()/3, getHeight()/2, 2*getHeight()/3};
			g.fillPolygon(xPoints, yPoints, 3);
		}
		else if (type == 1) {
			g.setColor(Color.black);
			Graphics2D g2 = (Graphics2D) g;
			g2.fillRect(getWidth()/4, getHeight()/4, 5, getHeight()/2);
			g2.setStroke(new BasicStroke(4));
			g2.drawLine(getWidth()/4 + 2, (getHeight()/2) - 2, 3*getWidth()/4, (getHeight()/2) - 2);
		}
		else if (type == 2) {
			g.setColor(Color.black);
			Graphics2D g2 = (Graphics2D) g;
			g2.fillRect((3*getWidth()/4) - 5, getHeight()/4, 5, getHeight()/2);
			g2.setStroke(new BasicStroke(4));
			g2.drawLine(getWidth()/4, (getHeight()/2) - 2, 3*getWidth()/4 - 2, (getHeight()/2) - 2);
		}
	}

}
