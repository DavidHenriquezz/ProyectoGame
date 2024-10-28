package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class VidaExtra implements PowerUp {
	private Texture textura;
	private Rectangle bounds;
	private boolean collected;
	
	public VidaExtra(float x, float y) {
		this.textura = new Texture("1up.png"); //textura del powerUp
		this.bounds = new Rectangle(x,y, textura.getWidth(), textura.getHeight());
		this.collected = false;
	}
	
	@Override
	public void aplicarEfecto(Nave4 nave) {
		if (!collected) {
			nave.setVidas(nave.getVidas() + 1);
			collected = true;
		}
	}
	
	@Override
    public void update() {
        bounds.y -= 2; // Ajusta la velocidad de caída
    }
	
	@Override
    public void render(SpriteBatch batch) {
        if (!collected) {
            batch.draw(textura, bounds.x, bounds.y); //dibujar el powerUp
        }
    }
	
	@Override 
	
	public boolean isCollected() {
		return collected;
	}
}
