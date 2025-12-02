package mru.tsc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;


import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.io.IOException; 
import java.util.logging.*;
import java.util.ArrayList;
import java.util.Scanner;

import mru.tsc.exceptions.InvalidPlayersException;
import mru.tsc.exceptions.NegativePriceException;
import mru.tsc.model.*;
import mru.tsc.view.Menu;

public class toyJavaFxController {
	String toyType;
	private static final Logger logger = Logger.getLogger(toyJavaFxController.class.getName());
	ArrayList<Toy> filteredList;
	
	ArrayList<Toy> sameSNList;
	
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
	
	private ToggleGroup homeSearchGroup;
	private NegativePriceException error;
	/**
	 * This constructor initializes the Menu class, assigns the arraylist containg the toys into toyList
	 * It also initializes the ToyStorageDb class and calls to load the data into an arraylist
	 * This also starts the program by calling the main menu.
	 */
	public toyJavaFxController() {
		this.input = new Scanner(System.in); //probably temporaryr
		error = new NegativePriceException();
		menu = new Menu();
		
		toyStorageDB =  new ToyStorageDB(FILE_PATH);
		toyStorageDB.addData();
		
		toyList = toyStorageDB.getToyDB();
		
		
		

		    // Create logger
		     

		        setupLogger();
		    
//		startMenu();
		
	}
	
