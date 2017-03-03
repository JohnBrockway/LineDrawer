import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stroke {
	private Color color;
	private List<Point> points;
	private int thickness;
	
	public Stroke(Color c, int thickness) {
		this.color = c;
		this.thickness = thickness;
		points = new ArrayList();
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getThickness() {
		return thickness;
	}
}
