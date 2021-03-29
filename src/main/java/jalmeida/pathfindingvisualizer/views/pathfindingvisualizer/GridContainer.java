package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import org.atmosphere.inject.annotation.ApplicationScoped;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@CssImport("./grid.css")
@ApplicationScoped
public class GridContainer {

    private int nCols;
    private int nRows;
    private final Div container;
    private ArrayList<GridSquare> squares;
    private AtomicBoolean isMousePressed;
    private AtomicBoolean isActiveObstaclePlacement;

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

        isActiveObstaclePlacement = new AtomicBoolean(true);
        isMousePressed = new AtomicBoolean(false);
        container.getElement().addEventListener("mousedown", e -> { isMousePressed.set(true); });
        container.getElement().addEventListener("mouseup", e -> { isMousePressed.set(false); });
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

        for (int i = 0; i < nCols * nRows; i++)
            addSquare();
    }

    private void addSquare() {
        GridSquare sqr = new GridSquare(this);
        container.add(sqr.getContainer());
        squares.add(sqr);
    }

    public void clearGrid() {
        for (GridSquare sqr : squares)
            sqr.reset();
    }

    public void setObstaclePlacement(Boolean value) { isActiveObstaclePlacement.set(value); }

    public GridSquare getSquareAt(int line, int col) {
        return squares.get(line * nCols + col);
    }

    public Div getContainer() {
        return container;
    }

    public boolean isMousePressed() { return isMousePressed.get(); }

    public boolean isActiveObstaclePlacement() { return isActiveObstaclePlacement.get(); }

}
