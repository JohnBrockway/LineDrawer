import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		Model model = new Model();
        JComponent canvas = new CanvasView(model);
        JComponent colors = new ColorView(model);
        JComponent thickness = new ThicknessView(model);
        JComponent slider = new SliderView(model);
        
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileSave = new JMenuItem("Save Current Doodle");
        JMenuItem fileNew = new JMenuItem("Open Blank Doodle");
        JMenuItem fileOpen = new JMenuItem("Open Saved Doodle");
        JMenuItem fileClose = new JMenuItem("Close Doodle");
        
        fileMenu.add(fileSave);
        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileClose);
        
        menu.add(fileMenu);
        
        JPanel west = new JPanel();
        west.setLayout(new GridLayout(2, 1));
        west.add(colors);
        west.add(thickness);
        
        JPanel south = new JPanel();
        JPanel subsouth = new JPanel();
        subsouth.setLayout(new GridLayout(1, 2));
        JButton play = new IconButton(0);
        JButton start = new IconButton(1);
        JButton end = new IconButton(2);
        subsouth.add(start);
        subsouth.add(end);
        south.setLayout(new BorderLayout());
        south.add(play, BorderLayout.WEST);
        south.add(slider, BorderLayout.CENTER);
        south.add(subsouth, BorderLayout.EAST);
        
        JFrame frame = new JFrame("Line Doodler");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.getContentPane().add(west, BorderLayout.WEST);
        frame.getContentPane().add(south, BorderLayout.SOUTH);
        frame.setJMenuBar(menu);
        frame.pack();
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(250, 150));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        fileSave.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                model.save();
            }
		});
        
        fileNew.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                model.blank();
            }
		});
        
        fileOpen.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                model.open();
            }
		});
        
        fileClose.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                model.close();
            }
		});
        
        play.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                ((CanvasView) canvas).animate();
            }
		});
        
        start.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                model.setCurPoint(0);
                model.setCurStroke(0);
            }
		});
        
        end.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                model.setCurPoint(model.getStrokes().get(model.getStrokes().size()-1).getPoints().size());
                model.setCurStroke(model.getStrokes().size()-1);
            }
		});
	}

}
