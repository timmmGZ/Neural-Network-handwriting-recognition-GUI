package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import neuralNetwork.TrainSet;

public class ReadWriteFile {
	public static List<TrainSet> readTrainingSets() throws IOException {
		List<TrainSet> trainingSets = new ArrayList<>();
		Files.walk(Paths.get("dataset"), 1).filter(Files::isRegularFile).map(Path::toString).forEach(s -> {
			try {
				String word = s.substring(s.indexOf('\\') + 1, s.indexOf('.'));
				for (List<Integer> trainVector : readFromSingleTxt(s))
					trainingSets.add(new TrainSet(trainVector, TrainSetsInfo.getTrueOutput(word)));
			} catch (FileNotFoundException e) {
			}
		});
		return trainingSets;
	}

	private static List<List<Integer>> readFromSingleTxt(String filename) throws FileNotFoundException {
		List<List<Integer>> inputsForSingleWord = new ArrayList<>();
		for (Scanner s = new Scanner(new FileInputStream(new File(filename))); s.hasNextLine();)
			inputsForSingleWord.add(
					Arrays.asList(s.nextLine().split("")).stream().map(Integer::parseInt).collect(Collectors.toList()));
		return inputsForSingleWord;
	}

	public static void saveToFile(List<Integer> input, String filename) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(("dataset/" + filename + ".txt")), true));
			for (Integer i : input)
				pw.write(Integer.toString(i));
			pw.write("\n");
			pw.close();
		} catch (Exception e) {
		}
	}

}
