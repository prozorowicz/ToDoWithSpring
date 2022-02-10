package io.github.prozorowicz.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    List<TaskGroup> findAll();

    //Page<TaskGroup> findAll(Pageable page);

    Optional<TaskGroup> findById(Integer id);

    TaskGroup save(TaskGroup entity);

}
