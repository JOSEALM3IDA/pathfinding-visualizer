package jalmeida.pathfindingvisualizer.algorithms.logic;

import jalmeida.pathfindingvisualizer.algorithms.Algorithm;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridSquare;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Astar extends Algorithm {

    public Astar(GridContainer gridContainer) {
        super(gridContainer);
    }

    @Override
    public void solve() {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        Node sP = new Node(startPoint);
        sP.configure(null, 0, sP.getHeuristic(endPoint));
        openList.add(sP);

        while (!openList.isEmpty()) {
            Node n = openList.peek();
            if (n.getSquare() == endPoint) {
                paintSolution(n);
                return;
            }

            ArrayList<Node.Neighbour> neighbours = n.getNeighbours();
            for (Node.Neighbour neighbour : neighbours) {
                Node m = neighbour.getNode();
                double totalWeight = n.getGValue() + neighbour.getWeight();

                if(!openList.contains(m) && !closedList.contains(m)){
                    openList.add(m);
                    m.getSquare().setAsToSearch();
                } else if (totalWeight < m.getGValue()){
                    m.configure(n, totalWeight, m.getGValue() + m.getHeuristic(endPoint));

                    if (closedList.contains(m)) {
                        closedList.remove(m);
                        openList.add(m);
                        m.getSquare().setAsToSearch();
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
    }

    private void paintSolution(Node n) {
        Node parent;
        while ((parent = n.getParent()) != null) { parent.getSquare().setAsSolution(); }
    }
}

class Node implements Comparable<Node>  {

    private Node parentNode;
    private GridSquare gridSquare;
    private double g;
    private double f;
    private ArrayList<Neighbour> neighbours;

    public Node(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        this.parentNode = null;
        neighbours = new ArrayList<>();
        g = 0;
        f = 0;
    }

    @Override
    public int compareTo(Node n) { return Double.compare(this.f, n.f); }

    public void setParentNode(Node node) { parentNode = node; }

    public void setGValue(double value) { g = value; }

    public void setFValue(double value) { f = value; }

    public double getHeuristic(GridSquare target) { return gridSquare != null ? gridSquare.getDistanceTo(target) : 10000; }

    public GridSquare getSquare() { return gridSquare; }

    public Node getParent() { return parentNode; }

    public double getFValue() { return f; }

    public double getGValue() { return g; }

    public static class Neighbour {
        private GridSquare gridSquare;
        private Node node;

        public Neighbour(GridSquare gridSquare, Node node){
            this.gridSquare = gridSquare;
            this.node = node;
        }

        public int getWeight() { return gridSquare.getCost(); }
        public Node getNode() { return node; }
    }

    public void addBranch(GridSquare gridSquare, Node node){
        Neighbour neighbour = new Neighbour(gridSquare, node);
        neighbours.add(neighbour);
    }

    public void configure(Node parent, double gVal, double fVal) {
        setParentNode(parent);
        setGValue(gVal);
        setFValue(fVal);
    }

    public ArrayList<Neighbour> getNeighbours() { return neighbours; }
}
