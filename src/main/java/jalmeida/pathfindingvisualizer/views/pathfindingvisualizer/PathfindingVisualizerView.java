package jalmeida.pathfindingvisualizer.views.pathfindingvisualizer;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import jalmeida.pathfindingvisualizer.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

import java.util.concurrent.atomic.AtomicBoolean;


@Route(value = "Pathfinding Visualizer", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Pathfinding Visualizer")
@CssImport("./pathfinding-visualizer-view.css")
public class PathfindingVisualizerView extends HorizontalLayout {

    private final GridContainer gridContainer;

    public PathfindingVisualizerView() {
        addClassName("pathfindingvisualizer-view");

        this.gridContainer = ComponentUtil.getData(UI.getCurrent(), GridContainer.class);
        add(gridContainer.getContainer());

    }
}
