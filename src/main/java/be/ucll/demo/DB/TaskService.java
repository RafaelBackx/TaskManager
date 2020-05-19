package be.ucll.demo.DB;

import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.Domain.DTOFormatter;
import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.ucll.demo.Domain.DTOFormatter.createDTOfromSubtask;

@Service
public class TaskService {
    final
    TaskRepository taskRepository;
    final
    SubTaskRepository subTaskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, SubTaskRepository subTaskRepository){
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;

        Task ipminor = new Task("finish ip minor","complete crud operation", LocalDateTime.of(2020,3,17,14,30));
        add(DTOFormatter.createDTOfromTask(ipminor));
    }

    public TaskDTO get(long id){
        Optional<Task> optional = taskRepository.findById(id);
        TaskDTO t = DTOFormatter.createDTOfromTask(optional.orElseThrow(() -> new DbException("no task with id:"+ id +" found")));
        t.setSubtasks(subTaskRepository.getAllSubTaskByTaskid(t.getId()));
        return t;
    }

    public List<TaskDTO> getAll() {
        List<TaskDTO> result = new ArrayList<>();
        for (Task t:taskRepository.findAll()){
            t.setSubtasks(subTaskRepository.getAllSubTaskByTaskid(t.getId()));
            result.add(DTOFormatter.createDTOfromTask(t));
        }
        return result;
    }

    public List<SubTaskDTO> getAll(long id){
        List<SubTaskDTO> result = new ArrayList<>();
        for (SubTask t:subTaskRepository.getAllSubTaskByTaskid(id)){
            result.add(DTOFormatter.createDTOfromSubtask(t));
        }
        return result;
    }

    public TaskDTO add(TaskDTO dto) {
        taskRepository.save(DTOFormatter.DTOToTask(dto));
        return dto;
    }

    public void delete(long id){
        taskRepository.deleteById(id);
        subTaskRepository.removeAllByTaskid(id);
    }

    public TaskDTO update(TaskDTO dto){
        taskRepository.updateTask(dto.getName(),dto.getDeadline(),dto.getDescription(),dto.isCompleted());
        return dto;
    }
}
