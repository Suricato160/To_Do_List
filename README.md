# To-Do List - Project Manager

## Descrizione

**To-Do List Project Manager** è un'applicazione web completa per la gestione di progetti e task. Permette agli utenti di creare progetti, organizzare attività con stati differenti, monitorare progressi e scadenze, e visualizzare statistiche attraverso interfacce intuitive. L'applicazione supporta l'assegnazione di task a vari utenti e la categorizzazione delle attività per una gestione ottimale del lavoro individuale o di squadra.

## Funzionalità Principali

### Gestione Progetti

- Creazione e gestione di progetti con titolo, descrizione e scadenza
- Monitoraggio dello stato di avanzamento dei progetti
- Visualizzazione percentuale di completamento basata sulle task associate
- Dashboard con indicatori di performance e statistiche

### Gestione Task

- Creazione, modifica ed eliminazione di task con dettagli completi
- Assegnazione task a utenti specifici
- Tracciamento di stati diversi (In attesa, Iniziate, In corso, Completate)
- Prioritizzazione delle attività (Bassa, Media, Alta)
- Categorizzazione delle task per una migliore organizzazione
- Filtri per visualizzare task in base a stato, data o categoria

### Sistema di Commenti

- Possibilità di aggiungere commenti alle task
- Cronologia delle interazioni e aggiornamenti
- Comunicazione integrata tra gli utenti del progetto

### Interfaccia Utente

- Design moderno e responsive con Bootstrap 5
- Tema chiaro/scuro personalizzabile
- Visualizzazione intuitiva delle scadenze e priorità
- Indicatori visivi per lo stato delle attività
- Menu laterale per una navigazione semplice

### Funzionalità Aggiuntive

- Sistema di autenticazione utenti con ruoli differenziati
- Notifiche per task in scadenza (in sviluppo)
- Statistiche dettagliate con grafici

## Tecnologie Utilizzate

### Backend

- Java 17
- Spring Boot 3.x
- Spring Security per l'autenticazione
- Spring Data JPA per la persistenza

### Frontend

- Thymeleaf come template engine
- HTML5, CSS3 e JavaScript
- Bootstrap 5 per il design responsivo
- Font Awesome per le icone
- jQuery per funzionalità AJAX
- Select2 per input avanzati

### Database

- MySQL/PostgreSQL per la persistenza dei dati
- Modello relazionale con entità principali (User, Project, Task, Comment)

### Altre Tecnologie

- Maven per la gestione delle dipendenze
- Git per il controllo versione

## Struttura del Database

### Users

- Dati personali (nome, cognome, email)
- Credenziali di accesso (username, password)
- Ruolo (USER, PROJECT_MANAGER, ecc.)
- Mansione professionale

### Projects

- Informazioni base (titolo, descrizione)
- Date rilevanti (creazione, aggiornamento, scadenza, chiusura)
- Relazione con l'utente creatore
- Collezione di task associate

### Tasks

- Dettagli (titolo, descrizione, priorità, stato)
- Timeline (date di creazione, aggiornamento, scadenza, completamento)
- Categorizzazione
- Relazioni con progetto, utente assegnato e creatore

### Comments

- Contenuto testuale
- Data di creazione
- Relazioni con task e utente

## Come Iniziare

### Prerequisiti

- JDK 17 o superiore
- Maven 3.6+
- MySQL/PostgreSQL
- Git (opzionale)

### Installazione

1. Clona il repository:

   ```bash
   git clone https://github.com/tuonome/To_Do_List.git
   cd To_Do_List
   ```

