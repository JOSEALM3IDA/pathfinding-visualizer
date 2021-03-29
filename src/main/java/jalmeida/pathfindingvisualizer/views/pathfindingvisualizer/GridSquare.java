package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;

@CssImport("./grid.css")
public class GridSquare {

    static final String DEFAULT_COLOR = "#f3f3f3";

    Div container;
    String color;

    public GridSquare() {
        container = new Div();
        container.setClassName("grid-square");
        setColor(DEFAULT_COLOR);
    }

    public void setColor(String color) {
        container.getElement().getStyle().set("backgroundColor", color);
        this.color = color;
    }

    public Div getContainer() {
        return container;
    }

    public void reset() {
        setColor(DEFAULT_COLOR);
    }
}
