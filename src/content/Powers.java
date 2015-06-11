package content;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Powers {
	
	public static final int NUM_POWERS = 10;
	public static BufferedImage[] powers;
	
	public static void load() {
		powers = new BufferedImage[NUM_POWERS];
		try {
			powers[0] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/brush.gif"));
			powers[1] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/fraoula.gif"));
			powers[2] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/makairi.gif"));
			powers[3] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/nota.gif"));
			powers[4] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/elenhdunamh.gif"));
			powers[5] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/Map.gif"));
			powers[6] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/phone.gif"));
			powers[7] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/aplwstra.gif"));
			powers[8] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/shadow.gif"));
			powers[9] = ImageIO.read(Powers.class.getResourceAsStream("/Powers/torch.gif"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage get(int num) {
		return powers[num];
	}

}
