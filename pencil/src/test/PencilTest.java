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

		//Test 2 & 3 - woodchuck test (erase 2x)
		testPencil.ClearPage();
		testString = "How much wood would a woodchuck chuck if a woodchuck could chuck wood?";
		testPencil.Write(testString);
		testPencil.Erase("chuck");
		//testPencil.Print();
		String expectedString = "How much wood would a woodchuck chuck if a woodchuck could       wood?";
		assertTrue("Test 2 failed",testPencil.Text.equals(expectedString));
		testPencil.Erase("chuck");
		//testPencil.Print();
		expectedString = "How much wood would a woodchuck chuck if a wood      could       wood?";
		assertTrue("Test 3 failed",testPencil.Text.equals(expectedString));
	}

	@Test
	public void testEraserDegradation() {
		//Test 1 - check for decrease in eraser durability
		String testString = "testing eraser degradation...";
		String eraseText = "...";
		int durabilityUse = Math.min(testPencil.eraserDurability,eraseText.length());
		int expectedDurability = testPencil.eraserDurability - durabilityUse;
		testPencil.Write(testString);
		testPencil.Erase(eraseText);
		int actualDurability = testPencil.eraserDurability;
		assertEquals("Test 1 failed",expectedDurability,actualDurability);

		//Test 2 - check for no change in text when eraser durability is too low
		testPencil.ClearPage();
		testPencil.eraserDurability = 2;
		testPencil.Write(testString);
		testPencil.Erase(eraseText);
		assertTrue("Test 2 failed",testPencil.Text.equals("testing eraser degradation.  "));
	}

	@Test
	public void testEdit(){
		//Test 1 - basic edit
		String testString = "testing edit...";
		testPencil.Write(testString);
		testPencil.Erase("testing");
		testPencil.Edit("using");
		assertTrue("Test 1 failed",testPencil.Text.equals("using   edit..."));

		//Test 2 - over editing
		testPencil.ClearPage();
		testPencil.Write(testString);
		testPencil.Erase("testing");
		testPencil.Edit("over-using");
		assertTrue("Test 2 failed",testPencil.Text.equals("over-usi@@it..."));

		//Test 3 - test for index out of bounds
		testPencil.ClearPage();
		testPencil.Write(testString);
		testPencil.Erase("...");
		testPencil.Edit("1234");
		assertTrue("Test 3 failed",testPencil.Text.equals("testing edit123"));
	}

	@Test
	public void testEditDegradation() {
		//Test 1 - check for decrease in point durability
		String testString = "testing edit...";
		String editString = "using";
		testPencil.Write(testString);
		testPencil.Erase("testing");
		int durabilityUse = testPencil.countDurabilityUse(editString);
		int expectedDurability = testPencil.pointDurability - durabilityUse;
		testPencil.Edit(editString);
		int actualDurability = testPencil.pointDurability;
		assertEquals("Test 1 failed",expectedDurability,actualDurability);

		//Test 2 - "Test1" with capital letters
		testPencil.ClearPage();
		editString = "USING";
		testPencil.Write(testString);
		testPencil.Erase("testing");
		durabilityUse = testPencil.countDurabilityUse(editString);
		expectedDurability = testPencil.pointDurability - durabilityUse;
		testPencil.Edit(editString);
		actualDurability = testPencil.pointDurability;
		assertEquals("Test 2 failed",expectedDurability,actualDurability);

		//Test 3 - over edit and capital letters (ensure @ degrades 1 point)
		testPencil.ClearPage();
		editString = "OVER-USING";
		testPencil.Write(testString);
		testPencil.Erase("testing");
		durabilityUse = testPencil.countDurabilityUse(editString);
		expectedDurability = testPencil.pointDurability - durabilityUse;
		testPencil.Edit(editString);
		actualDurability = testPencil.pointDurability;
		assertEquals("Test 3 failed",expectedDurability,actualDurability);
	}
}