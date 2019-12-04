/*
 * Autor:David Garcia
 * 
 */
package codigo;

import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.Font;
import acm.util.RandomGenerator;


public class Arkanoid extends GraphicsProgram{
	RandomGenerator aleatorio= new RandomGenerator();
	boolean partidaIniciada = false;
	boolean automatico=false;
	GLabel Auto=new GLabel("Auto");
	GImage Fondo=new GImage("img/fondo.jpg");
	GImage arkanoidTitulo=new GImage("img/arkanoid.png");
	GImage FondoInicio=new GImage("img/fondoIncio.jpg");
	GImage FondoNegro=new GImage("img/FondoNegro.jpg");
	GImage Muerte=new GImage("img/MUERTE.gif");
	GRect muro=new GRect(120,30);
	GLabel Muro=new GLabel("Muro");
	GRect piramide=new GRect(120,30);
	GLabel Piramide=new GLabel("Piramide");
	Bola bola = new Bola(10,Color.black);
	Cursor cursor= new Cursor(400, 60, 12,"img/ImgCursor.png"); 
	static int ALTO_PANTALLA=500,ANCHO_PANTALLA=600;
	static int ANCHO_LADRILLO=35,ALTO_LADRILLO=15;
	int GAMEOVER=3,ladrillos=0,puntuacion=0;
	boolean direccionLadrillos=false;//Depende del mapa que se ejecute los ladrillos tendran una direccion
	boolean Victoria=false;//Indicara que el usuario ha eliminado todos los ladrillos
	GImage Corazon1=new GImage("img/corazon1.png");
	GImage Corazon2=new GImage("img/corazon2.png");
	GImage Corazon3=new GImage("img/corazon3.png");
	GLabel marcador=new GLabel("0");

	public void init(){
		pantallaIncio();//Iniciamos la pantalla de inicio
		addMouseListeners();
	}

	public void mouseClicked(MouseEvent evento){
		int posicionX = evento.getX();//Donde se ha producido el click en eje X
		int posicionY = evento.getY();//Donde se ha producido el click en eje Yç
		if(getElementAt(posicionX,posicionY)==muro||getElementAt(posicionX,posicionY)==Muro){
			removeAll();
			add(Fondo);
			add(Corazon1,ANCHO_PANTALLA-50,ALTO_PANTALLA-90);
			add(Corazon2,ANCHO_PANTALLA-80,ALTO_PANTALLA-90);
			add(Corazon3,ANCHO_PANTALLA-110,ALTO_PANTALLA-90);
			add(marcador,20,ALTO_PANTALLA-80);		
			creaMuro();
			add(cursor,ANCHO_PANTALLA/2-25,ALTO_PANTALLA-135);
			add(bola,ANCHO_PANTALLA/2,ALTO_PANTALLA-150);
			partidaIniciada = true;
		}
		if(getElementAt(posicionX,posicionY)==piramide||getElementAt(posicionX,posicionY)==Piramide ){
			removeAll();
			add(Fondo);
			add(Corazon1,ANCHO_PANTALLA-50,ALTO_PANTALLA-90);
			add(Corazon2,ANCHO_PANTALLA-80,ALTO_PANTALLA-90);
			add(Corazon3,ANCHO_PANTALLA-110,ALTO_PANTALLA-90);
			add(marcador,20,ALTO_PANTALLA-80);
			creaPiramide();
			add(cursor,ANCHO_PANTALLA/2-25,ALTO_PANTALLA-135);
			add(bola,ANCHO_PANTALLA/2,ALTO_PANTALLA-150);
			partidaIniciada = true;
		}
		Auto.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		if(getElementAt(posicionX,posicionY)==arkanoidTitulo){
			if(automatico){
				automatico=false;
				remove(Auto);
			}
			else{
				automatico=true;
				add(Auto,2,20);
			}
		}
		
		
	}

