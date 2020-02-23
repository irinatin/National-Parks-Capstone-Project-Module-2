package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MenuNew {
	
	private PrintWriter out;
	private Scanner in = new Scanner(System.in);
	
	
	//construtor
	public MenuNew(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	
	

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	
	

	public Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption <= 0) {
				out.println("\n*** " + userInput + " is not a valid option!!! ***");
				return choice;
			}
			if (selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}

		} catch (NumberFormatException e) { // eat the exception, an error message will be displayed below since choice will be null

		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option!!! ***");
		}
		
		return choice;
	}
	
	

	private void displayMenuOptions(Object[] options) {
		System.out.println();
		
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			System.out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	

	public String getUserInput() {
		String input = this.in.nextLine();
		return input;
	}

}
