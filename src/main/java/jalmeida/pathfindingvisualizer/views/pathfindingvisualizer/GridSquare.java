package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.DomListenerRegistration;

import java.util.ArrayList;

@CssImport("./grid.css")
public class GridSquare {

    static final String DEFAULT_COLOR = "#f3f3f3";
    static final String OBSTACLE_COLOR = "#6b6b6b";
    static final String START_POINT_COLOR = "#ffd000";
    static final String END_POINT_COLOR = "#a30000";

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
        listeners.add(container.getElement().addEventListener("mousedown", e -> handleMouseClick()));
    }

    public boolean handleMouseClick() {
        if (color.equals(START_POINT_COLOR) || color.equals(END_POINT_COLOR)) return false;

        if (grid.isActiveStartPointPlacement()) {
            setAsStartPoint();
        } else if (grid.isActiveEndPointPlacement()) {
            setAsEndPoint();
        } else if (grid.isActiveObstaclePlacement()) {
            setAsObstacle();
        } else {
            setAsDefault();
        }

        return true;
    }

    public void setAsDefault() { setColor(DEFAULT_COLOR); }

    public void setAsObstacle() { setColor(OBSTACLE_COLOR); }

    public void setAsStartPoint() {
        setColor(START_POINT_COLOR);
        grid.getContainer().removeClassName("cursor-crosshair-start");
        grid.setActiveStartPointPlacement(false);
        grid.setStartPoint(this);
    }

    public void setAsEndPoint() {
        setColor(END_POINT_COLOR);
        grid.getContainer().removeClassName("cursor-crosshair-end");
        grid.setActiveEndPointPlacement(false);
        grid.setEndPoint(this);
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
