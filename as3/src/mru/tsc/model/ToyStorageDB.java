package mru.tsc.model;


import mru.tsc.view.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The ToyStorageDB class serves as the main database manager for all toy objects in the system.
 * 
 * It loads toy data from a text file into an ArrayList at startup, allows modifications such as adding or removing toys,
 * and saves the updated toy list back to the file when the program exits.
 * 
 * This class also provides methods to search toys by serial number, name, or type.
 * It plays a key role as the data layer in the Toy Store Management System.
 * 
 * 
 * @author Alexander Yoseph, Lorenzo Sta Maria, Haseeb Ullah
 * @version 1.0
 * @since 2025-11-09
 * @course CIS 2230 - Mount Royal University
 */
public class ToyStorageDB {
	Menu menu;
	ArrayList<Toy> toydb;
	private String FILE_PATH;

	/**
	 * Constructor for the ToyStorageDB class.
	 * Initializes the ArrayList and stores the file path for toy data.
	 * 
	 * @param FILE_PATH the file path to the toy data file
	 * @throws Exception if an error occurs while setting up the file
	 */
	public ToyStorageDB(String FILE_PATH)  {
		menu = new Menu();
		toydb = new ArrayList<>(); // Ensure toydb is initialized
		this.FILE_PATH = FILE_PATH;

	}
	/*
	 * Your project must have the following features and specifications: Your
	 * program should read all the toys into a **single** ArrayList on startup, and
	 * save the (potentially modified) list of toys back to the file on exit.
	 */

	/**
	 * Reads the toy data from the file and adds each toy object to the ArrayList.
	 * This method determines the toy type based on the serial number and calls
	 * the appropriate creation method (Figure, Animal, Puzzle, or BoardGame).
	 * 
	 * @throws Exception if the file cannot be found or read properly
	 */
	public void addData()  {
		File toyfile = new File(FILE_PATH);
		String currentLine;
		String[] splittedLine;
		Scanner fileReader;
		if (toyfile.exists()) { // Checking if the file exists
			
			try {
				fileReader = new Scanner(toyfile);// Create scanner for the file
				while (fileReader.hasNextLine()) { // Loop through the file lines
					currentLine = fileReader.nextLine();
					splittedLine = currentLine.split(";"); // Split the line at semi-colons

//					Toy currentToy; // Change type to Toy
					
					
					String toyType = getToyType(splittedLine[0]);
					
					if(toyType.equals("Figure")) { //takes in the serial number and checks for specific type of toy
						createFigure(splittedLine);
					}
					
					else if(toyType.equals("Animal") ){ //takes in the serial number and checks for specific type
						createAnimal(splittedLine);
					}
					
					else if(toyType.equals("Puzzle") ){ //takes in the serial number and checks for specific type
						createPuzzle(splittedLine);
					}
					
					else if(toyType.equals("BoardGame") ){ //takes in the serial number and checks for specific type
						createBoardGame(splittedLine);
					}
					
				}
				
				fileReader.close(); // Close the file reader
				
			} catch (FileNotFoundException e) {
				menu.errorMessage("unable to open file, please check the database path");
				
			} 
		}
	}
	
	/*
	 * create[Toy_TYPE] can be used for the file and user input
	 */

	/**
	 * Creates a Figure object using data read from the file or user input and adds it to the ArrayList.
	 * 
	 * @param data an array containing the Figure's details
	 */
	public void createFigure(String[] data) {
	    String serialNum = data[0];
	    String name = data[1];
	    String brand = data[2];
	    double price = Double.parseDouble(data[3]);
	    int count = Integer.parseInt(data[4]);
	    int age = Integer.parseInt(data[5]);
	    char classification = data[6].charAt(0);

	    toydb.add(new Figure(serialNum, name, brand, price, count, age,Character.toUpperCase(classification)));
	}

	/**
	 * Creates an Animal object using data read from the file or user input and adds it to the ArrayList.
	 * 
	 * @param data an array containing the Animal's details
	 */
	public void createAnimal(String[] data) {
	    String serialNum = data[0];
	    String name = data[1];
	    String brand = data[2];
	    double price = Double.parseDouble(data[3]);
	    int count = Integer.parseInt(data[4]);
	    int age = Integer.parseInt(data[5]);
	    String material = data[6];
	    char size = data[7].charAt(0);

	    toydb.add(new Animal(serialNum, name, brand, price, count, age, material,Character.toUpperCase(size)));
	}

	/**
	 * Creates a Puzzle object using data read from the file or user input and adds it to the ArrayList.
	 * 
	 * @param data an array containing the Puzzle's details
	 */
	public void createPuzzle(String[] data) {
	    String serialNum = data[0];
	    String name = data[1];
	    String brand = data[2];
	    double price = Double.parseDouble(data[3]);
	    int count = Integer.parseInt(data[4]);
	    int age = Integer.parseInt(data[5]);
	    char puzzleType = data[6].charAt(0);

	    toydb.add( new Puzzle(serialNum, name, brand, price, count, age,Character.toUpperCase(puzzleType) ));
	}

