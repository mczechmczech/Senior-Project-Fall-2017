package prototype_MinimalFunctionality;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.BCrypt;

public class SQLQueryBuilder {
	
	private int projectNum;
	private String name;
	private String dateDue;
	private String description;
	private String notes;
	private String assignedUserName;
	private String percentComplete;
    private int isComplete;
    private int taskIDNum;
	private final String url = "jdbc:mysql://ec2-184-73-45-179.compute-1.amazonaws.com:3306/senior";
	private final String username = "seniorUser";
	private final String password = "seniorUser";
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
		this.taskIDNum = task.getTaskID();
		this.projectNum = Integer.parseInt(task.getProjectNum());
		this.name = task.getName();
		this.dateDue = task.getDateDue();
		this.description = task.getDescription();
		this.notes = task.getNotes();
		this.percentComplete = task.getPercentComplete();
		this.assignedUserName = task.getAssignedUserName();
		if(task.isComplete())
		{
			this.isComplete = 1;
		}
		else
		{
			this.isComplete = 0;
		}
	}
	
	/**
	 * Adds the values of the task stored in the SQLQueryBuilder instance to the database
	 */
	void addTask(int ID)
	{
		try
		{
			String query = "INSERT INTO TASK VALUES(DEFAULT,DEFAULT, ?, ?, ?, ?, ?, ?, ?,0,1,?,DEFAULT,DEFAULT)";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			
			s.setInt(1, ID);
			System.out.println(getIDFromUserName(assignedUserName));
			s.setInt(2, getIDFromUserName(assignedUserName));
			s.setInt(3, projectNum);
			s.setString(4, name);
			s.setString(5, dateDue);
			s.setString(6, description);
			s.setString(7, notes);
			s.setString(8, percentComplete);
			s.execute();
			
			System.out.println(s.toString());
			connection.close();
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
	}
	
	void editTask(int taskIDNum)
	{
		int assignedID = getIDFromUserName(assignedUserName);
		try
		{
			String query = "UPDATE senior.TASK SET user_assigned_ID=" + assignedID + ", project_num=" 
					+ projectNum + ", task_name=\"" + name  + "\",  due_date=\"" + dateDue + "\", task_descr=\"" 
					+ description + "\", task_notes=\"" + notes + "\", percent_complete=\"" + percentComplete 
					+ "\", is_complete=" + isComplete + " WHERE task_ID= " + taskIDNum + ";";
			
                        
            Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement s = connection.prepareStatement(query);
			
			s.executeUpdate(query);
			s.execute(query);
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
	 * Catch-all function for pulling lists of tasks from the database. 
	 * 
	 * @param ID The assigned ID of the user that is requesting tasks from the database
	 * @param table The table that is being updated. Current options:
	 * 				user - Updates the table of tasks assigned to the logged in user
	 * 				all - Updates the table of all tasks in the database
	 * 				inbox - Updates the table of tasks newly assigned to the logged in user
	 * 				archive - Updates the table of tasks assigned to the logged in user that have been marked as complete
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
				query = "SELECT * FROM TASK WHERE user_assigned_ID = " + ID  + " AND is_complete = 0";
			}
			else if(table.equals("all"))
			{
				query = "SELECT * FROM TASK"  + " WHERE is_complete = 0";
			}
			else if(table.equals("inbox"))
			{
				query = "SELECT * FROM TASK WHERE user_assigned_ID = '" + ID + "' AND is_new = 1" + " AND is_complete = 0";
			}
			else if(table.equals("archive"))
			{
				query = "SELECT * FROM TASK WHERE user_assigned_ID = '" + ID + "' AND is_complete = 1"  + " AND is_complete = 1";
			}
			
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			
			// Loop through the result set, storing each field in a task object, then add that object to an ArrayList
			while (srs.next()) {
				{
					Task task = new Task();
					task.setProjectNum(((Integer)(srs.getInt("project_num"))).toString());
					task.setTaskID(srs.getInt("task_ID"));
					task.setName(srs.getString("task_name"));
					task.setDateDue((srs.getString("due_date")));
					task.setAssignedUserID(srs.getInt("user_assigned_ID"));
					task.setAssignedUserName(getUserNameFromID(srs.getInt("user_assigned_ID")));
					task.setDescription(srs.getString("task_descr"));
					task.setNotes(srs.getString("task_notes"));
					task.setPercentComplete(srs.getString("percent_complete"));
					task.setComplete(srs.getBoolean(("is_complete")));
					task.setIsNew(srs.getBoolean("is_new"));
					task.setDateCreated(srs.getTimestamp("date_created"));
					task.setLastModified(srs.getTimestamp("last_modified"));
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
	
	/**
	 * Converts a user ID number into the corresponding username
	 * 
	 * @param ID The ID number of the username to be looked up
	 * @return The username corresponding to the given ID number
	 */
	String getUserNameFromID(int ID)
	{
		String nameOfUser = null;
		try
		{
			String query = "SELECT * FROM USER WHERE user_ID = " + ID;
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
	 * Converts a username into the corresponding user ID
	 * 
	 * @param nameOfUser The username of the user ID to be looked up
	 * @return The user ID corresponding to the given username
	 */
	int getIDFromUserName(String nameOfUser)
	{
		int ID = 2;
		try
		{
			String query = "SELECT * FROM USER";
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
			String query = "SELECT * FROM USER WHERE username = ?";
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
