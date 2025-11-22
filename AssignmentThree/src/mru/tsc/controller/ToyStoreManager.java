package mru.tsc.controller;

import java.util.ArrayList;
import java.util.Scanner;

import mru.tsc.exceptions.InvalidPlayersException;
import mru.tsc.exceptions.NegativePriceException;
import mru.tsc.model.*;
import mru.tsc.view.Menu;

public class ToyStoreManager {

	/**
	 * This field contains a list of the toy object from the data base
	 */
	ArrayList<Toy> toyList;
	
	Scanner input; //Temporary
	
	/**
	 * This field contains the ToyStorageDB class and can be used to access the  ToyStorageDb class
	 */
	ToyStorageDB toyStorageDB;
	
	/**
	 * This field contains the menu class can be used to access the Menu class
	 */
	Menu menu;
	
	/**
	 * This field is for instantly returning back to the main menu through skipping the sub menus
	 * 
	 * When this field is true it will return the user back to the main menu
	 */
	boolean backToMainMenu = false;
	
	/**
	 * This field is the File path containing the data of all toys
	 */
	final String FILE_PATH = "res/toys.txt" ;
	
	
	private NegativePriceException error;
	/**
	 * This constructor initializes the Menu class, assigns the arraylist containg the toys into toyList
	 * It also initializes the ToyStorageDb class and calls to load the data into an arraylist
	 * This also starts the program by calling the main menu.
	 */
	public ToyStoreManager() {
		this.input = new Scanner(System.in); //probably temporaryr
		error = new NegativePriceException();
		menu = new Menu();
		
		toyStorageDB =  new ToyStorageDB(FILE_PATH);
		toyStorageDB.addData();
		

		
		toyList = toyStorageDB.getToyDB();
		
		startMenu();
		
	}
	
	/**
	 * This method starts the program and calls for to prompt the main menu that loops until the user chooses to exit
	 * Additionally, it resets the backToMainMenu field to false to ensure that the user can go to the sub-menu
	 */
	private void startMenu() {
		
		while(true) {
			backToMainMenu = false; // re-initialize this to false just incase it was turned true when user wanted to go back to menu
			String userOption = menu.displayMainMenu(); // no validation yet
			
			switch (userOption) {
			case "1":
				searchInventory();
				
				break;
			case "2":
				addNewToy();
				break;
			case "3":
				removeToy();
				break;
			case "4":
				giftSuggestion();
				break;
			case "5":
				saveExit();
				return;
				

			default:
				break;
			}
		}
		
	}

	/**
	 * This method displays indication of data saving as well as calls to transfer all toy data in the array list into the original text file
	 */
	private void saveExit() {
		menu.displaySaveExit();
		toyStorageDB.saveData();
	}

	/**
	 * This method calls for to prompt the sub menu for finding a specific toy that loops until the user chooses to exit back to the main menu
	 * It also checks if the backToMainMenu is True which returns back to the main menu before user input
	 */
	private void searchInventory() {
		while(true) {
			if(backToMainMenu == true) return;
			String userOption = menu.displayFindToys();
			
			
			switch (userOption) {
				case "1":
					findToysUsingSerialNumber();
					break;
				case "2":
					findUsingName();
					break;
				case "3":
					findUsingType();
					break;
				case "4":
					return;
					
		
				default:
					menu.InvalidOption();
					break;
			}
		}
	}

	/**
	 * This method finds all toys with the users chosen toy type and displays and allows the user to purchase the available toys
	 */
	private void findUsingType() {
		String toyType =  menu.askTypeInput();
		if (toyType.equals("b")) toyType = "BoardGames";
		else if (toyType.equals("f")) toyType = "Figure";
		else if (toyType.equals("a")) toyType = "Animal";
		else if (toyType.equals("p")) toyType = "Puzzle";
		
		ArrayList<Toy> snList = toyStorageDB.compareTypeToAllToys(toyType); //returns an arraylist containg same type
		
		if(snList.isEmpty()) { //if its empty then let user know and stop this function by returning
			menu.toyNotFound();
			return;
		}
		
		displayToyList(snList);
		Toy selectedToy = selectValidation(snList);
		if(selectedToy == null) {
			backToMainMenu = true;
			return;
		}
		purchase(selectedToy);
	}

