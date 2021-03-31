package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.DomListenerRegistration;

import java.util.ArrayList;

@CssImport("./grid.css")
public class GridSquare {

    static final String DEFAULT_CLASS = "grid-s-default";
    static final String OBSTACLE_CLASS = "grid-s-obstacle";
    static final String START_POINT_CLASS = "grid-s-start-point";
    static final String END_POINT_CLASS = "grid-s-end-point";

    Div container;
    String currClass;
    ArrayList<DomListenerRegistration> listeners;
    GridContainer grid;

    public GridSquare(GridContainer grid) {
        this.grid = grid;

        container = new Div();
        container.setClassName("grid-square");
        setClass(DEFAULT_CLASS);

        listeners = new ArrayList<>();

        attachListeners();
    }

    private void attachListeners() {
        listeners.add(container.getElement().addEventListener("mouseenter", e -> { if (grid.isMousePressed()) handleMouseClick(); }));
        listeners.add(container.getElement().addEventListener("mousedown", e -> handleMouseClick()));
    }

    public boolean handleMouseClick() {
        if (currClass.equals(START_POINT_CLASS) || currClass.equals(END_POINT_CLASS)) return false;

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

    public void setAsDefault() { setClass(DEFAULT_CLASS); }

    public void setAsObstacle() { setClass(OBSTACLE_CLASS); }

    public void setAsStartPoint() {
        setClass(START_POINT_CLASS);
        grid.getContainer().removeClassName("cursor-crosshair-start");
        grid.setActiveStartPointPlacement(false);
        grid.setStartPoint(this);
    }

    public void setAsEndPoint() {
        setClass(END_POINT_CLASS);
        grid.getContainer().removeClassName("cursor-crosshair-end");
        grid.setActiveEndPointPlacement(false);
        grid.setEndPoint(this);
    }

    private void setClass(String cssClass) {
        removeAllClasses();
        container.addClassName(cssClass);
        this.currClass = cssClass;
    }

    private void removeAllClasses() {
        container.removeClassName(DEFAULT_CLASS);
        container.removeClassName(START_POINT_CLASS);
        container.removeClassName(END_POINT_CLASS);
        container.removeClassName(OBSTACLE_CLASS);
    }

    public Div getContainer() {
        return container;
    }

    public void reset() {
        setClass(DEFAULT_CLASS);
    }

    public void resetObstacle() {
        if (currClass.equals(OBSTACLE_CLASS))
            setClass(DEFAULT_CLASS);
    }
}