	/**
	 * This method sets up the logger by creating and opening the log files and setting the level
	 */
	private void setupLogger() {
        try {
            FileHandler handler = new FileHandler("res/toy_store_log.txt", true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
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
	@FXML
	private void addNewToy(ActionEvent event) { //WIP 
   	 lblNoticeAdd1.setText("");

		ArrayList<Toy> sameSNList;
		String userSerialNumber; 
		 double priceToy; 
			String count = countTF.getText();
			String price = priceTF.getText();
			String age = ageTF.getText();
			
			String name = nameTF.getText().trim();
		    String brand = brandTF.getText().trim();
		
		String sn = serialTF.getText();
		
		if( sn.isEmpty() || name.isEmpty() || brand.isEmpty() || price.isEmpty()  || count.isEmpty() || age.isEmpty()) {
        	lblNoticeAdd1.setText("ERROR: All fields MUST be filled out");
        	return;
	 }
	    try {
	    		priceToy = Double.parseDouble(priceTF.getText().trim()) ;
            
            menu.checkNegativePrice(priceToy);
             price = Double.toString(priceToy);

        } catch (NegativePriceException e) {
        		lblNoticeAdd1.setText("Invalid price input: price cannot be negative number"); 
        		return ;

        }
        catch (Exception e ) {
        	lblNoticeAdd1.setText("Invalid Price input: Input must be a number.");
        	return ;
        }
	   
	    
	    
	    try {
            int countToy = Integer.parseInt(countTF.getText().trim());
            
            if (!(countToy >= 0)) {
            	lblNoticeAdd1.setText("Invalid Count input: Count cannot be negative number");
            	return ;
            } else  count = Integer.toString(countToy) ;
        } catch (Exception e) {
        	lblNoticeAdd1.setText("Invalid Count input: Input must be a number");
        		return ;
        }
    
	    
	    
	    try {
            int ageToy = Integer.parseInt(ageTF.getText().trim()) ;
            
            if (!(ageToy >= 0)) {
            	lblNoticeAdd1.setText("Invalid age input: Age cannot be a negative number");  
            	return;
            	
            }
            else age = Integer.toString(ageToy);
        } catch (Exception e) {
        	lblNoticeAdd1.setText("Invalid  age input: Input must be a number");
        	return;
           

        }
		 
		
	    try {
	         sn = serialTF.getText().trim();
	        
	       
	        if (sn.length() != 10) {
	            lblNoticeAdd1.setText("Invalid serial number: must be exactly 10 digits");
	            return;
	        }
	        
	       
	        Long snLong = Long.parseLong(sn);

	        
	    } catch (NumberFormatException e) {
	        lblNoticeAdd1.setText("Invalid serial number Input: must contain only digits (0-9)");
	        return;
	    } catch (Exception e) {
	        lblNoticeAdd1.setText("Invalid serial number input");
	        return;
	    }
	
		
			
			sameSNList = toyStorageDB.compareSNToAllToys(sn); //a list containing an item with the same serial number
			if (!sameSNList.isEmpty()) {
				lblNoticeAdd1.setText("Serial Number must be unique!!"); // Calls menu class now for sn situation.
				return;
			}
			
		 toyType = toyStorageDB.getToyType(sn); //takes in the serial number and checks for specific type of toy
		 if (!(categoryCB1.getValue().toLowerCase().equals(toyType.toLowerCase()))){
				
			switch(categoryCB1.getValue().toLowerCase()) {
    	        case "figure":
    	        	lblNoticeAdd1.setText("Serial Number must start with: 0 or 1 "); 
    	            break;
    	        case "animal":
    	        	lblNoticeAdd1.setText("Serial Number must start with: 2 or 3 "); 
    	            break;
    	        case "puzzle":
    	        	lblNoticeAdd1.setText("Serial Number must start with: 4, 5, or 6  "); 
    	            break;
    	        case "boardgame":
    	        	lblNoticeAdd1.setText("Serial Number must start with: 7, 8, or 9 "); 
    	            break;
    	    }
				return;
		 } 
	
		 


		 

			 
		 
		/*
		 * create the toy type by using the toy data from user
		 */
		if(toyType.equals("Figure")) { 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method
			
			toyStorageDB.createFigure(askFigureData(sn, name, brand, price, count, age)); 
		}
		
		else if(toyType.equals("Animal") ){ 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method
		    String material = animalMaterialTF.getText().trim();
		    if(material.isEmpty()) {
		    		lblNoticeAdd1.setText("Invalid Animal input: This MAterial field is empty");
		    		return;
		    }

			toyStorageDB.createAnimal(askAnimalData(sn, name, brand, price, count, age, material));
		}
		
		else if(toyType.equals("Puzzle") ){ 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method

			toyStorageDB.createPuzzle(askPuzzleData(sn, name, brand, price, count, age));
		}
		
		else if(toyType.equals("BoardGame") ){ 
			// prompts to ask for toy data to feed into a specific create[TOY TYPE] method
			
		    
		    String minPlayers;
	        try {
	            int minNum =Integer.parseInt(bgMinTF.getText().trim()) ;
	           
	            if (minNum >= 1) {
	            	minPlayers = Integer.toString(minNum);
	            } else {
	            	lblNoticeAdd1.setText("Invalid Board Game input: Must have at least 1 player");
	            	return;
	                
	            }
	        } catch (Exception e) {
	        	lblNoticeAdd1.setText("Invalid Board Game input: minimum player field must be a number");
            	return;

	        }
	        
		    String maxPlayers;
		    try {
	            int maxNum = Integer.parseInt(bgMaxTF.getText().trim()) ;
	          
	            if (maxNum >= 1) {
	            	maxPlayers = Integer.toString(maxNum);
	            } else {
	            	lblNoticeAdd1.setText("Invalid Board Game input: maximum players must be more than 1");
	               return;
	            }
	        } catch (Exception e) {
	        	lblNoticeAdd1.setText("Invalid Board Game input: Input must be a number");
	            return;

	        }
		    
		    String designers = bgDesignersTF.getText().trim();
		    if(designers.isEmpty()) {
		    		lblNoticeAdd1.setText("Invalid BoardGame input: The Designer field is empty");
		    		return;
		    }
			
			try {
				toyStorageDB.createBoardGame(askBoardGameData(sn, name, brand, price, count, age, minPlayers, maxPlayers, designers));
			} catch (InvalidPlayersException e) {
				
				lblNoticeAdd1.setText("Minimum number of players cannot be more than Maximumnumbers of players");
				return;
			}
		}
		
	 	toyStorageDB.saveData();
		lblNoticeAdd1.setText( "Toy successfully ADDED");

		
	}
	
	/**
	 * This method asks the user to input all related data to create a new boardgame object and stores it into a string  to return
	 * 
	 * @param serialNum the serial number that the user wants for the boardgame
	 * @return an array with the data to create a boardgame
	 * @throws InvalidPlayersException when minplayer is more than maxplayer
	 */
	private String[] askBoardGameData(String serialNum,String name,String brand,String price,String count,String age, String minPlayers, String maxPlayers, String designers) throws InvalidPlayersException {

	    if( Integer.parseInt(minPlayers)>Integer.parseInt(maxPlayers)) {
	    		throw new InvalidPlayersException("Minimum number of players cannot be more than Maximumnumbers of players"); //throws an exception
	    }
		logger.info(serialNum + ";" + name + ";" + brand + ";" + price + ";" + count + ";" + age +";" + minPlayers + ";" + maxPlayers + ";" + designers);
	    return new String[]{serialNum, name, brand, price, count, age, minPlayers + "-" + maxPlayers, designers};
	}

	/**
	 * This method asks the user to input all related data to create a new puzzle object and stores it into a string  to return
	 * 
	 * @param serialNum  the serial number that the user wants for the new Puzzle
	 * @return  an array with the data to create a new puzzle toy
	 */
	private String[] askPuzzleData(String serialNum,String name,String brand,String price,String count,String age) {

//		String name = menu.askNameInput();
//	    String brand = menu.askBrandInput();
//	    String price = Double.toString(menu.askPriceInput()) ;
//	    String count = Integer.toString(menu.askCountInput()) ;
//	    String age = Integer.toString(menu.askAgeInput()) ;
	    
	    
		String puzzleType = Character.toString(cbTypePuzzle.getValue().toLowerCase().charAt(0));
   	 if(puzzleType.equals("m") || puzzleType.equals("c") || puzzleType.equals("l") || puzzleType.equals("t") || puzzleType.equals("r") ) {

   	 }
 	logger.info(serialNum + ";" + name + ";" + brand + ";" + price + ";" + count + ";" + age +";" + puzzleType);
	    return  new String[] {serialNum, name, brand, price, count, age, puzzleType};
	}

	/**
	 * This method asks the user to input all related data to create a new animal toy object and stores it into a string  to return
	 * 
	 * @param serialNum  the serial number that the user wants for the new Animal toy
	 * @return  an array with the data to create a new animal toy
	 */
	private String[] askAnimalData(String serialNum,String name,String brand,String price,String count,String age, String material) {
	
	
	    
	   
	    
		String size =Character.toString(cbSizeAnimal.getValue().toLowerCase().charAt(0)) ;
    	if (size.equals("s")|| size.equals("m") || size.equals("l")) {
    		
    	}
 
    	logger.info(serialNum + ";" + name + ";" + brand + ";" + price + ";" + count + ";" + age +";" + material + ";" + size);
	    return new String[] {serialNum, name, brand, price, count, age, material, size};
	}

	/**
	 * This method asks the user to input all related data to create a new Figure object and stores it into a string  to return
	 * 
	 * @param serialNum  the serial number that the user wants for the new Figure toy
	 * @return  an array with the data to create a new Figure toy
	 */
	private String[] askFigureData(String serialNum,String name,String brand,String price,String count,String age) {
	
		

	    
	    
	    char classification = cbClassFig.getValue().toLowerCase().charAt(0);
    	if(classification == 'a' || classification == 'd' || classification == 'h' ) {
    		
    	}

    	 logger.info(serialNum + ";" + name + ";" + brand + ";" + price + ";" + count + ";" + age + ";" + classification);
	    return new String[] {serialNum, name, brand, price, count, age, Character.toString(classification)};
	}

	/**
	 * this method promts and removes a toy from the database
	 * 
	 * It first asks for a serial number then validates if that serial number matcher with any serial number in the database.
	 * The index of the matching toy with the same serial number is then located in the database and removes that object in that index
	 */
	private void removeToy( String serialNumber) {
		int indexInDataBase;
		if (sameSNList != null )sameSNList.clear();
        try {
            
            if (serialNumber.isEmpty()) {
                lblErrorRemove.setText("Please enter a serial number.");
                return;
            }

            Long snLong = Long.parseLong(serialNumber);
    
            if(!(Long.toString(snLong).length() == 10)) {
            	lblErrorRemove.setText("Invalid input: must have 10 digits");
			} else lblErrorRemove.setText( "Error: Item Not in DataBase");


        } catch (Exception e) {
        	lblErrorRemove.setText( "Invalid input: Input must be a number or must be 10 long");

        }

	    
	    String userSerialNumber = serialNumber.trim();
	    sameSNList = toyStorageDB.compareSNToAllToys(userSerialNumber);
	    

	    
	    // Display the toy 
	    if (sameSNList.isEmpty()) {
	    	toyListView.getItems().clear();
	    	return;
	    }
		lblErrorRemove.setText( "");
	    displayToy(sameSNList.get(0));
		
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
			
		}
		else menu.outOfStock();// now calls the menu class
	}
	
	/**
	 * This method displays the list of toys that matches the users search by calling the menu class to print
	 * 
	 * @param searchedList the list containing the toys in the database as an ArrayList Class
	 */
	private void displayToyList(ArrayList<Toy> searchedList) {
		listViewGift.getItems().clear();
		int count = 0;
		for (Toy toy : searchedList) {
			count ++;
			//There should be a menu call here that takes in count
			listViewGift.getItems().add(toy.toString());
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
		toyListView.getItems().clear();
		toyListView.getItems().add(singleToy.toString());
		
		
	}
	
	/**
	 * This method promts the user to enter details to filter through the toy data base
	 * 
	 * It asks for age of the person recieving the gift, type of toy, and maximum and minimum price range of the toy
	 * at least one of the question must be answered for the filter to work
	 * A list of toys that was filtered is then displayed for the user to purchase
	 */
	private void giftSuggestion(String giftAge, String typeInput, String minPriceInput, String maxPriceInput ) { //WIPWIPWIPWIPWIP
		String ageString = null; //originally was working needed null which is why we used the class name instead, but changed later, however it does not make a difference
		Integer age = null;
		String type = null;
		String minPriceString = null;
		Double minPrice = null;
		String maxPriceString = null;
		Double maxPrice = null;
		
		
		if (filteredList != null )filteredList.clear();
		
//		while(true) {
			
			ageString = menu.askGiftAge(giftAge);
			age = ageValidation(ageString);
			
			type =  menu.askTypeInputGift(typeInput);
			if(!type.isEmpty()) {
				if (type.equals("b")) type = "BoardGames";
				else if (type.equals("f")) type = "Figure";
				else if (type.equals("a")) type = "Animal";
				else if (type.equals("p")) type = "Puzzle";
			}
			
//			while(true) { //keep runing until minprice is less than maxprice or left blank
//				menu.pricerange();
				minPriceString = menu.askMinPrice(minPriceInput);
				minPrice = minPriceValidation(minPriceString);
				
				
				maxPriceString = menu.askMaxPrice(maxPriceInput);
				maxPrice = maxPriceValidation(maxPriceString);
			
				if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
					listViewGift.getItems().clear();
					lblErrorGift.setText("Error: minimum price cannot be greater than the maximum price.");
	                menu.displayMinMoreThanMaxError();
	            } //else break;
//			}
			
			if (ageString.isEmpty() && type.isEmpty() && minPriceString.isEmpty() && maxPriceString.isEmpty()) { //do this in the menu class
				lblErrorGift.setText("ERROR: at least one field must be filled out");

				menu.atLeastOneFieldMessage(); // now calls the menu class
			}
			else {
				
				filteredList = toyStorageDB.compareTypeToAllToys(type);
				if(type.isEmpty()) filteredList = toyList; //if the user chooses not to enter anything for the toy type, make sure filteredlist has all the toys
				
				filteredList = filterAge(filteredList, age);
				filteredList = filterPrice(filteredList, minPrice, maxPrice);
				
				menu.displayGiftSuggestionResult(); // Now calls the menu class
				if(filteredList.isEmpty()) { //if its empty then let user know and stop this function by returning
					menu.toyNotFound();
					listViewGift.getItems().clear();
					return;
				}
				
				displayToyList(filteredList);
//				Toy selectedToy = selectValidation(filteredList);
		//		if(selectedToy == null) {
		//			backToMainMenu = true;
		//			return;
		//		}
		//		purchase(selectedToy);
				
			}
//		}
	

	
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
			  		listViewGift.getItems().clear();
			  		lblErrorGift.setText("Invalid Input: setting age field as default");
			  		menu.invalidMessage("setting age field as default"); //empty means all there is not age limit
			  		age = Integer.MAX_VALUE;
			  	}

			} catch (Exception e) {
				listViewGift.getItems().clear();
		  		lblErrorGift.setText("Invalid Input: setting age field as empty");

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
				listViewGift.getItems().clear();
				lblErrorGift.setText("Invalid Input: Cannot be negative number, seeting  field to default");
				menu.invalidMessage(" Cannot be negative number, seeting  field to default");
				minPrice = 0.0;
				//just set to zero if its a negative doesnt make a difference
			}
			catch (Exception e) {
				listViewGift.getItems().clear();
				lblErrorGift.setText("Invalid Input: invalid minimum price, setting field as empty");
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
				listViewGift.getItems().clear();
				lblErrorGift.setText("Invalid Input: Cannot be negative number, seeting  field to default");

				menu.invalidMessage(" Cannot be negative number, seeting  field to default");
				
			}
			catch (Exception e) {
				listViewGift.getItems().clear();
				lblErrorGift.setText("Invalid Input: setting field as empty");

				menu.invalidMessage(" setting field as empty");
			}	
		}
		
