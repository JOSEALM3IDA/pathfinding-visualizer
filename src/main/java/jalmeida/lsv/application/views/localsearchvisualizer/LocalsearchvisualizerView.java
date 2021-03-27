package jalmeida.lsv.application.views.localsearchvisualizer;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Buttons;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import jalmeida.lsv.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

import java.awt.*;
import java.util.ArrayList;

@Route(value = "Local Search Visualizer", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Local Search Visualizer")
@CssImport("./views/localsearchvisualizer/localsearchvisualizer-view.css")
public class LocalsearchvisualizerView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public LocalsearchvisualizerView() {
        /*addClassName("localsearchvisualizer-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });*/

        addClassName("localsearchvisualizer-view");

        float freeVw = 80;
        float buttonSide = 1f;
        float nButtons = freeVw/buttonSide;
        freeVw -= nButtons * 0.1;


        nButtons = (float) Math.ceil(nButtons);
        buttonSide = freeVw/nButtons;
/*
        Grid<Button> buttonGrid = new Grid<Button>();
        ArrayList<Button> buttonColumn = new ArrayList<Button>((int) nButtons);
        for (int i = 0; i < nButtons; i++) {
            Button b = new Button();
            b.setClassName("button-grid");
            buttonColumn.add(b);
        }

        buttonGrid.addColumn(new NativeButtonRenderer<Button>())*/

        ArrayList<ArrayList<Button>> buttonArray = new ArrayList<>((int) nButtons);
        add(new HtmlComponent("br"));
        for (int i = 0; i < nButtons; i++) {
            buttonArray.add(new ArrayList<Button>((int) nButtons));
            for (int j = 0; j < nButtons; j++) {
                Button b = new Button();
                b.setClassName("button-grid");
                b.setWidth(buttonSide + "vw");
                b.setHeight(buttonSide + "vw");
                buttonArray.get(i).add(b);
                add(b);
                setVerticalComponentAlignment(Alignment.END, b);
            }
            add(new HtmlComponent("br"));
        }
    }

}
