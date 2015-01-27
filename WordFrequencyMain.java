///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Word Frequency
// Files:            WordFrequencyMain.java, SimpleHashMap.java
// Semester:         CS367 Fall 2013
//
// Author:           Navneet Reddy
// CS Login:         navneet
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jason Tiedt
// CS Login:         jtiedt
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          N/A
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.*;
import java.util.*;

/*
 * Reads in a large text file and outputs the 11 most frequent words in the file.
 */
public class WordFrequencyMain {
    /*
     * Main method to read input from file and output frequent words.
     *
     * @param args Name of the text file to read from.
     */
	public static final void main(String[] args) {
		//Create a new simple hash map
		SimpleHashMap<String,Integer> hashMap = new SimpleHashMap<String,Integer>();

		//Checks whether exactly one command-line argument is given.
		if (args.length != 1)
		{
			System.out.println("Usage: java WordFrequencyMain FileName");
			System.exit(-1);
		}
		
		//Read the contents of the file
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String line;
			
			while ((line = br.readLine()) != null) 
			{
				String[] words = line.split(" ");
				
				for (String word: words) 
				{
					//Add the word to the map
					if (hashMap.get(word) == null)
						hashMap.put(word,1);
					else
						hashMap.put(word, hashMap.get(word) + 1);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: Cannot access input file");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Error: Input/Output Exception");
			System.exit(-1);
		}

		//List of all the entries in the map
		List<SimpleHashMap.Entry<String, Integer>> entries = hashMap.entries();
		//List of the keys of all the entries in the map
		List<String> wordList = new LinkedList<String>();
		//Temporary list of some of the entries in the map
		List<String> tempWordList = new LinkedList<String>();
		//List of the values of all the entries in the map
		List<Integer> values = new LinkedList<Integer>();
		//Iterator to iterate over the list of entries
		Iterator<SimpleHashMap.Entry<String, Integer>> entryIterator = entries.iterator();
		
		//Add all the values in the map to the list of values
		while (entryIterator.hasNext())
			values.add(entryIterator.next().getValue());

		Collections.sort(values);

		//Value of the current entry
		int value = 0;
		//Value of the previous entry
		int preValue = 0;
		
		//Go through the list of values and match them with their key
		for (int j = values.size() - 1; j >= 0; j--)
		{
			value = values.get(j);

			//Find the next lowest value
			if (value == preValue)
				continue;

			tempWordList = new LinkedList<String>();
			preValue = value;
			
			//Iterator to iterate over the list of entries
			Iterator<SimpleHashMap.Entry<String, Integer>> entryIterator2 = entries.iterator();
			
			//Find the entries with the given values
			while (entryIterator2.hasNext())
			{
				//Store the current entry
				SimpleHashMap.Entry<String, Integer> entry = entryIterator2.next();

				if (entry.getValue() == value)
					tempWordList.add(entry.getKey());
			}

			if (!values.isEmpty())
				value = values.get(values.size() - 1);

			//Sort the words into alphabetical order
			Collections.sort(tempWordList);
			wordList.addAll(tempWordList);

			if (wordList.size() >= 11)
				break;
		}
		
		//Store the number of words to be printed
		int words = 0;
		
		//Check the number of words to print
		if (wordList.size() > 11)
			words = 11;
		else
			words = wordList.size();
		
		//Print the top 11 words
		for (int i = 0; i < words; i++)
			System.out.println(wordList.get(i));
	}
}
