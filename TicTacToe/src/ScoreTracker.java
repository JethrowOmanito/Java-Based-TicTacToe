import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ScoreTracker {
    private static final String SCORES_FILE = "score.txt";

    public void saveScore(int player1Score, int player2Score) {
        Properties properties = loadProperties();
        properties.setProperty("player1Score", String.valueOf(player1Score));
        properties.setProperty("player2Score", String.valueOf(player2Score));
        try {
            FileOutputStream fos = new FileOutputStream(SCORES_FILE);
            properties.store(fos, null);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved scores: " + player1Score + ", " + player2Score); // Print scores for debugging
    }

    public int[] loadScore() {
        Properties properties = loadProperties();
        int[] scores = new int[2];
        scores[0] = Integer.parseInt(properties.getProperty("player1Score", "0"));
        scores[1] = Integer.parseInt(properties.getProperty("player2Score", "0"));
        System.out.println("Loaded scores: " + scores[0] + ", " + scores[1]); // Print scores for debugging
        return scores;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(SCORES_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            // If the file does not exist or cannot be read, return an empty properties object
        }
        return properties;
    }
}
