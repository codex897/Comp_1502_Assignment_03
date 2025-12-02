package mru.test.model;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mru.tsc.model.*;

class ToyStorageDBTest {
	ToyStorageDB db;
	ArrayList<Toy> fullToyList;
	@BeforeEach
    void setUp() {
		try {
			db= new ToyStorageDB("res/toys2.txt");
			db.addData();
			fullToyList = db.getToyDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e + "There was an error opening this file");
		}
    }
	
	@Test
	void testGetToyDB() {
		ArrayList<Toy> newToyDB= db.getToyDB();
		assertEquals(newToyDB, fullToyList);
	}
	
	@Test
	void testCreateFigure_CompareNameToAllToys() { //also tests the compareNameToAllToys
		String [] figureArray = {"1111111111", "Buzz", "Toy Story", "12.99", "5", "9", "A"};
		db.createFigure(figureArray);
		
		String createdToy = db.compareNameToAllToys("Buzz").get(0).stringToFormat(); //find the toy using compareNameToAllToys and get its file string format
		
		assertEquals("1111111111;Buzz;Toy Story;12.99;5;9;A", createdToy); 
		System.out.println("created and added figure");
	}
	
	
	@Test
	void testCreateAnimal_CompareSNToAllToys() { //also tests the compareSNToAllToys
		String [] animalArray = {"2222222222", "Buzz", "Toy Story", "12.99", "5", "9", "wood", "M" };
		db.createAnimal(animalArray);
		
		String createdToy = db.compareSNToAllToys("2222222222").get(0).stringToFormat(); //find the toy using compareSNToAllToys and get its file string format
		
		assertEquals("2222222222;Buzz;Toy Story;12.99;5;9;wood;M", createdToy); 
		System.out.println("created and added animal");
	}
	
	@Test
	void testCreatePuzzle_CompareTypeToAllToys() { //also tests the compareTypeToAllToys
		String [] puzzleArray = {"5555555555", "Buzz", "Toy Story", "12.99", "5", "9", "T"}; 
		db.createPuzzle(puzzleArray);
		
		ArrayList<Toy> arrayToyList = db.compareTypeToAllToys("Puzzle");
		/*
		 * next, use compareTypeToAllToys method to get find the newly created toy and 
		 * get the size of the array list subtract one and that is the index of the newly created toy
		 */
		String createdToy = arrayToyList.get(db.compareTypeToAllToys("Puzzle").size()-1).stringToFormat(); 
		
		assertEquals("5555555555;Buzz;Toy Story;12.99;5;9;T", createdToy); 
		System.out.println("created and added puzzle");
	}
	
	@Test
	void testCreateBoardGame() {
		String [] boardGameArray = {"7777777777", "Buzz", "Toy Story", "12.99", "5", "9", "2-4", "Bill"};
		db.createBoardGame(boardGameArray);
		
		String createdToy = db.compareNameToAllToys("Buzz").get(0).stringToFormat();
		 
		assertEquals("7777777777;Buzz;Toy Story;12.99;5;9;2-4;Bill", createdToy); 
		System.out.println("created and added board game");
	}
	
	@Test
	void testRemoveData() {
		/*
		 * first and second toy in the array list should not be the same
		 */
		String secondToy = fullToyList.get(1).stringToFormat();
		String firstToy = fullToyList.get(0).stringToFormat();
		assertNotEquals(secondToy, firstToy);
		try {
			//The original variable of secondToy should be the same as the firstToyNew after removing
			db.removeData(fullToyList.get(0));
			String firstToyNew = fullToyList.get(0).stringToFormat();
			assertEquals(secondToy, firstToyNew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testToString() {
		String [] boardGameArray = {"7777777777", "Buzz", "Toy Story", "12.99", "5", "9", "2-4", "Bill"};
		db.createBoardGame(boardGameArray);
		String dbString = db.toString();
		
		//check if these keywords and characters exists in the file to indicate that it exists and works
		assertTrue(dbString.contains("Buzz"));
		assertTrue(dbString.contains("Khet"));
		assertTrue(dbString.contains(";"));
		assertTrue(dbString.contains("-"));
		assertTrue(dbString.contains("\n"));
	}
	
	@Test
	void testGetToyType() {
		String [] animalArray = {"2222222222", "Buzz", "Toy Story", "12.99", "5", "9", "wood", "M" };
		db.createAnimal(animalArray);
		String createdToy = db.compareSNToAllToys("2222222222").get(0).typeOf();
		String toyType = db.getToyType("2222222222"); //gets the toy type from the first or second number in the serial number
		
		assertEquals(toyType, createdToy);
		
	}
	
	@Test
	void testSaveData() {
		String [] figureArray = {"1111111111", "Buzz", "Toy Story", "12.99", "5", "9", "A"};// create a new toy
		db.createFigure(figureArray);
		assertTrue(db.toString().contains("Buzz")); //check if the toy name exists
		
		db.saveData();
		this.setUp();
		
		assertTrue(db.toString().contains("Buzz")); //check if it exist after saving and opening it up again
		
		try {
			db.removeData(db.compareNameToAllToys("Buzz").get(0)); //remove the toy and save and open it again
			db.saveData();
			this.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(db.toString().contains("Buzz")); //after save and opening again, check if "buzz" still exists
		
	}

}
