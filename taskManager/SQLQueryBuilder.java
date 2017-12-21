package taskManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.BCrypt;


/**
 * The SQLQueryBuilder class builds SQL queries for the database connected
 * to the taskManager application
 *
 *@version 12.20.2017
 */
public class SQLQueryBuilder {

	private int projectNum;
	private String name;
	private Date dateDue;
	private int parentID;
	private String description;
	private String notes;
	private String assignedUserName;
	private String percentComplete;
	private int isComplete;
	private String category;
	private int priority;
	private ArrayList<Task> tasks = new ArrayList<>();
	private ArrayList<String> users = new ArrayList<>();
	private ArrayList<String> categories = new ArrayList<>();

	private ArrayList<Message> messages = new ArrayList<>();

	/**
	 * Creates an empty SQLQueryBuilder object
	 */
	public SQLQueryBuilder() {
	}

	/**
	 * Creates a SQLQueryBuilder object with the values of the Task object to be
	 * added to the database
	 * 
	 * @param task
	 *            Task object to be added to the database
	 */
	public SQLQueryBuilder(Task task) {
		task.getTaskID();
		this.projectNum = Integer.parseInt(task.getProjectNum());
		this.name = task.getName();
		this.parentID = task.getParentID();
		this.dateDue = task.getDateDue();
		this.description = task.getDescription();
		this.notes = task.getNotes();
		this.percentComplete = task.getPercentComplete();
		this.assignedUserName = task.getAssignedUserName();
		this.priority = task.getPriority();
		this.category = task.getCategory();
		this.isComplete = task.isComplete() ? 1 : 0;
	}

	/**
	 * Adds the values of the task stored in the SQLQueryBuilder instance to the
	 * database
	 * 
	 * @param user the user name of the user
	 * @param password the password of the user
	 * @param first the first name of the user
	 * @param last the last name of the user
	 * @param admin whether the user has administrator privileges
	 */
	void addUser(String user, String password, String first, String last, boolean admin) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "INSERT INTO USER VALUES(DEFAULT, ?, ?, ?, ?, ?, 1, 4)";

			PreparedStatement s = connection.prepareStatement(query);

			s.setString(1, user);
			s.setString(2, password);
			s.setString(3, first);
			s.setString(4, last);
			s.setInt(5, admin ? 1 : 0);
			s.execute();

