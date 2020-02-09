import java.util.*;
import java.io.*;

/** Clase vertice que permite identificar un vertices a 
partir de sus atributos: Identificador, nombre, coordenadas 
(x,y) y el peso.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public class Vertice{
	private int identificador;	// Identificador del vertice
	private String nombre;		// Nombre del vertice
	private double x, y;		// Coordenadas (x,y) del vertice
	private double peso; 		// Peso del vertice

	/** Constructor de la clase Vertice
    * @param: int id, identificador del vertice.
    * @param: String nombre, nombre del vertie.
    * @param: double x, coordenada x del vertice.
    * @param: double y, coordenada y del vertice.
    * @param: double p, peso del vertice.
    */
	public Vertice(int id, String nombre, double x, double y, double p){
		this.identificador = id;
		this.nombre = nombre;
		this.x = x;
		this.y = y;
		this.peso = p;
	}

	/** Funcion que retorna el peso del nodo v.
	* @param: Vertice v, vertice al que nos referimos.
    * @return: double, El peso del nodo v.
    */
	public double getPeso(Vertice v){ return v.peso; }

	/** Funcion que retorna el identificador del nodo v.
	* @param: Vertice v, vertice al que nos referimos.
    * @return: int, El identificador del nodo v.
    */
	public int getId(Vertice v){ return v.identificador; }

	/** Funcion que retorna el nombre del nodo v.
	* @param: Vertice v, vertice al que nos referimos.
    * @return: String, El Nombre del nodo v.
    */
	public String getNombre(Vertice v){ return v.nombre; }

	/** Funcion que retorna la coordenada X del nodo v.
	* @param: Vertice v, vertice al que nos referimos.
    * @return: double, La coordenada x del nodo v.
    */
	public double getX(Vertice v){ return v.x; }

	/** Funcion que retorna la coordenada Y del nodo v.
	* @param: Vertice v, vertice al que nos referimos.
    * @return: double, La coordenada y del nodo v.
    */
	public double getY(Vertice v){ return v.y; }

	/** Funcion que retorna un string con todos los atributos del nodo v.
	* @param: Vertice v, vertice al que nos referimos.
    * @return: String, Contiene todos los atriubutos de v separados por lineas.
    */
	public String toString(Vertice v){
		String out = "Nombre: "+ v.nombre + "\n" +
					 "Identificador: "+ v.identificador + "\n" +
					 "Coordenadas: "+ "(" + v.x + "," +v.y+ ")" + "\n" +
					 "Peso: "+ v.peso + "\n\n";
		return out;
	}
}