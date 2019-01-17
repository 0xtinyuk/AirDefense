package airdefense;
import java.util.Random;

public class Plane extends FlyingObject {
	public int timeMark;
	@Override
	public boolean outOfBounds() {
		return (this.y >= GameBoard.HEIGHT + 5) || (this.x + this.width < -5) 
				|| (this.x >= GameBoard.WIDTH + 5) || (this.y + this.height < -5);
	}
	
	@Override
	public void nextStep() {
		this.x += this.xSpeed;
		this.y += this.ySpeed;
		++timeMark;
	}
	
	public Plane() {
		this.image = GameBoard.planeImage;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		Random rand = new Random();
		this.xSpeed = (rand.nextInt(10) > 4)?(1):(-1);
		this.ySpeed = rand.nextInt(3) - 1;
		this.x = (this.xSpeed > 0)?(-this.width/2):(GameBoard.WIDTH -1);
		this.y = rand.nextInt(150);
		timeMark = 0;
	}
	
	public Bomb dropBomb() {
		return new Bomb(this.x, this.y, this.xSpeed, this.ySpeed);
	}
}
