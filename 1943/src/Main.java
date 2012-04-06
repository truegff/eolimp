import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * E-olimp #1943 МОСТЫ
 *
 * @author Alexander Dolidze <alexander.dolidze@gmail.com>
 */

public class Main {

    public static int bridgesCount = 0;
    public static boolean[] bridges;
    public static int dfsTimer = 0;

    public static void dfs(Node v, Edge p) {
        v.dfsTime = v.lowlink = (++dfsTimer);

        for (Edge e : v.edges) {
            // mshobelshi ar vbrundebit
            if (e != p)
                for (Node n : e.nodes) {
                    if (n != v) {
                        // mivagenit rancxas
                        if (n.dfsTime != 0) {
                            v.lowlink = Math.min(n.dfsTime, v.lowlink);
                            //continue;
                        }

                        // cin mivdivart
                        if (n.dfsTime == 0) {
                            dfs(n, e);
                            v.lowlink = Math.min(v.lowlink, n.lowlink);
                            if (v.dfsTime < n.lowlink) {
                                bridgesCount++;
                                bridges[e.index] = true;
                            }
                        }

                    }

                }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter printWriter = new PrintWriter(System.out);


        String[] tokens;
        tokens = bufferedReader.readLine().split("[ ]+");
        int N = Integer.parseInt(tokens[0]);
        int M = Integer.parseInt(tokens[1]);

        NodeFactory.init(N);
        EdgeFactory.init(M);
        bridges = new boolean[M];

        for (int i = 0; i < M; i++) {
            tokens = bufferedReader.readLine().split("[ ]+");
            int v1, v2;
            v1 = Integer.parseInt(tokens[0]) - 1;
            v2 = Integer.parseInt(tokens[1]) - 1;
            Node node1 = NodeFactory.getNode(v1);
            Node node2 = NodeFactory.getNode(v2);

            Edge e = EdgeFactory.getEdge(i);
            node1.edges.add(e);
            node2.edges.add(e);

            e.nodes.add(node1);
            e.nodes.add(node2);
        }

        if (M > 0)
            for (Node n : NodeFactory.nodes) {
                if (n.dfsTime == 0) dfs(n, null);
            }

        printWriter.println(bridgesCount);
        //if (bridgesCount == 0) printWriter.println();
        for (int i = 0; i < M; i++) {
            if (bridges[i]) {
                if ((--bridgesCount) == 0)
                    printWriter.println((i + 1));
                else
                    printWriter.print((i + 1) + " ");
            }
        }
        printWriter.flush();
    }
}

class Node {
    int index;
    int dfsTime = 0;
    int lowlink = 0;
    ArrayList<Edge> edges = new ArrayList<Edge>();

    Node(int index) {
        this.index = index;
    }
}

class Edge {
    int index;
    ArrayList<Node> nodes = new ArrayList<Node>(2);

    Edge(int index) {
        this.index = index;
    }
}

class EdgeFactory {
    private static Edge[] edges;

    public static void init(int i) {
        edges = new Edge[i];
        for (int a = 0; a < i; a++) edges[a] = new Edge(a);
    }

    public static Edge getEdge(int i) {
        //if (edges[i]==null) edges[i]=new Edge(i);
        return edges[i];
    }
}

class NodeFactory {
    public static Node[] nodes;

    public static void init(int i) {
        nodes = new Node[i];
        for (int a = 0; a < i; a++) nodes[a] = new Node(a);
    }

    public static Node getNode(int i) {
        //if (nodes[i]==null) nodes[i]=new Node(i);
        return nodes[i];
    }
}