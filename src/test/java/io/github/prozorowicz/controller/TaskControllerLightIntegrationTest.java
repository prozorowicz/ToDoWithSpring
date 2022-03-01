package io.github.prozorowicz.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.prozorowicz.model.Task;
import io.github.prozorowicz.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerLightIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskRepository repo;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        //given
        when(repo.findById(anyInt())).thenReturn(Optional.of(new Task("foo", LocalDateTime.now())));
        //when + then
        mockMvc.perform(get("/tasks/123"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("\"description\":\"foo\"")));
    }

    @Test
    void httpPost_savesNewTask() throws Exception {
        //given
        var task = new Task("foo",LocalDateTime.now(),null);
        //and
        when(repo.save(any(Task.class))).thenReturn(task);
        //when+then
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"description\":\"foo\"")));
    }
}
