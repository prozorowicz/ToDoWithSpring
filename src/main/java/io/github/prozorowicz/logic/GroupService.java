package io.github.prozorowicz.logic;

import io.github.prozorowicz.model.Project;
import io.github.prozorowicz.model.TaskGroup;
import io.github.prozorowicz.model.TaskGroupRepository;
import io.github.prozorowicz.model.TaskRepository;
import io.github.prozorowicz.model.projection.GroupReadModel;
import io.github.prozorowicz.model.projection.GroupTaskReadModel;
import io.github.prozorowicz.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

public class GroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    GroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;

    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source,null);
    }
    GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll() {
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
    public List<GroupTaskReadModel> readAllTasksFromGroup(int groupId) {
        return taskRepository.findAllByGroup_Id(groupId)
                .stream()
                .map(GroupTaskReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            //TODO if group is done no exception, and then fix tests
            throw new IllegalStateException("Group has undone tasks. Do all the tasks first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.getDone());
        repository.save(result);
    }


}
