package j2ee;

public class Test4 {
	
	public static void main(String[] args) {
		String str=" o45po 0e9";
		System.out.println(countNum(str));
	}

	private static int countNum(String str) {
		int count=0;
		for (char c : str.toCharArray()) {
			if(c>='0'&&c<='9'){
				count++;
			}
		}
		return count;
	}
}
