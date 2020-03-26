package forms;

public class AppSearch {
	public String username;
	public String status;

	public AppSearch() {
	}

	public AppSearch(String username,String status) {
		this.username = username;
		this.status = status;
	}
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status=status;
	}
	// Getter and Setter methods
}