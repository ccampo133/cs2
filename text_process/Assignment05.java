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
        System.out.println("\nBRUTE FORCE SEARCH:\n===================");
        bruteForceSearch(text, pattern);
        
        System.out.println("\nKMP SEARCH:\n===========");
        kmpSearch(text, pattern);
        
        System.out.println("\nBOYER-MOORE SEARCH:\n===================");
        boyerMooreSearch(text, pattern);
        
        // print character frequencies
        System.out.println("\nCHARACTER FREQUENCY LISTING:\n============================");
        Map<Character, Integer> freqs = getCharFreqs(text);
        int tot = 0;
        for(char c : freqs.keySet())
        {
            String s = Character.toString(c); // for formatting
            if(c == '\n') s = "\\n";
            int freq = freqs.get(c);
            System.out.printf("%s: %d\n", s, freq);
            tot += freq;
        }
        System.out.printf("Total Frequency: %d\n", tot);
        
        // encode the text
        System.out.println("\nENCODED TEXT:\n=============");
        String encoded = huffmanEncode(text);
        System.out.println(encoded);
        
        // decode the text
        System.out.println("\nDECODED TEXT:\n=============");
        String decoded = huffmanDecode(encoded, freqs);
        System.out.println(decoded);        
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
    
    /**************************
     * TEXT SEARCH ALGORITHMS *
     **************************/
    
    // simple brute force text search algorithm.
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
        int i   = start;
        int cmp = 0;
        while(i <= text.length() - pattern.length())
        {
            int j = 0;
            while(j < pattern.length() && text.charAt(i+j) == pattern.charAt(j))
            {
                cmp++; 
                j++;
            }
            if(j == pattern.length())
            {
                System.out.printf("PATTERN FOUND AT INDEX %4d AFTER %4d COMPARISONS.\n", i, cmp);
                return i; 
            }
            cmp++;
            i++;
        }
        return -1; // no match
    }
     
    // knuth-morris-pratt algorithm    
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
    
    // boyer-moore algorithm for multiple occurances
    public static void boyerMooreSearch(String text, String pattern)
    {
        int i = -1;
        do
        {
            i = boyerMooreSearch(text, pattern, i+1);
        }while(i != -1);
    }
    
    // boyer-moore text searching algorithm
    public static int boyerMooreSearch(String text, String pattern, int start)
    {
        Map<Character, Integer> last = lastOccuranceFunc(pattern);
        int cmp = 0;
        int   i = start + pattern.length() - 1;
        int   j = pattern.length() - 1;
        
        // loop over entire text
        while(i <= text.length() - 1)
        {
            cmp++;
            if(text.charAt(i) == pattern.charAt(j))
            {
                if(j == 0)
                {
                    System.out.printf("PATTERN FOUND AT INDEX %4d AFTER %4d COMPARISONS.\n", i, cmp);
                    return i; // match at i
                }
                else
                {
                    i--;
                    j--;
                }
            }
            else    // character jump
            {
                int l = -1;
                if(last.containsKey(text.charAt(i)))
                    l = last.get(text.charAt(i));
                i = i + pattern.length() - Math.min(j, 1+l);
                j = pattern.length() - 1;
            }
        }
        return -1;  // no match
    }
    
    // last occurance function
    public static Map<Character, Integer> lastOccuranceFunc(String pattern)
    {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        // find rightmost occurance of letter; map to integers
        for(int i = pattern.length()-1; i >= 0; i--)
        {
            char c = pattern.charAt(i);
            if(!map.containsKey(c))
                map.put(c, i);
        }
        return map;
    }
    
    /*******************************
     * HUFFMAN ENCODING ALGORITHMS *
     *******************************/
    // node class for huffman encoding tree
    public static class Node implements Comparable<Node>
    {
        private final char ch;
        private final int freq;
        private final Node left, right;
       
        Node(char ch, int freq, Node left, Node right)
        {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }
       
        // returns true if the node is a leaf node; false otherwise
        public boolean isLeaf()
        {
            return (this.left == null && this.right == null);
        }
       
        // used for priority queue comparison
        public int compareTo(Node other)
        { 
            return Integer.compare(this.freq, other.freq);
        }
    }
     
    // encodes some text using the huffman encoding algorithm, and then 
    // returns the encoded text as a string.
    public static String huffmanEncode(String text)
    {
        // get character frequency and optimal coding tree
        Map<Character, Integer> freqs = getCharFreqs(text);
        Node root = buildHuffmanTree(freqs);

        // generate huffman code for all characters
        Map<Character, String> codes = new HashMap<Character, String>();
        genHuffmanCode(codes, root, "");
        
        // output huffman code for each character of the text
        String encoded = "";
        for(char c : text.toCharArray())
            encoded += codes.get(c);
        return encoded;
    }
     
    // decodes huffman encoded text. inverse of huffmanEncode
    public static String huffmanDecode(String encoded, Map<Character, Integer> freqs)
    {
        // get optimal coding tree
        Node root = buildHuffmanTree(freqs);

        // traverse tree and decode, starting at the root
        String decoded = "";
        Node   curNode = root;
        for(char c : encoded.toCharArray())
        {
            // 1 goes left, 0 goes right
            if(c == '1')
                curNode = curNode.left;
            else
                curNode = curNode.right;
            if(curNode.isLeaf())
            {
                decoded += curNode.ch;
                curNode  = root;
            }
        }
        return decoded;
    }
     
    // compute character frequencies in the text
    public static Map<Character, Integer> getCharFreqs(String text)
    {
        Map<Character, Integer> freqs = new HashMap<Character, Integer>();
        for(char c : text.toCharArray())
        {
            int freq = 0;
            if(freqs.containsKey(c))
                freq = freqs.get(c);
            freqs.put(c, freq+1);   // increment by one for each new appearance
        }   
        return freqs;
    }
     
    // builds optimal huffman coding tree
    public static Node buildHuffmanTree(Map<Character, Integer> freqs)
    {
        PriorityQueue<Node> heap = new PriorityQueue<Node>();
        
        // create min heap with nodes for each character
        for(char c : freqs.keySet())
            heap.add(new Node(c, freqs.get(c), null, null));
        
        // create optimal tree
        while(heap.size() > 1)
        {
            Node right  = heap.poll();
            Node left   = heap.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            heap.add(parent);
        }
        return heap.poll();
    }
     
    // generate the huffman code for the characters
    public static void genHuffmanCode(Map<Character, String> codes, Node node, String code)
    {
        if(!node.isLeaf())
        {
            // traverse and increment left code by 1, right code by 0
            genHuffmanCode(codes, node.left,  code + "1");
            genHuffmanCode(codes, node.right, code + "0");
        }
        else
            codes.put(node.ch, code);
    }
}