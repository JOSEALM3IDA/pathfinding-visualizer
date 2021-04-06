package jalmeida.pathfindingvisualizer.algorithms;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridSquare;

import java.util.ArrayList;
import java.util.LinkedList;

public class BreadthFirst extends Algorithm {

    public BreadthFirst(GridContainer gridContainer) {
        super(gridContainer);
    }

    @Override
    public void solve() {
        startPoint = gridContainer.getStartPoint();
        endPoint = gridContainer.getEndPoint();

        LinkedList<GridSquare> queue = new LinkedList<>();
        queue.add(startPoint);

        ArrayList<GridSquare> neighbours;
        GridSquare currNode;
        while (queue.size() > 0) {
            currNode = queue.poll();
            while(queue.remove(currNode));
            ui.access(currNode::setAsSearched);

            if (currNode.isEndPoint())
                break;

            neighbours = gridContainer.getNeighbours(currNode);
            for (GridSquare neighbour : neighbours)
                if (!neighbour.isSearched()) {
                    ui.access(neighbour::setAsToSearch);
                    queue.add(neighbour);
                }
        }
    }
}
