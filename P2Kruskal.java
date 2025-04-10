
import java.util.*;

public class P2Kruskal {

    public static List<Arista> algoritmoKruskal(Map<Integer, List<Arista>> grafo) {

        List<Arista> aristasOrdenadas = ordenarAristasPeso(grafo);

        int n = grafo.size();

        List<Arista> aristasIncluidas = new ArrayList<>();

        int[] nodoRaiz = new int[n];
        int[] numHijos = new int[n];

        Arrays.fill(numHijos, 0);

        for (int i = 0; i < nodoRaiz.length; i++) {
            nodoRaiz[i] = i;
        }
        int indArista = 0;
        while (aristasIncluidas.size() < n-1) {
            Arista arista = aristasOrdenadas.get(indArista);
            int node1 = arista.nodoOrigen;
            int node2 = arista.nodoDestino;

            int raiz1 = find(nodoRaiz, node1);
            int raiz2 = find(nodoRaiz, node2);

            if (raiz1!= raiz2) {//incluimos la arista

                union(nodoRaiz, numHijos, raiz1, raiz2);
                aristasIncluidas.add(arista);



            }

            indArista ++;
        }


        return aristasIncluidas;

    }

    private static int find(int[] parent, int nodoEncontrar) {

        int raizActual = parent[nodoEncontrar];

        while (raizActual!=nodoEncontrar) {
            nodoEncontrar = raizActual;
            raizActual = parent[nodoEncontrar];
        }

        return nodoEncontrar;
    }

    private static void union(int[] parent, int[]rank, int nodo1, int nodo2){
        int hijos1 = rank[nodo1];
        int hijos2 = rank[nodo2];

        if (hijos1>=hijos2){
            parent[nodo2] = nodo1;
            rank[nodo1] = hijos1 + 1;
        }

        else {
            parent[nodo1] = nodo2;
            rank[nodo2] = hijos2 + 1;
        }
    }

    private static List<Arista> ordenarAristasPeso(Map<Integer, List<Arista>> grafo){

        Set<Arista> aristasUnicas = new HashSet<>();

        for (List<Arista> listaAdyacencia: grafo.values()) {
            aristasUnicas.addAll(listaAdyacencia);
        }

        List<Arista> listaCompleta = new ArrayList<>(aristasUnicas);

        listaCompleta.sort(null);

        return listaCompleta;

    }

    public static Map<Integer, List<Arista>> construirGrafo(String[] input){

        int n = Integer.parseInt(input[0]);

        Map<Integer, List<Arista>> grafo = new HashMap<>();

        for (int i = 0; i < n; i++) {
            List<Arista> adyacentes = new ArrayList<>();
            grafo.put(i, adyacentes);
        }

        for (int i = 2; i < input.length; i++) {
            String aristaStr = input[i];

            String[] aristaInfo = aristaStr.split(",");

            int nodo1 = Integer.parseInt(aristaInfo[0]);
            int nodo2 = Integer.parseInt(aristaInfo[1]);
            int peso = Integer.parseInt(aristaInfo[2]);

            Arista nuevaArista = new Arista(nodo1, nodo2, peso);
            grafo.get(nodo1).add(nuevaArista);
            grafo.get(nodo2).add(nuevaArista);
        }

        return grafo;
    }

    private static String reconstruirMST(List<Arista> aristasIncluidas) {
        String retorno = "";

        for (Arista arista : aristasIncluidas) {
            retorno += String.format(" %d,%d,%d", arista.nodoOrigen, arista.nodoDestino, arista.peso);
        }
        

        return retorno;
    }

    private static int calcularPesoTotalMST(List<Arista> aristasIncluidas) {
        int total = 0;

        for (Arista arista : aristasIncluidas) {
            total += arista.peso;
        }
        return total;
    }

    public static void main(String[] args) {
        /* 
        String caso = "10 7 5,7,75 5,9,73 5,2,86 3,2,57 3,1,70 4,1,57 4,6,70 4,0,63 4,8,85 0,1,483 0,2,664 0,3,243 0,5,770 0,6,118 0,7,975 0,8,542 0,9,556 1,2,736 1,5,617";
        String[] listStrings = caso.split(" ");
        Map<Integer, List<Arista>> grafoPrueba = construirGrafo(listStrings);
        List<Arista> respuestaPrueba = algoritmoKruskal(grafoPrueba);
        */

        Scanner scanner = new Scanner(System.in);
        int ncasos = Integer.parseInt(scanner.nextLine());
        
        for (int i = 0; i < ncasos; i++) {
            String[] input = scanner.nextLine().split(" ");
            Map<Integer, List<Arista>> grafo = construirGrafo(input);

            List<Arista> respuesta = algoritmoKruskal(grafo);
            int pesoTotal = calcularPesoTotalMST(respuesta);
            String aristas = reconstruirMST(respuesta);

            String imprimir = String.format("%d"+aristas, pesoTotal);

            System.out.println(imprimir);
        }
        scanner.close();
    }
    

}

class Arista implements Comparable<Arista> {
    int nodoOrigen;
    int nodoDestino;
    int peso;

    public Arista(int nodoOrigen, int nodoDestino, int peso) {
        this.nodoOrigen = nodoOrigen;
        this.nodoDestino = nodoDestino;
        this.peso = peso;
    }

    

    @Override 
    public int compareTo(Arista otraArista) {
        return Integer.compare(this.peso, otraArista.peso);
    }

}