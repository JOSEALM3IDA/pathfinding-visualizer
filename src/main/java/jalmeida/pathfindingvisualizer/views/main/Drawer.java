package jalmeida.pathfindingvisualizer.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.CssImport;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;

import java.awt.*;

@JsModule("./shared-styles.js")
@CssImport("./drawer.css")
public class Drawer extends AppLayout {
    private static final int INIT_COLS = 100;
    private static final int INIT_ROWS = 45;

    private TextField colsField;
    private TextField rowsField;
    private Button resetButton;
    private Checkbox obstacleCheckbox;

    private final GridContainer gridContainer;
    private final Component drawerContent;

    public Drawer(GridContainer gridContainer) {
        this.gridContainer = gridContainer;
        drawerContent = createDrawerContent();
    }

    private Component createDrawerContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Image lsvLogo = new Image("images/logo.png", "pathfinder-visualizer logo");
        lsvLogo.setWidth("3em");
        lsvLogo.setHeight("3em");

        logoLayout.add(lsvLogo);
        logoLayout.add(new H1("Pathfinding Visualizer"));

        HorizontalLayout inputLayout = getGridSizeInputLayout();
        VerticalLayout buttonLayout = getButtonLayout();
        HorizontalLayout checkboxLayout = getCheckboxLayout();

        layout.add(logoLayout, inputLayout, checkboxLayout, buttonLayout);
        return layout;
    }

    private HorizontalLayout getGridSizeInputLayout() {
        colsField = new TextField("Columns");
        colsField.setClassName("input-field");
        colsField.setValue(Integer.toString(INIT_COLS));
        colsField.addValueChangeListener(e -> {
            onInputFieldChange(colsField, "columns");
        });

        gridContainer.setCols(INIT_COLS);

        rowsField = new TextField("Rows");
        rowsField.setClassName("input-field");
        rowsField.setValue(Integer.toString(INIT_ROWS));
        rowsField.addValueChangeListener(e -> {
            onInputFieldChange(rowsField, "rows");
        });

        gridContainer.setRows(INIT_ROWS);

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setClassName("input-field-container");
        hLayout.add(colsField, rowsField);
        return hLayout;
    }

    private VerticalLayout getButtonLayout() {
        resetButton = new Button("Reset Grid");
        resetButton.addClickListener(e -> {
            gridContainer.clearGrid();
        });

        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setClassName("button-container");
        vLayout.add(resetButton);

        return vLayout;
    }

    private HorizontalLayout getCheckboxLayout() {
        obstacleCheckbox = new Checkbox("Obstacle Placement");
        obstacleCheckbox.setValue(true);

        obstacleCheckbox.addValueChangeListener(e -> {
            gridContainer.setObstaclePlacement(obstacleCheckbox.getValue());
        });

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setClassName("checkbox-container");
        hLayout.add(obstacleCheckbox);

        return hLayout;
    }

    private void onInputFieldChange(TextField field, String name) {
        try {
            int value = Integer.parseInt(field.getValue());

            if (value <= 0)
                throw new NumberFormatException();

            if (name.equals("columns"))
                gridContainer.setCols(value);
            else if (name.equals("rows"))
                gridContainer.setRows(value);

        } catch (NumberFormatException exc) {
            throwErrorNotification("Number of " + name + " must be a positive integer!");
        }
    }

    private void throwErrorNotification(String msg) {
        Notification notification = new Notification(msg);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(8000);
        notification.open();
    }

    public Component getDrawerContent() {
        return drawerContent;
    }
}
