package neuralNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import repository.ReadWriteFile;

public class Trainer {
	private Network network;
	private List<TrainSet> trainSets;

	public Trainer() throws IOException {
		network = new Network();
		trainSets = ReadWriteFile.readTrainingSets();
	}

	public void train(int count) {
		new Thread(() -> {
			for (int i = 0; i < count; i++) {
				TrainSet trainSet = trainSets.get((int) (Math.random() * trainSets.size()));
				network.setAllNeuronsInputs(trainSet.getInputs());
				network.modifyAllNeuronsWeights(trainSet.getTrueOutput());
			}
		}).start();
	}

	public void setCurrentMapAsInputs(ArrayList<Integer> inputs) {
		network.getNeurons().forEach(n -> n.setInputs(inputs));
	}

	public void addTrainSet(TrainSet newSet) {
		trainSets.add(newSet);
	}

	public List<Double> getOutputs() {
		return network.getNeurons().stream().map(n -> n.perdictedOutput()).collect(Collectors.toList());
	}

}