	/**
	 * This method finds all toys with the users chosen toy name and displays and allows the user to purchase the available toys
	 */
	private void findUsingName() {
		String toyName =  menu.askNameInput();
		ArrayList<Toy> snList = toyStorageDB.compareNameToAllToys(toyName); //returns an arraylist containg same name
		
		if(snList.isEmpty()) { //if its empty then let user know and stop this function by returning
			menu.toyNotFound();
			return;
		}
		displayToyList(snList);
		Toy selectedToy = selectValidation(snList);
		if(selectedToy == null) {
			backToMainMenu = true;
			return;
		}
		purchase(selectedToy);
	}

	/**
	 * This method finds all toys with the users chosen toy Serial number to displays and allows the user to purchase the available toys
	 */
	private void findToysUsingSerialNumber() {
		String userSerialNumber =  menu.askSerialNumber();
		ArrayList<Toy> snList = toyStorageDB.compareSNToAllToys(userSerialNumber); //returns an arraylist containg same sn
		
		if(snList.isEmpty()) { //if its empty then let user know and stop this function by returning
			menu.toyNotFound();
			return;
		}
		displayToyList(snList);
		Toy selectedToy = selectValidation(snList);
		if(selectedToy == null) {
			backToMainMenu = true;
			return;
		}
		purchase(selectedToy);
		
	}

	/**
	 * This method promts and adds a toy in the database
	 * 
	 * It first asks the user to enter a serial number
	 * that serial number is validated and matched with the matching toy type
	 * then promts the user to enter the specific toy data related to the type
	 * finally, it calls a method from the ToyStorageDB class that gives all the type information into that method that adds it to the arraylist containing all toys
	 */
	private void addNewToy() { //WIP 
		
		ArrayList<Toy> sameSNList;
		String userSerialNumber; 
		
		while(true) { // should add try catch 
			userSerialNumber =  menu.askSerialNumber(); //must validate sn
			sameSNList = toyStorageDB.compareSNToAllToys(userSerialNumber); //a list containing an item with the same serial number
			if (!sameSNList.isEmpty()) menu.snNotUnique(); // Calls menu class now for sn situation.
			else break;
		}
		
		String toyType = toyStorageDB.getToyType(userSerialNumber); //takes in the serial number and checks for specific type of toy
		
		/*
		 * create the toy type by using the toy data from user
		 */
		if(toyType.equals("Figure")) { 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method
			toyStorageDB.createFigure(askFigureData(userSerialNumber)); 
		}
		
		else if(toyType.equals("Animal") ){ 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method
			toyStorageDB.createAnimal(askAnimalData(userSerialNumber));
		}
		
		else if(toyType.equals("Puzzle") ){ 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method
			toyStorageDB.createPuzzle(askPuzzleData(userSerialNumber));
		}
		
		else if(toyType.equals("BoardGame") ){ 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method
			try {
				toyStorageDB.createBoardGame(askBoardGameData(userSerialNumber));
			} catch (InvalidPlayersException e) {
				// TODO Auto-generated catch block
				
				menu.errorMessage("\n"+ e.getMessage() + " Returning to main menu");
				return;
			}
		}
		
		menu.toyAddMessage();
		menu.pressEnter();
		
	}
	
	/**
	 * This method asks the user to input all related data to create a new boardgame object and stores it into a string  to return
	 * 
	 * @param serialNum the serial number that the user wants for the boardgame
	 * @return an array with the data to create a boardgame
	 * @throws InvalidPlayersException when minplayer is more than maxplayer
	 */
	private String[] askBoardGameData(String serialNum) throws InvalidPlayersException {

		String name = menu.askNameInput();
	    String brand = menu.askBrandInput();
	    String price = Double.toString(menu.askPriceInput()) ;
	    String count = Integer.toString(menu.askCountInput()) ;
	    String age = Integer.toString(menu.askAgeInput()) ;
	    
	    String minPlayers = Integer.toString(menu.askMinPlayersInput());
	    String maxPlayers = Integer.toString(menu.askMaxPlayersInput());
	    if( Integer.parseInt(minPlayers)>Integer.parseInt(maxPlayers)) {
	    		throw new InvalidPlayersException("Minimum number of players cannot be more than Maximumnumbers of players"); //throws an exception
	    }
	    
	    String designers = menu.askDesignerNamesInput();

	    return new String[]{serialNum, name, brand, price, count, age, minPlayers + "-" + maxPlayers, designers};
	}

