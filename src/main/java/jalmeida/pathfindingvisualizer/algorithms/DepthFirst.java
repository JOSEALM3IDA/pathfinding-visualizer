package jalmeida.pathfindingvisualizer.algorithms;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridSquare;

import java.util.ArrayList;

public class DepthFirst extends Algorithm {

    public DepthFirst(GridContainer gridContainer) {
        super(gridContainer);
    }

    @Override
    public void solve() {
        startPoint = gridContainer.getStartPoint();
        endPoint = gridContainer.getEndPoint();
        clear();
        solveNode(startPoint);
    }

    private boolean solveNode(GridSquare node) {
        //sleep();

        addSearched(node);
        if (node.isEndPoint())
            return true;

        ArrayList<GridSquare> neighbours = gridContainer.getNeighbours(node);
        neighbours = removeRepeated(neighbours);

        for (GridSquare neighbour : neighbours)
            addToSearch(neighbour);

        for (GridSquare neighbour : neighbours)
            if (solveNode(neighbour))
                return true;

        return false;
    }
}
