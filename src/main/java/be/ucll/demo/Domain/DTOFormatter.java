package be.ucll.demo.Domain;

import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;

public class DTOFormatter {

    public static SubTask DTOToSubtask(SubTaskDTO dto){
        if (dto == null)
            return null;
        SubTask subTask = new SubTask();
        subTask.setId(dto.getId());
        subTask.setTaskid(dto.getTaskid());
        subTask.setName(dto.getName());
        subTask.setDescription(dto.getDescription());
        return subTask;
    }

    public static Task DTOToTask(TaskDTO dto){
        if (dto == null)
            return null;
        Task t = new Task();
        t.setId(dto.getId());
        t.setName(dto.getName());
        t.setDescription(dto.getDescription());
        t.setDeadline(dto.getDeadline());
        t.setSubtasks(dto.getSubtasks());
        return t;
    }

    public static SubTaskDTO createDTOfromSubtask(SubTask subTask) {
        if (subTask == null)
            return null;
        SubTaskDTO dto = new SubTaskDTO();
        dto.setId(subTask.getId());
        dto.setDescription(subTask.getDescription());
        dto.setName(subTask.getName());
        dto.setTaskid(subTask.getTaskid());
        return dto;
    }

    public static TaskDTO createDTOfromTask(Task task) {
        if (task == null)
            return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setName(task.getName());
        dto.setDeadline(task.getDeadline());
        dto.setSubtasks(task.getSubtasks());
        return dto;
    }
}
