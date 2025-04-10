package punto3;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeneracionCasosPruebaP3 {
    private static int NUMCASOS = 20;
    private static int MAXNODES = 20;
    private static int MINNODES = 10;
    private static int MAXWEIGHT = 100;



    public static String[] generarArchivo() {
        String[] retorno = new String[2];
        Random generadorAleatorio = new Random();
        String grafosString ="";
        String respuestaString = "";

        //Bipartitos
        for (int i = 0; i < NUMCASOS/2 ; i++) {
            
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



            int aristasACrear = generadorAleatorio.nextInt(maxAristas) + 1;

            

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

            grafosString += grafoString;

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

            respuestaString += esperadoString;


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
            writer.write(Integer.toString(NUMCASOS/2));
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
