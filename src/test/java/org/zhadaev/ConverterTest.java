package org.zhadaev;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConverterTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void testConverterWithoutArguments() {
        Converter.main(new String[]{});
        Assert.assertEquals("Not all arguments entered", output.toString().trim());
    }

    @Test
    public void testConverterWithNonexistentSourceFile() {
        Converter.main(new String[]{"xxx", "xxx"});
        Assert.assertEquals("Source document not found", output.toString().trim());
    }

    @Test
    public void testConverterWithNonexistentSourceFile1() {
        String file = getClass().getClassLoader().getResource("data-2.bin").getFile();
        Converter.main(new String[]{file, "xxx"});
        Assert.assertEquals("Source file is corrupted or empty", output.toString().trim());
    }

    @Test
    public void testConverterWithWrongReceiverFileName() {
        String file = getClass().getClassLoader().getResource("data-1.bin").getFile();
        Converter.main(new String[]{file, "!@#$%^&*()?|/:"});
        Assert.assertEquals("An error occurred while writing the receiver file", output.toString().trim());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

}
