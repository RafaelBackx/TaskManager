package be.ucll.demo.DB;

import be.ucll.demo.Domain.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;

import java.util.List;

//public interface SubTaskRepository extends CrudRepository<SubTask,Integer> {
//}

public interface SubTaskRepository extends JpaRepository<SubTask,Long>{
@Query("select s from SubTask s where s.taskid = ?1")
    List<SubTask> getAllSubTaskByTaskid(long taskid);
}
