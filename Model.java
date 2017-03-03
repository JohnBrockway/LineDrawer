import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Model {

	private List<View> views;
	private List<Stroke> strokes;

	private int curStroke;
	private int curPoint;

	private Color curColor;
	private int curThickness;

	private JFileChooser chooser;
	private FileNameExtensionFilter filter;

	public boolean newPoint;

	public Model() {
		views = new ArrayList();
		strokes = new ArrayList();
		curColor = Color.BLACK;
		curThickness = 5;
		curStroke = -1;
		curPoint = -1;
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter("Doodle Files", "dood");
		chooser.setFileFilter(filter);
		newPoint = false;
	}

	public void addView(View view) {
		views.add(view);
	}

	public void removeObserver(View view) {
		views.remove(view);
	}

	public List<Stroke> getStrokes() {
		return strokes;
	}

	public void addStroke(Color c) {
		if (curStroke != -1 && (curStroke < strokes.size() - 1 || curPoint < strokes.get(curStroke).getPoints().size()-1)) {
		for (int j = strokes.size() - 1 ; j >= 0 ; j--) {
				if (j > curStroke) {
					strokes.remove(j);
					continue;
				}
				else if (strokes.indexOf(strokes.get(j)) == curStroke) {
					for (int i = strokes.get(j).getPoints().size() - 1 ; i >= 0 ; i--) {
						if (i > curPoint) {
							strokes.get(j).getPoints().remove(i);
						}
					}
				}
			}
		}
		strokes.add(new Stroke(c, curThickness));
		curPoint = -1;
		curStroke++;
		notifyViews();
	}

	public void addPoint(int x, int y) {
		strokes.get(strokes.size() - 1).addPoint(new Point(x, y));
		curPoint++;
		newPoint = true;
		notifyViews();
	}

	public Color getColor() {
		return curColor;
	}

	public void setColor(Color c) {
		curColor = c;
		notifyViews();
	}

	public int getThickness() {
		return curThickness;
	}

	public void setThickness(int t) {
		curThickness = t;
		notifyViews();
	}

	public void notifyViews() {
		for (View view: views) {
			view.update();
		}
	}

	public void save() {
		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			BufferedWriter output = null;
			String path;
			try {
				if (chooser.getSelectedFile().getName().substring
						(chooser.getSelectedFile().getName().length() - 5).equals(".dood")) {
					path = chooser.getSelectedFile().getAbsolutePath();
				}
				else {
					path = chooser.getSelectedFile().getAbsolutePath() + ".dood";
				}
			}
			catch (StringIndexOutOfBoundsException a) {
				path = chooser.getSelectedFile().getAbsolutePath() + ".dood";
			}
			try {
				output = new BufferedWriter(new FileWriter(new File(path)));
				for (Stroke s : strokes) {
					output.write(s.getColor().getRed() 
							+ "@" + s.getColor().getGreen() 
							+ "@" + s.getColor().getBlue() + "\n");
					output.write(s.getThickness() + "\n");
					List<Point> points = s.getPoints();
					for (Point p : points) {
						output.write(p.getX() + "@" + p.getY());
						if (points.indexOf(p) != points.size() - 1) {
							output.write(":");
						}
					}
					output.write("\n");
				}
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void blank() {
		if (!strokes.isEmpty()) {
			int result = JOptionPane.showOptionDialog(null, "Would you like to save the current doodle?", "Warning: Contents will be lost", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, JOptionPane.NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				save();
			}
		}
		strokes = new ArrayList();
		curStroke = -1;
		curPoint = -1;
		notifyViews();
	}

	public void open() {
		if (!strokes.isEmpty()) {
			int result = JOptionPane.showOptionDialog(null, "Would you like to save the current doodle?", "Warning: Contents will be lost", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, JOptionPane.NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				save();
			}
		}
		curStroke = -1;
		curPoint = -1;
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				BufferedReader input = new BufferedReader(new FileReader(chooser.getSelectedFile()));
				String line = input.readLine();
				strokes = new ArrayList();
				while (line != null) {
					String [] colors = line.split("@");
					line = input.readLine();
					setThickness(Integer.parseInt(line));
					addStroke(new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2])));
					line = input.readLine();
					String [] points = line.split(":");
					for (int i = 0 ; i < points.length ; i++) {
						String [] xy = points[i].split("@");
						addPoint(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
					}
					line = input.readLine();
				}
				input.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not Found!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		notifyViews();
	}

	public void close () {
		if (!strokes.isEmpty()) {
			int result = JOptionPane.showOptionDialog(null, "Would you like to save the current doodle?", "Warning: Contents will be lost", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, JOptionPane.NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				save();
			}
		}
		System.exit(0);
	}

	public int getCurStroke() {
		return curStroke;
	}

	public int getCurPoint() {
		return curPoint;
	}

	public void setCurStroke(int s) {
		curStroke = s;
		notifyViews();
	}

	public void setCurPoint(int p) {
		curPoint = p;
		notifyViews();
	}
}
