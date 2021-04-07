package jalmeida.pathfindingvisualizer.algorithms;

import com.vaadin.flow.component.UI;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridSquare;

public abstract class Algorithm {

    protected final int WAIT_TIME = 50; // ms

    protected GridContainer gridContainer;
    protected GridSquare startPoint;
    protected GridSquare endPoint;

    protected UI ui;

    public Algorithm(GridContainer gridContainer) { this.gridContainer = gridContainer; }

    public abstract void solve();

    public void setStartPoint(GridSquare startPoint) { this.startPoint = startPoint; }
    public void setEndPoint(GridSquare endPoint) { this.endPoint = endPoint; }

    protected void sleep() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void setUI(UI ui) { this.ui = ui; }
}
