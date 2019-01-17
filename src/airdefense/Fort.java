package airdefense;

public class Fort extends FlyingObject{
	int lives;
	
	@Override
	public boolean outOfBounds() {
		return false;
	}
	
	@Override
	public void nextStep() {
		return;
	}
	
	public void moveTo(int nx, int ny) {
		this.x = nx - this.width / 2;
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public Fort() {
		this.image = GameBoard.fortImage;
		this.lives = 3;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		this.x = GameBoard.WIDTH / 2;
		this.y = GameBoard.HEIGHT - this.height;
	}
	
	public Bullet fireWeapon() {
		return new Bullet(this.x+(this.width/2), this.y);
	}
}
