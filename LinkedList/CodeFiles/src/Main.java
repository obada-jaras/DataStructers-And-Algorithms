





import java.awt.Desktop;	//for run files
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;	//for writing report
import java.util.Calendar;	//used in report
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.DirectoryChooser;	//used to choose where to save the report
import javafx.stage.FileChooser;		//used to choose files to read
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;	//used on displaying top10 students
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;


public class Main extends Application {
	Area Gaza = new Area();
	Area WestBank = new Area();
	
	File westRecordFile;
	File gazaRecordFile;
	
	Boolean canGoNext = false;
	Boolean canOpenWestBankFile = false;
	Boolean canOpenGazaFile = false;
	
	char selectionWG;
	char selectionSL = 'S';
	
	int changed = 0;
	int changedForNumAndPerc = 0;
	
	
	double mainAvg;
	String mainMode;
	double mainMedian;
	double mainVariance;
	double mainStdDev;
	int mainNumberOfAll;
	double mainGradeForAbove;
	int mainNumAbove;
	double mainPercAbove;

	Stage STG_aboveOrEqual = new Stage();
	Boolean reportExported = false;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
					
			BorderPane root = new BorderPane();
			
				VBox vbox = new VBox(20);
				
					HBox hbox1 = new HBox(80);
						VBox vbox1 = new VBox(10);
							Label LBL_chooseWestFile = new Label("Choose West Bank Record File");
							LBL_chooseWestFile.getStyleClass().add("choose-label");
							Button BTN_browseForWest = new Button("Browse");
							BTN_browseForWest.getStyleClass().add("browse-button");
							Label LBL_westFileName = new Label("fileName");
							LBL_westFileName.getStyleClass().add("fileName-label");
							vbox1.getChildren().addAll(LBL_chooseWestFile, BTN_browseForWest, LBL_westFileName);
							vbox1.setAlignment(Pos.CENTER);
						
						VBox vbox2 = new VBox(10);
							Label LBL_chooseGazaFile = new Label("Choose Gaza Record File");
							LBL_chooseGazaFile.getStyleClass().add("choose-label");
							Button BTN_browseForGaza = new Button("Browse");
							BTN_browseForGaza.getStyleClass().add("browse-button");
							Label LBL_gazaFileName = new Label("fileName");
							LBL_gazaFileName.getStyleClass().add("fileName-label");
							vbox2.getChildren().addAll(LBL_chooseGazaFile, BTN_browseForGaza, LBL_gazaFileName);
							vbox2.setAlignment(Pos.CENTER);
							
						hbox1.getChildren().addAll(vbox1, vbox2);
						hbox1.setAlignment(Pos.CENTER);	
						hbox1.setPadding(new Insets(40));
					
					
					BorderPane hbox2 = new BorderPane(); 
						Label LBL_AcceptedFilesTypes = new Label("*You must upload (.csv) or (.txt) files only");
						LBL_AcceptedFilesTypes.getStyleClass().add("note-label");
						hbox2.setLeft(LBL_AcceptedFilesTypes);
						
						Button BTN_next = new Button("Next");
						BTN_next.getStyleClass().add("next-button");
						BTN_next.setLayoutY(-20);
						hbox2.setRight(BTN_next);
						
						hbox2.setPadding(new Insets(0, 40, 70, 20));

					
						
					vbox.getChildren().addAll(hbox1, hbox2);
				
				root.setCenter(vbox);

				
					
				
			
			
			
			BTN_browseForWest.setOnAction(e -> {
				LBL_westFileName.setText("Loading.");
				
				FileChooser fc = new FileChooser();
				fc.setTitle("Choose West Bank Record File");
				westRecordFile = fc.showOpenDialog(primaryStage);
				
				
				if (westRecordFile == null) {
					LBL_westFileName.setText("fileName");
				}
				
				
				else {
				
					String fileName = westRecordFile.getName();
					String fileExtension = fileName.substring(fileName.lastIndexOf("."));
					
					try {
						if (!fileExtension.equalsIgnoreCase(".csv") && !fileExtension.equalsIgnoreCase(".txt")) {	//accepted files are just csv or txt
							throw new IllegalArgumentException("Not .csv or .txt file");
						}
						
						
						if (gazaRecordFile != null && westRecordFile.getAbsolutePath().equals(gazaRecordFile.getAbsolutePath())) {	//cannot choose the same file for Gaza and West Bank
							throw new IllegalArgumentException("File already uploaded for Gaza");
						}
							
						
						read(WestBank, westRecordFile);		//read West Bank data from westRecordFile

						
						if (WestBank.Literary.isEmpty() && WestBank.Scientific.isEmpty()) {		//cannot choose empty file
							throw new IllegalArgumentException("Empty File");
						}
						
						
						
						LBL_westFileName.setText(fileName);
						canOpenWestBankFile = true;
						
						canGoNext = (westRecordFile != null || gazaRecordFile != null);
						if (canGoNext) 
							BTN_next.setStyle("-fx-background-color: -style1-secondaryColor;");
						
						
					} catch (Exception ee){
						WestBank.clear();
						fileErrorScreen(westRecordFile, ee.getMessage());
						westRecordFile = null;
						LBL_westFileName.setText("fileName");
						canOpenWestBankFile = false;
	
						canGoNext = (westRecordFile != null || gazaRecordFile != null);
						if (canGoNext)
							BTN_next.setStyle("-fx-background-color: -style1-secondaryColor;");
						else 
							BTN_next.setStyle("-fx-background-color: -style1-gray;");
						
					}
				}
			});
			
			
			
