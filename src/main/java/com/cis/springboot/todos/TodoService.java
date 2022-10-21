package com.cis.springboot.todos;

import com.cis.springboot.error.ConflictException;
import com.cis.springboot.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    /**
     * Get All Todos
     * @return List of Todo
     * */
    public List<Todo> findAll() {
       return todoRepository.findAll();
    }
    public List<Todo> findAllCurrentUserTodos(String id) {
        return todoRepository.findByUserID(id);
    }
    public Todo getByID(String id)
    {
        try {
            return todoRepository.findById(id).get();

        }catch (NoSuchElementException ex){
            throw new NotFoundException(String.format("No record with th id [%s] was found in our database",id));
        }
    }
    public Todo save(Todo todo){
        if(todoRepository.findByTitle(todo.getTitle())!=null) {
            throw new ConflictException("Another record with the same title exists");
        }
           return todoRepository.insert(todo);
    }

    public void delete(String id){
         todoRepository.deleteById(id);
    }
}
