package puppy.code;

public class EfectoPowerUp {
	private PowerUp powerUp;
	
	public void setPowerUp (PowerUp powerUp) {
		this.powerUp = powerUp;
	}
	
	public void aplicarEfecto(Nave4 nave) {
		if (powerUp == null) {
			
		}
		else {
			powerUp.aplicarEfecto(nave);
		}
		
	}
}
