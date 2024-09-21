package src;

import src.elements.*;

import javafx.fxml.*;
import javafx.scene.canvas.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class SimulationController {
    
    @FXML private Canvas canvas;
    @FXML private TextField divTemplate;
    @FXML private TextField divCommander;
    @FXML private Spinner<String> rankSpinner;
    @FXML private ListView<String> greenCommanders;
    @FXML private ListView<String> redCommanders;
   	@FXML private SplitMenuButton corpSelection;

   	private SaveLoad saverLoader = new SaveLoad();
    private List<Unit> listOfUnits = new ArrayList<>();
    private Unit selectedUnit = null;
    private double offsetX, offsetY;
    private String unitName = "0"; 
    private Commander unitCommander;
    private Image backgroundMap;
    private String backgroundMapName;
    private boolean deleteUnit = false;
    private boolean promoteUnit = false;
    private boolean demoteUnit = false;
    private FileHandler file;
    private Color corp = Color.GRAY;

    public void initialize() {
        this.backgroundMap = new Image("/maps/MainMap.png");
        this.backgroundMapName = "/maps/MainMap.png";
        unitCommander = new Commander("Unknown", "Captain");
        file = new FileHandler(redCommanders, greenCommanders, listOfUnits);
        canvas.getGraphicsContext2D().drawImage(this.backgroundMap, 0, 0);
        canvas.setOnMousePressed(this::onCanvasMousePressed);
        canvas.setOnMouseDragged(this::onCanvasMouseDragged);
        canvas.setOnMouseReleased(this::onCanvasMouseReleased);
        divTemplate.textProperty().addListener((observable, oldValue, newValue) -> {
            this.unitName = newValue.toString();
        });
        divCommander.textProperty().addListener((observable, oldValue, newValue) -> {
            this.unitCommander.setName(newValue.toString());
        });
        rankListener();
    }

    @FXML
    private void setInfantry(){
        this.corp = Color.GRAY;
        this.corpSelection.setText("Infantry");
    }

    @FXML
    private void setMount(){
        this.corp = Color.BLUE;
        this.corpSelection.setText("Mountain");
    }

    @FXML
    private void setDiv(){
        this.corp = Color.BLACK;
        this.corpSelection.setText("Division");
    }

    @FXML
    private void setSC(){
        this.corp = Color.WHITE;
        this.corpSelection.setText("Sec. Comm.");
    }

    @FXML
    private void setComm(){
        this.corp = Color.RED;
        this.corpSelection.setText("East. Comm.");
    }

    @FXML
    private void toggleDelete(){
        this.deleteUnit = !(this.deleteUnit);
    }

    @FXML
    private void togglePromote(){
        this.promoteUnit = !(this.promoteUnit);
        this.demoteUnit = false;
    }

    @FXML
    private void toggleDemote(){
        this.demoteUnit = !(this.demoteUnit);
        this.promoteUnit = false;
    }

    @FXML
    private void onCanvasMousePressed(MouseEvent event) {
        for (Unit unit : listOfUnits) {
            if (unit.contains(event.getX(), event.getY())) {
                selectedUnit = unit;
                if(deleteUnit){
                    break;
                }
                offsetX = event.getX() - unit.getX();
                offsetY = event.getY() - unit.getY();
                break;
            }
        }
        if(deleteUnit && selectedUnit != null){
            listOfUnits.remove(selectedUnit);
            selectedUnit = null;
            redraw();
            file.handleUnits();
        }
        if(promoteUnit && selectedUnit != null){
        	Commander newCommander = file.promote(selectedUnit.getCommander());
        	selectedUnit.setCommander(newCommander);
        	file.handleUnits();
        }
        if(demoteUnit && selectedUnit != null){
        	Commander newCommander = file.demote(selectedUnit.getCommander());
        	selectedUnit.setCommander(newCommander);
        	file.handleUnits();
        }
    }

    @FXML
    private void onCanvasMouseDragged(MouseEvent event) {
        if (selectedUnit != null) {
            double newX = event.getX() - offsetX;
            double newY = event.getY() - offsetY;
            selectedUnit.setX(newX);
            selectedUnit.setY(newY);
            redraw();
        }
    }

    @FXML
    private void onCanvasMouseReleased(MouseEvent event) {
        selectedUnit = null;
    }

    @FXML
    private void createRedUnit() {
        addUnit(100, 100, true);
    }

    @FXML
    private void createGreenUnit() {
        addUnit(100, 100, false);
    }

    @FXML
    private void loadMap() {
    	String mapPath = saverLoader.loadMap();
    	this.backgroundMapName = mapPath;
    	this.backgroundMap = new Image(mapPath);
    	redraw();
    }

    @FXML
    private void saveSimulation() {
    	saverLoader.saveSimulation(listOfUnits, backgroundMapName);
    }

    @FXML
    private void saveSimulationEdit() {
        saverLoader.saveEdit(listOfUnits, backgroundMapName);
    }

    @FXML
    private void loadSimulation() {
        try{
            List<Unit> newList = saverLoader.loadSimulation();
            if(newList != null && !newList.isEmpty()){
                this.listOfUnits = newList;
                this.backgroundMapName = saverLoader.getMapName();
                this.backgroundMap = new Image(backgroundMapName);
                redraw();
                file = new FileHandler(redCommanders, greenCommanders, listOfUnits);
                file.handleUnits();
            }
        } catch (NullPointerException e) {
            System.out.println("No file loaded");
        }
    }

    @FXML
    private void startAnimation() {
        try {
            int sceneWidth = 1300, sceneHeight = 710;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/simulationBeta.fxml"));
            Parent root = loader.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(root, sceneWidth, sceneHeight);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TimeLapse Window");
            primaryStage.setMinWidth(sceneWidth);
            primaryStage.setMaxWidth(sceneWidth);
            primaryStage.setMinHeight(sceneHeight);
            primaryStage.setMaxHeight(sceneHeight);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Couldn't Start the Animation Application");
        }
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(this.backgroundMap, 0, 0);
        for (Unit unit : listOfUnits) {
            gc.setFill(unit.getCorp());
            gc.fillRect(unit.getX() + 10, unit.getY() - 10, 6, 20);
            gc.setFill(unit.getColor());
            gc.fillRect(unit.getX() - 10, unit.getY() - 10, 20, 20);
            gc.setFill(Color.WHITE);
            gc.fillText(unit.getName(), unit.getX() - 7, unit.getY() + 5);
        }
    }

    private void addUnit(double x, double y, boolean isRed) {
        Commander temp = new Commander(unitCommander.getName(), unitCommander.getRankString());
        Color newColor = corp;
        if(newColor.equals(Color.RED)) {
            x = -60;
            y = -60;
        }
        if(isRed)
            listOfUnits.add(new Unit(x, y, Color.RED, this.unitName, temp, newColor));
        else
            listOfUnits.add(new Unit(x, y, Color.GREEN, this.unitName, temp, newColor));
        redraw();
        file.handleUnits();
    }

    private void rankListener(){
        String[] ranks = {"Captain", "Major", "Lt.Colonel", "Colonel", "Brigadier", "Maj.General", "Lt.General", "General", "F.Marshal"};
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(ranks));
        rankSpinner.setValueFactory(valueFactory);
        rankSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.unitCommander.setRank(newValue.toString());
        });
    }
}
