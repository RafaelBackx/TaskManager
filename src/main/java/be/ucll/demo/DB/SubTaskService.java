package be.ucll.demo.DB;

import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.Domain.SubTask;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
@Service
public class SubTaskService {
    @Autowired
    SubTaskRepository repository;

    public void add(SubTaskDTO dto){
        repository.save(toEntity(dto));
    }

    public void delete(long id){
        repository.deleteById(id);
    }

    public void update(SubTaskDTO dto){
        repository.save(toEntity(dto));
    }

    public List<SubTask> getAll(long id){
        return repository.getAllSubTaskByTaskid(id);
    }

    public List<SubTask> getAll(){
        return repository.findAll();
    }

    public SubTask get(long  id){
        Optional<SubTask> optionalSubTask = repository.findById(id);
        return optionalSubTask.orElseThrow(() -> new DbException("subtask not found"));
    }

    public SubTask toEntity(SubTaskDTO dto){
        SubTask subTask = new SubTask();
        subTask.setId(dto.getId());
        subTask.setTaskid(dto.getTaskid());
        subTask.setName(dto.getName());
        subTask.setDescription(dto.getDescription());
        subTask.setCompleted(dto.isCompleted());
        return subTask;
    }
}
