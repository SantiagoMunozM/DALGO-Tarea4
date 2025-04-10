
import java.util.*;

public class P3Bipartito {

    public static List<Set<Integer>> esBipartito(Map<Integer, List<Arista>> grafo) {

        Set<Integer> nodosRojos = new HashSet<>();
        Set<Integer> nodosAzules = new HashSet<>();

        
        int nodoOrigen = 0;
        boolean dobleColor = false;
        //BFS desde el nodoOrigen

        Set<Integer> descubiertos = new HashSet<>();
        Set<Integer> procesados = new HashSet<>();

        Queue<Integer> cola = new LinkedList<>();

        cola.offer(nodoOrigen);
        descubiertos.add(nodoOrigen);
        nodosRojos.add(nodoOrigen);

        while (cola.size()>0 && dobleColor == false) {

            int colorOrigen;

            int nodoActual = cola.poll();

            if(nodosRojos.contains(nodoActual)){
                colorOrigen = 1; //1 para rojo, 0 para azul
            }

            else {
                colorOrigen = 0;
            }

            procesados.add(nodoActual);

            List<Arista> listaAdyacencia = grafo.get(nodoActual);

            for (Arista arista : listaAdyacencia) {

                int nodoDestino = arista.getNodoConectado();
                
                if (colorOrigen == 1) {
                    if (nodosRojos.contains(nodoDestino)) {
                        dobleColor = true;
                    }
                    else {
                        if(!descubiertos.contains(nodoDestino)) {
                            cola.add(nodoDestino);
                            descubiertos.add(nodoDestino);
                            nodosAzules.add(nodoDestino);
                        }
                        
                    }
                }
                else {
                    if (nodosAzules.contains(nodoDestino)) {
                        dobleColor = true;
                    }
                    else {
                        if(!descubiertos.contains(nodoDestino)) {
                            cola.add(nodoDestino);
                            descubiertos.add(nodoDestino);
                            nodosRojos.add(nodoDestino);
                        }
                        
                    }
                }
                
            }


        }


        List<Set<Integer>> retorno = new ArrayList<>();
        if (!dobleColor) {
            retorno.add(nodosAzules);
            retorno.add(nodosRojos);

        }
        return retorno;
    }

    public static Map<Integer, List<Arista>> construirGrafo(String[] input){

        int n = Integer.parseInt(input[0]);

        Map<Integer, List<Arista>> grafo = new HashMap<>();

        for (int i = 0; i < n; i++) {

            List<Arista> listaNodo = new LinkedList<>();
            
            grafo.put(i, listaNodo);
            
        }

        for (int i = 1; i < input.length; i++) {
            String aristaStr = input[i];

            String[] aristaInfo = aristaStr.split(",");

            int origen = Integer.parseInt(aristaInfo[0]);
            int destino = Integer.parseInt(aristaInfo[1]);
            int peso = Integer.parseInt(aristaInfo[2]);

            Arista aristaOrigen = new Arista(destino, peso);
            Arista aristaDestino = new Arista(origen, peso);

            grafo.get(origen).add(aristaOrigen);
            grafo.get(destino).add(aristaDestino);
            
        }

        return grafo;
    }

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int ncasos = Integer.parseInt(scanner.nextLine());
        
        for (int i = 0; i < ncasos; i++) {
            String[] input = scanner.nextLine().split(" ");
            Map<Integer, List<Arista>> grafo = construirGrafo(input);

            List<Set<Integer>> respuesta = esBipartito(grafo);

            if (respuesta.isEmpty()) {
                System.out.println("False");
            }

            else{
                String imprimir = "True {";
                Set<Integer> azules = respuesta.get(0);
                Set<Integer> rojos = respuesta.get(1);

                String strAzules = "";
                for (Integer nodo:azules){
                    strAzules += Integer.toString(nodo) + ",";
                }
                strAzules = strAzules.substring(0,strAzules.length()-1);
                imprimir+= strAzules +"} {";

                String strRojos = "";
                for (Integer nodo:rojos){
                    strRojos += Integer.toString(nodo) + ",";
                }
                strRojos = strRojos.substring(0,strRojos.length()-1);
                imprimir+= strRojos +"}";

                System.out.println(imprimir);



            }

            

            
        }
        scanner.close();
    }


    
}


class Arista implements Comparable<Arista> {
    int nodoConectado;
    int peso;

    public Arista(int nodoConectado, int peso) {
        this.nodoConectado = nodoConectado;
        this.peso = peso;
    }

    

    @Override 
    public int compareTo(Arista otraArista) {
        return Integer.compare(getPeso(), otraArista.getPeso());
    }

    public int getPeso() {
        return peso;
    }



    public int getNodoConectado() {
        return nodoConectado;
    }


}