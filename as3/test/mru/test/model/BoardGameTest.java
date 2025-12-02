package mru.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mru.test.model.*;
import mru.tsc.model.BoardGame;

class BoardGameTest {

	BoardGame bg;
	@BeforeEach
	void initialize() {
		bg = new BoardGame("1111111111", "StarFish", "Hasbro", 12.99, 5, 6, 1, 4, "Blake Griffin");
	}
	@Test
	void testGetSetDesigner() {
		assertEquals("Blake Griffin", bg.getDesigners());
		
		bg.setDesigners("Billy Jeans");
		assertEquals("Billy Jeans", bg.getDesigners());
	}
	
	@Test
	void testTypeOf() {
		String type = bg.typeOf();
		assertNotEquals("Animal", type);
		assertEquals("BoardGames", type);
	}
	
	@Test
	void testGetSetMinPlayers() {
		int min = bg.getMinPlayers();
		assertEquals(1, min);
		
		bg.setMinPlayers(2);
		int min2 = bg.getMinPlayers();
		assertEquals(2, min2);
	}
	
	@Test
	void testGetSetMaxPlayers() {
		int max = bg.getMaxPlayers();
		assertEquals(4, max);
		
		bg.setMaxPlayers(10);
		int max2 = bg.getMaxPlayers();
		assertEquals(10, max2);
	}
	
	@Test
	void testStringToFormat() { 
		String formatted = bg.stringToFormat();
		assertNotEquals("BoardGames", formatted);
		assertEquals("1111111111;StarFish;Hasbro;12.99;5;6;1-4;Blake Griffin", formatted);
	}
	
	@Test
	void testToString() { //Work IN progress
		String string = bg.toString();
		assertNotEquals("1111111111;StarFish;Hasbro;12.99;5;6;plastic;L", string);
		System.out.println(string);
//		assertEquals("???????????", string);
	}
	
    @Test
    void testGetName() {
        assertEquals("StarFish", bg.getName());
    }
    
    @Test
    void testSetName() {
        bg.setName("Hollow Knight");
        assertEquals("Hollow Knight", bg.getName());
    }
    
    @Test
    void testGetSerialNum() {
        assertEquals("1111111111", bg.getSerialNum());
    }
    
    @Test
    void testSetSerialNum() {
        bg.setSerialNum("0000000001");
        assertEquals("0000000001", bg.getSerialNum());
    }
    
    @Test
    void testGetBrand() {
        assertEquals("Hasbro", bg.getBrand());
    }
    
    @Test
    void testSetBrand() {
        bg.setBrand("Nerf");
        assertEquals("Nerf", bg.getBrand());
    }
    
    @Test
    void testGetPrice() {
        assertEquals(12.99, bg.getPrice());
    }
    
    @Test
    void testSetPrice() {
        bg.setPrice(100.11);
        assertEquals(100.11, bg.getPrice());
    }
    
    @Test
    void testGetCount() {
        assertEquals(5, bg.getCount());
    }
    
    @Test
    void testSetCount() {
        bg.setCount(99);
        assertEquals(99, bg.getCount());
    }
    
    @Test
    void testGetAgeAllowed() {
        assertEquals(6, bg.getAgeAllowed());
    }
    
    @Test
    void testSetAgeAllowed() {
        bg.setAgeAllowed(12);
        assertEquals(12, bg.getAgeAllowed());
    }
    
    @Test
    void testToyDecrement() {
        int initialCount = bg.getCount();
        int newCount = bg.toyDecrement();
        assertEquals(initialCount - 1, newCount);
        assertNotEquals(initialCount, newCount);
    }
    


}
