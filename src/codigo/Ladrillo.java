package codigo;

import acm.graphics.GImage;

public class Ladrillo extends GImage{
	public int golpes=2;
	public Ladrillo (int posX,int posY,double alto, double ancho,String ladrillo){
		super(ladrillo);
		setSize(ancho,alto);
		setLocation(posX,posY);
	}
}