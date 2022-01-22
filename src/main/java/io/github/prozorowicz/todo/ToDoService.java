package io.github.prozorowicz.todo;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
class ToDoService {
    private ToDoRepository repository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.repository = toDoRepository;
    }
    ToDo ToggleToDo(Integer id){
        var result = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        result.setDONE(!result.getDONE());
        repository.save(result);
        return result;
    }
}
