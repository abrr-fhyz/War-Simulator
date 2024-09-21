package src.animation;

import src.*;
import src.elements.*;

import java.util.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Animation {
    @FXML private Canvas canvas;
    @FXML private Canvas legend;
    @FXML private TextArea infoTab;
    @FXML private ListView<String> greenCommanders;
    @FXML private ListView<String> redCommanders;

    private List<Unit> firstListOfUnits = new ArrayList<>();
    private List<Unit> secondListOfUnits = new ArrayList<>();
    private FileHandler fileHandler;
    private SaveLoad saveLoad = new SaveLoad();
    private int currentSave = 1;
    private Image backgroundMap;
    private String backgroundMapName;

    @FXML
    public void startStuff() {
        if (saveLoad.savesNotEmpty()) {
            loadLegend();
            startSimulation();
        } else {
            System.out.println("No Save Files to Simulate");
        }
    }

    @FXML
    public void restart() {
        this.currentSave = 1;
        if (saveLoad.savesNotEmpty()) {
            startSimulation();
        } else {
            System.out.println("No Save Files to Simulate");
        }
    }

    private void startSimulation() {
        new Thread(() -> {
            try {
                runSimulation();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void runSimulation() throws InterruptedException {
        firstListOfUnits = saveLoad.loadSpecificSimulation(currentSave++);
        fileHandler = new FileHandler(redCommanders, greenCommanders, firstListOfUnits);
        backgroundMapName = saveLoad.getMapName();
        backgroundMap = new Image(backgroundMapName);
        Platform.runLater(() -> {
            redraw(firstListOfUnits);
            fileHandler.handleUnits();
        });

        this.infoTab.setText(saveLoad.getInfoTab());

        Thread.sleep(1000);

        this.backgroundMapName = saveLoad.getMapName();
        this.backgroundMap = new Image(this.backgroundMapName);
        secondListOfUnits = saveLoad.loadSpecificSimulation(currentSave++);
        while (!firstListOfUnits.isEmpty() && !secondListOfUnits.isEmpty()) {
            List<Unit> tempListOfUnits = new ArrayList<>(firstListOfUnits);
            List<Unit> tempSecondListOfUnits = new ArrayList<>(secondListOfUnits);

            this.infoTab.setText(saveLoad.getInfoTab());

            fileHandler = new FileHandler(redCommanders, greenCommanders, secondListOfUnits);
            Platform.runLater(() -> {
                UnitAnimator animator = new UnitAnimator(canvas, tempListOfUnits, tempSecondListOfUnits, backgroundMap);
                animator.startAnimation(() -> {
                    fileHandler.handleUnits();
                    animator.changeBackgroundMap(this.backgroundMap);
                    this.backgroundMapName = saveLoad.getMapName();
                    this.backgroundMap = new Image(this.backgroundMapName);
                });
            });

            firstListOfUnits = secondListOfUnits;
            secondListOfUnits = saveLoad.loadSpecificSimulation(currentSave++);

            Thread.sleep(1000);
        }
    }

    private void redraw(List<Unit> listOfUnits) {
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

    private void loadLegend() {
        Image dataImg = new Image("/assets/legend.png");
        GraphicsContext gc = legend.getGraphicsContext2D();
        double canvasWidth = legend.getWidth();
        double canvasHeight = legend.getHeight();
        gc.drawImage(dataImg, 0, 0, canvasWidth, canvasHeight);
    }
}
