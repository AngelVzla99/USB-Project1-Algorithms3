import java.util.*;
import java.io.*;

/** Clase que permite interactuar con todas las duncionalidades
de los TADs a través de un menu.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
class ClienteGrafo{
    public static void main(String[] args)throws IOException{

    	/* Explicacion rapida

    	Clase Vertice (tiene cosas de vertices)

    	Claso Lado (tiene 2 vertices, tipo y peso)
			-> (subclase de Lado) Arco
			-> (subclase de Lado) Arista

    	*/

        // Crear dos vertice ( Borrar si quieres Amin )
        Vertice v1 = new Vertice(1, "Caracas", 1.2, 1.5, 100);
        Vertice v2 = new Vertice(2, "Apure", 1000, 1000.99, 5);

        Arista arco = new Arista(v1,v2,7,10.5);

        System.out.println( arco.toString(arco) );
    	//----
    }
}