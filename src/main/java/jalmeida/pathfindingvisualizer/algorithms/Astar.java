package jalmeida.pathfindingvisualizer.algorithms;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridSquare;

import java.util.ArrayList;

public class Astar extends Algorithm {

    public Astar(GridContainer gridContainer) {
        super(gridContainer);
    }

    @Override
    public void solve() {
        int cost = 0;
        solveNode(startPoint, 0);
    }

    private void solveNode(GridSquare node, int currentCost) {
        currentCost += node.getCost();

        ArrayList<GridSquare> neighbours = gridContainer.getNeighbours(node);


    }
}
