package prototype_MinimalFunctionality;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.BCrypt;

import com.mysql.jdbc.JDBC4PreparedStatement;

public class SQLQueryBuilder {
	
	private int projectNum;
	private String name;
	private String dateDue;
	private int assignedUserID;
	private String description;
	private String notes;
	private final String url = "jdbc:mysql://localhost:3306/senior";
	private final String username = "root";
	private final String password = "development";
	private ArrayList<Task> tasks = new ArrayList<>();
	
	/**
	 * 
	 * Creates an empty SQLQueryBuilder object
	 * 
	 */
	public SQLQueryBuilder()
	{
	}
	
	/**
	 * 
	 * Creates a SQLQueryBuilder object with the values of the Task object to be added to the database
	 * 
	 * @param task Task object to be added to the database
	 */
	public SQLQueryBuilder(Task task)
	{
		this.projectNum = Integer.parseInt(task.getProjectNum());
		this.name = task.getName();
		this.dateDue = task.getDateDue();
		this.assignedUserID = task.getAssignedUserID();
		this.description = task.getDescription();
		this.notes = task.getNotes();
	}
	
	/**
	 * Adds the values of the task stored in the SQLQueryBuilder instance to the database
	 */
	void addTask()
	{
		try
		{
			//String query = "INSERT INTO TASK VALUES(DEFAULT,1,1, " + projectNum + ", '" + name + "', '" + dateDue + "', '" + description + "', '" + notes + "',0); ";
			String query = "INSERT INTO TASK VALUES(DEFAULT,1,?, ?, ?, ?, ?, ?,0)";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			
			s.setInt(1, 12);
			s.setInt(2, projectNum);
			s.setString(3, name);
			s.setString(4, dateDue);
			s.setString(5, description);
			s.setString(6, notes);
			
			s.execute();
			connection.close();
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
	}
	
	/**
	 * 
	 * Pulls all the tasks assigned to the logged in user that are in the database
	 * 
	 * @param ID The assigned ID of the user that is requesting tasks from the database
	 * @return An ArrayList of Task objects, containing all the tasks that are assigned to the logged in user
	 */
	ArrayList<Task> getTasks(int ID, String table)
	{
		try
		{
			String query = null;
			
			// Determine what subset of tasks are being requested, and set query accordingly
			if(table.equals("user"))
			{
				query = "SELECT * FROM task WHERE t_user_assigned_ID = " + ID;
			}
			else if(table.equals("all"))
			{
				query = "SELECT * FROM task";
			}
			else if(table.equals("inbox"))
			{
				query = "SELECT * FROM task WHERE t_user_assigned_ID = '" + ID + "' AND t_is_new = 1";
			}
			else if(table.equals("archive"))
			{
				
			}
			
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			
			// Loop through the result set, storing each field in a task object, then add that object to an ArrayList
			while (srs.next()) {
				{
					Task task = new Task();
					task.setProjectNum(((Integer)(srs.getInt("t_project_num"))).toString());
					task.setName(srs.getString("t_task_name"));
					task.setDateDue((srs.getString("t_due_date")));
					task.setAssignedUserID(srs.getInt("t_user_assigned_ID"));
					task.setAssignedUserName(getUserNameFromID(ID));
					task.setDescription(srs.getString("t_task_descr"));
					task.setNotes(srs.getString("t_task_notes"));
					task.setComplete(srs.getBoolean(("t_is_complete")));
					task.setIsNew(srs.getBoolean("t_is_new"));
					tasks.add(task);
				}
			}
			
			connection.close();
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
		return tasks;
	}
	
	int getIDFromUsername(String nameOfUser)
	{
		int ID = 0;
		try
		{
			String query = "SELECT * FROM user";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			
			while(srs.next()) {
				if(srs.getString("username").equals(nameOfUser))
				{
					ID = srs.getInt("user_ID");
				}
			}
			connection.close();
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
		return ID;
	}
	
	String getUserNameFromID(int ID)
	{
		String nameOfUser = null;
		try
		{
			String query = "SELECT * FROM user WHERE user_ID = " + ID;
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			
			if(srs.next()) {
				nameOfUser = srs.getString("username");
			}
			connection.close();
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
		return nameOfUser;
	}
	
	/**
	 * 
	 * Checks the given password against the salted hash in the database
	 * 
	 * @param nameOfUser The username typed in by the user
	 * @param passwordOfUser The password typed in by the user
	 * @return An ArrayList of Task objects, containing all the tasks that are assigned to the logged in user
	 */
	boolean checkPassword(String nameOfUser, char[] passwordOfUser)
	{
		try {
			String query = "SELECT * FROM user WHERE username = ?";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			s.setString(1, nameOfUser);
			ResultSet srs = s.executeQuery();
			srs.next();
			String hashed = srs.getString("password");
			
			return BCrypt.checkpw(String.valueOf(passwordOfUser), hashed);
			
		} catch (SQLException e1) {
		    throw new IllegalStateException("Cannot connect to the database!", e1);
		    } 
	}
}
