package jalmeida.pathfindingvisualizer.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.CssImport;
import jalmeida.pathfindingvisualizer.algorithms.Algorithm;
import jalmeida.pathfindingvisualizer.algorithms.DepthFirst;
import jalmeida.pathfindingvisualizer.views.pathfindingvisualizer.GridContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@JsModule("./shared-styles.js")
@CssImport("./drawer.css")
public class Drawer extends AppLayout {
    private static final int INIT_COLS = 100;
    private static final int INIT_ROWS = 45;
    private static final String DEPTH_FIRST = "Depth-First Search";
    private static final String A_STAR = "A*";

    private Label optionsLabel;
    private TextField colsField;
    private TextField rowsField;
    private Button resetObstaclesButton;
    private Button setStartPointButton;
    private Button setEndPointButton;
    private Button startButton;
    private Button clearButton;
    private Checkbox obstacleCheckbox;
    private Select<String> algorithmSelect;

    private Label extraLabel;
    private Label operatorOrderLabel;
    private ArrayList<IntegerField> operatorOrderFields;
    private String operatorOrder;

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

        optionsLabel = new Label("General Options");
        optionsLabel.setClassName("title-label");

        HorizontalLayout inputLayout = getGridSizeInputLayout();
        VerticalLayout buttonLayout = getButtonLayout();
        HorizontalLayout checkboxLayout = getCheckboxLayout();
        VerticalLayout selectLayout = getSelectLayout();
        VerticalLayout extraLayout = getExtraLayout();

        layout.add(logoLayout, optionsLabel, inputLayout, checkboxLayout, selectLayout, buttonLayout, extraLayout);
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
        clearButton = new Button("Clear");
        clearButton.addClickListener(e -> gridContainer.clearSolution());

        startButton = new Button("Start");
        startButton.addClickListener(e -> {
            if (!validateOptions())
                return;

            clearButton.click();

            updateOperatorOrder();
            gridContainer.solve();
        });

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setClassName("button-container-start-clear");
        hLayout.add(startButton);
        hLayout.add(clearButton);

        setStartPointButton = new Button("Set Start Point");
        setStartPointButton.addClickListener(e -> {
            if (!gridContainer.isActiveStartPointPlacement()) {
                setStartPointPlacement(true);
                setEndPointPlacement(false);
            } else {
                setStartPointPlacement(false);
            }
        });

        setEndPointButton = new Button("Set End Point");
        setEndPointButton.addClickListener(e -> {
            if (!gridContainer.isActiveEndPointPlacement()) {
                setEndPointPlacement(true);
                setStartPointPlacement(false);
            } else {
                setEndPointPlacement(false);
            }
        });

        resetObstaclesButton = new Button("Reset Obstacles");
        resetObstaclesButton.addClickListener(e -> gridContainer.clearObstacles());

        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setClassName("button-container");
        vLayout.add(setStartPointButton, setEndPointButton, hLayout, resetObstaclesButton);

