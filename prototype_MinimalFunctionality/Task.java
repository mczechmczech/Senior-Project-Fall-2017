package prototype_MinimalFunctionality;

public class Task {
	private String projectNum;
	private String name;
	private String dateDue;
	private int assignedUser;
	private String description;
	private String notes;
	private String assignedUserName;
	
	public Task(String num, String name, String dateDue, int assignedUser, String description, String notes) {
		this.projectNum = num;
		this.name = name;
		this.dateDue = dateDue;
		this.assignedUser = assignedUser;
		this.description = description;
		this.notes = notes;
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
	public int getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(int assignedUser) {
		this.assignedUser = assignedUser;
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
}
