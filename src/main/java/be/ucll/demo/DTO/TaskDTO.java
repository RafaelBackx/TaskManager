package be.ucll.demo.DTO;

import be.ucll.demo.Domain.DateFormatter;
import be.ucll.demo.Domain.SubTask;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDTO {
    private long id;

    @NotNull(message = "Name cannot be emtpy")
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "deadline cannot be empty")
    private LocalDateTime deadline;
    @NotNull(message = "description cannot be empty")
    @NotEmpty(message = "description cannot be empty")
    private String description;
    private List<SubTask> subtasks;

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public String deadlineToString(){
        DateFormatter formatter =new DateFormatter();
        return formatter.print(deadline, null);
    }

    public void setId(long id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String toString(){
        return name + " " + this.deadlineToString();
    }

    public void addSubTask(SubTask t){
        long id = 1;
        if (this.subtasks.size()>0){
            id = this.subtasks.get(subtasks.size()-1).getId()+1;
        }
        t.setId(id);
        this.subtasks.add(t);
    }

    public List<SubTask> getSubtasks(){
        return this.subtasks;
    }


    public void setSubtasks(List<SubTask> subtasks){
        this.subtasks = subtasks;
    }
}
