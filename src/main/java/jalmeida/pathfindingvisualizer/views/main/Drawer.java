package jalmeida.pathfindingvisualizer.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
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

@JsModule("./shared-styles.js")
@CssImport("./drawer.css")
public class Drawer extends AppLayout {
    private static final int INIT_COLS = 100;
    private static final int INIT_ROWS = 45;

    private TextField colsField;
    private TextField rowsField;
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

        HorizontalLayout hLayout = getInputBoxLayout();

        layout.add(logoLayout, hLayout);
        return layout;
    }

    private HorizontalLayout getInputBoxLayout() {
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

    private void onInputFieldChange(TextField field, String name) {
        try {
            int value = Integer.parseInt(field.getValue());

            if (value <= 0)
                throw new NumberFormatException();

            gridContainer.setCols(value);
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
