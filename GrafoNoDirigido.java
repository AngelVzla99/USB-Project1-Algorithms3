import java.util.*;
import java.io.*;

/** Clase que permite representar un grafo no dirigido, extendido
de la clase Grafo.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public class GrafoNoDirigido extends Grafo{

    /** Metodo actualiza la informacion del grafo actual dado los datos de un archivo. 
    * @param String direccion del archivo, incluyendo su nombre.
    * @return Boolean que indica si la lectura se hizo con exito.
    */
    public boolean crearGrafo(String file)throws IOException, 
        NumberFormatException,
        NoSuchElementException { 

        // Dejamos al grafo actual vacio.
        this.flush();

        String stringGraph;
        String[] lines;
        try {
            // Leemos el archivo y lo separamos por linea
            stringGraph = this.readGraph(file);
            lines = stringGraph.split(System.getProperty("line.separator"));
        } catch (IOException e) {
            String msg = "No se puede leer el archivo indicado.";
            System.out.println(msg); 
            return false;
        }

        // Si el archivo tiene menos de 3 lineas, error.
        if (lines.length < 3) {
            String msg = "Error de formato. El archivo debe contener al menos 3 lineas " + 
                "indicando si es dirigido o no dirigido, numero de vertices y numero de lados.";
            System.out.println(msg); 
            return false;
        }

        // Si la primera linea tiene mas de 2 caracteres, error.
        if ( lines[0].length() > 2){
            String msg = "Error de formato. La primera linea del archivo debe contener unicamente " +
            "un caracter: 'D' para indicar que es dirigido o 'N' para indicar que es no dirigido.";
            System.out.println(msg); 
            return false;
        }

        // Si la primera linea no indica que el grafo es no dirigido, entonces
        // hay un error con los datos del archivo.
        if (! lines[0].equals("N")){
            String msg = "Error de formato. No se indico que el grafo debe ser no dirigido. Si " +
            "desea representar un grafo dirigido, considere usar la clase GrafoDirigido.";
            System.out.println(msg); 
            return false;
        }

        int numVertices, numLados;
        // La segunda y tercera linea deben contener Int.
        try {
            numVertices = (int) Integer.parseInt(lines[1].substring(0, lines[1].length()));
            numLados = (int) Integer.parseInt(lines[2].substring(0, lines[1].length()));
        } catch (NumberFormatException e) {
            String msg = "Error de formato. La segunda y tercera linea del archivo deben ser " +
            "numeros enteros que indiquen el numero de vertices y aristas respectivamente.";
            System.out.println(msg); 
            return false;
        }

        // El numero de lineas debe corresponder con lo indicado en el numero de vertices y lados.
        if (numVertices + numLados + 3 != lines.length) {
            String msg = "Error de formato. El numero de lineas del archivo debe corresponder " +
            "al numero de vertices mas el numero de lados mas 3.";
            System.out.println(msg); 
            return false;
        }

        // posI indica la posicion inicial de la data y posF la posicion final.
        int posI, posF;

        // Informacion de los vertices.
        int id;
        String name;
        double x, y, p;

        // Verificamos cada linea desde la cuarta hasta lo indicado por el numero de vertices.
        for (int i = 3; i < numVertices + 3; i++){

            // El ID de cada vertice debe ser un Int.
            try {

                posF = lines[i].indexOf(" ");
                // Si no se consigue un espacio en blanco, error.
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                id = (int) Integer.parseInt(lines[i].substring(0, posF));

            } catch (NumberFormatException e) {
                String msg = "Linea " + String.valueOf(i) + ": Error de formato. El id del " + 
                "vertice debe ser un numero natural.";
                System.out.println(msg);
                return false;
            }

            posI = posF+1;
            posF = lines[i].indexOf(" ", posI);
            // Si no se consigue un espacio en blanco, error.
            if (posF == -1){
                String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                System.out.println(msg);
                return false;
            }
            name = lines[i].substring(posI, posF);

            // Los siguientes datos deben ser Doubles.
            try {

                posI = posF+1;
                posF = lines[i].indexOf(" ", posI);
                // Si no se consigue un espacio en blanco, error.
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                x = (double) Double.valueOf(lines[i].substring(posI, posF));

                posI = posF+1;
                posF = lines[i].indexOf(" ", posI);
                // Si no se consigue un espacio en blanco, error.
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                y = (double) Double.valueOf(lines[i].substring(posI, posF));

                posI = posF+1;
                posF = lines[i].length();
                p = (double) Double.valueOf(lines[i].substring(posI, posF));

            } catch (NumberFormatException e) {
                String msg = "Linea " + String.valueOf(i) + ": Error de formato. Asegurese " +
                "de que las coordenadas y el peso indicados son Doubles.";
                System.out.println(msg);
                return false;
            }

            // Si se inidca un ID mas de una vez.
            if (! agregarVertice(this, id, name, x, y, p) ) {
                String msg = "Linea " + String.valueOf(i) + ": Error de formato. Se indico " +
                    "una vertice con el mismo ID anteriormente.";
                System.out.println(msg);
                return false;
            }
        }


        // ID de los vertices incidentes de un arista y el tipo de arista.
        int id1, id2, type;
        // Informacion de los vertices incidentes a la arista.
        Vertice u, v;
        // Verificamos las lineas desde el final de los vertices indicados
        for (int i = numVertices + 3; i < numVertices + numLados + 3; i++){

            // Los siguientes datos deben ser Int o Doubles.
            // Los ID's de los vertices de las aristas deben corresponder a uno de los vertices
            // anteriores.
            try {
                posF = lines[i].indexOf(" ");
                // Si no se consigue un espacio en blanco, error.
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                id1 = (int) Integer.parseInt(lines[i].substring(0, posF));
                u = obtenerVertice(this, id1);

                posI = posF+1;
                posF = lines[i].indexOf(" ", posI);
                // Si no se consigue un espacio en blanco, error.
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                id2 = (int) Integer.parseInt(lines[i].substring(posI, posF));
                v = obtenerVertice(this, id2);

                posI = posF+1;
                posF = lines[i].indexOf(" ", posI);
                // Si no se consigue un espacio en blanco, error.
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                type = (int) Integer.parseInt(lines[i].substring(posI, posF));

                posI = posF+1;
                posF = lines[i].length();
                p = (double) Double.valueOf(lines[i].substring(posI, posF));

                // Si se indica la misma arista mas de una vez.
                if (! agregarArista(this, u, v, type, p) ) {
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato. Se indico " +
                    "una arista con los mismos datos anteriormente.";
                    System.out.println(msg);
                    return false;
                }

            } catch (NumberFormatException e) {
                String msg = "Linea " + String.valueOf(i) + ": Error de formato. Asegurese " +
                "de que los datos del vertice son numeros.";
                System.out.println(msg);
                return false;
            } catch (NoSuchElementException e) {
                String msg = "Linea " + String.valueOf(i) + ": Error de formato. Asegurese " +
                "de que los ID de los vertices incidentes en la arista corresponde a uno de " +
                "los ID's indicados para los vertices anteriormente.";
                System.out.println(msg);
                return false;
            }
        }

        // Si pasas las casi 20 excepciones... fino!
        return true;
    }


    /** Metodo que verifica si se encuentra una arista en un grafo no dirigido.
    * @param GrafoNoDirigido informacion del grafo.
    * @param int ID del vertice incidente 1.
    * @param int ID del vertice incidente 2.
    * @param int tipo de arista.
    * @return Boolean que indica si la arista se encuentra en el grafo.
    */
    public boolean estaArista(GrafoNoDirigido g, int u, int v, int tipo){

        ArrayList<Arista> aristas = g.lados(g);
        Vertice p, q;  // Vertices incidentes de la i-esima arista.
        Arista a;   // i-esima arista

        // Revisamos cada arista del grafo
        for (int i = 0; i < aristas.size(); i++){
            a = aristas.get(i);
            p = a.getExtremo1(a);
            q = a.getExtremo2(a);

            // Verificamos si el ID de los vertices incidentes corresponden con el ID
            // de los vertices dados, ademas de verificar el tipo de la arista.
            if ( (u == p.getId(p)|| u == q.getId(q)) &&
                 (v == p.getId(p)|| v == q.getId(q)) &&
                 (a.getTipo(a) == tipo)  ){
                return true;
            }
        }
        return false;

    }


    /** Metodo que permite agregar una arista al grafo.
    * @param GrafoNoDirigido informacion del grafo.
    * @param Vertice informacion del vertice incidente 1.
    * @param Vertice informacion del vertice incidente 2.
    * @param int tipo de arista.
    * @param double peso de la arista.
    * @return Boolean que indica si la arista se agrego correctamente al grafo.
    */
    public boolean agregarArista(GrafoNoDirigido g, Vertice u, Vertice v, int tipo, double peso){

        // Si la arista ya se encuentra en el grafo, entonces no se puede agregar.
        if (g.estaArista(g, u.getId(u), v.getId(v), tipo)){
            return false;
        } else {
            Arista a = new Arista(u, v, tipo, peso);
            g.listLados.add(a);
            g.numL++;
            return true;
        }
    }


    /** Metodo que permite agregar una arista al grafo.
    * @param GrafoNoDirigido informacion del grafo.
    * @param Arista informacion de la arista.
    * @return Boolean que indica si la arista se agrego correctamente al grafo.
    */
    public boolean agregarArista(GrafoNoDirigido g, Arista a) {

        Vertice u = a.getExtremo1(a);
        Vertice v = a.getExtremo2(a);

        return g.agregarArista(g, u, v, a.getTipo(a), a.getPeso(a));
    }


    /** Metodo que permite eliminar una arista del grafo.
    * @param GrafoNoDirigido informacion del grafo.
    * @param Arista informacion de la arista. 
    * @return Boolean que indica si la arista se elimino correctamente del grafo.
    */
    public boolean eliminarArista(GrafoNoDirigido g, Arista a) {

        ArrayList<Arista> aristas = g.lados(g);
        // Vertices incidentes de la arista indicada.
        Vertice p = a.getExtremo1(a);
        Vertice q = a.getExtremo2(a);
        // Vertices de la i-esima arista.
        Vertice u, v;
        // i-esima arista.
        Arista b;

        // Verificamos la informacion de cada arista del grafo.
        for (int i = 0; i < aristas.size(); i++){
            b = aristas.get(i);
            u = b.getExtremo1(b);
            v = b.getExtremo2(b);

            // Si los datos de la i-esima arista son iguales a los de la arista indicada,
            // se elimina.
            if (  (p.getId(p) == u.getId(u) || p.getId(p) == v.getId(v))  &&
                  (q.getId(q) == u.getId(u) || q.getId(q) == v.getId(v)) &&
                  (b.getTipo(b) == a.getTipo(a))  ) {
                g.listLados.remove(i);
                g.numL--;
                return true;
            }
        }

        // Si ninguna arista coincide, se retorna false.
        return false;
    }


    /** Metodo que retorna una arista de un grafo dado su ID.
    * @param GrafoNoDirigido informacion del grafo.
    * @param int ID del vertice incidente 1.
    * @param int ID del vertice incidente 2.
    * @param int tipo de arista.
    * @return Arista con la informacion de la Arista.
    */
    public Arista obtenerArista(GrafoNoDirigido g, int u, int v, int tipo)
        throws NoSuchElementException {

        ArrayList<Arista> aristas = g.lados(g);
        // Vertices incidentes de la i-esima arista.
        Vertice p, q;
        // i-esima arista.
        Arista a;

        // Verificamos la informacion de cada arista del grafo.
        for (int i = 0; i < aristas.size(); i++){
            a = aristas.get(i);
            p = a.getExtremo1(a);
            q = a.getExtremo2(a);

            // Si los datos de la i-esima arista son iguales a los datos indicados,
            // se retorna.
            if ( (u == p.getId(p)|| u == q.getId(q)) &&
                 (v == p.getId(p)|| v == q.getId(q)) &&
                 (a.getTipo(a) == tipo)  ){
                return a;
            }
        }
        
        // Si no encontramos ninguna arista, error
        String msg = "Elemento inexistente. La arista indicada no existe.";
        throw new NoSuchElementException(msg);
    }


    /** Metodo que permite clonar la informacion de un grafo no dirigido.
    * @param GrafoNoDirigido informacion del grafo.
    * @return GrafoNoDirigido informacion del grafo. 
    */
    public GrafoNoDirigido clone(GrafoNoDirigido g)throws NoSuchElementException{
        
        // Creamos un nuevo grafo no dirigido donde almacenaremos la informacion.
        GrafoNoDirigido newG = new GrafoNoDirigido();
        // Obtenemos los vertices y las aristas del grafo indicado.
        ArrayList<Vertice> vertices = g.vertices(g);
        ArrayList<Arista> aristas = g.lados(g);
        // i-esima arista e i-esimo vertice junto a sus respectivos clones.
        Arista a, b;
        Vertice u, v;

        // Obtenemos la informacion de cada vertice.
        for (int i = 0; i < vertices.size(); i++){
            u = vertices.get(i);
            v = new Vertice(u.getId(u), u.getNombre(u), u.getX(u), u.getY(u), u.getPeso(u));
            newG.agregarVertice(g, v);
        }

        // Obtenemos la informacion de cada arista.
        for (int i = 0; i < aristas.size(); i++){
            a = aristas.get(i);
            u = a.getExtremo1(a);
            v = a.getExtremo2(a);

            // Obtenemos los nuevos vertices incidentes
            u = newG.obtenerVertice(newG, u.getId(u));
            v = newG.obtenerVertice(newG, v.getId(v));

            // Creamos la nueva arista y la agregamos.
            b = new Arista(u, v, a.getTipo(a), a.getPeso(a));
            newG.agregarArista(newG, b);
        }

        newG.toString(newG);
        g.toString(g);

        return newG;
    }
}