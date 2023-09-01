package booking;

import java.util.*;
import java.sql.*;

public class User_validation 
{
	public int get_validation_details(String phone,String pass)
	{
		try
		{  
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/useraccount","root","root");
			Statement stmt = con.createStatement();
			//String dmlcmd = "insert into userlogins (username,password) values (\""+(String)username+"\",\""+(String)pass+"\");";
			//System.out.println(dmlcmd);
			//stmt.executeUpdate(dmlcmd);
			String get_login_details = "select * from userlogins where username=\""+phone+"\"";
			ResultSet rs = stmt.executeQuery(get_login_details);
			rs.next();
			String db_pass=rs.getString("password");
			String user = rs.getString("username");
			con.close();
			if(db_pass.equals(pass) && user.equals(phone))
				return 1;
			else
				return 0;
		}
		catch(Exception e)
		{
			System.out.println("Failed to connect "+e);
			return 0;
		} 
		
	}
	public int main()
	{
		int return_val=0;
		Scanner sc = new Scanner(System.in);
		int choice = 1;
		do
		{
			System.out.println("1. Sign In");
			System.out.println("2. Sign Up");
			System.out.println("0. Exit");
			
			choice = sc.nextInt();
			sc.nextLine();
			
			if(choice==1)
			{
				System.out.println("Enter Mobile Number :");
				String phone_num = sc.nextLine();
				System.out.println("Enter Password :");
				String pass = sc.nextLine();
				int status = get_validation_details(phone_num, pass);
				if(status!=0)
				{
					return_val = status;
					break;
				}
				else
				{
					System.out.println("Sign In Failed!!!");
				}
			}
			else if(choice==2)
			{
				
			}
			else
			{
				break;
			}
		}while(choice!=0);
		return return_val;
	}
}
