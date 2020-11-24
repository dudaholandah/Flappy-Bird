package br.grupo11.flappybird;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Animation {

    public Game game;
    public ImageIcon img = new ImageIcon("flappyIcon.png");

    private boolean running = true;
    private long t0 = 0;
    private long t1 = 0;
    private double dt = 0;

    public BufferStrategy strategy;   

    public Animation(Game g) {
        this.game = g;
        
        // Interface Grafica
        Canvas canvas = new Canvas();
        JFrame container = new JFrame(Game.TITLE);
        
        container.setIconImage(img.getImage());

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        panel.setLayout(null);        
        
        container.setResizable(false);
        canvas.setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
        panel.add(canvas);
        canvas.setIgnoreRepaint(true);

        container.pack();
        container.setVisible(true);
        
        // Fecha janela
        container.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // Tratamento do teclado
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent evt) {                
                game.onKeyPressed(keyString(evt));
            }

            @Override
            public void keyReleased(KeyEvent evt) {               
            }

            @Override
            public void keyTyped(KeyEvent evt) {                
            }
        });

       
        canvas.createBufferStrategy(2);
        strategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        
        container.pack();
        container.setLocationRelativeTo(null);

    }

    // pega a tecla
    private static String keyString(KeyEvent evt) {
        if (evt.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
            return String.valueOf(evt.getKeyChar()).toLowerCase();
        }
        return "";
    }

    // Loop principal da animacao na tela
    private void mainLoop() {

        while (this.running) {
            if (!this.running) {
                return;
            }

            this.t1 = System.currentTimeMillis();

            this.dt = (this.t1 - this.t0) * 0.001; // tempo em segundos para 1 frame
            this.t0 = this.t1;

            game.step(this.dt); // passo/frame do jogo

            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

            game.draw(new Screen(g)); // desenha situacao atual
            strategy.show();
            
            if (this.game.isGameOver()){
                this.turnOff();
            }
        }
    }

    // inicia animacao
    public void turnOn() {
        this.t0 = System.currentTimeMillis();
        this.running = true;
        this.mainLoop();
    }

    // para animacao
    public void turnOff() {
        this.running = false;
    }

}
