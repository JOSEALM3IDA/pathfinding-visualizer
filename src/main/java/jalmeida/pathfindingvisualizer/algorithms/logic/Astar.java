package jalmeida.pathfindingvisualizer.algorithms.logic;

import jalmeida.pathfindingvisualizer.algorithms.Algorithm;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridSquare;

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
