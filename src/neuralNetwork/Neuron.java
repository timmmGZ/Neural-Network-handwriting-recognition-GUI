package neuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
	private ArrayList<Integer> inputs = new ArrayList<>();
	private static double learningRate = 0.05;
	private double weights[] = new double[1600];
	private static int bias = 1;
	private double biasWeight = Math.random();

	public Neuron() {
		for (int i = 0; i < inputs.size(); i++)
			weights[i] = Math.random();
	}

	public void setInputs(List<Integer> list) {
		inputs = new ArrayList<>(list);
	}

	public void modifyWeights(double error) {
		for (int i = 0; i < inputs.size(); i++)
			weights[i] += learningRate * error * inputs.get(i);
		biasWeight += learningRate * error * bias;
	}

	public double perdictedOutput() {
		double sum = 0;
		for (int i = 0; i < inputs.size(); i++)
			sum += inputs.get(i) * weights[i];
		sum += bias * biasWeight;
		return sigmoid(sum);
	}

	public static double sigmoid(Double sum) {
		return (1 / (1 + Math.exp(-sum)));
	}

}
