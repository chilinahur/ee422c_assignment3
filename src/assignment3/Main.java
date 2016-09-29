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


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	static class Node{
		String data;
		Node parent;
 		Node(String value, Node mom){
 			data=value;
 			parent = mom;
 		}
 	}	
	static String startGlobe;
	static String endGlobe;
	
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
		
		//our TEST code
		initialize();
		ArrayList<String> inputVals = parse(kb);	//get input
		startGlobe = inputVals.get(0);
		endGlobe = inputVals.get(1);
		//ArrayList<String> ladder = getWordLadderDFS(startGlobe, endGlobe);	//output DFS
		ArrayList<String> ladder = getWordLadderBFS(startGlobe, endGlobe);	//output BFS
		printLadder(ladder);
		
		// TODO methods to read in words, output ladder
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
		String val = keyboard.nextLine().toLowerCase();
		
		if(val.contains("/quit")){
			System.exit(0);
		}
		else{
			String[] val2 = val.split("\\s+");
			inputList.add(0, val2[0]);
			inputList.add(1, val2[1]);
		}
		return inputList;
	}
		
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
				
		Set<String> dict = makeDictionary();
		
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<String> ladder = new ArrayList<String>();

		ladder = recDFS(visited, start, end, dict);

		
		// TODO more code
		
		if(ladder == null){
			ArrayList<String> ladderError = new ArrayList<String>();
			return ladderError;
		}
		
		Collections.reverse(ladder);
		return ladder;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		Queue<Node> queue = new LinkedList<Node>();
		
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<String> ladder = new ArrayList<String>();
		
		Node parent = new Node(start, null);
		
		queue.add(parent);
		
		while(!queue.isEmpty()){
			
			Node pointer = queue.element();	//head of queue
			
			if(pointer.data == null){
				ArrayList<String> ladderError = new ArrayList<String>();
				return ladderError;
			}
			
			String[] mutants = getAllMutants(pointer.data, dict);
			
			while(visited.contains(pointer.data)){
				
					if(queue.isEmpty()){
						ArrayList<String> ladderError = new ArrayList<String>();
						return ladderError;
					}
					
					queue.remove();
					pointer = queue.element();
					mutants = getAllMutants(pointer.data, dict);
			}
			
			int length = mutants.length;
						
			for(int i = 0; i < length; i++){
				if(mutants[i] != null){
					Node child = new Node(mutants[i], pointer);
					if(!visited.contains(child.data)) queue.add(child);	
				}
			}
			
			if((queue.element().data).equals(end)){
				Node child = queue.element();
				Node mom = child.parent;
				
				while(mom != null){
					ladder.add(child.data);
					child = child.parent;
					mom = mom.parent;
				}
				ladder.add(start);
				Collections.reverse(ladder);
				return ladder;
			}
			visited.add(queue.element().data);
			queue.remove();
		}
		ArrayList<String> ladderError = new ArrayList<String>();
		return ladderError;
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
		if(ladder.isEmpty()){
			System.out.println("no word ladder can be found between " + startGlobe + " and " + endGlobe + ".");
		}
		else{
		int size = ladder.size();
		System.out.println("a "+ (size - 2) + "-rung word ladder exists between " + startGlobe + " and " + endGlobe + ".");
		for(int i = 0; i<(size); i++){
			System.out.println(ladder.get(i));
		}
		}
	}
	// TODO
	// Other private static methods here
	
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
	
	private static ArrayList<String> recDFS(ArrayList<String> visited, String start, String end, Set<String> dict){
		
		ArrayList<String> ladder = new ArrayList<String>();
		String[] mutants = getAllMutants(start, dict);
		visited.add(start);
		
		if(start.equals(end)){
			ladder.add(end);
			return ladder;
		}
		
		ladder.add(start);
		
		for(int i = 0; i < mutants.length; i++){
				if(!visited.contains(mutants[i]) && mutants[i] != null){
					ladder = recDFS(visited, mutants[i], end, dict);
				}
				if(ladder != null && ladder.get(0).equals(end)){
					ladder.add(start);
					return ladder;
				}
		}
		return null;	
	}
}