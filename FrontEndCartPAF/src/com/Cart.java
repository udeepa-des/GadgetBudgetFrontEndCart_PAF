package com;

import java.sql.*;

public class Cart {
	//DB Connection
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

	//Read method
	public String readItems()
	{
		String output = "";

		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error occured when connecting to the DB for Reading";
			}

			//html table 
			output = "<table border='1'><tr><th>Research ID</th><th>Research Name</th><th>Amount</th>" 
					+ "<th>Description</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from cart";

			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(query);

			// iterate through the result set
			while (result.next())
			{
				String ID = Integer.toString(result.getInt("ID"));
				String researchID = result.getString("researchID");
				String researchName = result.getString("researchName");
				String Amount = Double.toString(result.getDouble("Amount"));
				String Description = result.getString("Description");

				// Add into the html table							
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + ID + "'>" + researchID + "</td>";
				output += "<td>" + researchName + "</td>";
				output += "<td>" + Amount + "</td>";
				output += "<td>" + Description + "</td>";					
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-cartid='" + ID + "'></td>" + 
						"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-cartid='"
						+ ID + "'>" + "</td></tr>";
			}
			con.close();

			// Closing the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error occured when reading the cart service.";
			System.err.println(e.getMessage());
		}

		return output;
	}


	//Insert method
	public String insertItem(String rID, String rName, String amount, String desc)
	{

		String output = "";

		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error occured when connecting to the DB for inserting.";
			}

			// Sql query
			String query = " insert into cart (`researchID`,`researchName`,`Amount`,`Description`)" + " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, rID);
			preparedStmt.setString(2, rName);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, desc);
			
			// execute the preparedStatement
			preparedStmt.execute();
			
			con.close();
			
			String newCartItems = readItems();			
			output = "{\"status\":\"success\", \"data\": \"" + newCartItems + "\"}";
			
		}
		catch (Exception e)
		{
			
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the cart.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	//update method
	public String updateItem(String ID, String rID, String rName, String Amont, String desc)
	{
		
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error occured when connecting to the DB for updating.";
			}
			
			// sql query
			String query = "UPDATE cart SET researchID=?,researchName=?,Amount=?,Description=? WHERE ID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, rID);
			preparedStmt.setString(2, rName);
			preparedStmt.setDouble(3, Double.parseDouble(Amont));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, Integer.parseInt(ID));

			// execute the statement
			//testing git hub
			preparedStmt.execute();
			con.close();
			
			String newCartItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newCartItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the cart.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//delete method
	public String deleteItem(String ID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error occured when connecting to the DB for deleting.";
			}
			// sql query
			String query = "delete from cart where ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newCartItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newCartItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the cart.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
