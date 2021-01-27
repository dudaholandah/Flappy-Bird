package flappybird_grupo11;

public class Pipe extends Element {
    
    public static int HOLE_SIZE = 120;
    public static int MAX_HEIGHT = 270;
    
    // velocidade que se move na tela
    private double velocity; 
    // posicao do par de canos (tamanho)
    private Box boxTop;
    private Box boxBottom;

    public Pipe(double x, double y, double vx) {
        this.x = x;
        this.y = y;
        this.width = 52;
        this.height = 270;
        this.velocity = vx;

        boxTop = new Box(x, y - this.height, x + this.width, y);
        boxBottom = new Box(x, y + Pipe.HOLE_SIZE, x + this.width, y + Pipe.HOLE_SIZE + this.height);
    }
    
    public void move(double dt){
        x += velocity * dt;
    }

    @Override
    public void update(double dt) {
        this.move(dt);
        boxTop.move(velocity * dt, 0);
        boxBottom.move(velocity * dt, 0);
    }

    @Override
    public void draw(Screen s) {
        s.drawImage(604, 0, this.width, this.height, 0, x, y - this.height); //cano de cima
        s.drawImage(660, 0, this.width, this.height, 0, x, y + Pipe.HOLE_SIZE); //cano de baixo
    }

    public Box getBoxTop() {
        return boxTop;
    }

    public Box getBoxBottom() {
        return boxBottom;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }  
        
}
