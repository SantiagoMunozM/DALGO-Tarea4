
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeneracionCasosPruebaP3 {
    private static int NUMCASOS = 100;
    private static int MAXNODES = 1000;
    private static int MINNODES = 10;
    private static int MAXWEIGHT = 100;
    private static int MAXADDITIONALEDGES = 2000;

    public static String[] generarGrafoBipartito () {

        String[] retorno = new String[2];

        Random generadorAleatorio = new Random();

        int n = generadorAleatorio.nextInt(MAXNODES - MINNODES+1) + MINNODES;

        int numRojos = generadorAleatorio.nextInt(n - 3 * n/4) + n/4;
        int numAzules = n - numRojos;

        int maxAristas = numRojos * numAzules;

        Set<Integer> nodosRojos = new HashSet<>();
        Set<Integer> nodosAzules = new HashSet<>();
        Map<Integer, Set<Integer>> grafo = new HashMap<>();

        int nodoLlenar = 0;
        while (nodoLlenar<numRojos) {
            nodosRojos.add(nodoLlenar);
            nodoLlenar++;
        }

        while (nodoLlenar < n) {
            nodosAzules.add(nodoLlenar);
            nodoLlenar++;
        }

        for (int j = 0; j < n; j++) {
            Set<Integer> listaAdyacentes = new HashSet<>();
            grafo.put(j, listaAdyacentes);
        }
        
        
        

        String grafoString = "";//creamos la linea
        grafoString += Integer.toString(n); // n

        Set<Integer> conectados = new HashSet<>();

        Set<Integer> noConectadosRojos = new HashSet<>(nodosRojos);
        Set<Integer> noConectadosAzules = new HashSet<>(nodosAzules);

        List<Integer> listaRojos = new ArrayList<>(noConectadosRojos);
        List<Integer> listaAzules = new ArrayList<>(noConectadosAzules);

        Set<Integer> nodosIncompletos = new HashSet<>();

        nodosIncompletos.addAll(nodosRojos);
        nodosIncompletos.addAll(nodosAzules);

        int indNodoRojo = generadorAleatorio.nextInt(listaRojos.size());
        int indNodoAzul = generadorAleatorio.nextInt(listaRojos.size());
        
        int nodoRojo = listaRojos.get(indNodoRojo);
        int nodoAzul = listaAzules.get(indNodoAzul);

        while (conectados.size()< n && noConectadosRojos.size()>0 && noConectadosAzules.size()>0) {
            
            grafo.get(nodoRojo).add(nodoAzul);
            grafo.get(nodoAzul).add(nodoRojo);
            int peso = generadorAleatorio.nextInt(MAXWEIGHT) + 1;

            conectados.add(nodoRojo);
            conectados.add(nodoAzul);

            if(noConectadosRojos.contains(nodoRojo)) noConectadosRojos.remove(nodoRojo);
            if(noConectadosAzules.contains(nodoAzul)) noConectadosAzules.remove(nodoAzul);

            if(grafo.get(nodoRojo).size() == nodosAzules.size()) nodosIncompletos.remove(nodoRojo);
            if(grafo.get(nodoAzul).size() == nodosRojos.size()) nodosIncompletos.remove(nodoAzul);


            grafoString += String.format(" %d,%d,%d", nodoRojo, nodoAzul, peso);
            
            maxAristas--;

            double rand = generadorAleatorio.nextDouble();
            if(conectados.size()<n && noConectadosRojos.size()>0 && noConectadosAzules.size()>0) {
                if (rand>=0.5){
                    //reemplazamos nodo rojo
                    indNodoRojo = generadorAleatorio.nextInt(noConectadosRojos.size());
                    listaRojos = new ArrayList<>(noConectadosRojos);
                    nodoRojo = listaRojos.get(indNodoRojo);
                }
                else {
                    //reemplazamos nodo azul
                    indNodoAzul = generadorAleatorio.nextInt(noConectadosAzules.size());
                    listaAzules = new ArrayList<>(noConectadosAzules);
                    nodoAzul = listaAzules.get(indNodoAzul);
                } 
            }

        }
        listaAzules = new ArrayList<>(nodosAzules);
        while (noConectadosRojos.size()>0) {
            
            indNodoRojo = generadorAleatorio.nextInt(noConectadosRojos.size());
            listaRojos = new ArrayList<>(noConectadosRojos);
            nodoRojo = listaRojos.get(indNodoRojo);
            
            indNodoAzul = generadorAleatorio.nextInt(listaAzules.size());
            nodoAzul = listaAzules.get(indNodoAzul);

            int peso = generadorAleatorio.nextInt(MAXWEIGHT) + 1;

            grafoString += String.format(" %d,%d,%d", nodoRojo, nodoAzul, peso);
            noConectadosRojos.remove(nodoRojo);
            
            maxAristas--;

            
            
        }

        listaRojos = new ArrayList<>(nodosRojos);
        while (noConectadosAzules.size()>0) {
            
            indNodoAzul = generadorAleatorio.nextInt(noConectadosAzules.size());
            listaAzules = new ArrayList<>(noConectadosAzules);
            nodoAzul = listaAzules.get(indNodoAzul);
            
            indNodoRojo = generadorAleatorio.nextInt(listaRojos.size());
            nodoRojo = listaRojos.get(indNodoRojo);

            int peso = generadorAleatorio.nextInt(MAXWEIGHT) + 1;

            grafoString += String.format(" %d,%d,%d", nodoRojo, nodoAzul, peso);
            noConectadosAzules.remove(nodoAzul);
            
            maxAristas--;
        }



        int aristasACrear = Math.min(generadorAleatorio.nextInt(maxAristas) + 1, MAXADDITIONALEDGES);

        

        while (aristasACrear>0) {

            List<Integer> listaIncompletos = new ArrayList<>(nodosIncompletos);
            int indiceNodoOrigen = generadorAleatorio.nextInt(nodosIncompletos.size());
            int nodoOrigen = listaIncompletos.get(indiceNodoOrigen);

            if (nodosRojos.contains(nodoOrigen)) {
                Set<Integer> yaConectados = grafo.get(nodoOrigen);
                Set<Integer> disponibles = new HashSet<>(nodosAzules);
                disponibles.removeAll(yaConectados);

                List<Integer> listaDisponibles = new ArrayList<>(disponibles);

                int indiceNodoDestino = generadorAleatorio.nextInt(disponibles.size());

                int nodoDestino = listaDisponibles.get(indiceNodoDestino);

                int peso = generadorAleatorio.nextInt(MAXWEIGHT) + 1;

                grafo.get(nodoOrigen).add(nodoDestino);
                grafo.get(nodoDestino).add(nodoOrigen);

                grafoString += String.format(" %d,%d,%d", nodoOrigen, nodoDestino, peso);

                if (grafo.get(nodoOrigen).size() == nodosAzules.size()) {
                    nodosIncompletos.remove(nodoOrigen);
                }

                if (grafo.get(nodoDestino).size() == nodosRojos.size()) {
                    nodosIncompletos.remove(nodoDestino);
                }


            }

            else {
                Set<Integer> yaConectados = grafo.get(nodoOrigen);
                Set<Integer> disponibles = new HashSet<>(nodosRojos);
                disponibles.removeAll(yaConectados);

                List<Integer> listaDisponibles = new ArrayList<>(disponibles);

                int indiceNodoDestino = generadorAleatorio.nextInt(disponibles.size());

                int nodoDestino = listaDisponibles.get(indiceNodoDestino);

                int peso = generadorAleatorio.nextInt(MAXWEIGHT);

                grafo.get(nodoOrigen).add(nodoDestino);
                grafo.get(nodoDestino).add(nodoOrigen);

                if (grafo.get(nodoOrigen).size() == nodosRojos.size()) {
                    nodosIncompletos.remove(nodoOrigen);
                }

                if (grafo.get(nodoDestino).size() == nodosAzules.size()) {
                    nodosIncompletos.remove(nodoDestino);
                }

                grafoString += String.format(" %d,%d,%d", nodoOrigen, nodoDestino, peso);

            }

            aristasACrear--;




        }


        grafoString += "\n";

        retorno[0] = grafoString;

        String esperadoString = "True {";

        String strAzules = "";
        for (Integer nodo:nodosAzules){
            strAzules += Integer.toString(nodo) + ",";
        }
        strAzules = strAzules.substring(0,strAzules.length()-1);
        esperadoString+= strAzules +"} {";

        String strRojos = "";
        for (Integer nodo:nodosRojos){
            strRojos += Integer.toString(nodo) + ",";
        }
        strRojos = strRojos.substring(0,strRojos.length()-1);
        esperadoString+= strRojos +"}\n";

        retorno[1] = esperadoString;

        return retorno;

    }



    public static String[] generarArchivo() {
        String[] retorno = new String[2];
        Random generadorAleatorio = new Random();
        String grafosString ="";
        String respuestaString = "";

        //Bipartitos
        for (int i = 0; i < NUMCASOS/2 ; i++) {
            
            String [] grafo = generarGrafoBipartito();

            grafosString += grafo[0];
            respuestaString += grafo[1];
        }

        //no bipartitos
        for (int i = 0; i < NUMCASOS/2 ; i++) {

            int n = generadorAleatorio.nextInt(MAXNODES - MINNODES+1) + MINNODES;

            int maxAristas = (n*n - n)/2;

            Map<Integer, Set<Integer>> grafo = new HashMap<>();

            for (int j = 0; j < n; j++) {
                Set<Integer> listaAdyacentes = new HashSet<>();
                grafo.put(j, listaAdyacentes);
            }
            

            Set<Integer> conectados = new HashSet<>();


            String grafoString = "";//creamos la linea
            grafoString += Integer.toString(n); // n

            int node1 = generadorAleatorio.nextInt(n);
            int node2 = generadorAleatorio.nextInt(n);
            int node3 = generadorAleatorio.nextInt(n);

            while (node2 == node1) {
                node2 = generadorAleatorio.nextInt(n);
            }
            while (node3==node1 && node3 == node2) {
                node3 = generadorAleatorio.nextInt(n);
            }

            //Generar un ciclo impar tal que no sea bipartito
            grafo.get(node1).add(node2);
            grafo.get(node1).add(node3);
            grafo.get(node2).add(node1);
            grafo.get(node2).add(node3);
            grafo.get(node3).add(node1);
            grafo.get(node3).add(node2);

            int peso1 = generadorAleatorio.nextInt(MAXWEIGHT);
            int peso2 = generadorAleatorio.nextInt(MAXWEIGHT);
            int peso3 = generadorAleatorio.nextInt(MAXWEIGHT);

            grafoString += String.format(" %d,%d,%d", node1, node2, peso1);
            grafoString += String.format(" %d,%d,%d", node1, node3, peso2);
            grafoString += String.format(" %d,%d,%d", node2, node3, peso3);

            conectados.add(node1);
            conectados.add(node2);
            conectados.add(node3);

            int newNode = generadorAleatorio.nextInt(n);

            while(newNode == node3 || newNode == node1 || newNode == node2) {
                newNode = generadorAleatorio.nextInt(n);
            }


            while (conectados.size()<n) {
                
                grafo.get(node1).add(node2);
                grafo.get(node2).add(node1);
                int peso = generadorAleatorio.nextInt(MAXWEIGHT) + 1;

                conectados.add(node1);
                conectados.add(node2);

                grafoString += String.format(" %d,%d,%d", node1, node2, peso);
                maxAristas--;

                double rand = generadorAleatorio.nextDouble();
                if(conectados.size()<n) {
                if (rand>=0.5){
                    //reemplazamos node 1
                    int newNode1 = generadorAleatorio.nextInt(n);

                    while (newNode1 == node1 || conectados.contains(newNode1) || grafo.get(newNode1).contains(node2)) {
                        newNode1 = generadorAleatorio.nextInt(n);
                    }
                    node1 = newNode1;
                }
                else {
                    //reemplazamos node 2
                    int newNode2 = generadorAleatorio.nextInt(n);
                    while (newNode2 == node2 || conectados.contains(newNode2) || grafo.get(newNode2).contains(node1)) {
                        newNode2 = generadorAleatorio.nextInt(n);
                    }
                    node2 = newNode2;
                } 
            }

            }                  


            int aristasAdicionales =Math.min(generadorAleatorio.nextInt(maxAristas) + 1, MAXADDITIONALEDGES);

            node1 = 0;
            while (aristasAdicionales>0 && node1<n) {

                Set<Integer> yaConectados = grafo.get(node1);

                node2 = 0;
                while (aristasAdicionales>0 && node2<n) {

                    if (!yaConectados.contains(node2)&& node1!=node2){
                        int peso = generadorAleatorio.nextInt(MAXWEIGHT) +1;
                        grafo.get(node1).add(node2);
                        grafo.get(node2).add(node1);
                        grafoString += String.format(" %d,%d,%d", node1, node2, peso);
                        aristasAdicionales--;
                    }
                    node2++;
                    
                }
                node1++;

            }
            grafoString += "\n";
            grafosString += grafoString;

            respuestaString += "False\n";


        }



        retorno[0] = grafosString;
        retorno[1] = respuestaString;

        return retorno;

    }


    public static void main(String[] args) {
        String[] respuesta = generarArchivo();
        String casosPrueba = respuesta[0];
        String respuestaEsperada = respuesta[1];
        try {
            FileWriter writer = new FileWriter("punto3/pruebaBipartito.IN");
            writer.write(Integer.toString(NUMCASOS));
            writer.write("\n");
            writer.write(casosPrueba);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("punto3/respuestaEsperadaBipartito.txt");
            writer.write(respuestaEsperada);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    
    }

}
