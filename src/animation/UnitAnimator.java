package src.animation;

import src.*;
import src.elements.*;

import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class UnitAnimator {
    private Canvas canvas;
    private List<Unit> currentUnits;
    private Map<String, Unit> targetUnitsMap;
    private Timeline timeline;
    private Image backgroundMap;
    private Runnable onComplete;
    private boolean redrawNeeded = false;

    public UnitAnimator(Canvas canvas, List<Unit> currentUnits, List<Unit> targetUnits, Image backgroundMap) {
        this.canvas = canvas;
        this.backgroundMap = backgroundMap;
        this.currentUnits = currentUnits != null ? new ArrayList<>(currentUnits) : new ArrayList<>();
        this.targetUnitsMap = createTargetUnitsMap(targetUnits);
        initAnimation();
    }

    public void changeBackgroundMap(Image backgroundMap){
        this.backgroundMap = backgroundMap;
    }

    private Map<String, Unit> createTargetUnitsMap(List<Unit> targetUnits) {
        Map<String, Unit> map = new HashMap<>();
        if (targetUnits != null) {
            for (Unit target : targetUnits) {
                String key = generateUnitKey(target);
                map.put(key, target);
            }
        }
        return map;
    }

    private String generateUnitKey(Unit unit) {
        String[] unitID = unit.toString().split(",");
        return unitID[2] + "-" + unitID[3] + "-" + unitID[5];
    }

    private void initAnimation() {
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> animate())); 
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startAnimation(Runnable onComplete) {
        this.onComplete = onComplete;
        timeline.play();
    }

    public void stopAnimation() {
        timeline.stop();
        if (onComplete != null) {
            onComplete.run();
        }
        redraw(targetUnitsMap.values());
    }

    private void animate() {
        boolean allUnitsAtTarget = true;

        for (Unit unit : currentUnits) {
            Unit target = findTarget(unit);
            if (target == null) {
                continue;
            }

            double currentX = unit.getX();
            double currentY = unit.getY();
            double targetX = target.getX();
            double targetY = target.getY();

            double newX = currentX + (targetX - currentX) * 0.2;  
            double newY = currentY + (targetY - currentY) * 0.2;

            if (Math.abs(newX - targetX) > 0.5 || Math.abs(newY - targetY) > 0.5) {
                unit.setX(newX);
                unit.setY(newY);
                allUnitsAtTarget = false;
                redrawNeeded = true;
            }
        }

        if (redrawNeeded) {
            redraw(currentUnits);
            redrawNeeded = false;
        }

        if (allUnitsAtTarget) {
            stopAnimation();
        }
    }

    private Unit findTarget(Unit unit) {
        return targetUnitsMap.get(generateUnitKey(unit));
    }

    private void redraw(Collection<Unit> units) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(this.backgroundMap, 0, 0);
        for (Unit unit : units) {
            gc.setFill(unit.getCorp());
            gc.fillRect(unit.getX() + 10, unit.getY() - 10, 6, 20);
            gc.setFill(unit.getColor());
            gc.fillRect(unit.getX() - 10, unit.getY() - 10, 20, 20);
            gc.setFill(Color.WHITE);
            gc.fillText(unit.getName(), unit.getX() - 7, unit.getY() + 5);
        }
    }
}
