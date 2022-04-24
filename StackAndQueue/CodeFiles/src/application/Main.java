package application;

import Project2_Pkg.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Lists.Queue;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;


public class Main extends Application {
	File sharesFile;
	File dailyPriceFile;
	
	boolean FLAG_sharesFileChosen = false;
	boolean FLAG_dailyPriceFileChosen = false;
	char FLAG_modeSelected = 'E';
	boolean FLAG_canReadFile = false;
	boolean FLAG_canGoNextStep = false;
	boolean FLAG_canBuy = false;
	boolean FLAG_canSell = false;
	boolean FLAG_changed = false;
	
	BorderPane startRoot = new BorderPane();
	BorderPane buySellRootPane = new BorderPane();
	Label LBL_capitalGainLossValue = new Label();
	String STR_details = "";
	Button BTN_showHistory;
	Button BTN_storeNewData;
	
	
	
//	@Override
	public void start(Stage primaryStage) {
		try {
			
			VBox vbox0 = new VBox(50);
				VBox vbox1 = new VBox(55);
					
					VBox vbox1_1 = new VBox(3);
					
						Button BTN_browseSharesFile = new Button("Browse File");
						BTN_browseSharesFile.getStyleClass().add("enabled-button");
						Label LBL_fileChosen = new Label("fileName");
						LBL_fileChosen.getStyleClass().add("disabled-label");
						vbox1_1.getChildren().addAll(BTN_browseSharesFile, LBL_fileChosen);
						vbox1_1.setAlignment(Pos.CENTER);
					
						
					VBox vbox1_2 = new VBox(15);
						
						Image img = new Image("images/emptyCircle.png");
						ImageView imgViewEmpty1 = new ImageView(img);
						imgViewEmpty1.setFitHeight(16);
						imgViewEmpty1.setFitWidth(16);
						
						ImageView imgViewEmpty2 = new ImageView(img);
						imgViewEmpty2.setFitHeight(16);
						imgViewEmpty2.setFitWidth(16);
						
						ImageView imgViewFilled = new ImageView(new Image("images/filledCircle.png"));
						imgViewFilled.setFitHeight(16);
						imgViewFilled.setFitWidth(16);
						
						ToggleGroup tg = new ToggleGroup();
	
						ToggleButton tBTN_oldFirst = new ToggleButton("  Sell Old Shares First");
						tBTN_oldFirst.setAlignment(Pos.CENTER_LEFT);
						tBTN_oldFirst.setGraphic(imgViewEmpty1);
						tBTN_oldFirst.setToggleGroup(tg);
						tBTN_oldFirst.getStyleClass().add("toggleButton");
	
						
						ToggleButton tBTN_newFirst = new ToggleButton("  Sell New Shares First");
						tBTN_newFirst.setAlignment(Pos.CENTER_LEFT);
						tBTN_newFirst.setGraphic(imgViewEmpty2);
						tBTN_newFirst.setToggleGroup(tg);
						tBTN_newFirst.getStyleClass().add("toggleButton");
						
						
						vbox1_2.getChildren().addAll(tBTN_oldFirst, tBTN_newFirst);
						vbox1_2.setAlignment(Pos.CENTER);
						vbox1_2.setTranslateY(-20);
						
					
						
				
					Button BTN_readFile = new Button("Read File");
					BTN_readFile.getStyleClass().add("disabled-button");
					
						
						
					vbox1.getChildren().addAll(vbox1_1, vbox1_2, BTN_readFile);
					vbox1.setAlignment(Pos.CENTER);	
					
					
				Line line = new Line(0, 0, 400, 0);
				line.getStyleClass().add("line");
					
				
				VBox vbox2 = new VBox(30);	
					HBox hbox2_1 = new HBox(15);
						Button BTN_buyOrSell = new Button("BUY/SELL");
						BTN_buyOrSell.getStyleClass().add("disabled-button");
						
						Button BTN_addCompany = new Button("Add New Company");
						BTN_addCompany.getStyleClass().add("disabled-button");
						
						hbox2_1.getChildren().addAll(BTN_buyOrSell, BTN_addCompany);
						hbox2_1.setAlignment(Pos.CENTER);

					
					HBox hbox2_2 = new HBox(15);
						Button BTN_showBoughtShares = new Button("Show Bought Shares");
						BTN_showBoughtShares.getStyleClass().add("disabled-button");
						
						Button BTN_showCompanies = new Button("Show Companies");
						BTN_showCompanies.getStyleClass().add("disabled-button");
						
						hbox2_2.getChildren().addAll(BTN_showBoughtShares, BTN_showCompanies);
						hbox2_2.setAlignment(Pos.CENTER);
						
						
					HBox hbox2_3 = new HBox(15);
						BTN_showHistory = new Button("Trading History");
						BTN_showHistory.getStyleClass().add("disabled-button");
						
						BTN_storeNewData = new Button("Store New Data");
						BTN_storeNewData.getStyleClass().add("disabled-button");
						
						hbox2_3.getChildren().addAll(BTN_showHistory, BTN_storeNewData);
						hbox2_3.setAlignment(Pos.CENTER);
						
						
					HBox hbox2_4 = new HBox(10);
						Label LBL_totalName = new Label("Total Gain/Loss:");
						LBL_totalName.getStyleClass().add("disabled-label");
						
						hbox2_4.getChildren().addAll(LBL_totalName, LBL_capitalGainLossValue);
						hbox2_4.setAlignment(Pos.CENTER);
						
					HBox hbox2_5 = new HBox();
						Button BTN_exit = new Button("Exit");
						BTN_exit.getStyleClass().addAll("enabled-button", "exit-button");
						hbox2_5.getChildren().add(BTN_exit);
						hbox2_5.setAlignment(Pos.CENTER_RIGHT);
						hbox2_5.setPadding(new Insets(0, 30, 30, 0));
						
					vbox2.getChildren().addAll(hbox2_1, hbox2_2, hbox2_3, hbox2_4, hbox2_5);
					vbox2.setAlignment(Pos.CENTER);
				
				
				vbox0.getChildren().addAll(vbox1, line, vbox2);
				vbox0.setAlignment(Pos.TOP_CENTER);
				vbox0.setPadding(new Insets(40, 0, 0, 0));
			
			
			startRoot.setCenter(vbox0);
			
			
			
			
			
			
			
			
			
			
			
			
			BTN_browseSharesFile.setOnAction(e -> {
				File tempFile = sharesFile;

				try {
					FileChooser fc = new FileChooser();
					fc.setTitle("Choose File");
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
					fc.getExtensionFilters().add(extFilter);
					sharesFile = fc.showOpenDialog(primaryStage);
	
					LBL_fileChosen.setText(sharesFile.getName());
					LBL_fileChosen.getStyleClass().add("enabled-label");					
					FLAG_sharesFileChosen = true;
					checkReadButtonAbility(BTN_readFile);
					setOtherButtonAbility(false, BTN_buyOrSell, BTN_addCompany, BTN_showBoughtShares, BTN_showCompanies);
					Driver.capitalGainLoss = 0;
					LBL_capitalGainLossValue.setText("");
					
				
				} catch (Exception ee){
					if (tempFile != null) {
						sharesFile = tempFile;
					}
				}
			});
			
			
			
			LBL_fileChosen.setOnMouseClicked(e -> {
				if (FLAG_sharesFileChosen)
					openFile(sharesFile);
			});
			
			
			
			
			
			
			tBTN_oldFirst.setOnAction(e -> {
				if (tBTN_oldFirst.isSelected()) {
					FLAG_modeSelected = 'Q';
					tBTN_oldFirst.setGraphic(imgViewFilled);
					tBTN_newFirst.setGraphic(imgViewEmpty2);
				} 
				
				else {
					FLAG_modeSelected = 'E';
					tBTN_oldFirst.setGraphic(imgViewEmpty1);
				}
				
				checkReadButtonAbility(BTN_readFile);
				setOtherButtonAbility(false, BTN_buyOrSell, BTN_addCompany, BTN_showBoughtShares, BTN_showCompanies, BTN_showHistory, BTN_storeNewData);
				Driver.capitalGainLoss = 0;
				LBL_capitalGainLossValue.setText("");
			});
			
			
			tBTN_newFirst.setOnAction(e -> {
				if (tBTN_newFirst.isSelected()) {
					FLAG_modeSelected = 'S';
					tBTN_newFirst.setGraphic(imgViewFilled);
					tBTN_oldFirst.setGraphic(imgViewEmpty1);
				} 
				
				else {
					FLAG_modeSelected = 'E';
					tBTN_newFirst.setGraphic(imgViewEmpty2);
				}
				
				checkReadButtonAbility(BTN_readFile);
				setOtherButtonAbility(false, BTN_buyOrSell, BTN_addCompany, BTN_showBoughtShares, BTN_showCompanies, BTN_showHistory, BTN_storeNewData);
				Driver.capitalGainLoss = 0;
				LBL_capitalGainLossValue.setText("");
			});
			
			
			
			
			
			
			BTN_readFile.setOnAction(e -> {
				try {
					if (FLAG_canReadFile) {
						Driver.queueList.clear();
						Driver.stackList.clear();
						Driver.readFile(sharesFile, FLAG_modeSelected);
						setOtherButtonAbility(true, BTN_buyOrSell, BTN_addCompany, BTN_showBoughtShares, BTN_showCompanies);
					}
				
				} catch (Exception e1) {
					fileErrorScreen(sharesFile);
				}
			});
			
			
			
			
			
			BTN_buyOrSell.setOnAction(e -> {
				if (FLAG_canGoNextStep) {
					if (dailyPriceFile == null) {
						chooseDailyPricePage(new Stage(), 1);
					}
					
					else {
						buySellRootPane.setDisable(false);
						buyAndSellWindow(new Stage());
					}
					
					startRoot.setDisable(true);
				}
			});
			
			
			BTN_addCompany.setOnAction(e -> {
				if (FLAG_canGoNextStep) {
					if (dailyPriceFile == null) {
						chooseDailyPricePage(new Stage(), 2);
					}
					
					else {
						buySellRootPane.setDisable(false);
						addCompanyWindow(new Stage());
					}
					
					startRoot.setDisable(true);
				}
			});
			
			
			
			BTN_showBoughtShares.setOnAction(e -> {
				if (FLAG_canGoNextStep) {
					if (dailyPriceFile == null) {
						chooseDailyPricePage(new Stage(), 3);
					}
					
					else {
						showRecordsWindow(new Stage());
					}
					
					startRoot.setDisable(true);
				}
			});
			
			
			
			
			BTN_showCompanies.setOnAction(e -> {
				if (FLAG_canGoNextStep) {
					if (dailyPriceFile == null) {
						chooseDailyPricePage(new Stage(), 4);
					}
					
					else {
						showCompaniesWindow(new Stage());
					}
					
					startRoot.setDisable(true);
				}
			});
			
			
			
			
			BTN_showHistory.setOnAction(e -> {
				if (FLAG_changed) {
					showHistory(new Stage());
					startRoot.setDisable(true);
				}
			});
			
			
			
			BTN_storeNewData.setOnAction(e -> {
				if (FLAG_changed) {
					storeDataInFiles(new Stage());
					startRoot.setDisable(true);
				}
			});			
			

			
			
			Scene scene = new Scene(startRoot, 400, 670);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Stocks");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				startRoot.setDisable(true);
				buySellRootPane.setDisable(true);
				sureExitPage(new Stage());
			});
			
