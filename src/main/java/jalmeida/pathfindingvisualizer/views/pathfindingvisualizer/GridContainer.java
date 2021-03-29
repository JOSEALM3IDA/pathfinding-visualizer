package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import org.atmosphere.inject.annotation.ApplicationScoped;

import java.util.ArrayList;

@CssImport("./grid.css")
@ApplicationScoped
public class GridContainer {

    private int nCols;
    private int nRows;
    final Div container;
    ArrayList<GridSquare> squares;

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
    }

    public void setCols(int cols) {
        this.nCols = cols;
        redrawGrid();
    }

    public void setRows(int rows) {
        this.nRows = rows;
        redrawGrid();
    }

    private void redrawGrid() {
        container.removeAll();
        container.getElement().executeJs("document.getElementById(" +
                "\"grid-container-id\").style.gridTemplateColumns = \"repeat("
                + nCols + ", 1fr)\"");

        System.out.println("Cols: " + nCols + "; Rows: " + nRows);
        for (int i = 0; i < nCols * nRows; i++)
            addSquare();

        if (nCols * nRows > 10)
            getSquareAt(2, 2).setColor("green");
    }

    private void addSquare() {
        GridSquare sqr = new GridSquare();
        container.add(sqr.getContainer());
        squares.add(sqr);
    }

    public void clearGrid() {
        for (GridSquare sqr : squares)
            sqr.reset();
    }

    public GridSquare getSquareAt(int line, int col) {
        return squares.get(line * nCols + col);
    }

    public Div getContainer() {
        return container;
    }


}
