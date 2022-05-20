package com.example.todoApplication.Repositories;

import com.example.todoApplication.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user where user.email = ?1", nativeQuery = true)
    User getUserByEmail(String email);


    @Query(value = "select * from user where supervisor_id = ?1", nativeQuery = true)
    List<User> getUsersIdBySupervisorId(long id);


}
