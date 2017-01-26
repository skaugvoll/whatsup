package com.skaugvoll.timetable;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by sigveskaugvoll on 26.01.2017.
 */
public class DatabaseTest extends TestCase {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void outTest() {
        System.out.print("hello");
        assertEquals("hello", outContent.toString());
    }

    @Test
    public void errTest() {
        System.err.print("hello again");
        assertEquals("hello again", errContent.toString());
    }


}
