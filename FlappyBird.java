package br.grupo11.flappybird;

import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements Game {

    private double ground_offset = 0;
    private double background_offset = 0;
    private double groundVelocity = 70;

    private Bird bird;
    private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    private Score score;

    private Random generator = new Random();
    
    private double timePipes = 0;
    private double limitPipes = 2.5;
    
    private boolean isGameOver = false;
    private boolean scoreCounted = false;
       

    public FlappyBird() {
        this.bird = new Bird(35, (Game.WIDTH - 112) / 2 + 24 / 2);
        this.score = new Score(0, 5, 5);

        this.addPipes();
    }

    private void addPipes() {
        //pipes.add(new Pipe(Game.WIDTH + 10, generator.nextInt(Game.HEIGHT - 112 - Pipe.HOLE_SIZE), -groundVelocity));
        pipes.add(new Pipe(Game.WIDTH + 10, generator.nextInt(Pipe.MAX_HEIGHT - 20), -groundVelocity));
    }

    @Override
    public void onKeyPressed(String key) {
        if (key.equals(" ")) {
            bird.jump();
        }
    }

    @Override
    public void step(double dt) {
        
        // Incrementa background
        this.background_offset += dt * 25;
        this.background_offset = this.background_offset % 288;

        // Incrementa ground
        this.ground_offset += dt * groundVelocity;
        this.ground_offset = this.ground_offset % 308;

        this.stepPipes(dt); // atualiza velocidade dos canos
        
        bird.update(dt); // atualiza passaro
        
        // checa colisao do passaro com os limites da tela
        if (bird.y + 24 >= Game.HEIGHT - 112) {
            this.gameOver();
        } else if (bird.y <= 0) {
            this.gameOver();
        }

        // checa colisao do passaro com os canos
        for (Pipe cano : pipes) {
            cano.update(dt);
            if (bird.box.collision(cano.getBoxTop()) || bird.box.collision(cano.getBoxBottom())) {
                this.gameOver();
            }
        }
        
        // remove cano da lista ao sair da tela
        if (pipes.size() > 0 && pipes.get(0).x < -60) {          
            pipes.remove(0);
            this.scoreCounted = false;
        }
        
        if (!this.scoreCounted && pipes.size() > 0 && pipes.get(0).x < bird.x - 35){
            score.update(dt);
            this.scoreCounted = true;
        }
    }  

    public void stepPipes(double dt) {
        this.timePipes += dt;
        if (this.timePipes > this.limitPipes) {
            this.addPipes();
            this.timePipes -= this.limitPipes;
        }
    }

    @Override
    public void draw(Screen s) {
        // desenha background
        s.drawImage(0, 0, 288, 512, 0, -background_offset, 0);
        s.drawImage(0, 0, 288, 512, 0, 288 - background_offset, 0);
        s.drawImage(0, 0, 288, 512, 0, 288 * 2 - background_offset, 0);

        // desenha pipes
        for (Pipe pipe : pipes) {
            pipe.draw(s);
        }

        // desenha ground
        s.drawImage(292, 0, 308, 112, 0, -ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 - ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 * 2 - ground_offset, Game.HEIGHT - 112);

        // desenha bird
        bird.draw(s);
        
        // desenha score
        score.draw(s);
        
        // checa fim de jogo
        if (this.isGameOver){
            if (score.getScore() > Score.record){
                Score.record = score.getScore();
            }
            s.drawImage(292, 398, 188, 38, 0, Game.WIDTH/2 - 188/2, 100);
            s.drawImage(292, 116, 226, 116, 0, Game.WIDTH/2 - 226/2, Game.HEIGHT/2 - 116/2);
            score.drawScore(s, Game.WIDTH/2 + 50, Game.HEIGHT/2 - 25);
            score.drawRecord(s, Game.WIDTH/2 + 55, Game.HEIGHT/2 + 16);
        }

    }

    public void gameOver() {
        this.isGameOver = true;
    }
    
    @Override
    public boolean isGameOver(){
        return this.isGameOver;
    }

    private static void start() {
        Animation a = new Animation(new FlappyBird());
        a.turnOn();
    }

    public static void main(String[] args) {
        start();
    }

}
