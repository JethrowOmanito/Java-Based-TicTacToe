import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        TicTacToe ticTacToe = new TicTacToe(scoreTracker);

        SwingUtilities.invokeLater(() -> ticTacToe.playGame());
    }
}
