package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import jalmeida.pathfindingvisualizer.logic.Algorithm;
import org.atmosphere.inject.annotation.ApplicationScoped;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

@CssImport("./grid.css")
@ApplicationScoped
public class GridContainer {

    private static final String GRABBING_CLASS = "grabbing";

    private int nCols;
    private int nRows;
    private final Div container;
    private final ArrayList<GridSquare> gridSquares;

    private GridSquare startPoint;
    private GridSquare endPoint;

    private Algorithm currAlgorithm;
    private String currOperatorOrder;

    private boolean isSolved;
    private final AtomicBoolean isMousePressed;
    private final AtomicBoolean isActiveObstaclePlacement;
    private final AtomicBoolean isActiveStartPointDrag;
    private final AtomicBoolean isActiveEndPointDrag;

    public GridContainer() {
        this(0, 0);
    }

    public GridContainer(int initialCols, int initialRows) {
        container = new Div();
        container.setClassName("grid-container");
        container.setId("grid-container-id");

        gridSquares = new ArrayList<>();

        nCols = initialCols;
        nRows = initialRows;

        setCols(nCols);
        setRows(nRows);
        redrawGrid();

        isSolved = false;

        isActiveStartPointDrag = new AtomicBoolean(false);
        isActiveEndPointDrag = new AtomicBoolean(false);

        isActiveObstaclePlacement = new AtomicBoolean(true);
        isMousePressed = new AtomicBoolean(false);
        container.getElement().addEventListener("mousedown", e -> isMousePressed.set(true));
        container.getElement().addEventListener("mouseup", e -> {
            isMousePressed.set(false);
            setActiveStartPointDrag(false);
            setActiveEndPointDrag(false);
        });
    }

    public void setCols(int cols) { this.nCols = cols; }

    public void setRows(int rows) { this.nRows = rows; }

    public void redrawGrid() { redrawGrid(true); }

    public void redrawGrid(boolean doPointInit) {
        container.removeAll();
        container.getElement().executeJs("document.getElementById(" +
                "\"grid-container-id\").style.gridTemplateColumns = \"repeat("
                + nCols + ", 1fr)\"");

        gridSquares.clear();
        for (int i = 0; i < nCols * nRows; i++)
            addSquare();

        if (doPointInit)
            initializeStartEndPoints();
    }

    private void initializeStartEndPoints() {
        Random rn = new Random();

        if (nCols * nRows < 2)
            return;

        while (!gridSquares.get(rn.nextInt(nCols * nRows)).setAsStartPoint());
        while (!gridSquares.get(rn.nextInt(nCols * nRows)).setAsEndPoint());
    }

    private void addSquare() {
        GridSquare sqr = new GridSquare(this);
        container.add(sqr.getContainer());
        gridSquares.add(sqr);
    }

    public void clearGrid() {
        for (GridSquare sqr : gridSquares)
            sqr.reset();

        initializeStartEndPoints();
        isSolved = false;
    }

    public void clearObstacles() {
        for (GridSquare sqr : gridSquares)
            sqr.resetObstacle();
    }

    public void setOperatorOrder(String operatorOrder) { currOperatorOrder = operatorOrder; }

    public void solve() {
        if (currAlgorithm != null) {
            clearSolution();
            currAlgorithm.setStartPoint(startPoint);
            currAlgorithm.setEndPoint(endPoint);
            currAlgorithm.solve();
            isSolved = true;
        }
    }

    public void clearSolution() {
        if (isSolved) {
            for (GridSquare sqr : gridSquares)
                sqr.resetSolution();

            isSolved = false;
        }
    }

    public void setStartPoint(GridSquare gridSquare) {
        if (startPoint == gridSquare)
            return;

        try {
            startPoint.setAsLastState();
        } catch (NullPointerException ignored) {}

        startPoint = gridSquare;
    }

    public void setEndPoint(GridSquare gridSquare) {
        if (endPoint == gridSquare)
            return;

        try {
            endPoint.setAsLastState();
        } catch (NullPointerException ignored) {}

        endPoint = gridSquare;
    }

    public void setCurrAlgorithm(Algorithm algorithm) {
        currAlgorithm = algorithm;
    }

    private void setGrabbing(boolean value) {
        if (value)
            container.addClassName(GRABBING_CLASS);
        else
            container.removeClassName(GRABBING_CLASS);
    }

