package src.elements;

import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import java.util.Comparator;

public class FileHandler{
	private ListView<String> red;
	private  ListView<String> green;
	private List<Unit> listOfUnits;

	public FileHandler(ListView<String> red, ListView<String> green, List<Unit> listOfUnits){
		this.red = red;
        this.green = green;
        this.listOfUnits = listOfUnits;
	}

	public void handleUnits() {
        listOfUnits.sort(Comparator.comparingInt(unit -> {
                int rank = unit.getCommanderRank();
                return rank;
            }));
        red.getItems().clear();
        green.getItems().clear();

        for (Unit unit : listOfUnits) {
        	if(unit != null){
        		Color color = unit.getColor();
                Color corp = unit.getCorp();
                String divType = "";
                if(corp.equals(Color.BLACK))
                    divType = "Div.";
                if(corp.equals(Color.BLUE))
                    divType = "Mount.";
                if(corp.equals(Color.GRAY))
                    divType = "Brig.";
                if(corp.equals(Color.WHITE))
                    divType = "Sec.";
                String unitInfo;
                if(unit.getX() < 0 && unit.getY() < 0){
                    divType = "East Com.";
                    unitInfo = String.format("%s [%s]", formatName(unit.getCommander()), divType);
                }
                else if(divType.equals("Sec.")){
                    String unitNum = unit.getName();
                    unitInfo = String.format("%s [Sec. %s]", formatName(unit.getCommander()), unitNum);
                }
                else {
                    String unitNum = unit.getName();
            	    unitInfo = String.format("%s [%s %s]", formatName(unit.getCommander()), unitNum, divType);
                }
                if (color.equals(Color.RED)) {
                    red.getItems().add(unitInfo);
                } else if (color.equals(Color.GREEN)) {
                    green.getItems().add(unitInfo);
                }
        	}
        }
    }

    public Commander promote(String command){
        return processRankChange(command, true);
    }

    public Commander demote(String command){
        return processRankChange(command, false);
    }

    private Commander processRankChange(String command, boolean isPromoted){
        String[] parts = command.split(" ");
        String rank = parts[0];
        String name = command.replace(rank + " ", "");
        rank = getNewRank(rank, isPromoted);
        return new Commander(name, rank);
    }

    private String getNewRank(String current, boolean isPromoted){
        if(current.equals("Captain")){
            if(isPromoted)
                return "Major";
            else
                return "Captain";
        }
        if(current.equals("Major")){
            if(isPromoted)
                return "Lt.Colonel";
            else
                return "Captain";
        }
        if(current.equals("Lt.Colonel")){
            if(isPromoted)
                return "Colonel";
            else
                return "Major";
        }
        if(current.equals("Colonel")){
            if(isPromoted)
                return "Brigadier";
            else
                return "Lt.Colonel";
        }
        if(current.equals("Brigadier")){
            if(isPromoted)
                return "Maj.General";
            else
                return "Colonel";
        }
        if(current.equals("Maj.General")){
            if(isPromoted)
                return "Lt.General";
            else
                return "Brigadier";
        }
        if(current.equals("Lt.General")){
            if(isPromoted)
                return "General";
            else
                return "Maj.General";
        }
        if(current.equals("General")){
            if(isPromoted)
                return "F.Marshal";
            else
                return "Lt.General";
        }

        if(!isPromoted)
            return "General";
        return current;
    }

    private String formatName(String name){
        return String.format("%-26s", name);
    }

}