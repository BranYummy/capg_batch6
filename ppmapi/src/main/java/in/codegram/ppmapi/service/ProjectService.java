package in.codegram.ppmapi.service;

import in.codegram.ppmapi.domain.Project;

/**
 * This ProjectService interface will hold the functionality for Project Related Business logic
 * @author panka
 *
 */
public interface ProjectService {
	
	/**
	 * This saveOrUpdate method will save the project if id not provided otherwise update. 
	 * @param project to be saved or updated
	 * @return the Saved or updated project
	 */
	public Project saveOrUpdate(Project project);
	
	public Project findProjectByProjectIdentifier(String projectId);
	
	public Iterable<Project> findAll();
	
	public void deleteProjectByIdentifier(String projectId);

}
