package airdefense;

public class Bullet extends FlyingObject {
	@Override
	public boolean outOfBounds() {
		return (this.y + this.height < 0) ;
	}
	
	@Override
	public void nextStep() {
		this.x += this.xSpeed;
		this.y += this.ySpeed;
	}
	
	public Bullet(int sx, int sy) {
		this.x = sx;
		this.y = sy;
		this.xSpeed = 0;
		this.ySpeed = -4;
		this.image = GameBoard.bulletImage;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
}
