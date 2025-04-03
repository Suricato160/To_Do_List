/**
 * Charts Initialization
 * Handles loading Plotly and initializing dashboard charts
 */

document.addEventListener('DOMContentLoaded', function() {
    // Check if Plotly is available, if not load it
    if (typeof Plotly === 'undefined') {
        console.log('Loading Plotly...');
        const script = document.createElement('script');
        script.src = 'https://cdn.plot.ly/plotly-latest.min.js';
        script.onload = initializeCharts;
        script.onerror = handlePlotlyError;
        document.head.appendChild(script);
    } else {
        console.log('Plotly already loaded');
        initializeCharts();
    }
});

function handlePlotlyError() {
    console.error('Failed to load Plotly library');
    const chartContainers = ['mainChart', 'categoryChart', 'trendChart'];
    chartContainers.forEach(containerId => {
        const container = document.getElementById(containerId);
        if (container) {
            container.innerHTML = `
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    Failed to load chart library. Please refresh the page or check your internet connection.
                </div>
            `;
        }
    });
}

function initializeCharts() {
    console.log('Initializing charts...');
    
    // Get chart data
    const chartData = document.getElementById('chartData');
    if (!chartData) {
        console.error('Chart data element not found');
        return;
    }

    const data = {
        completed: parseFloat(chartData.dataset.completed || 0),
        inProgress: parseFloat(chartData.dataset.inProgress || 0),
        pending: parseFloat(chartData.dataset.pending || 0),
        taskCounts: parseInt(chartData.dataset.taskCounts || 0),
        projects: parseInt(chartData.dataset.projects || 0),
        categories: chartData.dataset.categories || 0,
        todayTasks: parseInt(chartData.dataset.todayTasks || 0)
    };

    console.log('Chart data loaded:', data);
    
    try {
        // Initialize main chart as pie chart
        create3DChart(data);
        
        // Initialize category and trend charts
        createCategoryChart(data);
        createTrendChart(data);
        
        // Setup chart toggle buttons
        setupChartButtons(data);
        
        console.log('Charts initialized successfully');
    } catch (error) {
        console.error('Error initializing charts:', error);
        document.getElementById('mainChart').innerHTML = `
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-triangle me-2"></i>
                Error initializing charts: ${error.message}
            </div>
        `;
    }
}

function create3DChart(data) {
    console.log('Creating 3D chart with data:', data);
    
    const isDarkMode = document.body.classList.contains('dark-mode-text');
    const bgColor = isDarkMode ? '#353852' : '#ffffff';
    const textColor = isDarkMode ? '#edf2f4' : '#333333';
    
    const chartData = [{
        values: [data.completed, data.inProgress, data.pending],
        labels: ['Completate', 'In Corso', 'Da Iniziare'],
        type: 'pie',
        hole: 0.4,
        textinfo: 'label+percent',
        textposition: 'outside',
        automargin: true,
        marker: {
            colors: ['#4caf50', '#ff9800', '#2196f3']
        }
    }];

    const layout = {
        title: 'Distribuzione Task per Stato',
        height: 400,
        paper_bgcolor: bgColor,
        plot_bgcolor: bgColor,
        font: { color: textColor },
        showlegend: true,
        legend: { orientation: 'h' }
    };

    Plotly.newPlot('mainChart', chartData, layout, {responsive: true});
}

function createDistributionChart(data) {
    console.log('Creating distribution chart with data:', data);
    
    const isDarkMode = document.body.classList.contains('dark-mode-text');
    const bgColor = isDarkMode ? '#353852' : '#ffffff';
    const textColor = isDarkMode ? '#edf2f4' : '#333333';
    
    const chartData = [
        {
            x: ['Completate', 'In Corso', 'Da Iniziare'],
            y: [data.completed, data.inProgress, data.pending],
            type: 'bar',
            marker: {
                color: ['#4caf50', '#ff9800', '#2196f3']
            }
        }
    ];

    const layout = {
        title: 'Distribuzione Task (%)',
        height: 400,
        paper_bgcolor: bgColor,
        plot_bgcolor: bgColor,
        font: { color: textColor },
        xaxis: {
            title: 'Stato'
        },
        yaxis: {
            title: 'Percentuale',
            range: [0, 100]
        }
    };

    Plotly.newPlot('mainChart', chartData, layout, {responsive: true});
}

