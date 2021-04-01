package jalmeida.pathfindingvisualizer.algorithms;

import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridSquare;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Algorithm {

    protected final int WAIT_TIME = 100; // ms

    protected GridContainer gridContainer;
    protected GridSquare startPoint;
    protected GridSquare endPoint;
    protected Set<GridSquare> toSearch;
    protected Set<GridSquare> searched;

    public Algorithm(GridContainer gridContainer) {
        this.gridContainer = gridContainer;

        toSearch = new LinkedHashSet<>();
        searched = new LinkedHashSet<>();
    }

    public abstract void solve();

    public void clear() {
        clearToSearch();
        clearSearched();
    }

    public void setStartPoint(GridSquare startPoint) { this.startPoint = startPoint; }
    public void setEndPoint(GridSquare endPoint) { this.endPoint = endPoint; }

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

    protected void sleep() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
