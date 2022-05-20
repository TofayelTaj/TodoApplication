package com.example.todoApplication.Repositories;

import com.example.todoApplication.Entitys.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query(value = "select * from todo where user_id = ?1", nativeQuery = true)
    List<Todo> findByUserId(long id);

    @Query(value = "select * from todo where id = ?1", nativeQuery = true)
    Todo findById(long id);

    @Query(value = "select * from todo where created_time = ?1 and user_id = ?2", nativeQuery = true)
    List<Todo> findByCreatedTime(String date, long id);

}
