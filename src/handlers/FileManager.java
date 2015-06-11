package handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class FileManager {
	
	private Formatter formatter;
	private Scanner scanner;
	
	private String text;
	
	public FileManager(String text) {
		this.text = text;
	}
	
	public void writeToFile(String s) {
		// opens the file
		try {
			formatter = new Formatter(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//writes on file
		formatter.format(s);
		//closes the file
		formatter.close();
	}
	
	public ArrayList<String> readFile() {
		//Creates the file
		try {
			scanner = new Scanner(new File(text));
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Can't find that file!");
		}
		//reads the file
		ArrayList<String> data = new ArrayList<String>();
		while(scanner.hasNext()) {
			data.add(scanner.next());
		}
		//closes the file
		scanner.close();
		
		return data;
	}

}
