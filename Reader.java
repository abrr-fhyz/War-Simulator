import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Reader{
	private static String location = System.getProperty("user.dir") + "/saves/";
	private static ArrayList<String> pakistan = new ArrayList<>();
	private static ArrayList<String> bangladesh = new ArrayList<>();
	private static ArrayList<String> india = new ArrayList<>();

	private static void clear(){
		pakistan.clear();
		bangladesh.clear();
		india.clear();
	}

	private static String processCorp(String corp, String country, String name, String unitName){
		if(corp.equals("0xff0000ff")){
			if(country.equals("0xff0000ff"))
				return "GOC, Eastern Command";
			if(name.equals("JS Aurora"))
				return "GOC-in-Charge";
			if(name.equals("MAG Osmani"))
				return "Commander-in-Chief";
			return "GOC, Corp Command";
		}
		if(corp.equals("0x808080ff"))
			return unitName + " Infantry Brigade";
		if(corp.equals("0xffffffff"))
			return "Sector " + unitName + " Command";
		if(corp.equals("0x0000ffff"))
			return unitName + " Mountain Brigade";
		if(corp.equals("0x000000ff"))
			return "Divison " + unitName + " Commander";
		return "Unknown";
	}

	private static String fullRank(String rank){
		if(rank.equals("Lt.Colonel"))
			return "Lieutenant Colonel";
		if(rank.equals("Brigadier"))
			return "Brigadier General";
		if(rank.equals("Maj.General"))
			return "Major General";
		if(rank.equals("Lt.General"))
			return "Lieutenant General";
		return rank;
	}

	private static void processLine(String country, String unitName, String commander, String corp){
		String[] com = commander.split(" ", 2);
		commander = fullRank(com[0]) + " " + com[1];
		//pakistan
		if(country.equals("0xff0000ff")){
			corp = processCorp(corp, country, com[1], unitName);
			String dataLine = String.format("%-" + 45 + "s\t\t%-" + 25 + "s", commander, corp);
			pakistan.add(dataLine);
			return;
		}
		//bangladesh
		if(corp.equals("0xffffffff") || unitName.equals(" Z") || unitName.equals(" S") || unitName.equals(" K") || com[1].equals("MAG Osmani")){
			corp = processCorp(corp, country, com[1], unitName);
			String dataLine = String.format("%-" + 45 + "s\t\t%-" + 25 + "s", commander, corp);
			bangladesh.add(dataLine);
			return;
		}
		//india
		corp = processCorp(corp, country, com[1], unitName);
		String dataLine = String.format("%-" + 45 + "s\t\t%-" + 25 + "s", commander, corp);
		india.add(dataLine);
		return;
	}

	private static void processFile(int save){
		String path = location + save + ".txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean date = true;
            while ((line = reader.readLine()) != null) {
            	if(date){
            		String[] splitIt = line.split(" ", 2);
            		System.out.println("DATE:\t\t" + splitIt[1]);
            		date = false;
            		continue;
            	}
            	String[] variables = line.split(",");
            	//System.out.println(variables[2]);
            	processLine(variables[2], variables[3], variables[4], variables[5]);
            }
        } catch (IOException e) {
           	System.out.println("ERROR loading file");
        }
	}

	private static void print(){
		System.out.println("\n----------BANGLADESHI ORDER OF BATTLE----------");
		for(String s : bangladesh)
			System.out.println(s);
		if(bangladesh.isEmpty())
			System.out.println("-----------N/A-----------");
		System.out.println("\n----------PAKISTANI ORDER OF BATTLE----------");
		for(String s : pakistan)
			System.out.println(s);
		if(pakistan.isEmpty())
			System.out.println("-----------N/A-----------");
		System.out.println("\n----------INDIAN ORDER OF BATTLE----------");
		for(String s : india)
			System.out.println(s);
		if(india.isEmpty())
			System.out.println("-----------N/A-----------");
	}

	public static void main(String[] args){
		Reader rd = new Reader();
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome To The Save File Simplifier\nChoose a save file, and all relevant information will be displayed.");
		while(true){
			System.out.println("Enter Save File Number: ");
			int saveNumber = sc.nextInt();
			processFile(saveNumber);
			print();
			System.out.println("\n\n\n");
			clear();
		}
	}
}