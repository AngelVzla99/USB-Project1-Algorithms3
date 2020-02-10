import java.io.*;
import java.util.*;

/** Clase abstracta que permite representar un lado.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public abstract class Lado{
	protected Vertice v1, v2;		// Vertices del lado (v1,v2)
	protected int tipo;				// Tipo del lado
	protected double peso;			// Peso del lado

	/** Funcion que retorna el peso del lado l.
	* @param Lado l, Lado al que nos referimos
    * @return double, El peso del lado l.
    */
	public double getPeso(Lado l){ return l.peso; }


	/** Funcion que retorna true si el lado l incide en el vertice v.
	* @param Lado l, Lado al que nos referimos
	* @param Vertice v, vertice al que queremos saber si incide.
    * @return boolean, Indica si el lado ​l incide en el vértice ​ v.
    */
	public boolean incide(Lado l, Vertice v){ 
		return (v.getId(v) == v1.getId(v1)) || (v.getId(v) == v2.getId(v1)); 
	} 

	
	/** Metodo que verifica si un lado dado es un bucle
	* @param Lado linformacion del lado.
	* @return boolean indica si el lado es un bucle. 
	*/
	protected boolean bucle(Lado l, Vertice v){
		return (v.getId(v) == v1.getId(v1)) && (v.getId(v) == v2.getId(v1)); 
	}


	/** Metodo que retorna los vertices incidentes de un lado
	* @param Lado informacion del lado.
	* @return Vertice[] arreglo con los dos vertices incidentes. 
	*/
	protected Vertice[] vertices(Lado l){ 
		Vertice[] vertices ={l.v1, l.v2};
		return vertices; 
	}


	/** Funcion que retorna el tipo del lado l.
	* @param Lado l, Lado al que nos referimos
    * @return int, el tipo del lado v.
    */
	public int getTipo(Lado l){ return l.tipo; }


	/** Funcion que retorna un string con los atributos de l.
	* @param Lado l, Lado al que nos referimos.
    * @return String, Contiene todos los atriubutos de l separados por lineas.
    */
	public String toString(Lado l){
		String out = "\033[0;1mVertices:\033[0;0m \n" +
					 l.v1.toString(v1) +
					 l.v2.toString(v2) +
					 "\033[0;1mTipo:\033[0;0m " + l.tipo + "\n" +
					 "\033[0;1mPeso:\033[0;0m " + l.peso + "\n";
		return out;
	}
}