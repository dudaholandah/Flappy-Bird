package br.grupo11.flappybird;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Screen {

    Graphics2D g;
    public ImageIcon img = new ImageIcon("flappy3.png");

    public Screen(Graphics2D g) {
        this.g = g;
    }

    public void drawImage(int xa, int ya, int larg, int alt, double dir, double x, double y) {       
        AffineTransform trans = g.getTransform();
        g.rotate(dir, x + larg / 2, y + alt / 2);        
        g.drawImage(img.getImage(), (int) Math.round(x), (int) Math.round(y), (int) Math.round(x) + larg,
                (int) Math.round(y) + alt, xa, ya, xa + larg, ya + alt, null);
        g.setTransform(trans);
    }
}
