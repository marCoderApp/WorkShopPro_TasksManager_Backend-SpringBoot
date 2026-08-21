package com.equipo.tallerproapp.repository;

import com.equipo.tallerproapp.enums.TaskStatus;
import com.equipo.tallerproapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findByUserId(Long userId);

    //LIST TASKS BY TECH
    @Query("SELECT t FROM Task t WHERE t.assignedTo = :tech_id")
    List<Task> findByTechId(@Param("tech_id") Long tech_id);

    //LIST TASKS BY STATUS
    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> findByStatus(@Param("status") TaskStatus status);

    //LIST TASKS BY CREATED DATE
    @Query("SELECT t FROM Task t WHERE t.createdAt " +
            "BETWEEN :startDate AND :endDate")
    List<Task> findByCreatedDate(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    //LIST TASKS BY DUE DATE
    @Query("SELECT t FROM Task t WHERE t.due_date BETWEEN :startDate AND " +
            ":endDate")
    List<Task> findByDueDate(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate);

}
