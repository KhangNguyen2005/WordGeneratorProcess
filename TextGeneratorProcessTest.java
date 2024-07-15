package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextGeneratorProcessTest {
	TextGeneratorProcess process;

	

	// When theres no word make seed the next word
	@Test
	void testNoNextWordReturnSeedOptsAll() {


		String seedWord = "hello";
		String generatedText = seedWord;
		int wordsToGenerate = 7;
		
		TextGeneratorProcess chain = new TextGeneratorProcess("helloWorld.txt ",seedWord, wordsToGenerate,"all");
		MarkovChain chain = new MarkovChain(null);
		  // Generate additional words until the desired length is reached
        for (int i = 0; i < wordsToGenerate - 1; i++) {
            chain.run();
            
           String generatedText = chain;
        
		// Verify the generated text matches the expected output
		assertEquals("hello world hello world hello world hello", generatedText);
	}

	@Test
	void testNoNextWordReturnSeedOptsOne() {
		MarkovChain chain = new MarkovChain("one");

		// Add some data to the Markov chain
		chain.add("hello");
		chain.add("world");

		String seedWord = "hello";
		String generatedText = seedWord;
		int wordsToGenerate = 7;
		// Generate text using the Markov chain
		for (int i = 0; i < wordsToGenerate; i++) {
			String nextWord = chain.getNext(seedWord);
			if (nextWord != null) {
				generatedText += " " + nextWord;
				seedWord = nextWord;
			} else {
				// No possible next word, use the seed word again
				generatedText += " " + seedWord;
			}
		}

		// Verify the generated text matches the expected output
		assertEquals("hello world hello world hello world hello", generatedText);
	}

	@Test
	void testSameProbabilityToLexicoComparison() {
        TextGeneratorProcess process = new TextGeneratorProcess("abc.txt", "a", 5, "one");
        
        Set<Map.Entry<String, Double>> nextWords = process.getNextWords(5,"a");

	}
	// random prediction has possible result (0/5)
		@Test
		void testRandomPrediction() {
			MarkovChain chain = new MarkovChain("one");

			chain.add("hello");
			chain.add("world");
			chain.add("good");
			chain.add("morning");
			chain.add("neighbor");

			Random rnd = new Random();
			String seedWord = "a";
			String generatedText = seedWord;

			for (int i = 0; i < rnd.nextInt(100); i++) {
				String nextWord = chain.getNext(seedWord);
				if (nextWord != null) {
					generatedText += " " + nextWord;
					seedWord = nextWord;
				} else {
					break; // Exit loop if nextWord is null
				}
			}
			// Check if generated text contains more than just the seed word
			assertTrue(generatedText.length() > 1);
			// You can add more assertions to verify the format/content of the generated
			// text
		}

			//Checking for punctuation, lowercase, ...
			@Test
			void testPunctuation() {
				// Initialize TextGeneratorProcess with tricky formatting
		        TextGeneratorProcess process = new TextGeneratorProcess("trickyFormatting.txt", "<nope>", 1, "one");

		        // Get the seed word after punctuation and lowercase conversion
		        	
		        // Assert that the seed word is as expected after punctuation and lowercase conversion
		        assertEquals("nope", process, "Seed word should be 'nope' after punctuation and lowercase conversion");
			}
			
			//predict sequence requiring a resetting to seed (0/5)
			@Test
			void testResettingToSeed() {
		        String seedWord = "hello";
				String generatedText = seedWord;
				int wordsToGenerate = 5;
		        TextGeneratorProcess process = new TextGeneratorProcess("helloWorld.txt", seedWord, wordsToGenerate, "one");

		        for (int i = 0; i < wordsToGenerate; i++) {
					String nextWord = chain.put();
					if (nextWord != null) {
						generatedText += " " + nextWord;
						seedWord = nextWord;
					}
		        }
				assertEquals("hello world hello world hello world hello", generatedText);

			}
}
