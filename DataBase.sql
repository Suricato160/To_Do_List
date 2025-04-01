
-- DOCKER --

docker run --name mysql-ToDoList -v ~/Documents/docker-data/mysql-ToDoList:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=blackfriday -p 3306:3306 -d mysql:8.0.41




-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ToDoList
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ToDoList
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ToDoList` DEFAULT CHARACTER SET utf8mb3 ;
USE `ToDoList` ;

-- -----------------------------------------------------
-- Table `ToDoList`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `role` ENUM('USER', 'ADMIN') NOT NULL,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `cognome` VARCHAR(45) NULL DEFAULT NULL,
  `mansione` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ToDoList`.`projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`projects` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `data_creation_project` DATETIME NULL DEFAULT NULL,
  `data_update_project` DATETIME NULL DEFAULT NULL,
  `data_deadline` DATETIME NULL DEFAULT NULL,
  `data_closed_project` DATETIME NULL DEFAULT NULL,
  `User_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_projects_User_idx` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_projects_User`
    FOREIGN KEY (`User_id`)
    REFERENCES `ToDoList`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ToDoList`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`tasks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titolo` VARCHAR(45) NULL DEFAULT NULL,
  `descrizione` VARCHAR(45) NULL DEFAULT NULL,
  `status` ENUM('PENDING', 'STARTED', 'WORK_IN_PROGRESS', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
  `data_pending` DATETIME NULL DEFAULT NULL,
  `data_started` DATETIME NULL DEFAULT NULL,
  `data_progress` DATETIME NULL DEFAULT NULL,
  `data_completed` DATETIME NULL DEFAULT NULL,
  `data_deadline` DATETIME NULL DEFAULT NULL,
  `categoria` VARCHAR(45) NULL DEFAULT NULL,
  `reminder` VARCHAR(45) NULL DEFAULT NULL,
  `notes` VARCHAR(500) NULL DEFAULT NULL,
  `repeat_task` VARCHAR(45) NULL DEFAULT NULL,
  `attachment` VARCHAR(45) NULL DEFAULT NULL,
  `data_created_task` DATETIME NULL DEFAULT NULL,
  `data_updated_task` DATETIME NULL DEFAULT NULL,
  `projects_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `assigner_id` INT NOT NULL,
  `completed` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_Task_projects1_idx` (`projects_id` ASC) VISIBLE,
  INDEX `fk_Task_User1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Task_User2_idx` (`assigner_id` ASC) VISIBLE,
  CONSTRAINT `fk_Task_projects1`
    FOREIGN KEY (`projects_id`)
    REFERENCES `ToDoList`.`projects` (`id`),
  CONSTRAINT `fk_Task_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ToDoList`.`user` (`id`),
  CONSTRAINT `fk_Task_User2`
    FOREIGN KEY (`assigner_id`)
    REFERENCES `ToDoList`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ToDoList`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ToDoList`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_comment` DATETIME NULL DEFAULT NULL,
  `text` VARCHAR(300) NULL DEFAULT NULL,
  `Task_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comments_Task1_idx` (`Task_id` ASC) VISIBLE,
  INDEX `fk_comments_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_comments_Task1`
    FOREIGN KEY (`Task_id`)
    REFERENCES `ToDoList`.`task` (`id`),
  CONSTRAINT `fk_comments_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ToDoList`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;





-- INSERIMENTO DATI --

USE `ToDoList`;

