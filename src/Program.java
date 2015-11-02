import java.io.*;

/**
 * Created by Jason on 10/30/2015.
 */
public class Program {

    MyLinkedList<MyLinkedList<String>> dictionary;
    long wordsFound = 0;
    long wordsNotFound = 0;
    long compsWordsFound = 0;
    long compsWordsNotFound = 0;

    /**
     * The entry point of the program
     * @param args Any command line arguments. This program does not use any
     */
    public static void main(String[] args)
    {
        (new Program()).run();
    }

    /**
     * The program's main method
     */
    public void run()
    {
        dictionary = new MyLinkedList<>();
        prepareLinkedList();
        readDictionaryIntoLinkedList();
        spellCheck();
        double avgComparisonsFound = compsWordsFound / wordsFound;
        double avgComparisonsNotFound = compsWordsNotFound / wordsNotFound;
        System.out.println("Words Found: " + wordsFound);
        System.out.println("Words Not Found: " + wordsNotFound);
        System.out.println("Average comparisons of Words Found: " + avgComparisonsFound);
        System.out.println("Average comparisons of Words Not Found: " + avgComparisonsNotFound);
    }

    /**
     * Reads the dictionary into the list. requires the list to be filled with 26 linked lists
     * @
     */
    private void readDictionaryIntoLinkedList()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("random_dictionary.txt")));
            String line = reader.readLine();

            while(line != null)
            {

                line = line.trim();
                char firstLetter = line.toUpperCase().toCharArray()[0];
                int index = ((int)firstLetter) - 65;
                dictionary.get(index).add(line.toUpperCase());
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1); // could not find file or read from it
        }
    }

    /**
     * Fills the linked list with 26 linked lists for the characters
     */
    private void prepareLinkedList()
    {
        for(int i =0; i < 26; i++)
        {
            dictionary.addLast(new MyLinkedList<>());
        }
    }

    /**
     * The spell check had to be written a little bit differently compared to Assignment 2 in order to take advantage of the link list
     * Spell Checks the document
     */
    private void spellCheck()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("oliver.txt")));
            String line = reader.readLine();
            int count = 0;
            while(line != null)
            {
                String[] words = line.split("\\s+");

                for(String word : words)
                {
                    word = word.trim();
                    searchDictionary(word);
                }
                count++;
                if((count % 1000) == 0)
                {
                    System.out.println(count); // get a progress on the spell check but don't do it so often that it slows down the parser
                }
                line = reader.readLine();

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1); // could not find file or read from it
        }
    }

    /**
     * Searches the dictionary Linked List for a word
     * @param word The word to be searched
     */
    private void searchDictionary(String word)
    {
        if(word.length() <= 0) // not a word
        {
            return;
        }
        word = word.toUpperCase();
        int index = ((int)word.toCharArray()[0]) - 65;
        dictionary.stringComparisons = 0;
        if(index < 0 || index > 25) // not a word
        {
            return;
        }
        if(dictionary.get(index).contains(word))
        {
            compsWordsFound += dictionary.stringComparisons;
            wordsFound++;
        }
        else
        {
            compsWordsNotFound += dictionary.stringComparisons;
            wordsNotFound++;
        }
    }


}
