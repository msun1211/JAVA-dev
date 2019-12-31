package edu.gatech.seclass.encode;
import java.io.*;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.lang.*;

public class Main {

    /**
     * This is a Georgia Tech provided code template for use in assigned private GT repositories. Students and users of this template
     * code are advised not to share it with other students or to make it available on publicly viewable websites including
     * repositories such as github and gitlab. Such sharing may be investigated as a GT honor code violation. Created for CS6300.
     *
     * Empty Main class for starting the Individual Project.
     */

    public static void main(String[] args) {
        // Empty Skeleton Method
        // If args length <= 0
        if (args.length <= 0) {
            usage();
            return;
        }
        // arg length
        int args_len = args.length;
        String file_name = args[args_len - 1];
        File myFile = new File(file_name);

        // If no file exists
        if (!(myFile.exists() )) {
            System.err.println("File Not Found");
            return;
        }
        String file_content = getFileContent(args[args_len - 1]);



        if (file_content.length()==0){
            // if there is no content in the file, then just return the original file
            return;
        }

        //oldstring in the original file
        String oldstring = "";

        try {
            FileReader FR = new FileReader(file_name);
            BufferedReader reader = new BufferedReader(FR);


            int c ;
            StringBuilder buildText = new StringBuilder();
            while ((c = reader.read()) != -1) {
                char character = (char) c;
                buildText.append(character);
            }

            oldstring = buildText.toString();
            FR.close();
            reader.close();

        } catch (IOException ioe) {
            usage();
            return;
        }

        //set new string to be empty
        String newstring = "";

        //set has_OPT
        boolean has_OPT = false;
        List<String> OPT_list = Arrays.asList("-a", "-c", "-r", "-k", "-l");
        for (int i=0; i < args_len-1; i++) {
            if (OPT_list.contains(args[i])) {
                has_OPT = true;
                break;
            }
        }

        //if starts with - but not a one in OPT
        boolean bad_opt = false;
        for (int i=0; i < args_len-1; i++) {
            String s_arg = args[i];
            if (s_arg.startsWith("-") && s_arg != "-k" && s_arg != "-l" && s_arg != "-r" && s_arg != "-a" && s_arg != "-c") {
                bad_opt = true;
            }
        }
        if(bad_opt){usage();return;}


        if (!has_OPT && !bad_opt){
            newstring = reverseAll(oldstring);
            try {

                FileWriter FW = new FileWriter(file_name);

                FW.write(newstring);

                FW.close();
                return;

            } catch (IOException ioe) {
                usage();
                return;
            }
        }

        boolean has_String = false;
        for (int i=0; i < args_len-1; i++) {
            if (!OPT_list.contains(args[i])) {
                has_String = true;
                break;
            }
        }



        if (!has_String) {
            if ( (Arrays.asList(args).contains("-k") || Arrays.asList(args).contains("-r")) && (!Arrays.asList(args).contains("-a")) && (!Arrays.asList(args).contains("-l"))) {
                usage();
                return;
            }
        }

        //set the length
        int length = 0;

        //Also need to set the string after -a
        int index = 0;
        boolean a_2 = false;

        for (int i=0; i < args_len-1; i++) {
            if (args[i] == "-a") {
                a_2 = true;
                index = i;
            }
        }
        String alpha_str = "";
        if (a_2 && index+1 == args_len-1){}
        else{
            for (int i=index+1;i < args_len-1; i++) {
                if (!isInterger(args[i]) && (!OPT_list.contains(args[i])) && (args[i-1]!="-r")) {
                    alpha_str = args[i];
                }
            }
        }



        //both OPT and string
        boolean Select_a = false;
        boolean Select_c = false;
        boolean Select_k = false;
        boolean Select_r = false;
        boolean Select_l = false;


        //check if both r and k in args
        boolean has_r = false;
        boolean has_k = false;
        for (int i=0; i < args_len-1; i++) {
            if (args[i]== "-r" ) {
                has_r = true;
            }
            if (args[i] == "-k") {
                has_k = true;
            }
        }
        if (has_r && has_k){usage(); return;}

        //there is string but still possible after c k r there is no alphanumeric string
        index = 0;
        boolean r_2 = false;
        for (int i=0; i < args_len-1; i++) {
            if (args[i] == "-r") {
                r_2 = true;
                index = i;
            }
        }
        boolean r_str_valid = false;
        if (r_2 && index+1 == args_len-1){usage();return;}
        else{
            for (int i=index+1;i < args_len-1; i++) {
                String s_arg = args[i];
                for (int j = 0; j < s_arg.length(); j++) {
                    if (Character.isDigit(s_arg.charAt(j)) || Character.isLetter(s_arg.charAt(j))) {
                        r_str_valid = true;
                    }
                }
            }
        }

        if(r_2 && !r_str_valid){usage();return;}

        index = 0;
        boolean c_2 = false;
        for (int i=0; i < args_len-1; i++) {
            if (args[i] == "-c") {
                c_2 = true;
                index = i;
            }
        }
        boolean c_str_valid = false;
        if (c_2 && index+1 == args_len-1){usage();return;}
        else{
            for (int i=index+1;i < args_len-1; i++) {
                String s_arg = args[i];
                for (int j = 0; j < s_arg.length(); j++) {
                    if (Character.isLetter(s_arg.charAt(j))) {
                        c_str_valid = true;
                    }
                }
            }
        }

        if(c_2 && !c_str_valid){usage();return;}


        index = 0;
        boolean k_2 = false;
        for (int i=0; i < args_len-1; i++) {
            if (args[i] == "-k") {
                k_2 = true;
                index = i;
            }
        }
        boolean k_str_valid = false;
        if (k_2 && index+1 == args_len-1){usage();return;}
        else{
            for (int i=index+1;i < args_len-1; i++) {
                String s_arg = args[i];
                for (int j = 0; j < s_arg.length(); j++) {
                    if (Character.isDigit(s_arg.charAt(j)) || Character.isLetter(s_arg.charAt(j))) {
                        k_str_valid = true;
                    }
                }
            }
        }

        if(k_2 && !c_str_valid){usage();return;}

        //If just -a there is actually a case
        if (args.length==2 && args[0]=="-a")
        {newstring = encode(length, oldstring, "");}

        boolean restore = false;
        //Other non edge cases
        for (int i=0; i < args_len-1; i++) {
            if (args[i].equals("-a"))
            {Select_a = true;}
            else if (args[i].equals("-c"))
            {Select_c = true;}
            else if (args[i].equals("-l"))
            {Select_l = true;}
            else if (args[i].equals("-k") & (!Select_r))
            {Select_k = true;}
            else if (args[i].equals("-r") & (!Select_k))
            {Select_r  = true;}

            else {
                if (Select_a && !restore) {
                    if (Select_l && has_String) {
                        printSt(file_name, args[i]);
                    }
                    if (Select_l && (!has_String)) {
                        printSt(file_name, "");
                    }
                    if (Select_c && newstring.length() > 0) {
                        newstring = reverse(newstring, args[i]);
                    }
                    if (Select_c && newstring.length() == 0) {
                        newstring = reverse(oldstring, args[i]);
                    }
                    if (Select_k && newstring.length() > 0) {
                        newstring = keepStr(newstring, args[i]);
                    }
                    if (Select_k && newstring.length() == 0) {
                        newstring = keepStr(oldstring, args[i]);
                    }
                    if (Select_r && newstring.length() > 0) {
                        newstring = removeStr(newstring, args[i]);
                    }
                    if (Select_r && newstring.length() == 0) {
                        newstring = removeStr(oldstring, args[i]);
                    }
                    if (isInterger(args[i]) && newstring.length() > 0 && args[i - 1] != "-r" && args[i - 1] != "-k" && args[i - 1] != "-c" && args[i - 1] != "-l") {
                        length = Integer.parseInt(args[i]);
                        newstring = encode(length, newstring, alpha_str);
                    }
                    if (isInterger(args[i]) && newstring.length() == 0 && args[i - 1] != "-r" && args[i - 1] != "-k" && args[i - 1] != "-c" && args[i - 1] != "-l") {
                        length = Integer.parseInt(args[i]);
                        newstring = encode(length, oldstring, alpha_str);
                    }
                    if (isInterger(args[i])  && newstring.length() > 0 && (args[i - 1] == "-r" || args[i - 1] == "-k" || args[i - 1] == "-c" || args[i - 1] == "-l")) {
                        newstring = encode(length, newstring, alpha_str);
                    }
                    if (isInterger(args[i])  && newstring.length() == 0 && (args[i - 1] == "-r" || args[i - 1] == "-k" || args[i - 1] == "-c" || args[i - 1] == "-l")) {
                        newstring = encode(length, oldstring, alpha_str);
                    }
                    if (!isInterger(args[i]) && newstring.length() > 0) {
                        newstring = encode(length, newstring, alpha_str);
                    }
                    if (!isInterger(args[i]) && newstring.length() == 0) {
                        newstring = encode(length, oldstring, alpha_str);
                    }

                    restore = true;
                } else if (restore) {
                    newstring = oldstring;
                    if (Select_l && has_String) {
                        printSt(file_name, args[i]);
                    }
                    if (Select_l && (!has_String)) {
                        printSt(file_name, "");
                    }
                    if (Select_c && newstring.length() > 0) {
                        newstring = reverse(newstring, args[i]);
                    }
                    if (Select_c && newstring.length() == 0) {
                        newstring = reverse(oldstring, args[i]);
                    }
                    if (Select_k && newstring.length() > 0) {
                        newstring = keepStr(newstring, args[i]);
                    }
                    if (Select_k && newstring.length() == 0) {
                        newstring = keepStr(oldstring, args[i]);
                    }
                    if (Select_r && newstring.length() > 0) {
                        newstring = removeStr(newstring, args[i]);
                    }
                    if (Select_r && newstring.length() == 0) {
                        newstring = removeStr(oldstring, args[i]);
                    }
                    if (isInterger(args[i]) && Select_a && newstring.length() > 0 && args[i - 1] != "-r" && args[i - 1] != "-k" && args[i - 1] != "-c" && args[i - 1] != "-l") {
                        length = Integer.parseInt(args[i]);
                        newstring = encode(length, newstring, alpha_str);
                    }
                    if (isInterger(args[i]) && Select_a && newstring.length() == 0 && args[i - 1] != "-r" && args[i - 1] != "-k" && args[i - 1] != "-c" && args[i - 1] != "-l") {
                        length = Integer.parseInt(args[i]);
                        newstring = encode(length, oldstring, alpha_str);
                    }
                    if (isInterger(args[i]) && Select_a && newstring.length() > 0 && (args[i - 1] == "-r" || args[i - 1] == "-k" || args[i - 1] == "-c" || args[i - 1] == "-l")) {
                        newstring = encode(length, newstring, alpha_str);
                    }
                    if (isInterger(args[i]) && Select_a && newstring.length() == 0 && (args[i - 1] == "-r" || args[i - 1] == "-k" || args[i - 1] == "-c" || args[i - 1] == "-l")) {
                        newstring = encode(length, oldstring, alpha_str);
                    }
                    if (!isInterger(args[i]) && Select_a && newstring.length() > 0) {
                        newstring = encode(length, newstring, alpha_str);
                    }
                    if (!isInterger(args[i]) && Select_a && newstring.length() == 0) {
                        newstring = encode(length, oldstring, alpha_str);
                    }
                }
                else
                    {if (Select_l && has_String) {
                    printSt(file_name, args[i]);
                }
                    if (Select_l && (!has_String)) {
                        printSt(file_name, "");
                    }
                    if (Select_c && newstring.length() > 0) {
                        newstring = reverse(newstring, args[i]);
                    }
                    if (Select_c && newstring.length() == 0) {
                        newstring = reverse(oldstring, args[i]);
                    }
                    if (Select_k && newstring.length() > 0) {
                        newstring = keepStr(newstring, args[i]);
                    }
                    if (Select_k && newstring.length() == 0) {
                        newstring = keepStr(oldstring, args[i]);
                    }
                    if (Select_r && newstring.length() > 0) {
                        newstring = removeStr(newstring, args[i]);
                    }
                    if (Select_r && newstring.length() == 0) {
                        newstring = removeStr(oldstring, args[i]);
                    }

                    Select_a = false;
                    Select_c = false;
                    Select_k = false;
                    Select_l = false;
                    Select_r = false;

                }
            }
        }

        if (newstring.length() >0){
            try {
                FileWriter FW = new FileWriter(file_name);

                FW.write(newstring);

                FW.close();
                return;

            } catch (IOException e) {
                usage();
                return;
            }
        }
        else{
            return;
        }
    }


