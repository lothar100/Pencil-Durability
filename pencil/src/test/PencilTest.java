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
	public void testPointDegradation() {
		//Test 1 - check for decrease in point durability
		String testString = "testing point degradation...";
		int durabilityUse = testPencil.countDurabilityUse(testString);
		int expectedDurability = testPencil.pointDurability - durabilityUse;
		testPencil.Write(testString);
		int actualDurability = testPencil.pointDurability;
		assertEquals("Test 1 failed",expectedDurability,actualDurability);

		//Test 2 - check for upper case using two durability
		testString = "TESTING POINT DEGRADATION...";
		durabilityUse = testPencil.countDurabilityUse(testString);
		expectedDurability = testPencil.pointDurability - durabilityUse;
		testPencil.Write(testString);
		actualDurability = testPencil.pointDurability;
		assertEquals("Test 2 failed",expectedDurability,actualDurability);
	}

	@Test
	public void testSharpening() {
		//Test 1 - check for increase in point durability
		String testString = "testing sharpening...";
		int expectedDurability = testPencil.initialDurability;
		testPencil.Write(testString);
		testPencil.Sharpen();
		int actualDurability = testPencil.pointDurability;
		assertEquals("Test 1 failed",expectedDurability,actualDurability);

		//Test 2 - check for no change in point durability when the length is too low to sharpen
		testString = "testing not sharpening...";
		testPencil.bodyLength = 1;
		testPencil.Write(testString);
		testPencil.Sharpen();
		testPencil.Write(testString);
		expectedDurability = testPencil.pointDurability;
		testPencil.Sharpen();
		actualDurability = testPencil.pointDurability;
		assertEquals("Test 2 failed",expectedDurability,actualDurability);
		testPencil.bodyLength = 10;
	}

	@Test
	public void testErase() {
		//Test 1 - basic erase
		String testString = "testing erase...";
		testPencil.Write(testString);
		testPencil.Erase("...");
		assertTrue("Test 1 failed",testPencil.Text.equals("testing erase   "));
	}

	@Test
	public void testEraserDegradation() {
		//Test 1 - check for decrease in eraser durability
		String testString = "testing eraser degradation...";
		int characters = testString.replaceAll("\\s+","").length();
		int expectedDurability = testPencil.eraserDurability - characters;
		testPencil.Write(testString);
		testPencil.Erase("...");
		int actualDurability = testPencil.eraserDurability;
		assertEquals("Test 1 failed",expectedDurability,actualDurability);
	}
}