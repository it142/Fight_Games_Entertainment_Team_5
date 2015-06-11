package handlers;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager {

	AudioInputStream inputStream1;
	AudioInputStream inputStream2;
	Clip MenuClip;
	Clip BattleClip;

	public void Playaudio(String audioName) {
		try {
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(new File(audioName));
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void StartLoop(String aState) {
		try {

			if (aState.equals("menu")) {
				inputStream1 = AudioSystem.getAudioInputStream(new File(
						"Menu.wav"));
				MenuClip = AudioSystem.getClip();
				MenuClip.open(inputStream1);
				MenuClip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			if (aState.equals("battle")) {
				inputStream2 = AudioSystem.getAudioInputStream(new File(
						"Battle.wav"));
				BattleClip = AudioSystem.getClip();
				BattleClip.open(inputStream2);
				BattleClip.loop(Clip.LOOP_CONTINUOUSLY);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void StopLoop(String aState) {
		try {

			if (aState.equals("menu")) {
				MenuClip.stop();
			}
			if (aState.equals("battle")) {

				BattleClip.stop();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}