    private static String encode(Integer length, String str, String substring) {

        char[] result = str.toCharArray();
        if (substring == "")
        { for (int i = 0; i < result.length; i++) {
            if (Character.isLetter(result[i])) {
                if (Character.isUpperCase(result[i])) {
                    result[i] = (char) ('A'  + 'Z' - Character.toUpperCase(encrpt(Character.toLowerCase(result[i]), length)));
                    result[i] = Character.toUpperCase(result[i]);
                } else {
                    result[i] = (char) ('a' + 'z' - encrpt((result[i]), length));
                    result[i] = Character.toLowerCase(result[i]);
                }
            }
        }
        }
        else{
            for (int i = 0; i < result.length; i++) {
                if (substring.toLowerCase().indexOf(Character.toLowerCase(result[i])) >= 0 & Character.isLetter(result[i])) {
                    if (Character.isUpperCase(result[i])) {
                        result[i] = (char) ('A'  + 'Z' - Character.toUpperCase(encrpt(Character.toLowerCase(result[i]), length)));
                        result[i] = Character.toUpperCase(result[i]);
                    } else {
                        result[i] = (char) ('a' + 'z' - encrpt((result[i]), length));
                        result[i] = Character.toLowerCase(result[i]);
                    }
                }
            }
        }
        String result_str1 = new String(result);
        return result_str1;
    }

