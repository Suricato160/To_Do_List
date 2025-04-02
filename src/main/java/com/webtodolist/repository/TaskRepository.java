package com.webtodolist.repository;

import com.webtodolist.model.Task;
import com.webtodolist.model.Task.TaskStatus;
import com.webtodolist.model.User;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByDataDeadlineBetween(LocalDateTime todayStart, LocalDateTime todayEnd);

    List<Task> findByStatus(TaskStatus completed);

    List<Task> findByUser(User user);
}