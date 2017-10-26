package prototype_MinimalFunctionality;

public class Task {
	private String projectNum;
	private String name;
	private String dateDue;
	private int assignedUserID;
	private String description;
	private String notes;
	private String assignedUserName;
	private boolean isNew;
	private boolean isComplete;
	
	public Task(String num, String name, String dateDue, int assignedUserID, String description, String notes, boolean isNew) {
		this.projectNum = num;
		this.name = name;
		this.dateDue = dateDue;
		this.setAssignedUserID(assignedUserID);
		this.description = description;
		this.notes = notes;
		this.isNew = isNew;
	}
	
	public Task()
	{
		
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
	public String getDateDue() {
		return dateDue;
	}
	public void setDateDue(String dateDue) {
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

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
}
