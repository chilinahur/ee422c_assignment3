/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 *
 * Sriram Chilukuri
 * smc4474
 * 16445
 * Yousef Abdelrazzaq
 * Yja87
 * 16445
 * Slip days used: <0>
 * Git URL:https://github.com/chilinahur/ee422c_assignment3
 * Fall 2016
 */


//package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	//	static Stack<String> s = new Stack<String>();
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		//parse(kb);
		//String[] wordArray = getAllMutants("start");
		/*for(int i = 0; i < wordArray.length; i++){
			System.out.println(wordArray[i]);
		}*/
		/*
		 * 		initialize();
		ArrayList<String> inputVals = parse(kb);	//get input
		getWordLadderDFS(inputVals.get(0), inputVals.get(1));	//output DFS
		getWordLadderBFS(inputVals.get(0), inputVals.get(1));	//output BFS
		 */
		ArrayList<String> ladder = getWordLadderDFS("cones", "smart");
		System.out.println(ladder.toString());
		System.out.println(ladder.size());
		
		// TODO methods to read in words, output ladder
	}
	private static String[] getAllMutants(String input, Set<String> dictionary){
		String[] iterations = new String[input.length() * 25];
		int count = 0;
		for(int i = 0; i<input.length(); i++){
			StringBuilder temp = new StringBuilder(input);
			char startVal = input.charAt(i);
			for(char x = 'a'; x<='z'; x++){
				temp.setCharAt(i, x);
			if(startVal != temp.charAt(i) && dictionary.contains(temp.toString().toUpperCase())){
				iterations[count] = temp.toString();
				count++;
			}
			}
		}
		return iterations;	
	}
	/*
	public static String[] getAllMutants(String word){
		
			int length = word.length();
			String[] wordArray = new String[130];
			String[] originalArray = new String[130];
			String[] charArray = new String[length];
			
			for(int i = 0; i < length; i++){
				charArray[i] = Character.toString(word.charAt(i));
			}
			System.arraycopy(charArray, 0, originalArray, 0, length);
			//return array;
			int k = 0;
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 26; j++){		
					char x = (char) ('a' + j);
					charArray[i] = Character.toString(x);
					wordArray[k] = (charArray[0] + charArray[1] + charArray[2] + charArray[3] + charArray[4]);
					k++;
				}
				charArray[i] = originalArray[i];
			}
			
			Set <String> dict = makeDictionary();
			String[] finalArray = new String[50];
			
			int count = 0;
			for(int i = 0; i < wordArray.length; i++){
				if(dict.contains(wordArray[i].toUpperCase()) && !wordArray[i].equals(word)){
					finalArray[count] = wordArray[i];
					count++;
				}
			}
			
			return finalArray;
		
	}
	*/
	
	public static ArrayList<String> recDFS(ArrayList<String> visited, String start, String end){
		
		ArrayList<String> ladder = new ArrayList<String>();
		String[] mutants = getAllMutants(start);
		visited.add(start);
		
		if(start.equals(end)){
			ladder.add(end);
			return ladder;
		}
		
		ladder.add(start);
		
		for(int i = 0; i < mutants.length; i++){
				if(!visited.contains(mutants[i]) && mutants[i] != null){
					ladder = recDFS(visited, mutants[i], end);
				}
				if(ladder != null && ladder.get(0).equals(end)){
					ladder.add(start);
					return ladder;
				}
		}
		
		return null;
		
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		
		ArrayList<String> inputList = new ArrayList<String>();
		String val = keyboard.nextLine();
		if(val == "/quit"){
			return inputList;
		}
		inputList.add(0, val);
		inputList.add(1, keyboard.nextLine());
		//System.out.println(Arrays.toString(inputList.toArray()));
		return inputList;
	}
	
	/*
	 * 	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		s.push(start);
		String[] starterVals = getAllMutants(start, dict);
		for(int i = 0; i < starterVals.length; i++){		//takes into account all iterations
			if(starterVals[i] != null && starterVals[i] != end && !s.contains(starterVals[i]) ){
			getWordLadderDFS(starterVals[i], end);
			}
			else if(starterVals[i]==end){
				break;
			}
			else if(starterVals[i]==null){
				break;
			}
			else if(s.contains(starterVals[i])){
				break;
			}
		}
		if(!s.contains(end)){
			s.pop();
		}
		// TODO more code
		
		return null; // replace this line later with real return
	}
	 */
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		
		Set<String> dict = makeDictionary();
		
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<String> ladder = new ArrayList<String>();
		
		ladder = recDFS(visited, start, end);

		
		// TODO more code
		
		if(ladder == null){
			System.out.println("No such ladder");
			ladder.add("empty");
			return ladder;
		}
		
		//System.out.println("WE'RE DONE");
		return ladder; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	// TODO
	// Other private static methods here
}
