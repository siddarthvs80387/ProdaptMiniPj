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
	
	public int main(int login_user_id)
	{
		Scanner sc = new  Scanner(System.in);
		int hotel_console_choice=0;
		do
		{
			System.out.println("1.Registration");
			System.out.println("2.View Registered Hotels");
			System.out.println("3.Update Details");
			System.out.println("4.View Reviews");
			System.out.println("5.Payment");
			System.out.println("6.Logout");
			hotel_console_choice = sc.nextInt();
			sc.nextLine();
			if(hotel_console_choice==1)
			{
				System.out.println("Enter Hotel Name: ");
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
				System.out.println("Confirm Details");
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
					System.out.println("Confirmed Registration");
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
						System.out.println("Hotel Name: "+rs.getString("hotel_name"));
					}
					con.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			if(hotel_console_choice==6)
			{
				break;
			}
			
		}while(hotel_console_choice!=0);
		
		return 0;
	}
}
