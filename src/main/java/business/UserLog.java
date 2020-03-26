package business;

import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;

@Service
public class UserLog {

	private Set<String> usersLogged = new HashSet<String>();
	
	public UserLog()
	{
		
	}
	
	public boolean addLoggedUser(String user)
	{
		if(user != null && !"".equals(user) && !usersLogged.contains(user))
		{
			usersLogged.add(user);
			return true;
		}
		return false;
	}	
	public boolean removeLoggedUser(String user)
	{
		if(user != null && !"".equals(user) && usersLogged.contains(user))
		{
			usersLogged.remove(user);
			return true;
		}
		return false;
	}
	
	public boolean checkUserLoggedIn(String user)
	{
		if(user != null && !"".equals(user) && usersLogged.contains(user))
			return true;
		
		return false;
	}
	
	public void clearList()
	{
		usersLogged.clear();
	}
	
}