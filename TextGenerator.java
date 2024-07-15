package comprehensive;

/**
 * A class for generating text using a Markov Chain based on user-provided
 * arguments.
 * 
 * @author Phuc Do and Khang Nguyen
 * @version April 23, 2024
 */

public class TextGenerator {
	/**
	 * Main method for executing the text generation process.
	 * 
	 * @param args command-line arguments:
	 * 				args[0]: the path to the input text file
	 *             args[1]: the seed word to start text generation 
	 *             args[2]: the order (K) of the Markov Chain 
	 *             args[3] (optional): options for text generation ("one" or "all")
	 */

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Please provide at least 3 arguments");
		}
		String path = args[0];
		String seed = args[1];
		String K = args[2];

		String opts = null;// Options for text generation ("one" or "all")

		if (args.length == 4) {
			opts = args[3];
		}
		// Create an instance of TextGeneratorProcess to handle text generation
		TextGeneratorProcess processor = new TextGeneratorProcess(path, seed, Integer.parseInt(K), opts);
		processor.run();

	}

}