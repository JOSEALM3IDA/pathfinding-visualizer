package jalmeida.pathfindingvisualizer.algorithms;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridSquare;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Algorithm {

    protected GridContainer gridContainer;
    protected GridSquare startPoint;
    protected GridSquare endPoint;
    protected Set<GridSquare> toSearch;
    protected Set<GridSquare> searched;

    public Algorithm(GridContainer gridContainer, GridSquare startPoint, GridSquare endPoint) {
        this.gridContainer = gridContainer;
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        toSearch = new LinkedHashSet<>();
        searched = new LinkedHashSet<>();
    }

    public abstract void solve();

    protected void clearToSearch() { toSearch.clear(); }
    protected void clearSearched() { searched.clear(); }

    protected boolean addSearched(GridSquare gridSquare) {
        if (!(gridSquare.isStartPoint() || gridSquare.isEndPoint()))
            gridSquare.setAsSearched();
        return searched.add(gridSquare);
    }

    protected boolean addToSearch(GridSquare gridSquare) {
        if (!(gridSquare.isStartPoint() || gridSquare.isEndPoint()))
            gridSquare.setAsToSearch();
        return toSearch.add(gridSquare);
    }

    protected ArrayList<GridSquare> removeRepeated(ArrayList<GridSquare> list) {
        list.removeIf(gs -> searched.contains(gs));
        return list;
    }
}
