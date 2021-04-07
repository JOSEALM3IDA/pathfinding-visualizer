package jalmeida.pathfindingvisualizer.logic;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridSquare;

public class Node implements Comparable<Node> {

    private final GridSquare gridSquare;
    private int cost;
    private double f;

    public Node(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        f = 0;
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.f, n.f);
    }

    public double getF() {
        return f;
    }
    public void setF(double value) {
        f = value;
    }

    public GridSquare getSquare() {
        return gridSquare;
    }

    public int getCost() { return cost; }
    public void setCost(int value) { cost = value; }
}
