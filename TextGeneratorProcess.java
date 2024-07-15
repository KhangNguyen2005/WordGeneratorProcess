package comprehensive;

import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * A class responsible for processing text generation using a Markov Chain based
 * on the provided input file and seed word.
 * 
 * @author Phuc Do and Khang Nguyen
 * @version April 23, 2024
 */
public class TextGeneratorProcess {
	private MarkovChain model;
	private String seed;
	private String path;
	private int K;
	private String opts;

	/**
	 * Constructs a TextGeneratorProcess with the specified parameters.
	 *
	 * @param path the path to the input text file
	 * @param seed the seed word for text generation
	 * @param K    the order (K) of the Markov Chain
	 * @param opts options for text generation ("one" or "all")
	 */

	public TextGeneratorProcess(String path, String seed, int K, String opts) {
		this.path = path;
		this.seed = seed.toLowerCase();
		this.K = K;
		this.opts = opts;
	}

	/**
	 * Runs the text generation process.
	 */

	public void run() {
		toMarkovChain(path, opts);
		if (opts == null) {
			getNextWords(K, seed);
		} else {
			getNextWordOpts(seed, K);
		}
	}

	/**
	 * Transfers the contents of the text file to the Markov Chain model.
	 *
	 * @param file the path to the input text file
	 * @param opts options for text generation ("one" or "all")
	 */
	private void toMarkovChain(String file, String opts) {
		model = new MarkovChain(opts);
		// read file
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			// go through each line
			while ((line = reader.readLine()) != null) {
				// iterate and add each word
				String[] words = line.toLowerCase().split("\\s+");
				for (String word : words) {
					String[] subwords = word.toLowerCase().split("[^a-zA-Z0-9_]+");
					if (subwords.length > 0 && !subwords[0].isEmpty()) {
						model.add(subwords[0]);
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the next words based on the given seed and Markov Chain order.
	 *
	 * @param K    the order (K) of the Markov Chain
	 * @param seed the seed word
	 */
	private void getNextWords(int K, String seed) {
		StringBuilder resultBuilder = new StringBuilder();
		int count = 1;

		Set<Map.Entry<String, Double>> entry;
		entry = model.getNextWords(seed);
		for (Map.Entry<String, Double> mapEntry : entry) {
			// Append key-value pair to StringBuilder
			if (count > K || mapEntry.getKey() == null) {
				break;
			}
			resultBuilder.append(mapEntry.getKey()).append(" "); // Add newline for better readability, adjust as needed
			count++;
		}
		System.out.println(resultBuilder.toString().trim());
	}

	/**
	 * Retrieves the next words with options based on the given seed and count.
	 *
	 * @param seed  the seed word
	 * @param count the number of words to generate
	 */

	private void getNextWordOpts(String seed, int count) {
		StringBuilder resultBuilder = new StringBuilder();

		resultBuilder.append(seed).append(" "); // Append seed as the first word

		String currentWord = seed;
		for (int i = 0; i < count - 1; i++) {
			String nextWord = model.getNext(currentWord);
			if (nextWord != null) {
				resultBuilder.append(nextWord).append(" ");
				currentWord = nextWord;
			} else {
				resultBuilder.append(seed).append(" ");
				currentWord = seed;
			}
		}
		System.out.println(resultBuilder.toString().trim());
	}

}