			BTN_browseForGaza.setOnAction(e -> {
				LBL_gazaFileName.setText("Loading.");

				FileChooser fc = new FileChooser();
				fc.setTitle("Choose Gaza Record File");
				gazaRecordFile = fc.showOpenDialog(primaryStage);

				
				
				if (gazaRecordFile == null) {
					LBL_gazaFileName.setText("fileName");
				}
				
				
				else {
					String fileName = gazaRecordFile.getName();
					String fileExtension = fileName.substring(fileName.lastIndexOf("."));
					
					try {
						if (!fileExtension.equalsIgnoreCase(".csv") && !fileExtension.equalsIgnoreCase(".txt")) {	//accepted files are just csv or txt
							throw new IllegalArgumentException("Not .csv or .txt file");
						}
						
						
						if (westRecordFile != null && gazaRecordFile.getAbsolutePath().equals(westRecordFile.getAbsolutePath())) {	//cannot choose the same file for Gaza and West Bank
							throw new IllegalArgumentException("File already uploaded for West Bank");
						}
						
						
						read(Gaza, gazaRecordFile);		//read Gaza data from gazaRecordFile

						
						if (Gaza.Literary.isEmpty() && Gaza.Scientific.isEmpty()) {		//cannot choose empty file
							throw new IllegalArgumentException("Empty File");
						}
						
						

						
						
						LBL_gazaFileName.setText(fileName);
						canOpenGazaFile = true;
	
						canGoNext = (westRecordFile != null || gazaRecordFile != null);
						if (canGoNext) 
							BTN_next.setStyle("-fx-background-color: -style1-secondaryColor;");
						
						
					} catch (Exception ee){
						Gaza.clear();
						fileErrorScreen(gazaRecordFile, ee.getMessage());
						gazaRecordFile = null;
						LBL_gazaFileName.setText("fileName");
						canOpenGazaFile = false;
						
						canGoNext = (westRecordFile != null || gazaRecordFile != null);
						if (canGoNext) 
							BTN_next.setStyle("-fx-background-color: -style1-secondaryColor;");
						else 
							BTN_next.setStyle("-fx-background-color: -style1-gray;");
						
						
					}
				}
			});
			
				
			
			
			
			LBL_westFileName.setOnMouseEntered(e -> {
				if (canOpenWestBankFile) {
					LBL_westFileName.setStyle("-fx-cursor: hand;");
				}
				
				else {
					LBL_westFileName.setStyle("-fx-cursor: auto;");
				}
			});
		
				
			LBL_gazaFileName.setOnMouseEntered(e -> {
				if (canOpenGazaFile) {
					LBL_gazaFileName.setStyle("-fx-cursor: hand;");
				}
				
				else {
					LBL_gazaFileName.setStyle("-fx-cursor: auto;");
				}
			});
		
		
			
			
			
			LBL_westFileName.setOnMouseClicked(e -> {
				if (canOpenWestBankFile) {
					openFile(westRecordFile);
				}
			});
			
			
			LBL_gazaFileName.setOnMouseClicked(e -> {
				if (canOpenGazaFile) {
					openFile(gazaRecordFile);
				}
			});
				
				
			
			
			
			
			BTN_next.setOnMouseEntered(e -> {
				if (canGoNext) {
					BTN_next.setStyle("-fx-background-color: -style1-secondaryColorHover; -fx-cursor: hand;");
				}
			});
			
			
			BTN_next.setOnMouseExited(e -> {
				if (canGoNext) {
					BTN_next.setStyle("-fx-background-color: -style1-secondaryColor;");
				}
			});
			
			

			BTN_next.setOnAction(e -> {
				if (canGoNext) {
					mainPage(primaryStage);
					clearAll();
				}
			});
			
			
				
