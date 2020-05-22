package be.ucll.demo.DB;

import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.Domain.DTOFormatter;
import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.ucll.demo.Domain.DTOFormatter.createDTOfromSubtask;

@Service
public class SubTaskService {
    private final SubTaskRepository repository;

    @Autowired
    public SubTaskService(SubTaskRepository repository){
        this.repository = repository;
//        SubTask changeGet = new SubTask("get to post","change all faulty get to post request");
//        SubTask showSub = new SubTask("show SubTask","make a page to show subtasks, if you see this it means you can mark this task done");
//        SubTask validation = new SubTask("validation","add validation to each form");
//        validation.setTask(1);
//        changeGet.setTaskid(1);
//        showSub.setTaskid(1);
//        add(createDTOfromSubtask(changeGet));
//        add(createDTOfromSubtask(showSub));
//        add(createDTOfromSubtask(validation));
    }

    public SubTaskDTO add(SubTaskDTO dto){
        repository.save(DTOFormatter.DTOToSubtask(dto));
        return dto;
    }

    public void delete(long id){
        repository.deleteById(id);
    }

    public void update(SubTaskDTO dto){
        repository.update(dto.getName(),dto.getDescription(),dto.getTask(),dto.getId());
    }

    public List<SubTaskDTO> getAll(Task task){
        List<SubTaskDTO> result = new ArrayList<>();
        for (SubTask t : repository.findByTask(task)){
            result.add(DTOFormatter.createDTOfromSubtask(t));
        }
        return result;
    }

    public List<SubTaskDTO> getAll(){
        List<SubTaskDTO> result = new ArrayList<>();
        for (SubTask t : repository.findAll()){
            result.add(DTOFormatter.createDTOfromSubtask(t));
        }
        return result;
    }

    public SubTaskDTO get(long  id){
        Optional<SubTask> optionalSubTask = repository.findById(id);
        return DTOFormatter.createDTOfromSubtask(optionalSubTask.orElseThrow(() -> new DbException("subtask not found")));
    }

}