    private static String reverse(String str, String substring) {

        char[] result = str.toCharArray();
        for (int i = 0; i < result.length; i++) {
            if (substring.toLowerCase().indexOf(Character.toLowerCase(result[i])) >= 0) {
                if (Character.isLowerCase(result[i])) {
                    result[i] = Character.toUpperCase(result[i]);
                } else if (Character.isUpperCase(result[i])) {
                    result[i] = Character.toLowerCase(result[i]);
                }
            }
        }
        String result_str2 = new String(result);
        return result_str2;

    }


    private static String reverseAll(String str)
    {
        char[] result = str.toCharArray();
        for(int i = 0; i < result.length; i ++)
        {
            if (Character.isLowerCase(result[i])) {
                result[i] = Character.toUpperCase(result[i]);
            }
            else if (Character.isUpperCase(result[i])) {
                result[i] = Character.toLowerCase(result[i]);
            }
        }
        String result_str3 = new String(result);
        return result_str3;
    }


    private static String removeStr(String str, String substring) {
        for (int i = 0; i < str.length();) {
            if ((Character.isLetter(str.charAt(i)) & substring.toLowerCase().indexOf(Character.toLowerCase(str.charAt(i)))>= 0) ) {
                String sub = String.valueOf(str.charAt(i));
                str = str.replace(sub, "");
            }
            else if (Character.isDigit(str.charAt(i)) && substring.indexOf(str.charAt(i))>=0)
            {String sub = String.valueOf(str.charAt(i));
                str = str.replace(sub, "");
            }
            else {i++;}
        }
        return str;
    }

