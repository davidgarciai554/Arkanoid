package codigo;

import acm.util.RandomGenerator;
import java.awt.Color;
import acm.graphics.GObject;
import acm.graphics.GOval;


public class Bola extends GOval{
	RandomGenerator aleatorio= new RandomGenerator();
	private double vx=(aleatorio.nextDouble(-2.5,2.5)),vy=-1;
	public Bola(double width, double height) {
		super(width, height);
	}
	/**
	 * @param width :el ancho y alto de la bola
	 * @param color : el color de la bola
	 */
	public Bola(double width, Color color){
		super(width,width);
		setFilled(true);
		setFillColor(color);
	}
	public void FisicasBola(Arkanoid arkanoid){
		move(vx,vy);
		if(this.getX()>arkanoid.ANCHO_PANTALLA-25|| this.getX()<0){
			vx=-vx;
		}
		if(this.getY()<0){
			vy=-vy;
		}
		if(chequeaColision(arkanoid, getX(), getY()+getHeight())){
			if(chequeaColision(arkanoid, getX()+getWidth(), getY()+getHeight())){
				if(chequeaColision(arkanoid, getX(), getY())){
					if(chequeaColision(arkanoid, getX()+getWidth(), getY())){
						if(chequeaColision(arkanoid, getX()+getWidth()/2, getY())){
							if(chequeaColision(arkanoid, getX(), getY()+getHeight()/2)){
								if(chequeaColision(arkanoid, getX()+getWidth()/2, getY()+getHeight())){
									if(chequeaColision(arkanoid, getX()+getWidth(), getY()+getHeight()/2)){
										if(chequeaColision(arkanoid, getX()+getWidth()/4, getY())){
											if(chequeaColision(arkanoid, getX(), getY()+getHeight()/4)){
												if(chequeaColision(arkanoid, getX()+getWidth()/4, getY()+getHeight())){
													if(chequeaColision(arkanoid, getX()+getWidth(), getY()+getHeight()/4)){	
														if(chequeaColision(arkanoid, getX()+getWidth()/8, getY())){
															if(chequeaColision(arkanoid, getX(), getY()+getHeight()/8)){
																if(chequeaColision(arkanoid, getX()+getWidth()/8, getY()+getHeight())){
																	if(chequeaColision(arkanoid, getX()+getWidth(), getY()+getHeight()/8)){
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(this.getY()+72>arkanoid.ALTO_PANTALLA){
			arkanoid.GAMEOVER-=1;
			arkanoid.puntuacion-=2;
			if(arkanoid.GAMEOVER==2){
				arkanoid.remove(arkanoid.Corazon1);
			}
			if(arkanoid.GAMEOVER==1){
				arkanoid.remove(arkanoid.Corazon2);
			}
			if(arkanoid.GAMEOVER==0){
				vy=0;
				vx=0;
			}
			vy=-vy;
		}
	}
	private boolean chequeaColision(Arkanoid arkanoid,double posX,double posY){
		boolean noHaChocado=true;
		GObject aux=arkanoid.getElementAt(posX,posY);
		if(aux instanceof Cursor){
			/*si entra aqui es porque el objeto
			//esta en la posicion PosX,PosY
			 *es de tipo cursor.
			 *
			 * Creamos un rebote que no cambie el signo de la vx
			 * pero que dependiendo de pone rebote tomara distintas
			 * direcciones
			 */
			if(posX<=aux.getX()+aux.getWidth()/3){	
				if(vx>0){
					vx=((posX-aux.getX())/10);
				}
				else{
					vx=-((posX-aux.getX())/10);
				}
			}
			if(posX>=aux.getX()+aux.getWidth()*2/3){
				if(vx>0){
					vx=((posX-aux.getX())/10);
				}
				else{
					vx=-((posX-aux.getX())/10);
				}
			}
			while(vx>2.5)vx-=0.1; //Regulamos la vx para que sea jugable
			while(vx<-2.5)vx+=0.1;

			vy*=-1;
			sonidoCursor _sonidoCursor=new sonidoCursor();
			_sonidoCursor.start();
			noHaChocado=false;
		}
		if(aux instanceof Ladrillo){
			//si entra aqui es porque el objeto
			//esta en la posicion PosX,PosY
			//es de tipo ladrillo
			vy*=-1;
			vx*=-1;
			((Ladrillo) aux).golpes-=1;
			if(((Ladrillo) aux).golpes ==1){
				((Ladrillo) aux).setImage("img/LadrilloPiramideRoto.png");
				arkanoid.puntuacion+=5;
				//Depende del mapa los ladrillos tendran distinta orientacion
				if (arkanoid.direccionLadrillos==true){
					((Ladrillo) aux).setSize(arkanoid.ALTO_LADRILLO,arkanoid.ANCHO_LADRILLO);
				}
				else{
					((Ladrillo) aux).setSize(arkanoid.ANCHO_LADRILLO,arkanoid.ALTO_LADRILLO);
				}
			}
			if(((Ladrillo) aux).golpes ==0){
				arkanoid.remove(aux);
				arkanoid.puntuacion+=10;
				arkanoid.ladrillos--;

			}
			sonidoLadrillo _sonidoLadrillo=new sonidoLadrillo();
			_sonidoLadrillo.start();
			noHaChocado=false;
		}
		return noHaChocado;
	}

	public class sonidoCursor extends Thread {		
		public void run() {                               
            ReproducirSonidos s = new ReproducirSonidos();
            s.sonido(s.getClass().getResource("/Sound/golpeoCursor.wav").getFile());
        }
	} 

	public class sonidoLadrillo extends Thread {												

        public void run() {     
        	ReproducirSonidos s = new ReproducirSonidos(); //que sigue el juego
            s.sonido(s.getClass().getResource("/Sound/golpeoLadrillo.wav").getFile());
        }
    }
}