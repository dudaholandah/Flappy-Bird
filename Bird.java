package br.grupo11.flappybird;

public class Bird extends Element {

    public double velocity = 0; // velocidade do passaro

    public static double GRAVITY = 1000; // gravidade
    public static double FLAP = -300; // velocidade pulo

    public Box box; // "caixa" passaro (tamanho)

    public Bird(double x, double y){
        this.x = x;
        this.y = y;
        this.width = 34;
        this.height = 24;

        this.box = new Box(x, y, x + this.width, y + this.height);
    }

    // atualiza
    @Override
    public void update(double dt){
        this.fall(dt);		
        box.move(0, velocity*dt);
    }

    // pula
    public void jump(){
        velocity = FLAP;
    }

    // cai
    public void fall(double dt){
        velocity += GRAVITY*dt;
        y += velocity*dt;
    }

    // desenha
    @Override
    public void draw(Screen s){
        s.drawImage(528, 128, this.width, this.height, Math.atan(velocity/150), x, y);
    }	

}
