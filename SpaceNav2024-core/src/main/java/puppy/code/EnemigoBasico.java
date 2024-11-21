package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class EnemigoBasico extends Enemigo {
	public EnemigoBasico (Texture textura, int x, int y, int xSpeed, int ySpeed) {
		super(textura, x, y, xSpeed, ySpeed);
	}
	
	@Override
    public void move() {
        // Actualiza la posición en X e Y
        float newX = getSprite().getX() + getXSpeed();
        float newY = getSprite().getY();

        // Verifica si el enemigo ha tocado los bordes de la pantalla en X
        if (newX > Gdx.graphics.getWidth() - getSprite().getWidth() || newX < 0) {
            // Cambia la dirección en X
            setXSpeed(-getXSpeed());
            
            // Desciende en el eje Y
            newY -= 40; // Ajusta este valor para el tamaño de "escalón" en Y

            // Asegura que el enemigo no baje más allá del borde inferior
            if (newY < 0) {
                newY = 0; // Limita la posición Y al borde inferior
            }
        }

        // Actualiza la posición del sprite
        getSprite().setPosition(newX, newY);
    }
}
