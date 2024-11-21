package puppy.code;

public class HighscoreManage {
	private static HighscoreManage instancia;
	
	private int highscore;
	
	private HighscoreManage() {
		highscore = 0;
	}
	
	public static HighscoreManage getInstancia() {
		if (instancia == null) {
			synchronized (HighscoreManage.class) {
				if (instancia ==null) {
					instancia = new HighscoreManage();
				}
			}
		}
		return instancia; 
	}
	
	public void actualizarHighscore(int nuevoScore) {
		if (nuevoScore > highscore) {
			highscore = nuevoScore;
		}
	}
	
	public void setHighscore(int Score) {
		highscore = Score;
	}
	public int getHighscore() {
		return highscore;
	}
}
  