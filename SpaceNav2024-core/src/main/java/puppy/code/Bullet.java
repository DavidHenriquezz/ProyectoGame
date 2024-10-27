package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bullet {

	private int xSpeed;
	private int ySpeed;
	private boolean destroyed = false;
	private Sprite spr;
	private boolean isEnemyBullet;
	
	public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx, boolean isEnemyBullet) {
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.isEnemyBullet = isEnemyBullet;
    }
	
	public void update() {
        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true;
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
        	destroyed = true;
        }
        
    }
	public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }   
	
	// Método de colisión para asteroides o enemigos (en el caso de balas de la nave)
	public boolean checkCollision(Enemigo enemigo) {
        if(spr.getBoundingRectangle().overlaps(enemigo.getArea())){
        	// Se destruyen ambos
            this.destroyed = true;
            return true;

        }
        return false;
    }
	
	// Método de colisión para la nave (solo para balas enemigas)
    public boolean checkCollision(Nave4 nave) {
        if (isEnemyBullet && spr.getBoundingRectangle().overlaps(nave.getArea())) {
            destroyed = true;
            return true;
        }
        return false;
    }	     
	    
    public boolean isDestroyed() {return destroyed;}
    public boolean isEnemyBullet() {return isEnemyBullet;}
}
