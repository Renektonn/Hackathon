package cc.openhome;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Hackathon extends JFrame {

    public Hackathon() {

        initUI();
    }

    private void initUI() {

        add(new Board());
        setTitle("Hackathon");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
	pack();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var game = new Hackathon();
            game.setVisible(true);
        });
    }
}
