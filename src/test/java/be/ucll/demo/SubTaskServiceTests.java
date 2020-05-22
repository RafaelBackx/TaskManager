package be.ucll.demo;

import be.ucll.demo.DB.SubTaskService;
import be.ucll.demo.DB.TaskService;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.Domain.DTOFormatter;
import be.ucll.demo.Domain.SubTask;
import be.ucll.demo.Domain.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SubTaskServiceTests {
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
        SubTask sub = new SubTask("test subtask for ci/cd","idk i dont want to test this");
        sub.setTask(t);
        subService.add(DTOFormatter.createDTOfromSubtask(sub));
    }

    @Test
    public void getSubtask(){
        System.out.println("size: " + service.getAll().size());
        assertNotNull(service.getAll().get(0));
    }

    @AfterEach
    public void clear(){
        for (TaskDTO t:service.getAll()){
            service.delete(t.getId());
        }
        assertEquals(0,service.getAll().size());
    }
}
