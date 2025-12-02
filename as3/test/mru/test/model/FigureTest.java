package mru.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mru.tsc.model.Figure;


class FigureTest {

    private Figure figure;

    @BeforeEach
    void initialize() {
        figure = new Figure("1234567890", "ActionMan", "Hasbro", 29.99, 5, 8, 'A');
    }

    @Test
    void testGetAndSetClassification() {
        assertEquals('A', figure.getClassification());
        figure.setClassification('H');
        assertEquals('H', figure.getClassification());
    }

    @Test
    void testTypeOf() {
        assertEquals("Figure", figure.typeOf());
    }

    @Test
    void testStringToFormat() {
        String expected = "1234567890;ActionMan;Hasbro;29.99;5;8;A";
        assertEquals(expected, figure.stringToFormat());
    }


    @Test
    void testGetAndSetName() {
        assertEquals("ActionMan", figure.getName());
        figure.setName("Knight");
        assertEquals("Knight", figure.getName());
    }

    @Test
    void testGetAndSetSerialNum() {
        assertEquals("1234567890", figure.getSerialNum());
        figure.setSerialNum("0987654321");
        assertEquals("0987654321", figure.getSerialNum());
    }

    @Test
    void testGetAndSetBrand() {
        assertEquals("Hasbro", figure.getBrand());
        figure.setBrand("Mattel");
        assertEquals("Mattel", figure.getBrand());
    }

    @Test
    void testGetAndSetPrice() {
        assertEquals(29.99, figure.getPrice());
        figure.setPrice(49.99);
        assertEquals(49.99, figure.getPrice());
    }

    @Test
    void testGetAndSetCount() {
        assertEquals(5, figure.getCount());
        figure.setCount(10);
        assertEquals(10, figure.getCount());
    }

   

    @Test
    void testToyDecrement() {
        int initialCount = figure.getCount();
        int newCount = figure.toyDecrement();
        assertEquals(initialCount - 1, newCount);
    }
}
