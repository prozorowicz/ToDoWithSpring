package io.github.prozorowicz.todo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ToDoService {
    private ToDoRepository repository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.repository = toDoRepository;
    }

    List<ToDo> findAll() {
        return repository.findAll();
    }

    Optional<ToDo> toggleToDo(Integer id) {
        var toDo = repository.findById(id);
        toDo.ifPresent(t -> {
            t.setDONE(!t.getDONE());
            repository.save(t);
        });
        return toDo;
    }

    ToDo addTodo(ToDo toDo) {
        return repository.save(toDo);
    }
}
