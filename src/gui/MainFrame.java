package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import neuralNetwork.TrainSet;
import neuralNetwork.Trainer;
import tool.ReadWriteFile;
import tool.TrainSetsInfo;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int RESOLUTION = 40;
	TrainSetsInfo info = new TrainSetsInfo();
	Trainer trainer = new Trainer();
	private DrawingPanel drawPanel = new MouseDrawingListener(this, 400, 400, RESOLUTION);
	ProbabilityPanel probPanel = new ProbabilityPanel(this, 27);
	JLabel predict = new JLabel(" ?", SwingConstants.CENTER);
	private JButton clearButton = new JButton("Clear");
	private JButton randomDrawing = new JButton("Random drawing of->");
	private JButton trainNetworkButton = new JButton("Train K times:");
	private JButton addToTrainSetButton = new JButton("<-Add to train set");
	private JTextField trainingTimes = new JFormattedTextField("2000");
	private JComboBox<String> trainSelection = new JComboBox<>(info.getTrainWordNamesArray());

	public static void main(String[] args) throws IOException {
		new MainFrame();
	}

	public MainFrame() throws IOException {
		super("Hand Writing Recognization                                           --TimmmGZ");
		setBottuns();
		setLeft();
		setRight();
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setLeft() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(0, 206, 209));
		setLeftUp(panel);
		setLeftDown(panel);
		add(panel);
	}

	private void setLeftUp(JPanel jPanel) {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(156, 206, 209));
		panel.setBorder(BorderFactory.createEmptyBorder(33, 0, 33, 0));
		panel.add(drawPanel);
		jPanel.add(panel, BorderLayout.NORTH);
	}

	private void setLeftDown(JPanel jPanel) {
		JPanel panel = new JPanel(new GridLayout(0, 3, 3, 0));
		panel.setBackground(new Color(0, 206, 209));
		panel.add(trainNetworkButton);
		panel.add(trainingTimes);
		panel.add(clearButton);
		panel.add(randomDrawing);
		panel.add(trainSelection);
		panel.add(addToTrainSetButton);
		jPanel.add(predict, BorderLayout.CENTER);
		predict.setFont(new Font("MS Song", Font.BOLD, 60));
		jPanel.add(panel, BorderLayout.SOUTH);
	}

	private void setRight() {
		add(probPanel, BorderLayout.EAST);
	}

	private void setBottuns() {
		randomDrawing.addActionListener(e -> {
			drawPanel.drawRandomWord(this, (String) trainSelection.getSelectedItem());
		});
		clearButton.addActionListener(e -> {
			drawPanel.clear();
			predict.setText("?");
		});
		addToTrainSetButton.addActionListener(e -> {
			String word = (String) trainSelection.getSelectedItem();
			trainer.addTrainSet(new TrainSet(drawPanel.mapToVector(), TrainSetsInfo.getTrueOutput(word)));
			ReadWriteFile.saveToTxt(drawPanel.mapToVector(), word);
		});

		trainNetworkButton.addActionListener(e -> {
			try {
				int number = Integer.parseInt(trainingTimes.getText());
				if (number <= 0)
					JOptionPane.showMessageDialog(null, "Please enter > 0", "ERROR", JOptionPane.WARNING_MESSAGE);
				else
					trainer.train(number);
			} catch (Exception x) {
				JOptionPane.showMessageDialog(null, "Wrong input", "ERROR", JOptionPane.WARNING_MESSAGE);
			}
		});
	}

}
