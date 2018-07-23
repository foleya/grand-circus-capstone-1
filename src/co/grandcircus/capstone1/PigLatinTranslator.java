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
			// Get a word or line from the user for translation
			// Remove apostrophes because they don't play nice with pig latin
			System.out.print("Enter a word or line to be translated to Pig Latin: ");
			userInput = removeApostrophes(getValidInput(scnr));

			// Translate the user's input
			System.out.println("\nIn Pig Latin, that's: ");
			System.out.println(translateLineToPigLatin(userInput));

			// Ask if user would like to continue the program
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
			input = scnr.nextLine();
			try {
				if (input.length() > 0) {
					inputIsValid = true;
				} else {
					System.out.print("\n -- You must enter something! Try again: ");
				}
			} catch (InputMismatchException ime) {
				System.out.print("\n -- You must enter something! Try again: ");
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
		String regex = "[^\\p{Punct}\\s]+";
		Matcher matcher = Pattern.compile(regex).matcher(line);
		int indexOffset = 0;

		// Iterate through line looking for things other than punctuation and whitespace
		while (matcher.find()) {
			// Upon finding something, store that word as a variable
			String word = matcher.group();
			// Create a pig-latin translated version of the word
			String translatedWord = translateWordToPigLatin(word);
			/* Replace the original word with the pig latin version.
			 * Doing so often increases the length of the word, which means the index
			 * at which matcher found a match in the original line needs to be increased/padded
			 * so that the word in the translated line (which is ever growing in size) is
			 * replaced at the index in that line that would have corresponded to the index
			 * matcher found in the original line.
			 */
			translatedLine.replace(matcher.start() + indexOffset,
								   matcher.end() + indexOffset,
								   translatedWord);
			if (!word.equals(translatedWord)) {
				if (word.charAt(0) == translatedWord.charAt(0)) {
					// Increase index-offset by 3 if word changed and added "way"
					indexOffset += 3; 
				} else {
					// Increase index-offset by 2 if word changed and added "ay"
					indexOffset += 2;
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
		if (word.equals(word.toUpperCase()) && word.length() > 1) {
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