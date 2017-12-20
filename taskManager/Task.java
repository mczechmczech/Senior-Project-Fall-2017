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
	 * @param num
	 * @param parentID
	 * @param name
	 * @param sqlDate
	 * @param assignedUserName
	 * @param description
	 * @param notes
	 * @param status
	 * @param isNew
	 * @param category
	 * @param inPriority
	 * @param createdByID
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
	 * 
	 */
	public Task() {
	}

	/**
	 * @param id
	 */
	public void setTaskID(int id) {
		taskID = id;
	}

	/**
	 * @return
	 */
	public int getTaskID() {
		return taskID;
	}

	/**
	 * @return
	 */
	public String getProjectNum() {
		return projectNum;
	}

	/**
	 * @param name
	 */
	public void setProjectNum(String name) {
		projectNum = name;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Date getDateDue() {
		return dateDue;
	}

	/**
	 * @param dateDue
	 */
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}

	/**
	 * @return
	 */
	public int getAssignedUserID() {
		return assignedUserID;
	}

	/**
	 * @param assignedUserID
	 */
	public void setAssignedUserID(int assignedUserID) {
		this.assignedUserID = assignedUserID;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return
	 */
	public String getAssignedUserName() {
		return assignedUserName;
	}

	/**
	 * @param assignedUserName
	 */
	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}

	/**
	 * @return
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return
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
	 * @param isComplete
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * @param percent
	 */
	public void setPercentComplete(String percent) {
		this.percentComplete = percent;
	}

	/**
	 * @return
	 */
	public String getPercentComplete() {
		return percentComplete;
	}

	/**
	 * @param num
	 * @param name
	 * @param date
	 * @param aUser
	 * @param desc
	 * @param notes
	 * @param completion
	 * @param category
	 * @param inPriority
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
	 * @return
	 */
	public String getAssignedUser() {
		return assignedUser;
	}

	/**
	 * @param assignedUser
	 */
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	/**
	 * @return
	 */
	public Timestamp getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated
	 */
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return
	 */
	public Timestamp getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified
	 */
	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + projectNum + " " + name;
	}

	/**
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param inPriority
	 */
	public void setPriority(int inPriority) {
		priority = inPriority;
	}

	/**
	 * @return
	 */
	public int getParentID() {
		return parentID;
	}

	/**
	 * @param parentID
	 */
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	/**
	 * @return
	 */
	public int getCreatedByID() {
		return createdByID;
	}

	/**
	 * @param createdByID
	 */
	public void setCreatedByID(int createdByID) {
		this.createdByID = createdByID;
	}
}
