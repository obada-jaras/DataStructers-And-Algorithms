package application;
	
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Main extends Application {
	public static ArrayList<Integer> lcs = new ArrayList<Integer>();
	
//	@Override
	public void start(Stage primaryStage) {
	try {
			BorderPane root = new BorderPane();
				
				VBox vbox = new VBox(60);
				
					HBox hbox = new HBox();
						Label lbl = new Label("Select file to display the ordering of leds");
						
						hbox.getChildren().add(lbl);
						hbox.setAlignment(Pos.CENTER);
						hbox.setPadding(new Insets(0, 0, 0, 0));
			
					Button BTN_readFiles = new Button("Read File");
					BTN_readFiles.getStyleClass().add("enabled-button");
			
				vbox.getChildren().addAll(hbox, BTN_readFiles);
				vbox.setAlignment(Pos.CENTER);
				vbox.setPadding(new Insets(110, 0, 0, 0));
				
				root.setTop(vbox);
			
			
			BTN_readFiles.setOnAction(e -> {
				FileChooser fc = new FileChooser();
				fc.setTitle("Select File/s");
				FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
				fc.getExtensionFilters().addAll(txtFilter);
				
				File file = fc.showOpenDialog(primaryStage);
				try {
					secondPage(primaryStage, file);
					
					
					
					
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid File");
					alert.show();
				}
			});
				
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Leds");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("off.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	
	public void secondPage(Stage primaryStage, File file) throws Exception {
		int[] x = Project.Driver.getArray(file);
		int[] array = Project.Driver.getLCS(file);
		
		ScrollPane scroll = new ScrollPane();
			BorderPane root = new BorderPane();
			scroll.setContent(root);
			root.setPadding(new Insets(180));

				VBox leftVBox = new VBox(30);
				root.setLeft(leftVBox);
					for (int i = 0; i < array.length; i++) {
						if (array[x[i+1]-1] == 0) {
							HBox leftElementHBox = new HBox(10);
								ImageView imgV = new ImageView(new Image("off.png"));
								imgV.setFitWidth(81);
								imgV.setFitHeight(130);
								
								Label leftElementLabel = new Label(x[i+1] + "");
								leftElementLabel.setTranslateY(65);
								
								leftElementHBox.getChildren().addAll(imgV, leftElementLabel);
								
							leftVBox.getChildren().add(leftElementHBox);

						}
						
						else {
							HBox leftElementHBox = new HBox(10);
								ImageView imgV = new ImageView(new Image("on.png"));
								imgV.setFitWidth(81);
								imgV.setFitHeight(130);
								
								Label leftElementLabel = new Label(x[i+1] + "");
								leftElementLabel.setTranslateY(65);
								
								leftElementHBox.getChildren().addAll(imgV, leftElementLabel);
							
							leftVBox.getChildren().add(leftElementHBox);
						}
					}
				
				
				VBox rightVBox = new VBox(30);
				root.setRight(rightVBox);
					for (int i = 0; i < array.length; i++) {						
						HBox rightElementHBox = new HBox(10);
							Label leftElementLabel = new Label(i+1 + "");
							leftElementLabel.setTranslateY(65);
							
							ImageView imgV = new ImageView(new Image("power.png"));
							imgV.setFitWidth(118);
							imgV.setFitHeight(130);

							rightElementHBox.getChildren().addAll(leftElementLabel, imgV);
						
						rightVBox.getChildren().add(rightElementHBox);
					}

				
				
				Pane middlePane = new Pane();
				root.setCenter(middlePane);
				middlePane.setPrefWidth(950);
					for (int i = 0; i < array.length; i++) {
						if (array[x[i+1]-1] == 1) {
							int y1 = i*160 + 75;
							int y2 = (x[i+1]-1)*160 + 75;
							
							Line line = new Line(10, y1, 940, y2);
							line.setStroke(Color.BLACK);
							line.setStrokeWidth(5);
							middlePane.getChildren().add(line);
						
						}
						
					}				
				
				
		Scene scene = new Scene(scroll,Integer.MAX_VALUE,Double.MAX_VALUE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("Leds");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("on.png"));
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
