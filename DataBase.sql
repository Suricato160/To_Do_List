
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL,
  `role` ENUM('USER', 'ADMIN') NOT NULL,
  `nome` VARCHAR(45) NULL,
  `cognome` VARCHAR(45) NULL,
  `mansione` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ToDoList`.`projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`projects` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(45) NOT NULL,
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `titolo` VARCHAR(45) NULL,
  `descrizione` VARCHAR(45) NULL,
  `status` ENUM('PENDING', 'STARTED', 'WORK_IN_PROGRESS', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
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
  `completed` TINYINT NOT NULL DEFAULT 0,
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
  `id` INT NOT NULL AUTO_INCREMENT,
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

