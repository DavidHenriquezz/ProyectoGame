package puppy.code;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;



public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private boolean invulnerable = false;
    private float tiempoInvulnerable = 0; // Duración de invulnerabilidad
    private final float TIEMPO_INVULNERABLE_MAX = 5.0f; // Duración en segundos
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    
    //private ArrayList<Bullet> balas = new Arraylist<>();
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);

    }
    private void moverConTeclado() {
    	float deltaTime = Gdx.graphics.getDeltaTime();
    	float x = spr.getX();
    	float y = spr.getY();
    	
    	if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x-= 400 * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x+= 400 * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += 400 * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= 400 * deltaTime;
        
        spr.setPosition(x,y);
    }
    private void mantenerEnPantalla() {
    	float ScreenWidth = Gdx.graphics.getWidth();
    	float screenHeight = Gdx.graphics.getHeight();
    	float x =  spr.getX();
        float y =  spr.getY();
    	
    	//Limitar el movimiento en el eje x
    	if (x < 0 ) 
        	spr.setX(0);
    	else if(x + spr.getWidth() > ScreenWidth){
    		spr.setX(ScreenWidth - spr.getWidth());
    	}
    	
    	//Limitar el movimiento en el eje y
    	if (y < 0) 
            spr.setY(0);
        else if (y + spr.getHeight() > screenHeight) {
            spr.setY(screenHeight - spr.getHeight());
        }
    }
    private void efectoHerido() {
    	spr.setX(spr.getX() + MathUtils.random(-2, 2));
        tiempoHerido--;
        if (tiempoHerido <= 0) {
            herido = false;
        }
    }
    private void disparar(PantallaJuego juego) {
    	Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,20,txBala, false);
	    juego.agregarBala(bala);
	    soundBala.play();
    }
    public void draw(SpriteBatch batch, PantallaJuego juego){
        if (!herido) {
        	//Movimiento principal
	        moverConTeclado();
	        mantenerEnPantalla();
	        spr.setPosition(spr.getX() + xVel, spr.getY() + yVel);
            spr.draw(batch);
            
        } else {
        	//Herido
           efectoHerido();
 		   spr.draw(batch); 
 		 }
        // disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {         
        	disparar(juego);
        }
        
        if (invulnerable) {
        	tiempoInvulnerable -= Gdx.graphics.getDeltaTime();
            if (tiempoInvulnerable <= 0) {
                invulnerable = false;
            }
        }
    }
      
    public boolean checkCollision(Enemigo enemigo) {
        if(!invulnerable && !herido && enemigo.getArea().overlaps(spr.getBoundingRectangle())){
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido = tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    
    public void setInvulnerable(boolean estado) {
        this.invulnerable = estado;
        if (estado) {
            tiempoInvulnerable = TIEMPO_INVULNERABLE_MAX;
        }
    }
    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
	public Rectangle getArea() {
		return spr.getBoundingRectangle();
	}
}
