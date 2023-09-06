package booking;

import java.sql.*;
import java.sql.Date;
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
			PreparedStatement stmt = con.prepareStatement("select * from hotel_details where hotel_city=? order by "+room_price_chk+" ;");
			stmt.setString(1, city);
			//stmt.setString(2, room_price_chk);
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println("Hotel ID: "+rs.getString("hotel_id"));
				System.out.println("Hotel Name: "+rs.getString("hotel_name"));
				System.out.println("Hotel Location: "+rs.getString("hotel_city"));
				System.out.println("Rooms: "+roomcount);
				System.out.println("Rooms Type: "+room_t);
				System.out.println("Price: "+rs.getFloat(room_price_chk)*roomcount);
				System.out.println();
				
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	// Booking Hotel Room
	public void booking(int login_user_id,int id,int roomcount,int room_type,LocalDate chkindate,LocalDate chkoutdate)
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
		
		float tot_price=0;
		ArrayList<Integer> available_list = new ArrayList<Integer>();
		ArrayList<Integer> registered_list = new ArrayList<Integer>();
		ArrayList<Integer> toregister_list = new ArrayList<Integer>();
		ArrayList<Date> chkindatelist = new ArrayList<Date>();
		ArrayList<Date> chkoutdatelist = new ArrayList<Date>();
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			// Price 
			PreparedStatement price_stmt = con.prepareStatement("select "+room_price_chk+" from hotel_details where hotel_id=?;");
			price_stmt.setInt(1,id);
			ResultSet prs=price_stmt.executeQuery();
			while(prs.next())
			{
				tot_price=prs.getFloat(room_price_chk)*roomcount;
			}
			
			//Booking Flow
			PreparedStatement stmt = con.prepareStatement("select room_id from hotel_rooms where hotel_id=? and room_type=?;");
			stmt.setInt(1, id);
			stmt.setString(2, room_t);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				available_list.add(rs.getInt("room_id"));
			}
			PreparedStatement stmt1 = con.prepareStatement("select * from reservation where hotel_id = ?;");
			stmt1.setInt(1, id);
			ResultSet rs1 = stmt1.executeQuery();
			while(rs1.next())
			{
				registered_list.add(rs1.getInt("room_id"));
				chkindatelist.add(rs1.getDate("checkin"));
				chkoutdatelist.add(rs1.getDate("checkout"));
			}
			
			for(int ind=1;ind<=roomcount;ind++)
			{
				for(int i:available_list)
				{
					if(registered_list.contains(i) )
					{
						//chk
						int room_avail=1;
						
						if(toregister_list.contains(i))
						{
							continue;
						}
//						for(int x: toregister_list)
//						{
//							System.out.println(x);
//						}
						
						for(int j=0;j<registered_list.size();j++)
						{
							if(registered_list.get(j)==i)
							{
								LocalDate d1 = chkindatelist.get(j).toLocalDate();
								LocalDate d2 = chkoutdatelist.get(j).toLocalDate();
								if(chkindate.isEqual(d1) && chkoutdate.isEqual(d2))
								{
									room_avail=0;
									break;
								}
								else if(chkindate.isAfter(d1) && chkindate.isBefore(d2))
								{
									room_avail=0;
									break;
								}
								else if(chkoutdate.isAfter(d1) && chkoutdate.isBefore(d2))
								{
									room_avail=0;
									break;
								}
								else if(d1.isAfter(chkindate) && d1.isBefore(chkoutdate))
								{
									room_avail=0;
									break;
								}
								else if(d2.isAfter(chkoutdate) && d2.isBefore(chkoutdate))
								{
									room_avail=0;
									break;
								}
							}
						}
						
						if (room_avail==1)
						{
							toregister_list.add(i);
							break;
						}
					}
					else
					{
					//insert
						toregister_list.add(i);
						registered_list.add(i);
						break;
					}
				}
				
			}
			int insert = toregister_list.size();
			if(insert==roomcount)
			{
				
				PreparedStatement stmt3 = con.prepareStatement("insert into user_bookingid (user_id,hotel_id,price,room) values (?,?,?,?) ;");
				stmt3.setInt(1,login_user_id);
				stmt3.setInt(2,id);
				stmt3.setFloat(3, tot_price);
				stmt3.setString(4, room_t);
				stmt3.executeUpdate();
				PreparedStatement stmt4 = con.prepareStatement("select booking_id from user_bookingid where user_id=? order by booking_id desc limit 1;");
				stmt4.setInt(1, login_user_id);
				ResultSet rs2 = stmt4.executeQuery();
				int bookingid=0;
				while(rs2.next())
				{
					bookingid=rs2.getInt("booking_id");
				}
				
				Date date = new Date(0);
				PreparedStatement stmt2 = con.prepareStatement("insert into reservation (hotel_id,room_id,checkin,checkout,booking_id) values (?,?,?,?,?) ;");
				for(int i:toregister_list)
				{
					stmt2.setInt(1, id);
					stmt2.setInt(2, i);
					stmt2.setDate(3, date.valueOf(chkindate));
					stmt2.setDate(4, date.valueOf(chkoutdate));
					stmt2.setInt(5, bookingid);
					//	System.out.println(id);
					stmt2.executeUpdate();
				}
				System.out.println("Booking Successfull");
			}
			else
			{
				System.out.println("Required rooms not available ");
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void viewbookings(int login_user_id)
	{
		//select h.hotel_name,b.booking_id,count(r.booking_id) from hotel_details h , user_bookingid b, reservation r where b.hotel_id=h.hotel_id and b.user_id=5 and r.booking_id=b.booking_id and month(curdate())=month(r.checkin) group by h.hotel_name,b.booking_id;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("select h.hotel_name,b.booking_id,count(r.booking_id),b.price,b.room from hotel_details h , user_bookingid b, reservation r where b.hotel_id=h.hotel_id and b.user_id=? and r.booking_id=b.booking_id and month(curdate())=month(r.checkin) group by h.hotel_name,b.booking_id ;");
			stmt.setInt(1, login_user_id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println();
				System.out.println("Hotel Name: "+rs.getString("hotel_name"));
				System.out.println("Booking ID: "+rs.getInt("booking_id"));
				System.out.println("Rooms Booked: "+rs.getInt("count(r.booking_id)"));
				System.out.println("Room Type: "+rs.getString("room"));
				System.out.println("Price: "+rs.getFloat("price"));
				System.out.println();
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	// Cancel Booking;
	public void cancelbooking(int login_user_id)
	{
		viewbookings(login_user_id);
		System.out.println("Enter The Booking ID For Cancellation");
		Scanner sc = new Scanner(System.in);
		int bookingid=sc.nextInt();
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("call delete_reservation(?);");
			stmt.setInt(1, bookingid);
			stmt.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		sc.close();
	}
	
	//Enter Review
	public void reviews(int login_user_id)
	{
		ArrayList<String> h_name = new ArrayList<String>();
		ArrayList<Integer> h_id = new ArrayList<Integer>();
		Scanner sc = new Scanner(System.in);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","root");
			PreparedStatement stmt = con.prepareStatement("select hotel_id,hotel_name from hotel_details where hotel_id in (select hotel_id from user_bookingid where user_id=?) order by hotel_id;");
			stmt.setInt(1, login_user_id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				h_id.add(rs.getInt("hotel_id"));
				h_name.add(rs.getString("hotel_name"));
			}
			for(int i=0;i<h_name.size();i++)
			{
				System.out.println();
				System.out.println("Hotel ID: "+h_id.get(i));
				System.out.println("Hotel Name: "+h_name.get(i));
			}
			System.out.println("Enter the Hotel Id To Leave Review");
			int id = sc.nextInt();
			System.out.println("How Did You Enjoy Your Stay(1-5)");
			int rating = sc.nextInt();
			sc.nextLine();
			System.out.println("Please Leave Your Valuable Feedback");
			String rev = sc.nextLine();
			int ind = h_id.indexOf(id);
			PreparedStatement stmt1 = con.prepareStatement("insert into review (hotel_name,hotel_id,user_id,rating,feedback) values (?,?,?,?,?)");
			stmt1.setString(1,h_name.get(ind));
			stmt1.setInt(2, h_id.get(ind));
			stmt1.setInt(3, login_user_id);
			stmt1.setInt(4, rating);
			stmt1.setString(5, rev);
			stmt1.executeUpdate();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int main(int login_user_id)
	{
		Scanner sc = new Scanner(System.in);
		int choice=1;
		do
		{
			System.out.println("1.Book Hotel");
			System.out.println("2.View Bookings");
			System.out.println("3.Cancel Bookings");
			System.out.println("4.Leave A Review");
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
				System.out.println();
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
				
				booking(login_user_id,id,roomcount,room_type,chkindate,chkoutdate);
			}
			if(choice==2) 
			{
				viewbookings(login_user_id);
			}
			if(choice==3)
			{
				cancelbooking(login_user_id);
				System.out.println("Booking Cancelled");
				System.out.println("We Regret To See You Go");
			}
			if(choice==4)
			{
				reviews(login_user_id);
			}
		}
		while(choice!=0);
				
		
		return 0;
	}
}
