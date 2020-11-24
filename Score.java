package br.grupo11.flappybird;

public class Score extends Element {

    private int score;
    public String stringScore;
    public static int record = 0;
    public static int[][] numberData = {
        {576, 200},
        {578, 236},
        {578, 268},
        {578, 300},
        {574, 346},
        {574, 370},
        {330, 490},
        {350, 490},
        {370, 490},
        {390, 490}
    };

    public Score(int n, double x , double y) {
        this.x = x;
        this.y = y;
        this.width = 14;
        this.height = 20;
        this.score = n;
        
        setStringScore();
    }

    public void setStringScore() {
        stringScore = String.valueOf(score);
    }

    public void setScore(int n) {
        score = n;
        setStringScore();
    }

    public int getScore() {
        return score;
    }

    public void attScore() {
        score ++;
        setStringScore();
    }

    public void drawScore(Screen s, int x, int y) {
        for (int i = 0; i < stringScore.length(); i++) {
            drawNumber(s, Integer.parseInt(stringScore.substring(i, i + 1)), x + 15 * i, y);
        }
    }

    public void drawRecord(Screen s, int x, int y) {
        String srecord = String.valueOf(Score.record);
        for (int i = 0; i < srecord.length(); i++) {
            drawNumber(s, Integer.parseInt(srecord.substring(i, i + 1)), x + 15 * i, y);
        }
    }

    public void drawNumber(Screen s, int number, int x, int y) {
        s.drawImage(numberData[number][0], numberData[number][1], 14, 20, 0, x, y);
    }    
    
    @Override
    public void update(double dt) {
        this.attScore();
    }

    @Override
    public void draw(Screen s) {
        this.drawScore(s, (int)this.x, (int)this.y);
    }
}


    
    
    

