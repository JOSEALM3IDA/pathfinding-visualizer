package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import org.atmosphere.inject.annotation.ApplicationScoped;

import java.util.ArrayList;

@ApplicationScoped
public class GridContainer {

    private int nCols;
    private int nRows;
    final Div container;
    ArrayList<Div> squares;

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

        for (int j = 0; j < nRows; j++) {
            for (int i = 0; i < nCols; i++) {
                addSquare();
            }
        }
    }

    public Div getContainer() {
        return container;
    }

    private void addSquare() {
        Div sqr = new Div();
        sqr.setClassName("grid-square");
        squares.add(sqr);
        container.add(sqr);
    }
}
