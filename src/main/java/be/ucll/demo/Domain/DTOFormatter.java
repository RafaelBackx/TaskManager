package be.ucll.demo.Domain;

import be.ucll.demo.DTO.SubTaskDTO;
import be.ucll.demo.DTO.TaskDTO;
import be.ucll.demo.DTO.UserDTO;

public class DTOFormatter {

    public static SubTask DTOToSubtask(SubTaskDTO dto){
        if (dto == null)
            return null;
        SubTask subTask = new SubTask();
        subTask.setId(dto.getId());
        subTask.setTaskid(dto.getTaskid());
        subTask.setName(dto.getName());
        subTask.setDescription(dto.getDescription());
        subTask.setCompleted(dto.isCompleted());
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
        dto.setCompleted(subTask.isCompleted());
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
        dto.setCompleted(task.isCompleted());
        dto.setSubtasks(task.getSubtasks());
        return dto;
    }

    public static UserDTO createDTOfromUser(User user){
        if (user == null)
            return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setId(user.getId());
        return userDTO;
    }

    public static User DTOToUser(UserDTO userDTO){
        if (userDTO == null)
            return null;
        User user = new User();
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setId(userDTO.getId());
        return user;
    }
}
