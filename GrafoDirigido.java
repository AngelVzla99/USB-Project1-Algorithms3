import java.util.*;
import java.io.*;

/** Clase que permite representar un grafo dirigido, extendido
de la clase Grafo.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public class GrafoDirigido extends Grafo{
        
    /** Metodo actualiza la informacion del grafo actual dado los datos de un archivo. 
    *@param String direccion del archivo, incluyendo su nombre.
    * @return Boolean que indica si la lectura se hizo con exito.
    */
    public boolean crearGrafo(String file)throws IOException,
        NumberFormatException,
        NoSuchElementException  { 
        
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
        if (! lines[0].equals("D")){
            String msg = "Error de formato. No se indico que el grafo debe ser dirigido. Si " +
            "desea representar un grafo no dirigido, considere usar la clase GrafoNoDirigido.";
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

        // ID de los vertices incidentes de un arco y el tipo de arco.
        int id1, id2, type;
        // Informacion de los vertices incidentes al arco.
        Vertice vi, vf;
        // Verificamos las lineas desde el final de los vertices indicados
        for (int i = numVertices + 3; i < numVertices + numLados + 3; i++){

            // Los siguientes datos deben ser Int o Doubles.
            // Los ID's de los vertices de los arcos deben corresponder a uno de los vertices
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
                vi = obtenerVertice(this, id1);

                posI = posF+1;
                posF = lines[i].indexOf(" ", posI);
                // Si no se consigue un espacio en blanco, error.
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                id2 = (int) Integer.parseInt(lines[i].substring(posI, posF));
                vf = obtenerVertice(this, id2);

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

                // Si se indica el mismo arco mas de una vez.
                if (! agregarArco(this, vi, vf, type, p) ) {
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato. Se indico " +
                    "un arco con los mismos datos anteriormente.";
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
                "de que los ID de los vertices incidentes al arco corresponde a uno de " +
                "los ID's indicados para los vertices anteriormente.";
                System.out.println(msg);
                return false;
            }
        }

        // Si pasas las casi 20 excepciones... fino!
        return true;
    }


    /** Metodo que verifica si se encuentra una arista en un grafo no dirigido.
    *@param GrafoDirigido informacion del grafo.
    *@param int ID del vertice incidente inicial.
    *@param int ID del vertice incidente final.
    *@param int tipo de arista.
    * @return Boolean que indica si la arista se encuentra en el grafo.
    */
    public boolean estaArco(GrafoDirigido g, int vi, int vf, int tipo){

        ArrayList<Arco> arcos = g.lados(g);
        Vertice pi, pf;     // Vertices incidentes en el i-esimo arco.
        Arco a;     // i-esimo arco.

        // Revisamos cada arco del grafo.
        for (int i = 0; i < arcos.size(); i++){
            a = arcos.get(i);
            pi = a.getExtremoInicial(a);
            pf = a.getExtremoFinal(a);

            // Verificamos si el ID de los vertices incidentes corresponden con el ID
            // de los vertices dados, ademas de verificar el tipo de la arista.
            if ( (vi == pi.getId(pi)) && (vf == pf.getId(pf)) && (a.getTipo(a) == tipo) ){
                return true;
            }
        }
        return false;
    }


    /** Metodo que permite agregar una arista al grafo.
    *@param GrafoDirigido informacion del grafo.
    *@param Vertice informacion del vertice incidente 1.
    *@param Vertice informacion del vertice incidente 2.
    *@param int tipo de arista.
    *@param double peso de la arista.
    * @return Boolean que indica si la arista se agrego correctamente al grafo.
    */
    public boolean agregarArco(GrafoDirigido g, Vertice vi, Vertice vf, int tipo, double peso){

        // Si el arco ya se encuentra en el grafo, entonces no se puede agregar.
        if (g.estaArco(g, vi.getId(vi), vf.getId(vf), tipo)){
            System.out.println("Ya hay un vertice con ese ID.");
            return false;
        } else {
            Arco a = new Arco(vi, vf, tipo, peso);
            g.listLados.add(a);
            g.numL++;
            return true;
        }
    }


    /** Metodo que permite agregar una arista al grafo.
    *@param GrafoDirigido informacion del grafo.
    *@param Arista informacion de la arista.
    * @return Boolean que indica si la arista se agrego correctamente al grafo.
    */
    public boolean agregarArco(GrafoDirigido g, Arco a) {

        Vertice vi = a.getExtremoInicial(a);
        Vertice vf = a.getExtremoFinal(a);

        return g.agregarArco(g, vi, vf, a.getTipo(a), a.getPeso(a));
    }


    /** Metodo que permite eliminar un arco del grafo.
    *@param GrafoDirigido informacion del grafo.
    *@param Arco informacion de la arco. 
    * @return Boolean que indica si el arco sel elimino correctamente al grafo.
    */
    public boolean eliminarArco(GrafoDirigido g, Arco a) {

        ArrayList<Arco> aristas = g.lados(g);
        // Vertices incidentes al arco indicada.
        Vertice pi = a.getExtremoInicial(a);
        Vertice pf = a.getExtremoFinal(a);
        // Vertices del i-esimo arco.
        Vertice vi, vf;
        // i-esimo arco.
        Arco b;

        // Verificamos la informacion de cada arco del grafo.
        for (int i = 0; i < aristas.size(); i++){
            b = aristas.get(i);
            vi = b.getExtremoInicial(b);
            vf = b.getExtremoFinal(b);

            // Si los datos del i-esimo arco son iguales a los del arco indicado,
            // se elimina.
            if ( (vi.getId(vi) == pi.getId(pi)) && 
                 (vf.getId(vf) == pf.getId(pf)) && 
                 (a.getTipo(a) == b.getTipo(b)) ) {
                g.listLados.remove(i);
                g.numL--;
                return true;
            }
        }

        // Si ninguna arista coincide, se retorna false.
        return false;
    }


    /** Metodo que retorna un arco de un grafo dado su ID.
    *@param GrafoDirigido informacion del grafo.
    *@param int ID del vertice incidente inicial.
    *@param int ID del vertice incidente final.
    *@param int tipo de arco.
    * @return Arco con la informacion del arco.
    */
    public Arco obtenerArco(GrafoDirigido g, int vi, int vf, int tipo)
        throws NoSuchElementException {

        ArrayList<Arco> arcos = g.lados(g);
        // Vertices incidentes del i-esimo arco.
        Vertice pi, pf;
        // i-esimo arco.
        Arco a;

        // Verificamos la informacion de cada arista del grafo.
        for (int i = 0; i < arcos.size(); i++){
            a = arcos.get(i);
            pi = a.getExtremoInicial(a);
            pf = a.getExtremoFinal(a);

            // Si los datos de la i-esima arista son iguales a los datos indicados,
            // se retorna.
            if ( (vi == pi.getId(pi)) && 
                 (vf == pf.getId(pf)) && 
                 (a.getTipo(a) == tipo) ){
                return a;
            }
        }
        
        // Si no encontramos ninguna arista, error
        String msg = "Elemento inexistente. EL arco indicado no existe.";
        throw new NoSuchElementException(msg);
    }


    /** Metodo que retorna los predecesores de un vertice.
    *@param GrafoDirigido informacion del grafo.
    *@param int ID del vertice.
    * @return ArrayList<Vertice> arreglo con la informacion de los vertices predecesores.
    */
    public ArrayList<Vertice> predecesores(GrafoDirigido g, int id)
        throws NoSuchElementException {

        // Verificamos que efectivamente el vertice se encuentre en el grafo.
        if (g.estaVertice(g, id)) {

            ArrayList<Vertice> predecesores = new ArrayList();
            ArrayList<Arco> arcos = g.lados(g);
            // Vertice final del i-esimo arco.
            Vertice wf;
            // i-esimo arco.
            Arco a;

            // Revisamos cada arco del grafo.
            for (int i = 0; i < arcos.size(); i++){

                a = arcos.get(i);
                wf = a.getExtremoFinal(a);

                // Si el ID del vertice final del arco es igual al ID indicado,
                // agregamos el vertice inicial del arco a la lista de predecesores.
                if (wf.getId(wf) == id){
                    predecesores.add(a.getExtremoInicial(a));
                }
            }
            return predecesores;

        } else {

            // Si el vertice no se encuentra en el grafo, error.
            String msg = "Elemento inexistente. El vertice con ID " + String.valueOf(id) +
                " no se encuentra en el grafo.";
            throw new NoSuchElementException(msg);
        }
    }

    /** Metodo que retorna los sucesores de un vertice.
    *@param GrafoDirigido informacion del grafo.
    *@param int ID del vertice.
    * @return ArrayList<Vertice> arreglo con la informacion de los vertices sucesores.
    */
    public ArrayList<Vertice> sucesores(GrafoDirigido g, int id)
        throws NoSuchElementException {

        // Verificamos que efectivamente el vertice se encuentre en el grafo.
        if (g.estaVertice(g, id)) {

            ArrayList<Vertice> sucesores = new ArrayList();
            ArrayList<Arco> arcos = g.lados(g);
            // Vertice inicial del i-esimo arco.
            Vertice wi;
            // i-esimo arco.
            Arco a;

            // Revisamos cada arco del grafo.
            for (int i = 0; i < arcos.size(); i++){

                a = arcos.get(i);
                wi = a.getExtremoInicial(a);

                // Si el ID del vertice inicial del arco es igual al ID indicado,
                // agregamos el vertice final del arco a la lista de sucesores.
                if (wi.getId(wi) == id){
                    sucesores.add(a.getExtremoFinal(a));
                }
            }
            return sucesores;
        } else {
            
            // Si el vertice no se encuentra en el grafo, error.
            String msg = "Elemento inexistente. El vertice con ID " + String.valueOf(id) +
                " no se encuentra en el grafo.";
            throw new NoSuchElementException(msg);
        }
    }


    /** Metodo que retorna el grado interior de un vertice.
    *@param GrafoDirigido informacion del grafo.
    *@param int ID del vertice.
    * @return int grado interior del vertice.
    */
    public int gradoInterior(GrafoDirigido g, int id)
        throws NoSuchElementException {

        // Simplemente contamos el numero de predecesores del vertice.
        ArrayList predecesores = g.predecesores(g, id);
        return predecesores.size();
    }


    /** Metodo que retorna el grado exterior de un vertice.
    *@param GrafoDirigido informacion del grafo.
    *@param int ID del vertice.
    * @return int grado exterior del vertice.
    */
    public int gradoExterior(GrafoDirigido g, int id)
        throws NoSuchElementException {

        // Simplemente contamos el numero de sucesores del vertice.
        ArrayList sucesores = g.sucesores(g, id);
        return sucesores.size();
    }


    /** Metodo que permite clonar la informacion de un grafo dirigido.
    *@param GrafoDirigido informacion del grafo.
    * @return GrafoDirigido informacion del grafo. 
    */
    public GrafoDirigido clone(GrafoDirigido g)throws NoSuchElementException{ 

        // Creamos un nuevo grafo dirigido donde almacenaremos la informacion.
        GrafoDirigido newG = new GrafoDirigido();
        // Obtenemos los vertices y las aristas del grafo indicado.
        ArrayList<Vertice> vertices = g.vertices(g);
        ArrayList<Arco> arcos = g.lados(g);
        // i-esimo arco e i-esimo vertice junto a sus respectivos clones.
        Arco a, b;
        Vertice u, v;

        // Obtenemos la informacion de cada vertice.
        for (int i = 0; i < vertices.size(); i++){
            u = vertices.get(i);
            v = new Vertice(u.getId(u), u.getNombre(u), u.getX(u), u.getY(u), u.getPeso(u));
            newG.agregarVertice(newG, v);
        }

        // Obtenemos la informacion de cada arista.
        for (int i = 0; i < arcos.size(); i++){
            a = arcos.get(i);
            u = a.getExtremoInicial(a);
            v = a.getExtremoFinal(a);

            // Obtenemos los nuevos vertices incidentes
            u = newG.obtenerVertice(newG, u.getId(u));
            v = newG.obtenerVertice(newG, v.getId(v));

            // Creamos la nueva arista y la agregamos.
            b = new Arco(u, v, a.getTipo(a), a.getPeso(a));
            newG.agregarArco(newG, a);
        }

        return newG;
    }

}