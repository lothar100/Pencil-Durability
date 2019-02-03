package test;

import main.Pencil;
import static org.junit.Assert.*;
import org.junit.Test;

public class PencilTest {

	Pencil testPencil = new Pencil();

    @Test
	public void testWrite() {
		String testString = "testing write...";
		testPencil.Write(testString);
		assertTrue(testPencil.Text.equals(testString));
	}
}