	/**
	 * This method asks the user to input all related data to create a new puzzle object and stores it into a string  to return
	 * 
	 * @param serialNum  the serial number that the user wants for the new Puzzle
	 * @return  an array with the data to create a new puzzle toy
	 */
	private String[] askPuzzleData(String serialNum) {

		String name = menu.askNameInput();
	    String brand = menu.askBrandInput();
	    String price = Double.toString(menu.askPriceInput()) ;
	    String count = Integer.toString(menu.askCountInput()) ;
	    String age = Integer.toString(menu.askAgeInput()) ;
	    String puzzleType = Character.toString(menu.askPuzzleTypeInput());

	    return  new String[] {serialNum, name, brand, price, count, age, puzzleType};
	}

	/**
	 * This method asks the user to input all related data to create a new animal toy object and stores it into a string  to return
	 * 
	 * @param serialNum  the serial number that the user wants for the new Animal toy
	 * @return  an array with the data to create a new animal toy
	 */
	private String[] askAnimalData(String serialNum) {
	
		
		String name = menu.askNameInput();
	    String brand = menu.askBrandInput();
	    String price = Double.toString(menu.askPriceInput()) ;
	    String count = Integer.toString(menu.askCountInput()) ;
	    String age = Integer.toString(menu.askAgeInput()) ;
	    String material = menu.askMaterialInput();
	    String size = Character.toString(menu.askSizeInput()) ;

	    return new String[] {serialNum, name, brand, price, count, age, material, size};
	}

	/**
	 * This method asks the user to input all related data to create a new Figure object and stores it into a string  to return
	 * 
	 * @param serialNum  the serial number that the user wants for the new Figure toy
	 * @return  an array with the data to create a new Figure toy
	 */
	private String[] askFigureData(String serialNum) {
	
		
	    String name = menu.askNameInput();
	    String brand = menu.askBrandInput();
	    double price = menu.askPriceInput();
	    int count = menu.askCountInput();
	    int age = menu.askAgeInput();
	    char classification = menu.askClassificationInput();
	    
	    return new String[] {serialNum, name, brand, Double.toString(price), Integer.toString(count), Integer.toString(age), Character.toString(classification)};
	}

	/**
	 * this method promts and removes a toy from the database
	 * 
	 * It first asks for a serial number then validates if that serial number matcher with any serial number in the database.
	 * The index of the matching toy with the same serial number is then located in the database and removes that object in that index
	 */
	private void removeToy() {
		int indexInDataBase ;
		String userSerialNumber; 
		ArrayList<Toy> sameSNList;
		
		while (true){ 
			
			userSerialNumber = menu.askSerialNumber();
			if(userSerialNumber.isEmpty()) return;
	
			sameSNList = toyStorageDB.compareSNToAllToys(userSerialNumber); //a list containing an item with the same serial number // null is placeholder for userinput for SN
			if (sameSNList.isEmpty()) menu.toyNotFound(); // now calls menu class
			
			else break;
		}

		displayToy(sameSNList.get(0)); //gets the toy object and displays it to user
		boolean wantToRemove = menu.askToRemove(); //ask if the user wants to remove it
		if (wantToRemove) {
			indexInDataBase = toyList.indexOf(sameSNList.get(0)); //get the object in the sameSNList and get the index for that object within the data base arraylist of toys
			toyList.remove(indexInDataBase); //get the arraylist containing the toys and remove that toy from there
			menu.displayItemRemoved(); //now calls the appropriate menu class menu
		}

		menu.pressEnter();
		return;
	}
	
	/**
	 * validates the user input when selecting an item to purchase
	 * 
	 * The amount of items displayed is the maximum number that the user can enter
	 * and cannot enter a number that is bigger or less than the maximum number displayed
	 * 
	 * @param searchedList the list of toys that shows when searching up an item
	 * @param userSelectionInput
	 * @return the toy object that the user selected
	 */
	private Toy selectValidation (ArrayList<Toy> searchedList) {
		int userSelectionInput;
		int maxSearchedListSize = searchedList.size(); // check the size of the list, that will be the maximum number the user can input
		
		while (true) {
			userSelectionInput = menu.enterOptionNumber();
			// maxSearchedListSize + 1 accounts for the back to menu
			if (userSelectionInput > maxSearchedListSize + 1 || userSelectionInput <1) { //cannot be more than the list or less than 1
				menu.InvalidOption();
			}
			else break;
		}
		
		if(userSelectionInput == maxSearchedListSize + 1) return null; // if you select to go back then return null
		else return searchedList.get(userSelectionInput - 1); //if user choice 1 that would be index 0 in the list, so you must subtract 1
		
	}
	
	
	/**
	 * This method decrements the item that the user wants to purchase if there is at least one count of that object
	 * 
	 * @param toy the toy that the user chooses to buy
	 */
	private void purchase(Toy toy) {
		if (toy.getCount() > 0) { 
			toy.toyDecrement(); //if theres at least one toy available and the user wants to purchase then remove one count from the DB
			menu.purchaseSuccess(); // now calls the menu class
			menu.pressEnter();
		}
		else menu.outOfStock();// now calls the menu class
	}
	