	public void run(){
		
		while (!partidaIniciada){
			pause(5);
			//no hace nada hasta que empiece
		}
		while(GAMEOVER>0 && Victoria==false){
			marcador.setLabel(""+puntuacion);
			bola.FisicasBola(this);
			if(automatico){
				cursor.setLocation(bola.getX()-aleatorio.nextInt(2,58),cursor.getY());
			}
			//pause(5);
			if(ladrillos==0){
				Victoria=true;
			}			
		}
		if(GAMEOVER==0 && Victoria==false){//En el caso que pierdas saldra esta pantalla
			add(FondoNegro);
			FondoNegro.setSize(ANCHO_PANTALLA,ALTO_PANTALLA);
			add(Muerte,50,0);
		}
		if(ladrillos==0 && Victoria==true){//En el caso que ganes saldra esta pantalla
			removeAll();
			add(FondoNegro);
			GLabel puntos=new GLabel("");
			puntos.setFont(new Font("Comic Sans MS",Font.BOLD,20));
			puntos.setColor(Color.white);
			puntos.setLabel("Tu puntuacion final ha sido de: "+puntuacion);
			add(puntos,ANCHO_PANTALLA/2-150,ALTO_PANTALLA/2-50);
			GLabel vidas=new GLabel("Y has terminado con estas vidas");
			vidas.setFont(new Font("Comic Sans MS",Font.BOLD,20));
			vidas.setColor(Color.white);
			add(vidas,ANCHO_PANTALLA/2-150,ALTO_PANTALLA/2);
			add(Corazon1,ANCHO_PANTALLA/2-50,ALTO_PANTALLA/2+30);
			if(GAMEOVER>=2){
				add(Corazon2,ANCHO_PANTALLA/2-10,ALTO_PANTALLA/2+30);
			}
			if(GAMEOVER>=3){
				add(Corazon3,ANCHO_PANTALLA/2+30,ALTO_PANTALLA/2+30);
			}
			
			
		}
	}

	public void mouseMoved(MouseEvent evento){

		cursor.setLocation(evento.getX()-30,cursor.getY());
	}

	public void creaPiramide(){

		int numeroLadrillos=14;
		int desplazamientoInicial =(getWidth()-numeroLadrillos*ANCHO_LADRILLO)/2;
		for(int j=0;j<numeroLadrillos;j++){
			for(int i=j;i<numeroLadrillos;i++){
				Ladrillo miLadrillo =new Ladrillo(desplazamientoInicial+ANCHO_LADRILLO*i-ANCHO_LADRILLO/2*j,ALTO_LADRILLO*j+ALTO_LADRILLO,ANCHO_LADRILLO,ALTO_LADRILLO,"img/LadrilloPiramide.png");
				add(miLadrillo);
				ladrillos++;
				direccionLadrillos=true;
			}
		}
	}

	public void creaMuro(){
		int desplazamientoInicial=20;
		for(int i=0;i<7;i++){
			for(int j=0;j<15;j++){
				Ladrillo miLadrillo =new Ladrillo(desplazamientoInicial+(ANCHO_LADRILLO+1)*j,desplazamientoInicial*2+ (ALTO_LADRILLO+1)*i,ALTO_LADRILLO,ANCHO_LADRILLO,"img/Ladrillo.png");
				add(miLadrillo);
				ladrillos++;
				direccionLadrillos=false;
			}
		}
	}
	public void pantallaIncio(){//Todos los elemntos que aparecen en la primera pantalla
		setSize(ANCHO_PANTALLA,ALTO_PANTALLA);
		FondoInicio.setSize(ANCHO_PANTALLA,ALTO_PANTALLA);
		add(FondoInicio);
		arkanoidTitulo.scale(0.5,0.5);
		add(arkanoidTitulo,ANCHO_PANTALLA/2-200,50);
		muro.setFilled(true);
		muro.setFillColor(Color.white);
		piramide.setFilled(true);
		piramide.setFillColor(Color.white);
		add(muro,ANCHO_PANTALLA/2-60,ALTO_PANTALLA/2+60);
		add(Muro,ANCHO_PANTALLA/2-22,ALTO_PANTALLA/2+80);
		Muro.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		add(piramide,ANCHO_PANTALLA/2-60,ALTO_PANTALLA/2+10);
		add(Piramide,ANCHO_PANTALLA/2-40,ALTO_PANTALLA/2+32);
		Piramide.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		Corazon1.setSize(25,25);
		Corazon2.setSize(25,25);
		Corazon3.setSize(25,25);
		marcador.setFont(new Font("Comic Sans MS",Font.BOLD,20));
	}
}