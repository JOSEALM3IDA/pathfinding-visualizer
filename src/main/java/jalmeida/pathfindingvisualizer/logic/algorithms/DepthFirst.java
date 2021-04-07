package jalmeida.pathfindingvisualizer.logic.algorithms;

import jalmeida.pathfindingvisualizer.logic.Algorithm;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridSquare;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirst extends Algorithm {

    public DepthFirst(GridContainer gridContainer) {
        super(gridContainer);
    }

    @Override
    public void solve() {
        startPoint = gridContainer.getStartPoint();
        endPoint = gridContainer.getEndPoint();

        Stack<GridSquare> stack = new Stack<>();
        stack.push(startPoint);

        ArrayList<GridSquare> neighbours;
        GridSquare currNode;
        while (!stack.isEmpty()) {
            currNode = stack.pop();
            //ui.access(currNode::setAsSearched);
            currNode.setAsSearched();
            if (currNode.isEndPoint())
                break;

            neighbours = gridContainer.getNeighbours(currNode);
            for (int i = neighbours.size() - 1; i >= 0; i--)
                if (!neighbours.get(i).isSearched()) {
                    //final GridSquare toUpdate = neighbours.get(i);
                    //ui.access(toUpdate::setAsToSearch);
                    neighbours.get(i).setAsToSearch();
                    stack.push(neighbours.get(i));
                }
        }
    }


}
