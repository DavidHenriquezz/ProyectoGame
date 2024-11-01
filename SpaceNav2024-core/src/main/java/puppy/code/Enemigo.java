package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemigo {
	private Sprite sprite;
	private int xSpeed;
	private int ySpeed;
	
	public Enemigo(Texture textura, int x, int y, int xSpeed, int ySpeed) {
		this.sprite = new Sprite(textura);
		this.sprite.setPosition(x,y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public abstract void move();
	
	public void update() {
		move();
		sprite.setPosition(sprite.getX() + xSpeed, sprite.getY() + ySpeed);
		
		//Controlar los bordes de la pantalla
		if (sprite.getX() < 0 || sprite.getX() + 64 > Gdx.graphics.getWidth()) xSpeed = -xSpeed;
        if (sprite.getY() < 0 || sprite.getY() + 64 > Gdx.graphics.getHeight()) ySpeed = -ySpeed;
	}
	
	public Rectangle getArea() {
		return sprite.getBoundingRectangle();
	}
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	public void dispose() {
		sprite.getTexture().dispose();
	}
	
	//Setter y getters
	public Sprite getSprite() {
		return sprite;
	}
	public int getXSpeed() {
		return xSpeed;
	}
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	
	public int getYSpeed() {
		return ySpeed;
	}
	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
	
}
