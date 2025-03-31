
-- DOCKER --

docker run --name mysql-ToDoList -v ~/Documents/docker-data/mysql-ToDoList:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=blackfriday -p 3306:3306 -d mysql:8.0.41




-- CREAZIONE DATABASE ---


-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ToDoList
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ToDoList
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ToDoList` DEFAULT CHARACTER SET utf8 ;
USE `ToDoList` ;

-- -----------------------------------------------------
-- Table `ToDoList`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`user` (
  `id` INT NOT NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `role` ENUM('USER', 'ADMIN') NULL,
  `nome` VARCHAR(45) NULL,
  `cognome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ToDoList`.`projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`projects` (
  `id` INT NOT NULL,
  `Title` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `data_creation_project` DATETIME NULL,
  `data_update_project` DATETIME NULL,
  `data_deadline` DATETIME NULL,
  `data_closed_project` DATETIME NULL,
  `User_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_projects_User_idx` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_projects_User`
    FOREIGN KEY (`User_id`)
    REFERENCES `ToDoList`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ToDoList`.`Task_assignements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`Task_assignements` (
  `id` INT NOT NULL,
  `projects_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Task_assignements_projects1_idx` (`projects_id` ASC) VISIBLE,
  CONSTRAINT `fk_Task_assignements_projects1`
    FOREIGN KEY (`projects_id`)
    REFERENCES `ToDoList`.`projects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ToDoList`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`task` (
  `id` INT NOT NULL,
  `titolo` VARCHAR(45) NULL,
  `descrizione` VARCHAR(45) NULL,
  `status` ENUM('PENDING', 'STARTED', 'WORK IN PROGRESS', 'COMPLETED') NULL DEFAULT 'PENDING',
  `data_pending` DATETIME NULL,
  `data_started` DATETIME NULL,
  `data_progress` DATETIME NULL,
  `data_completed` DATETIME NULL,
  `data_deadline` DATETIME NULL,
  `categoria` VARCHAR(45) NULL,
  `reminder` VARCHAR(45) NULL,
  `notes` VARCHAR(500) NULL,
  `repeat_task` VARCHAR(45) NULL,
  `attachment` VARCHAR(45) NULL,
  `data_created_task` DATETIME NULL,
  `data_updated_task` DATETIME NULL,
  `projects_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `assigner_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Task_projects1_idx` (`projects_id` ASC) VISIBLE,
  INDEX `fk_Task_User1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Task_User2_idx` (`assigner_id` ASC) VISIBLE,
  CONSTRAINT `fk_Task_projects1`
    FOREIGN KEY (`projects_id`)
    REFERENCES `ToDoList`.`projects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ToDoList`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_User2`
    FOREIGN KEY (`assigner_id`)
    REFERENCES `ToDoList`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ToDoList`.`Task_assignements_has_Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`Task_assignements_has_Task` (
  `Task_assignements_id` INT NOT NULL,
  `Task_id` INT NOT NULL,
  PRIMARY KEY (`Task_assignements_id`, `Task_id`),
  INDEX `fk_Task_assignements_has_Task_Task1_idx` (`Task_id` ASC) VISIBLE,
  INDEX `fk_Task_assignements_has_Task_Task_assignements1_idx` (`Task_assignements_id` ASC) VISIBLE,
  CONSTRAINT `fk_Task_assignements_has_Task_Task_assignements1`
    FOREIGN KEY (`Task_assignements_id`)
    REFERENCES `ToDoList`.`Task_assignements` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_assignements_has_Task_Task1`
    FOREIGN KEY (`Task_id`)
    REFERENCES `ToDoList`.`task` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ToDoList`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`comments` (
  `id` INT NOT NULL,
  `data_comment` DATETIME NULL,
  `text` VARCHAR(300) NULL,
  `Task_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comments_Task1_idx` (`Task_id` ASC) VISIBLE,
  INDEX `fk_comments_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_comments_Task1`
    FOREIGN KEY (`Task_id`)
    REFERENCES `ToDoList`.`task` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ToDoList`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;




-- INSERIMENTO DATI --

USE `ToDoList`;

-- Inserimento dati nella tabella `user`
INSERT INTO `user` (`id`, `username`, `password`, `email`, `role`, `nome`, `cognome`) VALUES
(1, 'mario.rossi', 'password123', 'mario.rossi@email.com', 'USER', 'Mario', 'Rossi'),
(2, 'admin', 'adminpass', 'admin@email.com', 'ADMIN', 'Admin', 'Admin'),
(3, 'luca.bianchi', 'pass456', 'luca.bianchi@email.com', 'USER', 'Luca', 'Bianchi');

-- Inserimento dati nella tabella `projects`
INSERT INTO `projects` (`id`, `Title`, `description`, `data_creation_project`, `data_update_project`, `data_deadline`, `data_closed_project`, `User_id`) VALUES
(1, 'Sito Web Azienda', 'Sviluppo sito aziendale', '2025-03-01 10:00:00', '2025-03-15 14:30:00', '2025-04-30 23:59:59', NULL, 1),
(2, 'App Mobile', 'App per gestione task', '2025-03-10 09:00:00', '2025-03-20 16:00:00', '2025-05-15 23:59:59', NULL, 2),
(3, 'Report Mensile', 'Report per il team', '2025-03-15 12:00:00', NULL, '2025-03-31 18:00:00', '2025-03-30 10:00:00', 3);

-- Inserimento dati nella tabella `Task_assignements`
INSERT INTO `Task_assignements` (`id`, `projects_id`) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Inserimento dati nella tabella `task`
INSERT INTO `task` (`id`, `titolo`, `descrizione`, `status`, `data_pending`, `data_started`, `data_progress`, `data_completed`, `data_deadline`, `categoria`, `reminder`, `notes`, `repeat_task`, `attachment`, `data_created_task`, `data_updated_task`, `projects_id`, `user_id`, `assigner_id`) VALUES
(1, 'Progettazione UI', 'Creare mockup interfaccia', 'WORK IN PROGRESS', '2025-03-01 10:00:00', '2025-03-05 09:00:00', '2025-03-10 14:00:00', NULL, '2025-03-20 17:00:00', 'Design', '3 giorni prima', 'Usare colori aziendali', 'NONE', 'mockup.pdf', '2025-03-01 10:00:00', '2025-03-10 14:00:00', 1, 1, 2),
(2, 'Sviluppo Backend', 'API per autenticazione', 'STARTED', '2025-03-10 09:00:00', '2025-03-15 10:00:00', NULL, NULL, '2025-04-01 23:59:59', 'Backend', '1 giorno prima', 'Implementare JWT', 'NONE', NULL, '2025-03-10 09:00:00', '2025-03-15 10:00:00', 2, 2, 2),
(3, 'Scrittura Report', 'Report vendite marzo', 'COMPLETED', '2025-03-15 12:00:00', '2025-03-16 09:00:00', '2025-03-17 14:00:00', '2025-03-18 16:00:00', '2025-03-20 18:00:00', 'Documentazione', '2 giorni prima', 'Includere grafici', 'MONTHLY', 'report.pdf', '2025-03-15 12:00:00', '2025-03-18 16:00:00', 3, 3, 2);

-- Inserimento dati nella tabella `Task_assignements_has_Task`
INSERT INTO `Task_assignements_has_Task` (`Task_assignements_id`, `Task_id`) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Inserimento dati nella tabella `comments`
INSERT INTO `comments` (`id`, `data_comment`, `text`, `Task_id`, `user_id`) VALUES
(1, '2025-03-05 15:00:00', 'Ottimo lavoro sul mockup, aggiungere pulsante login', 1, 2),
(2, '2025-03-16 11:00:00', 'API funzionante, testare con frontend', 2, 2),
(3, '2025-03-17 15:00:00', 'Aggiungere più dettagli sulle vendite', 3, 1);