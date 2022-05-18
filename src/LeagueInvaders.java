import javax.swing.*;

public class LeagueInvaders {

    JFrame frame;
    GamePanel gamePanel;

    public static final int WIDTH = 500;
    public static final int HEIGHT = 800;

    public static void main(String[] args) {

        LeagueInvaders invaders = new LeagueInvaders();
        invaders.setup();
    }

    LeagueInvaders() {

        frame = new JFrame("League Invaders");
        gamePanel = new GamePanel();
    }

    void setup() {

        frame.add(gamePanel);
        frame.addKeyListener(gamePanel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}