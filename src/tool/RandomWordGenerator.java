package tool;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import neuralNetwork.Neuron;
import neuralNetwork.TrainSet;

public class RandomWordGenerator {
	private double prob[] = new double[1600];
	private double[] weights;
	private double biasWeight;

	public RandomWordGenerator(Neuron n) {
		weights = n.getWeights();
		biasWeight = n.getBiasWeight();
	}

	public int[][] randomDrawing(String word) throws IOException {
		createProbabilitiesForPixels(readTrainingSetsTxts(word));
		double sum = 0;
		double[][] total = new double[40][40];
		int[][] randomInput = new int[40][40];
		for (int i = 0; i < 100; i++) {
			do {
				sum = 0;
				randomInput = new int[40][40];
				for (int y = 0; y < 40; y++)
					for (int x = 0; x < 40; x++) {
						double random = Math.random();
						random = random <= prob[y * 40 + x] ? 1 : 0;
						randomInput[y][x] = (int) random;
						sum += (double) random * (double) weights[y * 40 + x];
					}
				sum += biasWeight;
			} while (Neuron.sigmoid(sum) < 0.90);
			for (int y = 0; y < 40; y++)
				for (int x = 0; x < 40; x++)
					total[y][x] += randomInput[y][x];
		}
		for (int y = 0; y < 40; y++)
			for (int x = 0; x < 40; x++) {
				total[y][x] = total[y][x] / 100 >= 0.2 ? 2 : 0;
				randomInput[y][x] = (int) total[y][x];
			}
		return randomInput;
	}

	public void createProbabilitiesForPixels(List<TrainSet> trainSet) throws IOException {
		for (TrainSet t : trainSet) {
			int i = 0;
			for (int pixel : t.getInputs())
				prob[i++] += pixel == 1 ? 1.0 / trainSet.size() : 0;
		}
	}

	public static List<TrainSet> readTrainingSetsTxts(String word) throws IOException {
		return ReadWriteFile.readFromSingleTxt("dataset\\" + word + ".txt").stream()
				.map(ts -> new TrainSet(ts, TrainSetsInfo.getTrueOutput(word))).collect(Collectors.toList());
	}

}
