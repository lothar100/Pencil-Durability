package test;

import main.Pencil;
import static org.junit.Assert.*;
import org.junit.Test;

public class PencilTest {

	Pencil testPencil = new Pencil();

    @Test
	public void testWrite() {
		//Test 1 - basic write
		String testString = "testing write...";
		testPencil.Write(testString);
		assertTrue("Test 1 failed",testPencil.Text.equals(testString));

		// Test 2 - append to current text
		String testAppend = "\ntesting write append...";
		testPencil.Write(testAppend);
		assertTrue("Test 2 failed",testPencil.Text.equals(testString + testAppend));
	}

	@Test
	public void testDegradation() {
		//Test 1 - check for degradation in point durability
		String testString = "testing degradation...";
		int numCharacters = testString.replaceAll("\\s+","").length();
		int expectedDurability = testPencil.pointDurability - numCharacters;
		testPencil.Write(testString);
		int actualDurability = testPencil.pointDurability;
		assertEquals("Test 1 failed",expectedDurability,actualDurability);
	}
}