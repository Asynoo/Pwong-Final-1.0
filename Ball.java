import greenfoot.*;


/**
* A Ball is a thing that bounces of walls and paddles (or at least i should).
*
* @author Daddy
* @version 1
*/
public class Ball extends SmoothMover
{
    private static final int BALL_SIZE = 25;
    private static final int BOUNCE_DEVIANCE_MAX = 5;
    private static final int STARTING_ANGLE_WIDTH = 90;
    private static final int DELAY_TIME = 100;
    
    private int difficultyCounter;
    private int speed;
    private int delay;
    
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private boolean goingUp;
    
    GreenfootSound hitSound;
    GreenfootSound scoreSound;
    GreenfootSound wallSound;
    
    /**
     * Contructs the ball and sets it in motion!
     */
    public Ball()
    {
        hitSound = new GreenfootSound("hit.mp3");
        scoreSound = new GreenfootSound("score.mp3");
        wallSound = new GreenfootSound("wall.mp3");
        init(false);
    }

    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (delay > 0)
        {
            delay--;
        }
        else
        {
            move(speed);
            checkBounceOffWalls();
            checkBounceOffCeiling();
            checkFloor();
            checkBounceOffBottomPaddle();
            checkBounceOffTopPaddle();
            checkInsidePaddle();
            checkIncreaseDifficulty();
            
        }
    }    
    
    
    /**
     * Returns true if the ball is touching one of the side walls.
     */
    private boolean isTouchingSides()
    {
        return (getX() <= BALL_SIZE/2 || getX() >= getWorld().getWidth() - BALL_SIZE/2);
        
    }
    
    /**
     * Returns true if the ball is touching the ceiling.
     */
    private boolean isTouchingCeiling()
    {
        return (getY() <= BALL_SIZE/2);
    }
    
    /**
     * Returns true if the ball is touching the paddle from the top or bottom.
     */
    private boolean isTouchingPaddle()
    {
   
        if(!goingUp && getOneObjectAtOffset(0,BALL_SIZE/2,Paddle.class) != null){
            return true;
        }
        if(goingUp && getOneObjectAtOffset(0,-BALL_SIZE/2,PaddlePC.class) != null){
            return true;
        }
        return false;
        
    }
    
    /**
     * Returns true if the ball is inside the paddles (by arriving at an odd angle).
     */
    private boolean isInsidePaddle()
    {
   
        if(!goingUp && getOneObjectAtOffset(0,0,Paddle.class) != null){
            return true;
        }
        if(goingUp && getOneObjectAtOffset(0,0,PaddlePC.class) != null){
            return true;
        }
        return false;
    }
 
    /**
     * Returns true if the ball is touching the floor.
     */
    private boolean isTouchingFloor()
    {
        return (getY() >= getWorld().getHeight() - BALL_SIZE/2);
    }
 
    /**
     * Check to see if the ball should bounce off one of the walls.
     * If touching one of the walls, the ball is bouncing off.
     */
    private void checkBounceOffWalls()
    {
        if (isTouchingSides())
        {
            if (! hasBouncedHorizontally)
            {
                revertHorizontally();
                wallSound.play();
            }
        }
        else
        {
            hasBouncedHorizontally = false;
        }
    }
 
    /**
     * Check to see if the ball should bounce off the ceiling.
     * If touching the ceiling the ball is bouncing off.
     */
    private void checkBounceOffCeiling()
    {
        if (isTouchingCeiling())
        {
            init(false);
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            ((PingWorld)getWorld()).addScore(1);
            ((PingWorld)getWorld()).setLevel(0);
            difficultyCounter = 0;
            scoreSound.play();
        }
    }
    
    /**
     * Check to see if the ball should teleport out of the paddle.
     * If inside a paddle the ball is teleporting out.
     */
    private void checkInsidePaddle()
    {
        if (isInsidePaddle())
        {
            if(getY() > getWorld().getHeight()/2){
                setLocation(getX(),getY() - BALL_SIZE);
            }
            if(getY() < getWorld().getHeight()/2){
                setLocation(getX(),getY() + BALL_SIZE);
            }
        }
    }
   
    /**
     * Check to see if the ball should bounce off the bottom paddle.
     * If touching the bottom paddle the ball is bouncing off.
     */
    private void checkBounceOffBottomPaddle()
    {
        if (isTouchingPaddle())
        {
            if (!goingUp)
            {
                revertBottomPaddle();
                hitSound.play();
                goingUp = true;
                difficultyCounter++;
            }
        }
 
    }
   
      /**
     * Check to see if the ball should bounce off the bottom paddle.
     * If touching the bottom paddle the ball is bouncing off.
     */
    private void checkBounceOffTopPaddle()
    {
        if (isTouchingPaddle())
        {
            if (goingUp)
            {
                revertVertically();
                hitSound.play();
                goingUp = false;
                difficultyCounter++;
            }
        }
 
    }
   
    /**
     * Check to see if the ball should be restarted.
     * If touching the floor the ball is restarted in initial position and speed.
     */
    private void checkFloor()
    {
        if (isTouchingFloor())
        {
            init(true);
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            ((PingWorld)getWorld()).addEnemyScore(1);
            ((PingWorld)getWorld()).setLevel(0);
            difficultyCounter = 0;
            scoreSound.play();
        }
    }
 
    /**
     * Bounces the ball back from a vertical surface.
     */
    private void revertHorizontally()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        setRotation((180 - getRotation()+ randomness + 360) % 360);
        hasBouncedHorizontally = true;
    }
   
    private void revertBottomPaddle()
    {
        int bounce = (getX() - getOneObjectAtOffset(0,BALL_SIZE/2,Paddle.class).getX());
        setRotation(360 - getRotation());
        if(getRotation() + bounce <= 200){
            setRotation(201);
        }
        else if(getRotation() + bounce >= 340){
            setRotation(339);
        }
        else{
            setRotation(getRotation() + bounce);
        }
        goingUp = true;
        difficultyCounter++;
        if(difficultyCounter == 10){
            speed++;
            difficultyCounter = 0;
            ((PingWorld)getWorld()).addLevel(1); }
    }
    
    private void revertTopPaddle()
    {
        int bounce = (getX() - getOneObjectAtOffset(0,BALL_SIZE/2,Paddle.class).getX());
        setRotation(360 - getRotation());
        if(getRotation() + bounce <= 20){
            setRotation(21);
        }
        else if(getRotation() + bounce >= 160){
            setRotation(159);
        }
        else{
            setRotation(getRotation() + bounce);
        }
        goingUp = true;
    }
   
 
    /**
     * Bounces the ball back from a horizontal surface.
     */
    private void revertVertically()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        setRotation((360 - getRotation()+ randomness + 360) % 360);
        hasBouncedVertically = true;
        goingUp = true;
    }
 
    /**
     * Initialize the ball settings.
     */
    private void init(boolean sentUp)
    {
        speed = 2;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        goingUp = sentUp;
        setRotation(Greenfoot.getRandomNumber(STARTING_ANGLE_WIDTH)+STARTING_ANGLE_WIDTH/2);
        if(sentUp){
            setRotation(getRotation() + 180);
        }
    }
   
    private void checkIncreaseDifficulty(){
        if(difficultyCounter >= 10){
            speed++;
            difficultyCounter = 0;
            ((PingWorld)getWorld()).addLevel(1);
        }
    }
    
    public boolean getGoingUp()
    {
        return goingUp;
    }
    
}

