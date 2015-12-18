package ss.week6.dictionaryattack;

import java.util.*;
import java.io.*;
import java.security.*;
import org.apache.commons.codec.binary.Hex;


public class DictionaryAttack {
	private Map<String, String> passwordMap;
	private Map<String, String> hashDictionary;
	private BufferedReader reader;
	private static final String PATH = "C://Users//Thomas//Desktop//Module TI//SS Home//Workspace//SoftwareSystemen//ExtraTest//src//ss//week6//test//";
	private static final String PATHPASS = "C://Users//Thomas//Desktop//Module TI//SS Home//Workspace//SoftwareSystemen//ExtraTest//src//ss//week6//test//MostUsedPasswords2013";
	/**
	 * Reads a password file. Each line of the password file has the form:
	 * username: encodedpassword
	 * 
	 * After calling this method, the passwordMap class variable should be
	 * filled withthe content of the file. The key for the map should be
	 * the username, and the password hash should be the content.
	 * @param filename
	 */
	public void readPasswords(String filename) {
		passwordMap = new HashMap<String, String>();   
		try {
			reader = new BufferedReader(new FileReader(filename));
			Scanner scanner = new Scanner(reader);
			while (scanner.hasNext()) {
				String[] line = scanner.nextLine().split(": ");
				passwordMap.put(line[0], line[1]);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Given a password, return the MD5 hash of a password. The resulting
	 * hash (or sometimes called digest) should be hex-encoded in a String.
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public String getPasswordHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte [] thedigest = md.digest(password.getBytes());
		return Hex.encodeHexString(thedigest);
	}
	/**
	 * Checks the password for the user the password list. If the user
	 * does not exist, returns false.
	 * @param user
	 * @param password
	 * @return whether the password for that user was correct.
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean checkPassword(String user, String password) throws NoSuchAlgorithmException {
        if (passwordMap.containsKey(user)) {
        	return (passwordMap.get(user).equals(getPasswordHash(password)));
        } 	
		return false;
	}

	/**
	 * Reads a dictionary from file (one line per word) and use it to add
	 * entries to a dictionary that maps password hashes (hex-encoded) to
     * the original password.
	 * @param filename filename of the dictionary.
	 * @throws NoSuchAlgorithmException 
	 */
    	public void addToHashDictionary(String filename) throws NoSuchAlgorithmException {
    		hashDictionary = new HashMap<String, String>();   
    		try {
    			reader = new BufferedReader(new FileReader(PATH + "MostUsedPasswordss.txt"));
    			Scanner scanner = new Scanner(reader);
    			while (scanner.hasNext()) {
    				String line = scanner.nextLine();
    				hashDictionary.put(getPasswordHash(line), line);
    			}
    			scanner.close();
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}        
    }
	/**
	 * Do the dictionary attack.
	 */
	public void doDictionaryAttack() {
		Integer count = 0;
		for (String key : passwordMap.keySet()) {
			if (hashDictionary.containsKey(passwordMap.get(key))){
				count++;
				System.out.println("Naam: " + key + ", wachtwoord: " + hashDictionary.get(passwordMap.get(key)));
			}
		}		
		System.out.println(count);
	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		DictionaryAttack da = new DictionaryAttack();
		da.readPasswords(PATH + "LeakedPasswords.txt");
		da.addToHashDictionary(PATH + "MostUsedPasswordss.txt");// To implement
		da.doDictionaryAttack();
	}

}