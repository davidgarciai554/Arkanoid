package codigo;

import java.applet.AudioClip;
import java.net.URL;

public class  SonidoLadrillo{
	public static void main(String[] args) {
		SonidoLadrillo s = new SonidoLadrillo();
		s.ReproducirSonido(s.getClass().getResource("/Sound/golpeoLadrillo.wav").getFile());
	}

	public void ReproducirSonido(String fichero) {
		AudioClip sonido;
		URL url;
		try{
			url = new URL("file:"+fichero);
			sonido = java.applet.Applet.newAudioClip(url);
			sonido.play();
			Thread.sleep (1200);
			sonido.loop();
			sonido.stop();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}