package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PowerUpBuilder {
	private String type; //Tipo de PowerUp
	private Texture texture;
	private float x;
	private float y;
	private long duracion;
	private boolean recogido;
	
	public PowerUpBuilder(String type){
		this.type = type;
		this.recogido = false;
	}
	public PowerUpBuilder setTexture(String texturePath) {
		this.texture = new Texture(texturePath);
		return this;
	}
	 public PowerUpBuilder setPosition(float x, float y) {
	        this.x = x;
	        this.y = y;
	        return this;
	    }
	 public PowerUpBuilder setDuracion(long duracion) {
		 this.duracion = duracion;
		 return this;
	 }
	 
	 public PowerUp build() {
	        PowerUp powerUp = null;
	        
	        if (type.equals("VidaExtra")) {
	            powerUp = new VidaExtra(x, y, texture);
	        } else if (type.equals("Invulnerable")) {
	            powerUp = new Invulnerable(x, y, texture, duracion);
	        }

	        return powerUp; // Ahora devuelve el tipo correcto que implementa PowerUp
	    }
	}
