import java.io.*;
import java.util.*;

/** Clase abstracta que permite representar un lado.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public class Arco extends Lado{

	/** Constructor de la clase Vertice
    * @param: Vertice v, vertice de partida.
    * @param: Vertice v, vertice de llegada.
    * @param: int tipo, tipo del lado(Arco).
    * @param: double peso, peso del lado(Arco).
    */
	public Arco(Vertice vi, Vertice vf, int tipo, double peso){
		v1 = vi;
		v2 = vf;
		this.tipo = tipo;
		this.peso = peso;
	}

	/** Funcion que retorna el extremo Inicial.
	* @param: Arco a, arco al que nos referimos.
    * @return: Vertice, Vertice de partida.
    */
	public Vertice getExtremoInicial(Arco a){ return a.v1; }

	/** Funcion que retorna el extremo final.
	* @param: Arco a, arco al que nos referimos.
    * @return: Vertice, Vertice de llegada.
    */
	public Vertice getExtremoFinal(Arco a){ return a.v2; }

	/** Funcion que retorna True si el vertice pasado por parametro es inicial.
	* @param: Arco a, arco al que nos referimos.
	* @param: Vertice v, el vertice que veremos si es el inicial.
    * @return: Boolean, True si el vertice es el inicial, en caso contrario False.
    */
	public boolean esExtremoInicial(Arco a, Vertice v){ return v.getId(v) == v1.getId(v1); }

	/** Funcion que retorna True si el vertice pasado por parametro es final.
	* @param: Arco a, arco al que nos referimos.
	* @param: Vertice v, el vertice que veremos si es el final.
    * @return: Boolean, True si el vertice es el final, en caso contrario False.
    */
	public boolean esExtremoFinal(Arco a, Vertice v){ return v.getId(v) == v2.getId(v2); }

	/** Funcion que retorna un string con la representacion del Arco.
	* @param: Arco a, arco al que nos referimos.
    * @return: String, representacion del Arco como un string.
    */
	public String toString(Arco a){ return super.toString( a ); }

}