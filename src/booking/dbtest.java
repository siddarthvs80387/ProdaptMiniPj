package booking;

import java.sql.*;
import java.util.*;
public class dbtest {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		int ch=1;
		do
		{
			System.out.println("1.Sign In ");
			System.out.println("2.Sign Up");
			ch = sc.nextInt();
			sc.nextLine();
			if(ch==2)
			{
				System.out.println("Enter Username");
				String username = sc.nextLine();
				System.out.println("Enter Password");
				String pass = sc.nextLine();
				try
				{  
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/useraccount","root","root");
					Statement stmt = con.createStatement();
					String dmlcmd = "insert into userlogins (username,password) values (\""+(String)username+"\",\""+(String)pass+"\");";
					//System.out.println(dmlcmd);
					stmt.executeUpdate(dmlcmd);
				}
				catch(Exception e)
				{
					System.out.println("Failed to connect "+e);
				} 
			}
		}while(ch!=0);
	}

}
