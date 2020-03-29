package forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.security.crypto.password.PasswordEncoder;

import bankApp.repositories.UserServiceRepository;
import model.User;
import model.UserDetail;

import constants.Constants;


public class UserForm {
    @NotEmpty
    @Email
    protected String email;

	@NotBlank
	@Pattern(regexp=Constants.passwordPattern, message=Constants.passwordErrorMessage, flags = Flag.UNICODE_CASE)
	protected String password;

	@NotBlank
	@Pattern(regexp=Constants.passwordPattern, message=Constants.passwordErrorMessage, flags = Flag.UNICODE_CASE)
	protected String confirmpassword;

	@NotBlank
	@Pattern(regexp=Constants.userNamePattern, message=Constants.userNameErrorMessage, flags = Flag.UNICODE_CASE)
	protected String username;

	@NotBlank
	@Pattern(regexp=Constants.namePattern, message=Constants.nameMessage)
	protected String firstname;

	@Pattern(regexp=Constants.namePattern, message=Constants.nameMessage)
	protected String middlename;

	@NotBlank
	@Pattern(regexp=Constants.namePattern, message=Constants.nameMessage)
	protected String lastname;

	@NotBlank
	@Pattern(regexp=Constants.addressPattern, message=Constants.addressErrorMessage)
	protected String address1;

	@NotBlank
	@Pattern(regexp=Constants.addressPattern, message=Constants.addressErrorMessage)
	protected String address2;

	@NotBlank
	@Pattern(regexp=Constants.phoneNumberPattern, message=Constants.phoneNumberErrorMessage)
	protected String phone;

	@NotBlank
	@Pattern(regexp=Constants.dateOfBirthPattern, message=Constants.dateOfBirthErrorMessage)
	protected String dateOfBirth;

	@NotBlank
	@Pattern(regexp=Constants.ssnPattern, message=Constants.ssnErrorMessage)
	protected String ssn;

	@NotBlank
	@Pattern(regexp=Constants.secPattern, message=Constants.secMessage)
	protected String secquestion1;

	@NotBlank
	@Pattern(regexp=Constants.secPattern, message=Constants.secMessage)
	protected String secquestion2;

	@NotBlank
	@Pattern(regexp=Constants.locationPattern, message=Constants.locationErrorMessage)
	protected String city;

	@NotBlank
	@Pattern(regexp=Constants.locationPattern, message=Constants.locationErrorMessage)
	protected String province;

	@NotBlank
	@Pattern(regexp=Constants.zipPattern, message=Constants.zipErrorMessage)
	protected String zip;

	@AssertTrue(message="User already exists in the database")
	public boolean getAlreadyExists() {
	  if (this.username == null)
		  return true;
	  try {
		  return !UserServiceRepository.userExists(this.username, this.email, this.ssn);
	  } catch (Exception e) {
		  e.printStackTrace();
		  // Dont care
	  }
	  return true;
	}

	@AssertTrue(message="Date of birth must of the format MM/DD/YYYY and must be in the past")
	public boolean getIsValidDate() {
	  if (this.dateOfBirth == null)
		  return true;
	  
	  try {
		  Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateOfBirth);
		  if (date.after(new Date()))
			  return false;
	  } catch (Exception e) {
		  return false;
	  }

	  return true;
	}
	
	@AssertTrue(message="Passwords do not match")
	public boolean getIsValid() {
	  if (this.password == null || this.confirmpassword == null)
		  return true;
	  return this.password.equals(this.confirmpassword);
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSecquestion1() {
		return secquestion1;
	}

	public void setSecquestion1(String secquestion1) {
		this.secquestion1 = secquestion1;
	}

	public String getSecquestion2() {
		return secquestion2;
	}

	public void setSecquestion2(String secquestion2) {
		this.secquestion2 = secquestion2;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public User createUser(PasswordEncoder passwordEncoder) throws ParseException {
		User user = new User();
		user.setUsername(this.username);
		user.setPassword(passwordEncoder.encode(this.password));
		user.setStatus(0);
		user.setIncorrectAttempts(0);
		user.setCreatedDate(new Date());
		user.setModifiedDate(new Date());

		UserDetail userDetail;
		userDetail = new UserDetail();
		userDetail.setFirstName(this.firstname);
		userDetail.setMiddleName(this.middlename);
		userDetail.setLastName(this.lastname);
		userDetail.setEmail(this.email);
		userDetail.setPhone(this.phone);
		userDetail.setAddress1(this.address1);
		userDetail.setAddress2(this.address2);
		userDetail.setCity(this.city);
		
		Date date = new SimpleDateFormat("mm/dd/yyyy").parse(dateOfBirth);
		
		userDetail.setDateOfBirth(date);
		userDetail.setProvince(this.province);
		userDetail.setSsn(this.ssn);
		userDetail.setZip(Long.parseLong(this.zip));
		userDetail.setQuestion1(this.secquestion1);
		userDetail.setQuestion2(this.secquestion2);

		userDetail.setUser(user);
		user.setUserDetail(userDetail);
		return user;
	}
}