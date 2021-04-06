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

    protected float getEstimation(GridSquare start, GridSquare end) {
        int[] startPos = gridContainer.getPosOfSquare(start);
        int[] endPos = gridContainer.getPosOfSquare(end);

        int x1 = startPos[0];
        int x2 = endPos[0];
        int y1 = startPos[1];
        int y2 = endPos[1];

        return (float) Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y2-y1, 2));
    }

    protected void sleep() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void setUI(UI ui) { this.ui = ui; }
}
