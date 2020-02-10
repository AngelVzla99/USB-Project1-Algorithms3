import java.io.*;
import java.util.*;

/** Clase abstracta que permite representar un grafo.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public abstract class Grafo{
    protected int numV = 0;     // Numero de vertices
    protected int numL = 0;     // Numero de de lados.
    protected ArrayList<Vertice> listVert = new ArrayList();   // Lista de vertices.
    protected ArrayList<Lado> listLados = new ArrayList();     // Lista de lados.

    /** Metodo que permite leer un archivo
    * @param String ubicacion del archivo, incluyendo su nombre.
    * @return String contenido del archivo.
    */
    protected String readGraph(String f)throws IOException { 

        FileReader file = new FileReader(f);
        StringBuilder graph = new StringBuilder();

        // Leemos el archivo caracter por caracter.
        int nextChar;
        while ((nextChar = file.read()) != -1){
            graph.append((char) nextChar);
        }
        file.close();

        return String.valueOf(graph);
    }


    /** Metodo base que permite la creacion de grafos dado un archivo con su informacion. 
    Su implementacion queda a cargo de las clases que la heredan.
    * @param String direccion del archivo, incluyendo su nombre.
    * @return Boolean que indica si la lectura se hizo con exito.
    */
    public boolean crearGrafo(String file)
        throws IOException,
        NumberFormatException,
        NoSuchElementException   
    { return true; }


    /** Metodo que retorna el numero de vertices del grafo.
    * @param Grafo informacion del grafo.
    * @return Int numero de vertices.
    */
    public int numeroDeVertices(Grafo g) { return g.numV; }


    /** Metodo que retorna el numero de lados del grafo.
    * @param Grafo informacion del grafo.
    * @return Int numero de lados.
    */
    public int numeroDeLados(Grafo g) { return g.numL; }


    /** Metodo que retorna un arreglo con los vertices del grafo.
    * @param Grafo informacion del grafo.
    * @return ArrayList<Vertice> lista de vertices.
    */
    public ArrayList<Vertice> vertices(Grafo g) { return g.listVert; }


    /** Metodo que retorna un arreglo con los lados del grafo.
    * @param Grafo con la informacion del grafo.
    * @return ArrayList<Lado> lista de lados.
    */
    public ArrayList lados(Grafo g) { return g.listLados; }


    /** Metodo que verifica si se encuentra un vertice en un grafo.
    * @param Grafo informacion del grafo.
    * @param int ID del vertice.
    * @return Boolean que indica si el vertice se encuentra en el grafo.
    */
    public boolean estaVertice(Grafo g, int id) {

        ArrayList<Vertice> vertices = g.vertices(g);
        Vertice v;

        // Verificamos el ID de cada vertice del grafo.
        for (int i = 0; i < vertices.size(); i++){
            v = vertices.get(i);
            if (v.getId(v) == id) {
                return true;
            }
        }
        return false;
    } 


    /** Metodo que agrega un vertice al grafo.
    * @param Grafo informacion del grafo.
    * @param Vertice informacion del vertice.
    * @return Boolean que indica si el vertice se agrego correctamente.
    */
    public boolean agregarVertice(Grafo g, Vertice v) {

        // Si el vertice ya se encuentra en el grafo, entonces no se puede agregar.
        if (g.estaVertice(g, v.getId(v))){
            return false;
        } else {
            g.listVert.add(v);
            g.numV++;
            return true;
        }
    }


    /** Metodo que agrega un vertice al grafo.
    * @param Grafo informacion del grafo.
    * @param int ID del vertice.
    * @param String nombre del vertice.
    * @param double coordenada x del vertice.
    * @param double coordenada y del vertice.
    * @param double peso del vertice.
    * @return Boolean que indica si el vertice se agrego correctamente.
    */
    public boolean agregarVertice(Grafo g, int id, String nombre, double x, double y, double p) {

        // Si el vertice ya se encuentra en el grafo, entonces no se puede agregar.
        if (g.estaVertice(g, id)){
            return false;
        } else {
            Vertice v = new Vertice(id, nombre, x, y, p);
            g.listVert.add(v);
            g.numV++;
            return true;
        }
    }


    /** Metodo que retorna un vertice de un grafo dado su ID.
    * @param Grafo informacion del grafo.
    * @param int ID del vertice.
    * @return Vertice con la informacion del vertice.
    */
    public Vertice obtenerVertice(Grafo g, int id)throws NoSuchElementException {

        Vertice v;  // Contendra la informacion del i-esimo vertice del grafo.

        // Verificamos el ID de cada vertice del grafo.
        for (int i = 0; i < g.listVert.size(); i++){
            v = g.listVert.get(i);
            if (v.getId(v) == id) {
                return v;
            }
        }

        // Si no encontramos ningun vertice con el ID indicado, error.
        String msg = "Elemento inexistente. El vertice con ID " + String.valueOf(id) +
            " no se encuentra en el grafo.";
        throw new NoSuchElementException(msg);
    }


    /** Metodo que elimina un vertice de un grafo dado su ID.
    * @param Grafo informacion del grafo.
    * @param int ID del vertice.
    * @return Boolean indica si la eliminacion se hizo correctamente.
    */
    public boolean eliminarVertice(Grafo g, int id) {

        Vertice v;  // Contendra la informacion del i-esimo vertice del grafo.

        // Verificamos el ID de cada vertice del grafo.
        for (int i = 0; i < g.listVert.size(); i++){
            v = g.listVert.get(i);
            if (v.getId(v) == id) {
                g.listVert.remove(i);
                g.numV--;
                return true;
            }
        }

        // Si no encontramos ningun vertice con el ID indicado, retornamos false.
        return false;
    }


    /** Metodo que retorna el grafo de un vertice en un grafo.
    * @param Grafo informacion del grafo.
    * @param int ID del vertice.
    * @return Int con el grado del vertice.
    */
    public int grado(Grafo g, int id)throws NoSuchElementException {

        // Verificamos que efectivamente el vertice se encuentre en el grafo.
        if (g.estaVertice(g, id)) {

            Vertice v = g.obtenerVertice(g, id); // Vertice indicado.
            ArrayList<Lado> lados = g.lados(g);   
            int grado = 0;
            Lado l;     // Contendra la informacion del i-esimo lado.

            // Verificamos cada lado del grafo.
            for (int i = 0; i < lados.size(); i++){

                l = lados.get(i);

                // Si el vertice indicado incide en el lado, se le suma 1 al grado.
                if (l.incide(l, v)) {
                    grado++;

                    // Si ademas el lado es bucle, se vuelve a sumar 1.
                    if (l.bucle(l, v)) {
                        grado++;
                    }
                }
            }
            return grado;   
        } else {

            // Si el vertice no se encuentra en el grafo, error.
            String msg = "Elemento inexistente. El vertice con ID " + String.valueOf(id) +
                " no se encuentra en el grafo.";
            throw new NoSuchElementException(msg);
        }
    }


    /** Metodo que dado un vertice, retorna un arreglo con sus adyacencias.
    * @param Grafo informacion del grafo.
    * @param int ID del vertice.
    * @return ArrayList adyacencias del vertice.
    */
    public ArrayList adyacentes(Grafo g, int id)throws NoSuchElementException {

        // Verificamos que efectivamente el vertice se encuentre en el grafo.
        if (g.estaVertice(g, id)) {

            ArrayList adyacencias = new ArrayList();
            ArrayList<Lado> lados = g.lados(g);
            Vertice v = g.obtenerVertice(g, id);
            Vertice u, w;   // Vertices incidentes al i-esimo lado.
            Lado l;     // i-esimo lado.

            // Verificamos cada lado del grafo.
            for (int i = 0; i < lados.size(); i++){

                l = lados.get(i);

                // Verificamos que el vertice indicado incida en el lado.
                if (l.incide(l, v)) {
                    u = l.vertices(l)[0];
                    w = l.vertices(l)[1];

                    // Si el otro extremo del lado no se encuentra en las adyacencias,
                    // lo agregamos.
                    if ( (u.getId(u) == v.getId(v)) && !(adyacencias.contains(w)) ){
                        adyacencias.add(w);       
                    } else if (! adyacencias.contains(u)) {
                        adyacencias.add(u);
                    }
                }
            }

            return adyacencias;
        } else {

            // Si el vertice no se encuentra en el grafo, error.
            String msg = "Elemento inexistente. El vertice con ID " + String.valueOf(id) +
                " no se encuentra en el grafo.";
            throw new NoSuchElementException(msg);
        }
    }


    /** Metodo que dado un vertice, retorna un arreglo con sus lados incidentes.
    * @param Grafo informacion del grafo.
    * @param int ID del vertice.
    * @return ArrayList lados incidentes del vertice.
    */
    public ArrayList incidentes(Grafo g, int id)throws NoSuchElementException {

        // Verificamos que efectivamente el vertice se encuentre en el grafo.
        if (g.estaVertice(g, id)) {

            ArrayList incidencias = new ArrayList();
            ArrayList<Lado> lados = g.lados(g);
            Vertice v = g.obtenerVertice(g, id);
            Lado l;     // i-esimo lado.

            // Verificamos cada lado del grafo.
            for (int i = 0; i < lados.size(); i++){

                l = lados.get(i);
                if (l.incide(l, v)) {
                    incidencias.add(l);
                }
            }

            return incidencias;
        } else {

            // Si el vertice no se encuentra en el grafo, error.
            String msg = "Elemento inexistente. El vertice con ID " + String.valueOf(id) +
                " no se encuentra en el grafo.";
            throw new NoSuchElementException(msg);
        }
    }


    /** Metodo base que retorna un clon del grafo actual.
    Su implementacion queda a cargo de las clases que la heredan.
    * @param Grafo informacion del grafo.
    * @return Grafo informacion del grafo.
    */
    public Grafo clone(Grafo g){ return this; }


    /** Metodo que elimina todos los datos del grafo, dejandolo en un grafo vacio. */
    protected void flush(){
        this.numV = 0;     // Numero de vertices
        this.numL = 0;     // Numero de de lados.
        this.listVert = new ArrayList();   // Lista de vertices.
        this.listLados = new ArrayList();     // Lista de lados.
    }


    /** Funcion que retorna un string con todos los atributos del grafo..
	* @param Grafo informacion del grafo..
    * @return String contiene los atributos de G.
    */
    public String toString(Grafo g) {
        ArrayList<Vertice> vertices = g.vertices(g);
        ArrayList<Lado> lados = g.lados(g);

        // Obtenemos la informacion de cada vertice.
        String stringVertices = "";
        for (int i = 0; i < vertices.size(); i++){
            stringVertices += vertices.get(i).toString(vertices.get(i));
        }

        // Obtenemos la informacion de cada lado.
        String stringLados = "";
        for (int i = 0; i < lados.size(); i++){
            stringLados += lados.get(i).toString(lados.get(i));
        }


        String out = "\033[0;1mVERTICES:\033[0;0m \n" +
					 stringVertices +
					 "\033[0;1mLADOS:\033[0;0m \n" +
					 stringLados;

        return out;
    }

    /** Clase que crea la excepcion para informar sobre un vertice inexistente.
    * @param String mensaje que se indicara al conseguir el error.
    */
    public class NoSuchElementException extends Exception {
        public NoSuchElementException(String msg) {
            super (msg);
        }
    }
}