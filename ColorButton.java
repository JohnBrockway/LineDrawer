import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ColorButton extends JButton {

	private Color color;

	public ColorButton(Color color) {
		this.color = color;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public Color getColor() {
		return color;
	}

}
