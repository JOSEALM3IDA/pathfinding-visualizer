package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import org.atmosphere.inject.annotation.ApplicationScoped;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@CssImport("./grid.css")
@ApplicationScoped
public class GridContainer {

    private int nCols;
    private int nRows;
    private final Div container;
    private ArrayList<GridSquare> squares;

    private GridSquare startPoint;
    private GridSquare endPoint;

    private final AtomicBoolean isMousePressed;
    private final AtomicBoolean isActiveObstaclePlacement;
    private final AtomicBoolean isActiveStartPointPlacement;
    private final AtomicBoolean isActiveEndPointPlacement;

    public GridContainer() {
        this(0, 0);
    }

    public GridContainer(int initialCols, int initialRows) {
        container = new Div();
        container.setClassName("grid-container");
        container.setId("grid-container-id");

        squares = new ArrayList<>();

        nCols = initialCols;
        nRows = initialRows;

        setCols(nCols);
        setRows(nRows);

        isActiveStartPointPlacement = new AtomicBoolean(false);
        isActiveEndPointPlacement = new AtomicBoolean(false);

        isActiveObstaclePlacement = new AtomicBoolean(true);
        isMousePressed = new AtomicBoolean(false);
        container.getElement().addEventListener("mousedown", e -> isMousePressed.set(true));
        container.getElement().addEventListener("mouseup", e -> isMousePressed.set(false));
    }

    public void setCols(int cols) {
        this.nCols = cols;
        redrawGrid();
    }

    public void setRows(int rows) {
        this.nRows = rows;
        redrawGrid();
    }

    public void redrawGrid() {
        container.removeAll();
        container.getElement().executeJs("document.getElementById(" +
                "\"grid-container-id\").style.gridTemplateColumns = \"repeat("
                + nCols + ", 1fr)\"");

        squares.clear();
        for (int i = 0; i < nCols * nRows; i++)
            addSquare();

        initializeStartEndPoints();
    }

    private void initializeStartEndPoints() {
        Random rn = new Random();

        if (isActiveStartPointPlacement == null || isActiveEndPointPlacement == null || nCols * nRows < 4)
            return;

        setActiveStartPointPlacement(true);
        while (!squares.get(rn.nextInt(nCols * nRows)).handleMouseClick());
        setActiveStartPointPlacement(false);

        setActiveEndPointPlacement(true);
        while (!squares.get(rn.nextInt(nCols * nRows)).handleMouseClick());
        setActiveEndPointPlacement(false);
    }

    private void addSquare() {
        GridSquare sqr = new GridSquare(this);
        container.add(sqr.getContainer());
        squares.add(sqr);
    }

    public void clearGrid() {
        startPoint = null;
        endPoint = null;

        for (GridSquare sqr : squares)
            sqr.reset();

        initializeStartEndPoints();
    }

    public void clearObstacles() {
        for (GridSquare sqr : squares)
            sqr.resetObstacle();
    }

    public void setStartPoint(GridSquare gridSquare) {
        try {
            startPoint.setAsDefault();
        } catch (NullPointerException ignored) {}

        startPoint = gridSquare;
    }

    public void setEndPoint(GridSquare gridSquare) {
        try {
            endPoint.setAsDefault();
        } catch (NullPointerException ignored) {}

        endPoint = gridSquare;
    }

    public void setActiveObstaclePlacement(boolean value) { isActiveObstaclePlacement.set(value); }
    public void setActiveStartPointPlacement(boolean value) { isActiveStartPointPlacement.set(value); }
    public void setActiveEndPointPlacement(boolean value) { isActiveEndPointPlacement.set(value); }

    public GridSquare getSquareAt(int line, int col) { return squares.get(line * nCols + col); }

    public Div getContainer() { return container; }
    public int getnCols() { return nCols; }
    public int getnRows() { return nRows; }

    public boolean isMousePressed() { return isMousePressed.get(); }

    public boolean isActiveObstaclePlacement() { return isActiveObstaclePlacement.get(); }
    public boolean isActiveStartPointPlacement() { return isActiveStartPointPlacement.get(); }
    public boolean isActiveEndPointPlacement() { return isActiveEndPointPlacement.get(); }
}
