package airdefense;

import java.awt.image.BufferedImage;

public class Explosion {
	protected int x,y,time;
	protected BufferedImage image;
	
	public Explosion(int sx, int sy) {
		this.x = sx;
		this.y = sy;
		this.time = 0;
		this.image = GameBoard.explosionImage;
	}
	
	public int nextStep(){
		return ++this.time;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
}
