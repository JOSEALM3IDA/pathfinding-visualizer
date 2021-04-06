package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.DomListenerRegistration;

import java.util.ArrayList;

@CssImport("./grid.css")
public class GridSquare {

    private static final String GRAB_CLASS = "grab";
    private static final String DEFAULT_CLASS = "grid-default";
    private static final String OBSTACLE_CLASS = "grid-s-obstacle";
    private static final String START_POINT_CLASS = "grid-start-point";
    private static final String END_POINT_CLASS = "grid-end-point";
    private static final String TO_SEARCH_CLASS = "grid-s-to-search";
    private static final String SEARCHED_CLASS = "grid-s-searched";

    private Div container;
    private String currClass;
    private String oldClass;
    private ArrayList<DomListenerRegistration> listeners;
    private GridContainer grid;

    private int cost;

    public GridSquare(GridContainer grid) {
        this.grid = grid;

        container = new Div();
        container.setClassName("grid-square");
        setClass(DEFAULT_CLASS);

        listeners = new ArrayList<>();

        attachListeners();

        cost = 1;
    }

    private void attachListeners() {
        listeners.add(container.getElement().addEventListener("mouseenter", e -> { if (grid.isMousePressed()) handleMouseClick(); }));
        listeners.add(container.getElement().addEventListener("mousedown", e -> handleMouseDown()));
    }

    public void handleMouseClick() {
        if (isStartPoint() || isEndPoint()) { return; }

        if (grid.isActiveStartPointDrag()) { // || grid.isActiveStartPointPlacement()
            setAsStartPoint();
        } else if (grid.isActiveEndPointDrag()) { // || grid.isActiveEndPointPlacement()
            setAsEndPoint();
        } else if (grid.isActiveObstaclePlacement()) {
            setAsObstacle();
        } else {
            setAsDefault();
        }
    }

    private void handleMouseDown() {
        if (isStartPoint() && !grid.isActiveEndPointDrag()) { grid.setActiveStartPointDrag(true);
        } else if (isEndPoint() && !grid.isActiveStartPointDrag()) { grid.setActiveEndPointDrag(true); }

        handleMouseClick();
    }

    public boolean isStartPoint() { return currClass.equals(START_POINT_CLASS); }
    public boolean isEndPoint() { return currClass.equals(END_POINT_CLASS); }
    public boolean isObstacle() { return currClass.equals(OBSTACLE_CLASS); }
    public boolean isToSearch() { return currClass.equals(TO_SEARCH_CLASS); }
    public boolean isSearched() { return currClass.equals(SEARCHED_CLASS); }

    public void setGrab(boolean value) {
        if (isEndPoint() || isStartPoint()) {
            if (value)
                container.addClassName(GRAB_CLASS);
            else
                container.removeClassName(GRAB_CLASS);
        }
    }

    public void setAsDefault() { setClass(DEFAULT_CLASS); }
    public void setAsObstacle() { setClass(OBSTACLE_CLASS); }
    public void setAsToSearch() { if (!(isStartPoint() || isEndPoint())) setClass(TO_SEARCH_CLASS); }
    public void setAsSearched() { if (!(isStartPoint() || isEndPoint())) setClass(SEARCHED_CLASS); }

    public boolean setAsStartPoint() {
        if (isEndPoint())
            return false;

        setClass(START_POINT_CLASS);
        //grid.getContainer().removeClassName("cursor-crosshair-start");
        //grid.setActiveStartPointPlacement(false);
        //setGrab(true);
        if (!grid.isActiveStartPointDrag())
            setGrab(true);

        grid.setStartPoint(this);

        return true;
    }

    public boolean setAsEndPoint() {
        if (isStartPoint())
            return false;

        setClass(END_POINT_CLASS);
        //grid.getContainer().removeClassName("cursor-crosshair-end");
        //grid.setActiveEndPointPlacement(false);
        if (!grid.isActiveEndPointDrag())
            setGrab(true);

        grid.setEndPoint(this);

        return true;
    }

    private void setClass(String cssClass) {
        oldClass = currClass;
        removeAllClasses();
        container.addClassName(cssClass);
        currClass = cssClass;
    }

    public void setAsLastState() { setClass(oldClass); }

    private void removeAllClasses() {
        container.removeClassName(DEFAULT_CLASS);
        container.removeClassName(START_POINT_CLASS);
        container.removeClassName(END_POINT_CLASS);
        container.removeClassName(OBSTACLE_CLASS);
        container.removeClassName(SEARCHED_CLASS);
        container.removeClassName(TO_SEARCH_CLASS);
    }

    public Div getContainer() {
        return container;
    }

    public int getCost() { return cost; }

    public void reset() {
        setClass(DEFAULT_CLASS);
    }

    public void resetObstacle() {
        if (currClass.equals(OBSTACLE_CLASS))
            setClass(DEFAULT_CLASS);
    }

    public void resetSolution() {
        if (currClass.equals(SEARCHED_CLASS) || currClass.equals(TO_SEARCH_CLASS))
            setClass(DEFAULT_CLASS);
    }
}
