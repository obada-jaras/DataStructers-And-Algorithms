package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Project.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

public class PWFController implements Initializable{

	char player = 'x';
	
    @FXML
    private Button BTN1;

    @FXML
    private Button BTN2;

    @FXML
    private Button BTN3;

    @FXML
    private Button BTN4;
    
    @FXML
    private Button BTN5;

    @FXML
    private Button BTN6;

    @FXML
    private Button BTN7;

    @FXML
    private Button BTN8;

    @FXML
    private Button BTN9;

    @FXML
    private Label LBL_playerTurn;

    @FXML
    private AnchorPane pane;

    @FXML
    void BTN1(ActionEvent event) {
    	action(0, 0);
    }

    @FXML
    void BTN2(ActionEvent event) {
    	action(1, 0);
    }

    @FXML
    void BTN3(ActionEvent event) {
    	action(2, 0);
    }

    @FXML
    void BTN4(ActionEvent event) {
    	action(0, 1);
    }

    @FXML
    void BTN5(ActionEvent event) {
    	action(1, 1);
    }

    @FXML
    void BTN6(ActionEvent event) {
    	action(2, 1);
    }

    @FXML
    void BTN7(ActionEvent event) {
    	action(0, 2);
    }

    @FXML
    void BTN8(ActionEvent event) {
    	action(1, 2);
    }

    @FXML
    void BTN9(ActionEvent event) {
    	action(2, 2);
    }
    
    

    @FXML
    void back(ActionEvent event) throws IOException {
    	showStage("sample.fxml");
    }
    
    @FXML
    void playAgain(ActionEvent event) {
    	for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				Driver.board[i][j] = ' ';
    	
    	for (int i = 0; i < pane.getChildren().size(); i++) {
    		((Button)(pane.getChildren().get(i))).setText("");
    	}
    	
    	LBL_playerTurn.setText("Player1 Turn (x)");
    	pane.setDisable(false);
    }
    
   
    
    
    private void action(int x, int y) {
    	int flag = 0;
    	Button btn = selectButton(x, y);
    	if (Driver.board[y][x] == ' ') {
    		btn.setText(player + "");
    		char result = Driver.PWFMove(x, y, player);
    		
    		if (result != ' ') {
    			flag = 1;
    			Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("player " + player + " WON");
				alert.show();
				
				pane.setDisable(true);
    		}
    		
    		player = player == 'x' ? 'o' : 'x';
    		
    		String playerNumber = player == 'x' ? "Player1" : "Player2";
    		LBL_playerTurn.setText(playerNumber + " Turn" + " (" + player + ")");
    	}
    	
    	if (isBoardFull()) {
    		if (flag == 0) {
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Tie");
				alert.show();
				pane.setDisable(true);
			}
    	}
    }

    private Button selectButton(int x, int y) {
    	int[][] coo = {{0, 0}, {0, 1}, {0, 2}, 
    				   {1, 0}, {1, 1}, {1, 2},
    				   {2, 0}, {2, 1}, {2, 2}};
        Button[] btn = {BTN1, BTN2, BTN3, BTN4, BTN5, BTN6, BTN7, BTN8, BTN9};

    	
    	for (int i = 0; i < coo.length; i++) {
    		if (x == coo[i][1] && y == coo[i][0])
    			return btn[i];
    	}
    	
    	return null;
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				Driver.board[i][j] = ' ';
	}
	
	private boolean isBoardFull() {
		for (int i = 0; i < 3; i++) 
			for (int j = 0; j < 3; j++)
				if (Driver.board[i][j] == ' ')
					return false;
		
		
		return true;
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
