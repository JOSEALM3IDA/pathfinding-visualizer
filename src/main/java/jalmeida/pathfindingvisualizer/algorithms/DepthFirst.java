package jalmeida.pathfindingvisualizer.algorithms;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridSquare;

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
            currNode.setAsSearched();
            if (currNode.isEndPoint())
                break;

            neighbours = gridContainer.getNeighbours(currNode);
            for (int i = neighbours.size() - 1; i >= 0; i--)
                if (!neighbours.get(i).isSearched()) {
                    neighbours.get(i).setAsToSearch();
                    stack.push(neighbours.get(i));
                }
        }
    }


}
