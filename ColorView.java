import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColorView extends JPanel implements View {

	private Model model;
	
	private ColorButton [] buttons;
	private JButton ellipsis;
	private JLabel curColorLabel;
	private JLabel curColor;
	
	public ColorView(Model model) {
		this.model = model;
		model.addView(this);
		
		buttons = new ColorButton[11];
		
		buttons[0] = new ColorButton(Color.red);
		buttons[1] = new ColorButton(Color.blue);
		buttons[2] = new ColorButton(Color.green);
		buttons[3] = new ColorButton(Color.yellow);
		buttons[4] = new ColorButton(Color.white);
		buttons[5] = new ColorButton(Color.black);
		buttons[6] = new ColorButton(Color.gray);
		buttons[7] = new ColorButton(Color.magenta);
		buttons[8] = new ColorButton(Color.orange);
		buttons[9] = new ColorButton(Color.pink);
		buttons[10] = new ColorButton(Color.cyan);
		ellipsis = new JButton("More...");
		curColorLabel = new JLabel("Current Color:");
		curColor = new JLabel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(model.getColor());
				g.fillRect(0, 0, curColor.getWidth(), curColor.getHeight());
			}
		};
		
		this.setLayout(new GridLayout(7, 2));
		
		for (int i = 0 ; i < buttons.length ; i++) {
			this.add(buttons[i]);
		}
		this.add(ellipsis);
		this.add(curColorLabel);
		this.add(curColor);
		
		for (int i = 0 ; i < buttons.length ; i++) {
			buttons[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					model.setColor(((ColorButton) e.getComponent()).getColor());
	            }
			});
		}
		
		ellipsis.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Color c = JColorChooser.showDialog(null, "Choose Line Color", Color.white);
				model.setColor(c);
            }
		});
	}
	
	@Override
	public void update() {
		repaint();
	}

}
