package com.webtodolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtodolist.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
