package be.ucll.demo;

import be.ucll.demo.DB.UserService;
import be.ucll.demo.DB.UserServiceImplementatie;
import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.DTO.UserDTO;
import be.ucll.demo.Domain.Role;
import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import be.ucll.demo.Domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static be.ucll.demo.Domain.DTOFormatter.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DTOFormatterTests {
    @Autowired
    private UserServiceImplementatie userService;
    @Test
    public void TaskDTOToTaskTest(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTO.setName("taskDTO To Task");
        taskDTO.setDescription("TaskDTO To Task Description");
        taskDTO.setDeadline(LocalDateTime.now());
        Task task = DTOToTask(taskDTO);
        assertEquals(task.getId(),taskDTO.getId());
        assertEquals(task.getName(),taskDTO.getName());
        assertEquals(task.getDescription(),taskDTO.getDescription());
        assertEquals(task.getDeadline(),taskDTO.getDeadline());
        assertEquals(task.getSubtasks(),taskDTO.getSubtasks());
    }

    @Test
    public void TaskToTaskDto(){
        Task t = new Task();
        t.setId(1);
        t.setName("Task to TaskDTo");
        t.setDescription("Task to taskdto description");
        t.setDeadline(LocalDateTime.now());
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(new SubTask("subtasktest", "Description"));
        t.setSubtasks(subTaskList);
        TaskDTO dto = createDTOfromTask(t);
        assertEquals(dto.getId(),t.getId());
        assertEquals(dto.getName(),t.getName());
        assertEquals(dto.getDescription(),t.getDescription());
        assertEquals(dto.getDeadline(),t.getDeadline());
        assertEquals(dto.getSubtasks(),t.getSubtasks());
    }

    @Test
    public void SubtaskDTOToSubtask(){
        SubTaskDTO subTaskDTO = new SubTaskDTO();
        subTaskDTO.setId(1);
        subTaskDTO.setName("taskDTOToTask");
        subTaskDTO.setDescription("TaskDTOToTaskDescription");
        SubTask subtask = DTOToSubtask(subTaskDTO);
        assertEquals(subtask.getId(),subTaskDTO.getId());
        assertEquals(subtask.getName(),subTaskDTO.getName());
        assertEquals(subtask.getDescription(),subTaskDTO.getDescription());
    }

    @Test
    public void SubtaskToSubtaskDTO(){
        SubTask subTask = new SubTask();
        subTask.setId(1);
        subTask.setName("subtask name");
        subTask.setDescription("subtask description");
        subTask.setTask(new Task("task","description",LocalDateTime.now()));
        SubTaskDTO dto = createDTOfromSubtask(subTask);
        assertEquals(dto.getId(),subTask.getId());
        assertEquals(dto.getTask(),subTask.getTask());
        assertEquals(dto.getDescription(),subTask.getDescription());
        assertEquals(dto.getName(),subTask.getName());
    }

    @Test
    public void testUserDTOToUser(){
        User user = new User("Rafael","password", Role.ADMIN);
        UserDTO userDTO = userService.convert(user);
        assertEquals(user.getId(),userDTO.getId());
        assertEquals(user.getName(),userDTO.getName());
        assertEquals(user.getRole(),userDTO.getRole());
    }
}
