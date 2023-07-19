import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {
    private JFrame frame;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JLabel textfield;
    private JButton[] buttons;
    private JButton restartButton;

    private boolean player1Turn;
    private int player1Score;
    private int player2Score;

    private ScoreTracker scoreTracker;

    private int[][] winCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
    };

    public TicTacToe(ScoreTracker scoreTracker) {
        this.scoreTracker = scoreTracker;
        frame = new JFrame();
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        textfield = new JLabel();
        buttons = new JButton[9];
        restartButton = new JButton("Restart");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Times new Roman", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        buttons[0] = new JButton();
        buttons[1] = new JButton();
        buttons[2] = new JButton();
        buttons[3] = new JButton();
        buttons[4] = new JButton();
        buttons[5] = new JButton();
        buttons[6] = new JButton();
        buttons[7] = new JButton();
        buttons[8] = new JButton();

        for (int i = 0; i < 9; i++) {
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("Calibre", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        restartButton.setFont(new Font("Calibre", Font.BOLD, 40));
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        titlePanel.add(textfield);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(restartButton, BorderLayout.SOUTH);

        resetGame();
        int[] scores = scoreTracker.loadScore();
        player1Score = scores[0];
        player2Score = scores[1];
        updateScoreboard();
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1Turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1Turn = false;
                        textfield.setText("O's turn");
                        check();
                    }
                } else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        player1Turn = true;
                        textfield.setText("X's turn");
                        check();
                    }
                }
            }
        }
    }

    public void check() {
        if (checkWin()) {
            if (player1Turn) {
                textfield.setText("Player 2 wins");
                player2Score++;
            } else {
                textfield.setText("Player 1 wins");
                player1Score++;
            }
            highlightWinningLine();
            scoreTracker.saveScore(player1Score, player2Score);
            updateScoreboard();
            disableButtons();
        } else if (checkTie()) {
            textfield.setText("It's a tie");
            disableButtons();
        }
    }

    public boolean checkWin() {
        for (int[] combination : winCombinations) {
            if (!buttons[combination[0]].getText().equals("") &&
                    buttons[combination[0]].getText().equals(buttons[combination[1]].getText()) &&
                    buttons[combination[1]].getText().equals(buttons[combination[2]].getText())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTie() {
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty())
                return false;
        }
        return true;
    }

    public void highlightWinningLine() {
        for (int[] combination : winCombinations) {
            JButton button1 = buttons[combination[0]];
            JButton button2 = buttons[combination[1]];
            JButton button3 = buttons[combination[2]];

            if (!button1.getText().equals("") &&
                    button1.getText().equals(button2.getText()) &&
                    button2.getText().equals(button3.getText())) {
                button1.setBackground(Color.YELLOW);
                button2.setBackground(Color.YELLOW);
                button3.setBackground(Color.YELLOW);
                return; // Exit the method once a winning line is found
            }
        }
    }




    public void updateScoreboard() {
        textfield.setText("Player 1: " + player1Score + "  Player 2: " + player2Score);
    }

    public void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(new Color(238, 238, 238));
            buttons[i].setForeground(new Color(0, 0, 0));
        }
        player1Turn = true;
        textfield.setText("X's turn");
        clearWinningLine();

        int option = JOptionPane.showConfirmDialog(frame, "Do you want to start a new game?", "New Game", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            player1Score = 0;
            player2Score = 0;
            scoreTracker.saveScore(player1Score, player2Score);
        }

        updateScoreboard();
    }


    public void disableButtons() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
    }

    public void clearWinningLine() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setBackground(new Color(238, 238, 238));
        }
    }

    public void playGame() {
        int option = JOptionPane.showConfirmDialog(frame, "Do you want to play a new game?", "Play Again", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            JOptionPane.showMessageDialog(frame, "Final Scores:\nPlayer 1: " + player1Score + "\nPlayer 2: " + player2Score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }





}
