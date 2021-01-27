package flappybird_grupo11;

import java.awt.Canvas;

public interface Game {

    public static int WIDTH = 384;
    public static int HEIGHT = 512;
    public static String TITLE = "Flappy Bird";
    
    boolean isGameOver();

    void step(double dt);
    
    void draw(Screen tela);

    void onKeyPressed(String tecla);
    
    void onMouseClicked(double x, double y);
    
    void onMouseMoved(Canvas canvas, double x, double y);
}
