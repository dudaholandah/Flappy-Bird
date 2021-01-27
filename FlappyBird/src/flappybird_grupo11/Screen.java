package flappybird_grupo11;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Screen {

    Graphics2D g;
    public ImageIcon img = new ImageIcon("flappy.png");

    public Screen(Graphics2D g) {
        this.g = g;
    }

    // desenha a imagem com o arquivo sprite padrao (img)
    public void drawImage(int xa, int ya, int larg, int alt, double dir, double x, double y) {       
        AffineTransform trans = g.getTransform();
        g.rotate(dir, x + larg / 2, y + alt / 2);        
        g.drawImage(img.getImage(), (int) Math.round(x), (int) Math.round(y), (int) Math.round(x) + larg,
                (int) Math.round(y) + alt, xa, ya, xa + larg, ya + alt, null);
        g.setTransform(trans);
    }
    
    // desenha a imagem com um arquivo dado
    public void drawImage(String img, int xa, int ya, int larg, int alt, double dir, double x, double y) {
        ImageIcon image = new ImageIcon(img);
        AffineTransform trans = g.getTransform();
        g.rotate(dir, x + larg / 2, y + alt / 2);        
        g.drawImage(image.getImage(), (int) Math.round(x), (int) Math.round(y), (int) Math.round(x) + larg,
                (int) Math.round(y) + alt, xa, ya, xa + larg, ya + alt, null);
        g.setTransform(trans);
    }
}