			connection.close();
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot register user!", e);
		}
	}

	/**
	 * Adds the values of the task stored in the SQLQueryBuilder instance to the
	 * database
	 * 
	 * @param ID the ID of the task that will be added
	 * @param isNew whether the tasks being added is a new task
	 */
	void addTask(int ID, boolean isNew) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "INSERT INTO TASK VALUES(DEFAULT,?, ?, ?, ?, ?, ?, ?, ?,0,?,0,?,?,DEFAULT,DEFAULT, ?)";

			PreparedStatement s = connection.prepareStatement(query);
			s.setInt(1, parentID);
			s.setInt(2, ID);
			s.setInt(3, getIDFromUserName(assignedUserName));
			s.setInt(4, projectNum);
			s.setString(5, name);
			s.setDate(6, dateDue);
			s.setString(7, description);
			s.setString(8, notes);
			s.setInt(9, isNew ? 1 : 0);
			s.setString(10, category);
			s.setString(11, percentComplete);
			s.setInt(12, priority);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot add task!", e);
		}
	}

	/**
	 * Edits the values of the specified task in the database
	 * 
	 * @param taskIDNum the ID number of the task that will be edited
	 */
	void editTask(int taskIDNum) {
		int assignedID = getIDFromUserName(assignedUserName);
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "UPDATE senior.TASK SET user_assigned_ID = ?, project_num = ?, task_name = ?,  due_date = ?, task_descr = ?, "
					+ "task_notes = ?, percent_complete = ?, is_complete = ?, category = ?, priority = ? WHERE task_ID = ?;";

			PreparedStatement s = connection.prepareStatement(query);
			s.setInt(1, assignedID);
			s.setInt(2, projectNum);
			s.setString(3, name);
			s.setDate(4, dateDue);
			s.setString(5, description);
			s.setString(6, notes);
			s.setString(7, percentComplete);
			s.setInt(8, isComplete);
			s.setString(9, category);
			s.setInt(10, priority);
			s.setInt(11, taskIDNum);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot edit task!", e);
		}
	}

	//
	/**
	 * Accepts a task to the database
	 * 
	 * @param taskIDNum the ID number of the accepted task
	 */
	void taskAccepted(int taskIDNum) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "UPDATE senior.TASK SET is_new = ? WHERE task_ID = ?;";

			PreparedStatement s = connection.prepareStatement(query);
			s.setInt(1, 0);
			s.setInt(2, taskIDNum);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot accept task!", e);
		}
	}

	/**
	 * Edits is_trash value of task when task has been deleted from a table
	 * 
	 * @param taskIDNum the ID number of the task that will be edited
	 */
	void putInTrash(int taskIDNum) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "UPDATE senior.TASK SET is_trash = ? WHERE task_ID = ?;";

			PreparedStatement s = connection.prepareStatement(query);
			s.setInt(1, 1);
			s.setInt(2, taskIDNum);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot put task in trash!", e);
		}
	}

	/**
	 * Deletes the specified task from the trash table in the database
	 * 
	 * @param taskIDNum the ID number of the task that will be deleted from 
	 * the trash table
	 */
	void deleteFromTrash(int taskIDNum) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "DELETE FROM senior.TASK WHERE task_ID = ?;";

			PreparedStatement s = connection.prepareStatement(query);
			s.setInt(1, taskIDNum);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot delete task from trash!", e);
		}
	}

	/**
	 * Used to retrieve a task from the trash table
	 * 
	 * @param taskIDNum
	 */
	void retrieveFromTrash(int taskIDNum) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "UPDATE senior.TASK SET is_trash = ? WHERE task_ID = ?;";

			PreparedStatement s = connection.prepareStatement(query);
			s.setInt(1, 0);
			s.setInt(2, taskIDNum);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot retrieve task from trash!", e);
		}
	}

	/**
	 * 
	 * Catch-all function for pulling lists of tasks from the database.
	 * 
	 * @param ID  The assigned ID of the user that is requesting tasks from the
	 *            database
	 * @param table The table that is being updated. Current options: user - Updates
	 *            the table of tasks assigned to the logged in user all - Updates
	 *            the table of all tasks in the database inbox - Updates the table
	 *            of tasks newly assigned to the logged in user archive - Updates
	 *            the table of tasks assigned to the logged in user that have been
	 *            marked as complete TODO: add rest of possible parameters
	 * @return An ArrayList of Task objects, containing all the tasks that are 
	 * 			  assigned to the logged in user
	 */
	ArrayList<Task> getTasks(int ID, String table, String search) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = null;

			// Determine what subset of tasks are being requested, and set query accordingly
			query = table.equals(
					"user") ? "SELECT * FROM TASK WHERE user_assigned_ID = " + ID + " AND is_complete = 0" + " AND is_new = 0" + " AND is_trash = 0" : table.equals("all") ? "SELECT * FROM TASK" + " WHERE is_complete = 0" + " AND is_new = 0" + " AND is_trash = 0" : table.equals("inboxTasks") ? "SELECT * FROM TASK WHERE user_assigned_ID = '" + ID + "' AND is_new = 1" + " AND is_complete = 0" + " AND is_trash = 0" : table.equals("sentTasks") ? "SELECT * FROM TASK WHERE user_created_ID = '" + ID + "' AND is_complete = 0" + " AND is_trash = 0" + " AND user_assigned_ID != '" + ID + "'" : table.equals("archive") ? "SELECT * FROM TASK WHERE user_assigned_ID = '" + ID + "' AND is_complete = 1" + " AND is_new = 0" + " AND is_trash = 0" : table.equals("allArchive") ? "SELECT * FROM TASK WHERE is_complete = 1" + " AND is_new = 0" + " AND is_trash = 0" : table.equals("trashReceivedTasks") ? "SELECT * FROM TASK WHERE user_assigned_ID = '" + ID + "' AND is_trash = 1" + " AND is_new = 0" : table.equals("trashSentTasks") ? "SELECT * FROM TASK WHERE user_created_ID = '" + ID + "' AND is_trash = 1" + " AND is_new = 0" : table.equals("created") ? "SELECT * FROM TASK WHERE user_created_ID = '" + ID + "' AND is_trash = 0" : null;

			query += search.equals("") ? ""
					: " AND ((task_name LIKE '%" + search + "%') OR (due_date LIKE '%" + search
							+ "%') OR (task_descr LIKE '%" + search + "%') OR (task_notes LIKE '%" + search + "%'))";

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			// Loop through the result set, storing each field in a task object, then add
			// that object to an ArrayList
			while (srs.next()) {
				Task task = new Task();
				task.setProjectNum(((Integer) (srs.getInt("project_num"))).toString());
				task.setTaskID(srs.getInt("task_ID"));
				task.setParentID(srs.getInt("parent_ID"));
				task.setName(srs.getString("task_name"));
				task.setDateDue(srs.getDate("due_date"));
				task.setAssignedUserID(srs.getInt("user_assigned_ID"));
				task.setAssignedUserName(getUserNameFromID(srs.getInt("user_assigned_ID")));
				task.setDescription(srs.getString("task_descr"));
				task.setNotes(srs.getString("task_notes"));
				task.setPercentComplete(srs.getString("percent_complete"));
				task.setComplete(srs.getBoolean(("is_complete")));
				task.setIsNew(srs.getBoolean("is_new"));
				task.setDateCreated(srs.getTimestamp("date_created"));
				task.setLastModified(srs.getTimestamp("last_modified"));
				task.setCategory(srs.getString("category"));
				task.setPriority(srs.getInt("priority"));
				task.setCreatedByID(srs.getInt("user_created_ID"));

				// Check that prevents default parent task from being displayed in any table
				if (!(Integer.parseInt(task.getProjectNum()) == 0)) {
					tasks.add(task);
				}
			}

			connection.close();
			return tasks;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot get tasks!", e);
		}
	}

	/**
	 * Returns all the subtasks of a specified task in the database
	 * 
	 * @param taskID the ID of the task of which the subtasks will be returned
	 * @return all the subtasks of the specified task
	 */
	ArrayList<Task> getSubTasks(int taskID) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM TASK WHERE parent_ID = " + taskID + " AND is_trash = 0";

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			// Loop through the result set, storing each field in a task object, then add
			// that object to an ArrayList
			while (srs.next()) {
				Task task = new Task();
				task.setProjectNum(((Integer) (srs.getInt("project_num"))).toString());
				task.setTaskID(srs.getInt("task_ID"));
				task.setParentID(srs.getInt("parent_ID"));
				task.setName(srs.getString("task_name"));
				task.setDateDue(srs.getDate("due_date"));
				task.setAssignedUserID(srs.getInt("user_assigned_ID"));
				task.setAssignedUserName(getUserNameFromID(srs.getInt("user_assigned_ID")));
				task.setDescription(srs.getString("task_descr"));
				task.setNotes(srs.getString("task_notes"));
				task.setPercentComplete(srs.getString("percent_complete"));
				task.setComplete(srs.getBoolean(("is_complete")));
				task.setIsNew(srs.getBoolean("is_new"));
				task.setDateCreated(srs.getTimestamp("date_created"));
				task.setLastModified(srs.getTimestamp("last_modified"));
				task.setPriority(srs.getInt("priority"));
				tasks.add(task);
				System.out.println(task.toString());
			}

			connection.close();
			return tasks;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot get subtasks!", e);
		}
	}

	/**
	 * Creates a new message in the database
	 * 
	 * @param receiverID 
	 * @param message
	 * @param senderID
	 */
	void newMessage(int receiverID, String message, int senderID) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "INSERT INTO MESSAGE VALUES(DEFAULT, ?, ?, ?, 0, 0, 0, 0)";

			PreparedStatement s = connection.prepareStatement(query);

			s.setInt(1, receiverID);
			s.setString(2, message);
			s.setInt(3, senderID);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot create new message!", e);
		}
	}

	/**
	 * Catch-all function for pulling lists of messages from the database.
	 * 
	 * @param ID the assigned ID of the user that is requesting tasks from the
	 *            database
	 * @param table the table that is being updated. Current options: inboxMessages -
	 *            Updates the table of messages sent to the logged in user
	 *            sentMessages - Updates the table of messages sent by the logged in
	 *            user
	 * @return An ArrayList of Message objects, containing all the messages that are
	 *         corresponding to the logged in user
	 */
	ArrayList<Message> getMessages(int ID, String table) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = null;

			// Determine what subset of messages are being requested, and set query
			// accordingly
			query = table.equals("inboxMessages")
					? "SELECT * FROM MESSAGE WHERE user_received_ID = '" + ID + "' AND user_received_is_trash = 0"
							+ " AND user_received_remove_trash = 0"
					: table.equals("sentMessages")
							? "SELECT * FROM MESSAGE WHERE user_created_ID = '" + ID + "' AND user_created_is_trash = 0"
									+ " AND user_created_remove_trash = 0"
							: table.equals("inboxMessagesTrash")
									? "SELECT * FROM MESSAGE WHERE user_received_ID = '" + ID
											+ "' AND user_received_is_trash = 1" + " AND user_received_remove_trash = 0"
									: table.equals("sentMessagesTrash")
											? "SELECT * FROM MESSAGE WHERE user_created_ID = '" + ID
													+ "' AND user_created_is_trash = 1"
													+ " AND user_created_remove_trash = 0"
											: null;

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			// Loop through the result set, storing each field in a message object, then add
			// that object to an ArrayList
			while (srs.next()) {
				Message message = new Message();
				int receiverID = srs.getInt("user_received_ID");
				int senderID = srs.getInt("user_created_ID");
				message.setReceiver(new SQLQueryBuilder().getUserNameFromID(receiverID));
				message.setSender(new SQLQueryBuilder().getUserNameFromID(senderID));
				message.setMessage(srs.getString("message"));
				message.setMessageID(srs.getInt("message_ID"));
				messages.add(message);
			}
			connection.close();
			return messages;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot get messages!", e);
		}
	}

	/**
	 * Place the specified message in the trash table
	 * 
	 * @param messageID the message ID to be placed in the trash
	 * @param table the table containing the messages
	 */
	void putMessageInTrash(int messageID, String table) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = null;

			// Determine what subset of messages are being requested, and set query
			// accordingly
			query = table.equals("inboxMessages")
					? "UPDATE senior.MESSAGE SET user_received_is_trash = 1 WHERE message_ID = " + messageID
					: table.equals("sentMessages")
							? "UPDATE senior.MESSAGE SET user_created_is_trash = 1 WHERE message_ID = " + messageID
							: null;
			PreparedStatement s = connection.prepareStatement(query);
			s.execute();

			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot put message in trash!", e);
		}
	}

	/**
	 * @param messageID the ID of the message to be removed from trash
	 * @param table the table containing the message to be removed from trash
	 */
	void removeMessageFromTrash(int messageID, String table) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = null;

			// Determine what subset of messages are being requested, and set query
			// accordingly
			query = table.equals("inboxMessages")
					? "UPDATE senior.MESSAGE SET user_received_remove_trash = 1 WHERE message_ID = " + messageID
					: table.equals("sentMessages")
							? "UPDATE senior.MESSAGE SET user_created_remove_trash = 1 WHERE message_ID = " + messageID
							: null;
			PreparedStatement s = connection.prepareStatement(query);
			s.execute();

			query = "DELETE FROM MESSAGE WHERE message_ID = '" + messageID + "' AND user_received_is_trash = 1"
					+ " AND user_received_remove_trash = 1" + "  AND user_created_is_trash = 1"
					+ " AND user_created_remove_trash = 1";

			PreparedStatement s2 = connection.prepareStatement(query);
			s2.execute();
			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot remove message from trash!", e);
		}
	}

	/**
	 * Adds a category to the database
	 * 
	 * @param cat the category to be added to the database
	 */
	void addCategory(String cat) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "INSERT INTO CATEGORY VALUES(?)";

			PreparedStatement s = connection.prepareStatement(query);
			s.setString(1, cat);
			s.execute();
			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot add category!", e);
		}
	}

	/**
	 * Return all the categories in the taskManager application
	 * 
	 * @return all the categories in the taskManager application
	 */
	ArrayList<String> getCategories() {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM CATEGORY";

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery();
			while (srs.next()) {
				categories.add(srs.getString("category"));
			}
			connection.close();
			return categories;
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot get categories!", e);
		}
	}

	/**
	 * Return true if the specified category exists
	 * 
	 * @param cat the category of which we are searching
	 * @return true if the specified category exists
	 */
	boolean containsCategory(String cat) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM CATEGORY";

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery();
			while (srs.next()) {
				if (cat.equals(srs.getString("category")))
					return true;
			}
			connection.close();
			return false;
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot compare categories!", e);
		}
	}
	
	/**
	 * Returns whether or not a specific user is an admin
	 * 
	 * @param user
	 * 			username of the user to be checked to see if that user is an admin
	 * 
	 * @return boolean
	 * 			true if user is an admin, false if user is not an admin
	 */
	boolean isAdmin(String user) 
	{
		int uID = getIDFromUserName(user);
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM USER WHERE user_ID = '" + uID + "'";
		
			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery();
			while (srs.next()) 
			{
				return(srs.getBoolean("is_admin"));
			}
			connection.close();
				return false;
			} catch (SQLException e) 
			  {
				throw new IllegalStateException("Cannot find boolean is_admin", e);
			  }
	}

	/**
	 * Return all the users in the taskManager application
	 * 
	 * @return all the users in the taskManager application
	 */
	ArrayList<String> getUsers() {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM USER";

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery();
			while (srs.next()) {
				users.add(srs.getString("username"));
			}
			connection.close();
			return users;
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot get users!", e);
		}
	}

	/**
	 * Return the user created ID
	 * 
	 * @param taskID the ID of the task
	 * @return the user created ID
	 */
	int getUserCreatedID(int taskID) {
		int userCreated = 0;
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM TASK WHERE task_ID = " + taskID;

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);
			if (srs.next()) {
				userCreated = srs.getInt("user_created_ID");
			}
			connection.close();
			return userCreated;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot get UserCreatedID!", e);
		}
	}

	/**
	 * Converts a user ID number into the corresponding username
	 * 
	 * @param ID the ID number of the username to be looked up
	 * @return the username corresponding to the given ID number
	 */
	String getUserNameFromID(int ID) {
		String nameOfUser = null;
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM USER WHERE user_ID = " + ID;

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);

			if (srs.next()) {
				nameOfUser = srs.getString("username");
			}
			connection.close();
			return nameOfUser;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot get username from ID!", e);
		}
	}

	/**
	 * Converts a username into the corresponding user ID
	 * 
	 * @param nameOfUser the username of the user ID to be looked up
	 * @return the user ID corresponding to the given username
	 */
	int getIDFromUserName(String nameOfUser) {
		int ID = 0;
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM USER";

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet srs = s.executeQuery(query);

			while (srs.next()) {
				if (srs.getString("username").equals(nameOfUser))
					ID = srs.getInt("user_ID");
			}
			connection.close();
			return ID;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot get ID from username!", e);
		}
	}

	/**
	 * Checks the given password against the salted hash in the database
	 * 
	 * @param nameOfUser the username typed in by the user
	 * @param passwordOfUser the password typed in by the user
	 * @return an ArrayList of Task objects, containing all the tasks that are assigned to the logged in user
	 */
	boolean checkPassword(String nameOfUser, char[] passwordOfUser) {
		try (Connection connection = ConnectionPool.getConnection()) {
			String query = "SELECT * FROM USER WHERE username = ?";

			PreparedStatement s = connection.prepareStatement(query);
			s.setString(1, nameOfUser);
			ResultSet srs = s.executeQuery();
			srs.next();
			String hashed = srs.getString("password");

			return BCrypt.checkpw(String.valueOf(passwordOfUser), hashed);
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot check password!", e);
		}
	}
}
