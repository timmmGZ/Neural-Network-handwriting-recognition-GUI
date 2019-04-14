package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TrainSetsInfo {
	private List<String> trainWordPaths;
	private List<String> trainWordNames = new ArrayList<>();
	private static Map<String, List<Integer>> trueOutputs = new TreeMap<>();

	public TrainSetsInfo() throws IOException {
		vectorsParser(trueOutputs, "dataset/standardOutput/trueOutput.txt");
		trainWordPaths = Files.walk(Paths.get("dataset"), 1).filter(Files::isRegularFile).map(Path::toString)
				.collect(Collectors.toList());
	}

	public List<String> getTrainWordsPath() {
		return trainWordPaths;
	}

	public List<String> getTrainWordNames() {
		return trainWordNames;
	}

	public String[] getTrainWordNamesArray() {
		return trainWordNames.toArray(new String[getTrainWordNames().size()]);
	}

	public static List<Integer> getTrueOutput(String letter) {
		return trueOutputs.get(letter);
	}

	public void vectorsParser(Map<String, List<Integer>> map, String file) throws FileNotFoundException {
		for (Scanner s = new Scanner(new FileInputStream(new File(file))); s.hasNextLine();) {
			String[] line = s.nextLine().split(",");
			trainWordNames.add(line[0]);
			map.put(line[0],
					Arrays.asList(line[1].split("")).stream().map(Integer::parseInt).collect(Collectors.toList()));
		}
	}
}
