package flappybird_grupo11;

public class Score extends Element {

    private int score;
    public String stringScore;
    private boolean Record = false;
    private int pos;
    // posicao no sprite dos numeros maiores
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
    // posicao no sprite dos numeros menores
    public static int[][] numberDataSmall ={
        {574, 148},
        {578, 324},
        {408, 490},
        {424, 490},
        {440, 490},
        {456, 490},
        {568, 394},
        {584, 394},
        {568, 426},
        {584, 426}
    };

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isRecord() {
        return Record;
    }

    public void setRecord(boolean Record) {
        this.Record = Record;
    }
    
    public Score(int n, double x , double y) {
        this.x = x;
        this.y = y;
        this.width = 14;
        this.height = 20;
        this.score = n;
        
        setStringScore();
    }

    // transforma o score em string para checar mais facil
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

    public void drawRecord(Screen s, int x, int y, int record) {
        String srecord = String.valueOf(record);
        for (int i = 0; i < srecord.length(); i++) {
            drawNumber(s, Integer.parseInt(srecord.substring(i, i + 1)), x + 15 * i, y);
        }
    }

    public void drawNumber(Screen s, int number, int x, int y) {
        s.drawImage(numberData[number][0], numberData[number][1], 14, 20, 0, x, y);
    } 
    
    public void drawNumberSmall(Screen s, int number, int x, int y) {
        s.drawImage(numberDataSmall[number][0], numberDataSmall[number][1], 12, 14, 0, x, y);
    }

    @Override
    public void draw(Screen s) {
        this.drawScore(s, (int)this.x, (int)this.y);
    }
}


    
    
    

