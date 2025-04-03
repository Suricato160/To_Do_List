package com.webtodolist.repository;

import com.webtodolist.model.Task;
import com.webtodolist.model.Task.TaskStatus;
import com.webtodolist.model.User;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDataDeadlineBetween(LocalDateTime todayStart, LocalDateTime todayEnd);

    List<Task> findByStatus(TaskStatus completed);
    List<Task> findByTitoloContainingIgnoreCase(String titolo); // Nuovo metodo
    List<Task> findByUser(User user);

    @Query("SELECT DISTINCT t.categoria FROM Task t WHERE t.user = :user AND t.categoria IS NOT NULL AND t.categoria <> ''")
    List<String> findDistinctCategoriesByUser(@Param("user") User user);
}