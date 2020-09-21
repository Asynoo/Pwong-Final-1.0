import greenfoot.*;

/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 * 
 * @author The teachers 
 * @version 1
 */
public class PaddlePC extends Actor
{
    private int width;
    private int height;
    private int speed;
    private boolean controlled;

    public PaddlePC(int width, int height, boolean controlled)
    {
        this.width = width;
        this.height = height;
        this.controlled = controlled;
        speed = 1;
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(controlled){
            moveLeft();
            moveRight();
        }
        else{
            if(((PingWorld)getWorld()).getBall().getGoingUp()){
                followBall(((PingWorld)getWorld()).getBall());
            }
            else{
                center();
            }
        }
    }

    /**
     * Follows the ball
     */
    private void followBall(Ball ball)
    {
        int deviance = ball.getX() - getX();

        if(deviance > 0){
            if(getX() + width/2 < getWorld().getWidth()){
                move(speed);
            }
        }
        if(deviance < 0){
            if(getX() - width/2 > 0){
                move(-speed);
            }
        }
    }
    
    /**
     * moves the paddle to the center of the screen
     */
    private void center()
    {
        int deviance = getWorld().getWidth()/2 - getX();
        
        if(deviance > 0){
            if(getX() + width/2 < getWorld().getWidth()){
                move(speed);
            }
        }
        if(deviance < 0){
            if(getX() - width/2 > 0){
                move(-speed);
            }
        }
    }
    
    private void moveLeft()
    {
        if (Greenfoot.isKeyDown("a")){
            if(getX()-width/2 > 0){
                move(-2);
            }
        }
    }
 
    private void moveRight()
    {
        if (Greenfoot.isKeyDown("d")){
            if(getX() + width/2 < getWorld().getWidth()){
                move(2);
            }
        }
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }
}