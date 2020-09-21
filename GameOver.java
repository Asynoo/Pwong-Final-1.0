import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOver extends World
{
    
    private GreenfootSound endSound;
    
    /**
     * Constructor for objects of class GameOver.
     * 
     */
    public GameOver(String winner)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(500, 700, 1); 
        GreenfootImage background = getBackground();
        background.setColor(new Color (251,53,53));
        
        background.setFont(new Font("Bauhaus 93",70));
        background.drawString(winner + " wins!",32,getHeight()/2);
        
        endSound = new GreenfootSound("gameOver.mp3");
        endSound.play();
    }
    
    public void act(){
        String key = Greenfoot.getKey();
        if (key != null && key.equals("enter"))
        {
            Greenfoot.setWorld(new IntroWorld());        
        }
    }
}