    public void setActiveObstaclePlacement(boolean value) { isActiveObstaclePlacement.set(value); }
    public void setActiveStartPointDrag(boolean value) {
        isActiveStartPointDrag.set(value);
        setGrabbing(value);
        startPoint.setGrab(!value);

        if (!value && isSolved)
            solve();
    }

    public void setActiveEndPointDrag(boolean value) {
        isActiveEndPointDrag.set(value);
        setGrabbing(value);
        endPoint.setGrab(!value);

        if (!value && isSolved)
            solve();
    }

    public GridSquare getSquareAt(int row, int col) { return gridSquares.get(row * nCols + col); }

    public int[] getPosOfSquare(GridSquare gridSquare) {
        int[] pos = {-1, -1};

        for (int row = 0; row < nRows; row++)
            for (int col = 0; col < nCols; col++)
                if (gridSquare.equals(getSquareAt(row, col))) {
                    pos[0] = row;
                    pos[1] = col;
                }

        return pos;
    }

    public ArrayList<GridSquare> getNeighbours(GridSquare gridSquare) {
        int[] pos = getPosOfSquare(gridSquare);

        if (pos[0] == -1 || pos[1] == -1) {
            System.out.println("GRID SQUARE NOT FOUND!");
            return null;
        }

        ArrayList<GridSquare> neighbours = new ArrayList<>();
        GridSquare neighbour;
        for (int i = 0; i < currOperatorOrder.length(); i++) {
            switch (currOperatorOrder.toCharArray()[i]) {
                case 'U':
                    if (pos[0] != 0) {
                        neighbour = getSquareAt(pos[0] - 1, pos[1]);
                        if (!neighbour.isObstacle())
                            neighbours.add(neighbour);
                    }
                    break;
                case 'D':
                    if (pos[0] != nRows - 1) {
                        neighbour = getSquareAt(pos[0] + 1, pos[1]);
                        if (!neighbour.isObstacle())
                            neighbours.add(neighbour);
                    }
                    break;
                case 'L':
                    if (pos[1] != 0) {
                        neighbour = getSquareAt(pos[0], pos[1] - 1);
                        if (!neighbour.isObstacle())
                            neighbours.add(neighbour);
                    }
                    break;
                case 'R':
                    if (pos[1] != nCols - 1) {
                        neighbour = getSquareAt(pos[0], pos[1] + 1);
                        if (!neighbour.isObstacle())
                            neighbours.add(neighbour);
                    }
                    break;

                default:
                    System.out.println("UNEXPECTED ERROR GETTING NEIGHBOURS!");
            }
        }

        return neighbours;
    }

    public String getOperatorOrder() { return currOperatorOrder; }

    public GridSquare getStartPoint() {
        return startPoint;
    }

    public GridSquare getEndPoint() {
        return endPoint;
    }

    public Div getContainer() { return container; }
    public int getnCols() { return nCols; }
    public int getnRows() { return nRows; }

    public boolean isMousePressed() { return isMousePressed.get(); }

    public boolean isSolved() { return isSolved; }

    public boolean isActiveObstaclePlacement() { return isActiveObstaclePlacement.get(); }
    public boolean isActiveStartPointDrag() { return isActiveStartPointDrag.get(); }
    public boolean isActiveEndPointDrag() { return isActiveEndPointDrag.get(); }

    public void addRandomObstacles(double chance) {
        clearSolution();
        clearObstacles();

        for (GridSquare gridSquare : gridSquares)
            if (!(gridSquare.isEndPoint() || gridSquare.isStartPoint()) && Math.random() < chance)
                gridSquare.setAsObstacle();
    }

    public void loadFromCharMatrix(ArrayList<ArrayList<Character>> info) {
        setRows(info.size());
        setCols(info.get(0).size());
        redrawGrid(false);

        for (int row = 0; row < nRows; row++)
            for (int col = 0; col < nCols; col++)
                getSquareAt(row, col).setFromValue(info.get(row).get(col));

    }

    @Override
    public String toString() {
        StringBuilder rtn = new StringBuilder();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                rtn.append(getSquareAt(row, col).toString());
            }

            if (row != nRows - 1)
                rtn.append('\n');
        }

        return rtn.toString();
    }
}
