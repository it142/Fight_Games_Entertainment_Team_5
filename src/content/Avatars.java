package content;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Avatars {
	
	public static final int NUM_AVATARS = 10;
	public static BufferedImage[] avatars;
	
	public static void load() {
		avatars = new BufferedImage[NUM_AVATARS];
		try {
			avatars[0] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/giwrgos.png"));
			avatars[1] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/katerina.png"));
			avatars[2] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/aggelos.png"));
			avatars[3] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/euthimis.png"));
			avatars[4] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/elena.png"));
			avatars[5] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/komotinaios.png"));
			avatars[6] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/sokratis.png"));
			avatars[7] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/nikiforos.png"));
			avatars[8] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/vana.png"));
			avatars[9] = ImageIO.read(Avatars.class.getResourceAsStream("/Avatars/giannis.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage get(int num) {
		return avatars[num];
	}

}
