package com.cis.springboot.todos;

import com.cis.springboot.error.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
public class TodoServiceTest {

    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @TestConfiguration
    static class TodoServiceContextConfiguration{
        @Bean
        public TodoService todoService(){
            return new TodoService();
        }
    }

    @Test
    public void whenFindAll_ReturnTodoList(){
       //Mockup
       Todo todo1=new Todo("1","Todo 1","Todo 1 des");
        Todo todo2=new Todo("2","Todo 2","Todo 2 des");
        List<Todo> data= Arrays.asList(todo1,todo2);

        given(todoRepository.findAll()).willReturn(data);

        //
        assertThat(todoService.findAll())
                .hasSize(2).contains(todo1,todo2);
    }
    @Test
    public void whenGetById_TodoShouldBeFound(){
        //Mockup
        Todo todo1=new Todo("1","Todo 1","Todo 1 des");

        given(todoRepository.findById(anyString())).willReturn(Optional.ofNullable(todo1));

        //
        Todo res=todoService.getByID("1");
        assertThat(res.getTitle()).containsIgnoringCase("todo");
    }

    @Test(expected = NotFoundException.class)
    public void whenInvalidID_TodoShouldNotBeFound(){
        given(todoRepository.findById(anyString())).willReturn(Optional.empty());
        todoService.getByID("1");
    }
}
