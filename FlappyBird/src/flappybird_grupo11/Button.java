package flappybird_grupo11;

public class Button extends Element{
    
    private int button;
    public boolean isVisible = false; // funcionando ou nao

    public Button(double x, double y, int button){
        this.x = x;
        this.y = y;
        this.width = 80;
        this.height = 28;
        this.button = button;
    }
    
    @Override
    public void draw(Screen s){
        switch (this.button) {
            case 1: s.drawImage(483, 425, this.width+1, this.height-1, 0, x, y); break; // botao start
            case 2: s.drawImage(488, 346, this.width, this.height-2, 0, x, y); break; // botao score
            case 3: s.drawImage(492, 236, this.width, this.height-2, 0, x, y); break; // botao menu
        }
        this.isVisible = true;
    }
    
    // checa se o botao foi pressionado ou nao
    public boolean pointOnButton(double x, double y){
        return (x) > this.x && x < (this.x + this.width) &&
               (y) > this.y && y < (this.y + this.height);
    }        
}
