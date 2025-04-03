document.addEventListener('DOMContentLoaded', function() {
    // Check if theme is saved in localStorage
    const isDarkMode = localStorage.getItem('darkMode') === 'true';
    
    // Get theme stylesheet link
    let themeStylesheet = document.getElementById('theme-stylesheet');
    
    // If not present, create it
    if (!themeStylesheet) {
        themeStylesheet = document.createElement('link');
        themeStylesheet.id = 'theme-stylesheet';
        themeStylesheet.rel = 'stylesheet';
        themeStylesheet.href = '/css/styles2.css';
        document.head.appendChild(themeStylesheet);
    }
    
    // Apply theme based on saved preference
    themeStylesheet.disabled = !isDarkMode;
    
    // Apply appropriate text color class to body
    if (isDarkMode) {
        document.body.classList.add('dark-mode-text');
        document.body.classList.remove('light-mode-text');
        document.documentElement.style.backgroundColor = '#2b2d42';
    } else {
        document.body.classList.add('light-mode-text');
        document.body.classList.remove('dark-mode-text');
        document.documentElement.style.backgroundColor = '';
    }
    
    // Add theme change event listener if it exists in the page
    const darkModeSwitch = document.getElementById('darkModeSwitch');
    if (darkModeSwitch) {
        // Assicurati che lo stato del checkbox rifletta il tema corrente
        darkModeSwitch.checked = isDarkMode;
        
        darkModeSwitch.addEventListener('change', function() {
            if (this.checked) {
                themeStylesheet.disabled = false;
                document.body.classList.add('dark-mode-text');
                document.body.classList.remove('light-mode-text');
                document.documentElement.style.backgroundColor = '#2b2d42';
                localStorage.setItem('darkMode', 'true');
            } else {
                themeStylesheet.disabled = true;
                document.body.classList.add('light-mode-text');
                document.body.classList.remove('dark-mode-text');
                document.documentElement.style.backgroundColor = '';
                localStorage.setItem('darkMode', 'false');
            }
        });
    }
    
    // Update theme icon
    const themeIcon = document.getElementById('themeIcon');
    if (themeIcon) {
        themeIcon.className = isDarkMode ? 'fas fa-moon' : 'fas fa-sun';
        
        // Update icon when theme changes
        darkModeSwitch?.addEventListener('change', function() {
            themeIcon.className = this.checked ? 'fas fa-moon' : 'fas fa-sun';
        });
    }
});