	/**
	 * This method displays the list of toys that matches the users search by calling the menu class to print
	 * 
	 * @param searchedList the list containing the toys in the database as an ArrayList Class
	 */
	private void displayToyList(ArrayList<Toy> searchedList) {
		
		int count = 0;
		for (Toy toy : searchedList) {
			count ++;
			//There should be a menu call here that takes in count
			
			menu.displaytoylist(count, toy.toString());

		}
		menu.displaytoylist(count + 1, "Back to Main Menu");
		
	}

	/**
	 * This method displays a single of toys that matches the users search by calling the menu class to print
	 * 
	 * @param singleToy A single toy that will be displayed as a Toy class
	 */
	private void displayToy(Toy singleToy) {
		menu.displaySingleToy(singleToy);// now calls MENU CLASS
		
	}
	
	/**
	 * This method promts the user to enter details to filter through the toy data base
	 * 
	 * It asks for age of the person recieving the gift, type of toy, and maximum and minimum price range of the toy
	 * at least one of the question must be answered for the filter to work
	 * A list of toys that was filtered is then displayed for the user to purchase
	 */
	private void giftSuggestion() { //WIPWIPWIPWIPWIP
		String ageString = null; //originally was working needed null which is why we used the class name instead, but changed later, however it does not make a difference
		Integer age = null;
		String type = null;
		String minPriceString = null;
		Double minPrice = null;
		String maxPriceString = null;
		Double maxPrice = null;
		
		ArrayList<Toy> filteredList;
		
		while(true) {
			
			ageString = menu.askGiftAge();
			age = ageValidation(ageString);
			
			type =  menu.askTypeInputGift();
			if(!type.isEmpty()) {
				if (type.equals("b")) type = "BoardGames";
				else if (type.equals("f")) type = "Figure";
				else if (type.equals("a")) type = "Animal";
				else if (type.equals("p")) type = "Puzzle";
			}
			
			while(true) { //keep runing until minprice is less than maxprice or left blank
				menu.pricerange();
				minPriceString = menu.askMinPrice();
				minPrice = minPriceValidation(minPriceString);
				
				
				maxPriceString = menu.askMaxPrice();
				maxPrice = maxPriceValidation(maxPriceString);
			
				if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
	                menu.displayMinMoreThanMaxError();
	            } else break;
			}
			
			if (ageString.isEmpty() && type.isEmpty() && minPriceString.isEmpty() && maxPriceString.isEmpty()) { //do this in the menu class
				menu.atLeastOneFieldMessage(); // now calls the menu class
			}
			else break;
		}
	
		filteredList = toyStorageDB.compareTypeToAllToys(type);
		if(type.isEmpty()) filteredList = toyList; //if the user chooses not to enter anything for the toy type, make sure filteredlist has all the toys
		
		filteredList = filterAge(filteredList, age);
		filteredList = filterPrice(filteredList, minPrice, maxPrice);
		
		menu.displayGiftSuggestionResult(); // Now calls the menu class
		if(filteredList.isEmpty()) { //if its empty then let user know and stop this function by returning
			menu.toyNotFound();
			return;
		}
		
