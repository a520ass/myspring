package j2ee;

public class Test2 {
	
	public static void main(String[] args) {
		int year=Integer.valueOf(args[0]);
		if(year%400==0||year%100!=0&&year%4==0){
			System.out.println(String.format("%s年是闰年", year));
		}else{
			System.out.println(String.format("%s年不是闰年", year));
		}
	}
}
