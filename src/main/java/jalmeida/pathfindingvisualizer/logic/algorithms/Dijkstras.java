package jalmeida.pathfindingvisualizer.logic.algorithms;

import jalmeida.pathfindingvisualizer.logic.Node;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;

public class Dijkstras extends Astar {

    public Dijkstras(GridContainer gridContainer) { super(gridContainer); }

    @Override
    protected double getEstimatedCost(Node node) { return 0; }
}

