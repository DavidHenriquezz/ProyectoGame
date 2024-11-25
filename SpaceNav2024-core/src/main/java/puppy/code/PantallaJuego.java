package puppy.code;

import java.util.ArrayList;
import puppy.code.PowerUpBuilder;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	
	private Nave4 nave;
	private EfectoPowerUp aplicadorEfectos = new EfectoPowerUp();
	private ArrayList<Enemigo> enemigos = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	public static ArrayList<Bullet> balasEnemigas = new ArrayList<>();
	private ArrayList<PowerUp> mejoras = new ArrayList<>();
	//private Texture enemigoBalaTexture;

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.mp3")); 
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
	    nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("Rocket2.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
        nave.setVidas(vidas);
        //crear EnemigoBasico
        
        //enemigoBalaTexture = new Texture(Gdx.files.internal("Rocket2.png"));
        crearEnemigos();
	}
	private void crearEnemigos() { //Crea los enemigos para la ronda
		Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            Enemigo enemigoBasico = new EnemigoBasico(new Texture(Gdx.files.internal("EnemigoBasico.png")),
                                                      r.nextInt(Gdx.graphics.getWidth() - 65),
                                                      Gdx.graphics.getHeight() - 100, // Inicia en la parte superior
                                                      velXAsteroides + r.nextInt(2),
                                                      0);
            enemigos.add(enemigoBasico);
        }

        for (int i = 0; i < cantAsteroides; i++) {
            EnemigoAvanzado enemigoAvanzado = new EnemigoAvanzado(new Texture(Gdx.files.internal("EnemigoAvanzado.png")),
                                                                  r.nextInt(Gdx.graphics.getWidth() - 65),
                                                                  Gdx.graphics.getHeight() - 100,
                                                                  velXAsteroides + r.nextInt(2),
                                                                  velYAsteroides + r.nextInt(2),
                                                                  1.0f);
            enemigos.add(enemigoAvanzado);
        }
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	@Override
	public void render(float delta) {
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          batch.begin();
		  dibujaEncabezado();
		  
	      if (!nave.estaHerido()) {
	    	  actualizarBalas();
	    	  actualizarEnemigos();
	    	  
		      }
          // Actualizar movimiento de enemigos
	      for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
	      for (int i = 0; i < mejoras.size(); i++) { //Power Ups
	    	    PowerUp powerUp = mejoras.get(i);
	    	    powerUp.update();
	    	    powerUp.render(batch);

	    	    if (nave.getArea().overlaps(powerUp.getBounds())) {
	    	    	
	    	    	aplicadorEfectos.setPowerUp(powerUp); // Se usa patron strategy
	    	    	aplicadorEfectos.aplicarEfecto(nave); //Aplicar el efecto al recogerlo
	    	    	
	    	    }

	    	    if (powerUp.isCollected()) {
	    	        mejoras.remove(i);
	    	        i--; // Ajustar índice tras eliminación
	    	    }
	    	}
	      if (nave.estaDestruido()) {
	    	  GameOver();
  		  }
	      batch.end();
	      //nivel completado
	      if (enemigos.size()==0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score, 
					velXAsteroides+1, velYAsteroides+1, cantAsteroides+5);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		  }
	    	 
	}
	private void actualizarBalas() {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            Random random = new Random();
            double r = random.nextDouble();
            for (int j = 0; j < enemigos.size(); j++) {    
                if (b.checkCollision(enemigos.get(j))) {          
                    explosionSound.play();
                    if (r < 0.025 && r > 0.01) { //Dropear power-ups. Cambiar luego para que PantallaJuego no lo haga
                    	//PowerUp nuevoPowerUp = new VidaExtra(enemigos.get(j).getSprite().getX(), enemigos.get(j).getSprite().getY());
                    	PowerUp nuevoPowerUp = new PowerUpBuilder("VidaExtra")
                    			.setTexture("1up.png")
                    			.setPosition(enemigos.get(j).getSprite().getX(), enemigos.get(j).getSprite().getY())
                    			.build();
                    	mejoras.add(nuevoPowerUp);
                    	
                    }
                    if (r < 0.01) {
                    	PowerUp inv = new PowerUpBuilder("Invulnerable")
                    			.setTexture("Invulnerable.png")
                    			.setPosition(enemigos.get(j).getSprite().getX(), enemigos.get(j).getSprite().getY())
                    			.setDuracion(5000)
                    			.build();
                    	mejoras.add(inv);
                    }
                    enemigos.remove(j); 
                    j--; // Ajustar índice tras eliminar
                    score += 10;
                    
                }
            }

            if (b.isDestroyed()) {
                balas.remove(i);
                i--; // Evitar saltar elementos tras eliminación
            }
        }
    }
	private void actualizarEnemigos() {
		float deltaTime = Gdx.graphics.getDeltaTime();
        for (int i = 0; i < enemigos.size(); i++) {
            Enemigo enemigo = enemigos.get(i);
            enemigo.update();
            enemigo.draw(batch);

            if (nave.checkCollision(enemigo)) {
                enemigos.remove(i);
                i--; // Ajustar índice tras eliminación
            }
        }
    }
	private void GameOver() {
        if (score > game.getHighScore()) {
            game.setHighScore(score);
        }
        Screen gameOverScreen = new PantallaGameOver(game);
        game.setScreen(gameOverScreen);
        dispose();
    }
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}
   
}
