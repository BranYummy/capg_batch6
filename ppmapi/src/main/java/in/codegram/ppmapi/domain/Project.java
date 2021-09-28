package in.codegram.ppmapi.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * This Project Class is a domain, which represents data and it will be moving
 * layer to layer.
 * 
 * @author panka
 *
 */
@Entity
public class Project {

	/**
	 * id of the project PK and auto generated
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * name fo the project
	 */
	@NotBlank(message="projectName is required")
	private String projectName;
	/**
	 * project Identifier Should be uniqe and will be used for fetching, deleting
	 * and for relationship with other entity.
	 */
	@NotBlank(message="projectIdentifier is required")
	@Size(min = 4, max=5, message="Size must be between 4 and 5")
	@Column(unique = true, updatable = false)
	private String projectIdentifier;
	/**
	 * description of the project
	 */
	@NotBlank(message="description is required")
	private String description;
	/**
	 * start date of the project
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date start_date;
	/**
	 * end date of the project
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date end_date;
	/**
	 * date at which the project is created
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date created_At;
	/**
	 * date at which the project got updated last.
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date updated_At;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
	private Backlog backlog;
	public Project() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Date getCreated_At() {
		return created_At;
	}
	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}
	public Date getUpdated_At() {
		return updated_At;
	}
	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}
	
	
	
	public Backlog getBacklog() {
		return backlog;
	}
	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_At =  new Date();
	}

}
