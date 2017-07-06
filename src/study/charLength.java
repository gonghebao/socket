package study;

import java.util.HashMap;
import java.util.Map;

public class charLength {

	public static void main(String[] args) {
		String  str = "good good study, day day up.";
		Map<String,Integer> map = new HashMap<String,Integer>();
		String reg  = "\\w";
		
		for(int i=0;i<str.length();i++){
			String s = String.valueOf(str.charAt(i));
			if(s.matches(reg)){
				if(map.containsKey(s)){
					map.put(s,map.get(s)+1);
				}else{
					map.put(s,1);
				}
			}
		}
		System.out.println(map);
	}

}
