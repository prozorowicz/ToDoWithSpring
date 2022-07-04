package io.github.prozorowicz.adapter;

import io.github.prozorowicz.model.Task;
import io.github.prozorowicz.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    @Override
    List<Task> findAllByGroup_Id(Integer groupId);

    @Override
    //@Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    List<Task> findAllByDoneIsFalseAndDeadlineIsNullOrDoneIsFalseAndDeadlineIsLessThanEqual(@Param("today") LocalDateTime today);
}
