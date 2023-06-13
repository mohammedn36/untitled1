import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainMenuPanel extends JPanel {
    private JButton playButton;
    private PlayButtonListener playButtonListener;

    MainMenuPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        setBackground(Color.BLACK);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);
        add(titlePanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Snake");
        titleLabel.setFont(new Font("Ink Free", Font.BOLD, 75));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(Color.BLACK);
        titlePanel.add(subtitlePanel, BorderLayout.SOUTH);

        JLabel subtitleLabel = new JLabel("...but cool...");
        subtitleLabel.setFont(new Font("Ink Free", Font.PLAIN, 20));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitlePanel.add(subtitleLabel, BorderLayout.CENTER);

        playButton = new JButton("Play");
        playButton.setFont(new Font("Ink Free", Font.BOLD, 40));
        playButton.setFocusPainted(false);
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.WHITE);
        playButton.addActionListener(e -> {
            if (playButtonListener != null) {
                playButtonListener.onPlayButtonClicked();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(playButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setPlayButtonListener(PlayButtonListener listener) {
        this.playButtonListener = listener;
    }

    interface PlayButtonListener {
        void onPlayButtonClicked();
    }
}