		displayToyList(filteredList);
		Toy selectedToy = selectValidation(filteredList);
		if(selectedToy == null) {
			backToMainMenu = true;
			return;
		}
		purchase(selectedToy);
	
	}
	
	/**
	 * This method filters the toys by the price range that the user chooses
	 * 
	 * @param createdToyList createdToyList this is a list containing toys that have reacently been filtered, to further filter it
	 * @param minPrice the lowest price the person is willing to buy (should not be less than 0)
	 * @param maxPrice the highest price the person is willing to buy (should not be less than minPrice)
	 * @return a new list containing the filtered toys by price range
	 */
	private ArrayList<Toy> filterPrice(ArrayList<Toy> createdToyList, Double minPrice, Double maxPrice) {
		ArrayList<Toy> newToyList = new ArrayList<Toy>();

		for (Toy toy : createdToyList) {
			if(toy.getPrice()>= minPrice && toy.getPrice() <= maxPrice) newToyList.add(toy);
		}
		return newToyList;
	}
	
	/**
	 * This method filters the toys by the minimum age requirement
	 * 
	 * if the age of a person given equals to the requirement of toys, then it will add to the newToyList
	 * If the age of a person is a max value integer, that means there is no limitation and all toys in a given list will be added to the newToyList
	 * 
	 * @param createdToyList this is a list containing toys that have reacently been filtered, to further filter it
	 * @param age the age of the person receiving the gift
	 * @return a new list containing the filtered toys that is allowed to be played by the given age of a person
	 */
	private ArrayList<Toy> filterAge(ArrayList<Toy> createdToyList, Integer age) {
		ArrayList<Toy> newToyList = new ArrayList<Toy>();
		
		for (Toy toy : createdToyList) {
			if(age >= toy.getAgeAllowed() && age == Integer.MAX_VALUE) newToyList.add(toy) ; //if the age is more than the allowed age to play, add to the list
			else if (age == toy.getAgeAllowed()) newToyList.add(toy);
		}
		return newToyList;
	}
	
	/**
	 * This method handles the exception in case the argument given for the age of the person is an invalid Integer
	 * 
	 * if the argument given is less than or equals to 0, or it cannot be parsed, then it will return the maximum value an integer can be
	 * The max value is the default, meaning no filter will happen (no limitation).
	 * Else set the age to the argument given
	 * 
	 * @param ageString the user input for the age of the age of a person
	 * @return the argument or if invalid a max value of integer (default)
	 */
	private Integer ageValidation(String ageString){
		Integer age = Integer.MAX_VALUE;
		
		if(!ageString.equals("")) { //if its not empty then parse the to Integer, if it is empty return null
			try {
			  	age = Integer.parseInt(ageString); //return this value unless invalid return max to get all toys
			  	if (age <= 0) {
			  		menu.invalidMessage("setting age field as default"); //empty means all there is not age limit
			  		age = Integer.MAX_VALUE;
			  	}

			} catch (Exception e) {
				menu.invalidMessage("setting age field as empty");
			}
		}

		return age;
	}
	
	/**
	 * This method handles the exception in case the argument given for minimum price of a toy is not a valid Double
	 * 
	 * If the argument given is a valid double that is a 0 or a positive integer, it will return that number.
	 * It will prompt a notice or an error and always returns a 0.0 in this case.
	 * 
	 * @param minPriceString the user input for the minimum price to filter. Can be left blank
	 * @return the minimum value for filtering price that the user chose or 0 if invalid input by user 
	 */
	private Double minPriceValidation(String minPriceString){
		Double minPrice = 0.0;
		
		if(!minPriceString.equals("")) { 
			try {
				minPrice = Double.parseDouble(minPriceString);
				menu.checkNegativePrice(minPrice);
			} 
			catch (NegativePriceException e) {
				menu.invalidMessage(" Cannot be negative number, seeting  field to default");
				minPrice = 0.0;
				//just set to zero if its a negative doesnt make a difference
			}
			catch (Exception e) {
				menu.invalidMessage("invalid minimum price, setting field as empty");

			}
		}
		
		return minPrice;
	}
	
	/**
	 * This method handles the exception in case the argument given for a maximum price of a toy is not a valid Double
	 * 
	 * If the argument given is a valid double that is a 0 or a positive integer, it will return that number
	 * else it will prompt a notice or an error and always returns a max value (no limit).
	 * 
	 * @param maxPriceString the user input for the maximum price to filter. Can be left blank
	 * @return the maximum value a double can be if invalid or a value at or above 0 that the user chose if valid
	 */
	private Double maxPriceValidation(String maxPriceString){
		Double maxPrice = Double.MAX_VALUE;
		
		if(!maxPriceString.equals("")){
			try {
				maxPrice = Double.parseDouble(maxPriceString);
				menu.checkNegativePrice(maxPrice);
			}
			catch (NegativePriceException e) {
				menu.invalidMessage(" Cannot be negative number, seeting  field to default");
				
			}
			catch (Exception e) {
				menu.invalidMessage(" setting field as empty");
			}	
		}
		
		return maxPrice;
	}
	


	
}
