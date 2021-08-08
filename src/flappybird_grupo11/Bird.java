package flappybird_grupo11;

public class Bird extends Element {

    public double velocity = 0; // velocidade do passaro

    public static double GRAVITY = 1000; // gravidade
    public static double FLAP = -300; // velocidade pulo

    public Box box; // posicao do passaro (tamanho)

    // variaveis para dar sensacao de movimento de bater asa
    private double timeBird = 0;
    private double limitBird = 120;

    public Bird(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 34;
        this.height = 24;

        this.box = new Box(x, y, x + this.width, y + this.height);
    }

    @Override
    public void update(double dt) {
        this.fall(dt);
        box.move(0, velocity * dt);

        timeBird++;
        if (timeBird > limitBird) {
            timeBird = 0;
        }

    }

    public void jump() {
        velocity = FLAP;
    }

    public void fall(double dt) {
        velocity += GRAVITY * dt;
        y += velocity * dt;
    }

    @Override
    public void draw(Screen s) {
        if (timeBird >= 0 && timeBird <= limitBird / 3) {
            s.drawImage(528, 128, this.width, this.height, Math.atan(velocity / 150), x, y); 
        } else if (timeBird <= limitBird * 2 / 3) {
            s.drawImage(528, 180, this.width, this.height, Math.atan(velocity / 150), x, y);
        } else if (timeBird <= limitBird) {
            s.drawImage(446, 248, this.width, this.height, Math.atan(velocity / 150), x, y);
        }
        // arcotangente traz sensacao de movimento ao cair ou subir
    }

}
