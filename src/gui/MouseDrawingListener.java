package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

public class MouseDrawingListener extends DrawingPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	MainFrame mainPanel;

	public MouseDrawingListener(MainFrame m, int w, int h, int count) {
		super(w, h, count);
		mainPanel = m;
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	private void setPixel(MouseEvent e) {
		try {
			map[e.getY() / pixelH][e.getX() / pixelW] = SwingUtilities.isLeftMouseButton(e) ? 1 : 0;
			repaint();
		} catch (Exception exc) {
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setPixel(e);
		mainPanel.trainer.setCurrentMapAsInputs(mapToVector());
		List<Double> outputs = mainPanel.trainer.getOutputs();
		int index = outputs.indexOf(Collections.max(outputs));
		try {
			mainPanel.probPanel.updateProbs();
			mainPanel.predict.setText(mainPanel.info.getTrainWordNames().get(index));
		} catch (IOException e1) {
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setPixel(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
