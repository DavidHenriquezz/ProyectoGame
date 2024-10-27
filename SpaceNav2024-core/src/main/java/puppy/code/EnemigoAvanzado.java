package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class EnemigoAvanzado extends Enemigo {
	private float IntervaloDisparo;
    private float TiempoDesdeUltimoDisparo;
    private Texture txBala;
    
    public EnemigoAvanzado (Texture textura, int x, int y, int xSpeed, int ySpeed, float IntervaloDisparo) {
    	 super(textura, x, y, xSpeed, ySpeed);
         this.IntervaloDisparo = IntervaloDisparo;
         this.TiempoDesdeUltimoDisparo = 0;
    }
    
    @Override
    public void move() {
        // Simplificación: dejamos que la lógica de rebote en los bordes sea controlada por `update` en Enemigo
        // Si queremos zigzag puro, podemos ajustar el cambio en `xSpeed` en intervalos de tiempo:
        if (Math.random() > 0.98) { // Cambia de dirección en X aleatoriamente
            setXSpeed(-getXSpeed());
        }
    }
    
    public void update(float deltaTime) {
        super.update(); // Actualiza posición y controla los bordes
        TiempoDesdeUltimoDisparo += deltaTime;

        // Verificar si el intervalo de disparo ha pasado
        if (TiempoDesdeUltimoDisparo >= IntervaloDisparo) {
            shoot();
            TiempoDesdeUltimoDisparo = 0;
        }
    }

    private void shoot() {
        Bullet enemyBullet = new Bullet(
            getSprite().getX(), // Posición inicial X de la bala
            getSprite().getY(), // Posición inicial Y de la bala
            0,                  // Velocidad en X, puedes ajustarla
            -5,                 // Velocidad en Y (hacia abajo)
            txBala, // Textura de la bala
            true                // Indica que es una bala enemiga
        );
        // Agrega la bala a la lista de balas enemigas en la pantalla de juego
        PantallaJuego.balasEnemigas.add(enemyBullet);
    }
	
}
