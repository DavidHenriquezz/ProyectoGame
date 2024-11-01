package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface PowerUp {
	void aplicarEfecto(Nave4 nave); // Aplica el efecto del power-up a la nave
	void update(); // Actualiza la posición y el estado del power-up
	void render(SpriteBatch batch); // Dibuja el power-up en pantalla
	boolean isCollected(); // Verifica si el power-up fue recogido por la nave
	public Rectangle getBounds();
}
