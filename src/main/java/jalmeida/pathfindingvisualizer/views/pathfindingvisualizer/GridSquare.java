package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;

@CssImport("./grid.css")
public class GridSquare {

    static final String DEFAULT_COLOR = "#f3f3f3";
    static final String OBSTACLE_COLOR = "#6b6b6b";
    static final String START_COLOR = "#ffd000";
    static final String OBJECTIVE_COLOR = "#a30000";

    Div container;
    String color;
    ArrayList<DomListenerRegistration> listeners;
    GridContainer grid;

    public GridSquare(GridContainer grid) {
        this.grid = grid;

        container = new Div();
        container.setClassName("grid-square");
        setColor(DEFAULT_COLOR);

        listeners = new ArrayList<>();

        attachListeners();
    }

    private void attachListeners() {
        listeners.add(container.getElement().addEventListener("mouseenter", e -> { if (grid.isMousePressed()) handleMouseClick(); }));
        listeners.add(container.getElement().addEventListener("mousedown", e -> { handleMouseClick(); }));
    }

    private void handleMouseClick() {
        if (grid.isActiveObstaclePlacement()) {
            if (!color.equals(OBSTACLE_COLOR) && grid.isActiveObstaclePlacement())
                setAsObstacle();
        } else {
            setAsDefault();
        }
    }

    public void setAsDefault() { setColor(DEFAULT_COLOR); }

    public void setAsObstacle() { setColor(OBSTACLE_COLOR); }

    public void setAsStart() { setColor(START_COLOR); }

    public void setAsObjective() { setColor(OBJECTIVE_COLOR); }

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
