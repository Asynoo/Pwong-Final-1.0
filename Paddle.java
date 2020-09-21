import greenfoot.*;
 
/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 *
 * @author The teachers
 * @version 1
 */
public class Paddle extends Actor
{
    private int width;
    private int height;
 
    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
 
    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        moveLeft();
        moveRight();
    }    
 
    private void moveLeft()
    {
        if (Greenfoot.isKeyDown("left")){
            if(getX()-width/2 > 0){
                move(-2);
            }
        }
    }
 
    private void moveRight()
    {
        if (Greenfoot.isKeyDown("right")){
            if(getX() + width/2 < getWorld().getWidth()){
                move(2);
            }
        }
    }
 
}