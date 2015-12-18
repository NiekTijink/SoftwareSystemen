package ss.week6.dictionaryattack;

import java.util.*;
import java.io.*;
import java.security.*;
import org.apache.commons.codec.binary.Hex;



public class DictionaryAttack {
	private Map<String, String> passwordMap;
	private Map<String, String> hashDictionary;
	private BufferedReader reader;	
    private static final String PATH = "C:\\SS home\\Workspace Eclipse\\SoftwareSystemen2\\bin\\ss\\week6\\test\\"; //Your path to the test folder

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
	 */
	public String getPasswordHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
    	return Hex.encodeHexString(md.digest(password.getBytes()));
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
    			BufferedReader br = new BufferedReader(new FileReader(filename));
    			Scanner scanner = new Scanner(br);
    			while (scanner.hasNext()) {
    				String line = scanner.nextLine();
    				hashDictionary.put(getPasswordHash(line),line);
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
		Iterator<String> it = passwordMap.keySet().iterator();
		int counter = 0;
		while (it.hasNext()) {
			String name = it.next();
			String hashString = passwordMap.get(name);
			if (hashDictionary.containsKey(hashString)) {
				counter++;
				System.out.println(name + ": " + hashDictionary.get(hashString));
			}
		}
		System.out.println(counter);

	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		DictionaryAttack da = new DictionaryAttack();
		da.readPasswords(PATH + "LeakedPasswords.txt");
		da.addToHashDictionary(PATH + "commonpasswords.txt");
		da.doDictionaryAttack();
	}

}
