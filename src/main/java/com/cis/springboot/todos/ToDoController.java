package com.cis.springboot.todos;

import com.cis.springboot.BaseController;
import com.cis.springboot.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/todos" )
public class ToDoController extends BaseController {

    @Autowired
    private TodoService todoService;

    @GetMapping(value = {"","/"})
    public ResponseEntity<List<Todo>> getCurrentUserTodos(){
       AppUser user=getCurrentUser();
       List <Todo>todos=todoService.findAllCurrentUserTodos(user.getId());
        return new ResponseEntity<>(todos,HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Todo> getOneTodo(@PathVariable String id){
        Todo todo=todoService.getByID(id);
        return new ResponseEntity<>(todo,HttpStatus.OK);
    }

    @PostMapping(value = {"","/"})
    public ResponseEntity<Todo> CreateNewTodo(@Valid @RequestBody Todo todo){
        todo.setUserID(getCurrentUser().getId());
        Todo res=todoService.save(todo);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<Void> deleteTodo(@PathVariable String id){
        todoService.delete(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
