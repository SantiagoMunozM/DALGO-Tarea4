
import java.util.*;

public class P2Kruskal {

    public static int algoritmoKruskal(Map<Integer, List<Arista>> grafo) {

        List<Arista> aristasOrdenadas = ordenarAristasPeso(grafo);

        return 1;

    }

    private static List<Arista> ordenarAristasPeso(Map<Integer, List<Arista>> grafo){

        List<Arista> listaCompleta = new ArrayList<>();

        for (List<Arista> listaAdyacencia: grafo.values()) {
            listaCompleta.addAll(listaAdyacencia);
        }

        listaCompleta.sort(null);

        return listaCompleta;

    }

}

class Arista implements Comparable<Arista> {
    int nodoOrigen;
    int nodoDestino;
    int peso;

    public Arista(int nodoOrigen, int peso, int nodoDestino) {
        this.nodoOrigen = nodoOrigen;
        this.nodoDestino = nodoDestino;
        this.peso = peso;
    }

    

    @Override 
    public int compareTo(Arista otraArista) {
        return Integer.compare(getPeso(), otraArista.getPeso());
    }

    public int getPeso() {
        return peso;
    }



    public int getNodoOrigen() {
        return nodoOrigen;
    }



    public int getNodoDestino() {
        return nodoDestino;
    }
}