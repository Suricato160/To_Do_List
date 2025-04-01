// scripts.js

// Hardcoded user ID for this example (replace with dynamic user ID in a real app)
const userId = 3;

// Fetch tasks for the user from the backend
async function fetchTasks() {
    try {
        const response = await fetch(`/api/tasks/user/${userId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch tasks');
        }
        const tasks = await response.json();
        return tasks;
    } catch (error) {
        console.error('Error fetching tasks:', error);
        return [];
    }
}

// Populate the To-Do section with incomplete tasks
function populateToDoTasks(tasks) {
    const todoTasksContainer = document.querySelector('#todo-tasks');
    const todoTasks = tasks.filter(task => task.status !== 'COMPLETED');
    todoTasksContainer.innerHTML = ''; // Clear existing content

    todoTasks.forEach(task => {
        const statusText = task.status === 'PENDING' ? 'Not Started' : 'In Progress';
        const taskCard = `
            <div class="col-md-6">
                <div class="task-card">
                    <div class="task-header">
                        <h6>${task.titolo}</h6>
                        <span class="priority">Priority: Moderate</span>
                    </div>
                    <p class="task-details">${task.descrizione}</p>
                    <p class="task-status">Status: ${statusText}</p>
                </div>
            </div>
        `;
        todoTasksContainer.innerHTML += taskCard;
    });
}
async function createTask(taskData) {
    try {
        const response = await fetch('/api/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(taskData), // e.g., { titolo: "New Task", descrizione: "Details", status: "PENDING", userId: 3 }
        });
        if (!response.ok) {
            throw new Error('Failed to create task');
        }
        const newTask = await response.json();
        // Refresh the task list
        initializeDashboard();
    } catch (error) {
        console.error('Error creating task:', error);
    }
}
async function deleteTask(taskId) {
    try {
        const response = await fetch(`/api/tasks/${taskId}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error('Failed to delete task');
        }
        // Refresh the task list
        initializeDashboard();
    } catch (error) {
        console.error('Error deleting task:', error);
    }
}

// Populate the Completed section with completed tasks
function populateCompletedTasks(tasks) {
    const completedTasksContainer = document.querySelector('#completed-tasks');
    const completedTasks = tasks.filter(task => task.status === 'COMPLETED');
    completedTasksContainer.innerHTML = ''; // Clear existing content

    completedTasks.forEach(task => {
        const completedTaskCard = `
            <div class="col-md-6">
                <div class="completed-task">
                    <img src="https://via.placeholder.com/50" alt="Task Image">
                    <div class="task-info">
                        <h6>${task.titolo}</h6>
                        <p>${task.descrizione}</p>
                        <p class="status">Status: Completed</p>
                    </div>
                </div>
            </div>
        `;
        completedTasksContainer.innerHTML += completedTaskCard;
    });
}

// Update task status percentages (completed, in-progress, not started)
function updateTaskStatusPercentages(tasks) {
    const totalTasks = tasks.length;
    const completedTasks = tasks.filter(task => task.status === 'COMPLETED').length;
    const inProgressTasks = tasks.filter(task => task.status === 'IN_PROGRESS').length;
    const notStartedTasks = tasks.filter(task => task.status === 'PENDING').length;

    const completedPercentage = totalTasks > 0 ? Math.round((completedTasks / totalTasks) * 100) : 0;
    const inProgressPercentage = totalTasks > 0 ? Math.round((inProgressTasks / totalTasks) * 100) : 0;
    const notStartedPercentage = totalTasks > 0 ? Math.round((notStartedTasks / totalTasks) * 100) : 0;

    document.querySelector('.completed-circle').textContent = `${completedPercentage}%`;
    document.querySelector('.in-progress-circle').textContent = `${inProgressPercentage}%`;
    document.querySelector('.not-started-circle').textContent = `${notStartedPercentage}%`;
}

// Set the current date in the header
function setCurrentDate() {
    const now = new Date();
    const options = { weekday: 'long', day: '2-digit', month: '2-digit', year: 'numeric' };
    document.querySelector('#current-date').textContent = now.toLocaleDateString('en-GB', options).replace(',', '');
}

// Initialize the dashboard
async function initializeDashboard() {
    // Hardcoded user profile (replace with API call in a real app)
    document.querySelector('.profile h5').textContent = 'Sundar Gurung';
    document.querySelector('.profile p').textContent = 'sundargurung360@gmail.com';
    document.querySelector('#welcome-name').textContent = 'Sundar';

    // Set the current date
    setCurrentDate();

    // Fetch and display tasks
    const tasks = await fetchTasks();
    populateToDoTasks(tasks);
    populateCompletedTasks(tasks);
    updateTaskStatusPercentages(tasks);
}

// Run initialization when the page loads
window.onload = initializeDashboard;