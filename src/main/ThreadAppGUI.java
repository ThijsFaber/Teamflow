import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadAppGUI extends Application {

    private ThreadController controller = new ThreadController();

    @Override
    public void start(Stage stage) {
        // Invoervelden voor titel en hoofdvraag
        TextField titelField = new TextField();
        titelField.setPromptText("Titel van thread");

        TextField vraagField = new TextField();
        vraagField.setPromptText("Hoofdvraag");

        // Knop om thread aan te maken
        Button aanmaakKnop = new Button("Maak thread aan");

        // Label voor feedback
        Label feedbackLabel = new Label();

        // Wat gebeurt er als je op de knop klikt?
        aanmaakKnop.setOnAction(e -> {
            String titel = titelField.getText();
            String vraag = vraagField.getText();

            String resultaat = controller.startNieuweThread(titel, vraag, "user123");
            feedbackLabel.setText(resultaat);
        });

        // Layout opbouwen
        VBox layout = new VBox(10, titelField, vraagField, aanmaakKnop, feedbackLabel);
        layout.setStyle("-fx-padding: 20");

        Scene scene = new Scene(layout, 400, 200);
        stage.setTitle("Nieuwe Thread Aanmaken");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
