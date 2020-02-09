import java.io.*;
import java.util.*;

/** Clase abstracta que permite representar un lado.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public class Arista extends Lado{

	/** Constructor de la clase Vertice
    * @param: Vertice u, primer vertice extremo de la arista.
    * @param: Vertice v, segundo vertice extremo de la arista.
    * @param: int tipo, tipo del lado(Arista).
    * @param: double peso, peso del lado(Arista).
    */
	public Arista(Vertice u, Vertice v, int tipo, double peso){
		v1 = u;
		v2 = v;
		this.tipo = tipo;
		this.peso = peso;
	}

	/** Funcion que retorna retorna el vértice que es el primer extremo de la arista ​ a . ​
	* @param: Lado l, Arista al que nos referimos.
    * @return: Vertice, Obtiene vértice que es el primer extremo de la arista ​ a .
    */
	public Vertice getExtremo1(Lado l){ return l.v1; }

	/** Funcion que retorna retorna el vértice que es el segundo extremo de la arista ​ a.
	* @param: Lado l, Arista al que nos referimos.
    * @return: Vertice, Obtiene vértice que es el segundo extremo de la arista ​ a .
    */
	public Vertice getExtremo2(Lado l){ return l.v2; }

	/** Funcion que retorna un string con la representacion del Arista.
	* @param: Arista a, Arista al que nos referimos.
    * @return: String, representacion del Arista como un string.
    */
	public String toString(Arista a){ return super.toString( a ); }

}