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
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDataDeadlineBetween(LocalDateTime todayStart, LocalDateTime todayEnd);

    List<Task> findByStatus(TaskStatus completed);

    List<Task> findByUser(User user);

    List<Task> findByUserAndCategoria(User user, String categoria);

    @Query("SELECT DISTINCT t.categoria FROM Task t WHERE t.user = :user AND t.categoria IS NOT NULL AND t.categoria <> ''")
    List<String> findDistinctCategoriesByUser(@Param("user") User user);
    
    //  Metodo per trovare categorie di task in progetti in cui l'utente è coinvolto
    @Query("SELECT DISTINCT t.categoria FROM Task t JOIN t.project p JOIN p.user u WHERE u = :user AND t.categoria IS NOT NULL AND t.categoria <> ''")
    List<String> findDistinctCategoriesByUserProjects(@Param("user") User user);
    
    //  Metodo per trovare tutte le task dei progetti in cui il loggato è coinvolto
    @Query("SELECT t FROM Task t JOIN t.project p JOIN p.user u WHERE u = :user")
    List<Task> findTasksByUserProjects(@Param("user") User user);

   // Nuovo metodo per trovare le tasks per categoria nei progetti in cui l'utente è coinvolto
    @Query("SELECT t FROM Task t JOIN t.project p JOIN p.user u WHERE u = :user AND t.categoria = :categoria")
    List<Task> findByUserProjectsAndCategoria(@Param("user") User user, @Param("categoria") String categoria);

    // Query per aggiornare il nome categoria su tutte le task associate
    @Modifying
    @Query("UPDATE Task t SET t.categoria = :newCategoryName WHERE t.categoria = :oldCategoryName")
    int updateTasksCategory(@Param("oldCategoryName") String oldCategoryName, @Param("newCategoryName") String newCategoryName);
}