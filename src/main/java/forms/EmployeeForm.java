package forms;

import java.text.ParseException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import constants.Constants;
import model.User;

public class EmployeeForm extends UserForm {
	@NotBlank
	@Pattern(regexp=Constants.employeeRolePattern, message=Constants.employeeRoleErrorMessage)
	protected String designation;
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public User createUser(PasswordEncoder passwordEncoder) throws ParseException {
		User user = super.createUser(passwordEncoder);
		user.setRole(this.designation);
		
		return user;
	}
}
