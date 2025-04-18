package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenuPanel extends JPanel {
    private JFrame frame;

    public MainMenuPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 193, 140));

        setPreferredSize(new Dimension(1100, 800));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Nút "Chơi game"
        JButton playButton = new JButton("Chơi game");
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        playButton.addActionListener(this::onPlayGame);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playButton, gbc);

        // Nút "Hướng dẫn"
        JButton guideButton = new JButton("Hướng dẫn");
        guideButton.setFont(new Font("Arial", Font.BOLD, 20));
        guideButton.addActionListener(this::onGuide);
        gbc.gridy = 1;
        add(guideButton, gbc);

        // Nút "Thoát"
        JButton exitButton = new JButton("Thoát");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 2;
        add(exitButton, gbc);
    }

    private void onPlayGame(ActionEvent e) {
        frame.getContentPane().removeAll(); // Xóa nội dung hiện tại
        GamePanel gamePanel = new GamePanel(); // Tạo GamePanel mới
        frame.add(gamePanel); // Thêm GamePanel vào JFrame
        frame.revalidate(); // Cập nhật giao diện
        frame.repaint(); // Vẽ lại giao diện
        gamePanel.launchGame(); // Khởi chạy GamePanel
    }

    private void onGuide(ActionEvent e) {
        String guide = """
        Trò chơi có thể chơi từ 2 đến 4 người. Mỗi người chơi có chuồng ngựa xuất phát riêng và có 4 con ngựa trong chuồng đó. 
        Chuồng ngựa xuất phát có màu đỏ, xanh lá, vàng và xanh dương, nằm ở 4 góc của bàn cờ. 
        Mục tiêu của mỗi người chơi là đưa ngựa của mình ra khỏi chuồng xuất phát và đua về chuồng đích an toàn. 
        Mỗi chuồng đích có 6 vị trí và được đánh số từ 1 đến 6. Bốn con ngựa đầu tiên đến chuồng đích sẽ giành các vị trí 6, 5, 4 và 3 để chiến thắng.

        - Bạn cần ra số 1 hoặc 6 để ngựa bắt đầu xuất phát.
        - Nếu lắc được số 1 hoặc 6, bạn sẽ được lắc thêm lượt nữa.
        - Một ô chỉ có thể chứa 1 con ngựa cùng lúc.
        - Bạn không thể đi qua con ngựa khác dù là của bạn hay đối thủ. 
        - Bạn có thể đá ngựa đối thủ về chuồng nếu bạn lắc đúng số cần để đến vị trí đó.
        - Không có điểm an toàn cho ngựa, vì vậy ngựa có thể bị đá về chuồng bất cứ lúc nào.
        """;

        JOptionPane.showMessageDialog(this, guide, "Hướng dẫn", JOptionPane.INFORMATION_MESSAGE);
    }
}