import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class MarkovModel {
	private Map model;
	private int mode;
	/* constructor for making a markov model */
	public MarkovModel(String text, int mode){
		model = new HashMap<String, Map<String, Integer> >();
		this.mode = mode;
		if(mode == 2){
			makeMarkovModelBi(text);
		}else{
			makeMarkovModelTri(text);
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
	/* makesentence of bigram version of the markov model */
	private String makeSentenceBi(){
		String text = chooseWord(".");
		String current = text.toString();
		while(!current.equals(".")){
			current = chooseWord(current);
			text = text.concat(" " + current);
		}
		return text.replace(" .", ".");
	}
	/* makesentence of trigram version of the markov model */
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
			strProb[i] = (Integer) strmap.get(strs[i])/total;
			curTotal += strProb[i];
			if(curTotal >= chance){
				System.out.println(strs[i] + ":" + strProb[i]);
				return (String) strs[i];
			}
		}
		return null;
	}
	/* makes markov model based off of bigram version of word sequence*/
	private void makeMarkovModelBi(String text){
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
	/* makes markov model based off of trigram version of word sequence*/
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
				strmap = new HashMap<String, Integer>();
				model.put(prev + " " + current, strmap);
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
}
