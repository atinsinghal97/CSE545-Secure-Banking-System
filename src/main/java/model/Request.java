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
	private Integer id;

	@Column(name="approval_level_required")
	private String approvalLevelRequired;

	private Boolean approved;

	@Column(name="level_1_approval")
	private Boolean level1Approval;

	@Column(name="level_2_approval")
	private Boolean level2Approval;

	@Column(name="request_id")
	private Integer requestId;

	@Column(name="type_of_request")
	private String typeOfRequest;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="request_assigned_to")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="requested_by")
	private User user2;

	public Request() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApprovalLevelRequired() {
		return this.approvalLevelRequired;
	}

	public void setApprovalLevelRequired(String approvalLevelRequired) {
		this.approvalLevelRequired = approvalLevelRequired;
	}

	public Boolean getApproved() {
		return this.approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Boolean getLevel1Approval() {
		return this.level1Approval;
	}

	public void setLevel1Approval(Boolean level1Approval) {
		this.level1Approval = level1Approval;
	}

	public Boolean getLevel2Approval() {
		return this.level2Approval;
	}

	public void setLevel2Approval(Boolean level2Approval) {
		this.level2Approval = level2Approval;
	}

	public Integer getRequestId() {
		return this.requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public String getTypeOfRequest() {
		return this.typeOfRequest;
	}

	public void setTypeOfRequest(String typeOfRequest) {
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