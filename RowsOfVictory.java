import java.awt.*;
import java.util.*;
import javax.swing.*;

public class RowsOfVictory{
    int boardWidth = 600;
    int boardHeight = 700;
    int xScore = 0;
    int oScore = 0;
    String player1Name;
    String player2Name;

    JFrame frame = new JFrame("RowsOfVictory");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    JLabel scoringSystem = new JLabel();
    JButton[][] board = new JButton[3][3];
    JButton backButton = new JButton();

    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    boolean gameOver = false;
    boolean vsComputer = false;
    String difficulty = "Easy";

    Random rand = new Random();
    Map<String, LinkedList<JButton>> playerMoves = new HashMap<>() {{
        put(playerX, new LinkedList<>());
        put(playerO, new LinkedList<>());
    }};

    public static void main(String[] args) {
        new RowsOfVictory();
    }

    RowsOfVictory() {
        selectGameMode();
        setupFrame();
        setupTextLabel();
        setupBoard();
        setupControlPanel();

        frame.setVisible(true);
    }

    void selectGameMode() {
        String[] options = {"2 Players", "VS Computer"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose a game mode:", "Game Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        vsComputer = (choice == 1);
    
        if (vsComputer) {
            player1Name = JOptionPane.showInputDialog(frame, "Enter your name: ");
            if (player1Name == null || player1Name.trim().isEmpty()) player1Name = "Player X";
            player2Name = "AI";
    
            String[] difficulties = {"Easy", "Medium", "Hard"};
            difficulty = (String) JOptionPane.showInputDialog(frame, "Select AI Difficulty:", "Difficulty",
                    JOptionPane.QUESTION_MESSAGE, null, difficulties, difficulties[0]);
        } else {
            player1Name = JOptionPane.showInputDialog(frame, "Enter Player 1 name (X): ");
            if (player1Name == null || player1Name.trim().isEmpty()) player1Name = "Player X";
    
            player2Name = JOptionPane.showInputDialog(frame, "Enter Player 2 name (O): ");
            if (player2Name == null || player2Name.trim().isEmpty()) player2Name = "Player O";
        }
    }
    

    void setupFrame() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    void setupTextLabel() {
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(player1Name + "'s Turn");
        textLabel.setOpaque(true);
    
        scoringSystem.setBackground(Color.darkGray);
        scoringSystem.setForeground(Color.white);
        scoringSystem.setFont(new Font("Arial", Font.BOLD, 30));
        scoringSystem.setHorizontalAlignment(JLabel.CENTER);
        scoringSystem.setText("<html><font color='green'>" + player1Name + ": " + xScore + "</font>" + " | " +
                "<font color='red'>" + player2Name + ": " + oScore + "</font></html>");
        scoringSystem.setOpaque(true);
    
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        textPanel.add(scoringSystem, BorderLayout.SOUTH);
    
        frame.add(textPanel, BorderLayout.NORTH);
    }
    

    void setupBoard() {
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);
        setupBackButton();
        frame.setVisible(true);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                tile.setBackground(Color.darkGray);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.addActionListener(e -> playerMove(tile));
            }
        }
    }

    void setupBackButton() {
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setFocusPainted(false);
    
        String formattedMessage = "<html><div style='text-align: center; font-size: 20px; font-weight: bold; color: blue; font-family: Georgia, serif'>"
                              + "Do you want to go back and select game mode again?" + "</div></html>";
        backButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame, formattedMessage,
                    "Switch Game Mode", JOptionPane.YES_NO_OPTION);
    
            if (choice == JOptionPane.YES_OPTION) {
                frame.dispose();
                new RowsOfVictory();
            }
        });
        controlPanel.add(backButton);

    }

    void setupControlPanel() {
        controlPanel.setLayout(new FlowLayout());
        frame.add(controlPanel, BorderLayout.SOUTH);
    }
    
    void playerMove(JButton tileClicked) {
        if (gameOver || !tileClicked.getText().isEmpty()) return;
    
        handleMove(tileClicked, currentPlayer);
        if (checkWinner()) return;
    
        currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
        textLabel.setText((currentPlayer.equals(playerX) ? player1Name : player2Name) + "'s Turn");
    
        if (vsComputer && currentPlayer.equals(playerO)) {
            SwingUtilities.invokeLater(this::computerMove);
        }
    }

    void computerMove() {
        if (gameOver) return;

        JButton move = switch (difficulty) {
            case "Easy" -> getRandomMove();
            case "Medium" -> getMediumMove();
            case "Hard" -> getBestMove();
            default -> getRandomMove();
        };

        if (move != null) {
            handleMove(move, playerO);
            if (checkWinner()) return;
            currentPlayer = playerX;
            textLabel.setText(player1Name + "'s turn");
        }
    }

    void handleMove(JButton tile, String player) {
        LinkedList<JButton> moves = playerMoves.get(player);

        tile.setText(player);
        tile.setForeground(player.equals(playerX) ? new Color(57, 255, 20) : new Color(255, 20, 20));
        tile.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        moves.add(tile);

        if (moves.size() > 3) {
            JButton oldestMove = moves.removeFirst();
            oldestMove.setText("");
            oldestMove.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        }
    }

    JButton getRandomMove() {
        ArrayList<JButton> availableMoves = new ArrayList<>();
        for (JButton[] row : board) for (JButton tile : row) if (tile.getText().isEmpty()) availableMoves.add(tile);
        return availableMoves.isEmpty() ? null : availableMoves.get(rand.nextInt(availableMoves.size()));
    }

    JButton getMediumMove() {
        JButton move = findWinningMove(playerO);
        if (move != null) return move;

        move = findWinningMove(playerX);
        if (move != null) return move;

        return getRandomMove();
    }

    JButton findWinningMove(String player) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().isEmpty()) {
                    board[r][c].setText(player);
                    if (checkImmediateWin(player)) {
                        board[r][c].setText("");
                        return board[r][c];
                    }
                    board[r][c].setText("");
                }
            }
        }
        return null;
    }

    JButton getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        JButton bestMove = null;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().isEmpty()) {
                    board[r][c].setText(playerO);
                    int score = minimax(0, false);
                    board[r][c].setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = board[r][c];
                    }
                }
            }
        }
        return bestMove;
    }

    int minimax(int depth, boolean isMaximizing) {
        if (checkImmediateWin(playerO)) return 10 - depth;
        if (checkImmediateWin(playerX)) return depth - 10;
        if (isBoardFull()) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        String currentSymbol = isMaximizing ? playerO : playerX;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().isEmpty()) {
                    board[r][c].setText(currentSymbol);
                    int score = minimax(depth + 1, !isMaximizing);
                    board[r][c].setText("");
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    String getCurrentPlayerName(){
        return currentPlayer.equals(playerX) ? player1Name : player2Name;
    }

    boolean checkWinner() {
        if (checkLines()) {
            textLabel.setText(getCurrentPlayerName() + " wins!");
            gameOver = true;

            if (currentPlayer.equals(playerX)) {
                xScore++;
            } else {
                oScore++;
            }
            restartGameDialog("Play again?");
            return true;
        }
        return false;
    }

    boolean checkLines() {
        for (int i = 0; i < 3; i++) {
            if (checkTriple(board[i][0], board[i][1], board[i][2]) || checkTriple(board[0][i], board[1][i], board[2][i])) return true;
        }
        return checkTriple(board[0][0], board[1][1], board[2][2]) || checkTriple(board[0][2], board[1][1], board[2][0]);
    }

    boolean checkTriple(JButton a, JButton b, JButton c) {
        boolean match = !a.getText().isEmpty() && a.getText().equals(b.getText()) && b.getText().equals(c.getText());
        if (match) {
            a.setBackground(Color.gray);
            b.setBackground(Color.gray);
            c.setBackground(Color.gray);
        }
        return match;
    }

    boolean checkImmediateWin(String player) {
        return (checkLineForPlayer(player, 0, 0, 0, 1, 0, 2) || checkLineForPlayer(player, 1, 0, 1, 1, 1, 2) ||
                checkLineForPlayer(player, 2, 0, 2, 1, 2, 2) || checkLineForPlayer(player, 0, 0, 1, 0, 2, 0) ||
                checkLineForPlayer(player, 0, 1, 1, 1, 2, 1) || checkLineForPlayer(player, 0, 2, 1, 2, 2, 2) ||
                checkLineForPlayer(player, 0, 0, 1, 1, 2, 2) || checkLineForPlayer(player, 0, 2, 1, 1, 2, 0));
    }

    boolean checkLineForPlayer(String player, int r1, int c1, int r2, int c2, int r3, int c3) {
        return player.equals(board[r1][c1].getText()) && player.equals(board[r2][c2].getText()) && player.equals(board[r3][c3].getText());
    }

    boolean isBoardFull() {
        for (JButton[] row : board) for (JButton tile : row) if (tile.getText().isEmpty()) return false;
        return true;
    }

    void restartGameDialog(String message) {
        String formattedMessage = "<html><div style='text-align: center; font-size: 20px; font-weight: bold; color: blue; font-family: Georgia, serif'>"
                              + message + "</div></html>";
        int choice = JOptionPane.showConfirmDialog(frame, formattedMessage, "Game Over", JOptionPane.YES_NO_OPTION);
    
        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            frame.dispose();
        }
    }
    
    void restartGame() {
        for (JButton[] row : board) {
            for (JButton tile : row) {
                tile.setText("");
                tile.setBackground(Color.darkGray);
                tile.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
        }
        playerMoves.get(playerX).clear();
        playerMoves.get(playerO).clear();
        currentPlayer = playerX;
        textLabel.setText(player1Name + "'s turn");
        gameOver = false;

        updateScoringSystem();

    }
    void updateScoringSystem() {
        scoringSystem.setText("<html><font color='green'>" + player1Name + ": " + xScore + "</font>" + " | " + "<font color='red'>" + player2Name + ": " + oScore + "</font>" + "</html>");
    }
    
}