package booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

class Hotel_registrationdetails 
{
	private String hotel_name;
	private String hotel_locality;
	private String hotel_city;
	private String hotel_address;
	private int single_room;
	private float single_room_price;
	private int double_room;
	private float double_room_price;
	private int deluxe_room;
	private float deluxe_room_price;
	private String phone;
	
	int hotel_id=0;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHotel_name() {
		return hotel_name;
	}
	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}
	public String getHotel_locality() {
		return hotel_locality;
	}
	public void setHotel_locality(String hotel_locality) {
		this.hotel_locality = hotel_locality;
	}
	public String getHotel_city() {
		return hotel_city;
	}
	public void setHotel_city(String hotel_city) {
		this.hotel_city = hotel_city;
	}
	public String getHotel_address() {
		return hotel_address;
	}
	public void setHotel_address(String hotel_address) {
		this.hotel_address = hotel_address;
	}
	public int getSingle_room() {
		return single_room;
	}
	public void setSingle_room(int single_room) {
		this.single_room = single_room;
	}
	public float getSingle_room_price() {
		return single_room_price;
	}
	public void setSingle_room_price(float single_room_price) {
		this.single_room_price = single_room_price;
	}
	public int getDouble_room() {
		return double_room;
	}
	public void setDouble_room(int double_room) {
		this.double_room = double_room;
	}
	public float getDouble_room_price() {
		return double_room_price;
	}
	public void setDouble_room_price(float double_room_price) {
		this.double_room_price = double_room_price;
	}
	public int getDeluxe_room() {
		return deluxe_room;
	}
	public void setDeluxe_room(int deluxe_room) {
		this.deluxe_room = deluxe_room;
	}
	public float getDeluxe_room_price() {
		return deluxe_room_price;
	}
	public void setDeluxe_room_price(float deluxe_room_price) {
		this.deluxe_room_price = deluxe_room_price;
	}
	
	// Update Details In Table
	public void update_details(int login_user_id)
	{
		Scanner sc = new Scanner(System.in);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("select * from hotel_details where user_id=?");
			stmt.setInt(1, login_user_id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				System.out.println();
				System.out.println("Hotel ID: "+rs.getInt("hotel_id"));
				System.out.println("Hotel Name: "+rs.getString("hotel_name"));
				System.out.println("Hotel City: "+rs.getString("hotel_city"));
				System.out.println("Single Room Price: "+rs.getFloat("single_room_price"));
				System.out.println("Double Room Price: "+rs.getFloat("double_room_price"));
				System.out.println("Deluxe Room Price: "+rs.getFloat("deluxe_room_price"));
				System.out.println();
			}
			ArrayList<Integer> available = new ArrayList<Integer>();
			PreparedStatement stmt12 = con.prepareStatement("select * from hotel_details where user_id=?");
			stmt12.setInt(1, login_user_id);
			ResultSet rs12 = stmt12.executeQuery();
			
			while(rs12.next())
			{
			
				available.add(rs12.getInt("hotel_id"));
				
			}
			// Get Hotel Id For Update 
			
			System.out.println("Select HotelID To Update Settings: ");
			int hotelid = sc.nextInt();
			if(available.contains(hotelid))
			{
			System.out.println("Select Detail To Update");
			System.out.println("1. Edit Single Room Price");
			System.out.println("2. Edit Double Room Price");
			System.out.println("3. Edit Deluxe Room Price");
			System.out.println("Enter Choice");
			int ch = sc.nextInt();
			System.out.println("Enter Price: ");
			float newprice = sc.nextFloat();
			String str ="";
			if (ch==1)
			{
				str="update hotel_details set single_room_price = ? where hotel_id=?;";
			}
			else if(ch==2)
			{
				str="update hotel_details set double_room_price = ? where hotel_id=?;";
			}
			else if(ch==3)
			{
				str="update hotel_details set deluxe_room_price = ? where hotel_id=?;";
			}
			PreparedStatement stmt1 = con.prepareStatement(str);
			stmt1.setFloat(1, newprice);
			stmt1.setInt(2, hotelid);
			stmt1.executeUpdate();
			System.out.println("Updates Applied\n");
			}
			else
			{
				System.out.println("Invalid Hotel ID\n");
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}
	
	// Hotel Registration & Room Creation
	public void registration(int login_user_id)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("insert into hotel_details (user_id,hotel_name,hotel_locality,hotel_city,hotel_address,single_room,single_room_price,double_room,double_room_price,deluxe_room,deluxe_room_price,hotel_contact) values (?,?,?,?,?,?,?,?,?,?,?,?);");
			stmt.setInt(1,login_user_id);
			stmt.setString(2,getHotel_name());
			stmt.setString(3,getHotel_locality());
			stmt.setString(4,getHotel_city());
			stmt.setString(5,getHotel_address());
			stmt.setInt(6,getSingle_room());
			stmt.setFloat(7,getSingle_room_price());
			stmt.setInt(8,getDouble_room());
			stmt.setFloat(9,getDouble_room_price());
			stmt.setInt(10,getDeluxe_room());
			stmt.setFloat(11,getDeluxe_room_price());
			stmt.setString(12, getPhone());
			stmt.executeUpdate();
			
			PreparedStatement stmt2 = con.prepareStatement("select hotel_id from hotel_details where hotel_name=? and hotel_city=? and hotel_contact=?;");
			stmt2.setString(1,getHotel_name());
			stmt2.setString(2,getHotel_city());
			stmt2.setString(3, getPhone());
			ResultSet rs = stmt2.executeQuery();
			rs.next();
			hotel_id = rs.getInt("hotel_id");
			try
			{
				PreparedStatement stmt1 = con.prepareStatement("insert into hotel_rooms (hotel_id,room_type) values (?,?);");
				stmt1.setInt(1, hotel_id);
				stmt1.setString(2, "single");
				for(int i=1;i<=getSingle_room();i++)
				{
					stmt1.executeUpdate();
				}
				stmt1.setString(2, "double");
				for(int i=1;i<=getDouble_room();i++)
				{
					stmt1.executeUpdate();
				}
				stmt1.setString(2, "deluxe");
				for(int i=1;i<=getDeluxe_room();i++)
				{
					stmt1.executeUpdate();
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void view_reviews(int login_user_id)
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			System.out.println("\nEnter Hotel ID To View Reviews");
			int h_id_review = sc.nextInt();
			sc.nextLine();
			
			ArrayList<Integer> available = new ArrayList<Integer>();
			PreparedStatement stmt12 = con.prepareStatement("select * from hotel_details where user_id=?");
			stmt12.setInt(1, login_user_id);
			ResultSet rs12 = stmt12.executeQuery();
			
			while(rs12.next())
			{
			
				available.add(rs12.getInt("hotel_id"));
				
			}
			
			if(available.contains(h_id_review))
			{
			
				PreparedStatement review_stmt = con.prepareStatement("select feedback from review where hotel_id=? limit 5;");
				review_stmt.setInt(1, h_id_review);
				ResultSet review_res = review_stmt.executeQuery();
				int ctr=0;
				while(review_res.next())
				{
					ctr+=1;
					System.out.println(ctr+". "+review_res.getString("feedback"));
					System.out.println();
				}
				if(ctr==0)
				{
					System.out.println("No Reviews Available\n");
				}
			}
			else
			{
				System.out.println("Enter A Valid Hotel ID To View Reviews\n");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Main
	public int main(int login_user_id)
	{
		Scanner sc = new  Scanner(System.in);
		
		int hotel_console_choice=0;
		do
		{
			System.out.println("\nHotel Administrator \n");
			System.out.println("1.Registration");
			System.out.println("2.View Registered Hotels");
			System.out.println("3.Update Details");
			System.out.println("4.View Reviews");
			System.out.println("5.Logout");
			hotel_console_choice = sc.nextInt();
			sc.nextLine();
			if(hotel_console_choice==1)
			{
				System.out.println("\nEnter Hotel Name: ");
				setHotel_name(sc.nextLine());
				System.out.println("Enter Hotel City: ");
				setHotel_city(sc.nextLine());
				System.out.println("Enter Hotel Locality: ");
				setHotel_locality(sc.nextLine());
				System.out.println("Enter Hotel Address: ");
				setHotel_address(sc.nextLine());
				System.out.println("Enter Hotel Contact Number: ");
				setPhone(sc.nextLine());
				System.out.println("Enter Total Number Of Single Rooms: ");
				setSingle_room(sc.nextInt());
				System.out.println("Enter Single Room Price: ");
				setSingle_room_price(sc.nextFloat());
				System.out.println("Enter Total Number Of Double Rooms: ");
				setDouble_room(sc.nextInt());
				System.out.println("Enter Double Room Price: ");
				setDouble_room_price(sc.nextFloat());
				System.out.println("Enter Total Number Of Deluxe Rooms: ");
				setDeluxe_room(sc.nextInt());
				System.out.println("Enter Deluxe Room Price: ");
				setDeluxe_room_price(sc.nextFloat());
				sc.nextLine();
				
				//Printing Details For Confirmation
				System.out.println();
				System.out.println("\nConfirm Details");
				System.out.println("Hotel Name: "+getHotel_name());
				System.out.println("Hotel City: "+getHotel_city());
				System.out.println("Hotel Locality: "+getHotel_locality());
				System.out.println("Hotel Address: "+getHotel_address());
				System.out.println("Enter Hotel Contact Number: "+getPhone());
				System.out.println("Number Of Single Rooms: "+getSingle_room());
				System.out.println("Single Room Price: "+getSingle_room_price());
				System.out.println("Number Of Double Rooms: "+getDouble_room());
				System.out.println("Double Room Price: "+getDouble_room_price());
				System.out.println("Number Of Deluxe Rooms: "+getDeluxe_room());
				System.out.println("Deluxe Room Price: "+getDeluxe_room_price());
				System.out.println("Do You Want To Continue (y/n)");
				char confirm = sc.nextLine().charAt(0);
				if(confirm=='y'||confirm=='Y') 
				{
					
					registration(login_user_id);
					System.out.println("Confirmed Registration\n");
				}
			}
			if(hotel_console_choice==2)
			{
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
					PreparedStatement stmt = con.prepareStatement("select * from hotel_details where user_id=?");
					stmt.setInt(1, login_user_id);
					ResultSet rs = stmt.executeQuery();
					
					while(rs.next())
					{
						System.out.println();
						System.out.println("Hotel ID: "+rs.getString("hotel_id"));
						System.out.println("Hotel Name: "+rs.getString("hotel_name"));
						System.out.println("Hotel City: "+rs.getString("hotel_city"));
						
					}
					con.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			if(hotel_console_choice==5)
			{
				break;
			}
			if(hotel_console_choice==3)
			{
				update_details(login_user_id);
			}
			if(hotel_console_choice==4)
			{
				view_reviews(login_user_id);
			}
			
		}while(hotel_console_choice!=0);
		
		return 0;
	}
}
