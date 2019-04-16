package neuralNetwork;

import java.io.IOException;
import java.util.List;

import tool.ReadWriteFile;

public class Trainer {
	public Network network;
	private List<TrainSet> trainSets;

	public Trainer() throws IOException {
		network = new Network();
		trainSets = ReadWriteFile.readTrainingSetsTxts();
		train(2000);
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

	public void addTrainSet(TrainSet newSet) {
		trainSets.add(newSet);
	}

}
