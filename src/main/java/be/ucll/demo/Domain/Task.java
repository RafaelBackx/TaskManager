package be.ucll.demo.Domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Name cannot be emtpy")
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "deadline cannot be empty")
    private LocalDateTime deadline;
    @NotNull(message = "description cannot be empty")
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubTask> subtasks;

    public Task(String name, String description,LocalDateTime deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        subtasks = new ArrayList<>();
    }

    public Task(){
        subtasks = new ArrayList<>();
    }

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

    @Override
    public boolean equals(Object t){
        if (t instanceof Task){
            Task obj = (Task) t;
            return obj.getId() == this.id && obj.getName().equalsIgnoreCase(this.name) && obj.getDescription().equalsIgnoreCase(this.description) && obj.getDeadline().equals(this.deadline);
        }
        return false;
    }


}
