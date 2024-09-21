package src.elements;

public class Commander{
    private String commanderName;
    private String commanderRank;

    public Commander(String name, String rank){
        this.commanderName = name;
        this.commanderRank = rank;
    }

    public String getData(){
        return commanderRank + " " + commanderName;
    }

    public String getName(){
        return this.commanderName;
    }

    public String getRankString(){
        return this.commanderRank;
    }

    public void setName(String name){
        this.commanderName = name;
    }

    public void setRank(String rank){
        this.commanderRank = rank;
    }

    public int getRank(){
        if(commanderRank.equals("Captain")){
            return 8;
        }
        if(commanderRank.equals("Major")){
            return 7;
        }
        if(commanderRank.equals("Lt.Colonel")){
            return 6;
        }
        if(commanderRank.equals("Colonel")){
            return 5;
        }
        if(commanderRank.equals("Brigadier")){
            return 4;
        }
        if(commanderRank.equals("Maj.General")){
            return 3;
        }
        if(commanderRank.equals("Lt.General")){
            return 2;
        }
        if(commanderRank.equals("General")){
            return 1;
        }
        if(commanderRank.equals("F.Marshal")){
            return 0;
        }
        return -1;
    }
}