			BTN_exit.setOnAction(e -> {
				startRoot.setDisable(true);
				buySellRootPane.setDisable(true);
				sureExitPage(new Stage());
			});

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public void chooseDailyPricePage(Stage primaryStage, int selection) {
		try {
			BorderPane root = new BorderPane();
			
				VBox vbox = new VBox(30);
				
					Label LBL_shouldBrowse = new Label("Choose Stocks Daily Price File");
					
					VBox vbox1 = new VBox(5);
						Button BTN_browseDailyPriceFile = new Button("Browse");
						BTN_browseDailyPriceFile.getStyleClass().add("enabled-button");
						Label LBL_dailyPriceFileName = new Label();
						if (dailyPriceFile == null) {
							LBL_dailyPriceFileName.setText("");
							LBL_dailyPriceFileName.getStyleClass().add("disabled-label");
						}
						else {
							LBL_dailyPriceFileName.setText(dailyPriceFile.getName());
							LBL_dailyPriceFileName.getStyleClass().add("enabled-label");
						}
						
						vbox1.getChildren().addAll(BTN_browseDailyPriceFile, LBL_dailyPriceFileName);
						vbox1.setAlignment(Pos.CENTER);
						
					HBox hbox2 = new HBox();
						Button BTN_nextToBuySell = new Button("Next");
						BTN_nextToBuySell.setStyle("-fx-pref-width: 60px;");
						BTN_nextToBuySell.getStyleClass().addAll("disabled-button");
						
						hbox2.getChildren().add(BTN_nextToBuySell);
						hbox2.setAlignment(Pos.CENTER_RIGHT);
						hbox2.setPadding(new Insets(20));
						
					vbox.getChildren().addAll(LBL_shouldBrowse, vbox1, hbox2);
					vbox.setAlignment(Pos.CENTER);
					vbox.setPadding(new Insets(30, 0, 0, 0));
					
				root.setCenter(vbox);
			
	
				
				
			BTN_browseDailyPriceFile.setOnAction(e -> {
				File tempFile = dailyPriceFile;

				try {
					FileChooser fc = new FileChooser();
					fc.setTitle("Choose File");
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
					fc.getExtensionFilters().add(extFilter);
					dailyPriceFile = fc.showOpenDialog(primaryStage);
					String fileName = dailyPriceFile.getName();
					String fileExtension = fileName.substring(fileName.lastIndexOf("."));
	
					LBL_dailyPriceFileName.setText(dailyPriceFile.getName());
					LBL_dailyPriceFileName.getStyleClass().add("enabled-label");					
					
					Driver.dailyPrices.clear();
					if (Driver.readDailyPrice(dailyPriceFile) && fileExtension.equalsIgnoreCase(".txt")) {
						FLAG_dailyPriceFileChosen = true;
						BTN_nextToBuySell.getStyleClass().add("exit-button");
					}
					
					else {
						FLAG_dailyPriceFileChosen = false;
						BTN_nextToBuySell.getStyleClass().clear();
						BTN_nextToBuySell.getStyleClass().addAll("button", "disabled-button");
						fileErrorScreen(dailyPriceFile);
						
					}
					
					
				} catch (Exception ee){
					if (tempFile != null) {
						dailyPriceFile = tempFile;
					}
				}
			});
				
				
			
			
			LBL_dailyPriceFileName.setOnMouseClicked(e -> {
				if (dailyPriceFile != null && FLAG_dailyPriceFileChosen)
					openFile(dailyPriceFile);
			});
			
			
			
			
			BTN_nextToBuySell.setOnAction(e -> {
				if (FLAG_dailyPriceFileChosen) {
					switch (selection) {
						case 1: buyAndSellWindow(primaryStage); break;
						case 2: addCompanyWindow(primaryStage); break;
						case 3: showRecordsWindow(primaryStage); break;
						case 4: showCompaniesWindow(primaryStage); break;
					}
				}
			});
			
			
			
			
			
				
			
			Scene scene = new Scene(root, 400, 235);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Choose Daily Price File");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
			
			
			
			
			primaryStage.setOnCloseRequest(e -> {
				startRoot.setDisable(false);
				LBL_capitalGainLossValue.setText(Driver.capitalGainLoss + "");
			});	
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void buyAndSellWindow(Stage primaryStage) {
		try {			
			
				VBox vbox = new VBox(50);
				
					VBox vbox1 = new VBox(30);
						
						HBox hbox1_1 = new HBox();
							HBox hbox1_1_1 = new HBox();
								Label LBL_FILE = new Label("File: ");
								Label LBL_fileName = new Label(dailyPriceFile.getName());
								LBL_fileName.getStyleClass().add("enabled-label");
								
								hbox1_1_1.getChildren().addAll(LBL_FILE, LBL_fileName);
								hbox1_1_1.setPadding(new Insets(0, 15, 0, 0));
							
							hbox1_1.getChildren().add(hbox1_1_1);
							hbox1_1.setAlignment(Pos.CENTER_RIGHT);
							
							
						VBox vbox1_2 = new VBox(10);
							HBox hbox1_2_1 = new HBox(7);
								Label LBL_STOCK = new Label("Stock");
								ComboBox<String> cb = new ComboBox<String>();
								for (int i = 0; i < Driver.dailyPrices.size(); i++) {
									cb.getItems().add(((CompanyPrice)Driver.dailyPrices.getAt(i)).getCompanyName());
								}
								cb.setVisibleRowCount(5);
								
								hbox1_2_1.getChildren().addAll(LBL_STOCK, cb);
								hbox1_2_1.setAlignment(Pos.CENTER);
								
							HBox hbox1_2_2 = new HBox();
								Label LBL_TODAYPRICE = new Label("Today Price: ");
								LBL_TODAYPRICE.getStyleClass().add("disabled-label");
								Label LBL_todayPriceValue = new Label("");
								LBL_todayPriceValue.getStyleClass().add("note-label");
								hbox1_2_2.getChildren().addAll(LBL_TODAYPRICE, LBL_todayPriceValue);
								hbox1_2_2.setAlignment(Pos.CENTER);

								
							HBox hbox1_2_3 = new HBox();
								Label LBL_OWNEDSHARES = new Label("Owned Shares: ");
								LBL_OWNEDSHARES.getStyleClass().add("disabled-label");
								Label LBL_ownedSharesValue = new Label("");
								LBL_ownedSharesValue.getStyleClass().add("note-label");
								hbox1_2_3.getChildren().addAll(LBL_OWNEDSHARES, LBL_ownedSharesValue);
								hbox1_2_3.setAlignment(Pos.CENTER);

							
							HBox hbox1_2_4 = new HBox();
								Label LBL_AVGPRICE = new Label("Average Buying Price: ");
								LBL_AVGPRICE.getStyleClass().add("disabled-label");
								Label LBL_avgPriceValue = new Label("");
								LBL_avgPriceValue.getStyleClass().add("note-label");
								hbox1_2_4.getChildren().addAll(LBL_AVGPRICE, LBL_avgPriceValue);
								hbox1_2_4.setAlignment(Pos.CENTER);

								
							vbox1_2.getChildren().addAll(hbox1_2_1, hbox1_2_2, hbox1_2_3, hbox1_2_4);
							
						vbox1.getChildren().addAll(hbox1_1, vbox1_2);
							
					
					Line line = new Line(0, 0, 400, 0);
					line.getStyleClass().add("line");					
					
					
					VBox vbox2 = new VBox(30);
						
						TextField TF_numberOfShares = new TextField();
						TF_numberOfShares.setPromptText("Number Of Shares");
						TF_numberOfShares.setDisable(true);
						
						HBox hbox2_1 = new HBox(20);
							Button BTN_buy = new Button("BUY");
							BTN_buy.getStyleClass().add("disabled-button");
							Button BTN_sell = new Button("SELL");
							BTN_sell.getStyleClass().add("disabled-button");

							
							hbox2_1.getChildren().addAll(BTN_buy, BTN_sell);
							hbox2_1.setAlignment(Pos.CENTER);
						
						HBox hbox2_2 = new HBox();
							Label LBL_confirmationMsg = new Label("");
							LBL_confirmationMsg.getStyleClass().add("note-label");
							
							Label LBL_clickForDetails = new Label("");
							LBL_clickForDetails.getStyleClass().addAll("note-label", "details-label");

							hbox2_2.getChildren().addAll(LBL_confirmationMsg, LBL_clickForDetails);
							hbox2_2.setAlignment(Pos.CENTER);
						
						HBox hbox2_3 = new HBox();
							Button BTN_done = new Button("Done");
							BTN_done.getStyleClass().addAll("exit-button", "enabled-button");
							
							hbox2_3.getChildren().add(BTN_done);
							hbox2_3.setAlignment(Pos.CENTER_RIGHT);
							hbox2_3.setPadding(new Insets(0, 15, 0, 0));
							
						vbox2.getChildren().addAll(TF_numberOfShares, hbox2_1, hbox2_2, hbox2_3);
						vbox2.setAlignment(Pos.CENTER);
					
					vbox.getChildren().addAll(vbox1, line, vbox2);
					vbox.setPadding(new Insets(10, 0, 0, 0));
					
				buySellRootPane.setCenter(vbox);
			
			
			
				
				
				
				
				
				
			LBL_fileName.setOnMouseClicked(e -> {
				chooseDailyPricePage(primaryStage, 1);
			});
			
			
			cb.setOnAction(e -> {
				String companyName = cb.getValue();
				LBL_TODAYPRICE.setStyle("-fx-text-fill: black");
				LBL_OWNEDSHARES.setStyle("-fx-text-fill: black");
				LBL_AVGPRICE.setStyle("-fx-text-fill: black");
				
				LBL_todayPriceValue.setText((Driver.getCompanyPrice(companyName) + "").trim());
				
				String str = Driver.getOwnedSharesAndAveragePrice(companyName, FLAG_modeSelected);
				LBL_ownedSharesValue.setText(str.substring(0, str.indexOf(",")).trim());
				LBL_avgPriceValue.setText(str.substring(str.indexOf(",")+1).trim());
				
				TF_numberOfShares.setDisable(false);
			});
			
			
			
			

			TF_numberOfShares.setOnKeyTyped(e -> {
				if (!isInteger(TF_numberOfShares.getText())) {
					TF_numberOfShares.setText(TF_numberOfShares.getText().replaceAll("[^\\d]", ""));
				}
				TF_numberOfShares.positionCaret(TF_numberOfShares.getText().length());
				
				
				
				if (!TF_numberOfShares.getText().equals("")) {
					BTN_buy.getStyleClass().add("buy-button");
					FLAG_canBuy = true;
					
					if (Integer.parseInt(TF_numberOfShares.getText()) <= Integer.parseInt(LBL_ownedSharesValue.getText())) {
						BTN_sell.getStyleClass().add("sell-button");
						FLAG_canSell = true;
					}
					
					else {
						BTN_sell.getStyleClass().clear();
						BTN_sell.getStyleClass().addAll("button", "disabled-button");
						FLAG_canSell = false;
					}
				}
				
				else {
					BTN_buy.getStyleClass().clear();
					BTN_buy.getStyleClass().addAll("button", "disabled-button");
					BTN_sell.getStyleClass().clear();
					BTN_sell.getStyleClass().addAll("button", "disabled-button");
					FLAG_canBuy = false;
					FLAG_canSell = false;
				}
			});
			
			
			
			
			
			BTN_buy.setOnAction(e -> {
				if (FLAG_canBuy) {
					String companyName = cb.getValue();
					int numberOfShares = Integer.parseInt(TF_numberOfShares.getText());
						
					Driver.buyShare(numberOfShares, companyName, FLAG_modeSelected);
					LBL_confirmationMsg.setText(numberOfShares + " Shares From " + companyName +  " Company Was Successfully Bought ");
					LBL_clickForDetails.setText("");
					FLAG_changed = true;
					BTN_showHistory.getStyleClass().add("enabled-button");
					BTN_storeNewData.getStyleClass().add("enabled-button");
					

				
					
					String str = Driver.getOwnedSharesAndAveragePrice(companyName, FLAG_modeSelected);
					LBL_ownedSharesValue.setText(str.substring(0, str.indexOf(",")).trim());
					LBL_avgPriceValue.setText(str.substring(str.indexOf(",")+1).trim());
					
					
					if (Integer.parseInt(TF_numberOfShares.getText()) <= Integer.parseInt(LBL_ownedSharesValue.getText())) {
						BTN_sell.getStyleClass().add("sell-button");
						FLAG_canSell = true;
					}
				}
			});
			
			
			
			BTN_sell.setOnAction(e -> {
				if (FLAG_canSell) {
					String companyName = cb.getValue();
					int numberOfShares = Integer.parseInt(TF_numberOfShares.getText());
					
					

					STR_details = Driver.sellShares(numberOfShares, companyName, FLAG_modeSelected);
					LBL_confirmationMsg.setText(numberOfShares + " Shares From " + companyName +  " Company Was Successfully Sold ");
					LBL_clickForDetails.setText("(Details)");
					FLAG_changed = true;
					BTN_showHistory.getStyleClass().add("enabled-button");
					BTN_storeNewData.getStyleClass().add("enabled-button");
					
					
					
					String str = Driver.getOwnedSharesAndAveragePrice(companyName, FLAG_modeSelected);
					LBL_ownedSharesValue.setText(str.substring(0, str.indexOf(",")).trim());
					LBL_avgPriceValue.setText(str.substring(str.indexOf(",")+1).trim());
					
					
					
					if (Integer.parseInt(TF_numberOfShares.getText()) > Integer.parseInt(LBL_ownedSharesValue.getText())) {
						BTN_sell.getStyleClass().clear();
						BTN_sell.getStyleClass().addAll("button", "disabled-button");
						FLAG_canSell = false;
					}
					
					LBL_capitalGainLossValue.setText(Driver.capitalGainLoss + "");
				}
			});
			
			
			
			LBL_clickForDetails.setOnMouseClicked(e -> {
				showSellDetails(STR_details);
				buySellRootPane.setDisable(true);
			});
			
			
			
			
			
			
			Scene scene = new Scene(buySellRootPane, 400, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Buy/Sell Shares");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();

			
			BTN_done.setOnAction(e -> {
				buySellRootPane = new BorderPane();
				primaryStage.close();
				startRoot.setDisable(false);
			});
			
			primaryStage.setOnCloseRequest(e -> {
				buySellRootPane = new BorderPane();
				primaryStage.close();
				startRoot.setDisable(false);
			});
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void addCompanyWindow(Stage primaryStage) {
		
		try {	
			
			BorderPane root = new BorderPane();

			
			VBox vbox = new VBox(35);
			
				GridPane GP = new GridPane();
				GP.setAlignment(Pos.CENTER);
				GP.setHgap(15);
				GP.setVgap(15);
				
					Label LBL_companyName = new Label("Company Name");
					GP.add(LBL_companyName, 0, 0);
					
					TextField TF_companyName = new TextField();
					GP.add(TF_companyName, 1, 0);

					
					Label LBL_stockPrice = new Label("Stock Price");
					GP.add(LBL_stockPrice, 0, 1);
					
					TextField TF_stockPrice = new TextField();
					GP.add(TF_stockPrice, 1, 1);

					
				Button BTN_addCompany = new Button("Add Company");
				BTN_addCompany.getStyleClass().add("enabled-button");
				Label LBL_confirmation = new Label("");
				LBL_confirmation.getStyleClass().add("note-label");
				LBL_confirmation.setStyle("-fx-translate-y: -20px;");
				
				
				vbox.getChildren().addAll(GP, BTN_addCompany, LBL_confirmation);
				vbox.setAlignment(Pos.CENTER);
				
			root.setCenter(vbox);
			
			
			
			BTN_addCompany.setOnAction(e -> {
				if (TF_companyName.getText().isEmpty() || TF_stockPrice.getText().isEmpty()) {
					LBL_confirmation.setText("You Should Fill All Fields");
				}
				
				else if (!isDouble(TF_stockPrice.getText())) {
					LBL_confirmation.setText("Stock Price Should Be Number");
				}
				
				else {
					double price = Driver.getCompanyPrice(TF_companyName.getText());
					String companyName = TF_companyName.getText();
					double stockPrice = Double.parseDouble(TF_stockPrice.getText());
					
					if (price == -1) {
						Driver.dailyPrices.insertLast(new CompanyPrice(companyName, stockPrice));
						Driver.history += "Company " + companyName + " with price " + stockPrice + " was added\n";
						LBL_confirmation.setText("Company " + companyName + " Added With Price " + stockPrice);
						FLAG_changed = true;
						BTN_showHistory.getStyleClass().add("enabled-button");
						BTN_storeNewData.getStyleClass().add("enabled-button");
					}
					
					else {
						LBL_confirmation.setText("Company " + companyName + " Is Already Exist With Price " + price);
					}
				}
			});
			
			
			
			
			Scene scene = new Scene(root, 400, 270);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Add Company");
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
	
	
	
	
	
	
	
	public void showRecordsWindow(Stage primaryStage) {
		try {	

			BorderPane root = new BorderPane();


				VBox vbox = new VBox(25);

					HBox hbox = new HBox(10);
						Label LBL_companyChosen = new Label("Company");

						ComboBox<String> cb = new ComboBox<String>();
						cb.getItems().clear();
						cb.getItems().add("ALL");
						
						for (int i = 0; i < Driver.dailyPrices.size(); i++) {
							cb.getItems().add(((CompanyPrice)Driver.dailyPrices.getAt(i)).getCompanyName());
						}

						cb.getSelectionModel().select(0);
						cb.setVisibleRowCount(5);

						hbox.getChildren().addAll(LBL_companyChosen, cb);
						hbox.setAlignment(Pos.CENTER);


					ScrollPane sp = new ScrollPane();
					sp.setStyle("-fx-background-color:transparent;");
						
						GridPane gridPane = new GridPane();
						gridPane.setHgap(40);
							Label LBL_NumOfShares = new Label("NUMBER OF SHARES");
							LBL_NumOfShares.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
							
							Label LBL_SharePrice = new Label("PRICE PER SHARE");
							LBL_SharePrice.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
	
							Label LBL_CompanyName = new Label("COMPANY NAME");
							LBL_CompanyName.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");

							Label LBL_BuyingDate = new Label("DATE OF BUYING");
							LBL_BuyingDate.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
							
							gridPane.add(LBL_NumOfShares, 0, 0);
							gridPane.add(LBL_SharePrice, 1, 0);
							gridPane.add(LBL_CompanyName, 2, 0);
							gridPane.add(LBL_BuyingDate, 3, 0);
							
							GridPane.setHalignment(LBL_NumOfShares, HPos.CENTER);
							GridPane.setHalignment(LBL_SharePrice, HPos.CENTER);
							GridPane.setHalignment(LBL_CompanyName, HPos.CENTER);
							GridPane.setHalignment(LBL_BuyingDate, HPos.CENTER);


							String str = Driver.getRecords(FLAG_modeSelected);
							Scanner scanner = new Scanner(str);
							int i=1;
							while (scanner.hasNextLine()) {
								String line = scanner.nextLine();
							  
								int firstComma = line.indexOf(",");
								int secondComma = line.indexOf(",", firstComma+1);
								int lastComma = line.lastIndexOf(",");
								
								Label numberOfShares = new Label(line.substring(0, firstComma).trim());
								Label pricePerShare = new Label(line.substring(firstComma+1, secondComma).trim());
								Label companyName = new Label(line.substring(secondComma+1, lastComma).trim());
								Label dateOfBuying = new Label(line.substring(lastComma+1).trim());
								GridPane.setHalignment(numberOfShares, HPos.CENTER);
								GridPane.setHalignment(pricePerShare, HPos.CENTER);
								GridPane.setHalignment(companyName, HPos.CENTER);
								GridPane.setHalignment(dateOfBuying, HPos.CENTER);

								
								
								gridPane.add(numberOfShares, 0, i);
								gridPane.add(pricePerShare, 1, i);
								gridPane.add(companyName, 2, i);
								gridPane.add(dateOfBuying, 3, i);
								i++;
							}
							scanner.close();

						
						sp.setContent(gridPane);
						sp.setPadding(new Insets(0, 0, 0, 70));

					

					vbox.getChildren().addAll(hbox, sp);
					vbox.setPadding(new Insets(30, 0, 20, 0));
				
				root.setCenter(vbox);
				

				
				
				
			cb.setOnAction(e -> {
				if (cb.getValue().equalsIgnoreCase("ALL")) {
					gridPane.getChildren().clear();
					Label LBL_NumOfShares1 = new Label("NUMBER OF SHARES");
					LBL_NumOfShares1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
					
					Label LBL_SharePrice1 = new Label("PRICE PER SHARE");
					LBL_SharePrice1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");

					Label LBL_CompanyName1 = new Label("COMPANY NAME");
					LBL_CompanyName1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");

					Label LBL_BuyingDate1 = new Label("DATE OF BUYING");
					LBL_BuyingDate1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
					
					gridPane.add(LBL_NumOfShares1, 0, 0);
					gridPane.add(LBL_SharePrice1, 1, 0);
					gridPane.add(LBL_CompanyName1, 2, 0);
					gridPane.add(LBL_BuyingDate1, 3, 0);
					
					GridPane.setHalignment(LBL_NumOfShares1, HPos.CENTER);
					GridPane.setHalignment(LBL_SharePrice1, HPos.CENTER);
					GridPane.setHalignment(LBL_CompanyName1, HPos.CENTER);
					GridPane.setHalignment(LBL_BuyingDate1, HPos.CENTER);
					
					String str1 = Driver.getRecords(FLAG_modeSelected);
					Scanner scanner1 = new Scanner(str1);
					int i1=1;
					while (scanner1.hasNextLine()) {
						String line = scanner1.nextLine();
					  
						int firstComma = line.indexOf(",");
						int secondComma = line.indexOf(",", firstComma+1);
						int lastComma = line.lastIndexOf(",");
						
						Label numberOfShares = new Label(line.substring(0, firstComma).trim());
						Label pricePerShare = new Label(line.substring(firstComma+1, secondComma).trim());
						Label companyName = new Label(line.substring(secondComma+1, lastComma).trim());
						Label dateOfBuying = new Label(line.substring(lastComma+1).trim());
						GridPane.setHalignment(numberOfShares, HPos.CENTER);
						GridPane.setHalignment(pricePerShare, HPos.CENTER);
						GridPane.setHalignment(companyName, HPos.CENTER);
						GridPane.setHalignment(dateOfBuying, HPos.CENTER);
	
						
						
						gridPane.add(numberOfShares, 0, i1);
						gridPane.add(pricePerShare, 1, i1);
						gridPane.add(companyName, 2, i1);
						gridPane.add(dateOfBuying, 3, i1);
						i1++;
					}
					scanner1.close();
				}
				else {
					gridPane.getChildren().clear();
					Label LBL_NumOfShares1 = new Label("NUMBER OF SHARES");
					LBL_NumOfShares1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
					
					Label LBL_SharePrice1 = new Label("PRICE PER SHARE");
					LBL_SharePrice1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");

					Label LBL_CompanyName1 = new Label("COMPANY NAME");
					LBL_CompanyName1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");

					Label LBL_BuyingDate1 = new Label("DATE OF BUYING");
					LBL_BuyingDate1.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
					
					gridPane.add(LBL_NumOfShares1, 0, 0);
					gridPane.add(LBL_SharePrice1, 1, 0);
					gridPane.add(LBL_CompanyName1, 2, 0);
					gridPane.add(LBL_BuyingDate1, 3, 0);
					
					GridPane.setHalignment(LBL_NumOfShares1, HPos.CENTER);
					GridPane.setHalignment(LBL_SharePrice1, HPos.CENTER);
					GridPane.setHalignment(LBL_CompanyName1, HPos.CENTER);
					GridPane.setHalignment(LBL_BuyingDate1, HPos.CENTER);
					String str1 =Driver.getCompanyRecords(cb.getValue(), FLAG_modeSelected);
					Scanner scanner1 = new Scanner(str1);
					int i1=1;
					while (scanner1.hasNextLine()) {
						String line = scanner1.nextLine();
					  
						int firstComma = line.indexOf(",");
						int secondComma = line.indexOf(",", firstComma+1);
						int lastComma = line.lastIndexOf(",");
						
						Label numberOfShares = new Label(line.substring(0, firstComma).trim());
						Label pricePerShare = new Label(line.substring(firstComma+1, secondComma).trim());
						Label companyName = new Label(line.substring(secondComma+1, lastComma).trim());
						Label dateOfBuying = new Label(line.substring(lastComma+1).trim());
						GridPane.setHalignment(numberOfShares, HPos.CENTER);
						GridPane.setHalignment(pricePerShare, HPos.CENTER);
						GridPane.setHalignment(companyName, HPos.CENTER);
						GridPane.setHalignment(dateOfBuying, HPos.CENTER);
	
						
						
						gridPane.add(numberOfShares, 0, i1);
						gridPane.add(pricePerShare, 1, i1);
						gridPane.add(companyName, 2, i1);
						gridPane.add(dateOfBuying, 3, i1);
						i1++;
					}
					scanner1.close();
				}
			});
			
		
	
			Scene scene = new Scene(root, 700, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Stocks");
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
	
	
	
	
	
	public void showCompaniesWindow(Stage primaryStage) {
		try {	

			BorderPane root = new BorderPane();
			root.setPadding(new Insets(20, 0, 0, 0));

				GridPane GP = new GridPane();
				GP.setAlignment(Pos.TOP_CENTER);
				GP.setHgap(20);
					
					Label LBL_titleCompanyName = new Label("COMPANY NAME");
					LBL_titleCompanyName.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
				
					Label LBL_titleStockPrice = new Label("PRICE");
					LBL_titleStockPrice.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #ff7171;");
				
					GP.add(LBL_titleCompanyName, 0, 0);
					GP.add(LBL_titleStockPrice, 1, 0);
					GridPane.setHalignment(LBL_titleCompanyName, HPos.CENTER);
					GridPane.setHalignment(LBL_titleStockPrice, HPos.CENTER);
	
					
					Queue tempQueue = Driver.dailyPrices.toQueue();
					int i = 1;
					while (!tempQueue.isEmpty()) {
						CompanyPrice temp = (CompanyPrice)tempQueue.dequeue();;
						String name = temp.getCompanyName();
						double price = temp.getPrice();
						
						Label LBL_companyName = new Label(name);
						Label LBL_stockPrice = new Label(price+"");
					
						GP.add(LBL_companyName, 0, i);
						GP.add(LBL_stockPrice, 1, i);
						GridPane.setHalignment(LBL_companyName, HPos.CENTER);
						GridPane.setHalignment(LBL_stockPrice, HPos.CENTER);
						i++;
					}
	
					
					root.setCenter(GP);
				
			
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("COMPANIES");
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
	
	
	
	
	public void showHistory(Stage primaryStage) {
		try {	

			BorderPane root = new BorderPane();
			root.setPadding(new Insets(20, 0, 0, 0));
			
				ScrollPane SP = new ScrollPane();
				Label LBL_history = new Label(Driver.history);
					SP.setContent(LBL_history);
					
				root.setCenter(SP);
			
			
			
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("TRADING HISTORY");
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
	
	

	
	public void storeDataInFiles(Stage primaryStage) {
		try {

			BorderPane pane = new BorderPane();
			pane.setPadding(new Insets(20));
	
			
				VBox vbox = new VBox(20);
				Label LBL_confirmation = new Label("Both shares file and dailyPrices file will be updated");
				LBL_confirmation.setStyle("-fx-text-fill: #ff7171; -fx-font-weight: bold; -fx-font-size: 105%;");
				
				HBox hbox1 = new HBox(30);
					Button BTN_cancel = new Button("Cancel And Go Back");
					BTN_cancel.getStyleClass().addAll("exit-button", "exitSure-button");
					
					Button BTN_ok = new Button("OK");
					BTN_ok.getStyleClass().addAll("exit-button", "exitSure-button");
					
					hbox1.getChildren().addAll(BTN_cancel, BTN_ok);
					hbox1.setAlignment(Pos.CENTER);
					
				
					vbox.getChildren().addAll(LBL_confirmation, hbox1);
		
				pane.setCenter(vbox);
			
		
			BTN_cancel.setOnAction(e -> {
				primaryStage.close();
				startRoot.setDisable(false);
			});
			
			BTN_ok.setOnAction(e -> {
				primaryStage.close();
				startRoot.setDisable(false);
				Driver.saveData(sharesFile, dailyPriceFile, FLAG_modeSelected);
			});
			
			
			Scene scene = new Scene(pane, 420, 120);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("STORE DATA");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/icon.png"));
			primaryStage.show();
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	public void fileErrorScreen(File file) {
		try {

			BorderPane errorPane = new BorderPane();
			errorPane.setPadding(new Insets(0, 0, 20, 0));
	
			Label LBL_fileError = new Label("Error, cannot read data from " + file.getName() + " file");
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
	
	
	
	
	public void showSellDetails(String str) {
		try {

			BorderPane Pane = new BorderPane();
			Pane.setPadding(new Insets(30));
	
			Label LBL = new Label(str);
			Pane.setCenter(LBL);
			
			Scene scene = new Scene(Pane, 550, 200);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("File Error");
			stage.setResizable(false);
			stage.getIcons().add(new Image("images/icon.png"));
			stage.show();
			
			
			stage.setOnCloseRequest(e -> {
				buySellRootPane.setDisable(false);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void sureExitPage(Stage primaryStage) {
		try {

			BorderPane pane = new BorderPane();
			pane.setPadding(new Insets(20));
	
			
			VBox vbox = new VBox(20);
				Label LBL_confirmation = new Label("Are you sure you want to exit?");
				LBL_confirmation.setStyle("-fx-text-fill: #ff7171; -fx-font-weight: bold; -fx-font-size: 105%;");
				
				HBox hbox1 = new HBox(30);
					Button BTN_cancel = new Button("Cancel And Go Back");
					BTN_cancel.getStyleClass().addAll("exit-button", "exitSure-button");
					
					Button BTN_exit = new Button("YES, Just Close The Application");
					BTN_exit.getStyleClass().addAll("exit-button", "exitSure-button");
					
					hbox1.getChildren().addAll(BTN_cancel, BTN_exit);
					hbox1.setAlignment(Pos.CENTER);
					
				
				vbox.getChildren().addAll(LBL_confirmation, hbox1);
			

			pane.setCenter(vbox);
			
			
			Scene scene = new Scene(pane, 420, 120);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			Stage sureStage = new Stage();
			sureStage.setScene(scene);
			sureStage.setTitle("Exit?");
			sureStage.setResizable(false);
			sureStage.getIcons().add(new Image("images/icon.png"));
			sureStage.show();
			

			BTN_cancel.setOnAction(e -> {
				sureStage.close();
				startRoot.setDisable(false);
			});
			
			BTN_exit.setOnAction(e -> {
				System.exit(0);
			});
			
			sureStage.setOnCloseRequest(e -> {
				sureStage.close();
				startRoot.setDisable(false);
			});

			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	private void openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void checkReadButtonAbility(Button btn) {
		if (FLAG_sharesFileChosen && FLAG_modeSelected != 'E') {
			btn.getStyleClass().add("enabled-button");
			FLAG_canReadFile = true;
		}
		
		else {
			btn.getStyleClass().clear();
			btn.getStyleClass().addAll("button", "disabled-button");
			FLAG_canReadFile = false;
		}
	}
	
	
	
	private void setOtherButtonAbility(boolean ability, Button... btn) {
		if (ability) {
			FLAG_canGoNextStep = true;
			
			for (int i = 0; i < btn.length; i++) {
				btn[i].getStyleClass().add("enabled-button");
			}
		
		}

		else {
			FLAG_canGoNextStep = false;
			
			for (int i = 0; i < btn.length; i++) {
				btn[i].getStyleClass().clear();
				btn[i].getStyleClass().addAll("button", "disabled-button");
			}
		}
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
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
