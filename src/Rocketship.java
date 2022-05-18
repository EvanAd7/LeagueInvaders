import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Rocketship extends GameObject {

    public static BufferedImage image;
    public static boolean needImage = false;
    public static boolean gotImage = false;

    Rocketship(int x, int y, int width, int height) {

        super(x, y, width, height);

        speedX = 10;
        speedY = 10;

        if (needImage) {
            loadImage("rocket.png");
        }
    }

    void draw(Graphics g) {

        if (gotImage) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }
    }

    public void up() {
        speedY = -10;
    }

    public void down() {
        speedY = 10;
    }

    public void left() {
        speedX = -10;
    }

    public void right() {
        speedX = 10;
    }

    public void updateRocket() {

        if ((x + speedX) >= 0 && (x + width + speedX) < LeagueInvaders.WIDTH) {
            x += speedX;
        }

        if ((y + speedY) >= 0 && (y + height + speedY) < LeagueInvaders.HEIGHT) {
            y += speedY;
        }

        super.update();
    }

    public Projectile getProjectile() {

        return new Projectile(x + width / 2, y, 10, 10);
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
