package be.ucll.demo.DB;

import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;

import javax.transaction.Transactional;
import java.util.List;

//public interface SubTaskRepository extends CrudRepository<SubTask,Integer> {
//}

public interface SubTaskRepository extends JpaRepository<SubTask,Long>{
    @Modifying
    @Transactional
    @Query("update SubTask s set s.name=?1,s.description=?2,s.task=?3 where s.id = ?4")
    void update (String name,String description,Task task,long id);
    List<SubTask> findByTask(Task task);
}
