package be.ucll.demo.Controller;

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

    @GetMapping("/db")
    public String dbtest(){
        // create default tasks
        Task ipminor = new Task("finish ip minor","complete crud operation", LocalDateTime.of(2020,3,17,14,30));
        SubTask changeGet = new SubTask("get to post","change all faulty get to post request");
        SubTask showSub = new SubTask("show SubTask","make a page to show subtasks, if you see this it means you can mark this task done");
        SubTask validation = new SubTask("validation","add validation to each form");
        taskService.add(DTOFormatter.createDTOfromTask(ipminor));
        validation.setTaskid(1);
        changeGet.setTaskid(1);
        showSub.setTaskid(1);
        subTaskService.add(createDTOfromSubtask(changeGet));
        subTaskService.add(createDTOfromSubtask(showSub));
        subTaskService.add(createDTOfromSubtask(validation));
        return "db";
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
            for (SubTaskDTO dto :subTaskService.getAll(t.getId())){
                subTaskList.add(DTOFormatter.DTOToSubtask(dto));
            }
            if(subTaskList.size()>0)
            System.out.println(subTaskList.get(0).isCompleted());

            subtasks.put(t.getId(),subTaskList);
        }
        model.addAttribute("subtasks",subtasks);
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute @Valid Task task, BindingResult binding){
        if (binding.hasErrors()){
            return "add";
        }
        taskService.add(DTOFormatter.createDTOfromTask(task));
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(Model model, @PathVariable("id") long id){
        model.addAttribute("task", taskService.get(id));
        model.addAttribute("subtasks", subTaskService.getAll(id));
        return "/task";
    }

    @GetMapping("/add")
    public String goToAddPage(Model model){
        model.addAttribute("task",new Task());
        return "add";
    }

    @PostMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String goToEdit(Model model,@PathVariable("id") long id){
        model.addAttribute("task", taskService.get(id));
        return "editTask";
    }

    @PostMapping("/tasks/edit/{id}")
    public String edit(@ModelAttribute Task task, @PathVariable("id") long id){
        Task t  = DTOFormatter.DTOToTask(taskService.get(id));
        task.setSubtasks(t.getSubtasks());
        taskService.update(DTOFormatter.createDTOfromTask(task));
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/sub/create")
    public String goToSubTask(@PathVariable("id") long id,Model model){
        model.addAttribute("task", taskService.get(id));
        return "addsubtask";
    }

    @PostMapping("/tasks/{id}/sub/create")
    public String addSubTask(@ModelAttribute SubTask subTask, @PathVariable("id") long id){
        subTask.setTaskid(id);
        subTaskService.add(createDTOfromSubtask(subTask));
        System.out.println("subtask taskid:" + subTaskService.getAll().get(0).getTaskid());
        return "redirect:/tasks/"+id;
    }

    @PostMapping("/tasks/sub/remove/{subtaskid}")
    public String removeSubTask(@PathVariable("subtaskid") long subtaskid){
        subTaskService.delete(subtaskid);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/sub/{subtaskid}/accept")
    public String completeSubtask(@PathVariable("subtaskid") long subtaskid){
        SubTask subTask = DTOFormatter.DTOToSubtask(subTaskService.get(subtaskid));
        subTask.setCompleted(!subTask.isCompleted());
        subTaskService.update(DTOFormatter.createDTOfromSubtask(subTask));
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/sub/{subtaskid}")
    public String getSubTask(@PathVariable("subtaskid") long subtaskid, Model model,@PathVariable("id") long id){
        model.addAttribute("subtask",subTaskService.get(subtaskid));
        model.addAttribute("task",taskService.get(id));
        return "subtask";
    }

    @PostMapping("/tasks/{id}/accept")
    public String changeState(@PathVariable("id") long id){
        Task t = DTOFormatter.DTOToTask(taskService.get(id));
        t.setCompleted(!t.isCompleted());
        taskService.update(DTOFormatter.createDTOfromTask(t));
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/sub/edit/{subtaskid}")
    public String goToEdit(@PathVariable("subtaskid") long subtaskid,Model model,@PathVariable("id") long id){
        model.addAttribute("subtask",subTaskService.get(subtaskid));
        model.addAttribute("task",taskService.get(id));
        return "editSubTask";
    }

    @PostMapping("/tasks/{id}/sub/edit/{subtaskid}")
    public String editSubTask(@ModelAttribute SubTask subtask,@PathVariable("subtaskid")long subtaskid, @PathVariable("id") long taskid){
        subtask.setTaskid(taskid);
        subtask.setId(subtaskid);
        subTaskService.update(createDTOfromSubtask(subtask));
        return "redirect:/tasks";
    }
}
