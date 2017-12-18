package taskManager;

import java.sql.Date;
import java.sql.Timestamp;

public class Task {

	private boolean isComplete;
	private boolean isNew;
	private Date dateDue;
	private int assignedUserID;
	private int createdByID;
	private int parentID;
	private int priority;
	private int taskID;
	private String assignedUser;
	private String assignedUserName;
	private String category;
	private String description;
	private String projectNum;
	private String name;
	private String notes;
	private String percentComplete;
	private Timestamp dateCreated;
	private Timestamp lastModified;

	/**
	 * Create a task with specified attributes
	 * @param num the project number of this task
	 * @param parentID the ID of the parent of this task
	 * @param name the name of this task
	 * @param sqlDate the due date of this task
	 * @param assignedUserName the assigned user of this task
	 * @param description the description of this task
	 * @param notes the notes to be added to this task
	 * @param status the status of the completion of this task
	 * @param isNew whether or not this task is a new task
	 * @param category the category of this task
	 * @param inPriority the priority of this task
	 * @param createdByID the ID of the user that created this task
	 */
	public Task(String num, int parentID, String name, Date sqlDate, String assignedUserName, String description,
			String notes, String status, boolean isNew, String category, int inPriority, int createdByID) {
		this.projectNum = num;
		this.name = name;
		this.parentID = parentID;
		this.dateDue = sqlDate;
		this.assignedUserName = assignedUserName;
		this.description = description;
		this.notes = notes;
		this.percentComplete = status;
		this.isNew = isNew;
		this.category = category;
		this.priority = inPriority;
		this.createdByID = createdByID;
	}

	/**
	 * Create a task
	 */
	public Task() {
	}

	/**
	 * Set the ID of this task to a specified Id number
	 * @param id the ID to be set for this class
	 */
	public void setTaskID(int id) {
		taskID = id;
	}

	/**
	 * Get the ID for this task
	 * @return the ID for this task
	 */
	public int getTaskID() {
		return taskID;
	}

	/**
	 * Get the project number for this task
	 * @return the project number for this task
	 */
	public String getProjectNum() {
		return projectNum;
	}

	/**
	 * Set the project number for this task to a specified number
	 * @param name the project number to be set for this task
	 */
	public void setProjectNum(String name) {
		projectNum = name;
	}

	/**
	 * Get the name of this task
	 * @return the name of this task
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this task to the specified value
	 * @param name the name to be set for this task
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the due date of this task
	 * @return the due date of this task
	 */
	public Date getDateDue() {
		return dateDue;
	}

	/**
	 * Set the due date of this task
	 * @param dateDue the due date to be set for this class
	 */
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}

	/**
	 * Get the user ID assigned for this task
	 * @return the user ID assigned for this task
	 */
	public int getAssignedUserID() {
		return assignedUserID;
	}

	/**
	 * Set the assigned user for this task
	 * @param assignedUserID the user to be assigned this task
	 */
	public void setAssignedUserID(int assignedUserID) {
		this.assignedUserID = assignedUserID;
	}

	/**
	 * Get the description of this task
	 * @return the description of this task
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of this task
	 * @param description the description to be set for this task
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the notes for this task
	 * @return the notes for this task
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the notes for this task
	 * @param notes the notes to be set for this task
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Get the assigned user name for this task
	 * @return the assigned user name for this task
	 */
	public String getAssignedUserName() {
		return assignedUserName;
	}

	/**
	 * Set the assigned user name for this task
	 * @param assignedUserName the assigned user name to be set for this task
	 */
	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}

	/**
	 * Tests this task is a new task
	 * @return true if this task is a new task. Otherwise, false.
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * Set whether this task is new or not
	 * @param isNew whether this task is new or not
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * Get the category of this task
	 * @return the category of this task
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Set the category of this task
 * @param category the category of this task
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns true if this task is 100% complete
	 */
	public boolean isComplete() {
		if (this.percentComplete.equals("100%")) {
			isComplete = true;
		} else {
			isComplete = false;
		}
		return isComplete;
	}

	/**
 * Set whether or not this task is complete
	 * @param isComplete the status of whether or not this task is complete
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * Set the percentage of how much of this task has been completed
	 * @param percent how much of this task has been completed
	 */
	public void setPercentComplete(String percent) {
		this.percentComplete = percent;
	}

	/**
	 * Get the percentage of completion of this task
	 * @return the percentage of completion
	 */
	public String getPercentComplete() {
		return percentComplete;
	}

	/**
	 * Edit the attributes of this task
	 * @param num the project number of this task
	 * @param name the name of this task
	 * @param date the due date of this task
	 * @param aUser the assigned user of this task
	 * @param desc the description of this task
	 * @param notes the notes to be added to this task
	 * @param completion the percentage completion of this task
	 * @param category the category of this task
	 * @param inPriority the priority of this task
	 */
	public void edit(String num, String name, Date date, String aUser, String desc, String notes, String completion,
			String category, int inPriority) {
		this.projectNum = num;
		this.name = name;
		this.dateDue = date;
		this.assignedUserName = aUser;
		this.assignedUserID = new SQLQueryBuilder().getIDFromUserName(aUser);
		this.description = desc;
		this.notes = notes;
		this.percentComplete = completion;
		this.category = category;
		this.priority = inPriority;
	}

	/**
	 * Get the assigned user of this task
	 * @return the assigned user
	 */
	public String getAssignedUser() {
		return assignedUser;
	}

	/**
	 * Set the assigned user of this task
	 * @param assignedUser the assigned user of this task
	 */
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	/**
	 * Get the creation date of this task
	 * @return the date this task was created
	 */
	public Timestamp getDateCreated() {
		return dateCreated;
	}

	/**
	 * Set the creation date of this task
	 * @param dateCreated the creation date of this task
	 */
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Get the date this task was last modified
	 * @return The date this task was last modified
	 */
	public Timestamp getLastModified() {
		return lastModified;
	}

	/**
	 * Set the date this task was last modified
	 * @param lastModified the date this task was last modified
	 */
	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Return a String representation of this task
	 */
	@Override
	public String toString() {
		return "" + projectNum + " " + name;
	}

	/**
	 * Get the priority of this task
	 * @return the priority of this task
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Set the priority of this task
	 * @param inPriority the priority of this task
	 */
	public void setPriority(int inPriority) {
		priority = inPriority;
	}

	/**
	 * Get the ID of the parent task
	 * @return the ID of the parent task
	 */
	public int getParentID() {
		return parentID;
	}

	/**
	 * Set the ID of the parent task
	 * @param the ID of the parent task
	 */
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	/**
	 * Get the ID of the user this task was created by
	 * @return The ID of the user this task was created by
	 */
	public int getCreatedByID() {
		return createdByID;
	}

	/**
	 * Set the ID of the user this task was created by
	 * @param createdByID the ID of the user this task was created by
	 */
	public void setCreatedByID(int createdByID) {
		this.createdByID = createdByID;
	}
}
