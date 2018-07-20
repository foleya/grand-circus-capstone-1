package co.grandcircus.capstone1;

import java.util.Scanner;

public class PigLatinTranslator {

	public static void main(String[] args) {
		pigLatinTranslationLoop();
	}

	public static void pigLatinTranslationLoop() {
		String continueProgram;
		String userInput;
		Scanner scnr = new Scanner(System.in);

		// Greet the user
		System.out.println("Hello, welcome to the Pig Latin Translator!\n");

		// Loop program until user decides to quit
		do {
			System.out.print("Enter a word or line to be translated to Pig Latin: ");
			userInput = scnr.nextLine();

			System.out.println("\nIn Pig Latin, that's: ");
			System.out.println(translateLineToPigLatin(userInput));

			System.out.println("\nWould you like to translate another? (Y/n)");
			continueProgram = scnr.nextLine();

		} while (!continueProgram.equals("n"));

		// Say goodbye and close scanner
		System.out.println("\nOkayway! Oodbyegay!");
		scnr.close();

	}

	private static String translateLineToPigLatin(String line) {
		String[] toBeTranslated = line.split("[\\p{Punct}\\s]+");

		for (int index = 0; index < toBeTranslated.length; index++) {
			toBeTranslated[index] = translateWordToPigLatin(toBeTranslated[index]);
		}

		return String.join(" ", toBeTranslated);
	}

	private static String removeApostrophes(String word) {
		return word.replace("'", "");

	}

	private static String restoreFormerCase(String word, String formerCase) {
		switch (formerCase) {
		case "Uppercase":
			return word.toUpperCase();
		case "Lowercase":
			return word.toLowerCase();
		case "Titlecase":
			return word.substring(0, 1).toUpperCase() + word.substring(1);
		case "FuNkYcAsE":
			return word;
		default:
			return word;
		}
	}

	private static String translateWordToPigLatin(String word) {
		String vowels = "aeiou";
		String formerCase = checkWordCase(word);

		// If word has digits or special characters, do nothing
		if (!word.matches("[A-Za-z]+")) {
			return word;

		} else {
			// Force to Lower Case
			word = word.toLowerCase();

			// Remove Apostrophes
			word = removeApostrophes(word);

			// Iterate through string, looking for first vowel character
			for (int index = 0; index < word.length(); index++) {
				// Upon finding first vowel, use its index to translate the word
				// to pig latin, and break the for loop.
				if (vowels.contains(String.valueOf(word.charAt(index)))) {
					word = word.substring(index) + word.substring(0, index) + "ay";
					break;

				}

			}

			// Restore the word's former case
			word = restoreFormerCase(word, formerCase);
		}

		return word;
	}

	private static String checkWordCase(String word) {
		if (word.equals(word.toUpperCase())) {
			// upper case
			return "Uppercase";
		} else if (word.equals(word.toLowerCase())) {
			// lower case
			return "Lowercase";
		} else if (word.equals(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())) {
			// title case
			return "Titlecase";
		} else {
			// some kind of funky case situation
			return "FuNkYcAsE";
		}

	}

}
