package jalmeida.pathfindingvisualizer.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.PWA;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.grid.GridContainer;


/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "Pathfinding Visualizer", shortName = "PFV", enableInstallPrompt = false)
@JsModule("./shared-styles.js")
@CssImport("./main-view.css")

public class MainView extends AppLayout {

    private final Drawer drawer;
    private GridContainer gridContainer;

    public MainView() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());

        gridContainer = new GridContainer();
        drawer = new Drawer(gridContainer);

        ComponentUtil.setData(UI.getCurrent(), GridContainer.class, gridContainer);

        addToDrawer(drawer.getDrawerContent());
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        DrawerToggle dt = new DrawerToggle();
        dt.setId("drawer-toggle");

        layout.add(dt);
        return layout;
    }
}
