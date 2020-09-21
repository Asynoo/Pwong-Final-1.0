


import greenfoot.*;


/**
 * The Ping World is where Balls and Paddles meet to play pong.
 * 
 * @author The teachers 
 * @version 1
 */
public class PingWorld extends World
{
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;
    private int score;
    private int level;
    private int enemyScore;
    
    private GreenfootSound gameMusic;
    
    private GreenfootImage textAreaTop;
    private GreenfootImage background;
    
    private Ball ball;
    private PaddlePC paddlepc;
    
    /**
     * Constructor for objects of class PingWorld.
     */
    public PingWorld(boolean gameStarted,boolean multiplayer)
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        if (gameStarted)
        {
            background = getBackground();
            background.setColor(Color.BLACK);
            ball = new Ball();
            addObject(ball, WORLD_WIDTH/2, WORLD_HEIGHT/2);
            addObject(new Paddle(100,20), 60, WORLD_HEIGHT - 50);
            background.setFont(new Font(30));
            gameMusic = new GreenfootSound("Game Music.mp3");
            gameMusic.playLoop();
            
            if(multiplayer){
                paddlepc = new PaddlePC(100,20,true);
            }
            else{
                paddlepc = new PaddlePC(100,20,false);
            }
            addObject(paddlepc, 80, 50);
        }
        else
        {
            Greenfoot.setWorld(new IntroWorld());
        }
    }
    
    public void act(){
        showTextTop("  P1 Score: " + score + "                   Level: " +  level + "                 P2 Score: " + enemyScore);
        checkGameOver();
    }
    
    public void showTextTop(String text)
    {
        removeTextImageTop(); // so this method can replace any previous text image shown
        GreenfootImage textImage = new GreenfootImage(text, 25, null, null);
        textAreaTop = new GreenfootImage(textImage); // textImage used only to size the area
        textAreaTop.drawImage(background, 0, 0); // replaces image with affected background area (to be retained)
        getBackground().drawImage(textImage, 0, 0); // draws text image on background image
    }
 
    // method to remove text image (replace affected background)
    public void removeTextImageTop()
    {
        if (textAreaTop != null) background.drawImage(textAreaTop, 0, 0);
    }
    
    public void addScore(int score){
        this.score+=score;
    }
    
    public void setLevel(int level)
    {
        this.level=level;
    }
    
    public void addLevel(int level){
        this.level+=level;
    }
    
    private void checkGameOver(){
        if(score >= 5){
            gameMusic.stop();
            Greenfoot.setWorld(new GameOver("Player 1"));
        }
        if(enemyScore >= 5){
            gameMusic.stop();
            Greenfoot.setWorld(new GameOver("Player 2"));
        }
    }
    
    public void addEnemyScore(int enemyScore){
        this.enemyScore+=enemyScore;
    }
    
    public Ball getBall(){
        return ball;
    }
    
}
