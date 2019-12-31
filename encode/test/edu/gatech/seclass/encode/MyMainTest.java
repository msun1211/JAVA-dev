package edu.gatech.seclass.encode;

import static org.junit.Assert.*;

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

public class MyMainTest {

/*
Place all  of your tests in this class, optionally using MainTest.java as an example.
*/
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

    /*
     * TEST FILE CONTENT
     */
    private static final String FILE1 = "abcXYZ";
    private static final String FILE2 = "Howdy Billy,\n" +
            "I am going to take cs6300 and cs6400 next semester.\n" +
            "Did you take cs 6300 last semester? I want to\n" +
            "take 2 courses so that I will graduate Asap!";
    private static final String FILE3 = "abcXYZ123ABCxyz";
    private static final String FILE4 = "";
    private static final String FILE5 = "abcdef";
    private static final String FILE6 = "ABC def RRRMS 66!!";
    private static final String FILE7 = "Howdy Billy,\n";

    // test cases

    /*
     *   TEST CASES
     */

    // Purpose: To test when there are numbers in the input
    // Frame #: 4
    @Test
    public void encodeTest1() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {"12345x", inputFile.getPath()};
        Main.main(args);

        String expected = "ABCxyz123abcXYZ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test when the file is empty
    // Frame #: 6
    @Test
    public void encodeTest2() throws Exception {
        File inputFile = createInputFile(FILE4);

        String args[] = {"-c", "12345", inputFile.getPath()};
        Main.main(args);

        String expected = "";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test when all characters in file are lower case
    // Frame #: 10
    @Test
    public void encodeTest3() throws Exception {
        File inputFile = createInputFile(FILE5);

        String args[] = {"-a", "ab", inputFile.getPath()};
        Main.main(args);

        String expected = "zycdef";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }


    // Purpose: To test -a option with \n present in the file
    // Frame #: 12
    @Test
    public void encodeTest4() throws Exception {
        File inputFile = createInputFile(FILE2);

        String args[] = {"-a", "cs", inputFile.getPath()};
        Main.main(args);

        String expected =  "Howdy Billy,\n" +
                "I am going to take xh6300 and xh6400 next hemehter.\n" +
                "Did you take xh 6300 laht hemehter? I want to\n" +
                "take 2 xourheh ho that I will graduate Ahap!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -a option with all alphabetic characters in the file
    // Frame #: 15
    @Test
    public void encodeTest5() throws Exception {
        File inputFile = createInputFile(FILE1);

        String args[] = {"-a", "abcdef", inputFile.getPath()};
        Main.main(args);

        String expected = "zyxXYZ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -r option with both \n and blanks in the file. The characters have both lower and upper cases
    // Frame #: 21
    @Test
    public void encodeTest6() throws Exception {
        File inputFile = createInputFile(FILE2);

        String args[] = {"-r", "aDe", inputFile.getPath()};
        Main.main(args);

        String expected = "Howy Billy,\n" +
                "I m going to tk cs6300 n cs6400 nxt smstr.\n" +
                "i you tk cs 6300 lst smstr? I wnt to\n" +
                "tk 2 courss so tht I will grut sp!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -r option with both input string and file contains blanks
    // Frame #: 25
    @Test
    public void encodeTest7() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-r", "a R", inputFile.getPath()};
        Main.main(args);

        String expected = "BC def MS 66!!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -k option when there is \n in input string
    // Frame #: 30
    @Test
    public void encodeTest8() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-k", "r \n \n", inputFile.getPath()};
        Main.main(args);

        String expected = "  RRR !!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -k option when the input string contains blanks and file contains numbers
    // Frame #: 34
    @Test
    public void encodeTest9() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-k", "S ", inputFile.getPath()};
        Main.main(args);

        String expected = "  S !!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -c option when there are blanks in the input string and numbers in the file
    // Frame #: 42
    @Test
    public void encodeTest10() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-c", "input string", inputFile.getPath()};
        Main.main(args);

        String expected = "ABC def rrrMs 66!!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -k -c option and there is \n in the input string and the file only contains alphabetic characters
    // Frame #: 52
    @Test
    public void encodeTest11() throws Exception {
        File inputFile = createInputFile(FILE1);

        String args[] = {"-k", "-c", "a \n x", inputFile.getPath()};
        Main.main(args);

        String expected = "Ax";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -a -k -c option when there is \n in both input string and file content
    // Frame #: 60
    @Test
    public void encodeTest12() throws Exception {
        File inputFile = createInputFile(FILE2);

        String args[] = {"-a", "-k",  "-c", "a \n x", inputFile.getPath()};
        Main.main(args);

        String expected = " ,\n" +
                " Z   Z  Z  C .\n" +
                "  Z   Z ?  Z \n" +
                "Z    Z   ZZ zZ!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test -a -k -c option when blank in the input string and file is only with alphabetic characters
    // Frame #: 67
    @Test
    public void encodeTest13() throws Exception {
        File inputFile = createInputFile(FILE1);

        String args[] = {"-a", "-k", "-c", "   bxh", inputFile.getPath()};
        Main.main(args);

        String expected = "Yc";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test no option.  \n in the input string and there is number in the file
    // Frame #: 70
    @Test
    public void encodeTest14() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = { "b \n", inputFile.getPath()};
        Main.main(args);

        String expected = "ABCxyz123abcXYZ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }


    // Purpose: To test no input of the string
    // Frame #: 2
    @Test
    public void encodeTest15() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {inputFile.getPath()};
        Main.main(args);

        String expected = "ABCxyz123abcXYZ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: To test when the file not present
    // Frame # 1
    @Test
    public void encodeTest16() {
        String args[]= {"a.txt"};
        Main.main(args);
        assertEquals("File Not Found", errStream.toString().trim());
    }

    // Purpose: No alphabetic characters in the input string
    // Frame # 5
    @Test
    public void encodeTest17() throws Exception {
        File inputFile = createInputFile(FILE1);
        String args[] = {"-a", "23", inputFile.getPath()};

        Main.main(args);
        String expected = "cbaFED";

        String actual = getFileContent(inputFile.getPath());

         assertEquals("The files differ!", expected, actual);
     }

    // Purpose: String not present in the file
    // Frame # 7
    @Test
    public void encodeTest18() throws Exception {
        File inputFile = createInputFile(FILE1);

        String args[] = {"-r", "-c", "MS", inputFile.getPath()};
        Main.main(args);

        String expected = "abcXYZ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -a option. Input string has blank. Content of file has '\n'. Content has both upper and lower cases.
    // Frame # 16
    @Test
    public void encodeTest19() throws Exception {
         File inputFile = createInputFile(FILE7);

        String args[] = {"-a", "bk y ", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdb Yillb,\n";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -r option. Input string has blank. Content of file has numbres. Content has both upper and lower cases.
    // Frame # 26
     @Test
    public void encodeTest20() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {"-r", "aaa  ",  inputFile.getPath()};
        Main.main(args);

        String expected = "bcXYZ123BCxyz";

        String actual = getFileContent(inputFile.getPath());

         assertEquals("The files differ!", expected, actual);
     }


    // Purpose: Check -c option. Input string has \n. Content of file has \n. Content has both upper and lower cases.
    // Frame # 36
    @Test
    public void encodeTest21() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = {"-c", "h \n ",  inputFile.getPath()};
        Main.main(args);

        String expected = "howdy Billy,\n";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -a -r options together. Input string has \n. Content of file has numbers.
    // Frame # 46
    @Test
    public void encodeTest22() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {"-a", "-r", "b \n ",  inputFile.getPath()};
        Main.main(args);

        String expected = "zxCBA123ZXcba";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -a -r options together. Input string has blanks. Content of file has \n.
    // Frame # 48
    @Test
    public void encodeTest23() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = {"-a", "-r", "b  ",  inputFile.getPath()};
        Main.main(args);

        String expected = "Sldwb roob,\n";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
     }

     // Purpose: Check -a -r options together. Input string has blanks. Content of file has numbers.
    // Frame # 50
    @Test
    public void encodeTest24() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {"-a", "-r", "b  ",  inputFile.getPath()};
         Main.main(args);

        String expected = "zxCBA123ZXcba";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -k -c options together. Input string has \n. Content of file has all alphabetic characters.
    // Frame # 55
     @Test
    public void encodeTest25() throws Exception {
         File inputFile = createInputFile(FILE1);

         String args[] = {"-k", "abc", "-c", "bx \n",  inputFile.getPath()};
         Main.main(args);

         String expected = "aBc";

         String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -k -c options together. Input string has blanks. Content of file has \n.
    // Frame # 56
    @Test
    public void encodeTest26() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = {"-k", "-c", "bx ",  inputFile.getPath()};

        Main.main(args);

        String expected = " b,\n";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -a -k -c options together. Input string has blanks. Content of file has blanks.
    // Frame # 65
    @Test
    public void encodeTest27() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", "-k", "-c", "bx ", inputFile.getPath()};
        Main.main(args);

        String expected = "y   !!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check -a -k -c options together. Input string has blanks. Content of file has numbers.
    // Frame # 66
    @Test
    public void encodeTest28() throws Exception {
        File inputFile = createInputFile(FILE3);

        String args[] = {"-a", "-k", "-c", "bx ",  inputFile.getPath()};
        Main.main(args);

        String expected ="YcyC";

         String actual = getFileContent(inputFile.getPath());

         assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check no option. Input string has \n. Content of file has \n.
    // Frame # 68
    @Test
    public void encodeTest29() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = { "bx \n",  inputFile.getPath()};
        Main.main(args);

        String expected ="hOWDY bILLY,\n";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Check no option. Input string blanks. Content of file has all alphabetic characters.
    // Frame # 75
    @Test
    public void encodeTest30() throws Exception {
        File inputFile = createInputFile(FILE5);

        String args[] = { "bx ",  inputFile.getPath()};
        Main.main(args);

        String expected ="ABCDEF";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }


}




