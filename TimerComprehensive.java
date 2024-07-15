package comprehensive;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class TimerComprehensive extends TimerTemplate {
String path;
String seed;
int k;
private TextGeneratorProcess process;
/**
 
Create a timer*
@param problemSizes array of N's to use
@param timesToLoop  number of times to repeat the tests*/
public TimerComprehensive(int[] problemSizes, int timesToLoop) {
super(problemSizes, timesToLoop);

}


@Override
protected void setup(int n) {
    String txt = generateRandomText(n);
    saveTextToFile(txt, "src/main/java/comprehensive/timer.txt");
    this.process = new TextGeneratorProcess("src/main/java/comprehensive/timer.txt", "This", 5, "all");
}

@Override
protected void timingIteration(int n) {
    process.run();
}

@Override
protected void compensationIteration(int n) {

}

// Method to generate random text with a specified number of words
public static String generateRandomText(int numberOfWords) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();

    // Array of words to choose from
    String[] words = {"Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit",
            "sed", "do", "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua"};

    for (int i = 0; i < numberOfWords; i++) {
        // Randomly select a word from the array
        String randomWord = words[random.nextInt(words.length)];
        sb.append(randomWord).append(" ");
    }

    return sb.toString();
}

public static void saveTextToFile(String text, String filename) {
    try (FileWriter writer = new FileWriter(filename)) {
        writer.write(text);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}