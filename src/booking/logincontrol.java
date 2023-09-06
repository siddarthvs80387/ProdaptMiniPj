package booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class logincontrol 
{
	public int signup(String user_name,String phone,String password,int user_type)
	{
		try
		{  
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("insert into users (username,phone,password,user_type) values (?,?,?,?);");
			stmt.setString(1,user_name);
			stmt.setString(2,phone);
			stmt.setString(3,password);
			stmt.setInt(4,user_type);
			//System.out.println(stmt);
			stmt.executeUpdate();
			con.close();
			System.out.println("User Registered Successfully!!!");
			System.out.println("Please Proceeed To Sign In");
			return 1;
		}
		catch(Exception e)
		{
			System.out.println("Username or Phone Already Exists" +e);
			return 0;
		}
	}
	public int signin(String phone,String pass)
	{
		try
		{  
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			Statement stmt = con.createStatement();
			
			String get_login_details = "select * from users where phone=\""+phone+"\"";
			ResultSet rs = stmt.executeQuery(get_login_details);
			rs.next();
			
			String user_contact = rs.getString("phone");
			String db_pass=rs.getString("password");
			int user_id = rs.getInt("user_id");
			con.close();
			
			if(db_pass.equals(pass) && user_contact.equals(phone))
				return user_id;
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
		int user_type=0,choice=0;
		Scanner sc = new Scanner(System.in);
		do
		{
			System.out.println("1.Sign In ");
			System.out.println("2.Sign Up");
			choice = sc.nextInt();
			sc.nextLine();
			if(choice==1)
			{
				System.out.println("Enter Phone Number:");
				String phone_num = sc.nextLine();
				System.out.println("Enter Password:");
				String pass = sc.nextLine();
				user_type = signin(phone_num,pass);
				if(user_type!=0)
				{
					break;
				}
				else
				{
					System.out.println("Username/Password Incorrect!!!");
					System.out.println();
				}
			}
			else if(choice==2)
			{
				int user_type_choice=0;
				String user_name;
				String password;
				String phone;
				do
				{
					do
					{
						System.out.println("Enter User Type");
						System.out.println("1. User");
						System.out.println("2. Hotel Admin");
						user_type_choice = sc.nextInt();
						if(user_type_choice==1 || user_type_choice==2)
						{
							break;
						}
					}while(true);
					sc.nextLine();
					System.out.println("Enter Username:");
					user_name = sc.nextLine();
					System.out.println("Enter Password:");
					password = sc.nextLine();
					System.out.println("Enter Phone:");
					phone= sc.nextLine();
					int registration_status = signup(user_name,phone,password,user_type_choice);
					if(registration_status!=0)
					{
						break;
					}
				}while(true);
				break;
			}
			
		}while(user_type==0);
		return user_type;
	}
}
