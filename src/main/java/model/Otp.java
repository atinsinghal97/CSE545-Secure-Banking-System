package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the otp database table.
 * 
 */
@Entity
@Table(name="otp",schema = "secure_banking_system")
@NamedQuery(name="Otp.findAll", query="SELECT o FROM Otp o")
@org.hibernate.annotations.NamedQueries({
    @org.hibernate.annotations.NamedQuery(name = "FindPendingTransactionsByUser", 
      query = "SELECT COUNT(*) > 0 "
      		+ "FROM Otp "
      		+ "WHERE "
      			+ "initator = :user_id AND "
      			+ "completed = FALSE AND "
      			+ "expiry_date > NOW() AND "
      			+ "TIMESTAMPDIFF(MINUTE, creation_date, NOW()) < :offset"),
})
public class Otp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Boolean completed;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiry_date")
	private Date expiryDate;

	private int initator;

	@Column(name="ip_address")
	private String ipAddress;

	private int mode;

	@Column(name="otp_key")
	private String otpKey;

	public Otp() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getCompleted() {
		return this.completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getInitator() {
		return this.initator;
	}

	public void setInitator(int initator) {
		this.initator = initator;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getOtpKey() {
		return this.otpKey;
	}

	public void setOtpKey(String otpKey) {
		this.otpKey = otpKey;
	}

}