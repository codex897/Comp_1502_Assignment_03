package mru.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mru.tsc.model.Puzzle;

class PuzzleTest {

    Puzzle puzzle;

    @BeforeEach
    void setUp() {
        puzzle = new Puzzle("3333333333", "BrainTwister", "Ravensburger", 19.99, 10, 8, 'L');
    }

    @Test
    void testGetType() {
        assertEquals('L', puzzle.getType(), "Expected type should be 'L'");
    }

    @Test
    void testSetType() {
        puzzle.setType('M');
        assertEquals('M', puzzle.getType(), "Expected type should be updated to 'M'");
    }

    @Test
    void testTypeOf() {
        assertEquals("Puzzle", puzzle.typeOf(), "typeOf() should return 'Puzzle'");
    }

    @Test
    void testStringToFormat() {
        String formatted = puzzle.stringToFormat();
        assertTrue(formatted.contains(";L") || formatted.endsWith(";L"), "Formatted string should include the puzzle type at the end");
    }


}
