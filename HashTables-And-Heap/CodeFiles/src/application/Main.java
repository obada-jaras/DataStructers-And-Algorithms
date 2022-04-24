package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import Lists.LinkedList;
import Project.Driver;
import Project.FileYear;
import Project.Record;
import Project.YearFrequency;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	boolean FLAG_fileWasRead = false;
	LinkedList listOfFiles = new LinkedList();
	
	
	Button BTN_addName;
	Button BTN_deleteName;
	Button BTN_addFreqRecord;
	Button BTN_updateFreqRecord;
	Button BTN_searchForName;
	Button BTN_maxFrequency;
	Button BTN_maxFrequencyInYear;
	
	BorderPane startRoot;
	BorderPane chooseWindowRoot;
	
	
//	@Override
	public void start(Stage primaryStage) {
		try {
			startRoot = new BorderPane();
				VBox vbox = new VBox(30);
					Button BTN_readFiles = new Button("Read Files");
					BTN_readFiles.getStyleClass().add("enabled-button");
					
					Line line1 = new Line(0, 0, 400, 0);
					line1.getStyleClass().add("line");
					
					VBox vbox0 = new VBox(20);
					
						HBox hbox0_0 = new HBox(20);
							BTN_addName = new Button("Add New Name");
							BTN_addName.getStyleClass().add("disabled-button");
							
							BTN_deleteName = new Button("Delete Name");
							BTN_deleteName.getStyleClass().add("disabled-button");
						
							hbox0_0.getChildren().addAll(BTN_addName, BTN_deleteName);
							hbox0_0.setAlignment(Pos.CENTER);
						
							
						HBox hbox0_1 = new HBox(20);
							BTN_addFreqRecord = new Button("Add Frequency Record");
							BTN_addFreqRecord.getStyleClass().add("disabled-button");
							
							BTN_updateFreqRecord = new Button("Update Frequency Record");
							BTN_updateFreqRecord.getStyleClass().add("disabled-button");
						
							hbox0_1.getChildren().addAll(BTN_addFreqRecord, BTN_updateFreqRecord);
							hbox0_1.setAlignment(Pos.CENTER);

							
							
						HBox hbox0_2 = new HBox(20);
							BTN_maxFrequency = new Button("Max Frequency Name");
							BTN_maxFrequency.getStyleClass().add("disabled-button");
							
							BTN_maxFrequencyInYear = new Button("Max Frequency In Year");
							BTN_maxFrequencyInYear.getStyleClass().add("disabled-button");
						
							hbox0_2.getChildren().addAll(BTN_maxFrequency, BTN_maxFrequencyInYear);
							hbox0_2.setAlignment(Pos.CENTER);

							
						HBox hbox0_3 = new HBox();
							BTN_searchForName = new Button("Search For A Name");
							BTN_searchForName.getStyleClass().add("disabled-button");
						
							hbox0_3.getChildren().add(BTN_searchForName);
							hbox0_3.setAlignment(Pos.CENTER);

							
						vbox0.getChildren().addAll(hbox0_0, hbox0_1, hbox0_2, hbox0_3);
						vbox.setAlignment(Pos.CENTER);
					
					
					
					Line line2 = new Line(0, 0, 400, 0);
					line2.getStyleClass().add("line");

					HBox hbox = new HBox(20);
						Button BTN_exit = new Button("Exit");
						BTN_exit.getStyleClass().add("exit-button");
						
						hbox.getChildren().add(BTN_exit);				
						hbox.setAlignment(Pos.CENTER);
					
					vbox.getChildren().addAll(BTN_readFiles, line1, vbox0, line2, hbox);
					vbox.setAlignment(Pos.CENTER);
				
				startRoot.setCenter(vbox);
				
				
				
			
			BTN_readFiles.setOnAction(e -> {
				startRoot.setDisable(true);
				chooseFilesWindow(new Stage());
			});
				
			
			
			BTN_addName.setOnAction(e -> {
				if (FLAG_fileWasRead)
					add_deleteName(new Stage(), 'I');
			});
			
			BTN_deleteName.setOnAction(e -> {
				if (FLAG_fileWasRead)
					add_deleteName(new Stage(), 'D');
			});
			
				
			BTN_addFreqRecord.setOnAction(e -> {
				if (FLAG_fileWasRead)
					add_updateFreq(new Stage(), 'A');
			});
			
			BTN_updateFreqRecord.setOnAction(e -> {
				if (FLAG_fileWasRead)
					add_updateFreq(new Stage(), 'U');
			});
			
			BTN_searchForName.setOnAction(e -> {
				if (FLAG_fileWasRead)
					searchWindow(new Stage());
			});	
			
			
			BTN_maxFrequency.setOnAction(e -> {
				if (FLAG_fileWasRead)
					maxFreqWindow(new Stage());
			});
			
			BTN_maxFrequencyInYear.setOnAction(e -> {
				if (FLAG_fileWasRead)
					maxFreqInYear(new Stage());
			});
			
			
			
			BTN_exit.setOnAction(e -> {
				startRoot.setDisable(true);
				sureExitPage(new Stage());
			});
			
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				startRoot.setDisable(true);
				sureExitPage(new Stage());
			});
			
			
			Scene scene = new Scene(startRoot, 400, 420);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Babies Names");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	
	
	@SuppressWarnings("deprecation")
	public void chooseFilesWindow(Stage primaryStage) {
		try {
			chooseWindowRoot = new BorderPane();
				VBox vbox = new VBox(20);
					HBox hbox1 = new HBox(20);
						Button BTN_browseDir = new Button("Browse Directory");
						BTN_browseDir.getStyleClass().add("enabled-button");
						Button BTN_browseFiles = new Button("Browse File/s");
						BTN_browseFiles.getStyleClass().add("enabled-button");

						hbox1.getChildren().addAll(BTN_browseDir, BTN_browseFiles);
						hbox1.setAlignment(Pos.CENTER);
						
						
					ScrollPane scroll = new ScrollPane();
					scroll.setStyle("-fx-background-color:transparent;");

						HBox hh = new HBox();
					    hh.setAlignment(Pos.CENTER);
					    hh.setMinWidth(382);
						hh.setPadding(new Insets(0, 10, 10, 10));
						
							GridPane gp = new GridPane();
							gp.setHgap(10);
							gp.setVgap(10);
							hh.getChildren().add(gp);
							
					    scroll.setMinWidth(385);
						scroll.setContent(hh);
						
					
					HBox hbox2 = new HBox();	
						Button BTN_deleteAll = new Button("Delete All");
						BTN_deleteAll.getStyleClass().addAll("enabled-button", "deleteAll-button");
						BTN_deleteAll.setVisible(false);
						
						hbox2.getChildren().add(BTN_deleteAll);
						hbox2.setAlignment(Pos.CENTER_RIGHT);
						hbox2.setPadding(new Insets(0, 40, 0, 0));
						
					vbox.getChildren().addAll(hbox1, scroll, hbox2);
					vbox.setAlignment(Pos.CENTER);
					vbox.setPadding(new Insets(15, 0, 15, 0));
					
					
				chooseWindowRoot.setCenter(vbox);
				
				HBox hboxBottom = new HBox();
					Button BTN_done = new Button("Done");
					BTN_done.getStyleClass().add("disabled-button");
					hboxBottom.getChildren().add(BTN_done);
					hboxBottom.setAlignment(Pos.CENTER);
					hboxBottom.setPadding(new Insets(0, 0, 20, 0));
				chooseWindowRoot.setBottom(hboxBottom);
				
			
				
				
				
				for (int j = 0; j < listOfFiles.size(); j++) {
					HBox hboxTemp = new HBox();
						Label LBL = new Label(((FileYear)listOfFiles.getAt(j)).getFile().getName());
						LBL.getStyleClass().add("fileName-label");
						hboxTemp.getChildren().add(LBL);
						hboxTemp.setMinWidth(200);
					
					int year = -1;	
					String fileName = ((FileYear)listOfFiles.getAt(j)).getFile().getName();
					if (fileName.matches(".*\\D\\d\\d\\d\\d.txt$")){
						year = Integer.parseInt(fileName.substring(fileName.length()-8, fileName.length()-4));	
					}
					
					
					TextField tf = new TextField();
					tf.setPromptText("Year");
					tf.getStyleClass().add("files-textField");
					if (year != -1) {
						tf.setText(year + "");
						((FileYear)listOfFiles.getAt(j)).setYear(year);
					}
					
					
					Button BTN = new Button("Delete");
					BTN.getStyleClass().addAll("enabled-button", "deleteFile-button");
						
					
					gp.add(hboxTemp, 0, j);
					gp.add(tf, 1, j);
					gp.add(BTN, 2, j);
					
					
					if (!(listOfFiles.isEmpty()))
						BTN_done.getStyleClass().add("enabled-button");
					
					
					LBL.setOnMouseClicked(ee -> {
						int ii = GridPane.getRowIndex(hboxTemp);
						openFile(((FileYear)listOfFiles.getAt(ii)).getFile());
					});
					
					
					BTN.setOnAction(ee -> {
						int ii = GridPane.getRowIndex(hboxTemp);
						listOfFiles.deleteAt(ii);
						gp.getChildren().removeAll(hboxTemp, tf, BTN);
						rearrangeGridpane(gp, ii);
						
						if (listOfFiles.isEmpty()) {
							BTN_done.getStyleClass().clear();
							BTN_done.getStyleClass().addAll("button", "disabled-button");
						}
						
						if (listOfFiles.size() < 2) {
							BTN_deleteAll.setVisible(false);
						}
						
						primaryStage.setHeight(getStageHeight());
					});
					
					
					tf.setOnKeyTyped(ee -> {
						int ii = GridPane.getRowIndex(hboxTemp);

						if (tf.getText() == "") {
							((FileYear)listOfFiles.getAt(ii)).setYear(-1);
						}
						
						else {
							if (!isInteger(tf.getText())) {
								tf.setText(tf.getText().replaceAll("[^\\d]", ""));
							}
							
							if (tf.getText().length() > 4) {
								tf.setText(tf.getText().substring(0, 4));
							}
							
							tf.positionCaret(tf.getText().length());
							
							int newYear = -1;
							if (tf.getText() != "")
								newYear = Integer.parseInt(tf.getText());
							
							Date now = new Date();
							if (newYear <= (now.getYear()+1900) && newYear > 1500) {
								((FileYear)listOfFiles.getAt(ii)).setYear(newYear);
							}
							else {
								((FileYear)listOfFiles.getAt(ii)).setYear(-1);
							}
						}
					});
				}
				
				if (listOfFiles.size() > 1) {
					BTN_deleteAll.setVisible(true);
				}
				
				primaryStage.setHeight(getStageHeight());
				
				
				
				
				
				
				
			BTN_browseDir.setOnAction(e -> {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File selectedDirectory = directoryChooser.showDialog(primaryStage);
				if(selectedDirectory != null){
					FilenameFilter filter = new FilenameFilter() {
						@Override
						public boolean accept(File selectedDirectory, String name) {
							return name.matches(".*.txt$");
						}
					};
					File[] files = selectedDirectory.listFiles(filter);
					
					int i = listOfFiles.size();
					if (files != null ) {
						for (File f: files) {
							if (!(isFileExist(listOfFiles, f))) {
								FileYear fy = new FileYear(f, -1);
								listOfFiles.insertLast(fy);
							}
						}
					}
					
					
					for (; i < listOfFiles.size(); i++) {
						HBox hboxTemp = new HBox();
							Label LBL = new Label(((FileYear)listOfFiles.getAt(i)).getFile().getName());
							LBL.getStyleClass().add("fileName-label");
							hboxTemp.getChildren().add(LBL);
							hboxTemp.setMinWidth(200);
						
						int year = -1;	
						String fileName = ((FileYear)listOfFiles.getAt(i)).getFile().getName();
						if (fileName.matches(".*\\D\\d\\d\\d\\d.txt$")){
							year = Integer.parseInt(fileName.substring(fileName.length()-8, fileName.length()-4));	
						}
						
						
						TextField tf = new TextField();
						tf.setPromptText("Year");
						tf.getStyleClass().add("files-textField");
						if (year != -1) {
							tf.setText(year + "");
							((FileYear)listOfFiles.getAt(i)).setYear(year);
						}
						
						
						Button BTN = new Button("Delete");
						BTN.getStyleClass().addAll("enabled-button", "deleteFile-button");
							
						
						gp.add(hboxTemp, 0, i);
						gp.add(tf, 1, i);
						gp.add(BTN, 2, i);
						
						
						if (!(listOfFiles.isEmpty()))
							BTN_done.getStyleClass().add("enabled-button");
						
						
						LBL.setOnMouseClicked(ee -> {
							int ii = GridPane.getRowIndex(hboxTemp);
							openFile(((FileYear)listOfFiles.getAt(ii)).getFile());
						});
						
						
						BTN.setOnAction(ee -> {
							int ii = GridPane.getRowIndex(hboxTemp);
							listOfFiles.deleteAt(ii);
							gp.getChildren().removeAll(hboxTemp, tf, BTN);
							rearrangeGridpane(gp, ii);
							
							if (listOfFiles.isEmpty()) {
								BTN_done.getStyleClass().clear();
								BTN_done.getStyleClass().addAll("button", "disabled-button");
							}
							
							if (listOfFiles.size() < 2) {
								BTN_deleteAll.setVisible(false);
							}
							
							primaryStage.setHeight(getStageHeight());
						});
						
						
						tf.setOnKeyTyped(ee -> {
							int ii = GridPane.getRowIndex(hboxTemp);

							if (tf.getText() == "") {
								((FileYear)listOfFiles.getAt(ii)).setYear(-1);
							}
							
							else {
								if (!isInteger(tf.getText())) {
									tf.setText(tf.getText().replaceAll("[^\\d]", ""));
								}
								
								if (tf.getText().length() > 4) {
									tf.setText(tf.getText().substring(0, 4));
								}
								
								tf.positionCaret(tf.getText().length());
								
								int newYear = -1;
								if (tf.getText() != "")
									newYear = Integer.parseInt(tf.getText());
								
								Date now = new Date();
								if (newYear <= (now.getYear()+1900) && newYear > 1500) {
									((FileYear)listOfFiles.getAt(ii)).setYear(newYear);
								}
								else {
									((FileYear)listOfFiles.getAt(ii)).setYear(-1);
								}
							}
						});
					}
					
					if (listOfFiles.size() > 1) {
						BTN_deleteAll.setVisible(true);
					}
					
					primaryStage.setHeight(getStageHeight());
				}
			});
			
			
			
			BTN_browseFiles.setOnAction(e -> {
				FileChooser fc = new FileChooser();
				fc.setTitle("Select File/s");
				FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
				fc.getExtensionFilters().addAll(txtFilter);
				List<File> files = fc.showOpenMultipleDialog(primaryStage);
				
				int i = listOfFiles.size();
				if (files != null) {
					for (File f: files) {
						if (!(isFileExist(listOfFiles, f))) {
							FileYear fy = new FileYear(f, -1);
							listOfFiles.insertLast(fy);
						}
					}
				}
				
				
				
				for (; i < listOfFiles.size(); i++) {
					HBox hboxTemp = new HBox();
						Label LBL = new Label(((FileYear)listOfFiles.getAt(i)).getFile().getName());
						LBL.getStyleClass().add("fileName-label");
						hboxTemp.getChildren().add(LBL);
						hboxTemp.setMinWidth(200);
					
					int year = -1;	
					String fileName = ((FileYear)listOfFiles.getAt(i)).getFile().getName();
					if (fileName.matches(".*\\D\\d\\d\\d\\d.txt$")){
						year = Integer.parseInt(fileName.substring(fileName.length()-8, fileName.length()-4));	
					}
					
					
					TextField tf = new TextField();
					tf.setPromptText("Year");
					tf.getStyleClass().add("files-textField");
					if (year != -1) {
						tf.setText(year + "");
						((FileYear)listOfFiles.getAt(i)).setYear(year);
					}
					
					
					Button BTN = new Button("Delete");
					BTN.getStyleClass().addAll("enabled-button", "deleteFile-button");
						
					
					gp.add(hboxTemp, 0, i);
					gp.add(tf, 1, i);
					gp.add(BTN, 2, i);
					
					
					if (!(listOfFiles.isEmpty()))
						BTN_done.getStyleClass().add("enabled-button");
					
					
					LBL.setOnMouseClicked(ee -> {
						int ii = GridPane.getRowIndex(hboxTemp);
						openFile(((FileYear)listOfFiles.getAt(ii)).getFile());
					});
					
					
					BTN.setOnAction(ee -> {
						int ii = GridPane.getRowIndex(hboxTemp);
						listOfFiles.deleteAt(ii);
						gp.getChildren().removeAll(hboxTemp, tf, BTN);
						rearrangeGridpane(gp, ii);
						
						if (listOfFiles.isEmpty()) {
							BTN_done.getStyleClass().clear();
							BTN_done.getStyleClass().addAll("button", "disabled-button");
						}
						
						if (listOfFiles.size() < 2) {
							BTN_deleteAll.setVisible(false);
						}
						
						primaryStage.setHeight(getStageHeight());
					});
					
					
					tf.setOnKeyTyped(ee -> {
						int ii = GridPane.getRowIndex(hboxTemp);

						if (tf.getText() == "") {
							((FileYear)listOfFiles.getAt(ii)).setYear(-1);
						}
						
						else {
							if (!isInteger(tf.getText())) {
								tf.setText(tf.getText().replaceAll("[^\\d]", ""));
							}
							
							if (tf.getText().length() > 4) {
								tf.setText(tf.getText().substring(0, 4));
							}
							
							tf.positionCaret(tf.getText().length());
							
							int newYear = -1;
							if (tf.getText() != "")
								newYear = Integer.parseInt(tf.getText());
							
							Date now = new Date();
							if (newYear <= (now.getYear()+1900) && newYear > 1500) {
								((FileYear)listOfFiles.getAt(ii)).setYear(newYear);
							}
							else {
								((FileYear)listOfFiles.getAt(ii)).setYear(-1);
							}
						}
					});
				}
				
				if (listOfFiles.size() > 1) {
					BTN_deleteAll.setVisible(true);
				}
				
				primaryStage.setHeight(getStageHeight());
			});
			
			
			
			BTN_deleteAll.setOnAction(e -> {
				listOfFiles.clear();
				gp.getChildren().clear();
				BTN_deleteAll.setVisible(false);
				primaryStage.setHeight(getStageHeight());
			});
			
			
			
			
			
			BTN_done.setOnAction(e -> {
				if (!(listOfFiles.isEmpty())) {
					String ability = filesChosen(listOfFiles);
					
					if (ability == "done") {
						String message = Driver.readFiles(listOfFiles);
						if (message.equals("")) {
							Driver.getHash().clear();
							Driver.readFiles(listOfFiles);
							FLAG_fileWasRead = true;
							

							BTN_addName.getStyleClass().add("enabled-button");
							BTN_deleteName.getStyleClass().add("enabled-button");
							BTN_addFreqRecord.getStyleClass().add("enabled-button");
							BTN_updateFreqRecord.getStyleClass().add("enabled-button");
							BTN_searchForName.getStyleClass().add("enabled-button");
							BTN_maxFrequency.getStyleClass().add("enabled-button");
							BTN_maxFrequencyInYear.getStyleClass().add("enabled-button");

							startRoot.setDisable(false);
							primaryStage.close();
						}
						
						else {
							chooseWindowRoot.setDisable(true);
							fileErrorScreen(message);
						}
					}
					
					else {
						chooseWindowRoot.setDisable(true);
						fileErrorScreen(ability);						
					}
				}
			});
			
			
						
			
			Scene scene = new Scene(chooseWindowRoot, 400, 85);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Choose Files");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
			
			
			primaryStage.setOnCloseRequest(e -> {
				startRoot.setDisable(false);
			});
		
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	
	public void add_deleteName(Stage primaryStage, char selection) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 155);
				VBox vbox = new VBox(30);
					HBox hbox1_1 = new HBox(15);
						TextField tf = new TextField();
						tf.setPromptText("Name");

						
						HBox hbox1_1_1 = new HBox(5);
							int circleDiameter = 12;
							Image img = new Image("images/emptyCircle.png");
							ImageView imgViewEmpty1 = new ImageView(img);
							imgViewEmpty1.setFitHeight(circleDiameter);
							imgViewEmpty1.setFitWidth(circleDiameter);
							
							ImageView imgViewEmpty2 = new ImageView(img);
							imgViewEmpty2.setFitHeight(circleDiameter);
							imgViewEmpty2.setFitWidth(circleDiameter);
							
							ImageView imgViewFilled = new ImageView(new Image("images/filledCircle.png"));
							imgViewFilled.setFitHeight(circleDiameter);
							imgViewFilled.setFitWidth(circleDiameter);
							
							ToggleGroup tg = new ToggleGroup();
		
							ToggleButton tBTN_male = new ToggleButton("  MALE");
							tBTN_male.setAlignment(Pos.CENTER_LEFT);
							tBTN_male.setGraphic(imgViewEmpty1);
							tBTN_male.setToggleGroup(tg);
							tBTN_male.getStyleClass().add("toggleButton");
		
							
							ToggleButton tBTN_female = new ToggleButton(" FEMALE");
							tBTN_female.setAlignment(Pos.CENTER_LEFT);
							tBTN_female.setGraphic(imgViewEmpty2);
							tBTN_female.setToggleGroup(tg);
							tBTN_female.getStyleClass().add("toggleButton");
							
							hbox1_1_1.getChildren().addAll(tBTN_male, tBTN_female);
							
						hbox1_1.getChildren().addAll(tf, hbox1_1_1);
						hbox1_1.setAlignment(Pos.CENTER);
						
	
					VBox vboxTemp = new VBox(10);
						Button BTN_process;
						if (selection == 'I') 
							BTN_process = new Button("Insert Name");
						else 
							BTN_process = new Button("Delete Name");

						BTN_process.getStyleClass().add("disabled-button");
						
						Label LBL_msg = new Label("");
						LBL_msg.getStyleClass().add("note-label");
						
						vboxTemp.getChildren().addAll(BTN_process, LBL_msg);
						vboxTemp.setAlignment(Pos.CENTER);
	
				
				
						
					vbox.getChildren().addAll(hbox1_1, vboxTemp);
					vbox.setAlignment(Pos.TOP_CENTER);
					vbox.setPadding(new Insets(30, 0, 10, 0));
	
					
					
				root.setCenter(vbox);
						
			
				
				
			
				
			tBTN_male.setOnAction(e -> {
				if (tBTN_male.isSelected()) {
					tBTN_male.setGraphic(imgViewFilled);
					tBTN_female.setGraphic(imgViewEmpty2);
				} 
				
				else {
					tBTN_male.setGraphic(imgViewEmpty1);
				}
				
				
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});
			
			
			
			
			tBTN_female.setOnAction(e -> {
				if (tBTN_female.isSelected()) {
					tBTN_female.setGraphic(imgViewFilled);
					tBTN_male.setGraphic(imgViewEmpty1);
				} 
				
				else {
					tBTN_female.setGraphic(imgViewEmpty2);
				}	
				
				
				
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});
			
			
			
			
			
			
			tf.setOnKeyTyped(e -> {
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});
				
				
					
			
			
			BTN_process.setOnAction(e -> {
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected())) {	
					
					String name = tf.getText().trim();
					char gender = (tBTN_male.isSelected()) ? 'M' : 'F';
					
					if (selection == 'I') {
						if (Driver.insertName(name, gender)) 
							LBL_msg.setText(name + ", " + gender + " Inserted");
						
						else 
							LBL_msg.setText(name + ", " + gender + " Already Exists");
						
						
						
					}
					
					else {
						if (Driver.deleteName(name, gender))
							LBL_msg.setText(name + ", " + gender + " Deleted");
						else 
							LBL_msg.setText(name + ", " + gender + " Does Not Exsit");
					}
				}			
			});
			
				
			
			setGlobalEventHandler(root, BTN_process);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			if (selection == 'I')
				primaryStage.setTitle("Add New Name");
			else 
				primaryStage.setTitle("Delete Name");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void add_updateFreq(Stage primaryStage, char selection) {
		try {
			BorderPane root = new BorderPane();
	
			VBox vbox = new VBox(30);
				HBox hbox1_1 = new HBox(15);
					TextField tf = new TextField();
					tf.setPromptText("Name");
	
					
					HBox hbox1_1_1 = new HBox(5);
						int circleDiameter = 12;
						Image img = new Image("images/emptyCircle.png");
						ImageView imgViewEmpty1 = new ImageView(img);
						imgViewEmpty1.setFitHeight(circleDiameter);
						imgViewEmpty1.setFitWidth(circleDiameter);
						
						ImageView imgViewEmpty2 = new ImageView(img);
						imgViewEmpty2.setFitHeight(circleDiameter);
						imgViewEmpty2.setFitWidth(circleDiameter);
						
						ImageView imgViewFilled = new ImageView(new Image("images/filledCircle.png"));
						imgViewFilled.setFitHeight(circleDiameter);
						imgViewFilled.setFitWidth(circleDiameter);
						
						ToggleGroup tg = new ToggleGroup();
	
						ToggleButton tBTN_male = new ToggleButton("  MALE");
						tBTN_male.setAlignment(Pos.CENTER_LEFT);
						tBTN_male.setGraphic(imgViewEmpty1);
						tBTN_male.setToggleGroup(tg);
						tBTN_male.getStyleClass().add("toggleButton");
	
						
						ToggleButton tBTN_female = new ToggleButton(" FEMALE");
						tBTN_female.setAlignment(Pos.CENTER_LEFT);
						tBTN_female.setGraphic(imgViewEmpty2);
						tBTN_female.setToggleGroup(tg);
						tBTN_female.getStyleClass().add("toggleButton");
						
						hbox1_1_1.getChildren().addAll(tBTN_male, tBTN_female);
						
					hbox1_1.getChildren().addAll(tf, hbox1_1_1);
					hbox1_1.setAlignment(Pos.CENTER);
					
				HBox hbox1_2 = new HBox(10);
					TextField TF_year = new TextField();
					TF_year.setPromptText("Year");
				
					TextField TF_freq = new TextField();
					TF_freq.setPromptText("Frequency");
					
					hbox1_2.getChildren().addAll(TF_year, TF_freq);
					hbox1_2.setAlignment(Pos.CENTER);

					
				VBox vboxTemp = new VBox(10);
					Button BTN_process;
					if (selection == 'A') 
						BTN_process = new Button("Add Record");
					else 
						BTN_process = new Button("Update Record");
	
					BTN_process.getStyleClass().add("disabled-button");
					
					Label LBL_msg = new Label("");
					LBL_msg.getStyleClass().add("note-label");
					
					vboxTemp.getChildren().addAll(BTN_process, LBL_msg);
					vboxTemp.setAlignment(Pos.CENTER);
	
			
		
				
				vbox.getChildren().addAll(hbox1_1, hbox1_2, vboxTemp);
				vbox.setAlignment(Pos.TOP_CENTER);
				vbox.setPadding(new Insets(30, 0, 10, 0));

			
			
			root.setCenter(vbox);
			
			
			
			
			tBTN_male.setOnAction(e -> {
				if (tBTN_male.isSelected()) {
					tBTN_male.setGraphic(imgViewFilled);
					tBTN_female.setGraphic(imgViewEmpty2);
				} 
				
				else {
					tBTN_male.setGraphic(imgViewEmpty1);
				}
				
				
				if (tf.getText() != "" && TF_year.getText() != "" && TF_freq.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});
			
			
			
			
			tBTN_female.setOnAction(e -> {
				if (tBTN_female.isSelected()) {
					tBTN_female.setGraphic(imgViewFilled);
					tBTN_male.setGraphic(imgViewEmpty1);
				} 
				
				else {
					tBTN_female.setGraphic(imgViewEmpty2);
				}	
				
				
				
				if (tf.getText() != "" && TF_year.getText() != "" && TF_freq.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});
			
			
			
			
			
			
			tf.setOnKeyTyped(e -> {
				if (tf.getText() != "" && TF_year.getText() != "" && TF_freq.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});
				
			
			
			TF_year.setOnKeyTyped(e -> {
				if (!isInteger(TF_year.getText())) {
					TF_year.setText(TF_year.getText().replaceAll("[^\\d]", ""));
				}
				TF_year.positionCaret(TF_year.getText().length());
				
				if (tf.getText() != "" && TF_year.getText() != "" && TF_freq.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});	
			
			
			TF_freq.setOnKeyTyped(e -> {
				if (!isInteger(TF_freq.getText())) {
					TF_freq.setText(TF_freq.getText().replaceAll("[^\\d]", ""));
				}
				TF_freq.positionCaret(TF_freq.getText().length());
				
				if (tf.getText() != "" && TF_year.getText() != "" && TF_freq.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_process.getStyleClass().add("enabled-button");
				
				else { 
					BTN_process.getStyleClass().clear();
					BTN_process.getStyleClass().addAll("button", "disabled-button");
				}
			});	
			
			
			
			BTN_process.setOnAction(e -> {
				if (tf.getText() != "" && TF_year.getText() != "" && TF_freq.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected())) {	
					
					String name = tf.getText().trim();
					char gender = (tBTN_male.isSelected()) ? 'M' : 'F';
					int year = Integer.parseInt(TF_year.getText().trim());
					int freq = Integer.parseInt(TF_freq.getText().trim());
					
					
					LBL_msg.setText(insertAndUpdateReocrdInDriver(selection, name, gender, year, freq));
							
				}			
			});
			
			
			
			setGlobalEventHandler(root, BTN_process);
			
			Scene scene = new Scene(root, 400, 220);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			if (selection == 'A')
				primaryStage.setTitle("Add New Frequency Record");
			else 
				primaryStage.setTitle("Update Frequency Record");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private String insertAndUpdateReocrdInDriver(char selection, String name, char gender, int year, int freq) {
		if (Driver.findName(name, gender) == null) {

			Alert ALRT_addName = new Alert(AlertType.CONFIRMATION);
			ALRT_addName.setTitle("Insert New Name?");
			ALRT_addName.setHeaderText("Cannot find Name: " + name + "," + gender + "!!");
			ALRT_addName.setContentText("Insert New Name " + name + "," + gender + " with frequency " + freq + " for the year " + year + "?");
			Optional<ButtonType> result = ALRT_addName.showAndWait();

			
			if (result.get() == ButtonType.OK) { 
				Driver.insertFreqRecord(name, gender, year, freq);
				return "Frequency " + freq + " added to " + name + " for year " + year;	
			}
				
			else {
				return "No Records Added";
			}
		}
		
		
		else {
			if (selection == 'A') {
				Driver.insertFreqRecord(name, gender, year, freq);
				return "Frequency " + freq + " added to " + name + " for year " + year;	
			}
			
			else {
				if (Driver.haveRecordForYear(name, gender, year)) {
					Driver.updateFreqRecord(name, gender, year, freq);
					return year + " frequency updated to " + freq;	
				}
				
				else {
					Alert ALRT_addYear = new Alert(AlertType.CONFIRMATION);					
					ALRT_addYear.setTitle("Insert New Year Record?");
					ALRT_addYear.setHeaderText("Cannot find record for year " + year + " to update!!");
					ALRT_addYear.setContentText("Insert new record for year " + year + " and frequency " + freq + "?");
					Optional<ButtonType> resultYear = ALRT_addYear.showAndWait();

					if (resultYear.get() == ButtonType.OK) {
						Driver.updateFreqRecord(name, gender, year, freq);
						return "Frequency " + freq + " added to " + name + " for year " + year;	
					}
					
					else {
						return "No Records Updated";
					}
				}
			}
		}
		
	}
	
	
	public void searchWindow(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 145);
				VBox vbox = new VBox(30);
					HBox hbox1_1 = new HBox(15);
						TextField tf = new TextField();
						tf.setPromptText("Name");

						
						HBox hbox1_1_1 = new HBox(5);
							int circleDiameter = 12;
							Image img = new Image("images/emptyCircle.png");
							ImageView imgViewEmpty1 = new ImageView(img);
							imgViewEmpty1.setFitHeight(circleDiameter);
							imgViewEmpty1.setFitWidth(circleDiameter);
							
							ImageView imgViewEmpty2 = new ImageView(img);
							imgViewEmpty2.setFitHeight(circleDiameter);
							imgViewEmpty2.setFitWidth(circleDiameter);
							
							ImageView imgViewFilled = new ImageView(new Image("images/filledCircle.png"));
							imgViewFilled.setFitHeight(circleDiameter);
							imgViewFilled.setFitWidth(circleDiameter);
							
							ToggleGroup tg = new ToggleGroup();
		
							ToggleButton tBTN_male = new ToggleButton("  MALE");
							tBTN_male.setAlignment(Pos.CENTER_LEFT);
							tBTN_male.setGraphic(imgViewEmpty1);
							tBTN_male.setToggleGroup(tg);
							tBTN_male.getStyleClass().add("toggleButton");
		
							
							ToggleButton tBTN_female = new ToggleButton(" FEMALE");
							tBTN_female.setAlignment(Pos.CENTER_LEFT);
							tBTN_female.setGraphic(imgViewEmpty2);
							tBTN_female.setToggleGroup(tg);
							tBTN_female.getStyleClass().add("toggleButton");
							
							hbox1_1_1.getChildren().addAll(tBTN_male, tBTN_female);
							
						hbox1_1.getChildren().addAll(tf, hbox1_1_1);
						hbox1_1.setAlignment(Pos.CENTER);
						
	
					VBox vboxTemp = new VBox(30);	
						Button BTN_search = new Button("Search For Name");
						BTN_search.getStyleClass().add("disabled-button");

						
						vboxTemp.getChildren().add(BTN_search);
						vboxTemp.setAlignment(Pos.CENTER);
	
				
					Line line = new Line(0, 0, 400, 0);
					line.getStyleClass().add("line");
					
					VBox vbox1_1 = new VBox(20);
						VBox vbox1_1_1 = new VBox(2);
							Label LBL_name = new Label();
							LBL_name.setTextAlignment(TextAlignment.CENTER);
							LBL_name.setStyle("-fx-text-fill: -style1-secondaryColor; -fx-font-weight: bold");
	
							Label LBL_avgFreq = new Label();
							LBL_avgFreq.setTextAlignment(TextAlignment.CENTER);
							LBL_avgFreq.setStyle("-fx-text-fill: -style1-secondaryColor; -fx-font-weight: bold");
							
							vbox1_1_1.getChildren().addAll(LBL_name, LBL_avgFreq);
							vbox1_1_1.setAlignment(Pos.CENTER);
						
						VBox vbox1_1_2 = new VBox();
							GridPane GP_titles = new GridPane();
							GP_titles.setAlignment(Pos.CENTER);
							GP_titles.setHgap(67);
							
								Label LBL_YEAR = new Label("YEAR");
								LBL_YEAR.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
		
								Label LBL_FREQUENCY = new Label("FREQUENCY");
								LBL_FREQUENCY.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
								
								GP_titles.add(LBL_YEAR, 0, 0);
								GP_titles.add(LBL_FREQUENCY, 1, 0);
								
								GridPane.setHalignment(LBL_YEAR, HPos.CENTER);
								GridPane.setHalignment(LBL_FREQUENCY, HPos.CENTER);
							
								
							ScrollPane scroll = new ScrollPane();
							scroll.setStyle("-fx-background-color:transparent;");
	
								HBox hh = new HBox();
							    hh.setAlignment(Pos.CENTER);
							    hh.setMinWidth(385);
								hh.setPadding(new Insets(-15, 0, 0, 12));
								
									GridPane gp = new GridPane();
									gp.setAlignment(Pos.CENTER);
									gp.setHgap(67);
									hh.getChildren().add(gp);
									
							    scroll.setMinWidth(385);
								scroll.setContent(hh);
								
							vbox1_1_2.getChildren().addAll(GP_titles, scroll);
							
						vbox1_1.getChildren().addAll(vbox1_1_1, vbox1_1_2);
						vbox1_1.setAlignment(Pos.CENTER);
						
					vbox.getChildren().addAll(hbox1_1, vboxTemp, line, vbox1_1);
					vbox.setAlignment(Pos.TOP_CENTER);
					vbox.setPadding(new Insets(30, 0, 10, 0));
	
					
					
				root.setCenter(vbox);
						
			
				
				
			
				
			tBTN_male.setOnAction(e -> {
				if (tBTN_male.isSelected()) {
					tBTN_male.setGraphic(imgViewFilled);
					tBTN_female.setGraphic(imgViewEmpty2);
				} 
				
				else {
					tBTN_male.setGraphic(imgViewEmpty1);
				}
				
				
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_search.getStyleClass().add("enabled-button");
				
				else { 
					BTN_search.getStyleClass().clear();
					BTN_search.getStyleClass().addAll("button", "disabled-button");
				}
			});
			
			
			
			
			tBTN_female.setOnAction(e -> {
				if (tBTN_female.isSelected()) {
					tBTN_female.setGraphic(imgViewFilled);
					tBTN_male.setGraphic(imgViewEmpty1);
				} 
				
				else {
					tBTN_female.setGraphic(imgViewEmpty2);
				}	
				
				
				
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_search.getStyleClass().add("enabled-button");
				
				else { 
					BTN_search.getStyleClass().clear();
					BTN_search.getStyleClass().addAll("button", "disabled-button");
				}
			});
			
			
			
			
			
			
			tf.setOnKeyTyped(e -> {
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected()))
					BTN_search.getStyleClass().add("enabled-button");
				
				else { 
					BTN_search.getStyleClass().clear();
					BTN_search.getStyleClass().addAll("button", "disabled-button");
				}
			});
				
				
					
			
			
			BTN_search.setOnAction(e -> {
				if (tf.getText() != "" && (tBTN_male.isSelected() || tBTN_female.isSelected())) {	
					
					String name = tf.getText().trim();
					char gender = (tBTN_male.isSelected()) ? 'M' : 'F';
					Record record = Driver.findName(name, gender);
					
					
					if (record != null) {
						vboxTemp.getChildren().clear();
						vboxTemp.getChildren().add(BTN_search);
						
						String genderr = (record.getName().getGender() == 'M' || record.getName().getGender() == 'm') ? "Male" : "Female";
						
						LBL_name.setText(record.getName().getName() + " (" + genderr + ")\n");
						

						
						Object[] objectList = record.getFrequency().sortedArrayOfHeap();
						LinkedList list = new LinkedList();
						for (Object obj: objectList)
							list.insertLast(obj);

						
						
						gp.getChildren().clear();
						Label LBL_tempYEAR = new Label("YEAR");
						LBL_tempYEAR.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: transparent;");

						Label LBL_tempFREQUENCY = new Label("FREQUENCY");
						LBL_tempFREQUENCY.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: transparent;");
						
						gp.add(LBL_tempYEAR, 0, 0);
						gp.add(LBL_tempFREQUENCY, 1, 0);
						
						GridPane.setHalignment(LBL_tempYEAR, HPos.CENTER);
						GridPane.setHalignment(LBL_tempFREQUENCY, HPos.CENTER);
					
					
						int j = 1;
						for (int i = 0; i < list.size(); i++) {
							String year = ((YearFrequency)list.getAt(i)).getYear() + "";
							String freq = ((YearFrequency)list.getAt(i)).getFrequency() + "";
							
							Label LBL_yy = new Label(year);
							Label LBL_ff = new Label(freq);
						
							gp.add(LBL_yy, 0, j);
							gp.add(LBL_ff, 1, j);
							GridPane.setHalignment(LBL_yy, HPos.CENTER);
							GridPane.setHalignment(LBL_ff, HPos.CENTER);
							j++;
						}

						primaryStage.setHeight(457.3);
					}
					
					else {
						primaryStage.setHeight(220);
						Label LBL_note = new Label("Name Not Found!");
						LBL_note.getStyleClass().add("note-label");	
						vboxTemp.getChildren().add(LBL_note);
					}
				}				
			});
			
				
			
			setGlobalEventHandler(root, BTN_search);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Search For a Name");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	public void maxFreqWindow(Stage primaryStage) {
		try {			
			BorderPane root = new BorderPane();
				VBox vbox = new VBox(20);
					VBox vbox1_1_1 = new VBox(2);
						Label LBL_name = new Label();
						LBL_name.setTextAlignment(TextAlignment.CENTER);
						LBL_name.setStyle("-fx-text-fill: -style1-secondaryColor; -fx-font-weight: bold");

						Label LBL_avgFreq = new Label();
						LBL_avgFreq.setTextAlignment(TextAlignment.CENTER);
						LBL_avgFreq.setStyle("-fx-text-fill: -style1-secondaryColor; -fx-font-weight: bold");
						
						vbox1_1_1.getChildren().addAll(LBL_name, LBL_avgFreq);
						vbox1_1_1.setAlignment(Pos.CENTER);
					
					VBox vbox1_1_2 = new VBox();
						GridPane GP_titles = new GridPane();
						GP_titles.setAlignment(Pos.CENTER);
						GP_titles.setHgap(67);
						
							Label LBL_YEAR = new Label("YEAR");
							LBL_YEAR.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
	
							Label LBL_FREQUENCY = new Label("FREQUENCY");
							LBL_FREQUENCY.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
							
							GP_titles.add(LBL_YEAR, 0, 0);
							GP_titles.add(LBL_FREQUENCY, 1, 0);
							
							GridPane.setHalignment(LBL_YEAR, HPos.CENTER);
							GridPane.setHalignment(LBL_FREQUENCY, HPos.CENTER);
						
							
						ScrollPane scroll = new ScrollPane();
						scroll.setStyle("-fx-background-color:transparent;");

							HBox hh = new HBox();
						    hh.setAlignment(Pos.CENTER);
						    hh.setMinWidth(380);
							hh.setPadding(new Insets(-15, 0, 0, 12));
							
								GridPane gp = new GridPane();
								gp.setAlignment(Pos.CENTER);
								gp.setHgap(67);
								hh.getChildren().add(gp);
								
						    scroll.setMinWidth(385);
							scroll.setContent(hh);
							
						vbox1_1_2.getChildren().addAll(GP_titles, scroll);
						
					vbox.getChildren().addAll(vbox1_1_1, vbox1_1_2);
					vbox.setAlignment(Pos.CENTER);
					vbox.setPadding(new Insets(15, 0, 15, 0));
					
				root.setCenter(vbox);
						
			
				

				
				Record record = Driver.nameWithMaxFrequency();
				
				
				if (record != null) {
					
					String genderr = (record.getName().getGender() == 'M' || record.getName().getGender() == 'm') ? "Male" : "Female";
					
					LBL_name.setText(record.getName().getName() + " (" + genderr + ")\n");
					

					
					Object[] objectList = record.getFrequency().sortedArrayOfHeap();
					LinkedList list = new LinkedList();
					for (Object obj: objectList)
						list.insertLast(obj);
					
					
					gp.getChildren().clear();
					Label LBL_tempYEAR = new Label("YEAR");
					LBL_tempYEAR.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: transparent;");

					Label LBL_tempFREQUENCY = new Label("FREQUENCY");
					LBL_tempFREQUENCY.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: transparent;");
					
					gp.add(LBL_tempYEAR, 0, 0);
					gp.add(LBL_tempFREQUENCY, 1, 0);
					
					GridPane.setHalignment(LBL_tempYEAR, HPos.CENTER);
					GridPane.setHalignment(LBL_tempFREQUENCY, HPos.CENTER);
				
				
					int j = 1;
					for (int i = 0; i < list.size(); i++) {
						String year = ((YearFrequency)list.getAt(i)).getYear() + "";
						String freq = ((YearFrequency)list.getAt(i)).getFrequency() + "";
						
						Label LBL_yy = new Label(year);
						Label LBL_ff = new Label(freq);
					
						gp.add(LBL_yy, 0, j);
						gp.add(LBL_ff, 1, j);
						GridPane.setHalignment(LBL_yy, HPos.CENTER);
						GridPane.setHalignment(LBL_ff, HPos.CENTER);
						j++;
					}
	
				}
				
				else {
					primaryStage.setHeight(100);
					LBL_name.setText("No Names Found!");
				}
				
				
				
			
			Scene scene = new Scene(root, 400, 420);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Name With Max Frequency");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public void maxFreqInYear(Stage primaryStage) {
		try {			
			BorderPane root = new BorderPane();
				VBox vbox = new VBox(40);
					VBox vboxTemp = new VBox(20);
						HBox hbox = new HBox(20);
							TextField tf = new TextField();
							tf.setPromptText("Year");
							Button BTN = new Button("Get Max");
							BTN.getStyleClass().add("disabled-button");
							
							hbox.getChildren().addAll(tf, BTN);
							hbox.setAlignment(Pos.CENTER);
					
							Label LBL = new Label();
							LBL.getStyleClass().add("note-label");
							
							
						vboxTemp.getChildren().add(hbox);
						vboxTemp.setAlignment(null);
					
					Line line = new Line(0, 0, 400, 0);
					line.getStyleClass().add("line");
					
					VBox vbox1_1 = new VBox(20);
						VBox vbox1_1_1 = new VBox(2);
							Label LBL_name = new Label();
							LBL_name.setTextAlignment(TextAlignment.CENTER);
							LBL_name.setStyle("-fx-text-fill: -style1-secondaryColor; -fx-font-weight: bold");
	
							Label LBL_avgFreq = new Label();
							LBL_avgFreq.setTextAlignment(TextAlignment.CENTER);
							LBL_avgFreq.setStyle("-fx-text-fill: -style1-secondaryColor; -fx-font-weight: bold");
							
							vbox1_1_1.getChildren().addAll(LBL_name, LBL_avgFreq);
							vbox1_1_1.setAlignment(Pos.CENTER);
						
						VBox vbox1_1_2 = new VBox();
							GridPane GP_titles = new GridPane();
							GP_titles.setAlignment(Pos.CENTER);
							GP_titles.setHgap(67);
							
								Label LBL_YEAR = new Label("YEAR");
								LBL_YEAR.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
		
								Label LBL_FREQUENCY = new Label("FREQUENCY");
								LBL_FREQUENCY.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
								
								GP_titles.add(LBL_YEAR, 0, 0);
								GP_titles.add(LBL_FREQUENCY, 1, 0);
								
								GridPane.setHalignment(LBL_YEAR, HPos.CENTER);
								GridPane.setHalignment(LBL_FREQUENCY, HPos.CENTER);
							
								
							ScrollPane scroll = new ScrollPane();
							scroll.setStyle("-fx-background-color:transparent;");
	
								HBox hh = new HBox();
							    hh.setAlignment(Pos.CENTER);
							    hh.setMinWidth(385);
								hh.setPadding(new Insets(-15, 0, 0, 12));
								
									GridPane gp = new GridPane();
									gp.setAlignment(Pos.CENTER);
									gp.setHgap(67);
									hh.getChildren().add(gp);
									
							    scroll.setMinWidth(385);
								scroll.setContent(hh);
								
							vbox1_1_2.getChildren().addAll(GP_titles, scroll);
							
						vbox1_1.getChildren().addAll(vbox1_1_1, vbox1_1_2);
						vbox1_1.setAlignment(Pos.CENTER);
						
					vbox.getChildren().addAll(vboxTemp, LBL, line, vbox1_1);
					vbox.setAlignment(Pos.TOP_CENTER);
					vbox.setPadding(new Insets(20, 0, 0, 0));
				
				root.setCenter(vbox);
				
				
				
				
				
			tf.setOnKeyTyped(e -> {
				if (!isInteger(tf.getText())) {
					tf.setText(tf.getText().replaceAll("[^\\d]", ""));
				}
				tf.positionCaret(tf.getText().length());
				
				
				
				if (tf.getText() != "") {
					BTN.getStyleClass().add("enabled-button");
				
				} else {
					BTN.getStyleClass().clear();
					BTN.getStyleClass().addAll("button", "disabled-button");
				}
				
			});
			
			
			
			
			BTN.setOnAction(e -> {
				if (tf.getText() != "") {
					int yearTF = Integer.parseInt(tf.getText());
					
					Record record = Driver.nameWithMaxFrequencyInYear(yearTF);
					
					if (record != null) {
						LBL.setText("");
						
						String genderr = (record.getName().getGender() == 'M' || record.getName().getGender() == 'm') ? "Male" : "Female";
						
						LBL_name.setText(record.getName().getName() + " (" + genderr + ")\n");
						

						
						Object[] objectList = record.getFrequency().sortedArrayOfHeap();
						LinkedList list = new LinkedList();
						for (Object obj: objectList)
							list.insertLast(obj);

						
						
						gp.getChildren().clear();
						Label LBL_tempYEAR = new Label("YEAR");
						LBL_tempYEAR.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: transparent;");

						Label LBL_tempFREQUENCY = new Label("FREQUENCY");
						LBL_tempFREQUENCY.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: transparent;");
						
						gp.add(LBL_tempYEAR, 0, 0);
						gp.add(LBL_tempFREQUENCY, 1, 0);
						
						GridPane.setHalignment(LBL_tempYEAR, HPos.CENTER);
						GridPane.setHalignment(LBL_tempFREQUENCY, HPos.CENTER);
					
					
						int j = 1;
						for (int i = 0; i < list.size(); i++) {
							String year = ((YearFrequency)list.getAt(i)).getYear() + "";
							String freq = ((YearFrequency)list.getAt(i)).getFrequency() + "";
							
							Label LBL_yy = new Label(year);
							Label LBL_ff = new Label(freq);
						
							gp.add(LBL_yy, 0, j);
							gp.add(LBL_ff, 1, j);
							GridPane.setHalignment(LBL_yy, HPos.CENTER);
							GridPane.setHalignment(LBL_ff, HPos.CENTER);
							j++;
						}

						primaryStage.setHeight(457.3);
					}
					
					else {
						primaryStage.setHeight(177);
						Label LBL_note = new Label("There is no records is year " + yearTF);
						LBL.setText("There is no records is year " + yearTF);
						LBL_note.getStyleClass().add("note-label");	
					}
				}				
			});
				
			
			setGlobalEventHandler(root, BTN);

			
			Scene scene = new Scene(root, 400, 140);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Number Of Babies In A Year");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void sureExitPage(Stage primaryStage) {
		try {

			BorderPane errorPane = new BorderPane();
			errorPane.setPadding(new Insets(20));
	
			
				VBox vbox = new VBox(20);
					Label LBL_confirmation = new Label("Are you sure you want to exit?");				
					LBL_confirmation.setStyle("-fx-text-fill: -style1-basicColor; -fx-font-weight: bold; -fx-font-size: 105%;");
					
					HBox hbox1 = new HBox(30);
						Button BTN_cancel = new Button("Cancel And Go Back");
						BTN_cancel.getStyleClass().add("exitPage-button");
						
						Button BTN_exit = new Button("YES, Just Close This App");
						BTN_exit.getStyleClass().add("exitPage-button");
						
						hbox1.getChildren().addAll(BTN_cancel, BTN_exit);
						hbox1.setAlignment(Pos.CENTER);
						
					vbox.getChildren().addAll(LBL_confirmation, hbox1);
				errorPane.setCenter(vbox);
			
			
			
			Scene errorScene = new Scene(errorPane, 420, 120);
			errorScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			Stage sureStage = new Stage();
			sureStage.setScene(errorScene);
			sureStage.setTitle("Exit?");
			sureStage.setResizable(false);
			sureStage.getIcons().add(new Image("images/error.png"));
			
			sureStage.show();
			

			BTN_cancel.setOnAction(e -> {
				startRoot.setDisable(false);
				sureStage.close();
			});
			
			BTN_exit.setOnAction(e -> {
				System.exit(0);
			});
			
			sureStage.setOnCloseRequest(e -> {
				startRoot.setDisable(false);
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	//return true if string can cast to a long, and false if can't
	private boolean isInteger(String str) {
	   
		try { 
	        Integer.parseInt(str); 
	        
	    } catch(Exception e) { 
	        return false; 
	    
	    }
	    return true;
	}	
	
	
	private void rearrangeGridpane(GridPane gp, int x) {
		ObservableList<Node> childrens = gp.getChildren();
		x *= 3;
		
		for (int i = x; i < childrens.size(); i++) {
			Node node = childrens.get(i);
			int index = GridPane.getRowIndex(node);
			GridPane.setRowIndex(node, index-1);
		}
	}
	
	
	private boolean isFileExist(LinkedList listOfFiles, File file) {
		for (int i = 0; i < listOfFiles.size(); i++) {
			if (file.getAbsolutePath().equals(((FileYear)listOfFiles.getAt(i)).getFile().getAbsolutePath())) {
				return true;
			}
		}
		return false;
	}
	
	
	private String filesChosen(LinkedList listOfFiles) {
		for (int i = 0; i < listOfFiles.size(); i++) {
			int year = ((FileYear)listOfFiles.getAt(i)).getYear();
			if (year == -1)
				return "Invalid Input: FILE " + ((FileYear)listOfFiles.getAt(i)).getFile().getName();
			

		}
		
		return "done";
	}
	
	
	//run file
	private void openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private double getStageHeight() {
		int size = listOfFiles.size();
		if (size >= 8) {
			return 460;
		}
		
		else {
			if (size > 1) {
				return 25.5*size + 250;
			}
			
			else if (size == 1){
				return 250;
			}
			
			else {
				return 130;
			}
		}
	}
	
	
	private void fileErrorScreen(String str) {
		try {

			BorderPane errorPane = new BorderPane();
			errorPane.setPadding(new Insets(0, 0, 20, 0));
	
			Label LBL_fileError = new Label(str);
			errorPane.setCenter(LBL_fileError);
			
			Scene errorScene = new Scene(errorPane, 450, 75);
			Stage errorStage = new Stage();
			errorStage.setScene(errorScene);
			errorStage.setTitle("Error!!");
			errorStage.setResizable(false);
			errorStage.getIcons().add(new Image("images/error.png"));
			errorStage.show();
			
			errorStage.setOnCloseRequest(e -> {
				chooseWindowRoot.setDisable(false);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void setGlobalEventHandler(BorderPane root, Button btn) {
	    root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
	        if (ev.getCode() == KeyCode.ENTER) {
	           btn.fire();
	           ev.consume(); 
	        }
	    });
	}
}