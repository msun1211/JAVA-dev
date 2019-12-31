package edu.gatech.seclass.encode;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * This is a Georgia Tech provided code example for use in assigned private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it available on publicly viewable websites including
 * repositories such as github and gitlab.  Such sharing may be investigated as a GT honor code violation. Created for CS6300.
 *
 * DO NOT ALTER THIS CLASS.  Use it as an example for MyMainTest.java
 */

public class MainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
     *  TEST UTILITIES
     */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }


    //Read File Utility
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static final String USAGE = "Usage: encode [-a [integer]] [-r string | -k string] [-c string] [-l string] <filename>";
    /*
     * TEST FILE CONTENT
     */
    private static final String FILE1 = "abcXYZ";
    private static final String FILE2 = "Howdy Billy,\n" +
            "I am going to take cs6300 and cs6400 next semester.\n" +
            "Did you take cs 6300 last semester? I want to\n" +
            "take 2 courses so that I will graduate Asap!";
    private static final String FILE3 = "abcXYZ123ABCxyz";
    private static final String FILE4 = "AbcXYZ\nabc\nXYZcba";
    private static final String FILE5 = " ";
    private static final String FILE6 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String FILE7 = "0123456789";
    private static final String FILE8 = "Let's try some **special**  %!(characters)!% ###\r" +
            "and line breaks^$@ \r" +
            "in \\1/ file;\r" +
            ":-)";
    private static final String FILE9 = "Up with the white and gold\r" +
            "Down with the red and black\r" +
            "Georgia Tech is out for a victory\r" +
            "We'll drop a battle axe on georgia's head\r" +
            "When we meet her our team is sure to beat her\r" +
            "Down on the old farm there will be no sound\r" +
            "'Till our bow wows rips through the air\r" +
            "When the battle is over georgia's team will be found\r" +
            "With the Yellow Jacket's swarming 'round! Hey!";
    private static final String FILE10 = "Robert'); DROP TABLE students;--";
    private static final String FILE11 = ".*";
    private static final String FILE12 = " I’ve got a really good UDP joke to tell you, but I don’t know if you’ll get it. ";
    private static final String FILE13 = "3 Database Admins walked\n" +
            "into a NoSQL bar.\n"  +
            "A little later, they walked\n"  +
            "out because they couldn’t find a table.";
    private static final String FILE14 = "\naAbB\ncCdD eE\nfF";
    private static final String FILE15 = "";

    // test cases

    /*
     *   TEST CASES
     */

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 1 from assignment directions
    @Test
    public void mainTest1() throws Exception {
        File inputFile = createInputFile(FILE1);

        String args[] = {inputFile.getPath()};
        Main.main(args);

        String expected = "ABCxyz";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 2 from assignment directions
    @Test
    public void mainTest2() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {"-r", "aZ", inputFile.getPath()};
        Main.main(args);

        String expected = "bcXY123BCxy";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 3 from assignment directions
    @Test
    public void mainTest3() throws Exception {
        File inputFile = createInputFile(FILE2);

        String args[] = {"-c", "aeiou", "-k", "aeiouxyz", inputFile.getPath()};
        Main.main(args);

        String expected = "Oy Iy,\n" +
                "i A OI O AE  A  Ex EEE.\n" +
                "I yOU AE   A EEE? i A O\n" +
                "AE  OUE O A i I AUAE aA!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 4 from assignment directions
    @Test
    public void mainTest4() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {"-a", "-k", "abc", inputFile.getPath()};
        Main.main(args);

        String expected = "zyxZYX";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }


    // Purpose: To provide an example of a test case format (no arguments passed)
    // Frame #: Instructor error example
    @Test
    public void mainTest5() {
        String args[]  = new String[0]; //no arguments
        Main.main(args);
        assertEquals(USAGE, errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 5 from assignment directions
    @Test
    public void mainTest6() throws Exception {
        File inputFile = createInputFile(FILE4);

        String args[] = {"-l", "abc", inputFile.getPath()};
        Main.main(args);

        String expected = "abc\nXYZcba";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", FILE4, actual);
        assertEquals(expected, outStream.toString().trim().replaceAll("\\R", "\n"));
    }

    // Purpose: To provide an example test case format
    // Frame #: Instructor example 6 from assignment directions
    @Test
    public void mainTest7() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", "3", inputFile.getPath()};
        Main.main(args);

        String expected = "wvutsrqponmlkjihgfedcbazyxWVUTSRQPONMLKJIHGFEDCBAZYX";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest8() throws Exception {
        File inputFile = createInputFile(FILE2);

        String args[] = {"-a", "1", inputFile.getPath()};
        Main.main(args);

        String expected = "Rkcva Xqnna,\n"+
                "Q ym skqls fk fyou wg6300 ylv wg6400 lubf gumugfuh.\n" +
                "Vqv ake fyou wg 6300 nygf gumugfuh? Q cylf fk\n" +
                "fyou 2 wkehgug gk fryf Q cqnn shyveyfu Ygyj!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest9() throws Exception {
        File inputFile = createInputFile(FILE14);

        String args[] = {"-k", "XYZ123", inputFile.getPath()};
        Main.main(args);

        String expected = "\n\n \n";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest10() throws Exception {
        File inputFile = createInputFile(FILE13);

        String args[] = {"-a", "-r", "3", inputFile.getPath()};
        Main.main(args);

        String expected = " Wzgzyzhv Zwnrmh dzopvw\n" +
                "rmgl z MlHJO yzi.\n"  +
                "Z orggov ozgvi, gsvb dzopvw\n"  +
                "lfg yvxzfhv gsvb xlfowm’g urmw z gzyov.";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest11() throws Exception {
        File inputFile = createInputFile(FILE5);

        String args[] = {inputFile.getPath()};
        Main.main(args);

        String expected = " ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest12() throws Exception {
        File inputFile = createInputFile(FILE5);

        String args[] = {"-a", "abc", "-r", "xyz123", "-l", "123", inputFile.getPath()};
        Main.main(args);

        String expected = FILE5;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertTrue(outStream.toString().isBlank());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest13() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-k", "aeiou123", inputFile.getPath()};
        Main.main(args);

        String expected = "aeiouAEIOU";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest14() throws Exception {
        File inputFile = createInputFile(FILE13);

        String args[] = {"-k", "!@#", inputFile.getPath()};
        Main.main(args);

        String expected = FILE13;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals(USAGE, errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest15() throws Exception {
        File inputFile = createInputFile(FILE13);

        String args[] = {"-c", "123", inputFile.getPath()};
        Main.main(args);

        String expected = FILE13;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals(USAGE, errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest16() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = {"-r", "abc1", "-c", "abcdefghijkl", inputFile.getPath()};
        Main.main(args);

        String expected = "023456789";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest17() throws Exception {
        File inputFile = createInputFile(FILE8);

        String args[] = {"-l", "il*", inputFile.getPath()};
        Main.main(args);

        String expected = "Let's try some **special**  %!(characters)!% ###";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", FILE8, actual);
        assertEquals(expected, outStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest18() throws Exception {
        File inputFile = createInputFile(FILE8);

        String args[] = {"-k", "ABCDE", inputFile.getPath()};
        Main.main(args);

        String expected = "e'  e **eca**  %!(caace)!% ###\r" +
                "ad e bea^$@ \r" +
                " \\/ e;\r" +
                ":-)";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest19() throws Exception {
        File inputFile = createInputFile(FILE12);

        String args[] = {"-a", "25", "-r", "UDP", inputFile.getPath()};
        Main.main(args);

        String expected = " S’fw umh a jwappc umm  rmqw hm hwpp cm, zh S mn’h qnme sv cm’pp uwh sh. ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest20() throws Exception {
        File inputFile = createInputFile(FILE10);

        String args[] = {inputFile.getPath()};
        Main.main(args);

        String expected = "rOBERT'); drop table STUDENTS;--";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest21() throws Exception {
        File inputFile = createInputFile(FILE9);

        String args[] = {"-a", inputFile.getPath()};
        Main.main(args);

        String expected = "Fk drgs gsv dsrgv zmw tlow\r" +
                "Wldm drgs gsv ivw zmw yozxp\r" +
                "Tvlitrz Gvxs rh lfg uli z erxglib\r" +
                "Dv'oo wilk z yzggov zcv lm tvlitrz'h svzw\r" +
                "Dsvm dv nvvg svi lfi gvzn rh hfiv gl yvzg svi\r" +
                "Wldm lm gsv low uzin gsviv droo yv ml hlfmw\r" +
                "'Groo lfi yld dldh irkh gsilfts gsv zri\r" +
                "Dsvm gsv yzggov rh levi tvlitrz'h gvzn droo yv ulfmw\r" +
                "Drgs gsv Bvoold Qzxpvg'h hdzinrmt 'ilfmw! Svb!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest22() throws Exception {
        File inputFile = createInputFile(FILE11);

        String args[] = {"-r", "abcdefghijklmnopqrstuvwxyz", inputFile.getPath()};
        Main.main(args);

        String expected = FILE11;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertTrue(errStream.toString().trim().isBlank());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest23() throws Exception {
        File inputFile = createInputFile(FILE12);

        String args[] = {"-a-k", "abc", inputFile.getPath()};
        Main.main(args);

        String expected = FILE12;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals(USAGE, errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest24() throws Exception {
        File inputFile = createInputFile(FILE9);

        String args[] = {"-k", "abc", "-x", inputFile.getPath()};
        Main.main(args);

        String expected = FILE9;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals(USAGE, errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest25() throws Exception {

        String args[] = {"-a", "filedoesnotexist.txt"};
        Main.main(args);

        assertEquals("File Not Found", errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest26() throws Exception {

        File inputFile = createInputFile(FILE15);

        String args[] = {"-k", "abc", inputFile.getPath()};
        Main.main(args);

        String expected = FILE15;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertTrue(errStream.toString().trim().isBlank());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest27() throws Exception {

        File inputFile = createInputFile(FILE9);

        String args[] = {"-k", "abc", "-r", "a", inputFile.getPath()};
        Main.main(args);

        String expected = FILE9;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals(USAGE, errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest28() throws Exception {

        File inputFile = createInputFile(FILE9);

        String args[] = {"-l", "W", "-c", "abcdefghijklmnoPQRSTUVWXYZ", inputFile.getPath()};
        Main.main(args);

        String expected = "uP WITH THE WHITE AND GOLD\r" +
                "dOWN WITH THE RED AND BLACK\r" +
                "gEORGIA tECH IS OUT FOR A VICTORY\r" +
                "wE'LL DROP A BATTLE AXE ON GEORGIA'S HEAD\r" +
                "wHEN WE MEET HER OUR TEAM IS SURE TO BEAT HER\r" +
                "dOWN ON THE OLD FARM THERE WILL BE NO SOUND\r" +
                "'tILL OUR BOW WOWS RIPS THROUGH THE AIR\r" +
                "wHEN THE BATTLE IS OVER GEORGIA'S TEAM WILL BE FOUND\r" +
                "wITH THE yELLOW jACKET'S SWARMING 'ROUND! hEY!";

        String out = "We'll drop a battle axe on georgia's head\r" +
                "When we meet her our team is sure to beat her\r" +
                "When the battle is over georgia's team will be found\r" +
                "With the Yellow Jacket's swarming 'round! Hey!";


        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals(out, outStream.toString().trim().replaceAll("\\R", "\r"));
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest29() throws Exception {

        File inputFile = createInputFile(FILE9);

        String args[] = {"-l", "W", "-c", inputFile.getPath()};
        Main.main(args);

        String expected = FILE9;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals(USAGE, errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest30() throws Exception {

        File inputFile = createInputFile(FILE8);

        String args[] = {"-r", "*s*", inputFile.getPath()};
        Main.main(args);

        String expected = "Let' try ome **pecial**  %!(character)!% ###\r" +
                "and line break^$@ \r" +
                "in \\1/ file;\r" +
                ":-)";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

}