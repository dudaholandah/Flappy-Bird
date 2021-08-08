package flappybird_grupo11;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class FlappyBird implements Game {

    // variaveis para sensacao de movimento do jogo
    private double ground_offset = 0;
    private double background_offset = 0;
    private double groundVelocity = 70;
    private double backgroundVelocity = 25;

    private static Animation animation = new Animation(new FlappyBird());

    private Bird bird;
    private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    private Score score;
    
    private Button buttonStart;
    private Button buttonScore;
    private Button buttonMenu;
    
    private static int vet[] = new int[3];
    private static String dat[] = new String[3];

    private Random generator = new Random();

    // controla quantos canos aparecem por frame
    private double timePipes = 0;
    private double limitPipes = 2.8;

    private boolean isGameOver = false;
    private boolean scoreCounted = false;

    // variaveis para contar tempos especificos
    private double timeMenu = 0;
    private double timeReady = 0;
    private double timeBird = 0;
    private double limitBird = 120;
    
    // aumenta a velocidade do cenario
    private int multVelocity = 0;

    // estados do jogo
    public enum STATE {
        MENU, READY, START, RECORDS;
    }
    
    private STATE stateGame = STATE.MENU; 

    // variaveis para trabalhar com os botoes
    private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorDefault = new Cursor(Cursor.DEFAULT_CURSOR);

    public FlappyBird() {
        this.bird = new Bird(35, (Game.WIDTH - 112) / 2 + 24 / 2);
        this.score = new Score(0, 5, 5);
        this.buttonStart = new Button(((Game.WIDTH - (90 * 2)) / 2), Game.HEIGHT - 80, 1);
        this.buttonScore = new Button(((Game.WIDTH - (90 * 2)) / 2) + 98, Game.HEIGHT - 80, 2);
        this.buttonMenu = new Button(((Game.WIDTH - (90 * 2)) / 2) + 49, Game.HEIGHT - 80, 3);
    }

    // inicializa os valores das variaveis ao reiniciar o jogo
    public void init() {
        this.bird = new Bird(35, (Game.WIDTH - 112) / 2 + 24 / 2);
        this.score = new Score(0, 5, 5);
        this.timePipes = 0;
        this.timeReady = 0;
        this.pipes.clear();
        this.isGameOver = false;
        this.scoreCounted = false;
        
        this.limitPipes = 2.8;
        this.backgroundVelocity = 25;
        this.groundVelocity = 70;
        
        this.addPipes();
        
    }

    // adiciona um par de canos em altura aleatoria
    private void addPipes() {
        int randomY = generator.nextInt(Pipe.MAX_HEIGHT - 20);
        pipes.add(new Pipe(Game.WIDTH + 10, randomY < 30 ? 30 : randomY, -groundVelocity));

    }
    
    // se apertar barra de espaço, o passaro da um salto
    @Override
    public void onKeyPressed(String key) {
        if (key.equals(" ")) {
            bird.jump();
            Animation.playSound("sfx_wing.wav");
        }
    }
    
    // muda o cursor do mouse ao passar em cima de um botao
    @Override
    public void onMouseMoved(Canvas canvas, double x, double y) {
        if (buttonStart.isVisible && buttonStart.pointOnButton(x, y)) {
            canvas.setCursor(cursorHand);
        } else if (buttonScore.isVisible && buttonScore.pointOnButton(x, y)) {
            canvas.setCursor(cursorHand);
        } else if (buttonMenu.isVisible && buttonMenu.pointOnButton(x, y)) {
            canvas.setCursor(cursorHand);
        } else {
            canvas.setCursor(cursorDefault);
        }
    }

    // ao clicar em um botao muda o estado do jogo
    @Override
    public void onMouseClicked(double x, double y) {
        if (buttonStart.isVisible && buttonStart.pointOnButton(x, y)) {
            Animation.playSound("sfx_swooshing.wav");
            this.stateGame = STATE.READY;
            this.init();
        } else if (buttonScore.isVisible && buttonScore.pointOnButton(x, y)) {
            Animation.playSound("sfx_swooshing.wav");
            this.stateGame = STATE.RECORDS;
        } else if (buttonMenu.isVisible && buttonMenu.pointOnButton(x, y)) {
            Animation.playSound("sfx_swooshing.wav");
            this.stateGame = STATE.MENU;
        } 
    }

    public void stepGame(double dt) {
        
        int aux = multVelocity;
        multVelocity = this.score.getScore()/5;
        
        // muda a velocidade do cenario a cada 5 pontos feitos
        if (aux != multVelocity && this.score.getScore() <= 40){
            this.limitPipes = 2.8 - 0.2*multVelocity;
            this.backgroundVelocity = 25 + 5*multVelocity;
            this.groundVelocity = 70 + 10*multVelocity;          
        }                      
        
        this.stepPipes(dt); // adiciona canos

        bird.update(dt); // atualiza passaro

        // checa colisao do passaro com os limites da tela
        if (bird.y + 24 >= Game.HEIGHT - 112) {
            this.gameOver();
        } else if (bird.y <= 0) {
            this.gameOver();
        }

        // checa colisao do passaro com os canos
        for (Pipe pipe : pipes) {
            pipe.setVelocity(-groundVelocity); // atualiza a velocidade dos canos
            pipe.update(dt); // move o cano
            if (bird.box.collision(pipe.getBoxTop()) || bird.box.collision(pipe.getBoxBottom())) {               
                this.gameOver();                             
            }
        }

        // remove cano da lista ao sair da tela
        if (pipes.size() > 0 && pipes.get(0).x < -60) {
            pipes.remove(0);
            this.scoreCounted = false;
                      
        }

        // atualiza o score ao passar por um cano sem colidir
        if (!this.scoreCounted && pipes.size() > 0 && pipes.get(0).x < bird.x - 35) {
            score.attScore();
            Animation.playSound("sfx_point.wav");
            this.scoreCounted = true;           
        }
    }

    // faz o passaro se mover na tela de menu
    private void stepMenu(double dt) {
        this.timeMenu += dt;
        timeBird++;
        if (timeBird > limitBird) {
            timeBird = 0;
        }
    }
    
    // determina o tempo da tela de ready
    private void stepReady(double dt) {
        this.timeReady += dt;
        if (this.timeReady > 2) {
            this.stateGame = STATE.START;
        }
        timeBird++;
        if (timeBird > limitBird) {
            timeBird = 0;
        }
    }
    
    // atualiza o jogo a cada frame/step
    @Override
    public void step(double dt) {        
        
        // Incrementa background
        this.background_offset += dt * backgroundVelocity;
        this.background_offset = this.background_offset % 288;

        // Incrementa ground
        this.ground_offset += dt * groundVelocity;
        this.ground_offset = this.ground_offset % 308;

        // checa e determina os proximos estados de jogo
        if (this.stateGame == STATE.START && !this.isGameOver) {
            this.stepGame(dt);
        }

        if (this.stateGame == STATE.MENU) {
            this.stepMenu(dt);
        }

        if (this.stateGame == STATE.READY) {
            this.stepReady(dt);
        }

        if (this.stateGame == STATE.MENU) {
            buttonStart.isVisible = true;
            buttonScore.isVisible = true;
            buttonMenu.isVisible = false;
        } else if (this.stateGame == STATE.RECORDS || (this.stateGame == STATE.START && this.isGameOver())) {
            buttonStart.isVisible = false;
            buttonScore.isVisible = false;
            buttonMenu.isVisible = true;
        } else {
            buttonStart.isVisible = false;
            buttonScore.isVisible = false;
            buttonMenu.isVisible = false;
        }
    }

    // atualiza os canos na tela
    public void stepPipes(double dt) {
        this.timePipes += dt;
        
        if (this.timePipes > this.limitPipes) {
            this.addPipes();
            this.timePipes -= this.limitPipes;
        }
    }

    public void drawMenu(Screen s) {
        // desenha pipes
        s.drawImage(604, 130, 52, 140, 0, 120, 0); //cano de cima
        s.drawImage(660, 0, 52, 250, 0, 120, 240); //cano de baixo
        s.drawImage(604, 180, 52, 90, 0, 270, 0); //cano de cima
        s.drawImage(660, 0, 52, 250, 0, 270, 200); //cano de baixo

        // desenha ground
        s.drawImage(292, 0, 308, 112, 0, -ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 - ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 * 2 - ground_offset, Game.HEIGHT - 112);

        // desenha bird se mexendo     
        if (timeBird >= 0 && timeBird <= limitBird / 3) {
            s.drawImage(528, 128, 34, 24, 0, 35, (Game.WIDTH - 112) / 2 + 24 / 2);
        } else if (timeBird <= limitBird * 2/3) {
            s.drawImage(528, 180, 34, 24, 0, 35, (Game.WIDTH - 112) / 2 + 24 / 2);
        } else if (timeBird <= limitBird) {
            s.drawImage(446, 248, 34, 24, 0, 35, (Game.WIDTH - 112) / 2 + 24 / 2);
        }

        // desenha título
        s.drawImage(288, 346, 200, 44, 0, (Game.WIDTH - 200) / 2, 12);

        // desenha botao de Start e Score
        buttonStart.draw(s);
        buttonScore.draw(s);

    }

    public void drawReady(Screen s) {
        // desenha Get Ready
        s.drawImage(290, 441, 176, 46, 0, (Game.WIDTH - 200) / 2, 60);
        
        // desenha bird se mexendo       
        if (timeBird >= 0 && timeBird <= limitBird / 3) {
            s.drawImage(528, 128, 34, 24, 0, 35, (Game.WIDTH - 112) / 2 + 24 / 2);
        } else if (timeBird <= limitBird * 2/3) {
            s.drawImage(528, 180, 34, 24, 0, 35, (Game.WIDTH - 112) / 2 + 24 / 2);
        } else if (timeBird <= limitBird) {
            s.drawImage(446, 248, 34, 24, 0, 35, (Game.WIDTH - 112) / 2 + 24 / 2);
        }
    }

    public void drawRecords(Screen s) {
        // desenha background
        s.drawImage(0, 0, 288, 512, 0, -background_offset, 0);
        s.drawImage(0, 0, 288, 512, 0, 288 - background_offset, 0);
        s.drawImage(0, 0, 288, 512, 0, 288 * 2 - background_offset, 0);

        // desenha ground
        s.drawImage(292, 0, 308, 112, 0, -ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 - ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 * 2 - ground_offset, Game.HEIGHT - 112);

        // desenha quadros
        s.drawImage("quadroScore.png", 0, 0, 242, 123, 0, 71, 10);
        s.drawImage("quadroScore.png", 0, 0, 242, 123, 0, 71, 133);
        s.drawImage("quadroScore.png", 0, 0, 242, 123, 0, 71, 256);

        //desenha medalhas
        s.drawImage(470, (Game.HEIGHT - 54), 58, 54, 0, 86, 58);
        s.drawImage(528, (Game.HEIGHT - 54), 52, 54, 0, 96, 181);
        s.drawImage(604, 272, 58, 50, 0, 102, 300);

        // desenha botao Start
        buttonMenu.draw(s);
        
        //desenha recordes
        drawRecordes(s);
        drawDates(s);
    }
    
    public void drawRecordes(Screen s){
        String str;
        int x=256, y=90; // y=88
        
        for(int i=0; i<3; i++){
            str = Integer.toString(vet[i]);
            for(int j=0; j<str.length(); j++){
                switch(str.charAt(j)){
                    case '0': score.drawNumber(s, 0, x, y); break;
                    case '1': score.drawNumber(s, 1, x, y); break;
                    case '2': score.drawNumber(s, 2, x, y); break;
                    case '3': score.drawNumber(s, 3, x, y); break;
                    case '4': score.drawNumber(s, 4, x, y); break;
                    case '5': score.drawNumber(s, 5, x, y); break;
                    case '6': score.drawNumber(s, 6, x, y); break;
                    case '7': score.drawNumber(s, 7, x, y); break;
                    case '8': score.drawNumber(s, 8, x, y); break;
                    case '9': score.drawNumber(s, 9, x, y); break;
                    default: break;
                }
                x+=16;
            }
            y+=124;
            //x=250;
            x=256;
        }
    }
    
    public void drawDates(Screen s){
        String str;
        int x=150, y=46;
        
        for(int i=0; i<3; i++){
            str = dat[i];
            for(int j=0; j<str.length(); j++){
                switch(str.charAt(j)){
                    case '0': score.drawNumberSmall(s, 0, x, y); break;
                    case '1': score.drawNumberSmall(s, 1, x, y); break;
                    case '2': score.drawNumberSmall(s, 2, x, y); break;
                    case '3': score.drawNumberSmall(s, 3, x, y); break;
                    case '4': score.drawNumberSmall(s, 4, x, y); break;
                    case '5': score.drawNumberSmall(s, 5, x, y); break;
                    case '6': score.drawNumberSmall(s, 6, x, y); break;
                    case '7': score.drawNumberSmall(s, 7, x, y); break;
                    case '8': score.drawNumberSmall(s, 8, x, y); break;
                    case '9': score.drawNumberSmall(s, 9, x, y); break;
                    case '/': s.drawImage(614, 394, 5, 15, 0, x, y); x-=5; break;
                    default: break;
                }                
                x+=15;
            }
            y+=124;
            x=150;
        }
    }
    
    public void drawGame(Screen s) {            
        // desenha pipes
        for (Pipe pipe : pipes) {
            pipe.draw(s);
        }

        // desenha bird
        bird.draw(s);

        // desenha score
        score.draw(s);
    }

    // desenha principal
    @Override
    public void draw(Screen s) {
        String str = "";
        int month;
        
        // desenha background
        s.drawImage(0, 0, 288, 512, 0, -background_offset, 0);
        s.drawImage(0, 0, 288, 512, 0, 288 - background_offset, 0);
        s.drawImage(0, 0, 288, 512, 0, 288 * 2 - background_offset, 0);

        if (this.stateGame == STATE.START && !this.isGameOver) {
            this.drawGame(s);
        }

        // desenha ground
        s.drawImage(292, 0, 308, 112, 0, -ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 - ground_offset, Game.HEIGHT - 112);
        s.drawImage(292, 0, 308, 112, 0, 308 * 2 - ground_offset, Game.HEIGHT - 112);

        if (this.stateGame == STATE.MENU) {
            this.drawMenu(s);
        }

        if (this.stateGame == STATE.RECORDS) {
            this.drawRecords(s);
        }

        if (this.stateGame == STATE.READY) {
            this.drawReady(s);
        }

        // checa fim de jogo
        if (this.stateGame == STATE.START && this.isGameOver) {
            this.backgroundVelocity = 25;
            this.groundVelocity = 70;
            if (score.getScore() > vet[0]) {
                vet[0] = score.getScore();
                Calendar today = Calendar.getInstance();
                str += today.get(Calendar.DAY_OF_MONTH);
                str += "/";
                month = today.get(Calendar.MONTH);
                month++;
                str += Integer.toString(month);
                str += "/";
                str += today.get(Calendar.YEAR);
                dat[0] = str;
                score.setPos(1);
                score.setRecord(true);
            }
            else{
                if(!score.isRecord() && score.getScore() > vet[1]){
                    vet[1] = score.getScore();
                    Calendar today = Calendar.getInstance();
                    str += today.get(Calendar.DAY_OF_MONTH);
                    str += "/";
                    month = today.get(Calendar.MONTH);
                    month++;
                    str += Integer.toString(month);
                    str += "/";
                    str += today.get(Calendar.YEAR);
                    dat[1] = str;
                    score.setPos(2);
                    score.setRecord(true);
                }
                else{
                    if(!score.isRecord() && score.getScore() > vet[2]){
                        vet[2] = score.getScore();
                        Calendar today = Calendar.getInstance();
                        str += today.get(Calendar.DAY_OF_MONTH);
                        str += "/";
                        month = today.get(Calendar.MONTH);
                        month++;
                        str += Integer.toString(month);
                        str += "/";
                        str += today.get(Calendar.YEAR);
                        dat[2] = str;
                        score.setPos(3);
                    }
                }
            }
            
            s.drawImage(292, 398, 188, 38, 0, Game.WIDTH / 2 - 188 / 2, 100);
            s.drawImage(292, 116, 226, 116, 0, Game.WIDTH / 2 - 226 / 2, Game.HEIGHT / 2 - 116 / 2);
            score.drawScore(s, Game.WIDTH / 2 + 55, Game.HEIGHT / 2 - 25);
            score.drawRecord(s, Game.WIDTH / 2 + 55, Game.HEIGHT / 2 + 16, vet[0]);
            buttonMenu.draw(s);
            
            switch(score.getPos()){
                case 1: s.drawImage(470, (Game.HEIGHT-54), 58, 54, 0, 90, 242); break;
                case 2: s.drawImage(528, (Game.HEIGHT-54), 52, 54, 0, 100, 242); break;
                case 3: s.drawImage(604, 272, 58, 50, 0, 104, 238); break;
                default: break;
            }
            
            Files.setRecordes(vet);
            Files.setDates(dat);
            Files.write("records.txt");
            Files.writeDate("dates.txt");
        }

    }

    // fim do jogo
    public void gameOver() {
        Animation.playSound("sfx_hit.wav");
        Animation.playSound("sfx_die.wav");
        this.isGameOver = true;
    }

    @Override
    public boolean isGameOver() {
        return this.isGameOver;
    }

    // inicia jogo
    private static void start() {
        Files.read("records.txt");
        Files.readDate("dates.txt");
        
        vet = Files.getRecordes();
        dat = Files.getDates();
        
        animation.turnOn();
    }

    public static void main(String[] args) {
        start();
    }

}