INSERT INTO `ToDoList`.`users` (username, password, email, role, nome, cognome, mansione) VALUES
('anna_pm1', 'pass123', 'anna1@greenwave.com', 'ADMIN', 'Anna', 'Rossi', 'Project Manager'),
('mario_pm2', 'pass123', 'mario@greenwave.com', 'ADMIN', 'Mario', 'Bianchi', 'Project Manager'),
('clara_pm3', 'pass123', 'clara@greenwave.com', 'USER', 'Clara', 'Verdi', 'Project Manager'),
('piero_pm4', 'pass123', 'piero@greenwave.com', 'USER', 'Piero', 'Neri', 'Project Manager'),
('lisa_pm5', 'pass123', 'lisa@greenwave.com', 'USER', 'Lisa', 'Gialli', 'Project Manager'),
('dario_pm6', 'pass123', 'dario@greenwave.com', 'USER', 'Dario', 'Azzurri', 'Project Manager'),
('luca_d1', 'pass123', 'luca1@greenwave.com', 'USER', 'Luca', 'Marroni', 'Designer'),
('giada_d2', 'pass123', 'giada@greenwave.com', 'USER', 'Giada', 'Grigi', 'Designer'),
('simone_d3', 'pass123', 'simone@greenwave.com', 'USER', 'Simone', 'Rosa', 'Designer'),
('elisa_d4', 'pass123', 'elisa@greenwave.com', 'USER', 'Elisa', 'Viola', 'Designer'),
('matteo_d5', 'pass123', 'matteo@greenwave.com', 'USER', 'Matteo', 'Arancio', 'Designer'),
('sara_d6', 'pass123', 'sara@greenwave.com', 'USER', 'Sara', 'Blu', 'Designer'),
('alessia_d7', 'pass123', 'alessia@greenwave.com', 'USER', 'Alessia', 'Russo', 'Designer'),
('sofia_c1', 'pass123', 'sofia1@greenwave.com', 'USER', 'Sofia', 'Verdi', 'Copywriter'),
('giovanni_c2', 'pass123', 'giovanni@greenwave.com', 'USER', 'Giovanni', 'Neri', 'Copywriter'),
('laura_c3', 'pass123', 'laura@greenwave.com', 'USER', 'Laura', 'Bianchi', 'Copywriter'),
('francesco_c4', 'pass123', 'francesco@greenwave.com', 'USER', 'Francesco', 'Rossi', 'Copywriter'),
('chiara_c5', 'pass123', 'chiara@greenwave.com', 'USER', 'Chiara', 'Gialli', 'Copywriter'),
('riccardo_c6', 'pass123', 'riccardo@greenwave.com', 'USER', 'Riccardo', 'Azzurri', 'Copywriter'),
('marco_s1', 'pass123', 'marco1@greenwave.com', 'USER', 'Marco', 'Grigi', 'Social Media Manager'),
('valentina_s2', 'pass123', 'valentina@greenwave.com', 'USER', 'Valentina', 'Marroni', 'Social Media Manager'),
('fabio_s3', 'pass123', 'fabio@greenwave.com', 'USER', 'Fabio', 'Rosa', 'Social Media Manager'),
('giorgia_s4', 'pass123', 'giorgia@greenwave.com', 'USER', 'Giorgia', 'Viola', 'Social Media Manager'),
('andrea_s5', 'pass123', 'andrea1@greenwave.com', 'USER', 'Andrea', 'Arancio', 'Social Media Manager'),
('paola_s6', 'pass123', 'paola@greenwave.com', 'USER', 'Paola', 'Blu', 'Social Media Manager'),
('giulia_f1', 'pass123', 'giulia1@greenwave.com', 'USER', 'Giulia', 'Russo', 'Sviluppatore Frontend'),
('paolo_f2', 'pass123', 'paolo1@greenwave.com', 'USER', 'Paolo', 'Verdi', 'Sviluppatore Frontend'),
('federico_f3', 'pass123', 'federico@greenwave.com', 'USER', 'Federico', 'Neri', 'Sviluppatore Frontend'),
('alice_f4', 'pass123', 'alice@greenwave.com', 'USER', 'Alice', 'Bianchi', 'Sviluppatore Frontend'),
('stefano_f5', 'pass123', 'stefano@greenwave.com', 'USER', 'Stefano', 'Rossi', 'Sviluppatore Frontend'),
('marta_f6', 'pass123', 'marta@greenwave.com', 'USER', 'Marta', 'Gialli', 'Sviluppatore Frontend'),
('lorenzo_f7', 'pass123', 'lorenzo@greenwave.com', 'USER', 'Lorenzo', 'Azzurri', 'Sviluppatore Frontend'),
('emma_f8', 'pass123', 'emma@greenwave.com', 'USER', 'Emma', 'Grigi', 'Sviluppatore Frontend'),
('andrea_b1', 'pass123', 'andrea2@greenwave.com', 'USER', 'Andrea', 'Marroni', 'Sviluppatore Backend'),
('elena_b2', 'pass123', 'elena1@greenwave.com', 'USER', 'Elena', 'Rosa', 'Sviluppatore Backend'),
('davide_b3', 'pass123', 'davide@greenwave.com', 'USER', 'Davide', 'Viola', 'Sviluppatore Backend'),
('silvia_b4', 'pass123', 'silvia@greenwave.com', 'USER', 'Silvia', 'Arancio', 'Sviluppatore Backend'),
('nicola_b5', 'pass123', 'nicola@greenwave.com', 'USER', 'Nicola', 'Blu', 'Sviluppatore Backend'),
('cristina_b6', 'pass123', 'cristina@greenwave.com', 'USER', 'Cristina', 'Russo', 'Sviluppatore Backend'),
('giacomo_b7', 'pass123', 'giacomo@greenwave.com', 'USER', 'Giacomo', 'Verdi', 'Sviluppatore Backend'),
('veronica_b8', 'pass123', 'veronica@greenwave.com', 'USER', 'Veronica', 'Neri', 'Sviluppatore Backend'),
('roberto_a1', 'pass123', 'roberto@greenwave.com', 'USER', 'Roberto', 'Bianchi', 'Analista'),
('sabrina_a2', 'pass123', 'sabrina@greenwave.com', 'USER', 'Sabrina', 'Rossi', 'Analista'),
('luca_a3', 'pass123', 'luca2@greenwave.com', 'USER', 'Luca', 'Gialli', 'Analista'),
('martina_a4', 'pass123', 'martina@greenwave.com', 'USER', 'Martina', 'Azzurri', 'Analista'),
('gianluca_a5', 'pass123', 'gianluca@greenwave.com', 'USER', 'Gianluca', 'Grigi', 'Analista'),
('ilaria_t1', 'pass123', 'ilaria@greenwave.com', 'USER', 'Ilaria', 'Marroni', 'Tester'),
('vincenzo_t2', 'pass123', 'vincenzo@greenwave.com', 'USER', 'Vincenzo', 'Rosa', 'Tester'),
('beatrice_t3', 'pass123', 'beatrice@greenwave.com', 'USER', 'Beatrice', 'Viola', 'Tester'),
('tommaso_t4', 'pass123', 'tommaso@greenwave.com', 'USER', 'Tommaso', 'Arancio', 'Tester'),
('carla_t5', 'pass123', 'carla@greenwave.com', 'USER', 'Carla', 'Blu', 'Tester');

