package jalmeida.pathfindingvisualizer.logic.algorithms;

import jalmeida.pathfindingvisualizer.logic.Algorithm;
import jalmeida.pathfindingvisualizer.logic.Node;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridSquare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar extends Algorithm {

    private final PriorityQueue<Node> openSet = new PriorityQueue<>();
    private final HashMap<Node, Node> cameFrom = new HashMap<>();
    private final HashMap<Node, Double> gScore = new HashMap<>();

    public Astar(GridContainer gridContainer) { super(gridContainer); }

    @Override
    public void solve() {
        clear();

        openSet.add(startPoint.getNode());
        gScore.put(startPoint.getNode(), 0.0);

        startPoint.getNode().setF(startPoint.getDistanceTo(endPoint));

        while (!openSet.isEmpty()) {
            Node current = openSet.peek();
            if (current.getSquare().isEndPoint()) {
                paintSolution();
                return;
            }

            int[] pos = gridContainer.getPosOfSquare(current.getSquare());
            System.out.println("{" + pos[0] + "," + pos[1]  + "}");

            removeNode(current);

            ArrayList<GridSquare> neighbours = gridContainer.getNeighbours(current.getSquare());
            for (GridSquare neighbour : neighbours) {
                double tentativeGScore = gScore.get(current) + neighbour.getNode().getCost();

                if (tentativeGScore < gScore.getOrDefault(neighbour.getNode(), Double.MAX_VALUE)) {
                    updateNode(neighbour.getNode(), current, tentativeGScore);

                    if (!openSet.contains(neighbour.getNode()))
                        addNode(neighbour.getNode());
                }
            }
        }
    }

    private void clear() {
        openSet.clear();
        cameFrom.clear();
        gScore.clear();
    }

    private void addNode(Node child) {
        openSet.add(child);
        child.getSquare().setAsToSearch();
    }

    private void removeNode(Node node) {
        openSet.remove(node);
        node.getSquare().setAsSearched();
    }

    private void updateNode(Node child, Node parent, double g) {
        cameFrom.put(child, parent);
        gScore.put(child, g);
        child.setF(g + child.getSquare().getDistanceTo(endPoint));
    }

    private void paintSolution() {
        Node last = endPoint.getNode();
        while (cameFrom.containsKey(last)) {
            last = cameFrom.get(last);
            last.getSquare().setAsSolution();
        }
    }
}

