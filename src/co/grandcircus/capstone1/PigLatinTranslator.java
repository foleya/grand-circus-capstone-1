package co.grandcircus.capstone1;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PigLatinTranslator {

	public static void main(String[] args) {
		pigLatinTranslationLoop();
	}

	public static void pigLatinTranslationLoop() {
		String continueProgram;
		String userInput;
		final Scanner scnr = new Scanner(System.in);

		// Greet the user
		System.out.println("Hello, welcome to the Pig Latin Translator!\n");

		// Loop program until user decides to quit
		do {
			userInput = removeApostrophes(getValidInput(scnr));

			System.out.println("\nIn Pig Latin, that's: ");
			System.out.println(translateLineToPigLatin(userInput));

			System.out.println("\nWould you like to translate another? (Y/n)");
			continueProgram = scnr.nextLine();

		} while (!continueProgram.equals("n"));

		// Say goodbye and close scanner
		System.out.println("\nOkayway! Oodbyegay!");
		scnr.close();

	}

	// Validate that the user enters something
	private static String getValidInput(Scanner scnr) {
		boolean inputIsValid = false;
		String input = "";

		do {
			System.out.print("Enter a word or line to be translated to Pig Latin: ");
			input = scnr.nextLine();
			try {
				if (input.length() > 0) {
					inputIsValid = true;
				} else {
					System.out.println("\n -- You must enter something! --\n");
				}
			} catch (InputMismatchException ime) {
				System.out.println("\n -- You must enter something! --\n");
			}
		} while (!inputIsValid);

		return input;
	}

	// Remove apostrophes from user input
	private static String removeApostrophes(String word) {
		return word.replace("'", "");
	}

	// Translate a line to pig latin
	private static StringBuilder translateLineToPigLatin(String line) {
		StringBuilder translatedLine = new StringBuilder(line);
		Matcher matcher = Pattern.compile("[^\\p{Punct}\\s]+").matcher(line);
		int translationOffset = 0;

		while (matcher.find()) {
			String word = matcher.group();
			String translatedWord = translateWordToPigLatin(word);
			translatedLine.replace(matcher.start() + translationOffset,
								   matcher.end() + translationOffset,
								   translatedWord);
			if (!word.equals(translatedWord)) {
				if (word.charAt(0) == translatedWord.charAt(0)) {
					// Increase index-offset by 3 if word changed and added "way"
					translationOffset += 3; 
				} else {
					// Increase index-offset by 2 if word changed and added "ay"
					translationOffset += 2;
				}
			}
		}

		return translatedLine;
	}

	// Translate a single word to pig latin
	private static String translateWordToPigLatin(String word) {
		String formerCase = checkWordCase(word);

		// If word has digits or special characters, do nothing
		if (!word.matches("[A-Za-z]+")) {
			return word;

		} else {
			// Force to Lower Case
			word = word.toLowerCase();

			// Iterate through string, looking for first vowel character
			for (int index = 0; index < word.length(); index++) {
				// Upon finding first vowel, use its index to translate the word
				// to pig latin, and break the for loop.
				if (isVowel(word.charAt(index))) {
					if (index == 0) {
						word = word + "way";
						break;
					} else {
						word = word.substring(index) + word.substring(0, index) + "ay";
						break;
					}
				}
			}
			// Restore the word's former case
			word = restoreFormerCase(word, formerCase);
		}

		return word;
	}

	// Check whether a character is a vowel
	private static Boolean isVowel(char letter) {
		return "AEIOUaeiou".indexOf(letter) != -1;
	}

	// Check the case of a word, to be restored after translation
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

	// Restore the former case of a word, since translation forces lower case
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

}