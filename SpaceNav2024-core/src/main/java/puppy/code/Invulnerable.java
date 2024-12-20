package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Invulnerable implements PowerUp{
	
	private Texture textura;
    private Rectangle bounds;
    private boolean collected;
    private long startTime;
    //private static final long DURATION = 5000; // Duración en milisegundos (5 segundos)
    private long duration;
    
    public Invulnerable(float x, float y,Texture texture, long duration) {
    	this.textura = new Texture("Invulnerable.png");
    	this.bounds = new Rectangle(x, y, textura.getWidth(), textura.getHeight());
        this.collected = false;
        this.duration = duration;
    }
    
    @Override
	public void aplicarEfecto(Nave4 nave) {
		if (!collected) {
			nave.setInvulnerable(true);
			startTime = System.currentTimeMillis();
			collected = true;
		}
	}
    
    @Override
    public void update() {
        bounds.y -= 7; // Ajusta la velocidad de caída
        if (collected && System.currentTimeMillis() - startTime >= duration) {
            //nave.setInvulnerable(false); hay que ver esta linea ya que update solo deberia ver el movimiento del power up, no su aplicacion
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        if (!collected) {
            batch.draw(textura, bounds.x, bounds.y);
        }
    }
    
    @Override
    public boolean isCollected() {
        return collected;
    }
    
    @Override
	public Rectangle getBounds() {
		return bounds;
	}
}