import java.awt.*;
import javax.swing.*;

public class GameLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameLauncher::showStartPopup);
    }

    private static void showStartPopup() {
        JFrame popupFrame = new JFrame("Rows of Victory");
        popupFrame.setSize(800, 500);
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setResizable(false);
        popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        popupFrame.setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImage = new ImageIcon("ROV.jpg");
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        popupFrame.setContentPane(backgroundPanel);

        JButton playButton = new JButton("PLAY");
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        playButton.setBackground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.setPreferredSize(new Dimension(200, 50));
        playButton.addActionListener(e -> {
            popupFrame.dispose();
            new RowsOfVictory();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(300, 0, 0, 0);
        backgroundPanel.add(playButton, gbc);

        popupFrame.setVisible(true);
    }
}
