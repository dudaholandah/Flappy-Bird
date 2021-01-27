package flappybird_grupo11;

public class Box {

    private double x0, y0, x1, y1;

    public Box(double x0, double y0, double x1, double y1) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
    }

    // atualiza onde esta o elemento a medida q ele se move
    public void move(double dx, double dy) {
        this.x0 += dx;
        this.x1 += dx;
        this.y0 += dy;
        this.y1 += dy;
    }

    public boolean collision(Box box) {
        // Falso se nao tiver colisao
        if (this.x1 < box.x0 || this.x0 > box.x1) {
            return false;
        }
        else if (this.y1 < box.y0 || this.y0 > box.y1) {
            return false;
        }
        // Verdadeiro se tiver colisao
        return true;
    }

}