2. Configura il file `application.properties` con i dettagli del tuo database:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/todolist_db
   spring.datasource.username=root
   spring.datasource.password=blackfriday
   spring.jpa.hibernate.ddl-auto=validate
   ```

3. Compila e avvia l'applicazione:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. Accedi all'applicazione nel browser:
   ```
   http://localhost:8080
   ```

## Funzionalità in Sviluppo

- Notifiche email per task in scadenza
- Notifiche browser per aggiornamenti
- Visualizzazione con grafici avanzati
- Caricamento file e allegati per task
- App mobile companion



## Disclaimer - Licenza Non Garantita
**TO-DO LIST PROJECT MANAGER** VIENE FORNITO "COSÌ COM'È", SENZA GARANZIE DI ALCUN TIPO, ESPLICITE O IMPLICITE, INCLUSE, A TITOLO ESEMPLIFICATIVO MA NON ESAUSTIVO, LE GARANZIE DI COMMERCIABILITÀ, IDONEITÀ PER UN PARTICOLARE SCOPO E NON VIOLAZIONE. IN NESSUN CASO GLI AUTORI O I TITOLARI DEL COPYRIGHT SARANNO RESPONSABILI PER QUALSIASI RECLAMO, DANNO O ALTRA RESPONSABILITÀ, SIA IN UN'AZIONE DI CONTRATTO, TORTO O ALTRO, DERIVANTE DA, FUORI O IN CONNESSIONE CON IL SOFTWARE O L'USO O ALTRI RAPPORTI NEL SOFTWARE.

L'utente si assume la piena responsabilità per:
- La sicurezza e l'integrità dei dati inseriti nell'applicazione
- La conformità alle leggi sulla protezione dei dati personali
- L'uso appropriato del software in ambienti di produzione
- Eventuali modifiche o personalizzazioni apportate al codice base

Questa applicazione è stata sviluppata principalmente a scopo educativo e dimostrativo e potrebbe richiedere ulteriori revisioni per l'uso in ambienti di produzione con dati sensibili.

## Contatti

Per domande, suggerimenti o collaborazioni, contattami:

- Email: rytosm43@gmail.com (Osama Rytami)
- Email: adriano.pascolo@gmail.com (Adriano Nevio Pascolo)
- GitHub: @BoomTrading
- GitHub: @Suricato160

---

_Ultimo aggiornamento: 7 Aprile 2025_

## Struttura del Progetto

TO_DO_LIST/
├── .vscode/
│ └── settings.json # Configurazioni VS Code
├── src/
│ ├── main/
│ │ ├── java/com/webtodolist/
│ │ │ ├── controller/ # Controller REST
│ │ │ │ └── TaskController.java
│ │ │ ├── entity/ # Entità JPA
│ │ │ │ └── Task.java
│ │ │ ├── repository/ # Repository JPA
│ │ │ │ └── TaskRepository.java
│ │ │ ├── todolist/ # Classe principale
│ │ │ │ └── TodolistApplication.java
│ │ ├── resources/
│ │ │ ├── static/ # File statici per il frontend (HTML, CSS, JS)
│ │ │ ├── templates/ # Template (se usi Thymeleaf)
│ │ │ └── application.properties # Configurazioni Spring Boot
│ ├── test/ # Test unitari (da popolare)
├── target/ # File generati da Maven
├── .gitattributes # Configurazioni Git
├── HELP.md # Documentazione di supporto (da aggiornare)
├── mvnw # Maven wrapper (Unix)
├── mvnw.cmd # Maven wrapper (Windows)
├── pom.xml # File di configurazione Maven
└── README.md # Questo file

## Database

User

id (INT, PK): Identificatore univoco.
username (VARCHAR(45)): Nome utente.
password (VARCHAR(45)): Password.
email (VARCHAR(45)): Email.
role (ENUM): Ruolo utente.
nome (VARCHAR(45)): Nome.
cognome (VARCHAR(45)): Cognome.

projects

id (INT, PK): Identificatore univoco.
Title (VARCHAR(45)): Titolo del progetto.
description (VARCHAR(45)): Descrizione.
data_creation_project (DATETIME): Data creazione.
data_update_project (DATETIME): Ultimo aggiornamento.
data_deadline (DATETIME): Scadenza.
data_closed_project (DATETIME): Chiusura.
User_id (INT, FK): Riferimento a User.

Task

id (INT, PK): Identificatore univoco.
titolo (VARCHAR(45)): Titolo attività.
descrizione (VARCHAR(45)): Descrizione.
status (ENUM): Stato (es. "pending", "in progress", "completed").
data_pending (DATETIME): Data attesa.
data_started (DATETIME): Data inizio.
data_progress (DATETIME): Data progresso.
data_completed (DATETIME): Data completamento.
data_deadline (DATETIME): Scadenza.
categoria (VARCHAR(45)): Categoria.
reminder (VARCHAR(45)): Promemoria.
notes (VARCHAR(500)): Note.
repeat_task (VARCHAR(45)): Ripetizione.
attachment (VARCHAR(45)): Allegato.
data_created_task (DATETIME): Data creazione.
data_updated_task (DATETIME): Ultimo aggiornamento.
project_id (INT, FK): Riferimento a projects.

Task_assignments

task_id (INT, FK): Riferimento a Task.
User_id (INT, FK): Riferimento a User.

Relazioni:
User → projects (1:N)
projects → Task (1:N)
Task User (M:N tramite Task_assignments)

