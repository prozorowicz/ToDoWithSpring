package io.github.prozorowicz.logic;

import io.github.prozorowicz.model.TaskGroup;
import io.github.prozorowicz.model.TaskGroupRepository;
import io.github.prozorowicz.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when undone tasks exist")
    void toggleGroup_undoneTasksExist_throwsIllegalStateException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        //system under test
        var toTest = new GroupService(null, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class).
                hasMessageContaining("has undone tasks");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when no taskGroup for a given id")
    void toggleGroup_noTasksGroup_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //system under test
        var toTest = new GroupService(mockRepository, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("id not found");

    }

    @Test
    @DisplayName("should toggle group to flag it as finished")
    void toggleGroup_allTasksDone_existingGroup_togglesAndSavesGroup() {
        //given
        TaskGroup group = new TaskGroup();
        var beforeToggle = group.getDone();
        //and
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //and
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //system under test
        var toTest = new GroupService(mockRepository, mockTaskRepository);
        //when
        toTest.toggleGroup(group.getId());
        // then
        assertThat(group.getDone()).isEqualTo(!beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(final boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }
}