		return maxPrice;
	}
	
	
    // ===================== FXML FIELDS (FROM SCENE BUILDER) =====================

    @FXML
    private TextField ageTF;

    @FXML
    private TextField animalMaterialTF;

    @FXML
    private TextField animalSizeTF;

    @FXML
    private TextField bgDesignersTF;

    @FXML
    private TextField bgMaxTF;

    @FXML
    private TextField bgMinTF;

    @FXML
    private TextField brandTF;

    @FXML
    private Button btnSubmit;

    @FXML
    private ComboBox<String> categoryCB;
    
    @FXML
    private ComboBox<String> categoryCB1;

    @FXML
    private ComboBox<String> cbClassFig;

    @FXML
    private ComboBox<String> cbMaterialAnimal;

    @FXML
    private ComboBox<String> cbSizeAnimal;

    @FXML
    private ComboBox<String> cbTypePuzzle;


    @FXML
    private TextField countTF;

    @FXML
    private TextField figureClassTF;

    @FXML
    private Label lblErrorGift;

    @FXML
    private Label lblErrorRemove;
    
    @FXML
    private Label lblNoticeAdd1;
    
    @FXML
    private ListView<String> listViewGift;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField priceTF;

    @FXML
    private TextField puzzleTypeTF;

    @FXML
    private Button removeBtn;

    @FXML
    private TextField removeSerialTF;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField serialTF;


    @FXML
    private AnchorPane paneAnimal;

    @FXML
    private AnchorPane paneBg;

    @FXML
    private AnchorPane paneFigure;

    @FXML
    private AnchorPane panePuzzle;
    
    @FXML
    private Tab tabp1;

    @FXML
    private Tab tabp2;

    @FXML
    private Tab tabp3;

    @FXML
    private Tab tabp4;

    @FXML
    private TextField tfAge;

    @FXML
    private TextField tfMaxPrice;

    @FXML
    private Button btnpurchase;
    
    @FXML
    private TextField tfMinPrice;

    @FXML
    private ListView<String> toyListView;

    // ========= HOME TAB CONTROLS (ADDED) =========

    /** Radio button option to search by serial number on the Home tab. */
    @FXML
    private RadioButton rbSerial;

    /** Radio button option to search by name on the Home tab. */
    @FXML
    private RadioButton rbName;

    /** Radio button option to search by type on the Home tab. */
    @FXML
    private RadioButton rbType;

    /** Text field for entering the serial number search on the Home tab. */
    @FXML
    private TextField txtSerialHome;

    /** Text field for entering the name search on the Home tab. */
    @FXML
    private TextField txtNameHome;

    /** Text field for entering the type search on the Home tab. */
    @FXML
    private TextField txtTypeHome;

    /** Button for searching on the Home tab. */
    @FXML
    private Button btnSearchHome;

    /** Button for clearing search fields on the Home tab. */
    @FXML
    private Button btnClearHome;

    /** Button for buying a selected toy on the Home tab. */
    @FXML
    private Button btnBuy;

    /** List view that displays toys on the Home tab. */
    @FXML
    private ListView<String> listViewHome; //kokokko
    private ArrayList<Toy> homeSearchList;
    
    
    
	/**
	 * This method initiallizes the javafx default and start up displays
	 */
    @FXML
    private void initialize() {
		paneFigure.setDisable(true);
		
		panePuzzle.setDisable(true);
		paneBg.setDisable(true);
        categoryCB.getItems().addAll("Animal", "BoardGames", "Figure", "Puzzle", "");
        categoryCB.setValue("");
        
        categoryCB1.getItems().addAll("Animal", "BoardGame", "Figure", "Puzzle");
        categoryCB1.setValue("Animal");
        
        cbClassFig.getItems().addAll("Action", "Doll", "Historic");
        cbClassFig.setValue("Action");
        
        cbSizeAnimal.getItems().addAll("Small", "Medium", "Large");
        cbSizeAnimal.setValue("Small");
        
        cbTypePuzzle.getItems().addAll("Mechanical", "Cryptic", "Logic", "Trivia", "Riddle");
        cbTypePuzzle.setValue("Mechanical");
        
        


        // === Make the Home radio buttons mutually exclusive ===
        homeSearchGroup = new ToggleGroup();
        rbSerial.setToggleGroup(homeSearchGroup);
        rbName.setToggleGroup(homeSearchGroup);
        rbType.setToggleGroup(homeSearchGroup);

        // default selection
        rbSerial.setSelected(true);
        
        
    }


    /**
     * This method submits the gifts  by calling giftsuggetion to display
     * @param event the button to submit
     */
    @FXML
    void submitGift(ActionEvent event) {
    	String typeInput;
    	//String giftAge, String typeInput, String minPriceInput, String maxPriceInput
    	
    
        if (categoryCB.getValue() == null) {
        	// if its empty or user chooses none
    		typeInput = "";
        }
        else typeInput = categoryCB.getValue().toString();
    
        giftSuggestion(tfAge.getText(), typeInput, tfMinPrice.getText(), tfMaxPrice.getText());
    }
    
    /**
     * This method purchases the selected item
     * @param event the button to purchase
     */
    @FXML
    void purchaseThis(ActionEvent event) {
    	Toy selectedToy =null;
    	int selected =listViewGift.getSelectionModel().getSelectedIndex();
    	try {
    		 selectedToy = filteredList.get(selected);
		} catch (IndexOutOfBoundsException e) {
			lblErrorGift.setText("Error: no items selected");
			System.out.println("Error: no items selected");
			return;
			
		} catch (NullPointerException e) {
			lblErrorGift.setText("Error: no items selected");

			System.out.println("Error: no items selected");
			return;
		}
    	logger.info(selectedToy.toString()); // logger to log when purchasing
    	purchase(selectedToy);
    	lblErrorGift.setText("successfully Purchased Toy!");
    	 
    	toyStorageDB.saveData();
    	submitGift(event);
    	
    }
    
    /**
     * Handles the Search button on the Home tab.
     *
     * This method reuses the same logic as the console program:
     * - If "Serial Number" is selected, it searches using compareSNToAllToys.
     * - If "Name" is selected, it searches using compareNameToAllToys.
     * - If "Type" is selected, it converts F/A/P/B to the full type
     *   and searches using compareTypeToAllToys.
     *
     * The results are stored in homeSearchList and displayed in the
     * Home tab list view as strings from Toy.toString().
     *
     * @param event the ActionEvent generated by clicking the Search button
     */
    @FXML
    private void onSearchHome(ActionEvent event) {
    	ToggleGroup homeSearchGroup = new ToggleGroup();
    	rbSerial.setToggleGroup(homeSearchGroup);
    	rbName.setToggleGroup(homeSearchGroup);
    	rbType.setToggleGroup(homeSearchGroup);


        // Clear old results
        listViewHome.getItems().clear();
        homeSearchList = new ArrayList<Toy>();

        ArrayList<Toy> snList;

        // === Search by Serial Number ===
        if (rbSerial.isSelected()) {
            String userSerialNumber = txtSerialHome.getText().trim();
            if (userSerialNumber.isEmpty()) {
                // same idea as console: just warn and stop
                menu.invalidMessage("Serial Number field is empty");
                return;
            }

            snList = toyStorageDB.compareSNToAllToys(userSerialNumber);

            if (snList.isEmpty()) {
                menu.toyNotFound();
                return;
            }

            homeSearchList = snList;
        }

        // === Search by Name ===
        else if (rbName.isSelected()) {
            String toyName = txtNameHome.getText().trim();
            if (toyName.isEmpty()) {
                menu.invalidMessage("Name field is empty");
                return;
            }

            snList = toyStorageDB.compareNameToAllToys(toyName);

            if (snList.isEmpty()) {
                menu.toyNotFound();
                return;
            }

            homeSearchList = snList;
        }

        // === Search by Type ===
        else if (rbType.isSelected()) {
            String toyType = txtTypeHome.getText().trim().toLowerCase();
            if (toyType.isEmpty()) {
                menu.invalidMessage("Type field is empty");
                return;
            }

            // Same mapping as findUsingType in ToyStoreManager
            if (toyType.equals("b")) toyType = "BoardGames";
            else if (toyType.equals("f")) toyType = "Figure";
            else if (toyType.equals("a")) toyType = "Animal";
            else if (toyType.equals("p")) toyType = "Puzzle";

            snList = toyStorageDB.compareTypeToAllToys(toyType);

            if (snList.isEmpty()) {
                menu.toyNotFound();
                return;
            }

            homeSearchList = snList;
        }

        // Show whatever is in homeSearchList in the Home ListView
        for (Toy toy : homeSearchList) {
            listViewHome.getItems().add(toy.toString());
        }
    }


    /**
     * Handles the Clear button on the Home tab.
     *
     * This method clears the three search text fields and repopulates
     * the Home list view with the full toy list from toyList.
     *
     * @param event the ActionEvent generated by clicking the Clear button
     */
    @FXML
    private void onClearHome(ActionEvent event) {
        // Clear text fields
        txtSerialHome.clear();
        txtNameHome.clear();
        txtTypeHome.clear();

        // Reset search list to all toys
        listViewHome.getItems().clear();
        homeSearchList = new ArrayList<Toy>();

        for (Toy toy : toyList) {
            listViewHome.getItems().add(toy.toString());
            homeSearchList.add(toy);
        }
    }


    /**
     * Handles the Buy button on the Home tab.
     *
     * This method:
     *  - Gets the selected row in the Home list view
     *  - Uses homeSearchList to find the matching Toy object
     *  - Calls the existing purchase(Toy) method
     *  - Saves the data back to the file using toyStorageDB.saveData()
     *  - Refreshes the Home list view to reflect updated counts
     *
     * @param event the ActionEvent generated by clicking the Buy button
     */
    @FXML
    private void onBuy(ActionEvent event) {

        int selectedIndex = listViewHome.getSelectionModel().getSelectedIndex();

        if (selectedIndex < 0) {
            // No selection
            menu.invalidMessage("No toy selected to purchase");
            return;
        }

        if (homeSearchList == null || selectedIndex >= homeSearchList.size()) {
            menu.invalidMessage("Selected index is out of range");
            return;
        }

        // Get the Toy the user chose
        Toy selectedToy = homeSearchList.get(selectedIndex);

        // Reuse your existing purchase logic from the console version
        purchase(selectedToy);

        // Save changes to the file
        toyStorageDB.saveData();

        // Refresh the Home list so the new stock count shows
        listViewHome.getItems().clear();
        for (Toy toy : homeSearchList) {
            listViewHome.getItems().add(toy.toString());
        }
    }
    
    @FXML
    private Button btnSearchRemove;
    
    @FXML
    private ListView<String> listViewRemove;
    @FXML
    private TextField tfSearchRemove;
   

    /**
     * this method seerches for the item that the user wants to remove
     * @param event the button that searches
     */
    @FXML
    private void searchRemove(ActionEvent event) {
        
        
        String serialNumber = removeSerialTF.getText().trim();
       
	        try {
	            
	            if (serialNumber.isEmpty()) {
	                lblErrorRemove.setText("Please enter a serial number.");
	                return;
	            }

	            Long snLong = Long.parseLong(serialNumber);
	    
	            if(!(Long.toString(snLong).length() == 10)) {
	            	lblErrorRemove.setText("Invalid input: must have 10 digits");
				}


	        } catch (Exception e) {
	        	lblErrorRemove.setText( "Invalid input: Input must be a number or must be 10 long");

	        }
	        
	        removeToy(removeSerialTF.getText().trim());
    }
    
    /**
     * This method actually removes the selected toy from the databaase
     * @param event the button to remove the toy
     */
    @FXML
    private void submitRemove(ActionEvent event) {
    	Toy selectedToy =null;
    	int selected =toyListView.getSelectionModel().getSelectedIndex();
    	try {
    		 selectedToy = sameSNList.get(selected);
		} catch (IndexOutOfBoundsException e) {
			lblErrorRemove.setText("Error: no items selected");
			
			return;
			
		} catch (NullPointerException e) {
			lblErrorRemove.setText("Error: no items selected");

			return;
		}
    	
    	lblErrorRemove.setText("SUCCESSFULLY REMOVED ITEM");
    	int indexInDataBase ;
    	indexInDataBase = toyList.indexOf(sameSNList.get(0)); //get the object in the sameSNList and get the index for that object within the data base arraylist of toys
	toyList.remove(indexInDataBase); //get the arraylist containing the toys and remove that toy from there
	logger.info(sameSNList.get(0).toString()); //logger to log when removing an item
    	toyStorageDB.saveData();
    	searchRemove(event);
    }
    
    
    /**
     * This method is for enabling the other fields for the specific toy that the user selects to ensure that the right fields are correctly filled out
     * @param event the combobox that has the toy category
     */
    @FXML
    void enableToy(ActionEvent event) {
    	//disable all the panes first and then enamble them later
    			paneFigure.setDisable(true);
    			paneAnimal.setDisable(true);
    			panePuzzle.setDisable(true);
    			paneBg.setDisable(true);
    			

    			animalMaterialTF.setText("");
    			bgMinTF.setText("");
    			bgMaxTF.setText("");
    			bgDesignersTF.setText("");
    			

    			
    
    			switch(categoryCB1.getValue().toLowerCase()) {
    	        case "figure":
    	        	paneFigure.setDisable(false);
    	            break;
    	        case "animal":
    	        	paneAnimal.setDisable(false);
    	            break;
    	        case "puzzle":
    	        	panePuzzle.setDisable(false);
    	            break;
    	        case "boardgame":
    	        	paneBg.setDisable(false);
    	            break;
    	    }
    	
    }
    
}