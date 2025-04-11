
import java.util.*;

public class P2Prim {

    private static int INF = Integer.MAX_VALUE;

    public static List<List<Integer>> algoritmoPrim(int[][] grafo, int nodoInicial){

        //estructura para llevar conteo de los nodos visitados, HashSet para buscar eficientemente en O(1)
        Set<Integer> nodosVisitados = new HashSet<>(); 

        //estructura para guardar el MST
        List<List<Integer>> aristasIncluidas = new ArrayList<>();

        //'Visitar' el nodo inicial
        nodosVisitados.add(nodoInicial);

        //Estructura para guardar la arista de minimo peso que conecta a cada vertice con un vertice del arbol
        //Una matriz n por 2, cada fila corresponde a un vertice,
            //La primera columna guarda el vertice visitado que conecta al vertice de la fila al MST
            //La segunda columna guarda el peso de dicha conexion
                //Si no hay conexion todavia al MST el peso es un entero muy grande (INF)
        int[][] distancia = new int[grafo.length][2];

        //Inicializamos distancia con el unico vertice del MST, el inicial
        for (int i = 0; i < distancia.length; i++) {
            if (grafo[nodoInicial][i] !=0) { //Si hay conexion, ponerla
                distancia[i] = new  int [] {nodoInicial , grafo[nodoInicial][i]};
            }
            else {//De lo contrario, poner infinito
                distancia[i] = new int [] {-1, INF}; 
            }

        }

        //Vamos a ir añadiendo, un vertice a la vez, el vertice no visitado cuya conexion al MST sea la de menor peso
        while (nodosVisitados.size() < grafo.length){
            int nodoOrigenMenorPeso = -1;
            int nodoDestinoMenorPeso = -1;
            int menorPeso = INF;

            //recorremos distancia para buscar la arista de menor peso entre el MST y un nodo no visitado
            //esto toma n (v) operaciones
            for (int i = 0; i < distancia.length; i++) {
                //verificamos que el peso de la arista sea menor y que el nodo no se haya visitado ya
                if (distancia[i][1] < menorPeso && !nodosVisitados.contains(i)){
                    nodoOrigenMenorPeso = distancia[i][0];
                    nodoDestinoMenorPeso = i;
                    menorPeso = distancia[i][1];
                }
            }
            //generamos la arista como una lista para agregarla al retorno
            List<Integer> arista = new ArrayList<>();
            arista.add(nodoOrigenMenorPeso);
            arista.add(nodoDestinoMenorPeso);
            arista.add(menorPeso);
            aristasIncluidas.add(arista);

            //agregamos el nodo de destino a los visitados
            nodosVisitados.add(nodoDestinoMenorPeso);

            //actualizamos el arreglo de distancia 
            //esto toma n (v) operaciones 
            actualizarDistancia(grafo, nodoDestinoMenorPeso, distancia);


        }
            
        return aristasIncluidas;
    }

    private static void actualizarDistancia(int[][] grafo, int nodoAgregado, int[][] distancia) {
        //este metodo revisa para todos los nodos si, al incluir el nodoAgregado al MST, su arista de minima conexion a este cambia
                //Para esto, revisa el peso de la arista entre nodoAgregado y cada i
                    //Si es 0, o no esta conectado o es el mismo, por lo que lo evita
                    //Si es mayor que 0 pero menor que el peso min actual, actualiza este peso y la arista de conexion
        
        for (int i = 0; i < distancia.length; i++) {
            int nuevaDistancia = grafo[i][nodoAgregado];
            int distanciaActual = distancia[i][1];
            if(nuevaDistancia != 0 && nuevaDistancia < distanciaActual){
                distancia[i][0] = nodoAgregado;
                distancia[i][1] = nuevaDistancia;
            }
        }

        //dado que en la busqueda por la arista de minimo peso hacia el MST obviamos aquellos ya visitados, no hay necesidad de cambiar
        //la arista de minimo peso del nodoAgregado a algo como INF, pues igual no se tiene en cuenta..

        
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