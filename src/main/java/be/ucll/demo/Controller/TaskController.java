package be.ucll.demo.Controller;

import be.ucll.demo.DB.DbException;
import be.ucll.demo.DB.SubTaskService;
import be.ucll.demo.DB.TaskService;
import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.Domain.DTOFormatter;
import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static be.ucll.demo.Domain.DTOFormatter.DTOToTask;
import static be.ucll.demo.Domain.DTOFormatter.createDTOfromSubtask;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    SubTaskService subTaskService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/sub")
    public String getAllSubTasks(){
        System.out.println(subTaskService.getAll());
        return "tasks";
    }


    @GetMapping("/tasks")
    public String getTasks(Model model){
        List<Task> tasks = new ArrayList<>();
        for (TaskDTO t:taskService.getAll()){
            tasks.add(DTOFormatter.DTOToTask(t));
        }
        Map<Long,List<SubTask>> subtasks = new HashMap<>();
        model.addAttribute("tasks",tasks);
        for (Task t : tasks){
            List<SubTask> subTaskList = new ArrayList<>();
            for (SubTaskDTO dto :subTaskService.getAll(t)){
                subTaskList.add(DTOFormatter.DTOToSubtask(dto));
            }
            if(subTaskList.size()>0)
            subtasks.put(t.getId(),subTaskList);
        }
        model.addAttribute("subtasks",subtasks);
        return "tasks";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute("task") @Valid TaskDTO task, BindingResult binding){
        if (binding.hasErrors()){
            return "add";
        }
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(Model model, @PathVariable("id") long id){
        try {
            model.addAttribute("task", taskService.get(id));
        }catch (DbException db){
            model.addAttribute("taskerror","task not found");
            return "tasks";
        }
        model.addAttribute("subtasks", subTaskService.getAll(DTOToTask(taskService.get(id))));
        return "/task";
    }

    @GetMapping("/add")
    public String goToAddPage(Model model){
        model.addAttribute("task",new TaskDTO());
        return "add";
    }

    @PostMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String goToEdit(Model model,@PathVariable("id") long id){
        TaskDTO dto = new TaskDTO();
        TaskDTO getTask = taskService.get(id);
        dto.setId(getTask.getId());
        dto.setName(getTask.getName());
        dto.setDescription(getTask.getDescription());
        dto.setDeadline(getTask.getDeadline());
        dto.setSubtasks(getTask.getSubtasks());
        model.addAttribute("task", dto);
        return "editTask";
    }

    @PostMapping("/tasks/edit/{id}")
    public String edit(@ModelAttribute("task") @Valid TaskDTO task, BindingResult bindingResult, @PathVariable("id") long id){
        if (bindingResult.hasErrors()){
            return "editTask";
        }
        TaskDTO t  = taskService.get(id);
        task.setSubtasks(t.getSubtasks());
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/sub/create")
    public String goToSubTask(@PathVariable("id") long id,Model model){
        model.addAttribute("task", taskService.get(id));
        model.addAttribute("subtask",new SubTaskDTO());
        return "addsubtask";
    }

    @PostMapping("/tasks/{id}/sub/create")
    public String addSubTask(@ModelAttribute("subtask") @Valid SubTaskDTO subTask, BindingResult bindingResult, @PathVariable("id") long id, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("task",taskService.get(id));
            return "addsubtask";
        }
        subTask.setTask(DTOFormatter.DTOToTask(taskService.get(id)));
        subTaskService.add(subTask);
        return "redirect:/tasks/"+id;
    }

    @PostMapping("/tasks/sub/remove/{subtaskid}")
    public String removeSubTask(@PathVariable("subtaskid") long subtaskid){
        subTaskService.delete(subtaskid);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/sub/{subtaskid}")
    public String getSubTask(@PathVariable("subtaskid") long subtaskid, Model model,@PathVariable("id") long id){
        model.addAttribute("subtask",subTaskService.get(subtaskid));
        model.addAttribute("task",taskService.get(id));
        return "subtask";
    }

    @GetMapping("/tasks/{id}/sub/edit/{subtaskid}")
    public String goToEdit(@PathVariable("subtaskid") long subtaskid,Model model,@PathVariable("id") long id){
        model.addAttribute("subtask",subTaskService.get(subtaskid));
        model.addAttribute("task",taskService.get(id));
        return "editSubTask";
    }

    @PostMapping("/tasks/{id}/sub/edit/{subtaskid}")
    public String editSubTask(@ModelAttribute("subtask") @Valid SubTaskDTO subtask, BindingResult bindingResult ,@PathVariable("subtaskid")long subtaskid, @PathVariable("id") long taskid, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("task",taskService.get(taskid));
            return "editSubtask";
        }
        subtask.setTask(DTOFormatter.DTOToTask(taskService.get(taskid)));
        subtask.setId(subtaskid);
        subTaskService.update(subtask);
        return "redirect:/tasks";
    }
}
