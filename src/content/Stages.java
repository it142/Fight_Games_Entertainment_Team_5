package content;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Stages {
	
	public static final int NUM_STAGES = 6;
	public static BufferedImage[] stages;
	
	public static void load() {
		stages = new BufferedImage[NUM_STAGES];
		try {
			stages[0] = ImageIO.read(Stages.class.getResourceAsStream("/Pistes/pista0.png"));
			stages[1] = ImageIO.read(Stages.class.getResourceAsStream("/Pistes/pista1.png"));
			stages[2] = ImageIO.read(Stages.class.getResourceAsStream("/Pistes/pista2.png"));
			stages[3] = ImageIO.read(Stages.class.getResourceAsStream("/Pistes/pista3.png"));
			stages[4] = ImageIO.read(Stages.class.getResourceAsStream("/Pistes/pista4.png"));
			stages[5] = ImageIO.read(Stages.class.getResourceAsStream("/Pistes/pista5.png"));		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage get(int stage) {
		return stages[stage];
	}
	

}
