package be.ucll.demo.Controller;

import be.ucll.demo.DB.SubTaskService;
import be.ucll.demo.DB.TaskService;
import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.Domain.DTOFormatter;
import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {
    private final TaskService taskService;
    private final SubTaskService subtaskService;

    public RESTController(TaskService taskService,SubTaskService subTaskService){
        this.taskService = taskService;
        this.subtaskService = subTaskService;
    }

    @GetMapping("/tasks/all")
    @ResponseBody
    public List<TaskDTO> getAll(){
        return taskService.getAll();
    }

    @GetMapping("/tasks/{id}")
    public TaskDTO get(@PathVariable("id") long id){
        return taskService.get(id);
    }

    @PostMapping("/tasks/add")
    public TaskDTO addTask(@ModelAttribute @Valid Task t){
        TaskDTO dto = DTOFormatter.createDTOfromTask(t);
        taskService.add(dto);
        return dto;
    }

    @PostMapping("/tasks/edit/{id}")
    public TaskDTO editTask(@PathVariable("id") long id, @ModelAttribute @Valid Task t){
        TaskDTO dto = taskService.get(id);
        dto.setName(t.getName());
        dto.setDescription(t.getDescription());
        dto.setDeadline(t.getDeadline());
        dto = taskService.update(dto);
        List<SubTask> result = new ArrayList<>();
        for (SubTaskDTO sub : taskService.getAll(t.getId())){
            result.add(DTOFormatter.DTOToSubtask(sub));
        }
        dto.setSubtasks(result);
        return dto;
    }

    @PostMapping("/tasks/{id}/sub/create")
    public SubTaskDTO addSubtask(@ModelAttribute @Valid SubTask subTask,@PathVariable("id") long id){
        subTask.setTask(DTOFormatter.DTOToTask(taskService.get(id)));
        subtaskService.add(DTOFormatter.createDTOfromSubtask(subTask));
        return DTOFormatter.createDTOfromSubtask(subTask);
    }

    @GetMapping("/tasks/{id}/sub/all")
    public List<SubTaskDTO> getSubtasks(@PathVariable("id") long id){
        return subtaskService.getAll(DTOFormatter.DTOToTask(taskService.get(id)));
    }

}
