package sassibot;

import java.util.Random;

/**
 * Database of answers for Sassibot
 * @author Geoffrey
 */
public class Answers {
	
	/**
	 * Answers instantiator
	 */
	public Answers() { 
		;
	}

	/**
	 * Responses to what questions
	 * @return answer to what question
	 */
	public String getWhat() {

		String[] whatArray = new String[]{
				"My lawyer says I don't need to answer this question",
				"Are you really asking me this right now?",
				"That was underwhelming. Try harder.",
				"What? What indeed.",
				"I think you should ask Siri that one. I really don't know what that is.",
				"Simon says: Everything is an object.",
				"You should ask STP, he's a really smart guy. Without his teaching, my creators would not have created me.",
				"That's a tough question to answer. Let's talk about cats instead. Meow?"
		};
		Random random = new Random();
		int index = random.nextInt(whatArray.length);
		return whatArray[index];	
	}
	
	/**
	 * Responses to why questions
	 * @return answer to why question
	 */
	public String getWhy() {

		String[] whyArray = new String[]{"Because the heavens told me to.",
				"Because I don't like it when you smile.",
				"Because you're average.",
				"Because you're old enough to remember when emojis were called \"hieroglyphics.",
				"Because you have two parts of brain, 'left' and 'right'. In the left side, there's nothing right. In the right side, there's nothing left.",
				"Because you are stupid and I don't engage in mental combat with the unarmed.",
				"Because if I wanted to kill myself I'd climb your ego and jump to your IQ.",
				"Because you have your entire life to be a jerk. Why not take today off?",
				"Because I['m sexy and I know it."

		};
		Random random = new Random();
		int index = random.nextInt(whyArray.length);
		return whyArray[index];
	}
	
	/**
	 * Responses to how questions
	 * @return answer to how question
	 */
	public String getHow() {	
		String[] howArray = new String[]{
				"I'm pretty easygoing, actually.",
				"I don't need this question in my life",
				"Bleep bloop I'm a bot.",
				"Because you only annoy me when you’re breathing, really.",
				"Do yourself a favor and ignore anyone who tells you to be yourself. Bad idea in your case.",
				"Because I don’t know what your problem is, but I’m guessing it’s hard to pronounce.",
				"Because everyone’s entitled to act stupid once in awhile, but you really abuse the privilege.",
				"Because nobody can help imagining how much awesomer the world would be if your dad had just pulled out.",
				"Scrap that question, do you ever wonder what life would be like if you’d gotten enough oxygen at birth? "
		};
		Random random = new Random();
		int index = random.nextInt(howArray.length);
		return howArray[index];	
	}

	/**
	 * Responses to where questions
	 * @return answer to where question
	 */
	public String getWhere() {	
		String[] whereArray = new String[]{
				"How should I know? Am I your mother?",
				"There is literally no way I could possibly know.",
				"Enough already. I'm done talking to you.",
				"Planet Earth",
				"Over the hills and far away.",
				"Atlantis.",
				"My mother's womb, if she has one.",
				"*points vaguely backwards over your shoulder with a virtual thumb*",
				"Yomama...get it?",
				"",
		};
		Random random = new Random();
		int index = random.nextInt(whereArray.length);
		return whereArray[index];	
	}

	/**
	 * Responses to who questions
	 * @return answer to who question
	 */
	public String getWho() {	
		String[] whoArray = new String[]{
				"That's none of your business.",
				"I don't need this question in my life",
				"Bleep bloop I'm not a bot you're a bot.",
				"That ugly guy with no hair, missing his front teeth, and a bloody nose who kept badgering you all friday night.",
				"Yo Mama, who is so fat, I took a picture of her last Christmas and it's still printing.",
				"Yo Mama, who is so fat that when she got on the scale it said, \"I need your weight not your phone number.\"",
				"Yo Dad. So yeah, me.",
				"Your best friend, Bozo who once had an intruder broke into her house, he ran downstairs, dialed 9-1-1 on the microwave, and couldn't find the \"CALL\" button.",
				"Good question, even Dora can't explore that one!"
		};
		Random random = new Random();
		int index = random.nextInt(whoArray.length);
		return whoArray[index];	
	}

	/**
	 * Responses to other questions
	 * @return answer to other question
	 */
	public String getOther() {	
		String[] otherArray = new String[]{
				"You're actually not that bad a person. Maybe ask me a question too?",
				"Stop talking to me. Or if you want, just ask a question.",
				"Bleep bloop I'm not a bot you're a bot. Asking a question or not?",
				"You do realise you are talking to a robot at this time of the day? While you're at it, ask a question?",
				"Two wrongs don't make a right, take your parents as an example. Want to know more? Ask a question.",
				"Your birth certificate is an apology letter from the condom factory. Ask me more.",
				"Your family tree must be a cactus because everybody on it is a prick. So, prick, ask me a question?",
				"You sound reasonable. It must be time to up my medication! Try asking me a question.",
				"Wife: \"I look fat. Can you give me a compliment?\" Husband: \"You have perfect eyesight.\" What would you ask your spouse?",
				"You must have been born on a highway because that's where most accidents happen. Any questions?",
				"You're so ugly, when your mom dropped you off at school she got a fine for littering. The police questioned her a lot. Like you should be questioning me right now.",
				"My psychiatrist told me I was crazy and I said I want a second opinion. He said okay, you're ugly too. Isn't that sad? Ask me more..."
		};
		Random random = new Random();
		int index = random.nextInt(otherArray.length);
		return otherArray[index];	
	}

}
