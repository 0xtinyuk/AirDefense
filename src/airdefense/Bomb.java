package airdefense;


public class Bomb extends FlyingObject {
	public Bomb(int sx,int sy,int sxSpeed, int sySpeed) {
		this.xSpeed = sxSpeed;
		this.ySpeed = 2+sySpeed;
		this.x = sx;
		this.y = sy;
		this.image = GameBoard.bombImage;
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