INSERT INTO `ToDoList`.`projects` (Title, description, data_creation_project, data_update_project, data_deadline, data_closed_project, User_id) VALUES
('Green Product Launch', 'Lancio prodotto eco-sostenibile', '2025-04-01 09:00:00', '2025-04-01 09:00:00', '2025-04-30 17:00:00', '2025-05-05 17:00:00', 1),
('Eco Event Promotion', 'Promozione evento green', '2025-04-02 09:00:00', '2025-04-02 09:00:00', '2025-05-15 17:00:00', NULL, 2),
('Eco Tourism Website', 'Sito web eco-turismo', '2025-04-03 09:00:00', '2025-04-03 09:00:00', '2025-05-30 17:00:00', '2025-06-05 17:00:00', 3),
('Sustainability App', 'App per sostenibilità', '2025-04-04 09:00:00', '2025-04-04 09:00:00', '2025-06-15 17:00:00', NULL, 4),
('Green Video Campaign', 'Video promozionale green', '2025-04-05 09:00:00', '2025-04-05 09:00:00', '2025-06-30 17:00:00', NULL, 5);

INSERT INTO `ToDoList`.`tasks` (titolo, descrizione, status, data_pending, data_deadline, data_completed, categoria, projects_id, user_id, assigner_id, completed) VALUES
('Timeline P1', 'Definire scadenze', 'COMPLETED', '2025-04-01 09:00:00', '2025-04-05 17:00:00', '2025-04-05 17:00:00', 'Gestione', 1, 1, 1, 1),
('Logo P1', 'Creare logo', 'COMPLETED', '2025-04-02 09:00:00', '2025-04-10 17:00:00', '2025-05-02 17:00:00', 'Design', 1, 7, 1, 1),
('Slogan P1', 'Testo slogan', 'COMPLETED', '2025-04-02 09:00:00', '2025-04-08 17:00:00', '2025-05-01 17:00:00', 'Copywriting', 1, 14, 1, 1),
('Post P1', 'Calendario social', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-10 17:00:00', '2025-05-03 17:00:00', 'Social Media', 1, 20, 1, 1),
('Landing P1', 'Progettare pagina', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-15 17:00:00', '2025-05-04 17:00:00', 'Frontend', 1, 26, 1, 1),
('DB P1', 'Configurare DB', 'COMPLETED', '2025-04-01 09:00:00', '2025-04-05 17:00:00', '2025-04-05 17:00:00', 'Backend', 1, 34, 1, 1),
('Analisi P1', 'Ricerca mercato', 'COMPLETED', '2025-04-03 09:00:00', '2025-04-12 17:00:00', '2025-05-02 17:00:00', 'Analisi', 1, 42, 1, 1),
('Test P1', 'Verifica UI', 'COMPLETED', '2025-04-10 09:00:00', '2025-04-20 17:00:00', '2025-05-03 17:00:00', 'Testing', 1, 46, 1, 1),
('Revisione P1', 'Feedback design', 'COMPLETED', '2025-04-15 09:00:00', '2025-04-20 17:00:00', '2025-05-04 17:00:00', 'Design', 1, 8, 1, 1),
('Brochure P1', 'Testi brochure', 'COMPLETED', '2025-04-03 09:00:00', '2025-04-12 17:00:00', '2025-05-02 17:00:00', 'Copywriting', 1, 15, 1, 1),
('Ads P1', 'Campagne ads', 'COMPLETED', '2025-04-10 09:00:00', '2025-04-20 17:00:00', '2025-05-03 17:00:00', 'Social Media', 1, 21, 1, 1),
('Mobile P1', 'Design responsive', 'COMPLETED', '2025-04-10 09:00:00', '2025-04-18 17:00:00', '2025-05-04 17:00:00', 'Frontend', 1, 27, 1, 1),
('API P1', 'Endpoint dati', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-15 17:00:00', '2025-05-03 17:00:00', 'Backend', 1, 35, 1, 1),
('Report P1', 'Report cliente', 'COMPLETED', '2025-04-20 09:00:00', '2025-04-30 17:00:00', '2025-05-05 17:00:00', 'Gestione', 1, 1, 1, 1),
('Packaging P1', 'Mockup confezione', 'COMPLETED', '2025-04-03 09:00:00', '2025-04-12 17:00:00', '2025-05-02 17:00:00', 'Design', 1, 9, 1, 1),
('Video P1', 'Script spot', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-15 17:00:00', '2025-05-03 17:00:00', 'Copywriting', 1, 16, 1, 1),
('Performance P1', 'Analisi social', 'COMPLETED', '2025-04-25 09:00:00', '2025-04-30 17:00:00', '2025-05-05 17:00:00', 'Social Media', 1, 22, 1, 1),
('Deploy P1', 'Sito online', 'COMPLETED', '2025-04-25 09:00:00', '2025-04-30 17:00:00', '2025-05-05 17:00:00', 'Backend', 1, 36, 1, 1),
('Timeline P2', 'Definire scadenze', 'COMPLETED', '2025-04-02 09:00:00', '2025-04-06 17:00:00', '2025-04-06 17:00:00', 'Gestione', 2, 2, 2, 1),
('Poster P2', 'Grafica evento', 'WORK_IN_PROGRESS', '2025-04-03 09:00:00', '2025-04-11 17:00:00', NULL, 'Design', 2, 10, 2, 0),
('Invito P2', 'Testo email', 'STARTED', '2025-04-03 09:00:00', '2025-04-09 17:00:00', NULL, 'Copywriting', 2, 17, 2, 0),
('Stories P2', 'Contenuti IG', 'WORK_IN_PROGRESS', '2025-04-06 09:00:00', '2025-04-11 17:00:00', NULL, 'Social Media', 2, 23, 2, 0),
('Pagina P2', 'Sito evento', 'WORK_IN_PROGRESS', '2025-04-06 09:00:00', '2025-04-16 17:00:00', NULL, 'Frontend', 2, 28, 2, 0),
('DB P2', 'DB partecipanti', 'COMPLETED', '2025-04-02 09:00:00', '2025-04-06 17:00:00', '2025-04-06 17:00:00', 'Backend', 2, 37, 2, 1),
('Target P2', 'Studio audience', 'PENDING', '2025-04-04 09:00:00', '2025-04-13 17:00:00', NULL, 'Analisi', 2, 43, 2, 0),
('Test P2', 'Verifica sito', 'PENDING', '2025-04-11 09:00:00', '2025-04-21 17:00:00', NULL, 'Testing', 2, 47, 2, 0),
('Revisione P2', 'Feedback design', 'PENDING', '2025-04-16 09:00:00', '2025-04-21 17:00:00', NULL, 'Design', 2, 11, 2, 0),
('Social P2', 'Post evento', 'PENDING', '2025-04-04 09:00:00', '2025-04-13 17:00:00', NULL, 'Copywriting', 2, 18, 2, 0),
('Sponsor P2', 'Ads evento', 'PENDING', '2025-04-11 09:00:00', '2025-04-21 17:00:00', NULL, 'Social Media', 2, 24, 2, 0),
('UI P2', 'Interfaccia responsive', 'PENDING', '2025-04-11 09:00:00', '2025-04-19 17:00:00', NULL, 'Frontend', 2, 29, 2, 0),
('API P2', 'Endpoint registrazione', 'WORK_IN_PROGRESS', '2025-04-06 09:00:00', '2025-04-16 17:00:00', NULL, 'Backend', 2, 38, 2, 0),
('Report P2', 'Report cliente', 'PENDING', '2025-04-25 09:00:00', '2025-05-15 17:00:00', NULL, 'Gestione', 2, 2, 2, 0),
('Banner P2', 'Grafica banner', 'PENDING', '2025-04-04 09:00:00', '2025-04-13 17:00:00', NULL, 'Design', 2, 12, 2, 0),
('Promo P2', 'Testo video', 'PENDING', '2025-04-06 09:00:00', '2025-04-16 17:00:00', NULL, 'Copywriting', 2, 19, 2, 0),
('Monitor P2', 'Performance sponsor', 'PENDING', '2025-04-26 09:00:00', '2025-05-15 17:00:00', NULL, 'Social Media', 2, 25, 2, 0),
('Deploy P2', 'Sito online', 'PENDING', '2025-04-26 09:00:00', '2025-05-15 17:00:00', NULL, 'Backend', 2, 39, 2, 0),
('Timeline P3', 'Definire scadenze', 'COMPLETED', '2025-04-03 09:00:00', '2025-04-07 17:00:00', '2025-04-07 17:00:00', 'Gestione', 3, 3, 3, 1),
('Layout P3', 'Grafica sito', 'COMPLETED', '2025-04-04 09:00:00', '2025-04-12 17:00:00', '2025-06-02 17:00:00', 'Design', 3, 13, 3, 1),
('Homepage P3', 'Contenuto sito', 'COMPLETED', '2025-04-04 09:00:00', '2025-04-10 17:00:00', '2025-06-01 17:00:00', 'Copywriting', 3, 14, 3, 1),
('Campagne P3', 'Social strategy', 'COMPLETED', '2025-04-07 09:00:00', '2025-04-12 17:00:00', '2025-06-03 17:00:00', 'Social Media', 3, 20, 3, 1),
('Sito P3', 'Frontend sito', 'COMPLETED', '2025-04-07 09:00:00', '2025-04-17 17:00:00', '2025-06-04 17:00:00', 'Frontend', 3, 30, 3, 1),
('DB P3', 'Database utenti', 'COMPLETED', '2025-04-03 09:00:00', '2025-04-07 17:00:00', '2025-04-07 17:00:00', 'Backend', 3, 40, 3, 1),
('SEO P3', 'Ottimizzazione motori', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-14 17:00:00', '2025-06-02 17:00:00', 'Analisi', 3, 44, 3, 1),
('Test P3', 'Verifica mobile', 'COMPLETED', '2025-04-12 09:00:00', '2025-04-22 17:00:00', '2025-06-03 17:00:00', 'Testing', 3, 48, 3, 1),
('Revisione P3', 'Feedback UI', 'COMPLETED', '2025-04-17 09:00:00', '2025-04-22 17:00:00', '2025-06-04 17:00:00', 'Design', 3, 7, 3, 1),
('Blog P3', 'Articoli eco-turismo', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-14 17:00:00', '2025-06-02 17:00:00', 'Copywriting', 3, 15, 3, 1),
('Ads P3', 'Campagne Google', 'COMPLETED', '2025-04-12 09:00:00', '2025-04-22 17:00:00', '2025-06-03 17:00:00', 'Social Media', 3, 21, 3, 1),
('Animazioni P3', 'Effetti UI', 'COMPLETED', '2025-04-12 09:00:00', '2025-04-20 17:00:00', '2025-06-04 17:00:00', 'Frontend', 3, 31, 3, 1),
('API P3', 'Endpoint prenotazioni', 'COMPLETED', '2025-04-07 09:00:00', '2025-04-17 17:00:00', '2025-06-03 17:00:00', 'Backend', 3, 34, 3, 1),
('Report P3', 'Report cliente', 'COMPLETED', '2025-05-10 09:00:00', '2025-05-30 17:00:00', '2025-06-05 17:00:00', 'Gestione', 3, 3, 3, 1),
('Footer P3', 'Design footer', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-14 17:00:00', '2025-06-02 17:00:00', 'Design', 3, 8, 3, 1),
('FAQ P3', 'Sezione domande', 'COMPLETED', '2025-04-07 09:00:00', '2025-04-17 17:00:00', '2025-06-03 17:00:00', 'Copywriting', 3, 16, 3, 1),
('Monitor P3', 'Performance sito', 'COMPLETED', '2025-05-10 09:00:00', '2025-05-30 17:00:00', '2025-06-05 17:00:00', 'Social Media', 3, 22, 3, 1),
('Deploy P3', 'Sito online', 'COMPLETED', '2025-05-10 09:00:00', '2025-05-30 17:00:00', '2025-06-05 17:00:00', 'Backend', 3, 35, 3, 1),
('Timeline P4', 'Definire scadenze', 'COMPLETED', '2025-04-04 09:00:00', '2025-04-08 17:00:00', '2025-04-08 17:00:00', 'Gestione', 4, 4, 4, 1),
('App UI P4', 'Interfaccia app', 'WORK_IN_PROGRESS', '2025-04-05 09:00:00', '2025-04-13 17:00:00', NULL, 'Design', 4, 9, 4, 0),
('Contenuti P4', 'Testi app', 'STARTED', '2025-04-05 09:00:00', '2025-04-11 17:00:00', NULL, 'Copywriting', 4, 17, 4, 0),
('Lancio P4', 'Social app', 'WORK_IN_PROGRESS', '2025-04-08 09:00:00', '2025-04-13 17:00:00', NULL, 'Social Media', 4, 23, 4, 0),
('Frontend P4', 'Sviluppo UI', 'WORK_IN_PROGRESS', '2025-04-08 09:00:00', '2025-04-18 17:00:00', NULL, 'Frontend', 4, 32, 4, 0),
('Backend P4', 'Database app', 'COMPLETED', '2025-04-04 09:00:00', '2025-04-08 17:00:00', '2025-04-08 17:00:00', 'Backend', 4, 36, 4, 1),
('Utenti P4', 'Analisi target', 'PENDING', '2025-04-06 09:00:00', '2025-04-15 17:00:00', NULL, 'Analisi', 4, 45, 4, 0),
('Test P4', 'Verifica app', 'PENDING', '2025-04-13 09:00:00', '2025-04-23 17:00:00', NULL, 'Testing', 4, 49, 4, 0),
('Revisione P4', 'Feedback UI', 'PENDING', '2025-04-18 09:00:00', '2025-04-23 17:00:00', NULL, 'Design', 4, 10, 4, 0),
('Notifiche P4', 'Testi notifiche', 'PENDING', '2025-04-06 09:00:00', '2025-04-15 17:00:00', NULL, 'Copywriting', 4, 18, 4, 0),
('Promo P4', 'Campagne app', 'PENDING', '2025-04-13 09:00:00', '2025-04-23 17:00:00', NULL, 'Social Media', 4, 24, 4, 0),
('Ottimizzazione P4', 'Performance app', 'PENDING', '2025-04-13 09:00:00', '2025-04-21 17:00:00', NULL, 'Frontend', 4, 33, 4, 0),
('API P4', 'Endpoint dati', 'WORK_IN_PROGRESS', '2025-04-08 09:00:00', '2025-04-18 17:00:00', NULL, 'Backend', 4, 37, 4, 0),
('Report P4', 'Report cliente', 'PENDING', '2025-05-25 09:00:00', '2025-06-15 17:00:00', NULL, 'Gestione', 4, 4, 4, 0),
('Icone P4', 'Design icone', 'PENDING', '2025-04-06 09:00:00', '2025-04-15 17:00:00', NULL, 'Design', 4, 11, 4, 0),
('Tutorial P4', 'Guida utente', 'PENDING', '2025-04-08 09:00:00', '2025-04-18 17:00:00', NULL, 'Copywriting', 4, 19, 4, 0),
('Monitor P4', 'Performance social', 'PENDING', '2025-05-25 09:00:00', '2025-06-15 17:00:00', NULL, 'Social Media', 4, 25, 4, 0),
('Deploy P4', 'Rilascio app', 'PENDING', '2025-05-25 09:00:00', '2025-06-15 17:00:00', NULL, 'Backend', 4, 38, 4, 0),
('Timeline P5', 'Definire scadenze', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-09 17:00:00', '2025-04-09 17:00:00', 'Gestione', 5, 5, 5, 1),
('Storyboard P5', 'Grafica video', 'WORK_IN_PROGRESS', '2025-04-06 09:00:00', '2025-04-14 17:00:00', NULL, 'Design', 5, 12, 5, 0),
('Script P5', 'Testo video', 'STARTED', '2025-04-06 09:00:00', '2025-04-12 17:00:00', NULL, 'Copywriting', 5, 14, 5, 0),
('Teaser P5', 'Social teaser', 'WORK_IN_PROGRESS', '2025-04-09 09:00:00', '2025-04-14 17:00:00', NULL, 'Social Media', 5, 20, 5, 0),
('Player P5', 'Player video', 'WORK_IN_PROGRESS', '2025-04-09 09:00:00', '2025-04-19 17:00:00', NULL, 'Frontend', 5, 26, 5, 0),
('Backend P5', 'Upload video', 'COMPLETED', '2025-04-05 09:00:00', '2025-04-09 17:00:00', '2025-04-09 17:00:00', 'Backend', 5, 39, 5, 1),
('Reach P5', 'Analisi target', 'PENDING', '2025-04-07 09:00:00', '2025-04-16 17:00:00', NULL, 'Analisi', 5, 42, 5, 0),
('Test P5', 'Verifica qualità', 'PENDING', '2025-04-14 09:00:00', '2025-04-24 17:00:00', NULL, 'Testing', 5, 46, 5, 0),
('Revisione P5', 'Feedback grafica', 'PENDING', '2025-04-19 09:00:00', '2025-04-24 17:00:00', NULL, 'Design', 5, 13, 5, 0),
('Caption P5', 'Didascalie video', 'PENDING', '2025-04-07 09:00:00', '2025-04-16 17:00:00', NULL, 'Copywriting', 5, 15, 5, 0),
('Ads P5', 'Campagne video', 'PENDING', '2025-04-14 09:00:00', '2025-04-24 17:00:00', NULL, 'Social Media', 5, 21, 5, 0),
('Ottimizzazione P5', 'Velocità player', 'PENDING', '2025-04-14 09:00:00', '2025-04-22 17:00:00', NULL, 'Frontend', 5, 27, 5, 0),
('API P5', 'Endpoint streaming', 'WORK_IN_PROGRESS', '2025-04-09 09:00:00', '2025-04-19 17:00:00', NULL, 'Backend', 5, 40, 5, 0),
('Report P5', 'Report cliente', 'PENDING', '2025-06-10 09:00:00', '2025-06-30 17:00:00', NULL, 'Gestione', 5, 5, 5, 0),
('Thumbnail P5', 'Anteprima video', 'PENDING', '2025-04-07 09:00:00', '2025-04-16 17:00:00', NULL, 'Design', 5, 7, 5, 0),
('Promo P5', 'Testo teaser', 'PENDING', '2025-04-09 09:00:00', '2025-04-19 17:00:00', NULL, 'Copywriting', 5, 16, 5, 0),
('Monitor P5', 'Performance video', 'PENDING', '2025-06-10 09:00:00', '2025-06-30 17:00:00', NULL, 'Social Media', 5, 22, 5, 0),
('Deploy P5', 'Rilascio video', 'PENDING', '2025-06-10 09:00:00', '2025-06-30 17:00:00', NULL, 'Backend', 5, 34, 5, 0);

INSERT INTO `ToDoList`.`comments` (data_comment, text, Task_id, user_id) VALUES
('2025-04-05 10:00:00', 'Timeline approvata, procedere con i task.', 1, 1), -- Progetto 1, Anna (PM)
('2025-05-02 14:00:00', 'Logo completato, serve feedback.', 2, 7), -- Progetto 1, Luca (Designer)
('2025-04-10 09:30:00', 'Testi da rivedere per il tono.', 3, 14), -- Progetto 1, Sofia (Copywriter)
('2025-04-07 15:00:00', 'Aggiungere più immagini all’evento.', 20, 10), -- Progetto 2, Giada (Designer)
('2025-04-15 11:00:00', 'Sito responsive da testare.', 23, 28), -- Progetto 2, Paolo (Frontend)
('2025-06-02 16:00:00', 'SEO migliorato, risultati ok.', 42, 44), -- Progetto 3, Martina (Analista)
('2025-04-10 12:00:00', 'App UI quasi pronta.', 56, 9),
('2025-04-12 13:00:00', 'Testi notifiche da approvare.', 64, 18), 
('2025-04-15 14:00:00', 'Storyboard in revisione.', 74, 12),
('2025-04-20 10:00:00', 'API streaming da ottimizzare.', 85, 40); 


INSERT INTO `ToDoList`.`comments` (data_comment, text, Task_id, user_id) VALUES
-- Progetto 1: Green Product Launch (Completato, scaduto)
('2025-04-05 10:00:00', 'Timeline approvata, procedere con i task.', 1, 1), -- Anna (PM)
('2025-05-02 14:00:00', 'Logo completato, serve feedback.', 2, 7), -- Luca (Designer)
('2025-04-10 09:30:00', 'Testi da rivedere per il tono.', 3, 14), -- Sofia (Copywriter)
('2025-05-03 15:00:00', 'Post social pubblicati, risultati in arrivo.', 4, 20), -- Marco (Social Media)
('2025-05-04 11:30:00', 'Landing page pronta per il deploy.', 5, 26), -- Giulia (Frontend)
('2025-04-05 16:00:00', 'Database configurato, test ok.', 6, 34), -- Andrea (Backend)
('2025-04-07 15:00:00', 'Aggiungere più immagini all’evento.', 20, 10), -- Giada (Designer)
('2025-04-15 11:00:00', 'Sito responsive da testare.', 23, 28), -- Paolo (Frontend)
('2025-04-06 12:00:00', 'Timeline confermata, iniziare i lavori.', 19, 2), -- Mario (PM)
('2025-04-09 14:00:00', 'Testo invito da allineare con il tema.', 21, 17), -- Giovanni (Copywriter)
('2025-04-11 09:00:00', 'Sponsor contattati, aspettiamo risposta.', 29, 24), -- Giorgia (Social Media)
('2025-04-16 10:00:00', 'API in sviluppo, servono più dati.', 31, 38), -- Davide (Backend)
('2025-06-02 16:00:00', 'SEO migliorato, risultati ok.', 42, 44), -- Martina (Analista)
('2025-04-07 09:00:00', 'Timeline stabilita, via ai task.', 37, 3), -- Clara (PM)
('2025-06-02 13:00:00', 'Layout grafico approvato dal cliente.', 38, 13), -- Alessia (Designer)
('2025-06-01 15:00:00', 'Testi homepage pronti, serve revisione.', 39, 14), -- Sofia (Copywriter)
('2025-06-03 10:00:00', 'Campagne social attive, buon engagement.', 40, 20), -- Marco (Social Media)
('2025-06-04 12:00:00', 'Sito responsive, tutto funzionante.', 41, 30), -- Federico (Frontend)
('2025-04-10 12:00:00', 'App UI quasi pronta.', 56, 9), -- Matteo (Designer)
('2025-04-12 13:00:00', 'Testi notifiche da approvare.', 64, 18), -- Francesco (Copywriter)
('2025-04-08 14:00:00', 'Timeline rivista, rispettare le date.', 55, 4), -- Piero (PM)
('2025-04-13 11:00:00', 'Promo social in preparazione.', 58, 23), -- Valentina (Social Media)
('2025-04-18 09:00:00', 'Frontend in corso, serve test.', 59, 32), -- Stefano (Frontend)
('2025-04-08 15:00:00', 'Backend stabile, procedere con API.', 60, 36), -- Silvia (Backend)
('2025-04-15 16:00:00', 'Analisi target da approfondire.', 61, 45), -- Gianluca (Analista)
('2025-04-15 14:00:00', 'Storyboard in revisione.', 74, 12), -- Sara (Designer)
('2025-04-20 10:00:00', 'API streaming da ottimizzare.', 85, 40), -- Veronica (Backend)
('2025-04-09 13:00:00', 'Timeline ok, iniziare subito.', 73, 5), -- Lisa (PM)
('2025-04-12 15:00:00', 'Script da rifinire per durata.', 75, 14), -- Sofia (Copywriter)
('2025-04-14 11:00:00', 'Teaser pronto per i social.', 76, 20), -- Marco (Social Media)
('2025-04-19 12:00:00', 'Player testato, piccoli bug.', 77, 26); -- Giulia (Frontend)




Error Code: 1452. Cannot add or update a child row: a foreign key constraint fails (`todolist`.`projects`, CONSTRAINT `fk_projects_User` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`))
