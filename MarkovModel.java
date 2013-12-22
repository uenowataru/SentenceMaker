import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class MarkovModel {
	private Map model;
	private int mode;
	public MarkovModel(String text, int mode){
		model = new HashMap<String, Map<String, Integer> >();
		this.mode = mode;
		if(mode == 3){
			makeMarkovModelTri(text);
		}else{
			makeMarkovModel(text);
		}
	}
	/* makes sentence using the Markov Model we built */
	public String makeSentence(){
		if(mode==2){
			return makeSentenceBi();
		}else{
			return makeSentenceTri();
		}
	}
	private String makeSentenceBi(){
		String text = chooseWord(".");
		String current = text.toString();
		while(!current.equals(".")){
			current = chooseWord(current);
			text = text.concat(" " + current);
		}
		return text.replace(" .", ".");
	}
	private String makeSentenceTri(){
		String prev = ".";
		String current = chooseWord(". .");
		String text = current.toString();
		while(!current.equals(".")){
			String next = chooseWord(prev + " " + current);
			text = text.concat(" " + next);
			prev = current;
			current = next;
		}
		return text.replace(" .", ".");
	}
	/* chooses next word to use in a sentence based on the current word */
	private String chooseWord(String word){
		Map strmap = ((Map) model.get(word));
		if(strmap.isEmpty()){
			return ".";
		}
		Object[] strs = strmap.keySet().toArray();
		Random r = new Random();
		double total = 0.0;
		double curTotal = 0.0;
		for(Object s : strs){
			total += (Integer) strmap.get(s);
		}
		double[] strProb = new double[strs.length];
		double chance = r.nextDouble();
		for(int i = 0; i < strs.length; i++){
			curTotal += (Integer) strmap.get(strs[i])/total;
			if(curTotal >= chance){
				return (String) strs[i];
			}
		}
		return null;
	}
	/* makes markov model */
	private void makeMarkovModel(String text){
		text = ". ".concat(text);
		text = text.concat(" ");
		text = text.replace(". ", " . ");
		Scanner strScan = new Scanner(text);
		String current = strScan.next();
		while(strScan.hasNext()){
			String next = strScan.next();
			Map strmap = (Map) model.get(current);
			if(strmap == null){
				model.put(current, new HashMap<String, Integer>());
				strmap = (Map) model.get(current);
			}
			Integer freq = ((Integer) strmap.get(next));
			if(freq == null){
				freq = 0;
			}
			((Map) model.get(current)).put(next, freq+1);
			current = next;
		}
	}
	private void makeMarkovModelTri(String text){
		text = ". ".concat(text);
		text = text.concat(" ");
		text = text.replace(". ", " . . ");
		Scanner strScan = new Scanner(text);
		String prev = strScan.next();
		String current = strScan.next();
		while(strScan.hasNext()){
			String next = strScan.next();
			Map strmap = (Map) model.get(prev + " " + current);
			if(strmap == null){
				model.put(prev + " " + current, new HashMap<String, Integer>());
				strmap = (Map) model.get(prev + " " + current);
			}
			Integer freq = ((Integer) strmap.get(next));
			if(freq == null){
				freq = 0;
			}
			strmap.put(next, freq+1);
			prev = current;
			current = next;
		}
	}
	
	public static void main(String[] args){
		String text = "Thunderbolt shaped scar is craved into his forehead, revealing his past. The unique cicatrix exposes his tragic murder of his exclusive parents by the Dark Lord, initiating his equally tragic life with his last living unfriendly relatives, the Dursleys. After going through much misery, he finds out on his eleventh birth day that he is wizard-worldly famous for being the only survivor of the Dark Lord’s murderous attempt, and most importantly, he is a wizard himself. He, as you have unraveled, is Harry Potter. Harry potter is my ideal character as a person. I do not look up to him as a role model because of his magical powers, but because of his will in finding who he is as Harry Potter. In the novel, as well as the movie, it clearly portrays how Harry continues on his life in pursuit for the answer to who he is as a wizard. Similar to Harry Potter, Nora, a character from “the Doll’s House,” is another character that had a sudden revelation and decided to leave her family and set out to search for who she is, not how her husband made her. I, on the other hand, had never given a thought about searching for my identity until now. After thorough thinking, I believe that my life is something that I should savor, and elements that determine the taste of my life, are the answer to who I am. If I am to exemplify myself as a typical example such as a hamburger, + " +    "my culture is the ingredients, my spirituality is the salad, and my personality is the meat, and these factors determine the taste of the hamburger.  My culture is the origin for my spirituality and personality. It is the ingredients of Wataru-Hamburger. I was born in Japan. And I moved to San Diego, California when I was three years old. Before fully experiencing childhood in San Diego, I moved back to Tokyo in a year. Just as I was about to turn eight year old, my family decided to move back to San Diego again. By this time, Japanese culture became a part of me. Adjusting to the American standard of living was hard. As I bowed to express appreciation, Americans would not recognize the foreign body language and look at me with blank faces, not knowing how to react. Or when I sat in a position called “Taiiku-zuwari”, which is a formal way to sit in Japanese schools, on the floor, the teacher warned me about how I was being selfish by taking space on the floor from others. When I finished fourth grade, I came back to Japan for the final time. I went to a Japanese public school. Thinking that extroversive relationships were the mode for Japan also, I did not hesitate to interact with other classmates. I realized very soon that they were unfamiliar with a person like me, who is “too friendly,” and that I was avoided. Americans tend to be open and interactive; whereas the Japanese tends to stay introversive. My parents realized after several incidents that Japanese public schools did not suit me well. A year later, I transferred to an international school, Christian Academy in Japan. CAJ was very different from any other schools I have been to. There are mixes of diverse ethnicities and cultures. Quite recently, I was invited over to a friend’s birthday party, where everyone else was American. At the party, songs were shuffled and played continuously. Whenever a song everybody loved was on, everyone stopped everything they were doing, talking, drinking, eating, and danced like crazy. I was astonished at their sudden change of character. In Japanese parties, everything is calm and people just talk and enjoy the food. After experiencing two very distinct environment and cultures, it turned out to be that Japanese cultural influence was greater than American cultural influence. " +     "Culture is an important aspect of my identity. Compared to my cultural importance, my spirituality is like an additional piece of information. My religious values are equivalent to how much an eight year old would value salad in a hamburger. I confess that I do not value religion. I think culture is greatly involved with my values. I am not a Christian, even though I have been attending Christian Academy in Japan for almost five years now. Being taught about Christianity two or three times a week, or writing essays from a Biblical perspective was not enough to change who I was created by culture. Attending public schools that were unreligious both in Japan and America contributes to my ungodliness. I think the Bible does not depict me very well, for I do not act according to the Bible’s commandments. For instance, I have not kept up with the greatest commandment, and I do not think I can in the future. In Mark 12:30-31, Jesus declares that to love our God and our neighbors is the greatest commandment. I have to confess that I disagree with some things the Bible says. Since I do not wholly agree with what the Bible says, it is difficult for me to follow through on what God commands us to act like. My cultural and religious sides construct my personality. My personality is the most vital part of my identity, and it is like the meat of a hamburger. That being said, the Myers Briggs analysis was a rough estimate of who I am. The analysis was not enough to completely understand my personality. The result of the analysis of my personality was ENTJ. Basically, the analysis said that I am outgoing, intuitive, logical, and planning. The personality analysis did not capture my whole personality, however, many of the explanation fit. It captured a fraction of who I am. I think everything except the planning part is true. I always procrastinate and cram up everything on the day before it is due. There are some things that clarify what kind of personality I have that was not on the analysis. First of all, I am honest and frank; I prioritize honesty over harmony. According to The Values Americans Live By, I am like an American. Americans value directness and openness rather than atmosphere. Unlike the Americans, Japanese value social harmony. Japanese would keep harmony, even if they had to lie. Secondly, my thinking process is based on myself entirely. I put myself in front of others in almost all situations. In other words, I am very selfish. Philippians 2:3 states, “Do nothing out of selfish ambition or vain conceit, but in humility consider others better than yourselves. My personality reflects my cultural and religious influences." +     "In one reference, it testifies that God is great and awesome. In addition to that, the Bible defines God as the single god, the only perfect existence, plus, he is generous and forgiving. Hundreds of chapters of the Bible clarify God’s character and who He is. However, is that enough to describe all that God is? Is it possible to summarize who God, the ultimate creator of this world, is in one single book? No, it certainly is not. As well as the Bible, uncountable examples in this world, such as the nature, environment, and people express God. Similar to how the Bible is not enough to absolutely portray God, one definition is not enough to completely describe us. How crucial is it to not know what my ingredients, salad, and meat are? We have to start with understanding ourselves to be able to understand others. Importance of understanding who I am does not lie in completely understanding myself, and looking down on others who do not. It lies in understand who I am first, before, trying to understand others. For who can understand others when he cannot understand himself? In A Doll's House, there was a character called Ms. Linde. Ms. Linde desires to be busy and longs for someone to take care of. When Nora heard that Ms. Linde no longer has to take care of her family, Nora thought that Ms. Linde was delighted. Ms. Linde feels, however, that she is now empty, and there is no meaning in life. Nora could not understand her. However, maybe after realizing that she is only a doll of her husband, Nora could have understood Ms. Linde. For Nora is in the same situation as she is; they both lost their identity, and are searching for who they are. I believe that being confident with my identity is the first step in understanding others, because if I have knowledge of my character, then I have something to compare with when trying to understand others. Therefore, I conclude that knowing who I am is very significant, for it is the first step in understanding our family, colleagues, acquaintances, and even strangers, and making the best of our lives. ";
		MarkovModel mm = new MarkovModel(text,3);
		System.out.println(mm.makeSentence());
	}
}