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
}
