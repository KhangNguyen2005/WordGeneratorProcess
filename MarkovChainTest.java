package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarkovChainTest {
	MarkovChain chain;

	@BeforeEach
	void setUp() throws Exception {
		chain = new MarkovChain("one");

	}



//Select best option of 1 possibility (0/5)
	@Test
	void testBestOptionOf1Possiblity() {
		// Add some sample data
		MarkovChain markovChain = new MarkovChain("one");

		markovChain.add("hello");
		markovChain.add("world");
		markovChain.add("good");
		markovChain.add("morning");
		markovChain.add("neighbor");

		// Get next words for the seed "hello"
		Set<Map.Entry<String, Double>> nextWords = markovChain.getNextWords("world");

		// Ensure there's only one next word
		assertEquals(1, nextWords.size());

		// Extract the single entry from the set
		Map.Entry<String, Double> entry = nextWords.iterator().next();

		// Check if the next word is "world"
//		assertEquals("world", entry.getKey());
		assertEquals("good", entry.getKey());
	}

	// Long Sequence (0/5)
	@Test
	void testLongSequence() {
		MarkovChain chain = new MarkovChain("one");
		chain.add("a");
		chain.add("r");
		chain.add("a");
		chain.add("g");
		chain.add("a");
		chain.add("r");

		String seedWord = "a";
		String generatedText = seedWord;
		int wordsToGenerate = 5;

		// Generate text using the Markov chain
		for (int i = 0; i < wordsToGenerate; i++) {
			String nextWord = chain.getNext(seedWord);
			if (nextWord != null) {
				generatedText += " " + nextWord;
				seedWord = nextWord;
			} else {
				// No possible next word, reset to seed
				seedWord = "a";
			}
		}

		// Verify the generated text matches the expected output
		assertEquals("a r a r a", generatedText);
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

	// Select more options than (0/5)
	@Test
	void testMoreOptions() {
		MarkovChain markovChain = new MarkovChain("one");

		markovChain.add("hello");
		markovChain.add("world");
		markovChain.add("hello");
		markovChain.add("good");
		markovChain.add("hello");
		markovChain.add("morning");
		markovChain.add("neighbor");

		// Get next words for the seed "hello"
		Set<Map.Entry<String, Double>> nextWords = markovChain.getNextWords("hello");

		// Ensure that all expected possibilities are present
		assertTrue(nextWords.size() >= 2); // At least 2 possibilities expected
		assertTrue(nextWords.stream().anyMatch(entry -> entry.getKey().equals("world")));
		assertTrue(nextWords.stream().anyMatch(entry -> entry.getKey().equals("good")));
		assertTrue(nextWords.stream().anyMatch(entry -> entry.getKey().equals("morning")));
	}

	// Words frequency used when picking words randomly (0/5)
	@Test
	void testWordsFrequency() {
		MarkovChain chain = new MarkovChain("all");
		chain.add("hello");
        chain.add("world");

        // Get word frequencies from the Markov chain
        Set<Map.Entry<String, Double>> frequenciesMap = chain.getNextWords("hello");

        // Check if any frequency value is null or unexpected
        for (Map.Entry<String, Double> entry : frequenciesMap) {
            assertNotNull(entry.getValue(), "Frequency should not be null");
            assertNotEquals(5000, entry.getValue(), "Frequency should not be double the expected value");
        }
	}

	@Test
	void testLexigraphical() {
		MarkovChain chain = new MarkovChain("all");

        // Add some data to the MarkovChain
        chain.add("apple");
        chain.add("banana");
        chain.add("cherry");
        chain.add("banana");
        chain.add("apple");

        // Get the next word options for a seed
        Set<Map.Entry<String, Double>> nextWords = chain.getNextWords("banana");

        // Ensure there are next words
        assertTrue(nextWords != null && !nextWords.isEmpty(), "There should be next words.");

        // Verify lexicographical ordering
        String previousWord = null;
        boolean lexicographicallyOrdered = true;
        for (Map.Entry<String, Double> entry : nextWords) {
            if (previousWord != null && entry.getKey().compareTo(previousWord) < 0) {
                lexicographicallyOrdered = false;
                break;
            }
            previousWord = entry.getKey();
        }

        // Assert lexicographical ordering
        assertTrue(lexicographicallyOrdered, "Words should be ordered lexicographically.");
    
	}


}