    private static String keepStr(String str, String substring) {
        for (int i = 0; i < str.length();) {
            char thischa = str.charAt(i);
            if ((Character.isLetter(thischa) && (!(substring.toLowerCase().indexOf(Character.toLowerCase(str.charAt(i))) >= 0))) || (Character.isDigit(thischa) )) {
                String sub = String.valueOf(str.charAt(i));
                str = str.replace(sub, "");
            }
            else {i++;}
        }
        return str;
    }

    private static void printSt(String file_name, String substring) {
        String content = getFileContent(file_name);
        try{
            BufferedReader reader = new BufferedReader(new StringReader(content));
            String line = reader.readLine();
            while (line != null) {

                boolean contain = true;
                for (int i = 0; i < substring.length(); ) {
                    if (!(line.indexOf(substring.charAt(i)) >= 0)) {
                        contain = false;
                    }
                    {
                        i++;
                    }
                }
                if (contain == true) {
                    System.out.println(line);
                }
                line = reader.readLine();
            }
        }  catch (IOException e) {
            usage();
            return;
        }
    }

    private static String getFileContent(String file_name) {
        String content = null;
        try {
            Charset charset = StandardCharsets.UTF_8;
            content = new String(Files.readAllBytes(Paths.get(file_name)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static boolean isInterger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }



    public static Character encrpt(Character char_1, int shiftKey)
    {
        String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        int charPosition = ALPHABET.indexOf(char_1);
        int keyVal = (charPosition + shiftKey)%26;
        char replaceVal = ALPHABET.charAt(keyVal);
        return replaceVal;
    }



    private static void usage() {
        System.err.println("Usage: encode [-a [integer]] [-r string | -k string] [-c string] [-l string] <filename>");
    }

}