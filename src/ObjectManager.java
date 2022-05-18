import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObjectManager implements ActionListener {

    Rocketship rocket;
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    ArrayList<Alien> aliens = new ArrayList<Alien>();

    Random random = new Random();
    int score = 0;
    int nextSpeed = 30;

    ObjectManager(Rocketship rocket) {

        this.rocket = rocket;
    }

    void addProjectile(Projectile p) {

        projectiles.add(p);
    }

    void addAlien() {

        aliens.add(new Alien(random.nextInt(LeagueInvaders.WIDTH),0,50,50));
    }

    void update() {

        for (Alien alien : aliens) {
            alien.update();

            if (alien.y > LeagueInvaders.HEIGHT) {
                alien.isActive = false;
            }
        }

        for (Projectile projectile : projectiles) {
            projectile.update();

            if (projectile.y < 0) {
                projectile.isActive = false;
            }
        }

        checkCollision();
        purgeObjects();
    }

    void draw(Graphics g) {

        rocket.draw(g);

        for (Alien alien : aliens) {
            alien.draw(g);
        }

        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
    }

    void purgeObjects() {

        for (int i=0; i<aliens.size(); i++) {
            if (aliens.get(i).isActive == false) {
                aliens.remove(i);
            }
        }

        for (int i=0; i<projectiles.size(); i++) {
            if (projectiles.get(i).isActive == false) {
                projectiles.remove(i);
            }
        }

    }

    void checkCollision() {

        for (Alien alien : aliens) {
            for (Projectile projectile : projectiles) {
                if (projectile.collisionBox.intersects(alien.collisionBox)) {
                    projectile.isActive = false;
                    alien.isActive = false;
                    score++;
                }
            }

            if (rocket.collisionBox.intersects(alien.collisionBox)) {
                rocket.isActive = false;
                alien.isActive = false;
            }
        }
    }

    public int getScore() {

        return score;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        addAlien();
    }
}
