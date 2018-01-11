import org.apache.commons.lang3.math.NumberUtils;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String str = "sdfs - [10]";
		System.out.println(getIntegers(str));

	
	
	}

	public static String getIntegers(String str) {

		String retour = "";

		for (int f = 0; f < str.length(); f++) {

			String charac = str.substring(f, f + 1);
			if (NumberUtils.isParsable(charac))
							retour = retour + charac;
		}
		return retour;

	}

}
