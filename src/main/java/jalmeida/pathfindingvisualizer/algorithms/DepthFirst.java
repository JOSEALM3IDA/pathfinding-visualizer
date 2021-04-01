package jalmeida.pathfindingvisualizer.algorithms;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridSquare;

import java.util.ArrayList;

public class DepthFirst extends Algorithm {

    public DepthFirst(GridContainer gridContainer, GridSquare startPoint, GridSquare endPoint) {
        super(gridContainer, startPoint, endPoint);
    }

    @Override
    public void solve() {
        solveNode(startPoint);
    }

    private boolean solveNode(GridSquare node) {
        if (node.isEndPoint())
            return true;

        addSearched(node);

        ArrayList<GridSquare> neighbours = gridContainer.getNeighbours(node);
        for (GridSquare neighbour : neighbours) {
            addToSearch(neighbour);
            if (solveNode(neighbour))
                return true;
        }

        return false;
    }
}
