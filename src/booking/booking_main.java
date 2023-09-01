package booking;

import java.util.*;
public class booking_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int ch=1;
		do
		{
			System.out.println("1.Enter Form");
			System.out.println("2.Exit");
			ch = sc.nextInt();
			if(ch==1)
			{
				test1 t = new test1();
				t.main(args);
			}
		}while(ch!=0);
	}

}
