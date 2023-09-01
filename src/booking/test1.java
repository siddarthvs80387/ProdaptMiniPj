package booking;
import java.util.*;

public class test1 extends User_validation{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		User_validation user = new User_validation();
		int status = user.main();
		if (status!=0)
			System.out.println("Sign In Successfull");
	}

}
