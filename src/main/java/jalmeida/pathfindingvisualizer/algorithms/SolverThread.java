package jalmeida.pathfindingvisualizer.algorithms;

import com.vaadin.flow.component.UI;

public class SolverThread extends Thread {

    Algorithm algorithm;
    UI ui;

    public SolverThread(Algorithm algorithm, UI ui) {
        this.algorithm = algorithm;
        this.ui = ui;
    }

    public void run() {
        algorithm.setUI(ui);
        algorithm.solve();
    }
}
