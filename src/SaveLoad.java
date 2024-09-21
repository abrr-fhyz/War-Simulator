package src;

import src.elements.*;


import javafx.stage.*;
import java.io.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SaveLoad{

	private String systemPath = System.getProperty("user.dir");
	private int saveNumber = 0;
    private String currentPath = "";
	private String mapName = "/maps/MainMap.png";
	private String infoTabValue = "No Data Detected";

	public SaveLoad(){
		checkDirectories();
	}

	public String loadMap() {
		String filePath = "/maps/MainMap.png";
    	FileChooser fileChooser  = new FileChooser();
        fileChooser.setTitle("Import New Map");
        fileChooser.setInitialDirectory(new File(systemPath + "/maps/"));
        fileChooser.setInitialFileName("1.png");
        File file = fileChooser.showOpenDialog(new Stage());
    	if (file != null) {
            filePath = file.getPath().replace(systemPath, "");
        }
        return filePath;
	}

	public void saveSimulation(List<Unit> listOfUnits, String mapName) {
		checkDirectories();
		String fileName = systemPath + "/saves/" + saveNumber + ".txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(mapName);
            writer.newLine();
            for (Unit unit : listOfUnits) {
                writer.write(unit.toString());
                writer.newLine();
            }
            
            System.out.println("Simulation saved successfully.");
            saveNumber += 1;
        } catch (IOException e) {
            System.out.println("ERROR saving simulation.");
        }

	}

    public void saveEdit(List<Unit> listOfUnits, String mapName) {  
        String fileName = this.currentPath;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(mapName);
            writer.newLine();
            for (Unit unit : listOfUnits) {
                writer.write(unit.toString());
                writer.newLine();
            }
            System.out.println("Simulation edit saved successfully.");
        } catch (IOException e) {
            System.out.println("ERROR saving simulation.");
        }

    }

	public String getMapName() {
		return this.mapName;
	}

	public List<Unit> loadSimulation() {
		List<Unit> listOfUnits = new ArrayList<>();
    	FileChooser fileChooser  = new FileChooser();
        fileChooser.setTitle("Load Simulation");
        fileChooser.setInitialDirectory(new File(systemPath + "/saves/"));
        fileChooser.setInitialFileName(saveNumber + ".txt");
        File file = fileChooser.showOpenDialog(new Stage());
    	if (file.exists()) {
            String filePath = file.getPath();
            listOfUnits = processLoading(filePath);
            this.currentPath = filePath;
        } 
        return listOfUnits;
	}

    public List<Unit> loadSpecificSimulation(int saveNumber) {
        List<Unit> listOfUnits = new ArrayList<>();
        String filePath = systemPath + "/saves/" + saveNumber + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            listOfUnits = processLoading(filePath);
        }
        return listOfUnits;
    }

    public boolean savesNotEmpty() {
        if(this.saveNumber != 0)
            return true;
        return false;
    }

    public String getInfoTab() {
    	return this.infoTabValue;
    }

	private List<Unit> processLoading(String filePath){
		List<Unit> listOfUnits = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean mapData = true;
            while ((line = reader.readLine()) != null) {
                if(mapData){
                	if(!line.contains(" ")){
                		this.mapName = line;
                		this.infoTabValue = "No Data Detected";
                	} else {
                		String[] splitIt = line.split(" ", 2);
                		this.mapName = splitIt[0];
                		this.infoTabValue = splitIt[1];
                	}
                	mapData = false;
                	continue;
                }
                String[] variables = line.split(",");
                Unit newUnit = generateUnit(variables);
                listOfUnits.add(newUnit);
           	}
        } catch (IOException e) {
           	System.out.println("ERROR loading file");
        }
        return listOfUnits;
	}

	private Unit generateUnit(String[] variables){
		double x = Double.parseDouble(variables[0]);
		double y = Double.parseDouble(variables[1]);
		Color color = Color.valueOf(variables[2]);
		String unitName = variables[3];

		String[] command = variables[4].split(" ");
		String name = variables[4].replace(command[0] + " ", "");
		Commander commander = new Commander(name, command[0]);

		Color corp = Color.valueOf(variables[5]);
		return new Unit(x, y, color, unitName, commander, corp);
	}

	private void checkDirectories() {
    	File directory = new File(systemPath + "/saves/");
    	File[] saveFiles = directory.listFiles((dir, name) -> name.matches("\\d+(\\.\\w+)?"));
    	if (saveFiles != null && saveFiles.length > 0) {
        	for(File file : saveFiles){
        		String fileName = file.getName().split("\\.")[0];
        		int fileNumber = Integer.parseInt(fileName);
        		if(fileNumber >= this.saveNumber)
        			this.saveNumber = fileNumber + 1;
        	}
    	} else {
        	this.saveNumber = 1;
    	}
	}

}