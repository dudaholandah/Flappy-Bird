package br.grupo11.flappybird;

public interface Game {

    public static int WIDTH = 384;
    public static int HEIGHT = 512;
    public static String TITLE = "Flappy Bird";
    
    boolean isGameOver();

    void step(double dt);

    void onKeyPressed(String tecla);

    void draw(Screen tela);
}
