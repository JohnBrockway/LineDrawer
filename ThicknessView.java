import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class ThicknessView extends JPanel implements View {

	private Model model;
	
	private ThicknessButton [] buttons;
	
	public ThicknessView(Model model) {
		this.model = model;
		model.addView(this);
		
		buttons = new ThicknessButton [3];
		buttons[0] = new ThicknessButton(2, false);
		buttons[1] = new ThicknessButton(5, true);
		buttons[2] = new ThicknessButton(10, false);
		
		this.setLayout(new GridLayout(3, 1));
		for (int i = 0 ; i < buttons.length ; i++) {
			this.add(buttons[i]);
		}
		
		for (int i = 0 ; i < buttons.length ; i++) {
			buttons[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					model.setThickness(((ThicknessButton) e.getComponent()).getThickness());
					setAllFalse();
					((ThicknessButton) e.getComponent()).setSelected(true);
	            }
			});
		}
		
	}
	
	public void setAllFalse() {
		for (int i = 0 ; i < buttons.length ; i++) {
			buttons[i].setSelected(false);
		}
	}
	
	@Override
	public void update() {
		repaint();
	}

}
