package forms;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import constants.Constants;

public class PasswordChange {
	@NotBlank
	private String oldpassword;

	@NotBlank
	@Pattern(regexp=Constants.passwordPattern, message=Constants.passwordErrorMessage, flags = Flag.UNICODE_CASE)
	private String password;

	@NotBlank
	private String confirmpassword;

	@AssertTrue(message="Passwords do not match")
	public boolean getIsValid() {
	  if (this.password == null || this.confirmpassword == null)
		  return true;
	  return this.password.equals(this.confirmpassword);
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
}