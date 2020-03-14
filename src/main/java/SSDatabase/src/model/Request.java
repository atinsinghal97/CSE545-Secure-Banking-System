package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the request database table.
 * 
 */
@Entity
@Table(name="request")
@NamedQuery(name="Request.findAll", query="SELECT r FROM Request r")
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="request_id", unique=true, nullable=false)
	private int requestId;

	private boolean approved;

	@Column(name="type_of_account", nullable=false, length=25)
	private String typeOfAccount;

	@Column(name="type_of_request", nullable=false)
	private int typeOfRequest;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="request_assigned_to")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="requested_by", nullable=false)
	private User user2;

	public Request() {
	}

	public int getRequestId() {
		return this.requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public boolean getApproved() {
		return this.approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getTypeOfAccount() {
		return this.typeOfAccount;
	}

	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	public int getTypeOfRequest() {
		return this.typeOfRequest;
	}

	public void setTypeOfRequest(int typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}