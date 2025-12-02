package mru.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mru.test.model.*;
import mru.tsc.model.Animal;
class AnimalTest {

	Animal animal;
	@BeforeEach
	void initialize() {
		animal = new Animal("1111111111", "StarFish", "Hasbro", 12.99, 5, 6, "plastic", 'L');
	}
	
	@Test
	void testGetSize() {
		char size = animal.getSize();
		assertEquals('L', size);
	}
	
	@Test
	void testSetSize() {
		char size = animal.getSize();
		assertEquals('L', size);
		animal.setSize('M');
		char size2 = animal.getSize();
		assertEquals('M', size2);
	}
	
	@Test
	void testSetGetMaterial() {
		String mat = animal.getMaterial();
		assertEquals("plastic", mat);
		animal.setMaterial("wood");
		String mat2 = animal.getMaterial();
		assertEquals("wood", mat2);
	}
	
	@Test
	void testTypeOf() {
		String type = animal.typeOf();
		assertEquals("Animal", type);
	}
	
	@Test
	void testStringToFormat() { 
		String formatted = animal.stringToFormat();
		assertNotEquals("Animal", formatted);
		System.out.println(formatted);
		assertEquals("1111111111;StarFish;Hasbro;12.99;5;6;plastic;L", formatted);
	}
	

	
    @Test
    void testGetName() {
        assertEquals("StarFish", animal.getName());
    }
    
    @Test
    void testSetName() {
        animal.setName("Hollow Knight");
        assertEquals("Hollow Knight", animal.getName());
    }
    
    @Test
    void testGetSerialNum() {
        assertEquals("1111111111", animal.getSerialNum());
    }
    
    @Test
    void testSetSerialNum() {
    	animal.setSerialNum("0000000001");
        assertEquals("0000000001", animal.getSerialNum());
    }
    
    @Test
    void testGetBrand() {
        assertEquals("Hasbro", animal.getBrand());
    }
    
    @Test
    void testSetBrand() {
    	animal.setBrand("Nerf");
        assertEquals("Nerf", animal.getBrand());
    }
    
    @Test
    void testGetPrice() {
        assertEquals(12.99, animal.getPrice());
    }
    
    @Test
    void testSetPrice() {
    	animal.setPrice(100.11);
        assertEquals(100.11, animal.getPrice());
    }
    
    @Test
    void testGetCount() {
        assertEquals(5, animal.getCount());
    }
    
    @Test
    void testSetCount() {
    	animal.setCount(99);
        assertEquals(99, animal.getCount());
    }
    
    @Test
    void testGetAgeAllowed() {
        assertEquals(6, animal.getAgeAllowed());
    }
    
    @Test
    void testSetAgeAllowed() {
    	animal.setAgeAllowed(12);
        assertEquals(12, animal.getAgeAllowed());
    }
    
    @Test
    void testToyDecrement() {
        int initialCount = animal.getCount();
        int newCount = animal.toyDecrement();
        assertEquals(initialCount - 1, newCount);
        assertNotEquals(initialCount, newCount);
    }
    



}
