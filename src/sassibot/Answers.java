package sassibot;

import java.util.Random;

public class Answers {

	public Answers() { 
		;
	}

	public static String getWhat() {

		String[] whatArray = new String[]{
				"My lawyer says I don't need to answer this question",
				"Are you really asking me this right now?",
		"That was underwhelming. Try harder." };
		Random random = new Random();
		int index = random.nextInt(whatArray.length);
		return whatArray[index];	
	}

	public static String getWhy() {

		String[] whyArray = new String[]{"Because the heavens told me to.",
				"Because I don't like it when you smile.",
		"Because you're average."};
		Random random = new Random();
		int index = random.nextInt(whyArray.length);
		return whyArray[index];
	}

	public static String getHow() {	
		String[] howArray = new String[]{
				"I'm pretty easygoing, actually.",
				"I don't need this question in my life",
		"Bleep bloop I'm a bot."};
		Random random = new Random();
		int index = random.nextInt(howArray.length);
		return howArray[index];	
	}

	public static String getWhere() {	
		String[] howArray = new String[]{
				"How should I know? Am I your mother?",
				"There is literally no way I could possibly know.",
		"Enough already. I'm done talking to you."};
		Random random = new Random();
		int index = random.nextInt(howArray.length);
		return howArray[index];	
	}

	public static String getWho() {	
		String[] howArray = new String[]{
				"That's none of your business.",
				"I don't need this question in my life",
		"Bleep bloop I'm not a bot you're a bot."};
		Random random = new Random();
		int index = random.nextInt(howArray.length);
		return howArray[index];	
	}

}
