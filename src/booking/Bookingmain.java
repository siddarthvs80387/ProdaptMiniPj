package booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
public class Bookingmain {

	public static int get_user_type(int user_id)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			Statement stmt = con.createStatement();
			String get_login_details = "select * from users where user_id=\""+user_id+"\"";
			ResultSet rs = stmt.executeQuery(get_login_details);
			rs.next();
			int data = rs.getInt("user_type");
			con.close();
			return data;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return 0;
		}
	
	}
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		int login_user_id = 0,login=0;
		do
		{
			logincontrol user_login = new logincontrol();
			login_user_id = user_login.main();
			if(login_user_id!=0)
			{
				break;
			}
		}while(login_user_id==0);
		login = get_user_type(login_user_id);
		if(login==3)
			System.out.println("Admin Login Successfull");
		else if(login==1)
		{
			System.out.println("Login Successfull");
			User_actions usr = new User_actions();
			usr.main(login_user_id);
		}
		else if(login==2)
		{
			System.out.println("Hotel Administrator Login Successfull");
			Hotel_registrationdetails hotel = new Hotel_registrationdetails();
			int x =hotel.main(login_user_id);
			System.out.println("Logout Successfull");
		}
		else if(login==0)
		{
			System.out.println("Error");
		}
	}

}
