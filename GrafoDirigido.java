import java.util.*;
import java.io.*;

/** Clase que permite representar un grafo dirigido, extendido
de la clase Grafo.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
public class GrafoDirigido extends Grafo{

    public boolean crearGrafo(String file)throws IOException { 
        String stringGraph = this.readGraph(file);
        String[] lines = stringGraph.split(System.getProperty("line.separator"));

        int foo;

        if (lines[0].equals("D")){
            //GrafoDirigido graph = new GrafoDirigido();
            foo=1;
        } else if (lines[0].equals("N")){
            //GrafoDirigido graph = new GrafoDirigido();
            return false;
        } else {
            return false;
        }

        int numVertices = (int) Integer.parseInt(lines[1].substring(0, lines[1].length()));
        int numLados = (int) Integer.parseInt(lines[2].substring(0, lines[1].length()));

        int id, posI, posF;
        String name;
        double x, y, p;
        for (int i = 3; i < numVertices + 3; i++){
            posF = lines[i].indexOf(" ");
            id = (int) Integer.parseInt(lines[i].substring(0, posF));

            posI = posF+1;
            posF = lines[i].indexOf(" ", posI);
            name = lines[i].substring(posI, posF);

            posI = posF+1;
            posF = lines[i].indexOf(" ", posI);
            x = (double) Double.valueOf(lines[i].substring(posI, posF));

            posI = posF+1;
            posF = lines[i].indexOf(" ", posI);
            y = (double) Double.valueOf(lines[i].substring(posI, posF));

            posI = posF+1;
            posF = lines[i].length();
            p = (double) Double.valueOf(lines[i].substring(posI, posF));

            agregarVertice(this, id, name, x, y, p);
        }

        int id1, id2, type;
        Vertice u, v;
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

            //agregarArista(this, u, v, type, p);
        }

        return true;
    }

    public boolean estaArco(GrafoDirigido g, int vi, int vf, int tipo){
        ArrayList<Arco> arcos = g.lados(g);
        Arco a;
        Vertice pi, pf;

        for (int i = 0; i < arcos.size(); i++){
            a = arcos.get(i);
            pi = a.getExtremoInicial(a);
            pf = a.getExtremoFinal(a);
            if ( (vi == pi.getId(pi)) && (vf == pf.getId(pf)) && (a.getTipo(a) == tipo) ){
                return true;
            }
        }
        return false;

    }

    public boolean agregarArco(GrafoDirigido g, Vertice vi, Vertice vf, int tipo, double peso){
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

    public boolean agregarArco(GrafoDirigido g, Arco a) {
        Vertice vi = a.getExtremoInicial(a);
        Vertice vf = a.getExtremoFinal(a);

        return g.agregarArco(g, vi, vf, a.getTipo(a), a.getPeso(a));
    }

    public boolean eliminarArco(GrafoDirigido g, int id) {
        ArrayList<Arco> arcos = g.lados(g);
        Arco a;
        Vertice vi, vf;

        for (int i = 0; i < arcos.size(); i++){
            a = arcos.get(i);
            vi = a.getExtremoInicial(a);
            vf = a.getExtremoFinal(a);
            if (  g.estaArco(g, vi.getId(vi), vf.getId(vf), a.getTipo(a))  ) {
                g.listLados.remove(i);
                g.numL--;
                return true;
            }
        }
        return false;
    }

    public Arco obtenerArco(GrafoDirigido g, int vi, int vf, int tipo) {
        ArrayList<Arco> arcos = g.lados(g);
        Arco a;
        Vertice pi, pf;

        for (int i = 0; i < arcos.size(); i++){
            a = arcos.get(i);
            pi = a.getExtremoInicial(a);
            pf = a.getExtremoFinal(a);
            if ( (vi == pi.getId(pi)) && (vf == pf.getId(pf)) && (a.getTipo(a) == tipo) ){
                return a;
            }
        }
        System.out.println("Vertice no encontrado.");
        pi = new Vertice(0, "", 0, 0, 0);
        pf = new Vertice(0, "", 0, 0, 0);
        return new Arco(pi, pf, 0, 0);
    }

    public ArrayList<Vertice> predecesores(GrafoDirigido g, int id) {
        if (g.estaVertice(g, id)) {
            ArrayList<Vertice> predecesores = new ArrayList();
            ArrayList<Arco> arcos = g.lados(g);
            Arco a;
            Vertice wf;
            for (int i = 0; i < arcos.size(); i++){
                a = arcos.get(i);
                wf = a.getExtremoFinal(a);
                if (wf.getId(wf) == id){
                    predecesores.add(a.getExtremoInicial(a));
                }
            }
            return predecesores;
        } else {
            System.out.println("NO se encontro el vertice.");
            return new ArrayList();
        }
    }

    public ArrayList<Vertice> sucesores(GrafoDirigido g, int id) {
        if (g.estaVertice(g, id)) {
            ArrayList<Vertice> sucesores = new ArrayList();
            ArrayList<Arco> arcos = g.lados(g);
            Arco a;
            Vertice wi;
            for (int i = 0; i < arcos.size(); i++){
                a = arcos.get(i);
                wi = a.getExtremoInicial(a);
                if (wi.getId(wi) == id){
                    sucesores.add(a.getExtremoFinal(a));
                }
            }
            return sucesores;
        } else {
            System.out.println("NO se encontro el vertice.");
            return new ArrayList();
        }
    }

    public int gradoInterior(GrafoDirigido g, int id) {
        ArrayList predecesores = g.predecesores(g, id);
        return predecesores.size();
    }

    public int gradoExterior(GrafoDirigido g, int id) {
        ArrayList sucesores = g.sucesores(g, id);
        return sucesores.size();
    }

    public GrafoDirigido clone(GrafoDirigido g){ 
        GrafoDirigido newG = new GrafoDirigido();
        ArrayList<Vertice> vertices = g.vertices(g);
        ArrayList<Arco> arcos = g.lados(g);
        Arco a;
        Vertice v;

        for (int i = 0; i < arcos.size(); i++){
            a = arcos.get(i);
            newG.agregarArco(g, a);
        }

        for (int i = 0; i < vertices.size(); i++){
            v = vertices.get(i);
            newG.agregarVertice(g, v);
        }

        return newG;
    }

}