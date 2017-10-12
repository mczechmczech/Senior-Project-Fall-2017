package prototype;

public class Task {
	private String name;
	private String dateDue;
	private String assignedUser;
	private String description;
	private String notes;
	
	public Task(String name, String dateDue, String assignedUser, String description, String notes) {
		this.name = name;
		this.dateDue = dateDue;
		this.assignedUser = assignedUser;
		this.description = description;
		this.notes = notes;
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
	public String getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(String assignedUser) {
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
}
