package io.github.prozorowicz.model.projection;

import io.github.prozorowicz.model.Task;
import io.github.prozorowicz.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GroupReadModelTest {
    @Test
    @DisplayName("should create null deadline for group when no task deadlines")
    void constructor_noDeadlines_createsNullDeadline(){
        //given
        var source = new TaskGroup();
        source.setDescription("foo");
        source.setTasks(List.of((new Task("bar",null))));
        //when
        var result = new GroupReadModel(source);
        //then
        assertThat(result).hasFieldOrPropertyWithValue("deadline",null);
    }

}