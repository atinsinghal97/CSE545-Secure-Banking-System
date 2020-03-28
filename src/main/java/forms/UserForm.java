package forms;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import bankApp.repositories.UserServiceRepository;
import model.User;
import model.UserDetail;


public class UserForm {
    @NotEmpty
    @Email
    private String email;

	@NotBlank
	@Pattern(regexp = "^(?=.*\\d).{4,10}$", message="Password must be between 4 and 8 digits long and include at least one numeric digit.", flags = Flag.UNICODE_CASE)
	private String password;

	@NotBlank
	@Pattern(regexp = "^(?=.*\\d).{4,10}$", message="Password must be between 4 and 10 digits long and include at least one numeric digit.", flags = Flag.UNICODE_CASE)
	private String confirmpassword;

	@NotBlank
	@Pattern(regexp = "^[^!@#~$%^&*\\(\\)-\\+=\\[\\]\\{\\};:'\"<>,/\\?`].+{5,}$", message="Username cannot contain special characters. It can only contain alphabets, numbers, underscores and periods.", flags = Flag.UNICODE_CASE)
	private String username;

	@NotBlank
	private String firstname;

	private String middlename;

	@NotBlank
	private String lastname;

	@NotBlank
	@Pattern(regexp = "^(customer)|(merchant)$", message="You can register only as a customer or a merchant.")
	private String designation;

	@NotBlank
	private String address1;

	@NotBlank
	private String address2;

	@NotBlank
	@Pattern(regexp = "^\\+\\d{11}$", message="Phone number must be of the form +1XXXXXXXXXX (US Customers only)")
	private String phone;

	@NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@Past
	private Date dateOfBirth;

	@NotBlank
	@Pattern(regexp = "^\\d{9}$", message="SSN must consist of numbers of length 9")
	private String ssn;

	@NotBlank
	private String secquestion1;

	@NotBlank
	private String secquestion2;

	@NotBlank
	private String city;

	@NotBlank
	private String province;

	@NotBlank
	@Pattern(regexp = "^\\d+$", message="Zip code must be numbers")
	private String zip;

	@AssertTrue(message="User already exists in the database")
	public boolean getAlreadyExists() {
	  if (this.username == null)
		  return true;
	  try {
		  return !UserServiceRepository.userExists(this.username, this.email, this.ssn);
	  } catch (Exception e) {
		  // Dont care
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
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

	public User createUser(PasswordEncoder passwordEncoder) {
		User user = new User();
		user.setUsername(this.username);
		user.setPassword(passwordEncoder.encode(this.password));
		user.setRole(this.designation);
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
		userDetail.setDateOfBirth(this.dateOfBirth);
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