// Chris Campo
// COP 3503C - 0001
// HW 5 - Text Processing
// 3/30/2012

import java.io.*;
import java.util.*;
import java.lang.*;

public class Assignment05
{
    public static void main(String[] args)
    {
        // read text data
        System.out.println("TEXT:\n=====");
        String text = readTxtFile("Assignment05.txt");
        //String text = "abababab";
        System.out.println(text);
        
        // get pattern from user input
        System.out.println("\nPATTERN:\n========");
        Scanner   scnr = new Scanner(System.in);
        String pattern = scnr.nextLine();
        
        // run search algorithms        
        System.out.println("\nBRUTE FORCE SEARCH:\n===========");
        bruteForceSearch(text, pattern);
        
        System.out.println("\nKMP SEARCH:\n===========");
        kmpSearch(text, pattern);
        
       // boyerMooreSearch(text, pattern, 215);
    }
    
    // return all the text information as a string
    public static String readTxtFile(String fname)
    {
        try
        {
            Scanner scnr = new Scanner(new File(fname));
            String lines = "";
            while(scnr.hasNextLine())
            {
                lines += scnr.nextLine() + "\n";
            }
            return lines.trim();
        }
        catch(Exception ioe)
        { 
            System.out.println("Error: " + ioe.getMessage());
        }
        return null;
    }
    
    /*********************
     * SEARCH ALGORITHMS *
     *********************/
     
    // finds all occurances of pattern in text.
    public static void bruteForceSearch(String text, String pattern)
    {
        int i = -1;
        do
        {
            i = bruteForceSearch(text, pattern, i+1); 
        }while(i != -1);
    }
     
    // returns first occurance of pattern starting at index start.
     public static int bruteForceSearch(String text, String pattern, int start)
     {
        int m   = pattern.length();
        int n   = text.length();
        int i   = start;
        int cmp = 0;
        while(i <= n-m)
        {
            int j = 0;
            while(j < m && text.charAt(i+j) == pattern.charAt(j))
            {
                cmp++; 
                j++;
            }
            if(j == m)
            {
                System.out.printf("PATTERN FOUND AT INDEX %4d AFTER %4d COMPARISONS.\n", i, cmp);
                return i; 
            }
            cmp++;
            i++;
        }
        return -1; // no match
     }
     
    // KMP algorithm    
    // finds all occurances of pattern in the text, rather than the first 
    // occurance since some start index.
    public static void kmpSearch(String text, String pattern)
    {
        int i = -1;
        do
        {
            i = kmpSearch(text, pattern, i+1);
        }while(i != -1);
    }
    
    // returns index of the first occurance of pattern in the text, since 
    // index start.
    public static int kmpSearch(String text, String pattern, int start)
    {        
        int[] F   = kmpFailFunc(pattern, text.length());
        int   i   = start;
        int   j   = 0;
        int   cmp = 0;
        
        while(i < text.length())
        {
            cmp++;
            // compare current character
            if(text.charAt(i) == pattern.charAt(j))
            {
                if(j == pattern.length() - 1)
                {
                    System.out.printf("PATTERN FOUND AT INDEX %4d AFTER %4d COMPARISONS.\n", i-j, cmp);
                    return i-j;
                }
                else
                {
                    i++; 
                    j++;
                }
            }
            else
            {
                if(j > 0)
                    j = F[j-1]; // go to most recent occurance of prefix
                else
                    i++; 
            }
        }
        return -1; // no match
    }
    
    // computes and returns the kmp algorithm failure function
    public static int[] kmpFailFunc(String pattern, int size)
    {
        int[] F = new int[size];
        int   i = 1;
        int   j = 0;
     
        while(i < pattern.length())
        {
            if(pattern.charAt(i) == pattern.charAt(j))
            {
                F[i] = j + 1;
                i++;
                j++;
            }
            else if(j > 0)
                j = F[j-1];
            else
            {
                F[i] = 0;
                i++;
            }
        }
        return F;
    }
    
    // boyer-moore algorithm
    public static int boyerMooreSearch(String text, String pattern)
    {
        int[] last = lastOccuranceFunc(pattern);
        int cmp = 0;
        int i = pattern.length() - 1;
        int j = pattern.length() - 1;
        while(i <= text.length() - 1)
        {
            cmp++;
            if(text.charAt(i) == pattern.charAt(j))
            {
                if(j == 0)
                {
                    System.out.println(cmp);
                    return i; // match at i
                }
                else
                {
                    i--;
                    j--;
                }
            }
            else
            {
                int l = last[text.charAt(i)];
                i = i + pattern.length() - Math.min(j, 1+l);
                j = pattern.length() - 1;
            }
        }
        return -1;
    }
    
    // last occurance function
    public static int[] lastOccuranceFunc(String pattern)
    {
        int N = 256;
        int[] last = new int[N];
        
        // initialize to -1 and create map
        for(int i = 0; i < N; i++)
            last[i] = -1;
        for(int i = 0; i < pattern.length(); i++)
            last[pattern.charAt(i)] = i;
        return last;
    }
}