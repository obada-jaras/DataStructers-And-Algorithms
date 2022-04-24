package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SampleController {
	

    @FXML
    void playWithFriend(ActionEvent event) throws IOException {
    	showStage("PWF.fxml");
    }
    
    @FXML
    void playWithEasyComputer(ActionEvent event) throws IOException {
    	showStage("PWEC.fxml");
    }


    @FXML
    void playWithUnbeatableComputer(ActionEvent event) throws IOException {
    	showStage("PWHC.fxml");
    }
    
    private void showStage(String fileName) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Main.stage.setScene(scene);
        Main.stage.centerOnScreen();
        Main.stage.show();

    }
}
