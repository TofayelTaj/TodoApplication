package com.example.todoApplication.Repositories;

import com.example.todoApplication.Entitys.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    @Query(value = "select * from supervisor where supervisor.email = ?1", nativeQuery = true)
    Supervisor getSupervisorByEmail(String email);

}
