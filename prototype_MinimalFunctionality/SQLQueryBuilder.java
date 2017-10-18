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
	
	public SQLQueryBuilder()
	{
	}
	
	public SQLQueryBuilder(Task task)
	{
		this.projectNum = Integer.parseInt(task.getProjectNum());
		this.name = task.getName();
		this.dateDue = task.getDateDue();
		this.assignedUser = task.getAssignedUser();
		this.description = task.getDescription();
		this.notes = task.getNotes();
	}
	
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
	
	ArrayList getAllTasksForUser(int ID)
	{
		try
		{
			//String query = "INSERT INTO TASK VALUES(DEFAULT,1,2, " + projectNum + ", '" + name + "', '" + dateDue + "', '" + description + "', '" + notes + "',0); ";
			String query = "SELECT * FROM task WHERE t_user_assigned_ID = 1";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery("SELECT * FROM task");
			
			while (srs.next()) {
				Task task = new Task();
				task.setProjectNum(((Integer)(srs.getInt("t_project_num"))).toString());
				task.setName(srs.getString("t_task_name"));
				task.setDateDue((srs.getString("t_due_date")));
				task.setDescription(srs.getString("t_task_descr"));
				task.setNotes(srs.getString("t_task_notes"));
				tasks.add(task);
				}
			System.out.println(((JDBC4PreparedStatement)s).asSql());

			
			s.execute();
			connection.close();
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
		return tasks;
	}
}
