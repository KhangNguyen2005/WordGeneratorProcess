package comprehensive;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextGeneratorTest {
	TextGenerator main;

	@BeforeEach
	void setUp() throws Exception {
		TextGenerator main = new TextGenerator();
		this.main = main;
	}

	@Test
	void testMain_NullPointerException() {
		String[] args = { "helloWorld.txt", "a", "5" };

		// Ensure NullPointerException is thrown when calling main with null arguments
		assertThrows(NullPointerException.class, () -> {
			TextGenerator.main(args);
		});

	}

	@Test
	void testMain_ArrayIndexOutOfBoundsException() {
		String[] args = { "helloWorld.txt", "a", "5", "3" };

		assertTrue( args[3].equals("5"));
	}
}
