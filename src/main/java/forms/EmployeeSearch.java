package forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import constants.Constants;

public class EmployeeSearch {
	@NotBlank
	@Pattern(regexp = Constants.userNamePattern, message=Constants.userNameErrorMessage, flags = Flag.UNICODE_CASE)
	public String userName;

    @NotEmpty
    @Email
	public String email;

	@NotBlank
	@Pattern(regexp=Constants.namePattern, message=Constants.nameMessage)
	public String firstName;

	@NotBlank
	@Pattern(regexp=Constants.namePattern, message=Constants.nameMessage)
	public String lastName;

	@Pattern(regexp=Constants.namePattern, message=Constants.nameMessage)
	public String middleName;
	
	@NotBlank
	@Pattern(regexp=Constants.phoneNumberPattern, message=Constants.phoneNumberErrorMessage)
	public String phoneNumber;

	public EmployeeSearch() {
	}

	public EmployeeSearch(String userName,String email,String firstName,String lastName,String middleName,String phoneNumber) {
		this.userName = userName;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.phoneNumber = phoneNumber;
	
	}
	public void print()
	{


	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName=userName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	public String getMiddleName()
	{
		return middleName;
	}
	
	public void setMiddleName(String middleName)
	{
		this.middleName=middleName;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber=phoneNumber;
	}
	
	
	// Getter and Setter methods
}