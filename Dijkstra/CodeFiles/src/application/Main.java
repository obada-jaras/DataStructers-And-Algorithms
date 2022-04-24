package application;
	

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Project.City;
import Project.Dijkstra;
import Project.Graph;
import Project.Vertex;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Main extends Application {
	Graph graph = new Graph();
	boolean FLG_startSelected = false;
	String FLG_lastSelectedCityName = "";
	Vertex city1;
	Vertex city2;
	Circle CRCL_city1 = new Circle();
	Circle CRCL_city2 = new Circle();
	Label LBL_city1 = new Label();
	Label LBL_city2 = new Label();
	int FLG_number = 0;
	char FLG_lastAdded = 'd';
	boolean FLG_startCombo = false;
	boolean FLG_destinationCombo = false;
	int numberOfPaths = 0;
	ArrayList<Circle> AL_allCitiesPoints = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			readFile(new File("input.txt"));
			ArrayList<Vertex> vertices = graph.getVertices();
			
			
			BorderPane root = new BorderPane();
			
				VBox VBOX_left = new VBox(70);
				VBOX_left.setMinWidth(830);
				VBOX_left.setPadding(new Insets(25));
				VBOX_left.setAlignment(Pos.TOP_CENTER);
				VBOX_left.getStyleClass().add("vbox-left");
				
					GridPane GP_selections = new GridPane();
					GP_selections.setHgap(20);
					GP_selections.setVgap(40);
					GP_selections.setAlignment(Pos.CENTER);
				
						String[] citiesNames = new String[vertices.size()];
						for (int i = 0; i < citiesNames.length; i++) {
							citiesNames[i] = vertices.get(i).getCity().name;
						}
						
						Label LBL_start = new Label("Starting Province");
						LBL_start.getStyleClass().add("label-comboLabel-selectingNow");
						GP_selections.add(LBL_start, 0, 0);
						
						ComboBox<String> CB_start = new ComboBox<String>(FXCollections.observableArrayList(citiesNames));
						CB_start.setStyle("-fx-border-color: -secondColor;");
						GP_selections.add(CB_start, 1, 0);
						
						Label LBL_destination = new Label("Destination Province");
						LBL_destination.getStyleClass().add("comboLabel");
						GP_selections.add(LBL_destination, 0, 1);

						ComboBox<String> CB_destination = new ComboBox<String>(FXCollections.observableArrayList(citiesNames));
						CB_destination.setStyle("-fx-border-color: black;");
						GP_selections.add(CB_destination, 1, 1);
						
						
					Button BTN_getShortestPath = new Button("Get Shortest Path");
					BTN_getShortestPath.getStyleClass().add("button-getPath");
					
					
					TextArea TA_path = new TextArea("SHORTEST PATH IS: ");
					TA_path.setEditable(false);
					TA_path.setWrapText(true);
					TA_path.getStyleClass().add("textarea-path");
					
					
					
					Label LBL_distance = new Label("");
					
					Button BTN_reset = new Button("Clear");
					BTN_reset.getStyleClass().add("button-reset");
					
					
					VBOX_left.getChildren().addAll(GP_selections, BTN_getShortestPath, TA_path, LBL_distance, BTN_reset);
					
					
					
				Pane PANE_right = new Pane();
					Image img = new Image("italy.png");
					ImageView imgV = new ImageView(img);
					imgV.setFitHeight(840);
					imgV.setFitWidth(700);
					
					PANE_right.getChildren().addAll(imgV);
					PANE_right.getStyleClass().add("pane-right");
					

					for (Vertex vertex: vertices) {
				    	String cityName = vertex.getCity().name;
				    	double xCity = vertex.getCity().xCoordinate;
				    	double yCity = vertex.getCity().yCoordinate;
				        
				    	
				    	Circle CRCL_cityPoint = new Circle(xCity, yCity, 4);
				    	CRCL_cityPoint.getStyleClass().add("circle-province");
				    	PANE_right.getChildren().add(CRCL_cityPoint);
				    	AL_allCitiesPoints.add(CRCL_cityPoint);
				    	
				    	CRCL_cityPoint.setOnMouseEntered(e -> {
				    		Label LBL_onMapCityName = new Label(cityName);
				    		LBL_onMapCityName.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.5), new CornerRadii(5.0), new Insets(-5.0))));
				    		LBL_onMapCityName.getStyleClass().add("label-onMapCityName");
				    		LBL_onMapCityName.setLayoutX(xCity-10);
				    		LBL_onMapCityName.setLayoutY(yCity-25);
				    		PANE_right.getChildren().add(LBL_onMapCityName);
				    	});
				    	
				    	CRCL_cityPoint.setOnMouseExited(e -> {
				    		PANE_right.getChildren().remove(PANE_right.getChildren().size()-1);
				    	});
				    	
				    	
				    	CRCL_cityPoint.setOnMousePressed(e -> {
				    		if (!FLG_lastSelectedCityName.equals(cityName)) {
				    			FLG_lastSelectedCityName = cityName;
				    			
					    		if (!FLG_startSelected) {
					    			if (FLG_number > 1) {
						    			if (FLG_lastAdded == 'd') {
							    			PANE_right.getChildren().remove(PANE_right.getChildren().size()-3);
						    			}
						    			else {
						    				PANE_right.getChildren().remove(PANE_right.getChildren().size()-2);
						    			}
					    			}
					    			FLG_number++;
					    			
					    			CRCL_city1.setStyle("-fx-fill: red;");
					    			CRCL_city1 = CRCL_cityPoint;
					    			CRCL_city1.setStyle("-fx-fill: green;");
					    			
					    			CB_start.setValue(cityName);
					    			
					    			Label LBL_onMapCityName = new Label(cityName);
						    		LBL_onMapCityName.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.5), new CornerRadii(5.0), new Insets(-5.0))));
						    		LBL_onMapCityName.getStyleClass().add("label-onMapCityName");
						    		LBL_onMapCityName.setLayoutX(xCity-10);
						    		LBL_onMapCityName.setLayoutY(yCity-25);
						    		PANE_right.getChildren().add(LBL_onMapCityName);

						    		
						    		FLG_startSelected = true;
						    		FLG_lastAdded = 's';
						    		
						    		LBL_destination.getStyleClass().clear();
						    		LBL_destination.getStyleClass().add("label-comboLabel-selectingNow");
						    		CB_start.setStyle("-fx-border-color: black;");

						    		LBL_start.getStyleClass().clear();
						    		LBL_start.getStyleClass().add("comboLabel");
						    		CB_destination.setStyle("-fx-border-color: -secondColor;");
						    		
						    		city1 = vertex;
						    	}
					    		
					    		else {
					    			if (FLG_number > 1) {
						    			if (FLG_lastAdded == 's') {
							    			PANE_right.getChildren().remove(PANE_right.getChildren().size()-3);
						    			}
						    			else {
						    				PANE_right.getChildren().remove(PANE_right.getChildren().size()-2);
						    			}
					    			}
					    			FLG_number++;
					    			
					    			
					    			CRCL_city2.setStyle("-fx-fill: red;");
					    			CRCL_city2 = CRCL_cityPoint;
					    			CRCL_city2.setStyle("-fx-fill: yellow;");
					    			
					    			CB_destination.setValue(cityName);
					    			
					    			Label LBL_onMapCityName = new Label(cityName);
						    		LBL_onMapCityName.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.5), new CornerRadii(5.0), new Insets(-5.0))));
						    		LBL_onMapCityName.getStyleClass().add("label-onMapCityName");
						    		LBL_onMapCityName.setLayoutX(xCity-10);
						    		LBL_onMapCityName.setLayoutY(yCity-25);
						    		PANE_right.getChildren().add(LBL_onMapCityName);

						    		
						    		FLG_startSelected = false;
						    		FLG_lastAdded = 'd';
						    		
						    		LBL_start.getStyleClass().clear();
						    		LBL_start.getStyleClass().add("label-comboLabel-selectingNow");
						    		CB_start.setStyle("-fx-border-color: -secondColor;");
						    		
						    		LBL_destination.getStyleClass().clear();
						    		LBL_destination.getStyleClass().add("comboLabel");
						    		CB_destination.setStyle("-fx-border-color: black;");
						    		
						    		city2 = vertex;
					    		}
				    		}
				    	});
					}
					
					
				CB_start.setOnMouseClicked(e -> {
					FLG_startCombo = true;
					FLG_startSelected = false;
		    		
		    		LBL_start.getStyleClass().clear();
		    		LBL_start.getStyleClass().add("label-comboLabel-selectingNow");
		    		CB_start.setStyle("-fx-border-color: -secondColor;");
		    		
		    		LBL_destination.getStyleClass().clear();
		    		LBL_destination.getStyleClass().add("comboLabel");
		    		CB_destination.setStyle("-fx-border-color: black;");
				});	
				
				CB_destination.setOnMouseClicked(e -> {
					FLG_destinationCombo = true;
					FLG_startSelected = true;
		    		
		    		LBL_destination.getStyleClass().clear();
		    		LBL_destination.getStyleClass().add("label-comboLabel-selectingNow");
		    		CB_start.setStyle("-fx-border-color: black;");

		    		LBL_start.getStyleClass().clear();
		    		LBL_start.getStyleClass().add("comboLabel");
		    		CB_destination.setStyle("-fx-border-color: -secondColor;");
				});
				
				
				LBL_start.setOnMouseClicked(e -> {
					FLG_startSelected = false;
		    		
		    		LBL_start.getStyleClass().clear();
		    		LBL_start.getStyleClass().add("label-comboLabel-selectingNow");
		    		CB_start.setStyle("-fx-border-color: -secondColor;");
		    		
		    		LBL_destination.getStyleClass().clear();
		    		LBL_destination.getStyleClass().add("comboLabel");
		    		CB_destination.setStyle("-fx-border-color: black;");
				});
				
				
				LBL_destination.setOnMouseClicked(e -> {
					FLG_startSelected = true;
		    		
		    		LBL_destination.getStyleClass().clear();
		    		LBL_destination.getStyleClass().add("label-comboLabel-selectingNow");
		    		CB_start.setStyle("-fx-border-color: black;");

		    		LBL_start.getStyleClass().clear();
		    		LBL_start.getStyleClass().add("comboLabel");
		    		CB_destination.setStyle("-fx-border-color: -secondColor;");
				});
					
				CB_start.valueProperty().addListener(new ChangeListener<String>() {	
					@SuppressWarnings("rawtypes")
					@Override 
					public void changed(ObservableValue ov, String t, String t1) {
						String c1 = CB_start.getValue() == null ? "" : CB_start.getValue().trim();
						String c2 = CB_destination.getValue() == null ? "" : CB_destination.getValue().trim();
						if (c1.equals(c2) && !c1.equals("")) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("select different provinces");
							alert.show();
							String cName = city1 == null ? null : city1.getCity().name;
							CB_start.setValue(cName);
						}
						
						else {
							if (FLG_startCombo) {
								String cityName = CB_start.getValue().trim();
								
								
								if (!FLG_lastSelectedCityName.equals(cityName)) {
					    			FLG_lastSelectedCityName = cityName;
					    			FLG_startCombo = false;
					    			
									city1 = graph.getVertex(cityName);
									
									int k = -1;
									for (int i = 0; i < vertices.size(); i++) {
										if (vertices.get(i) == city1) {
											k = i;
										}
									}
									Circle CRCL_cityPoint = AL_allCitiesPoints.get(k);
									double xCity = city1.getCity().xCoordinate;
									double yCity = city1.getCity().yCoordinate;
									
									if (FLG_number > 1) {
						    			if (FLG_lastAdded == 'd') {
						    				PANE_right.getChildren().remove(PANE_right.getChildren().size()-2);
						    			}
						    			else {
						    				PANE_right.getChildren().remove(PANE_right.getChildren().size()-1);
						    			}
									}
					    			FLG_number++;
	
					    			CRCL_city1.setStyle("-fx-fill: red;");
					    			CRCL_city1 = CRCL_cityPoint;
					    			CRCL_city1.setStyle("-fx-fill: green;");
					    			
					    			
					    			Label LBL_onMapCityName = new Label(cityName);
						    		LBL_onMapCityName.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.5), new CornerRadii(5.0), new Insets(-5.0))));
						    		LBL_onMapCityName.getStyleClass().add("label-onMapCityName");
						    		LBL_onMapCityName.setLayoutX(xCity-10);
						    		LBL_onMapCityName.setLayoutY(yCity-25);
						    		PANE_right.getChildren().add(LBL_onMapCityName);
	
						    		
						    		FLG_startSelected = true;
						    		FLG_lastAdded = 's';
						    		
						    		LBL_destination.getStyleClass().clear();
						    		LBL_destination.getStyleClass().add("label-comboLabel-selectingNow");
						    		CB_start.setStyle("-fx-border-color: black;");
	
						    		LBL_start.getStyleClass().clear();
						    		LBL_start.getStyleClass().add("comboLabel");
						    		CB_destination.setStyle("-fx-border-color: -secondColor;");
								}
							}
						}
					}
				});
					
					
					
				
				CB_destination.valueProperty().addListener(new ChangeListener<String>() {	
					@SuppressWarnings("rawtypes")
					@Override 
					public void changed(ObservableValue ov, String t, String t1) {
						
						String c1 = CB_start.getValue() == null ? "" : CB_start.getValue().trim();
						String c2 = CB_destination.getValue() == null ? "" : CB_destination.getValue().trim();
						if (c1.equals(c2) && !c1.equals("")) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("select different provinces");
							alert.show();
							String cName = city2 == null ? null : city2.getCity().name;
							CB_destination.setValue(cName);
						}
						
						else {
							if (FLG_destinationCombo) {
								String cityName = CB_destination.getValue().trim();
								
								
								if (!FLG_lastSelectedCityName.equals(cityName)) {
					    			FLG_lastSelectedCityName = cityName;
					    			FLG_destinationCombo = false;
					    			
									city2 = graph.getVertex(cityName);
									
									int k = -1;
									for (int i = 0; i < vertices.size(); i++) {
										if (vertices.get(i) == city2) {
											k = i;
										}
									}
									Circle CRCL_cityPoint = AL_allCitiesPoints.get(k);
									double xCity = city2.getCity().xCoordinate;
									double yCity = city2.getCity().yCoordinate;
									
					    			
									if (FLG_number > 1) {
						    			if (FLG_lastAdded == 's') {
						    				PANE_right.getChildren().remove(PANE_right.getChildren().size()-2);
						    			}
						    			else {
						    				PANE_right.getChildren().remove(PANE_right.getChildren().size()-1);
						    			}
									}
					    			FLG_number++;
					    			
					    			CRCL_city2.setStyle("-fx-fill: red;");
					    			CRCL_city2 = CRCL_cityPoint;
					    			CRCL_city2.setStyle("-fx-fill: yellow;");
					    			
					    			
					    			Label LBL_onMapCityName = new Label(cityName);
						    		LBL_onMapCityName.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.5), new CornerRadii(5.0), new Insets(-5.0))));
						    		LBL_onMapCityName.getStyleClass().add("label-onMapCityName");
						    		LBL_onMapCityName.setLayoutX(xCity-10);
						    		LBL_onMapCityName.setLayoutY(yCity-25);
						    		PANE_right.getChildren().add(LBL_onMapCityName);
			
						    		
						    		FLG_startSelected = false;
						    		FLG_lastAdded = 'd';
						    		
						    		
						    		LBL_start.getStyleClass().clear();
						    		LBL_start.getStyleClass().add("label-comboLabel-selectingNow");
						    		CB_start.setStyle("-fx-border-color: -secondColor;");
						    		
						    		LBL_destination.getStyleClass().clear();
						    		LBL_destination.getStyleClass().add("comboLabel");
						    		CB_destination.setStyle("-fx-border-color: black;");
								}
							}
						}
					}
				});	
					
					
					
					
				BTN_getShortestPath.setOnAction(e -> {
					if (city1 != null && city2 != null) {
						try {
							Dijkstra Dijkstra = new Dijkstra(city1);
							ArrayList<Vertex> pathVerticies = Dijkstra.getShortestPathTo(city2);
							
							String str = "";
							for (int i = 0; i < pathVerticies.size(); i++) {
								str += pathVerticies.get(i).getCity().name+" >> ";
							}
							
							str = str.substring(0, str.length()-3);
							TA_path.setText("SHORTEST PATH FROM " + city1.getCity().name + " TO " + city2.getCity().name + " IS: " + str);
							
							
							
							double distance = 0;
							for (int i = 1; i < pathVerticies.size(); i++) {
								Vertex v1 = pathVerticies.get(i-1);
								Vertex v2 = pathVerticies.get(i);
								double x1 = v1.getCity().xCoordinate;
								double y1 = v1.getCity().yCoordinate;
								double x2 = v2.getCity().xCoordinate;
								double y2 = v2.getCity().yCoordinate;
								drawArrowLine(x1, y1, x2, y2, PANE_right);
								
								distance += realDistance(y1, y2, x1, x2);
							}
							LBL_distance.setText("Distance: " + String.format("%.2f", distance) + "Kilometer");
							LBL_distance.getStyleClass().add("label-distance");
							FLG_number = 0;
							
							
							for (Vertex v : graph.getVertices()) {
								v.known = false;
								v.pathPrev = null;
							}						
							
						} catch (Exception e1) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText(e1.getMessage());
							System.out.println(e1.getMessage());
							alert.show();
						}
					}
					
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("you should select start and end provinces");
						alert.show();
					}
					
				});
			
					
				
				
					
				BTN_reset.setOnAction(e1 -> {
					ObservableList<Node> ob = PANE_right.getChildren();
					while (ob.size() > 103) {
						ob.remove(ob.size()-1);
					}
					
					CRCL_city1.setStyle("-fx-fill: red;");
					CRCL_city1 = new Circle();
					city1 = null;
					
					CRCL_city2.setStyle("-fx-fill: red;");
					CRCL_city2 = new Circle();
					city2 = null;
					
					CB_start.setValue(null);
					CB_destination.setValue(null);
					
					TA_path.setText("SHORTEST PATH IS: ");
					LBL_distance.setText("");
				});
				
				
	
				root.setLeft(VBOX_left);
				root.setRight(PANE_right);
			
			
			Scene scene = new Scene(root, Integer.MAX_VALUE, Double.MAX_VALUE);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Italy Provinces Map");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("icon.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	private void readFile(File file) throws FileNotFoundException {		
		Scanner sc = new Scanner(file);
		
		int numberOfCities = sc.nextInt();
		int numberOfEdges = sc.nextInt();
		sc.nextLine();
		
		for (int i = 0; i < numberOfCities; i++) {
			String line = sc.nextLine();
			String[] arr = line.split(",");
			String cityName = arr[0].trim();
			double cityX = returnX(Double.parseDouble(arr[1].trim()));
			double cityY = returnY(Double.parseDouble(arr[2].trim()));
			
			graph.addVertex(new Vertex(new City(cityName, cityX, cityY)));
		}
		
		for (int i = 0; i < numberOfEdges; i++) {
			String line = sc.nextLine();
			String[] arr = line.split(",");
			String city1 = arr[0].trim();
			String city2 = arr[1].trim();
			
			graph.addAdjacentToCity(city1, city2);
		}
		
		sc.close();
	}
	
	private static double returnX(double xx) {
		double xRatio = 56.336073643024726;
		double initialX = 6.21135;
		return (xx-initialX) * xRatio;
	}
	
	private static double returnY(double yy) {
		double yRatio = -78.60325931102648;
		double initialY = 47.408;
		return (yy-initialY) * yRatio;
	}
	
	
	
	private double realDistance(double lat1, double lat2, double lon1, double lon2){
		lat1 = returnRealY(lat1);
		lat2 = returnRealY(lat2);
		lon1 = returnRealX(lon1);
		lon2 = returnRealX(lon2);
		
		// The math module contains a function
		// named toRadians which converts from
		// degrees to radians.
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		
		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);
		double c = 2 * Math.asin(Math.sqrt(a));
		
		// Radius of earth in kilometers.
		double r = 6371;
		
		// calculate the result
		return(c * r);
	}

	
	private static double returnRealX (double xx) {
		double xRatio = 56.336073643024726;
		double initialX = 6.21135;
		return (xx / xRatio) + initialX;
	}
	
	private static double returnRealY (double yy) {
		double yRatio = 78.60325931102648;
		double initialY = 47.408;
		return (yy / yRatio) + initialY;
	}
	
	
	
	private static void drawArrowLine(double startX, double startY, double endX, double endY, Pane pane) {
		// get the slope of the line and find its angle
		double slope = (startY - endY) / (startX - endX);
		double lineAngle = Math.atan(slope);
		
		double arrowAngle = startX > endX ? Math.toRadians(45) : -Math.toRadians(225);
		
		Line line = new Line(startX, startY, endX, endY);
		line.getStyleClass().add("line-pathLine");
		
		double arrowLength = 5.5;
				
		// create the arrow legs
		Line arrow1 = new Line();
		arrow1.getStyleClass().add("line-pathLine");
		arrow1.setStartX(line.getEndX());
		arrow1.setStartY(line.getEndY());
		arrow1.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle - arrowAngle));
		arrow1.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle - arrowAngle));
		
		Line arrow2 = new Line();
		arrow2.getStyleClass().add("line-pathLine");
		arrow2.setStartX(line.getEndX());
		arrow2.setStartY(line.getEndY());
		arrow2.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle + arrowAngle));
		arrow2.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle + arrowAngle));
		
		pane.getChildren().addAll(line, arrow1, arrow2);
	}
}
