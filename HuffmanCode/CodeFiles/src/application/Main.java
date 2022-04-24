package application;
	
import java.io.File;

import Project.Compress;
import Project.Decompress;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	File file;
	
//	@Override
//	public void start(Stage primaryStage) {
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
				HBox hbox = new HBox(35);
					Button BTN_compress = new Button("Compress");
					Button BTN_decompress = new Button("Decompress");
					hbox.getChildren().addAll(BTN_compress, BTN_decompress);
					hbox.setAlignment(Pos.CENTER);
				
			
				root.setCenter(hbox);
			
				BTN_compress.setOnAction(e -> {
					compress(primaryStage);
				});
				
				
				BTN_decompress.setOnAction(e -> {
					decompress(primaryStage);
				});
				
			Scene scene = new Scene(root,300,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void compress(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
				VBox vbox = new VBox(30);
					Button BTN_chooseFile = new Button("Choose File");
					Label LBL_sizeBefore = new Label("File Size Before Compress: ");
					Label LBL_sizeAfter = new Label("File Size After Compress: ");
					Button BTN_saveFile = new Button("Save File");
					BTN_saveFile.setDisable(true);
					
					vbox.getChildren().addAll(BTN_chooseFile, LBL_sizeBefore, LBL_sizeAfter, BTN_saveFile);
					vbox.setAlignment(Pos.CENTER);
					
				root.setCenter(vbox);
				
				Button BTN_back = new Button("Back");
				root.setBottom(BTN_back);
					
				
				
				
				BTN_chooseFile.setOnAction(e -> {
					FileChooser fc = new FileChooser();
					fc.setTitle("Select File/s");

					file = fc.showOpenDialog(primaryStage);
					try {
						LBL_sizeBefore.setText("File Size Before Compress: " + file.length() + "Byte");
						LBL_sizeAfter.setText("File Size Before Compress: " + Compress.main(file, "") + "Byte");
						
						BTN_saveFile.setDisable(false);
						
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(e1.getMessage());
						alert.show();
					}
				});
				
				
				BTN_saveFile.setOnAction(e -> {
					DirectoryChooser dc = new DirectoryChooser();
					dc.setTitle("Select Folder");

					File folder = dc.showDialog(primaryStage);
					try {
						Compress.main(file, folder.getAbsolutePath());
						
						
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(e1.getMessage());
						alert.show();
					}
				});
				
				BTN_back.setOnAction(e -> {
					start(primaryStage);
				});
			Scene scene = new Scene(root,300,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void decompress(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
				VBox vbox = new VBox(30);
					Button BTN_chooseFile = new Button("Choose File");
					Label LBL_sizeBefore = new Label("File Size Before Decompress: ");
//					Label LBL_sizeAfter = new Label("File Size After Decompress: ");
					Button BTN_saveFile = new Button("Save File");
					BTN_saveFile.setDisable(true);
					
					vbox.getChildren().addAll(BTN_chooseFile, LBL_sizeBefore, /*LBL_sizeAfter,*/ BTN_saveFile);
					vbox.setAlignment(Pos.CENTER);
					
				root.setCenter(vbox);
					

				Button BTN_back = new Button("Back");
				root.setBottom(BTN_back);
					
				
				
				BTN_chooseFile.setOnAction(e -> {
					FileChooser fc = new FileChooser();
					fc.setTitle("Select File/s");

					file = fc.showOpenDialog(primaryStage);
					try {
						LBL_sizeBefore.setText("File Size Before Decompress: " + file.length() + "Byte");
//						LBL_sizeAfter.setText("File Size Before Decompress: " + Decompress.main(file, "") + "Byte");
						
						BTN_saveFile.setDisable(false);
						
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(e1.getMessage());
						alert.show();
					}
				});
				
				
				BTN_saveFile.setOnAction(e -> {
					DirectoryChooser dc = new DirectoryChooser();
					dc.setTitle("Select Folder");

					File folder = dc.showDialog(primaryStage);
					try {
						Decompress.main(file, folder.getAbsolutePath());
						
						
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(e1.getMessage());
						alert.show();
					}
				});
				
				BTN_back.setOnAction(e -> {
					start(primaryStage);
				});
			Scene scene = new Scene(root,300,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
