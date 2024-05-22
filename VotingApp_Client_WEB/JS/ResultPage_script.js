document.getElementById('btnGoBackToStart').addEventListener('click', function() {
    // Implement the event handler logic here
    console.log('Go back clicked');
});

document.getElementById('cbQuestions').addEventListener('change', function() {
    // Implement the event handler logic here
    console.log('Question selection changed');
});

document.addEventListener('DOMContentLoaded', function() {
    var ctx = document.getElementById('lvcChart').getContext('2d');
    var chart = new Chart(ctx, {
        type: 'bar', // You can change this to 'line', 'pie', etc.
        data: {
            labels: ['Option 1', 'Option 2', 'Option 3'], // Example data
            datasets: [{
                label: 'Votes',
                data: [12, 19, 3], // Example data
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(75, 192, 192, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(75, 192, 192, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});
