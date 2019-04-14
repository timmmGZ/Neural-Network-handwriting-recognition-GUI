package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public int width;
	public int pixelW;
	public int height;
	public int pixelH;
	public int resolution;
	protected int[][] map;

	public DrawingPanel(int w, int h, int c) {
		super();
		width = w;
		height = h;
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
	}

	public ArrayList<Integer> mapToVector() {
		ArrayList<Integer> vector = new ArrayList<>();
		for (int y = 0; y < resolution; y++)
			for (int x = 0; x < resolution; x++)
				vector.add(map[y][x] == 1 ? 1 : 0);
		return vector;
	}

	public void cleasr() {
		map = new int[resolution][resolution];
		repaint();
	}

	public void clear() {
		for (int y = 0; y < resolution; y++)
			for (int x = 0; x < resolution; x++) {
				System.out.print(map[y][x]);
				map[y][x] = 0;
			}
		System.out.println();
		map = new int[resolution][resolution];
		repaint();
	}

	public void drawLetter(List<Integer> trueHLOutput) {
		for (int i = 0; i < trueHLOutput.size(); i++)
			map[i / resolution][i % resolution] = trueHLOutput.get(i) == 1 ? 1 : 0;
		repaint();
	}

}
