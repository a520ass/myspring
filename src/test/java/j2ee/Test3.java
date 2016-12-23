package j2ee;


public class Test3 {
	
	public static void main(String[] args) {
		int sum=0;
		int count=0;
		for(int i=100;i<=999;i++){
			if(i%4==3&&i%8==5&&i%12==7){
				sum+=i;
				count++;
			}
		}
		System.out.println(String.format("所有整数的和为%d,整数个数为%d", sum,count));
	}
}
