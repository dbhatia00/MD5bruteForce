//FOR ONLINE JAVA RUNNER, VISIT https://www.tutorialspoint.com/compile_java_online.php
//ENTER HASH INTO STDIO AND EXECUTE
//Local breaking can be done with up to 4-letter words efficiently
//Online breaking can be done with up to 3-letter words effectively

import java.security.MessageDigest;
import java.util.Scanner;

public class bruteForce
{
	MessageDigest md;
	
	char min_char_value;
	char max_char_value;
	
	char[] guess;
	
	int max_num_chars;

	
	public bruteForce() throws Exception {
		min_char_value = 32;
		max_char_value = 126;
		max_num_chars = 10;
		
		md = MessageDigest.getInstance("MD5");
		
		guess = null;
	}
	
	public String crack(String hash){
		boolean done = false;
		String guess_hash;
			
		for(int num_chars = 0; num_chars < max_num_chars && !done; num_chars++){
			// Initialize guess at the start of each interation
			guess = new char[num_chars];
			for(int x = 0; x < num_chars; x++){
				guess[x] = min_char_value;
			}
			
			while(canIncrementGuess() && !done){
				incrementGuess();
				md.reset();
				md.update(new String(guess).getBytes());
				guess_hash = hashToString(md.digest());
	
				if(hash.equals(guess_hash)){
					done = true;
				}
			}
		}
		return new String(guess);
	}
	
	protected boolean canIncrementGuess(){
		boolean canIncrement = false;
		
		for(int x=0; x < guess.length; x++){
			if(guess[x] < max_char_value) canIncrement = true;
		}
		return canIncrement;
	}
	
	protected void incrementGuess(){
		boolean incremented = false;

		for(int x = (guess.length - 1);x >= 0 && !incremented; x--){	
			if(guess[x] < max_char_value){
				guess[x]++;
				if(x < (guess.length-1)){
					guess[x+1] = min_char_value;
				}
				incremented = true;
			}
		} 
	}
	
	protected String hashToString(byte[] hash){
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < hash.length; i++){
			sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	public static void main(String args[]){
			try{
				bruteForce bc = new bruteForce();
				long start;
				long end;
				String answer;
				Scanner scan = new Scanner(System.in);

				System.out.println("Enter Hash: ");
    			String hash = scan.nextLine();

				start = System.nanoTime();
				answer = bc.crack(hash);
				end = System.nanoTime();
			
				System.out.println("Cracked Password: " + answer);
				System.out.println("Processing Time: " + ((end - start)/1000000000) + " seconds");
				scan.close();
			}
			catch(Exception e){
				System.out.println("Exception: " + e.toString());
			}
		
	}
}