			Scene scene = new Scene(root,600,280);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tawjihi Records - Browse Files");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(e -> {
				System.exit(0);
			});


		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void fileErrorScreen(File file, String errorMsg) {
		try {

			BorderPane errorPane = new BorderPane();
			errorPane.setPadding(new Insets(0, 0, 20, 0));
	
			Label LBL_fileError = new Label("Error, cannot read Tawjihi data from " + file.getName() + " file\nError Message: " + errorMsg);
			errorPane.setCenter(LBL_fileError);
			
			Scene errorScene = new Scene(errorPane, 450, 75);
			Stage errorStage = new Stage();
			errorStage.setScene(errorScene);
			errorStage.setTitle("File Error");
			errorStage.setResizable(false);
			errorStage.getIcons().add(new Image("images/error.png"));
			errorStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public void mainPage(Stage primaryStage) {
		try {			
			BorderPane root = new BorderPane();
			
				VBox vbox = new VBox(20);
				
					VBox vbox1 = new VBox(20);
						HBox hbox1_1 = new HBox();
							Button BTN_selectWestBank = new Button("West Bank");
							BTN_selectWestBank.getStyleClass().addAll("vbox1-button", "vbox1_left-button");
							
							Button BTN_selectGaza = new Button("Gaza");
							BTN_selectGaza.getStyleClass().addAll("vbox1-button", "vbox1_right-button");

							hbox1_1.getChildren().addAll(BTN_selectWestBank, BTN_selectGaza);
							hbox1_1.setAlignment(Pos.CENTER);
													
						HBox hbox1_2 = new HBox();
							Button BTN_selectScientific = new Button("Scientific");
							BTN_selectScientific.getStyleClass().addAll("vbox1-button", "vbox1_left-button");

							Button BTN_selectLiterary = new Button("Literary");
							BTN_selectLiterary.getStyleClass().addAll("vbox1-button", "vbox1_right-button");
							BTN_selectLiterary.setStyle("-fx-background-color: -style1-gray");
							
							hbox1_2.getChildren().addAll(BTN_selectScientific, BTN_selectLiterary);
							hbox1_2.setAlignment(Pos.CENTER);
							
						vbox1.getChildren().addAll(hbox1_1, hbox1_2);
						vbox1.setPadding(new Insets(30, 0, 0, 0));
						
					Line line_first = new Line(0, 0, 400, 0);
					line_first.getStyleClass().add("lines-line");
					
					
					
					
					
					VBox vbox2 = new VBox(20);
						Button BTN_insertRecord = new Button("Insert Record");
						BTN_insertRecord.getStyleClass().add("vbox2-button");
						
						Button BTN_deleteRecord = new Button("Delete Record");
						BTN_deleteRecord.getStyleClass().add("vbox2-button");

						Button BTN_searchForStudent = new Button("Search for a Student");
						BTN_searchForStudent.getStyleClass().add("vbox2-button");

						
						vbox2.getChildren().addAll(BTN_insertRecord, BTN_deleteRecord, BTN_searchForStudent);
						vbox2.setAlignment(Pos.CENTER);
					
						
						
						
					Line line_second = new Line(0, 0, 400, 0);
					line_second.getStyleClass().add("lines-line");
						
					
					
					
					
					VBox vbox3 = new VBox(15);
						
						BorderPane hbox3_1 = new BorderPane();
							HBox avgHBox = new HBox();
								Label LBL_average = new Label("Average: ");
								LBL_average.getStyleClass().add("labels-label");

								Label LBL_averageResult = new Label("");
								LBL_averageResult.getStyleClass().addAll("labels-label", "result-label");

								avgHBox.getChildren().addAll(LBL_average, LBL_averageResult);
							hbox3_1.setLeft(avgHBox);
							
							Button BTN_getAverage = new Button("Get");
							BTN_getAverage.getStyleClass().add("get-button");
							hbox3_1.setRight(BTN_getAverage);
							
							hbox3_1.setPadding(new Insets(0, 35, 0, 35));
					
						
						BorderPane hbox3_2 = new BorderPane();
							HBox modeHBox = new HBox();
								Label LBL_mode = new Label("Mode: ");
								LBL_mode.getStyleClass().add("labels-label");

								Label LBL_modeResult = new Label("");
								LBL_modeResult.getStyleClass().addAll("labels-label", "result-label");

								modeHBox.getChildren().addAll(LBL_mode, LBL_modeResult);
							hbox3_2.setLeft(modeHBox);
							
							Button BTN_getMode = new Button("Get");
							BTN_getMode.getStyleClass().add("get-button");
							hbox3_2.setRight(BTN_getMode);
							
							hbox3_2.setPadding(new Insets(0, 35, 0, 35));
					
					
						
							
						BorderPane hbox3_3 = new BorderPane();
							HBox varianceHBox = new HBox();
								Label LBL_variance = new Label("Variance: ");
								LBL_variance.getStyleClass().add("labels-label");

								Label LBL_varianceResult = new Label("");
								LBL_varianceResult.getStyleClass().addAll("labels-label", "result-label");

								varianceHBox.getChildren().addAll(LBL_variance, LBL_varianceResult);
							hbox3_3.setLeft(varianceHBox);
							
							Button BTN_getVariance = new Button("Get");
							BTN_getVariance.getStyleClass().add("get-button");
							hbox3_3.setRight(BTN_getVariance);
							
							hbox3_3.setPadding(new Insets(0, 35, 0, 35));
					
					
						
							
								
						BorderPane hbox3_4 = new BorderPane();
							HBox stdDevHBox = new HBox();
								Label LBL_stdDeviation = new Label("Standard deviation: ");
								LBL_stdDeviation.getStyleClass().add("labels-label");

								Label LBL_stdDevResult = new Label("");
								LBL_stdDevResult.getStyleClass().addAll("labels-label", "result-label");

								stdDevHBox.getChildren().addAll(LBL_stdDeviation, LBL_stdDevResult);
							hbox3_4.setLeft(stdDevHBox);
							
							Button BTN_getStdDev = new Button("Get");
							BTN_getStdDev.getStyleClass().add("get-button");
							hbox3_4.setRight(BTN_getStdDev);
							
							hbox3_4.setPadding(new Insets(0, 35, 0, 35));
					
							
						
							
						BorderPane hbox3_5 = new BorderPane();
							HBox medianHBox = new HBox();
								Label LBL_median = new Label("Median: ");
								LBL_median.getStyleClass().add("labels-label");

								Label LBL_medianResult = new Label("");
								LBL_medianResult.getStyleClass().addAll("labels-label", "result-label");

								medianHBox.getChildren().addAll(LBL_median, LBL_medianResult);
							hbox3_5.setLeft(medianHBox);
							
							Button BTN_getMedian = new Button("Get");
							BTN_getMedian.getStyleClass().add("get-button");
							hbox3_5.setRight(BTN_getMedian);
							
							hbox3_5.setPadding(new Insets(0, 35, 0, 35));
					
							
						Button BTN_Top10 = new Button("Top 10 Students");	
						BTN_Top10.getStyleClass().add("getNumPerc-button");
						
							
						Button BTN_numAndPercOfStd = new Button("Get Number and Percentage of Students Above or Equal a Grade");	
						BTN_numAndPercOfStd.getStyleClass().add("getNumPerc-button");
						
						Label LBL_numAndPerc = new Label("");
						LBL_numAndPerc.getStyleClass().addAll("numAndPerc-label", "up15px");
						
						vbox3.getChildren().addAll(hbox3_1, hbox3_2, hbox3_5, hbox3_3, hbox3_4, BTN_Top10, BTN_numAndPercOfStd, LBL_numAndPerc);
						vbox3.setAlignment(Pos.CENTER);
					
					
					Line line_third = new Line(0, 0, 400, 0);
					line_third.getStyleClass().addAll("lines-line", "up15px");
							
						
					
					
					HBox hbox4 = new HBox(18);
						Button BTN_chooseFiles = new Button("Choose Files");
						BTN_chooseFiles.getStyleClass().addAll("hbox4-button", "up15px");
						
						Button BTN_exportReport = new Button("Export Report");
						BTN_exportReport.getStyleClass().addAll("hbox4-button", "up15px");
						
						Button BTN_exit = new Button("Exit");
						BTN_exit.getStyleClass().addAll("hbox4-button", "up15px");

						hbox4.getChildren().addAll(BTN_chooseFiles, BTN_exportReport, BTN_exit);
						hbox4.setPadding(new Insets(0, 0, 0, 33));
					

					vbox.getChildren().addAll(vbox1, line_first, vbox2, line_second, vbox3, line_third, hbox4);
					
				root.setCenter(vbox);

				
				//default selection when start the main page depending on the file chose
				if (westRecordFile != null) {
					if (selectionWG != 'G') {
						BTN_selectGaza.setStyle("-fx-background-color: -style1-gray");
						BTN_selectWestBank.setStyle("-fx-background-color: -style1-basicColor");
						selectionWG = 'W';
					} else {
						BTN_selectGaza.setStyle("-fx-background-color: -style1-basicColor");
						BTN_selectWestBank.setStyle("-fx-background-color: -style1-gray");
					}
				} 
				
				else {
					BTN_selectWestBank.setStyle("-fx-background-color: -style1-gray");
					selectionWG = 'G';
				}
				
				
				
				
				
				
				if (selectionSL != 'L') {
					BTN_selectLiterary.setStyle("-fx-background-color: -style1-gray");
					BTN_selectScientific.setStyle("-fx-background-color: -style1-basicColor");
					selectionSL = 'S';
				} else {
					BTN_selectLiterary.setStyle("-fx-background-color: -style1-basicColor");
					BTN_selectScientific.setStyle("-fx-background-color: -style1-gray");
				}
				
				

				
	
				
				
				BTN_selectWestBank.setOnAction(e -> {
					if (selectionWG == 'G') {
						if (westRecordFile != null) {
							BTN_selectGaza.setStyle("-fx-background-color: -style1-gray");
							BTN_selectWestBank.setStyle("-fx-background-color: -style1-basicColor");
							selectionWG = 'W';
							
							LBL_averageResult.setText("");
							LBL_modeResult.setText("");
							LBL_medianResult.setText("");
							LBL_varianceResult.setText("");
							LBL_stdDevResult.setText("");
							LBL_numAndPerc.setText("");
							clearAll();
						}
					}
				});
				
				
				BTN_selectGaza.setOnAction(e -> {
					if (selectionWG == 'W') {
						if (gazaRecordFile != null) {
							BTN_selectWestBank.setStyle("-fx-background-color: -style1-gray");
							BTN_selectGaza.setStyle("-fx-background-color: -style1-basicColor");
							selectionWG = 'G';
							
							LBL_averageResult.setText("");
							LBL_modeResult.setText("");
							LBL_medianResult.setText("");
							LBL_varianceResult.setText("");
							LBL_stdDevResult.setText("");
							LBL_numAndPerc.setText("");
							clearAll();
						}
					}
				});


				
				
				BTN_selectWestBank.setOnMouseEntered(e -> {
					if(selectionWG == 'G') {
						if (westRecordFile != null) {
							BTN_selectWestBank.setStyle("-fx-background-color: -style1-basicColorHover; -fx-cursor: hand;");
						}
					} 
					
					
					else {
						BTN_selectWestBank.setStyle("-fx-cursor: hand;");
					}
				});
				
				
				
				BTN_selectWestBank.setOnMouseExited(e -> {
					if(selectionWG == 'G') {
						BTN_selectWestBank.setStyle("-fx-background-color: -style1-gray");
					}
					
					else {
						BTN_selectWestBank.setStyle("-fx-background-color: -style1-basicColor");
					}
				});
				
				
				
				
				
				BTN_selectGaza.setOnMouseEntered(e -> {
					if(selectionWG == 'W') {
						if (gazaRecordFile != null) {
							BTN_selectGaza.setStyle("-fx-background-color: -style1-basicColorHover; -fx-cursor: hand;");
						}
					} 
					
					
					else {
						BTN_selectGaza.setStyle("-fx-cursor: hand;");
					}
				});
				
				
				
				BTN_selectGaza.setOnMouseExited(e -> {
					if(selectionWG == 'W') {
						BTN_selectGaza.setStyle("-fx-background-color: -style1-gray");
					}
					
					else {
						BTN_selectGaza.setStyle("-fx-background-color: -style1-basicColor");
					}
				});
				
				
				
				
				BTN_selectScientific.setOnAction(e -> {
					if (selectionSL == 'L') {
						selectionSL = 'S';
						BTN_selectLiterary.setStyle("-fx-background-color: -style1-gray");
						BTN_selectScientific.setStyle("-fx-background-color: -style1-basicColor");
						
						LBL_averageResult.setText("");
						LBL_modeResult.setText("");
						LBL_medianResult.setText("");
						LBL_varianceResult.setText("");
						LBL_stdDevResult.setText("");
						LBL_numAndPerc.setText("");
						clearAll();
					}
				});
				
				BTN_selectLiterary.setOnAction(e -> {
					if (selectionSL == 'S') {
						selectionSL = 'L';
						BTN_selectScientific.setStyle("-fx-background-color: -style1-gray");
						BTN_selectLiterary.setStyle("-fx-background-color: -style1-basicColor");
						
						LBL_averageResult.setText("");
						LBL_modeResult.setText("");
						LBL_medianResult.setText("");
						LBL_varianceResult.setText("");
						LBL_stdDevResult.setText("");
						LBL_numAndPerc.setText("");
					}
					clearAll();
				});
				
				
				
				BTN_selectScientific.setOnMouseEntered(e -> {
					
					if (selectionSL == 'L') {
						BTN_selectScientific.setStyle("-fx-background-color: -style1-basicColorHover; -fx-cursor: hand");
					}
					
					else {
						BTN_selectScientific.setStyle("-fx-cursor: hand");
					}
				});
				
				
				
				BTN_selectScientific.setOnMouseExited(e -> {
					if (selectionSL == 'L') {
						BTN_selectScientific.setStyle("-fx-background-color: -style1-gray");
					}
				});
				
				
				
				
				

				BTN_selectLiterary.setOnMouseEntered(e -> {
					if (selectionSL == 'S') {
						BTN_selectLiterary.setStyle("-fx-background-color: -style1-basicColorHover; -fx-cursor: hand");
					}
					
					else {
						BTN_selectLiterary.setStyle("-fx-cursor: hand");
					}
				});
				
				
				
				BTN_selectLiterary.setOnMouseExited(e -> {
					if (selectionSL == 'S') {
						BTN_selectLiterary.setStyle("-fx-background-color: -style1-gray");
					}
				});
				
				
				
				
				
				
				
				BTN_insertRecord.setOnAction(e -> {
					insertRecordPage(primaryStage);
				});
				
				BTN_deleteRecord.setOnAction(e -> {
					deleteRecordPage(primaryStage);
				});
				
				BTN_searchForStudent.setOnAction(e -> {
					searchStdPage(primaryStage);
				});
				
				
				
				
				
				BTN_getAverage.setOnAction(e -> {
					mainAvg = getAverage();
					LBL_averageResult.setText(String.format("%.02f", mainAvg));
				});
				
				
				BTN_getMode.setOnAction(e -> {
					mainMode = getMode();
					LBL_modeResult.setText(mainMode);
				});
				
				
				BTN_getMedian.setOnAction(e -> {
					mainMedian = getMedian();
					LBL_medianResult.setText(String.format("%.02f", mainMedian));
				});
				
				
				BTN_getVariance.setOnAction(e -> {
					mainVariance = getVariance();
					LBL_varianceResult.setText(String.format("%.02f", mainVariance));
				});
				
				BTN_getStdDev.setOnAction(e -> {
					mainStdDev = getStdDev();
					LBL_stdDevResult.setText(String.format("%.02f", mainStdDev));
				});
				
				
				BTN_Top10.setOnAction(e -> {
					top10Page();
				});
				
				
				BTN_numAndPercOfStd.setOnAction(e -> {
					numAndPercPage(primaryStage);
				});
				
				
				
				
				
				STG_aboveOrEqual.setOnHiding(e -> {
					if (changedForNumAndPerc == 1)
						LBL_numAndPerc.setText("Students above or equal grade " + mainGradeForAbove + ": " + mainNumAbove + " (" + String.format("%.02f", mainPercAbove*100) + "%)");
					
					else 
						LBL_numAndPerc.setText("");
				
				});
				
				
				BTN_chooseFiles.setOnAction(e -> {
					westRecordFile = null;
					gazaRecordFile = null;
					canGoNext = false;
					clearAll();
					selectionWG = '-';
					selectionSL = 'S';
					start(primaryStage);
				});
				
				
				
				BTN_exportReport.setOnAction(e -> {
					if (isAllFieldsGot()) {
						DirectoryChooser directoryChooser = new DirectoryChooser();
					
						File selectedDirectory = directoryChooser.showDialog(primaryStage);
	
						if(selectedDirectory != null){
							saveReportFile(selectedDirectory.getAbsolutePath() + "\\out.txt");
							reportExported = true;
						}
					}
						
					
					else 
						exportReportErrorScreen();
				});
				
				
				
				BTN_exit.setOnAction(e -> {
					sureExitPage(primaryStage, reportExported);
				});
				
				primaryStage.setOnCloseRequest(e -> {
					e.consume();
					sureExitPage(primaryStage, reportExported);
				});
				
			
			Scene scene = new Scene(root,400,740);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tawjihi Records");
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
			primaryStage.setY(40);

			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void sureExitPage(Stage primaryStage, Boolean exported) {
		try {

			BorderPane errorPane = new BorderPane();
			errorPane.setPadding(new Insets(20));
	
			
			VBox vbox = new VBox(20);
				Label LBL_confirmation;
				
				if (exported) 
					LBL_confirmation = new Label("Are you sure you want to exit?");
				
				else 
					LBL_confirmation = new Label("You didn't export a report. Are you sure you want to exit?");
				
				
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
			
			sureStage.setOnCloseRequest(e -> { e.consume();});
			sureStage.show();
			

			BTN_cancel.setOnAction(e -> {
				sureStage.close();
			});
			
			BTN_exit.setOnAction(e -> {
				sureStage.close();
				primaryStage.close();
				System.exit(0);
			});
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	
	public void exportReportErrorScreen() {
		try {

			BorderPane errorPane = new BorderPane();
			errorPane.setPadding(new Insets(0, 0, 20, 0));
	
			Label LBL_fileError = new Label("Error, you should get all fields before export a report file.");
			errorPane.setCenter(LBL_fileError);
			
			Scene errorScene = new Scene(errorPane, 450, 75);
			Stage errorStage = new Stage();
			errorStage.setScene(errorScene);
			errorStage.setTitle("Export Report Error");
			errorStage.setResizable(false);
			errorStage.getIcons().add(new Image("images/error.png"));
			errorStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void insertRecordPage(Stage primaryStage) {

		try {
			
			BorderPane root = new BorderPane();

				VBox vbox = new VBox(20);
					GridPane gpane1 = new GridPane();
						Label LBL_seatNumber = new Label("Seat Number: ");
						LBL_seatNumber.getStyleClass().add("supPage-label");
						TextField TF_seatNumber = new TextField();
						TF_seatNumber.getStyleClass().add("supPage-textField");
						
						Label LBL_grade = new Label("Grade: ");
						LBL_grade.getStyleClass().add("supPage-label");
						TextField TF_grade = new TextField();
						TF_grade.getStyleClass().add("supPage-textField");

						
						gpane1.add(LBL_seatNumber, 0, 0);
						gpane1.add(TF_seatNumber, 1, 0);
						gpane1.add(LBL_grade, 0, 1);
						gpane1.add(TF_grade, 1, 1);
						gpane1.setVgap(15);
						gpane1.setHgap(35);
						gpane1.setPadding(new Insets(10, 0, 0, 0));
						gpane1.setAlignment(Pos.CENTER);
					
					
					HBox hbox2 = new HBox();
						Button BTN_insert = new Button("Insert");
						BTN_insert.getStyleClass().add("supPage-button");
						
						hbox2.getChildren().add(BTN_insert);
						hbox2.setAlignment(Pos.CENTER);
						
					HBox hbox3 = new HBox();
						Label LBL_note = new Label("");
						LBL_note.getStyleClass().add("note-label");
						LBL_note.setStyle("-fx-translate-y: -13px;");
						
						hbox3.getChildren().add(LBL_note);
						hbox3.setAlignment(Pos.CENTER);
						
					HBox hbox4 = new HBox();
						Button BTN_done = new Button("Done");
						BTN_done.getStyleClass().add("supPage_done-button");
						BTN_done.setStyle("-fx-translate-y: -22px;");
						
						hbox4.getChildren().add(BTN_done);
						hbox4.setAlignment(Pos.CENTER_RIGHT);
						hbox4.setPadding(new Insets(0, 30, 0, 0));
					
					
					vbox.getChildren().addAll(gpane1, hbox2, hbox3, hbox4);
			
			root.setCenter(vbox);
			
			
			changed = 0; 
			

			
			BTN_insert.setOnAction(e -> {
				String seatTFstring = TF_seatNumber.getText();
				String gradeTFstring = TF_grade.getText();
				
				if (seatTFstring == "" || gradeTFstring == "") {
					LBL_note.setText("All fields must be filled");
				}
				
				
				else {
					if (isLong(seatTFstring)) {
						long seatNumber = Long.parseLong(seatTFstring);
						
						if(determineList().getBySeat(seatNumber) == null) {	//check if there is existed record with the same seat number
							int valid = isGradeValid(gradeTFstring);
						
							if (valid == 0) {		//mean its valid
								double grade = Double.parseDouble(gradeTFstring);
								String branch = selectionSL == 'S'? "Scientific": "Literary";
								String area = selectionWG == 'W'? "West Bank": "Gaza";
								
								determineList().insertRecordSorted(new TRecord(seatNumber + "," + branch + "," + grade));
								changed = 1;
								LBL_note.setText("Student with number " + seatNumber + " and grade " + grade + " added to " + area + " " + branch + " records");
								TF_seatNumber.setText("");
								TF_grade.setText("");
								clearAll();
							}
							
							else if (valid == 2) {
								LBL_note.setText("Invalid Grade. Should be between 0 and 100");
							}
							
							else {
								LBL_note.setText("Invalid Grade. Should be double or integer");
							}
						}
						
						else {
							LBL_note.setText("Student With Seat Number " + seatNumber + " Is Already Exist");
						}
					}
					
					else {
						LBL_note.setText("Invalid Seat Number. Should be integer");
					}
					
				}
			});
			
			
			
			
			TF_seatNumber.setOnKeyTyped(e -> {
				LBL_note.setText("");
			});
			
			
			TF_grade.setOnKeyTyped(e -> {
				LBL_note.setText("");
			});
			
			
			
			
			
			
			Scene scene = new Scene(root, 450, 200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Tawjihi Records - Insert Record");
			stage.setResizable(false);
			stage.getIcons().add(new Image("images/icon.png"));
			
			stage.setOnCloseRequest(e -> { e.consume();});
			stage.show();
			
			
			
			BTN_done.setOnAction(e -> {
				stage.close();
				
				if (changed == 1)
					mainPage(primaryStage);
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	public void deleteRecordPage(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();

				VBox vbox = new VBox(20);
					GridPane gpane1 = new GridPane();
						Label LBL_seatNumber = new Label("Seat Number: ");
						LBL_seatNumber.getStyleClass().add("supPage-label");
						TextField TF_seatNumber = new TextField();
						TF_seatNumber.getStyleClass().add("supPage-textField");
						
						

						
						gpane1.add(LBL_seatNumber, 0, 0);
						gpane1.add(TF_seatNumber, 1, 0);
						gpane1.setHgap(35);
						gpane1.setPadding(new Insets(10, 0, 0, 0));
						gpane1.setAlignment(Pos.CENTER);
					
					
					HBox hbox2 = new HBox();
						Button BTN_delete = new Button("Delete");
						BTN_delete.getStyleClass().add("supPage-button");
						
						hbox2.getChildren().add(BTN_delete);
						hbox2.setAlignment(Pos.CENTER);
						
					HBox hbox3 = new HBox();
						Label LBL_note = new Label("");
						LBL_note.getStyleClass().add("note-label");
						LBL_note.setStyle("-fx-translate-y: -13px;");
						
						hbox3.getChildren().add(LBL_note);
						hbox3.setAlignment(Pos.CENTER);
						
					HBox hbox4 = new HBox();
						Button BTN_done = new Button("Done");
						BTN_done.getStyleClass().add("supPage_done-button");
						BTN_done.setStyle("-fx-translate-y: -42px;");
						
						hbox4.getChildren().add(BTN_done);
						hbox4.setAlignment(Pos.CENTER_RIGHT);
						hbox4.setPadding(new Insets(0, 30, 0, 0));
					
					
					vbox.getChildren().addAll(gpane1, hbox2, hbox3, hbox4);
			
			root.setCenter(vbox);
			
			
			
			changed = 0;
			
			
			BTN_delete.setOnAction(e -> {
				String seatTFstring = TF_seatNumber.getText();
				
				if (seatTFstring == "") {
					LBL_note.setText("No Seat Number entered!!");
				}
				
				
				else {
					if (isLong(seatTFstring)) {
						long seatNumber = Long.parseLong(seatTFstring);
				
						if (determineList().deleteBySeat(seatNumber) != -1) {
							changed = 1;
							LBL_note.setText("Student with number " + seatNumber + " deleted");
							TF_seatNumber.setText("");		
							clearAll();
						}
						
						else {
							LBL_note.setText("No Student Found with Seat Number " + seatNumber);
						}
					}
					
					else {
						LBL_note.setText("Invalid Seat Number. Should be integer");
					}
					
				}
			});
			
			
			
			
			TF_seatNumber.setOnKeyTyped(e -> {
				LBL_note.setText("");
			});
			
			
			
			
			
			
			
			Scene scene = new Scene(root, 450, 130);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Tawjihi Records - Delete Record");
			stage.setResizable(false);
			stage.getIcons().add(new Image("images/icon.png"));
			
			stage.setOnCloseRequest(e -> { e.consume();});
			stage.show();
			
			
			
			BTN_done.setOnAction(e -> {
				stage.close();
				
				if (changed == 1)
					mainPage(primaryStage);
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	


	public void searchStdPage(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();

				VBox vbox = new VBox(20);
					GridPane gpane1 = new GridPane();
						Label LBL_seatNumber = new Label("Seat Number: ");
						LBL_seatNumber.getStyleClass().add("supPage-label");
						TextField TF_seatNumber = new TextField();
						TF_seatNumber.getStyleClass().add("supPage-textField");
						
						

						
						gpane1.add(LBL_seatNumber, 0, 0);
						gpane1.add(TF_seatNumber, 1, 0);
						gpane1.setHgap(35);
						gpane1.setPadding(new Insets(10, 0, 0, 0));
						gpane1.setAlignment(Pos.CENTER);
					
					
					HBox hbox2 = new HBox();
						Button BTN_search = new Button("Search");
						BTN_search.getStyleClass().add("supPage-button");
						
						hbox2.getChildren().add(BTN_search);
						hbox2.setAlignment(Pos.CENTER);
						
					HBox hbox3 = new HBox();
						Label LBL_note = new Label("");
						LBL_note.getStyleClass().add("note-label");
						LBL_note.setStyle("-fx-translate-y: -13px;");
						
						hbox3.getChildren().add(LBL_note);
						hbox3.setAlignment(Pos.CENTER);
						
					HBox hbox4 = new HBox();
						Button BTN_done = new Button("Done");
						BTN_done.getStyleClass().add("supPage_done-button");
						BTN_done.setStyle("-fx-translate-y: -42px;");
						
						hbox4.getChildren().add(BTN_done);
						hbox4.setAlignment(Pos.CENTER_RIGHT);
						hbox4.setPadding(new Insets(0, 30, 0, 0));
					
					
					vbox.getChildren().addAll(gpane1, hbox2, hbox3, hbox4);
			
			root.setCenter(vbox);
			
			
			
			
			
			BTN_search.setOnAction(e -> {
				String seatTFstring = TF_seatNumber.getText();
				
				if (seatTFstring == "") {
					LBL_note.setText("No Seat Number entered!!");
				}
				
				
				else {
					if (isLong(seatTFstring)) {
						long seatNumber = Long.parseLong(seatTFstring);
						TRecord record = determineList().getBySeat(seatNumber);
						
						if (record == null)
							LBL_note.setText("No Student Found with Seat Number " + seatNumber);
						
						else {
							LBL_note.setText(record.toString());
							TF_seatNumber.setText("");						
						}
					}
					
					else {
						LBL_note.setText("Invalid Seat Number. Should be integer");
					}
					
				}
			});
			
			
			
			
			TF_seatNumber.setOnKeyTyped(e -> {
				LBL_note.setText("");
			});
			
			
			
			
			
			
			
			Scene scene = new Scene(root, 450, 130);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Tawjihi Records - Search For A Student");
			stage.setResizable(false);
			stage.getIcons().add(new Image("images/icon.png"));
			stage.setOnCloseRequest(e -> { e.consume();});
			stage.show();
			
			
			BTN_done.setOnAction(e -> {
				stage.close();
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	public void top10Page() {

			try {
				String branch = selectionSL == 'S'? "Scientific": "Literary";
				String area = selectionWG == 'W'? "West Bank": "Gaza";
				

				VBox root = new VBox(30);
						
					HBox hbox1 = new HBox();
						Label LBL_top10 = new Label("Top 10 Students in " + branch + " branch from " + area);
						LBL_top10.getStyleClass().add("top10Label-label");
						hbox1.getChildren().add(LBL_top10);
						hbox1.setAlignment(Pos.CENTER);
					
						
					ScrollPane sp = new ScrollPane();
				    sp.setStyle("-fx-background-color:transparent;");
						
						VBox vbox2 = new VBox(10);
						vbox2.setPadding(new Insets(0, 0, 0, 160));
							
							String str = determineList().topStudentsString(10);
							
						
							while (true) {
		
								int index = str.indexOf('\n');
								if (index == -1) break;
								
								String line = str.substring(0, index);
								Label lbl = new Label(line);
								lbl.getStyleClass().add("studentInfo-label");
								
								vbox2.getChildren().add(lbl);
	
								str = str.substring(index+1);
							}
							
					sp.setContent(vbox2);
				
					
				root.getChildren().addAll(hbox1, sp);
				root.setPadding(new Insets(25, 0, 0, 0));
				
					
	
				Scene scene = new Scene(root, 600, 420);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

				Stage stage = new Stage();
				
				stage.setScene(scene);
				stage.setTitle("Tawjihi Records - Top 10 Students");
				stage.setResizable(false);
				stage.getIcons().add(new Image("images/icon.png"));
				stage.show();
				
				
				
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	

	public void numAndPercPage(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();

				VBox vbox = new VBox(20);
					GridPane gpane1 = new GridPane();
						Label LBL_grade = new Label("Grade: ");
						LBL_grade.getStyleClass().add("supPage-label");
						TextField TF_grade = new TextField();
						TF_grade.getStyleClass().add("supPage-textField");
						
						

						
						gpane1.add(LBL_grade, 0, 0);
						gpane1.add(TF_grade, 1, 0);
						gpane1.setHgap(35);
						gpane1.setPadding(new Insets(10, 0, 0, 0));
						gpane1.setAlignment(Pos.CENTER);
					
					
					HBox hbox2 = new HBox();
						Button BTN_get = new Button("Get");
						BTN_get.getStyleClass().add("supPage-button");
						
						hbox2.getChildren().add(BTN_get);
						hbox2.setAlignment(Pos.CENTER);
						
					HBox hbox3 = new HBox();
						VBox vbox3_1 = new VBox(5);
							Label LBL_note = new Label("");
							LBL_note.getStyleClass().addAll("note-label", "up15px");
						
							HBox hbox3_11 = new HBox(5);
								Label LBL_all = new Label("Number of All Students:");
								LBL_all.getStyleClass().addAll("noteLabels-label", "up15px");
							
								Label LBL_noteAll = new Label("");
								LBL_noteAll.getStyleClass().addAll("note-label", "up15px");

								hbox3_11.getChildren().addAll(LBL_all, LBL_noteAll);
								
								
								
							HBox hbox3_12 = new HBox(5);
								Label LBL_aboveOrEqual = new Label("Number of Students Above or Equal Entered Grade:");
								LBL_aboveOrEqual.getStyleClass().addAll("noteLabels-label", "up15px");
							
								Label LBL_noteAboveOrEqual = new Label("");
								LBL_noteAboveOrEqual.getStyleClass().addAll("note-label", "up15px");
	
								hbox3_12.getChildren().addAll(LBL_aboveOrEqual, LBL_noteAboveOrEqual);
							
							
							HBox hbox3_13 = new HBox(5);
								Label LBL_perc = new Label("Percentage of Students Above or Equal Entered Grade:");
								LBL_perc.getStyleClass().addAll("noteLabels-label", "up15px");
							
								Label LBL_notePercentage = new Label("");
								LBL_notePercentage.getStyleClass().addAll("note-label", "up15px");
	
								hbox3_13.getChildren().addAll(LBL_perc, LBL_notePercentage);
							
								
							vbox3_1.getChildren().addAll(LBL_note, hbox3_11, hbox3_12, hbox3_13);
								
								
						hbox3.getChildren().add(vbox3_1);
						hbox3.setPadding(new Insets(10, 0, 0, 100));
						
					HBox hbox4 = new HBox();
						Button BTN_done = new Button("Done");
						BTN_done.getStyleClass().addAll("supPage_done-button", "up15px");
						
						
						hbox4.getChildren().add(BTN_done);
						hbox4.setAlignment(Pos.CENTER_RIGHT);
						hbox4.setPadding(new Insets(0, 30, 0, 0));
					
					
					vbox.getChildren().addAll(gpane1, hbox2, hbox3, hbox4);
			
			root.setCenter(vbox);
			
			

			BTN_get.setOnAction(e -> {
				String gradeTFstring = TF_grade.getText();
				
				if (gradeTFstring == "") {
					LBL_note.setText("All fields must be filled");
					LBL_noteAll.setText("");
					LBL_noteAboveOrEqual.setText("");
					LBL_notePercentage.setText("");
				}
				
				
				else {
					int valid = isGradeValid(gradeTFstring);
					
					if (valid == 0) {		//means it's valid
						mainNumberOfAll = determineList().size();
						mainGradeForAbove = Double.parseDouble(gradeTFstring);
						mainNumAbove = determineList().numberOfStudentsAboveOrEqual(mainGradeForAbove);
						mainPercAbove = determineList().percentageOfStudentsAboveOrEqual(mainGradeForAbove);
						
						LBL_note.setText("");
						LBL_noteAll.setText(mainNumberOfAll + "");
						LBL_noteAboveOrEqual.setText(mainNumAbove + "");
						LBL_notePercentage.setText(String.format("%.02f", mainPercAbove*100) + "%");	
						changedForNumAndPerc = 1;
					}
					
					else if (valid == 2) {
						LBL_note.setText("Invalid Grade. Should be between 0 and 100");
						LBL_noteAll.setText("");
						LBL_noteAboveOrEqual.setText("");
						LBL_notePercentage.setText("");
						changedForNumAndPerc = 0;
					}
					
					else {
						LBL_note.setText("Invalid Grade. Should be double or integer");
						LBL_noteAll.setText("");
						LBL_noteAboveOrEqual.setText("");
						LBL_notePercentage.setText("");
						changedForNumAndPerc = 0;
					}
				}
			});
			
			
				
			
			
			
			
			Scene scene = new Scene(root, 450, 240);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			STG_aboveOrEqual.setScene(scene);
			STG_aboveOrEqual.setTitle("Tawjihi Records - Get Number and Percentage of Students Above or Equal a Geade");
			STG_aboveOrEqual.setResizable(false);
			STG_aboveOrEqual.getIcons().add(new Image("images/icon.png"));
			STG_aboveOrEqual.show();
			STG_aboveOrEqual.setOnCloseRequest(e -> { e.consume();});
			
			BTN_done.setOnAction(e -> {
				STG_aboveOrEqual.close();
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	


	//read files and saves them in the LinkedLists
	public static boolean read(Area area, File file) {
		area.clear();
		
		try {
			Scanner sc = new Scanner(file);
			
			while (sc.hasNext()) {
				TRecord record = new TRecord(sc.nextLine());

				if (record.getBranch().equalsIgnoreCase("Literary")) {
					area.Literary.insertRecordSorted(record);
				
				} else if (record.getBranch().equalsIgnoreCase("Scientific")) {
					area.Scientific.insertRecordSorted(record);
				
				}
				
			}
			
			sc.close();
			return true;
		
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			return false;
		}
	}
	
	
	
	
	
	//run file
	private void openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	
	
	//determine selected LinkedList
	private LinkedList determineList() {
		if (selectionWG == 'W') { 
			if (selectionSL == 'S') 
				return WestBank.Scientific;
			
			else 
				return WestBank.Literary;
		}
		
		else { 
			if (selectionSL == 'S')
				return Gaza.Scientific;
			
			else 
				return Gaza.Literary;
		}
	}
	
	
	
	
	private double getAverage() {
		return determineList().average();
	}
	
	private String getMode() {
		return determineList().mode();
	}
	
	private double getMedian() {
		return determineList().median();
	}
	
	private double getVariance() {
		return determineList().variance();
	}
	
	private double getStdDev() {
		return determineList().stdDeviation();
	}
	
	
	//return true if the string can cast to double, and false if can't
	private Boolean isDouble(String str) {
		try { 
	        Double.parseDouble(str); 
	        
	    } catch(NumberFormatException e) { 
	        return false; 
	        
	    } catch(NullPointerException e) {
	        return false;
	    
	    }
		
		
		return true;
	}
	
	
	//return 0 if valid, 1 if not double, 2 if not between 0 and 100
	private int isGradeValid(String str) {
		
		if (isDouble(str)) {
			double grade = Double.parseDouble(str);
			
			if (grade >= 0 && grade <= 100) {
				return 0;
			}
			
			else {
				return 2;
			}
		}
		
		
		else {
			return 1;
		}
	}
	
	
	//return true if string can cast to a long, and false if can't
	public boolean isLong(String str) {
	   
		try { 
	        Long.parseLong(str); 
	        
	    } catch(NumberFormatException e) { 
	        return false; 
	        
	    } catch(NullPointerException e) {
	        return false;
	    
	    }

	    return true;
	}
	
	
	
	//write string in file
	private Boolean writeFile (String str, String folderPath) {
		try {
			FileWriter fileWriter = new FileWriter(folderPath);
			fileWriter.write(str);
			fileWriter.close();
			return true;
			
			
		} catch (IOException e) {
			return false;
		}
	}
	
	
	//save report to  a file
	private Boolean saveReportFile(String folderPath) {
		String branch = selectionSL == 'S'? "SCIENTIFIC": "LITERARY";
		String area = selectionWG == 'W'? "WEST BANK": "GAZA";
		
		String date = Calendar.getInstance().getTime().toString();  
		File file = selectionWG == 'W'? westRecordFile: gazaRecordFile;
		String separator = "------------------------------------------------------------------------------------------------------------------------";
		
		
		String str = area + " " + branch + " TAWJIHI STUDENTS REPORT\t\t\t\t\t" + date + "\n\n"
					+ "Records File:\n"
					+ "\tFile Name: " + file.getName()
					+ "\n\tFile Path: " +file.getAbsolutePath() + "\n\n"
					+ separator + "\n\n"
					+ "Number Of All Students: " + mainNumberOfAll + "\n\n"
					+ separator + "\n\n"
					+ "Grades Statistics:"
					+ "\n\t\tAverage: " + String.format("%.02f", mainAvg)
					+ "\n\t\tMode: " + mainMode
					+ "\n\t\tMedian: " +  String.format("%.02f", mainMedian)
					+ "\n\t\tVariance: " + String.format("%.02f", mainVariance)
					+ "\n\t\tStandard Deviation: " + String.format("%.02f", mainStdDev) + "\n\n"
					+ separator + "\n\n"
					+ "List of Top 10 Students\n"
					+ determineList().topStudentsString(10)
					+ separator + "\n\n"
					+ "Number Students Whose Grades Are More Than "
						+ mainGradeForAbove + " is " + mainNumAbove + " Student, Which Constitutes "
						+ String.format("%.02f", mainPercAbove*100) + "% Of The Number Of Students\n\n"
					+ separator + "\n\n"
					+ "\t\t\t\t\t\t--- THANKS, OBADA TAHAYNA ---\n\n\t\t\t\t\t\t  --- END OF THE REPORT ---";
		
		writeFile(str, folderPath);
		return true;
	}
	
	
	//return true if all fields got
	private Boolean isAllFieldsGot() {
		if (mainAvg != -1 && mainMode != null && mainMedian != -1 && mainVariance != -1 && mainStdDev != -1 && mainNumberOfAll != -1) {
			return true;
		}
		
		return false;
	}
	
	
	//clear all main variables
	private void clearAll() {
		changedForNumAndPerc = 0;
		reportExported = false;
		
		mainAvg = -1;
		mainMode = null;
		mainMedian = -1;
		mainVariance = -1;
		mainStdDev = -1;
		mainNumberOfAll = -1;
		mainGradeForAbove = -1;
		mainNumAbove = -1;
		mainPercAbove = -1;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
