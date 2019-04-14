package neuralNetwork;

import java.util.List;

public class TrainSet {

	private List<Integer> inputs;
	private List<Integer> trueOuput;

	public TrainSet(List<Integer> in, List<Integer> out) {
		inputs = in;
		trueOuput = out;
	}

	public List<Integer> getInputs() {
		return inputs;
	}

	public List<Integer> getTrueOutput() {
		return trueOuput;
	}
}
