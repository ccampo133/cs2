// Chris Campo
// COP 3503C - 0001
// HW 5 - Text Processing
// 3/30/2012

import java.io.*;
import java.util.*;

public class Assignment05
{
    public static void main(String[] args)
    {
        char[] txt = getTxtArr("Assignment05.txt");
        
        Scanner scnr = new Scanner(System.in);
        System.out.print("Input pattern to find: ");
        String inp = scnr.nextLine();
    }
    
    // get all the text information as a character array
    public static char[] getTxtArr(String fname)
    {
        try
        {
            Scanner scnr = new Scanner(new File(fname));
            String lines = "";
            while(scnr.hasNextLine())
            {
                lines += scnr.nextLine() + "\n";
            }
            
            System.out.println("TEXT: \n");
            System.out.println(lines);
            
            char[] chars = lines.trim().toCharArray(); // trim removes last newline
            return chars;
        }
        catch(Exception ioe)
        { 
            System.out.println("Error: " + ioe.getMessage());
        }
        return null;
    }
}