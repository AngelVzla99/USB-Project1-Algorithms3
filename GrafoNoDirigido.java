import java.util.*;
import java.io.*;

/** Clase que permite representar un grafo no dirigido, extendido
de la clase Grafo.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public class GrafoNoDirigido extends Grafo{

    /** Metodo actualiza la informacion del grafo actual dado los datos de un archivo. 
    @param String direccion del archivo, incluyendo su nombre.
    @return Boolean que indica si la lectura se hizo con exito.
    */
    public boolean crearGrafo(String file)throws IOException, NumberFormatException { 

        // Leemos el archivo y lo separamos por linea
        String stringGraph = this.readGraph(file);
        String[] lines = stringGraph.split(System.getProperty("line.separator"));

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

        try {
            int numVertices = (int) Integer.parseInt(lines[1].substring(0, lines[1].length()));
            int numLados = (int) Integer.parseInt(lines[2].substring(0, lines[1].length()));
        } catch (NumberFormatException e) {
            String msg = "Error de formato. La segunda y tercera linea del archivo deben ser " +
            "numeros enteros que indiquen el numero de vertices y aristas respectivamente.";
            System.out.println(msg); 
            return false;
        }

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
            try {
                posF = lines[i].indexOf(" ");
                id = (int) Integer.parseInt(lines[i].substring(0, posF));
            } catch (NumberFormatException e) {
                String msg = "Linea " + String.valueOf(i) + ": Error de formato. El id del " + 
                "vertice debe ser un numero natural.";
                System.out.println(msg);
                return false;
            }

            posI = posF+1;
            posF = lines[i].indexOf(" ", posI);
            if (posF == -1){
                String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                System.out.println(msg);
                return false;
            }
            name = lines[i].substring(posI, posF);

            try {

                posI = posF+1;
                posF = lines[i].indexOf(" ", posI);
                if (posF == -1){
                    String msg = "Linea " + String.valueOf(i) + ": Error de formato.";
                    System.out.println(msg);
                    return false;
                }
                x = (double) Double.valueOf(lines[i].substring(posI, posF));

                posI = posF+1;
                posF = lines[i].indexOf(" ", posI);
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

            agregarVertice(this, id, name, x, y, p);
        }


        // ID de los vertices incidentes de un arista y el tipo de arista.
        int id1, id2, type;
        // Informacion de los vertices incidentes a la arista.
        Vertice u, v;
        // Verificamos
        for (int i = numVertices + 3; i < numVertices + numLados + 3; i++){
            posF = lines[i].indexOf(" ");
            id1 = (int) Integer.parseInt(lines[i].substring(0, posF));
            u = obtenerVertice(this, id1);

            posI = posF+1;
            posF = lines[i].indexOf(" ", posI);
            id2 = (int) Integer.parseInt(lines[i].substring(posI, posF));
            v = obtenerVertice(this, id2);

            posI = posF+1;
            posF = lines[i].indexOf(" ", posI);
            type = (int) Integer.parseInt(lines[i].substring(posI, posF));

            posI = posF+1;
            posF = lines[i].length();
            p = (double) Double.valueOf(lines[i].substring(posI, posF));

            agregarArista(this, u, v, type, p);
        }

        return true;
    }

    public boolean estaArista(GrafoNoDirigido g, int u, int v, int tipo){
        ArrayList<Arista> aristas = g.lados(g);
        Arista a;
        Vertice p, q;

        for (int i = 0; i < aristas.size(); i++){
            a = aristas.get(i);
            p = a.getExtremo1(a);
            q = a.getExtremo2(a);
            if ( (u == p.getId(p)|| u == q.getId(q)) &&
                 (v == p.getId(p)|| v == q.getId(q)) &&
                 (a.getTipo(a) == tipo)  ){
                return true;
            }
        }
        return false;

    }

    public boolean agregarArista(GrafoNoDirigido g, Vertice u, Vertice v, int tipo, double peso){
        if (g.estaArista(g, u.getId(u), v.getId(v), tipo)){
            System.out.println("Ya hay un vertice con ese ID.");
            return false;
        } else {
            Arista a = new Arista(u, v, tipo, peso);
            g.listLados.add(a);
            g.numL++;
            return true;
        }
    }

    public boolean agregarArista(GrafoNoDirigido g, Arista a) {
        Vertice u = a.getExtremo1(a);
        Vertice v = a.getExtremo2(a);

        return g.agregarArista(g, u, v, a.getTipo(a), a.getPeso(a));
    }

    public boolean eliminarArista(GrafoNoDirigido g, int id) {
        ArrayList<Arista> aristas = g.lados(g);
        Arista a;
        Vertice u, v;

        for (int i = 0; i < aristas.size(); i++){
            a = aristas.get(i);
            u = a.getExtremo1(a);
            v = a.getExtremo2(a);
            if (  g.estaArista(g, u.getId(u), v.getId(v), a.getTipo(a))  ) {
                g.listLados.remove(i);
                g.numL--;
                return true;
            }
        }
        return false;
    }

    public Arista obtenerArista(GrafoNoDirigido g, int u, int v, int tipo) {
        ArrayList<Arista> aristas = g.lados(g);
        Arista a;
        Vertice p, q;

        for (int i = 0; i < aristas.size(); i++){
            a = aristas.get(i);
            p = a.getExtremo1(a);
            q = a.getExtremo2(a);
            if ( (u == p.getId(p)|| u == q.getId(q)) &&
                 (v == p.getId(p)|| v == q.getId(q)) &&
                 (a.getTipo(a) == tipo)  ){
                return a;
            }
        }
        System.out.println("Vertice no encontrado.");
        p = new Vertice(0, "", 0, 0, 0);
        q = new Vertice(0, "", 0, 0, 0);
        return new Arista(p, q, 0, 0);
    }

    public GrafoNoDirigido clone(GrafoNoDirigido g){ 
        GrafoNoDirigido newG = new GrafoNoDirigido();
        ArrayList<Vertice> vertices = g.vertices(g);
        ArrayList<Arista> aristas = g.lados(g);
        Arista a;
        Vertice v;

        for (int i = 0; i < aristas.size(); i++){
            a = aristas.get(i);
            newG.agregarArista(g, a);
        }

        for (int i = 0; i < vertices.size(); i++){
            v = vertices.get(i);
            newG.agregarVertice(g, v);
        }

        return newG;
    }
}