        return vLayout;
    }

    private HorizontalLayout getCheckboxLayout() {
        obstacleCheckbox = new Checkbox("Obstacle Placement");
        setObstaclePlacement(true);

        obstacleCheckbox.addValueChangeListener(e -> {
            setObstaclePlacement(obstacleCheckbox.getValue());
        });

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setClassName("checkbox-container");
        hLayout.add(obstacleCheckbox);

        return hLayout;
    }

    private VerticalLayout getSelectLayout() {
        algorithmSelect = new Select<>();
        algorithmSelect.setLabel("Algorithm");
        algorithmSelect.setItems(DEPTH_FIRST, A_STAR);
        algorithmSelect.addValueChangeListener(e -> {
            Algorithm algorithm;
            switch (algorithmSelect.getValue()) {
                default:
                case DEPTH_FIRST:
                    algorithm = new DepthFirst(gridContainer);
                    break;
            }
            gridContainer.setCurrAlgorithm(algorithm);
        });

        algorithmSelect.setValue(DEPTH_FIRST);


        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setClassName("select-container");
        vLayout.add(algorithmSelect);

        return vLayout;
    }

    private VerticalLayout getExtraLayout() {
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.add(new Hr());

        extraLabel = new Label("Extra Options");
        extraLabel.addClassName("title-label");
        vLayout.add(extraLabel);

        operatorOrderLabel = new Label("Operator Order");
        operatorOrderLabel.addClassName("subtitle-label");
        vLayout.add(operatorOrderLabel);

        operatorOrderFields = new ArrayList<IntegerField>(4);
        operatorOrderFields.add(new IntegerField("Up"));
        operatorOrderFields.add(new IntegerField("Down"));
        operatorOrderFields.add(new IntegerField("Left"));
        operatorOrderFields.add(new IntegerField("Right"));

        operatorOrderFields.get(0).setValue(1);
        operatorOrderFields.get(1).setValue(3);
        operatorOrderFields.get(2).setValue(4);
        operatorOrderFields.get(3).setValue(2);

        operatorOrder = "UDLR";

        for (IntegerField f : operatorOrderFields) {
            f.setClassName("operator-num-field");
            f.setMax(4);
            f.setMin(1);
            f.setHasControls(true);
        }

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setClassName("operator-num-field-pair");
        hLayout.add(operatorOrderFields.get(0));
        hLayout.add(operatorOrderFields.get(1));
        vLayout.add(hLayout);


        HorizontalLayout hLayout2 = new HorizontalLayout();
        hLayout2.setClassName("operator-num-field-pair");
        hLayout2.add(operatorOrderFields.get(2));
        hLayout2.add(operatorOrderFields.get(3));
        vLayout.add(hLayout2);

        return vLayout;
    }

    private void updateOperatorOrder() {
        HashMap<Integer, Character> hm = new HashMap<>();

        hm.put(0, 'U');
        hm.put(1, 'D');
        hm.put(2, 'L');
        hm.put(3, 'R');

        StringBuilder operatorOrder = new StringBuilder();
        operatorOrder.append(gridContainer.getOperatorOrder());

        if (operatorOrder.length() == 0)
            operatorOrder.append("----");

        for (int i = 0; i < operatorOrderFields.size(); i++)
            operatorOrder.setCharAt(operatorOrderFields.get(i).getValue() - 1, hm.get(i));

        gridContainer.setOperatorOrder(operatorOrder.toString());
    }

    private boolean validateOptions() { return handleOperatorOrderValidity(); }

    private boolean handleOperatorOrderValidity() {
        boolean isValid = true;
        HashSet<IntegerField> invalidFields = new HashSet<>();

        for (IntegerField intf : operatorOrderFields)
            intf.setInvalid(false);

        for (IntegerField intf : operatorOrderFields)
            if (!isValidOperatorOrderField(intf)) {
                isValid = false;
                invalidFields.add(intf);
            }

        for (IntegerField intf : invalidFields)
            intf.setInvalid(true);

        if (!isValid)
            throwErrorNotification("Operator order must be unique!");
        else
            updateOperatorOrder();

        return isValid;
    }

    private boolean isValidOperatorOrderField(IntegerField intf) {
        int value = intf.getValue();
        boolean isInvalid = false;
        for (IntegerField field : operatorOrderFields) {
            if (field == intf)
                continue;

            if (value == field.getValue())
                isInvalid = true;

        }

        return !isInvalid;
    }

    private void onInputFieldChange(TextField field, String name) {
        try {
            int value = Integer.parseInt(field.getValue());

            if (value <= 0)
                throw new NumberFormatException();

            if (name.equals("columns")) {
                if (gridContainer.getnRows() * value < 4)
                    throw new IllegalArgumentException();

                gridContainer.setCols(value);
            } else if (name.equals("rows")) {
                if (gridContainer.getnCols() * value < 4)
                    throw new IllegalArgumentException();

                gridContainer.setRows(value);
            }

        } catch (NumberFormatException exc) {
            throwErrorNotification("Number of " + name + " must be a positive integer!");
        } catch (IllegalArgumentException exc) {
            throwErrorNotification("There must be at least 4 squares on the grid!");
        }
    }

    private void setObstaclePlacement(boolean value) {
        obstacleCheckbox.setValue(value);
        gridContainer.setActiveObstaclePlacement(value);
    }

    private void setStartPointPlacement(boolean value) {
        if (value)
            gridContainer.getContainer().addClassName("cursor-crosshair-start");
        else
            gridContainer.getContainer().removeClassName("cursor-crosshair-start");

        gridContainer.setActiveStartPointPlacement(value);
    }

    private void setEndPointPlacement(boolean value) {
        if (value)
            gridContainer.getContainer().addClassName("cursor-crosshair-end");
        else
            gridContainer.getContainer().removeClassName("cursor-crosshair-end");

        gridContainer.setActiveEndPointPlacement(value);
    }

    private void throwErrorNotification(String msg) {
        Notification notification = new Notification(msg);
        notification.setPosition(Notification.Position.BOTTOM_END);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(8000);
        notification.open();
    }

    public Component getDrawerContent() {
        return drawerContent;
    }
}
