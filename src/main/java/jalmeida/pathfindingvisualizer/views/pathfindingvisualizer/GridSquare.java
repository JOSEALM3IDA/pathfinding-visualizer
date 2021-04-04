package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.DomListenerRegistration;

import java.util.ArrayList;

@CssImport("./grid.css")
public class GridSquare {

    private static final String DEFAULT_CLASS = "grid-s-default";
    private static final String OBSTACLE_CLASS = "grid-s-obstacle";
    private static final String START_POINT_CLASS = "grid-s-start-point";
    private static final String END_POINT_CLASS = "grid-s-end-point";
    private static final String TO_SEARCH_CLASS = "grid-s-to-search";
    private static final String SEARCHED_CLASS = "grid-s-searched";

    private Div container;
    private String currClass;
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

    public boolean isStartPoint() {
        return currClass.equals(START_POINT_CLASS);
    }
    public boolean isEndPoint() {
        return currClass.equals(END_POINT_CLASS);
    }
    public boolean isObstacle() {
        return currClass.equals(OBSTACLE_CLASS);
    }
    public boolean isToSearch() { return currClass.equals(TO_SEARCH_CLASS); }
    public boolean isSearched() { return currClass.equals(SEARCHED_CLASS); }

    public void setAsDefault() { setClass(DEFAULT_CLASS); }
    public void setAsObstacle() { setClass(OBSTACLE_CLASS); }
    public void setAsToSearch() { if (!(isStartPoint() || isEndPoint())) setClass(TO_SEARCH_CLASS); }
    public void setAsSearched() { if (!(isStartPoint() || isEndPoint())) setClass(SEARCHED_CLASS); }

    public void setAsStartPoint() {
        setClass(START_POINT_CLASS);
        grid.getContainer().removeClassName("cursor-crosshair-start");
        grid.setActiveStartPointPlacement(false);
        grid.setStartPoint(this);

        if (grid.isSolved())
            grid.solve();
    }

    public void setAsEndPoint() {
        setClass(END_POINT_CLASS);
        grid.getContainer().removeClassName("cursor-crosshair-end");
        grid.setActiveEndPointPlacement(false);
        grid.setEndPoint(this);

        if (grid.isSolved())
            grid.solve();
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
