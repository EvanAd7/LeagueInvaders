import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static BufferedImage image;
    public static boolean needImage = true;
    public static boolean gotImage = false;

    final int MENU = 0;
    final int GAME = 1;
    final int END = 2;
    int currentState = MENU;

    Font titleFont;
    Font textFont;
    Font scoreFont;

    Timer frameDraw;
    Timer alienSpawn;

    Rocketship rocketship;

    ObjectManager objManager;

    @Override
    public void paintComponent(Graphics g){

        if (currentState == MENU) {
            drawMenuState(g);
        } else if (currentState == GAME) {
            drawGameState(g);
        } else if (currentState == END) {
            drawEndState(g);
        }
    }

    GamePanel() {

        titleFont = new Font("Arial", Font.PLAIN, 48);
        textFont = new Font("Arial", Font.PLAIN, 30);
        scoreFont = new Font("Arial", Font.PLAIN, 25);

        frameDraw = new Timer(1000/60, this);
        frameDraw.start();

        rocketship = new Rocketship(250, 700, 50, 50);

        objManager = new ObjectManager(rocketship);

        if (needImage) {
            loadImage ("space.png");
        }
    }


    void updateMenuState() { }

    void updateGameState() {

        rocketship.updateRocket();
        objManager.update();

        if (rocketship.isActive == false) {
            currentState = END;
            alienSpawn.stop();
        }
    }

    void updateEndState() {	}


    void drawMenuState(Graphics g) {

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);

        g.setFont(titleFont);
        g.setColor(Color.YELLOW);
        g.drawString("LEAGUE INVADERS", 17, 100);

        g.setFont(textFont);
        g.drawString("Press ENTER to start", 90, 300);
        g.drawString("Press SPACE for instructions", 40, 400);
    }

    void drawGameState(Graphics g) {

        g.drawImage(image, 0, 0, null);

        g.setFont(scoreFont);
        g.setColor(Color.CYAN);
        g.drawString("Score: "+objManager.getScore(), 10, 25);

        objManager.draw(g);
    }

    void drawEndState(Graphics g) {

        g.setColor(Color.RED);
        g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);

        g.setFont(titleFont);
        g.setColor(Color.YELLOW);
        g.drawString("GAME OVER", 90, 100);

        g.setFont(textFont);
        g.drawString("You killed "+objManager.getScore()+" enemies", 100, 300);
        g.drawString("Press ENTER to restart", 80, 400);
    }


    void startGame( ) {

        alienSpawn = new Timer(1000, objManager);
        alienSpawn.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (currentState == MENU) {
            updateMenuState();
        } else if(currentState == GAME) {
            updateGameState();
        } else if(currentState == END) {
            updateEndState();
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (currentState == END) {
                currentState = MENU;
                rocketship = new Rocketship(250, 700, 50, 50);
                objManager = new ObjectManager(rocketship);
            } else if (currentState == MENU) {
                currentState = GAME;
                startGame();
            } else if (currentState == GAME) {
                currentState = END;
                alienSpawn.stop();
            } else {
                currentState++;
            }
        }

        if (currentState == MENU) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                JOptionPane.showMessageDialog(null, "Use the arrow keys to move, press space to fire a projectile.\n"
                        + "Your goal is to kill as many aliens as possible, but avoid touching an alien and dying.");
            }
        }

        if (currentState == GAME) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                rocketship.up();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                rocketship.down();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                rocketship.left();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rocketship.right();
            }
        }

        if (currentState == GAME) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                objManager.addProjectile(rocketship.getProjectile());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        rocketship.speedX = 0;
        rocketship.speedY = 0;
    }

    void loadImage(String imageFile) {
        if (needImage) {
            try {
                image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
                gotImage = true;
            } catch (Exception e) {

            }
            needImage = false;
        }
    }
}