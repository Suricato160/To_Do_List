/**
 * Gestione della sidebar retrattile
 * Questo script permette di ridurre la sidebar mostrando solo le icone
 * e mantiene la preferenza dell'utente tramite localStorage
 */
document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    const mainContent = document.querySelector('.main-content');
    const sidebarToggle = document.createElement('button');
    
    // Configura il pulsante toggle
    sidebarToggle.className = 'sidebar-toggle';
    sidebarToggle.innerHTML = '<i class="fas fa-angle-left"></i>';
    sidebarToggle.setAttribute('title', 'Riduci sidebar');
    sidebar.appendChild(sidebarToggle);
    
    // Verifica se c'è una preferenza salvata
    const isSidebarCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';
    
    // Applica lo stato iniziale
    if (isSidebarCollapsed) {
        sidebar.classList.add('collapsed');
        mainContent.classList.add('expanded');
        sidebarToggle.setAttribute('title', 'Espandi sidebar');
    }
    
    // Funzione toggle per la sidebar
    function toggleSidebar() {
        sidebar.classList.toggle('collapsed');
        mainContent.classList.toggle('expanded');
        
        const isNowCollapsed = sidebar.classList.contains('collapsed');
        localStorage.setItem('sidebarCollapsed', isNowCollapsed);
        
        // Aggiorna il tooltip del pulsante
        sidebarToggle.setAttribute('title', isNowCollapsed ? 'Espandi sidebar' : 'Riduci sidebar');
    }
    
    // Listener per il click sul pulsante
    sidebarToggle.addEventListener('click', toggleSidebar);
    
    // Tooltips per le icone in modalità contratta
    const navLinks = document.querySelectorAll('.sidebar .nav-link');
    navLinks.forEach(link => {
        const text = link.querySelector('span').textContent.trim();
        link.setAttribute('title', text);
    });
});
