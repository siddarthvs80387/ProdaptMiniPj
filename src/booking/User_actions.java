package booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class User_actions 
{
	void display_all_hotels(String city,int roomcount,int room_type)
	{
		String room_t="";
		String room_price_chk="";
		if (room_type==1)
		{
			room_t = "single";
			room_price_chk="single_room_price";
		}
		else if(room_type==2)
		{
			room_t = "double";
			room_price_chk="double_room_price";
		}
		else
		{
			room_t = "deluxe";
			room_price_chk="deluxe_room_price";
		}
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("select * from hotel_details where hotel_city=? order by ?;");
			stmt.setString(1, city);
			stmt.setString(2, room_price_chk);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println("Hotel ID: "+rs.getString("hotel_id"));
				System.out.println("Hotel Name: "+rs.getString("hotel_name"));
				System.out.println("Hotel Location: "+rs.getString("hotel_city"));
				System.out.println("Rooms: "+roomcount);
				System.out.println("Rooms Type: "+room_t);
				System.out.println("Price: "+rs.getFloat(room_price_chk)*roomcount);
				
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void booking(int id,int roomcount,int room_type,LocalDate chkindate,LocalDate chkoutdate)
	{
		String room_t="";
		if (room_type==1)
		{
			room_t = "single";
		}
		else if(room_type==2)
		{
			room_t = "double";
		}
		else
		{
			room_t = "deluxe";
		}
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("select room_id from hotel_rooms where hotel_id=? and room_type=?;");
			stmt.setInt(1, id);
			stmt.setString(2, room_t);
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public int main(int login_user_id)
	{
		Scanner sc = new Scanner(System.in);
		int choice=1;
		do
		{
			System.out.println("1.Book Hotel");
			choice = sc.nextInt();
			sc.nextLine();
			if(choice==1)
			{
				System.out.println("Enter The City");
				String city = sc.nextLine();
				System.out.println("Enter Room Type \n 1.Single \n 2.Double \n 3.Deluxe");
				int room_type = sc.nextInt();
				System.out.println("Enter Number Of Adults");
				int adults = sc.nextInt();
				int roomcount=0;
				if(room_type==2||room_type==3)
				{
					roomcount=adults/2;
					if(adults%2==1)
						roomcount+=1;
				}
				//Display All Hotels
				display_all_hotels(city,roomcount,room_type);
				System.out.println("Select Hotel Id To Proceed");
				int id = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Check In Date \"dd-mm-yyyy\"");
				String chkin = sc.nextLine();
				System.out.println("Enter Check Out Date \"dd-mm-yyyy\"");
				String chkout = sc.nextLine();
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate chkindate = LocalDate.parse(chkin, formatter);
				LocalDate chkoutdate = LocalDate.parse(chkout, formatter);
				
				booking(id,roomcount,room_type,chkindate,chkoutdate);
			}
		}
		while(choice!=0);
				
		
		return 0;
	}
}
