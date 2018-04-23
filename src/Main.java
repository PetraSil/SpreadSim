import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application{

    private Stage stage;
    private Simulation simulation;
    private BooleanProperty ready = new SimpleBooleanProperty(true);

    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        stage.setTitle("SpreadSim");
        BorderPane border = new BorderPane();
        Menu.createMenu();
        border.setLeft(Menu.getBox());

        Button next = new Button("Next");
        next.setOnAction(e ->{
            simulation.next();
            border.setCenter(simulation.getGrid());
        });
        next.setDisable(this.ready.getValue());

        Button prev = new Button("Prev");
        //prev.setOnAction(e -> simulation.prev());
        prev.setDisable(this.ready.getValue());

        Button newSimulation = new Button("New simulation");
        newSimulation.setOnAction(e -> {
            this.simulation = new Simulation((int)Menu.getTargetSlider().getValue(), (int)Menu.getInfectionChanceSlider().getValue());
            ready.set(false);
            border.setCenter(this.simulation.getGrid());
        });

        Button closeSimulation = new Button("Close simulation");
        closeSimulation.setDisable(this.ready.getValue());
        closeSimulation.setOnAction(e ->{
            ready.setValue(true);
        });

        ready.addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            next.setDisable(newValue);
            prev.setDisable(newValue);
            closeSimulation.setDisable(newValue);
        });

        Menu.getBox().getChildren().addAll(next,prev,newSimulation,closeSimulation);

        Scene scene = new Scene(border, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}