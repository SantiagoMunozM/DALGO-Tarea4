import java.util.*;

public class P2Prim {

    public static List<List<Integer>> algoritmoPrim(int[][] grafo, int nodoInicial){

    Set<Integer> nodosVisitados = new HashSet<>();

    List<List<Integer>> aristasIncluidas = new ArrayList<>();

    nodosVisitados.add(nodoInicial);

    while (nodosVisitados.size() < grafo.length){
        int nodoOrigenMenorPeso = -1;
        int nodoDestinoMenorPeso = -1;
        int menorPeso = Integer.MAX_VALUE;

        for(int nodoOrigen: nodosVisitados) {

           int nodoDestino = encontrarAristaMenorPesoUnicoNodo(grafo, nodoOrigen, nodosVisitados);

           if (nodoDestino!= -1){
                int pesoArista = grafo[nodoOrigen][nodoDestino];

                if (pesoArista<menorPeso) {
                    nodoOrigenMenorPeso = nodoOrigen;
                    nodoDestinoMenorPeso = nodoDestino;
                    menorPeso = pesoArista;
                }
           }

           
           

        }
        List<Integer> arista = new ArrayList<>();
        arista.add(nodoOrigenMenorPeso);
        arista.add(nodoDestinoMenorPeso);
        arista.add(menorPeso);

        aristasIncluidas.add(arista);
        nodosVisitados.add(nodoDestinoMenorPeso);

    }
        
    return aristasIncluidas;
    }

    private static int encontrarAristaMenorPesoUnicoNodo(int[][] grafo, int nodoOrigen, Set<Integer> nodosVisitados) {
        int menorPeso = Integer.MAX_VALUE;
        int nodoMenorPeso = -1;

        int[] listaAdyacencia = grafo[nodoOrigen];

        for (int i = 0; i < listaAdyacencia.length; i++) {
            int pesoArista = listaAdyacencia[i];
            if (pesoArista < menorPeso && pesoArista >0 && !nodosVisitados.contains(i)) {
                menorPeso = listaAdyacencia[i];
                nodoMenorPeso = i;
            }
        }

        return nodoMenorPeso;
    }

    public static int[][] construirGrafo(String[] input){

        int n = Integer.parseInt(input[0]);

        int [][] grafo = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(grafo[i], 0);
        }

        for (int i = 2; i < input.length; i++) {
            String aristaStr = input[i];

            String[] aristaInfo = aristaStr.split(",");

            int nodo1 = Integer.parseInt(aristaInfo[0]);
            int nodo2 = Integer.parseInt(aristaInfo[1]);
            int peso = Integer.parseInt(aristaInfo[2]);

            grafo[nodo1][nodo2] = peso;
            grafo[nodo2][nodo1] = peso;
        }

        return grafo;
    }

    public static int calcularPesoTotalMST(List<List<Integer>> aristas) {
        int pesoTotal = 0;
        for (List<Integer> arista : aristas) {
            pesoTotal += arista.get(2);
        }
        return pesoTotal;
    }

    public static String generarStringAristas(List<List<Integer>> aristas) {
        String respuesta = "";

        for (List<Integer> arista : aristas) {
            if (respuesta.isEmpty()) {
                respuesta += String.format("%d,%d,%d", arista.get(0), arista.get(1),arista.get(2));
            }
            else{
                respuesta += String.format(" %d,%d,%d", arista.get(0), arista.get(1),arista.get(2));
            }
        }

        return respuesta;
    }

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int ncasos = Integer.parseInt(scanner.nextLine());
        
        for (int i = 0; i < ncasos; i++) {
            String[] input = scanner.nextLine().split(" ");
            int nodoInicial = Integer.parseInt(input[1]);
            int[][] grafo = construirGrafo(input);

            List<List<Integer>> respuesta = algoritmoPrim(grafo, nodoInicial);
            int pesoTotal = calcularPesoTotalMST(respuesta);
            String aristas = generarStringAristas(respuesta);

            String imprimir = String.format("%d "+aristas, pesoTotal);

            System.out.println(imprimir);
        }
        scanner.close();
    }

    
}