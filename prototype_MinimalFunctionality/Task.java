package prototype_MinimalFunctionality;

import java.sql.Date;
import java.sql.Timestamp;

public class Task {
	private String projectNum;
	private String name;
	private Date dateDue;
	private int taskID;
	private int assignedUserID;
	private String description;
	private String notes;
	private String assignedUserName;
	private String percentComplete;
	private boolean isNew;
	private boolean isComplete;
	private String assignedUser;
	private Timestamp dateCreated;
	private Timestamp lastModified;
	private Integer priority;
	
	public Task(String num, String name, Date sqlDate, String assignedUserName, String description, String notes, String status, boolean isNew, int inPriority) {
		this.projectNum = num;
		this.name = name;
		this.dateDue = sqlDate;
		this.assignedUserName = assignedUserName;
		this.description = description;
		this.notes = notes;
		this.percentComplete = status;
		this.isNew = isNew;
		this.priority= inPriority;
	}
	
	public Task()
	{
		
	}
	
	public Task(String name, Date dateDue, String assignedUser, String description, String notes, Timestamp dateCreated, Timestamp lastModified, int inPriority) {
		this.name = name;
		this.dateDue = dateDue;
		this.setAssignedUser(assignedUser);
		this.description = description;
		this.notes = notes;
		this.setDateCreated(dateCreated);
		this.setLastModified(lastModified);
		this.priority= inPriority;
	}
	
	public void setTaskID(int id)
	{
		taskID = id;
	}
	public int getTaskID()
	{
		return taskID;
	}
	
	public String getProjectNum() {
		return projectNum;
	}
	public void setProjectNum(String name) {
		projectNum = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateDue() {
		return dateDue;
	}
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}
	public int getAssignedUserID() {
		return assignedUserID;
	}
	public void setAssignedUserID(int assignedUserID) {
		this.assignedUserID = assignedUserID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getAssignedUserName() {
		return assignedUserName;
	}
	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isComplete() 
	{
		if(this.percentComplete.equals("100%"))
		{
			isComplete = true;
		}
		else
		{
			isComplete = false;
		}
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	public void setPercentComplete(String percent)
	{
		this.percentComplete = percent;
	}
	
	public String getPercentComplete()
	{
		return percentComplete;
	}
	
	public void edit(String num, String name, Date date, String aUser, String desc, String notes, String completion, int inPriority)
	{
		this.projectNum = num;
		this.name = name;
		this.dateDue = date;
		this.assignedUserName = aUser;
		this.assignedUserID = new SQLQueryBuilder().getIDFromUserName(aUser);
		this.description = desc;
		this.notes = notes;
		this.percentComplete = completion;
		this.priority = inPriority;
	}

	public String getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}
	
	@Override
	public String toString()
	{
		return "" + projectNum + " " + name;
	}
	public Integer getPriority()
	{
		return priority;
	}
	public void setPriority(Integer inPriority)
	{
		priority = inPriority;
	}
}
