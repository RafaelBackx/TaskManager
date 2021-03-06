package be.ucll.demo;

import be.ucll.demo.DB.SubTaskService;
import be.ucll.demo.DB.TaskService;
import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.Domain.DTOFormatter;
import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService service;
    @Autowired
    private SubTaskService subService;

    @BeforeEach
    public void setup(){
        Task t = new Task();
        t.setName("test");
        t.setDescription("test task1 for ci/cd");
        t.setDeadline(LocalDateTime.now());
        t.setSubtasks(null);
        service.add(DTOFormatter.createDTOfromTask(t));
    }

    @AfterEach
    public void clear(){
        for (TaskDTO t:service.getAll()){
            service.delete(t.getId());
        }
        assertEquals(0,service.getAll().size());
    }

    @Test
    public void testTaskAdd(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("test add");
        taskDTO.setDescription("test add description");
        taskDTO.setDeadline(LocalDateTime.now());
        taskDTO.setSubtasks(null);
        this.service.add(taskDTO);
        for (TaskDTO t:this.service.getAll()){
            if (DTOFormatter.DTOToTask(t).equals(DTOFormatter.DTOToTask(taskDTO))){
                assertEquals(t.getName(),taskDTO.getName());
                assertEquals(t.getDescription(),taskDTO.getDescription());
                assertEquals(t.getDeadline(),taskDTO.getDeadline());
                assertEquals(t.getSubtasks(),taskDTO.getSubtasks());
            }
        }
    }

    @Test
    public void testTaskServiceGetAll(){
        List<TaskDTO> tasks = service.getAll();
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(1,tasks.size());
        assertNotNull(tasks.get(0));
    }

    @Test
    public void testTaskServiceGet(){
        TaskDTO task = service.get(service.getAll().get(0).getId());
        assertNotNull(task);
    }

    @Test
    public void testTaskServiceUpdate(){
        TaskDTO task = service.get(service.getAll().get(0).getId());
        task.setDescription("updated task description");
        service.update(task);
        Task t = DTOFormatter.DTOToTask(task);

        //assert
        assertEquals(t,DTOFormatter.DTOToTask(service.get(service.getAll().get(0).getId())));
    }

}
