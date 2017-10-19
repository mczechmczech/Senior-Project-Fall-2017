package prototype_MinimalFunctionality;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.JDBC4PreparedStatement;

public class SQLQueryBuilder {
	
	private int projectNum;
	private String name;
	private String dateDue;
	private String assignedUser;
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
		this.assignedUser = task.getAssignedUser();
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
			String query = "INSERT INTO TASK VALUES(DEFAULT,1,1, ?, ?, ?, ?, ?,0)";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			
			s.setInt(1, projectNum);
			s.setString(2, name);
			s.setString(3, dateDue);
			s.setString(4, description);
			s.setString(5, notes);
			
			System.out.println(((JDBC4PreparedStatement)s).asSql());

			
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
	ArrayList<Task> getAllTasksForUser(int ID)
	{
		try
		{
			// First we get the username of the logged in user
			String query = "SELECT * FROM user WHERE user_ID = ?";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			s.setInt(1, ID);
			ResultSet srs = s.executeQuery("SELECT * FROM user");
			srs.next();
			String userName = srs.getString("username");
			
			// Now we get all rows in the task table that are assigned to that user and store them in a ResultSet
			query = "SELECT * FROM task WHERE t_user_assigned_ID = ?";
			connection = DriverManager.getConnection(url, username, password);
			
			s = connection.prepareStatement(query);
			s.setInt(1, ID);
			srs = s.executeQuery("SELECT * FROM task");
			
			// Loop through the result set, storing each field in a task object, then add that object to an ArrayList
			while (srs.next()) {
				Task task = new Task();
				task.setProjectNum(((Integer)(srs.getInt("t_project_num"))).toString());
				task.setName(srs.getString("t_task_name"));
				task.setDateDue((srs.getString("t_due_date")));
				task.setAssignedUser(userName);
				task.setDescription(srs.getString("t_task_descr"));
				task.setNotes(srs.getString("t_task_notes"));
				tasks.add(task);
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
	
//	ArrayList<Task> getAllTasks()
//	{
//		try
//		{
//			// First we get the username of the logged in user
//			String query = "SELECT * FROM user WHERE user_ID = ?";
//			Connection connection = DriverManager.getConnection(url, username, password);
//			
//			PreparedStatement s = connection.prepareStatement(query);
//			s.setInt(1, ID);
//			ResultSet srs = s.executeQuery("SELECT * FROM user");
//			srs.next();
//			String userName = srs.getString("username");
//			
//			// Now we get all rows in the task table that are assigned to that user and store them in a ResultSet
//			query = "SELECT * FROM task WHERE t_user_assigned_ID = ?";
//			connection = DriverManager.getConnection(url, username, password);
//			
//			s = connection.prepareStatement(query);
//			s.setInt(1, ID);
//			srs = s.executeQuery("SELECT * FROM task");
//			
//			// Loop through the result set, storing each field in a task object, then add that object to an ArrayList
//			while (srs.next()) {
//				Task task = new Task();
//				task.setProjectNum(((Integer)(srs.getInt("t_project_num"))).toString());
//				task.setName(srs.getString("t_task_name"));
//				task.setDateDue((srs.getString("t_due_date")));
//				task.setAssignedUser(userName);
//				task.setDescription(srs.getString("t_task_descr"));
//				task.setNotes(srs.getString("t_task_notes"));
//				tasks.add(task);
//				}
//			
//			connection.close();
//		}
//		catch (Exception e)
//	    {
//	      System.err.println("Got an exception!");
//	      System.err.println(e.getMessage());
//	    }
//		return tasks;
//	}
}
