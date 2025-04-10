
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeneracionCasosPruebaP2 {
    
    private static int NUMCASOS = 100;
    private static int MAXNODES = 1000;
    private static int MINNODES = 10;
    private static int MAXWEIGHTMST = 100;
    private static int MAXWEIGHTGENERAL = 1000;
    private static int MAXADDITIONALEDGES = 25000;

    public static String[] generarArchivo() {
        String[] retorno = new String[2];
        Random generadorAleatorio = new Random();
        String grafosString ="";
        String respuestaString = "";
        for (int i = 0; i < NUMCASOS ; i++) {
            

            int n = generadorAleatorio.nextInt(MAXNODES - MINNODES+1) + MINNODES;
            int maxPeso = 0;

            Set<Integer> conectados = new HashSet<>();

            HashMap<Integer, List<Integer>> grafo = new HashMap<>();

            for (int j = 0; j < n; j++) {
                List<Integer> listaAdyacentes = new LinkedList<>();
                grafo.put(j, listaAdyacentes);
            }

            String grafoString = "";//creamos la linea
            grafoString += Integer.toString(n); // n
            grafoString += " " + Integer.toString(generadorAleatorio.nextInt(n)); //nodo de inicio

            String aristas = "";

            int pesoTotal = 0;

            int maxAristas = n*n -n;

            int node1 = generadorAleatorio.nextInt(n);
            int node2 = generadorAleatorio.nextInt(n);
            while (node2 == node1) {
                node2 = generadorAleatorio.nextInt(n);
            }
            while (conectados.size()<n) {
                
                grafo.get(node1).add(node2);
                grafo.get(node2).add(node1);
                int peso = generadorAleatorio.nextInt(MAXWEIGHTMST) + 1;

                if (peso>maxPeso) maxPeso = peso;

                pesoTotal += peso;

                conectados.add(node1);
                conectados.add(node2);

                grafoString += String.format(" %d,%d,%d", node1, node2, peso);
                aristas += String.format(" %d,%d,%d", node1, node2, peso);
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
            
            String respuestaEsperada = String.format("%d"+aristas + "\n", pesoTotal);
            respuestaString += respuestaEsperada;

            int aristasAdicionales = Math.min(generadorAleatorio.nextInt(maxAristas), MAXADDITIONALEDGES);

            node1 = 0;
            while (aristasAdicionales>0 && node1<n) {

                List<Integer> yaConectados = grafo.get(node1);

                node2 = 0;
                while (aristasAdicionales>0 && node2<n) {

                    if (!yaConectados.contains(node2)&& node1!=node2){
                        int peso = generadorAleatorio.nextInt(MAXWEIGHTGENERAL - maxPeso +1) + maxPeso;
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
            FileWriter writer = new FileWriter("pruebaPrim.IN");
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
            FileWriter writer = new FileWriter("respuestaEsperadaPrim.txt");
            writer.write(respuestaEsperada);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    
    }

}
