package com.example.hibernate;
// Generated Mar 11, 2020, 5:51:53 PM by Hibernate Tools 5.4.7.Final

/**
 * Request generated by hbm2java
 */
public class Request implements java.io.Serializable {

	private int requestId;
	private User userByRequestAssignedTo;
	private User userByRequestedBy;
	private int typeOfRequest;
	private String typeOfAccount;
	private Boolean approved;

	public Request() {
	}

	public Request(int requestId, User userByRequestedBy, int typeOfRequest, String typeOfAccount) {
		this.requestId = requestId;
		this.userByRequestedBy = userByRequestedBy;
		this.typeOfRequest = typeOfRequest;
		this.typeOfAccount = typeOfAccount;
	}

	public Request(int requestId, User userByRequestAssignedTo, User userByRequestedBy, int typeOfRequest,
			String typeOfAccount, Boolean approved) {
		this.requestId = requestId;
		this.userByRequestAssignedTo = userByRequestAssignedTo;
		this.userByRequestedBy = userByRequestedBy;
		this.typeOfRequest = typeOfRequest;
		this.typeOfAccount = typeOfAccount;
		this.approved = approved;
	}

	public int getRequestId() {
		return this.requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public User getUserByRequestAssignedTo() {
		return this.userByRequestAssignedTo;
	}

	public void setUserByRequestAssignedTo(User userByRequestAssignedTo) {
		this.userByRequestAssignedTo = userByRequestAssignedTo;
	}

	public User getUserByRequestedBy() {
		return this.userByRequestedBy;
	}

	public void setUserByRequestedBy(User userByRequestedBy) {
		this.userByRequestedBy = userByRequestedBy;
	}

	public int getTypeOfRequest() {
		return this.typeOfRequest;
	}

	public void setTypeOfRequest(int typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	public String getTypeOfAccount() {
		return this.typeOfAccount;
	}

	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	public Boolean getApproved() {
		return this.approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

}