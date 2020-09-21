import greenfoot.*;


/**
 * Write a description of class IntroWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroWorld extends World
{
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;
    
    private GreenfootSound introMusic;

    /**
     * Constructor for objects of class IntroWorld.
     */
    public IntroWorld()
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        introMusic = new GreenfootSound("intro.mp3");
        introMusic.play();
    }
    
    public void act()
    {
        String key = Greenfoot.getKey();
        if (key != null && key.equals("enter"))
        {
            Greenfoot.setWorld(new PingWorld(true,false)); 
            introMusic.stop();
        }
        if (key != null && key.equals("tab"))
        {
            Greenfoot.setWorld(new PingWorld(true,true)); 
            introMusic.stop();
        }
    }
    
}