	/**
	 * Creates a BoardGame object using data read from the file or user input and adds it to the ArrayList.
	 * 
	 * @param data an array containing the BoardGame's details
	 */
	public void createBoardGame(String[] data) {
	    String serialNum = data[0];
	    String name = data[1];
	    String brand = data[2];
	    double price = Double.parseDouble(data[3]);
	    int count = Integer.parseInt(data[4]);
	    int age = Integer.parseInt(data[5]);
	    String[] minMaxPlayers = data[6].split("-");
	    int minPlayers = Integer.parseInt(minMaxPlayers[0]);
	    int maxPlayers = Integer.parseInt(minMaxPlayers[1]);
	    String designers = data[7];

	    toydb.add( new BoardGame(serialNum, name, brand, price, count, age, minPlayers, maxPlayers, designers));
	}

	/**
	 * This method returns the arralyList containing all the data from the file
	 * 
	 * @return the arraylist containing the toy data from the file
	 */
	public ArrayList<Toy> getToyDB() {
		
		return toydb;
	}

	/**
	 * This method compares the given serial number to all serial number in the database and returns a matching serial number
	 * 
	 * @param serialNumber the serial number given by the user
	 * @return the toy object
	 */
	public ArrayList<Toy> compareSNToAllToys(String serialNumber) {
		ArrayList<Toy> toySNList = new ArrayList<Toy>();
		String currentSN;
		
		for (Toy toy : toydb) { // read each object in list, then do a getSN to get the serial number
			currentSN = toy.getSerialNum();
			
			if (serialNumber.equals(currentSN)) toySNList.add(toy);  //compare SN to database SN, if true  add to a list to return
		}
		
		return toySNList;
		
	}

	/**
	 * This method compares the a given name to all names in the database and returns a list containing that all toys with that string sequence
	 * 
	 * @param name the name given by the user
	 * @return an arraylist that contains all toys with matching string sequence that was given by the user
	 */
	public ArrayList<Toy> compareNameToAllToys(String name) {
		ArrayList<Toy> toyNameList = new ArrayList<Toy>();
		
		String currentName;
		
		for (Toy toy : toydb) { // read each object in list, then do a getName the Name
			currentName = toy.getName().toLowerCase();
			
			if (currentName.contains(name.toLowerCase())) toyNameList.add(toy);  //compare Name to database Name, if true  add to a list to return
		}
		
		return toyNameList;
	}

	
	/**
	 * This method checks if a given type is the same as an object in the database and returns a list with all toys with the same type
	 * 
	 * @param type the object type name that the user provides
	 * @return a list of toys with the same object type
	 */
	public ArrayList<Toy> compareTypeToAllToys (String type) { 
		ArrayList<Toy> toyTypeList = new ArrayList<Toy>();
		
//		String currentType;
		
		for (Toy toy : toydb) { 									 		// read each object in list
			if (toy.typeOf().equals(type)) toyTypeList.add(toy); 	 // if an object is a specific type then add to a list, then return that list
		}
		
		return toyTypeList;
	}

	/**
	 * Removes a toy from the ArrayList database.
	 * 
	 * @param toy the toy object to be removed
	 * @throws Exception if an error occurs while removing the toy
	 */
	public void removeData(Toy toy) throws Exception {
		toydb.remove(toy);

	}
	
	//this class that uses the a built in method subtracts  

//	private void add(Toy toy) {
//
//		toydb.add(toy);
//
//	}
	
	/**
	 * Converts all toys in the ArrayList into a formatted string representation.
	 * Each toy’s stringToFormat method is called to generate its data line.
	 * 
	 * @return a formatted string of all toys in the database
	 */
	public String toString() {
		StringBuilder formattedData = new StringBuilder();

		// Iterate through each toy and add its formatted string representation to
		// StringBuilder
		for (Toy toy : toydb) {

			formattedData.append(toy.stringToFormat()).append("\n");
		}

		return formattedData.toString();
	}

//	public Object findToySerialnum(String serialNumber) {
//		return null;
//	}

	/**
	 * Determines the type of toy (Figure, Animal, Puzzle, or BoardGame)
	 * based on the first character of its serial number.
	 * 
	 * @param serialNum the toy’s serial number
	 * @return the type of toy as a string, or "error" if invalid
	 */
	public String getToyType(String serialNum) {
	    if (serialNum == null || serialNum.isEmpty()) {
	        return "error";
	    }

	    char firstChar = serialNum.charAt(0);

	    if (firstChar == '0' || firstChar == '1')
	        return "Figure";
	    else if (firstChar == '2' || firstChar == '3')
	        return "Animal";
	    else if (firstChar == '4' || firstChar == '5' || firstChar == '6')
	        return "Puzzle";
	    else if (firstChar == '7' || firstChar == '8' || firstChar == '9')
	        return "BoardGame";
	    else
	        return "error";
	}

	/**
	 * This method saves the data from the array list to the file path toy data base 
	 * by calling first calling a method that formats each line in the toy array list 
	 * and then printing the formated version into the file path
	 */
	public void saveData() {
		FileWriter filewriter;
		
		try {
			filewriter = new FileWriter(FILE_PATH);
			PrintWriter outputFile = new PrintWriter(filewriter);
			outputFile.print(this.toString());
			outputFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	
	
	

}
