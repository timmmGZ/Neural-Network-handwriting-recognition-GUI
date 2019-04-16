package gui;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import tool.RandomWordGenerator;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public int pixelW, pixelH, resolution;
	protected int[][] map;

	public DrawingPanel(int w, int h, int c) {
		super();
		pixelW = w / c;
		pixelH = h / c;
		resolution = c;
		map = new int[resolution][resolution];
		setPreferredSize(new Dimension(w, h));
		setBackground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for (int y = 0; y < resolution; y++)
			for (int x = 0; x < resolution; x++)
				if (map[y][x] == 1)
					g.fillRect(x * pixelW, y * pixelH, pixelW, pixelH);
		g.setColor(Color.RED);
		for (int y = 0; y < resolution; y++)
			for (int x = 0; x < resolution; x++)
				if (map[y][x] == 2)
					g.fillRect(x * pixelW, y * pixelH, pixelW, pixelH);
	}

	public List<Integer> mapToVector() {
		return Arrays.stream(map).map(Arrays::stream).map(e -> e.boxed()).flatMap(s -> s).collect(Collectors.toList());
	}// map ->List<int[]> ->List<IntStream> ->List<Stream<Integer>> ->Stream<Integer>

	public void clear() {
		map = new int[resolution][resolution];
		repaint();
	}

	public void drawRandomWord(MainFrame frame, String word) {
		try {
			map = new RandomWordGenerator(frame.trainer.network.getNeurons() //
					.get(frame.info.getTrainWordNames().indexOf(word))).randomDrawing(word);
		} catch (IOException e) {
		}
		repaint();
	}
}
