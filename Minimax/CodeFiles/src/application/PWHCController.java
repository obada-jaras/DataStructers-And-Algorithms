package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Project.ComputerTurn;
import Project.Move;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class PWHCController implements Initializable{
	public static char[][] board = new char[3][3];
	int i = 0;
	
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
    	i=0;
    	for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = ' ';
    	
    	for (int i = 0; i < pane.getChildren().size(); i++) {
    		((Button)(pane.getChildren().get(i))).setText("");
    	}
    	
    	pane.setDisable(false);
    }
    
    
    
    private void action(int x, int y) {
    	int flag = 0;
    	Button btn = selectButton(x, y);
    	if (board[y][x] == ' ') {
    		btn.setText("x");
    		board[y][x] = 'x';
    		i++;
    		
    		
    		char result = evaluation();
    		if (result != ' ') {
    			flag = 1;
    			Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("player " + result + " WON");
				alert.show();
				
				pane.setDisable(true);
				return;
    		}
    		
    		if (i>8) {
    			if (flag == 0) {
	    			Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Tie");
					alert.show();
					pane.setDisable(true);
					return;
    			}
    		}
    		
			Move move = ComputerTurn.findBestMove(board);
			board[move.row][move.col] = 'o';
			btn = selectButton(move.col, move.row);
			btn.setText("o");
			i++;
	    		
    		
    		result = evaluation();
    		if (result != ' ') {
    			flag = 1;
    			Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("player " + result + " WON");
				alert.show();
				
				pane.setDisable(true);
				return;
    		}
    	}
    }
    
    private static int[] mapping(int n) {
    	int x = n%3;
    	int y = n/3;
    	
    	int[] arr = {x, y};
    	return arr;
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
    
    
    private void showStage(String fileName) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(fileName));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.stage.setScene(scene);
		Main.stage.centerOnScreen();
		Main.stage.show();
	}
    
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = ' ';
	}
    
    
    private static char evaluation() {
		int[][] win = {
				{0, 1, 2},
				{3, 4, 5},
				{6, 7, 8},
				{0, 3, 6},
				{1, 4, 7},
				{2, 5, 8},
				{0, 4, 8},
				{2, 4, 6}
			};
		
		
		for (int i = 0; i < win.length; i++) {
			int[] n1 = mapping(win[i][0]);
			int[] n2 = mapping(win[i][1]);
			int[] n3 = mapping(win[i][2]);
			if (board[n1[0]][n1[1]] != ' ' && board[n1[0]][n1[1]] == board[n2[0]][n2[1]] && board[n2[0]][n2[1]] == board[n3[0]][n3[1]]) {
				return board[n1[0]][n1[1]];
			}
		}
		
		return ' ';
	}
}