function createCandleChart(data) {
    console.log('Creating trend chart with data:', data);
    
    const isDarkMode = document.body.classList.contains('dark-mode-text');
    const bgColor = isDarkMode ? '#353852' : '#ffffff';
    const textColor = isDarkMode ? '#edf2f4' : '#333333';
    
    // Create sample data for the last 7 days
    const dates = [];
    const completedValues = [];
    const inProgressValues = [];
    const pendingValues = [];
    
    for (let i = 6; i >= 0; i--) {
        const date = new Date();
        date.setDate(date.getDate() - i);
        dates.push(date.toLocaleDateString());
        
        // Generate realistic looking trend data
        let baseCompleted = Math.max(10, data.completed - (i * 5));
        let basePending = Math.min(80, data.pending + (i * 5));
        let baseInProgress = 100 - baseCompleted - basePending;
        
        // Add some randomness
        completedValues.push(baseCompleted + (Math.random() * 5 - 2.5));
        inProgressValues.push(baseInProgress + (Math.random() * 5 - 2.5));
        pendingValues.push(basePending + (Math.random() * 5 - 2.5));
    }
    
    const chartData = [
        {
            x: dates,
            y: completedValues,
            type: 'scatter',
            mode: 'lines+markers',
            name: 'Completate',
            line: { color: '#4caf50', width: 3 }
        },
        {
            x: dates,
            y: inProgressValues,
            type: 'scatter',
            mode: 'lines+markers',
            name: 'In Corso',
            line: { color: '#ff9800', width: 3 }
        },
        {
            x: dates,
            y: pendingValues,
            type: 'scatter',
            mode: 'lines+markers',
            name: 'Da Iniziare',
            line: { color: '#2196f3', width: 3 }
        }
    ];
    
    const layout = {
        title: 'Andamento Tasks nell\'ultima settimana',
        height: 400,
        paper_bgcolor: bgColor,
        plot_bgcolor: bgColor,
        font: { color: textColor },
        xaxis: {
            title: 'Data'
        },
        yaxis: {
            title: 'Percentuale',
            range: [0, 100]
        },
        legend: {
            orientation: 'h',
            y: 1.1
        }
    };
    
    Plotly.newPlot('mainChart', chartData, layout, {responsive: true});
}

function createCategoryChart(data) {
    console.log('Creating category chart');
    
    const isDarkMode = document.body.classList.contains('dark-mode-text');
    const bgColor = isDarkMode ? '#353852' : '#ffffff';
    const textColor = isDarkMode ? '#edf2f4' : '#333333';
    
    // Example categories with random data
    const categories = ['Lavoro', 'Personale', 'Studio', 'Progetto', 'Altro'];
    const values = categories.map(() => Math.floor(Math.random() * 30) + 5);
    
    const chartData = [{
        values: values,
        labels: categories,
        type: 'pie',
        textinfo: 'label+percent',
        textposition: 'inside',
        automargin: true
    }];
    
    const layout = {
        title: 'Task per Categoria',
        height: 300,
        paper_bgcolor: bgColor,
        plot_bgcolor: bgColor,
        font: { color: textColor },
        showlegend: false
    };
    
    Plotly.newPlot('categoryChart', chartData, layout, {responsive: true});
}

function createTrendChart(data) {
    console.log('Creating trend chart');
    
    const isDarkMode = document.body.classList.contains('dark-mode-text');
    const bgColor = isDarkMode ? '#353852' : '#ffffff';
    const textColor = isDarkMode ? '#edf2f4' : '#333333';
    
    // Create a weekly trend chart
    const days = ['Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab', 'Dom'];
    const values = days.map(() => Math.floor(Math.random() * 8) + 2);
    
    const chartData = [{
        x: days,
        y: values,
        type: 'bar',
        marker: {
            color: '#4caf50'
        },
        name: 'Task completate'
    }];
    
    const layout = {
        title: 'Task Completate per Giorno',
        height: 300,
        paper_bgcolor: bgColor,
        plot_bgcolor: bgColor,
        font: { color: textColor },
        xaxis: {
            title: 'Giorno'
        },
        yaxis: {
            title: 'Quantità',
            dtick: 2
        }
    };
    
    Plotly.newPlot('trendChart', chartData, layout, {responsive: true});
}

function setupChartButtons(data) {
    const chart3dBtn = document.getElementById('chart3dBtn');
    const chartCandleBtn = document.getElementById('chartCandleBtn');
    const chartDistributionBtn = document.getElementById('chartDistributionBtn');
    
    if (chart3dBtn && chartCandleBtn && chartDistributionBtn) {
        console.log('Setting up chart buttons');
        
        chart3dBtn.addEventListener('click', function() {
            this.classList.add('active');
            chartCandleBtn.classList.remove('active');
            chartDistributionBtn.classList.remove('active');
            create3DChart(data);
        });
        
        chartCandleBtn.addEventListener('click', function() {
            this.classList.add('active');
            chart3dBtn.classList.remove('active');
            chartDistributionBtn.classList.remove('active');
            createCandleChart(data);
        });
        
        chartDistributionBtn.addEventListener('click', function() {
            this.classList.add('active');
            chart3dBtn.classList.remove('active');
            chartCandleBtn.classList.remove('active');
            createDistributionChart(data);
        });
    }
    
    // Update charts when theme changes
    const darkModeSwitch = document.getElementById('darkModeSwitch');
    if (darkModeSwitch) {
        darkModeSwitch.addEventListener('change', function() {
            console.log('Theme changed, updating charts');
            setTimeout(() => {
                // Redraw active chart
                if (chart3dBtn?.classList.contains('active')) {
                    create3DChart(data);
                } else if (chartCandleBtn?.classList.contains('active')) {
                    createCandleChart(data);
                } else if (chartDistributionBtn?.classList.contains('active')) {
                    createDistributionChart(data);
                } else {
                    create3DChart(data);
                }
                
                // Redraw other charts
                createCategoryChart(data);
                createTrendChart(data);
            }, 300);
        });
    }
}
