import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.Hashtable;

public class SliderView extends JPanel implements View, ChangeListener {

	private Model model;

	private JSlider slider;

	private boolean skipChanged;

	public SliderView(Model model) {
		this.model = model;
		model.addView(this);

		slider = new JSlider(0, 1000, 1000);
		slider.setPaintLabels(true);
		this.add(slider);
		slider.setEnabled(false);
		slider.addChangeListener(this);
		skipChanged = false;
	}

	@Override
	public void update() {
		slider.setEnabled(true);
		int totalPoints = 0;
		int index = 0;
		int [] amounts = new int [model.getStrokes().size()];
		for (int i = 0 ; i < amounts.length ; i++) {
			amounts[i] = 0;
		}
		for (Stroke s : model.getStrokes()) {
			for (Point p : s.getPoints()) {
				amounts[index]++;
				totalPoints++;
			}
			index++;
		}
		Hashtable<Integer, JLabel> dict = new Hashtable<Integer, JLabel>();
		dict.put(0, new JLabel(""));
		int [] cumulative = new int [amounts.length];
		for (int i = 0 ; i < amounts.length ; i++) {
			cumulative[i] = ((int) (1000*((double)amounts[i]/totalPoints)));
			if (i != 0) {
				cumulative[i] += cumulative[i-1];
			}
			dict.put(cumulative[i], new JLabel("|"));
		}
		slider.setLabelTable(dict);

		if (model.getCurStroke() > 0) {
			cumulative[model.getCurStroke()] -= cumulative[model.getCurStroke() - 1];
		}
		try {
			int place = (int) (cumulative[model.getCurStroke()] * (double)model.getCurPoint()/amounts[model.getCurStroke()]);
			if (model.getCurStroke() > 0) {

				place += cumulative[model.getCurStroke() - 1];
			}
			skipChanged = true;
			slider.setValue(place);
			repaint();
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (!skipChanged) {
			JSlider source = (JSlider)e.getSource();
			int place = (int)source.getValue();
			int totalPoints = 0;
			int index = 0;
			int [] amounts = new int [model.getStrokes().size()];
			for (int i = 0 ; i < amounts.length ; i++) {
				amounts[i] = 0;
			}
			for (Stroke s : model.getStrokes()) {
				for (Point p : s.getPoints()) {
					amounts[index]++;
					totalPoints++;
				}
				index++;
			}
			int stroke = -1;
			int [] cumulative = new int [amounts.length];
			for (int i = 0 ; i < amounts.length ; i++) {
				cumulative[i] = ((int) (1000*((double)amounts[i]/totalPoints)));
				if (i != 0) {
					cumulative[i] += cumulative[i-1];
				}
				if (place < cumulative[i]){
					stroke = i;
					break;
				}
			}
			if (stroke == -1) stroke = cumulative.length - 1;
			if (stroke > 0) {
				place -= cumulative[stroke - 1];
				cumulative[stroke] -= cumulative[stroke - 1];
			}
			int point = (int) (amounts[stroke] * (double)place/cumulative[stroke]);
			model.setCurStroke(stroke);
			model.setCurPoint(point);
		}
		skipChanged = false;
	}

}
