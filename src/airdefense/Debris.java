package airdefense;

public class Debris extends FlyingObject {
	public Debris(int sx,int sy) {
		this.xSpeed = 0;
		this.ySpeed = 3;
		this.x = sx;
		this.y = sy;
		this.image = GameBoard.debrisImage;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	@Override
	public boolean outOfBounds() {
		return (this.y >= GameBoard.HEIGHT) || (this.x + this.width < 0) || (this.x >= GameBoard.WIDTH);
	}
	
	@Override
	public void nextStep() {
		this.x += this.xSpeed;
		this.y += this.ySpeed;
	}
}
