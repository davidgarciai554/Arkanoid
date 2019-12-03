package codigo;

import acm.graphics.GImage;

/*
 * Autor:David Garcia
 */
public class Cursor extends GImage {
	/**
	 * @param posY :Posicion Y del cursor ,la x siempre sera
	 * (aparecera a la izq de la pantalla)
	 * @param ancho :ancho del cursor
	 * @param alto:alto del cursor 
	 * @param color:color del cursor
	 */
	GImage cursor;
	public Cursor(int posY,double ancho,double alto,String cursor){
		super(cursor);
		setSize(ancho,alto);
		setLocation(0,posY);
	}
}