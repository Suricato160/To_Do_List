const apiUrl = 'http://localhost:8080/api/tasks'; // URL del tuo backend

// Carica le attività all'avvio
document.addEventListener('DOMContentLoaded', loadTasks);

// Gestisci il form per aggiungere attività
document.getElementById('task-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const description = document.getElementById('task-input').value;

    const task = {
        description: description,
        completed: false
    };

    await fetch(apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(task)
    });

    document.getElementById('task-input').value = '';
    loadTasks();
});

// Carica e mostra tutte le attività
async function loadTasks() {
    const response = await fetch(apiUrl);
    const tasks = await response.json();
    const taskList = document.getElementById('task-list');
    taskList.innerHTML = '';

    tasks.forEach(task => {
        const li = document.createElement('li');
        li.className = 'task-item' + (task.completed ? ' completed' : '');

        const span = document.createElement('span');
        span.textContent = task.description;
        span.addEventListener('click', () => toggleTask(task.id, task.completed));

        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = 'Elimina';
        deleteBtn.addEventListener('click', () => deleteTask(task.id));

        li.appendChild(span);
        li.appendChild(deleteBtn);
        taskList.appendChild(li);
    });
}

// Cambia lo stato di completamento
async function toggleTask(id, completed) {
    const task = {
        completed: !completed
    };

    await fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(task)
    });

    loadTasks();
}

// Elimina un'attività
async function deleteTask(id) {
    await fetch(`${apiUrl}/${id}`, {
        method: 'DELETE'
    });

    loadTasks();
}