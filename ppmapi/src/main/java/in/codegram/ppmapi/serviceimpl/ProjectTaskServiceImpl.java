package in.codegram.ppmapi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.codegram.ppmapi.domain.Backlog;
import in.codegram.ppmapi.domain.ProjectTask;
import in.codegram.ppmapi.exception.ProjectIDException;
import in.codegram.ppmapi.repository.BacklogRepository;
import in.codegram.ppmapi.repository.ProjectTaskRepository;
import in.codegram.ppmapi.service.ProjectTaskService;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;

	@Override
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		try {
			//Exception : Project Not Found
			//ProjectTasks to be added to a specific project, project!=null, Backlog exists
			Backlog backlog =  backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
			//set the backlog to project task
			projectTask.setBacklog(backlog);
			//We want our project sequence to be like this FP01-1, FP01-2
			Integer backLogSequence = backlog.getPTSequence();
			//Update the backlog sequence
			backLogSequence++;
			backlog.setPTSequence(backLogSequence);
			//Add sequence to project task
			projectTask.setProjectSequence(projectIdentifier.toUpperCase()+"-"+backLogSequence);// FP01-1
			projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());
			//Initial status and priority should be updated
			if(projectTask.getPriority()==null) {
				projectTask.setPriority(ProjectTaskService.LOW);
			}
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus(ProjectTaskService.TODO);
			}
			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectIDException("Project Id : "+projectIdentifier.toUpperCase()+" does not exists");
		}
	}

	@Override
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		
		//Make sure we are searching on an existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id.toUpperCase());
		if(backlog==null) {
			throw new ProjectIDException("Project with id '"+backlog_id+"' does not exist");
		}
		
		// make sure that pt_id exists
		ProjectTask projectTask =  projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask==null) {
			throw new ProjectIDException("Project Task with id '"+pt_id+"' does not exist");
		}
		
		// make sure that the backlog/ project_id in the path corresponds to the right project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectIDException("Project Task id '"+pt_id.toUpperCase()+"' does not Match with '"+backlog_id.toUpperCase()+"'");
		}
		
		return projectTask;
	}

	@Override
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		// Find existing project task
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		// Replace it with updated task
		projectTask =  updatedTask; // cloning
		// Save project task		
		return projectTaskRepository.save(projectTask);
	}

	@Override
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask =  findPTByProjectSequence(backlog_id, pt_id);
		Backlog backlog = projectTask.getBacklog();
		List<ProjectTask> pts =  backlog.getProjectTasks();
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		projectTaskRepository.delete(projectTask);
		
		
	}

}
