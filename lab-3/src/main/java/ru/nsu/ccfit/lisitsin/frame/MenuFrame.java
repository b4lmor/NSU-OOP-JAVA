package ru.nsu.ccfit.lisitsin.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.repository.LeaderBoardRepository;
import ru.nsu.ccfit.lisitsin.utils.PrettifyUtils;

import javax.swing.*;
import java.awt.*;

@Component
public class MenuFrame extends JFrame {

    private static final int LEADERBOARD_LIMIT = 5;

    private final JTextField nicknameField;
    private final LeaderBoardRepository leaderBoardRepository;

    @Autowired
    public MenuFrame(
            SnakeFrame snakeFrame,
            LeaderBoardRepository leaderBoardRepository,
            @Value("${game.title}") String title
    ) {
        this.leaderBoardRepository = leaderBoardRepository;
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField(10);
        inputPanel.add(nicknameLabel);
        inputPanel.add(nicknameField);

        JPanel buttonPanel = getButtonPanel(snakeFrame);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel getButtonPanel(SnakeFrame snakeFrame) {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            String nickname = nicknameField.getText();
            if (!nickname.isBlank()) {
                dispose();
                snakeFrame.setVisible(true);
                snakeFrame.run(nickname, this);
            }
        });

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(e -> JOptionPane.showMessageDialog(
                MenuFrame.this,
                PrettifyUtils.getSortedLeaderBoardText(leaderBoardRepository.getAll(), LEADERBOARD_LIMIT)
        ));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(exitButton);
        return buttonPanel;
    }

}
