import java.awt.*;

public class GameObject {

    int x;
    int y;
    int width;
    int height;

    int speed = 0;
    int speedX = 0;
    int speedY = 0;

    boolean isActive = true;

    Rectangle collisionBox;

    GameObject(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        collisionBox = new Rectangle(x, y, width, height);
    }

    void update() {

        collisionBox.setBounds(x, y, width, height);
    }
}
