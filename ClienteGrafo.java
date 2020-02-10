import java.util.*;
import java.io.*;

/** Clase que permite interactuar con todas las duncionalidades
de los TADs a través de un menu.

* @author: Amin Arriaga 16-10072; Angel Garces 16-10400.
* @version: 09/02/2020
*/
class ClienteGrafo{
    public int menu(String msg, int n, int m)throws NumberFormatException,
        IOException {

        System.out.println(msg);
        boolean salirMenu = false;
        int action = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (! salirMenu ){
            try {
                action = (int) Integer.parseInt(br.readLine());

                if (action < 0 || action > n){
                    System.out.println("La accion indicada debe estar entre 1 y " + 
                        String.valueOf(n) + ".");
                } else {
                    salirMenu = true;
                }

            } catch( NumberFormatException e ) {
                System.out.println("La accion indicada debe ser un numero entero.");
            }
        }

        System.out.println("\033[H\033[2J");
        if (action != 0) {
            return action + m;
        }
        return action;
    }

    public static void main(String[] args)throws IOException, 
        NoSuchElementException,
        NumberFormatException {

        System.out.println("\033[H\033[2J");

        boolean salir = false;
        String msg, text;
        int action = 0;
        int n, id;
        String name;
        int u, v, tipo;
        Vertice U, V;
        double x, y, p;

        ClienteGrafo CG = new ClienteGrafo();

        ArrayList<GrafoDirigido> grafosD = new ArrayList();
        ArrayList<GrafoNoDirigido> grafosN = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        GrafoDirigido gD = new GrafoDirigido();
        GrafoNoDirigido gN = new GrafoNoDirigido();

        // Texto que se usara en el menu.
        String menuInicial = "Indique la accion que desea hacer:\n" +
            "\t\033[0;1m1.-\033[0;0m Listar grafos creados.\n" +
            "\t\033[0;1m2.-\033[0;0m Listar detalladamente un grafo.\n" +
            "\t\033[0;1m3.-\033[0;0m Crear grafo.\n" +
            "\t\033[0;1m4.-\033[0;0m Modificar un grafo.\n" +
            "\t\033[0;1m5.-\033[0;0m Eliminar un grafo.\n" +
            "\t\033[0;1m0.-\033[0;0m Salir\n";

        // Submenu: Listar
        String menuListarD = "Listar detalladamente un grafo:\n" +
            "\t\033[0;1m1.-\033[0;0m Dirigido.\n" +
            "\t\033[0;1m2.-\033[0;0m No dirigido.\n" +
            "\t\033[0;1m0.-\033[0;0m Regresar.\n";

        // Submenu: listar detalladamente2
        String menuListarD2 = "Indique el numero del grafo: ";

        // Submenu: Listar
        String menuListar = "Listar grafos:\n" +
            "\t\033[0;1m0.-\033[0;0m Regresar.\n" +
            "\t\033[0;1m1.-\033[0;0m Dirigidos.\n" +
            "\t\033[0;1m2.-\033[0;0m No dirigidos.\n";

        // Submenu: Crear
        String menuCrear = "Crear grafo:\n" +
            "\t\033[0;1m0.-\033[0;0m Regresar.\n" +
            "\t\033[0;1m1.-\033[0;0m Dirigido.\n" +
            "\t\033[0;1m2.-\033[0;0m No dirigido.\n";

        // Submenu: Crear2
        String menuCrear2 = "Crear grafo:\n" +
            "\t\033[0;1m0.-\033[0;0m Regresar.\n" +
            "\t\033[0;1m1.-\033[0;0m vacio.\n" +
            "\t\033[0;1m2.-\033[0;0m de un archivo.\n" +
            "\t\033[0;1m3.-\033[0;0m clonado.\n";

        // Submenu: File
        String menuFile = "Indique el nombre del archivo: ";

        // Submenu: Modificar
        String menuModificar = "Modificar un grafo:\n" +
            "\t\033[0;1m0.-\033[0;0m Regresar.\n" +
            "\t\033[0;1m1.-\033[0;0m Dirigido.\n" +
            "\t\033[0;1m2.-\033[0;0m No dirigido.\n";

        // Submenu: Clonar
        String menuClonar = "Indique el numero del grafo a clonar: ";

        // Submenu: Modificar2
        String menuModificar3 = "Indique la accion: \n" +
            "\t\033[0;1m0.-\033[0;0m Salir.\n" +
            "\t\033[0;1m1.-\033[0;0m Agregar vertice.\n" +
            "\t\033[0;1m2.-\033[0;0m Agregar arco.\n" +
            "\t\033[0;1m3.-\033[0;0m ELiminar vertice.\n" +
            "\t\033[0;1m4.-\033[0;0m Eliminar arco.\n";

        // Submenu: Eliminar
        String menuEliminar = "Elminiar grafo:\n" +
            "\t\033[0;1m0.-\033[0;0m Regresar.\n" +
            "\t\033[0;1m1.-\033[0;0m Dirigido.\n" +
            "\t\033[0;1m2.-\033[0;0m No dirigido.\n";

        // Submenu: Eliminar2
        String menuEliminar2 = "Indique el numero del grafo a eliminar: ";

        // Submenu: ModificarDirigido
        String menuModDir = "Indique como desea modificar el grafo:";

        // Submenu: Modificar

        while (! salir) {

            // MENU PRINCIPAL
            if (action == 0) {
                action = CG.menu(menuInicial, 5, 0);
                if (action == 0) {
                    System.out.println("Vuelva pronto!");
                    salir = true;
                }
            } 
                // MENU LISTAR
                else if (action == 1) { action = CG.menu(menuListar, 2, 5); } 
                    
                    // Listar los grafos dirigidos.
                    else if (action == 6) {
                        System.out.println("Mostrando grafos dirigidos: ");
                        for (int i = 0; i < grafosD.size(); i++) {
                            gD = grafosD.get(i);
                            msg = "\tGrafo numero " + String.valueOf(i) + ". Vertices: " + 
                                String.valueOf(gD.numeroDeVertices(gD)) +
                                ".\t Arcos: " + 
                                String.valueOf(gD.numeroDeLados(gD));

                            System.out.println(msg + "\n");
                        }
                        action = 0;
                    } 
                    // Listar los grafos no dirigidos.
                    else if (action == 7) {
                        System.out.println("Mostrando grafos no dirigidos: ");
                        for (int i = 0; i < grafosN.size(); i++) {
                            gN = grafosN.get(i);
                            msg = "\tGrafo numero " + String.valueOf(i) + ". Vertices: " + 
                                String.valueOf(gD.numeroDeVertices(gD)) +
                                ".\t Aristas: " + 
                                String.valueOf(gD.numeroDeLados(gD));

                            System.out.println(msg + "\n");
                        }
                        action = 0;
                    } 
                
                // MENU LISTAR DETALLADAMENTE
                else if (action == 2) { action = CG.menu(menuListarD, 2, 7); } 

                    // Listar detalladamente un grafo dirigido.
                    else if (action == 8) {
                        System.out.println(menuListarD2);
                        n = (int) Integer.parseInt(br.readLine());
                        if ((n >= grafosD.size()) || (n < 0)){
                            System.out.println("El grafo indicado no existe.");
                        } else {
                            System.out.println(grafosD.get(n).toString(grafosD.get(n)));
                        }
                        action = 0;
                    } 
                    // Listar detalladamente un grafo no dirigido.
                    else if (action == 9) {
                        System.out.println(menuListarD2);
                        n = (int) Integer.parseInt(br.readLine());
                        if (n >= grafosN.size()){
                            System.out.println("El grafo indicado no existe.");
                        } else {
                            System.out.println(grafosN.get(n).toString(grafosN.get(n)));
                        }
                        action = 0;
                    } 
                
                // MENU CREAR
                else if (action == 3) { action = CG.menu(menuCrear, 2, 9); }

                    // MENU CREAR DIRIGIDO 
                    else if (action == 10) { action = CG.menu(menuCrear2, 3, 15); } 

                        // Crear un grafo dirigido vacio.
                        else if (action == 16) {
                            gD = new GrafoDirigido();
                            grafosD.add(gD);
                            action = 0;
                        } 
                        // Crear un grafo dirigido desde un archivo.
                        else if (action == 17) {
                            System.out.println(menuFile);
                            text = br.readLine();
                            gD = new GrafoDirigido();

                            try {
                                gD.crearGrafo(text);
                            } catch(Exception e){
                                System.out.println(e.getMessage());
                            }

                            grafosD.add(gD);
                            action = 0;
                        }
                        // Crear un grafo dirigido clonado.
                        else if (action == 18){
                            System.out.println(menuClonar);
                            n = (int) Integer.parseInt(br.readLine());
                            if ((n >= 0) && (n <= grafosD.size()) ){
                                gD = grafosD.get(n);
                                try {
                                    grafosD.add(gD.clone(gD));
                                } catch(Exception e){
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("El grafo indicado no existe");
                            }
                            action = 0;
                        }
                    
                    // MENU CREAR NO-DIRIGIDO
                    else if (action == 11) { action = CG.menu(menuCrear2, 3, 18); } 

                        // Crear un grafo no dirigido vacio.
                        else if (action == 19) {
                            gN = new GrafoNoDirigido();
                            grafosN.add(gN);
                            action = 0;
                        } 
                        // Crear un grafo no dirigido desde un archivo.
                        else if (action == 20) {
                            System.out.println(menuFile);
                            text = br.readLine();
                            gN = new GrafoNoDirigido();
                            try {
                                gN.crearGrafo(text);
                            } catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            grafosN.add(gN);
                            action = 0;
                        }
                        // Crear un grafo no dirigido clonado.
                        else if (action == 21){
                            System.out.println(menuClonar);
                            n = (int) Integer.parseInt(br.readLine());
                            if ((n >= 0) && (n <= grafosD.size()) ){
                                gN = grafosN.get(n);
                                try {
                                    grafosN.add(gN.clone(gN));
                                } catch(Exception e){
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("El grafo indicado no existe");
                            }
                            action = 0;
                        }
                // MODIFICAR
                else if (action == 4) { action = CG.menu(menuModificar, 2, 11); } 
                    // MENU MODIFICAR DIRIGIDO
                    else if (action == 12) { 
                        System.out.println(menuListarD2);
                        n = (int) Integer.parseInt(br.readLine());
                        if ((n >= grafosD.size()) || (n < 0)){
                            System.out.println("El grafo indicado no existe.");
                            action = 0;
                        } else {
                            gD = grafosD.get(n);
                            action = 22;
                        }
                    }

                        else if (action == 22) {
                            while (action == 22) {

                                action = CG.menu(menuModificar3, 4 ,23);
                                System.out.println("A");

                                if (action == 24) {
                                    System.out.println("Indique el ID, nombre, coordenadas x, y;" +
                                    " y el peso del vertice");
                                    try {
                                        id = (int) Integer.parseInt(br.readLine());
                                        name = br.readLine();
                                        x = (double) Double.valueOf(br.readLine());
                                        y = (double) Double.valueOf(br.readLine());
                                        p = (double) Double.valueOf(br.readLine());
                                        gD.agregarVertice(gD, id, name, x, y, p);
                                    } catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                    action = 22;
                                }

                                else if (action == 25) {
                                    System.out.println("Indique el ID del vertice inicial y final " +
                                    "del arco, su tipo y su peso.");
                                    try {
                                        u = (int) Integer.parseInt(br.readLine());
                                        U = gD.obtenerVertice(gD, u);
                                        v = (int) Integer.parseInt(br.readLine());
                                        V = gD.obtenerVertice(gD, v);
                                        tipo = (int) Integer.parseInt(br.readLine());
                                        p = (double) Double.valueOf(br.readLine());
                                        gD.agregarArco(gD, U, V, tipo, p);
                                    } catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                    action = 22;
                                }

                                else if (action == 26) {
                                    System.out.println("Indique el ID del vertice a eliminar: ");
                                    try {
                                        u = (int) Integer.parseInt(br.readLine());
                                        gD.eliminarVertice(gD, u);
                                    } catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                    action = 22;
                                }

                                else if (action == 27) {
                                    System.out.println("Indique el ID del vertice inicial y final " +
                                    "del arco y su tipo.");
                                    try {
                                        u = (int) Integer.parseInt(br.readLine());
                                        v = (int) Integer.parseInt(br.readLine());
                                        tipo = (int) Integer.parseInt(br.readLine());
                                        gD.eliminarArco(gD, gD.obtenerArco(gD, u, v, tipo));
                                    } catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                    action = 22;
                                }
                            }
                        }

                    // MENU MODIFICAR NO-DIRIGIDO
                    else if (action == 13) { 
                        System.out.println(menuListarD2);
                        n = (int) Integer.parseInt(br.readLine());
                        if ((n >= grafosN.size()) || (n < 0)){
                            System.out.println("El grafo indicado no existe.");
                            action = 0;
                        } else {
                            action = 23;
                            gN = grafosN.get(n);
                        }
                    }

                        else if (action == 23) {
                            while (action == 23) {

                                action = CG.menu(menuModificar3, 4 ,27);

                                if (action == 28) {
                                    System.out.println("Indique el ID, nombre, coordenadas x, y;" +
                                    " y el peso del vertice");
                                    try {
                                        id = (int) Integer.parseInt(br.readLine());
                                        name = br.readLine();
                                        x = (double) Double.valueOf(br.readLine());
                                        y = (double) Double.valueOf(br.readLine());
                                        p = (double) Double.valueOf(br.readLine());
                                        gN.agregarVertice(gN, id, name, x, y, p);
                                    } catch (Exception e){
                                        System.out.println("Error");
                                    }
                                    action = 23;
                                }

                                else if (action == 29) {
                                    System.out.println("Indique el ID de los vertices incidentes " +
                                    "en la arista, su tipo y su peso.");
                                    try {
                                        u = (int) Integer.parseInt(br.readLine());
                                        U = gN.obtenerVertice(gN, u);
                                        v = (int) Integer.parseInt(br.readLine());
                                        V = gN.obtenerVertice(gN, v);
                                        tipo = (int) Integer.parseInt(br.readLine());
                                        p = (double) Double.valueOf(br.readLine());
                                        gN.agregarArista(gN, U, V, tipo, p);
                                    } catch (Exception e){
                                        System.out.println("Error");
                                    }
                                    action = 23;
                                }

                                else if (action == 30) {
                                    System.out.println("Indique el ID del vertice a eliminar: ");
                                    try {
                                        u = (int) Integer.parseInt(br.readLine());
                                        gN.eliminarVertice(gN, u);
                                    } catch (Exception e){
                                        System.out.println("Error");
                                    }
                                    action = 23;
                                }

                                else if (action == 31) {
                                    System.out.println("Indique el ID del vertice inicial y final " +
                                    "del arco y su tipo.");
                                    try {
                                        u = (int) Integer.parseInt(br.readLine());
                                        v = (int) Integer.parseInt(br.readLine());
                                        tipo = (int) Integer.parseInt(br.readLine());
                                        gN.eliminarArista(gN, gN.obtenerArista(gN, u, v, tipo));
                                    } catch (Exception e){
                                        System.out.println("Error");
                                    }
                                    action = 23;
                                }
                            }
                        }

                // ELIMINAR
                else if (action == 5) { action = CG.menu(menuEliminar, 2, 13); }

                    // Eliminar un grafo dirigido.
                    else if (action == 14) {
                        System.out.println(menuEliminar2);
                        n = (int) Integer.parseInt(br.readLine());
                        if ((n >= 0) && (n <= grafosD.size()) ){
                            grafosD.remove(n);
                        } else {
                            System.out.println("El grafo indicado no existe");
                        }
                        action = 0;
                    }
                    // Eliminar un grafo no dirigido.
                    else if (action == 15) {
                        System.out.println(menuEliminar2);
                        n = (int) Integer.parseInt(br.readLine());
                        if ((n > 0) && (n <= grafosN.size()) ){
                            grafosN.remove(n);
                        } else {
                            System.out.println("El grafo indicado no existe");
                        }
                        action = 0;
                    }
        }
    }
}