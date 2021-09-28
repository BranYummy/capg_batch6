package in.codegram.ppmapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.codegram.ppmapi.domain.ProjectTask;
@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
	
	ProjectTask findByProjectSequence(String projectSequence);

}
