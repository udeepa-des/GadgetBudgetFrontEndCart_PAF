package com;

import java.sql.*;

public class Cart {
	//Database Connection
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_project", "root", "");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//Read database values
	public String readItems()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Research ID</th><th>Research Name</th><th>Amount</th>"+ "<th>Description</th><th>Update</th><th>Remove</th></tr>";
					String query = "select * from cart";
								Statement stmt = con.createStatement();
								ResultSet rs = stmt.executeQuery(query);
								// iterate through the rows in the result set
								while (rs.next())
								{
									String ID = Integer.toString(rs.getInt("ID"));
									String researchID = rs.getString("researchID");
									String researchName = rs.getString("researchName");
									String Amount = Double.toString(rs.getDouble("Amount"));
									String Description = rs.getString("Description");
									// Add into the html table
									output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + ID + "'>" + researchID + "</td>";
									output += "<td>" + researchName + "</td>";
									output += "<td>" + Amount + "</td>";
									output += "<td>" + Description + "</td>";
									// buttons
									output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-cartid='" + ID + "'></td>" + 
											  "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-cartid='"
															+ ID + "'>" + "</td></tr>";
								}
								con.close();
								// Complete the html table
								output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the cart service.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String insertItem(String rID, String rName, String amount, String desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into cart (`researchID`,`researchName`,`Amount`,`Description`)" + " values (?, ?, ?, ?)";
								PreparedStatement preparedStmt = con.prepareStatement(query);
								// binding values
								preparedStmt.setString(1, rID);
								preparedStmt.setString(2, rName);
								preparedStmt.setDouble(3, Double.parseDouble(amount));
								preparedStmt.setString(4, desc);
								// execute the statement
								preparedStmt.execute();
								con.close();
								String newCartItems = readItems();
								output = "{\"status\":\"success\", \"data\": \"" + newCartItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String updateItem(String ID, String rID, String rName, String Amont, String desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE cart SET researchID=?,researchName=?,Amount=?,Description=? WHERE ID=?";
							PreparedStatement preparedStmt = con.prepareStatement(query);
								// binding values
								preparedStmt.setString(1, rID);
								preparedStmt.setString(2, rName);
								preparedStmt.setDouble(3, Double.parseDouble(Amont));
								preparedStmt.setString(4, desc);
								preparedStmt.setInt(5, Integer.parseInt(ID));
								
								// execute the statement
								preparedStmt.execute();
								con.close();
								String newCartItems = readItems();
								output = "{\"status\":\"success\", \"data\": \"" + newCartItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String deleteItem(String ID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from cart where ID=?";
								PreparedStatement preparedStmt = con.prepareStatement(query);
								// binding values
								preparedStmt.setInt(1, Integer.parseInt(ID));
								// execute the statement
								preparedStmt.execute();
								con.close();
								String newCartItems = readItems();
								output = "{\"status\":\"success\", \"data\": \"" +
										newCartItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
