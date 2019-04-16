package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

public class MouseDrawingListener extends DrawingPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	MainFrame mainFrame;

	public MouseDrawingListener(MainFrame m, int w, int h, int count) {
		super(w, h, count);
		mainFrame = m;
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	private void setPixel(MouseEvent e) {
		try {
			map[e.getY() / pixelH][e.getX() / pixelW] = SwingUtilities.isLeftMouseButton(e) ? 1 : 0;
			repaint();
			mainFrame.trainer.network.setAllNeuronsInputs(mapToVector());
			List<Double> outputs = mainFrame.trainer.network.getOutputs();
			mainFrame.probPanel.updateProbs();
			mainFrame.predict
					.setText(mainFrame.info.getTrainWordNames().get(outputs.indexOf(Collections.max(outputs))));
		} catch (Exception exc) {
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setPixel(e);
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
