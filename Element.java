package br.grupo11.flappybird;

public abstract class Element {

    protected double x;
    protected double y;

    protected int width;
    protected int height;

    public abstract void update(double dt);

    public abstract void draw(Screen s);

}
