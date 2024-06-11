// load page
document.addEventListener('DOMContentLoaded', function() {
    const session = JSON.parse(localStorage.getItem('votingSession'));
    const user = JSON.parse(localStorage.getItem('user'));

    if (!session || !user) {
        alert('Session data or user data is missing!');
        return;
    }

    initializePage(session, user);

    document.getElementById('cbQuestions').addEventListener('change', function(event) {
        cbQuestions_SelectionChanged(event, session);
    });

    document.getElementById('btnGoBackToStart').addEventListener('click', function() {
        window.location.href = 'StartPage.html';
    });
});

function initializePage(session, user) {
    document.getElementById('lblCreator').textContent = "Creator: " + session.creator;
    document.getElementById('lblSurvey').textContent = "Survey: " + session.title;

    if (session.questions && session.questions.length > 0) {
        document.getElementById('lblQuestion').textContent = session.questions[0].question;
    }

    const votes = CountVotes(session.results);
    PopulateCombobox(session);

    const cbQuestions = document.getElementById('cbQuestions');
    if (cbQuestions.options.length > 0) {
        cbQuestions.selectedIndex = 0;
        FillDiagramm(cbQuestions.selectedIndex, session, votes);
    }
}

// count the votes from the http response
function CountVotes(posts) {
    const voteSum = {};
    posts.forEach(post => {
        for (const [key, value] of Object.entries(post.votes)) {
            if (!voteSum[key]) {
                voteSum[key] = {};
            }
            if (!voteSum[key][value]) {
                voteSum[key][value] = 0;
            }
            voteSum[key][value]++;
        }
    });
    return voteSum;
}

// populate the question combobox
function PopulateCombobox(session) {
    const cbQuestions = document.getElementById('cbQuestions');
    session.questions.forEach((question, index) => {
        const option = document.createElement('option');
        option.value = index;
        option.text = `Question ${index + 1}`;
        cbQuestions.add(option);
    });
}

let chartInstance = null; // Variable to store the chart instance

function FillDiagramm(questionIndex, session, votes) {
    const lvcChart = document.getElementById('lvcChart');
    lvcChart.innerHTML = ''; // Clear previous chart element

    const question = session.questions[questionIndex];
    const labels = question.options.map(option => option.length > 20 ? option.substring(0, 20) + '...' : option);
    const chartValues = question.options.map((_, index) => (votes[questionIndex] && votes[questionIndex][index]) ? votes[questionIndex][index] : 0);

    const ctx = document.getElementById('lvcChart').getContext('2d');

    // Destroy previous chart instance if it exists
    if (chartInstance) {
        chartInstance.destroy();
    }

    // Create new chart instance
    chartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Votes',
                data: chartValues,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1,
                        font: {
                            size: 16,
                            weight: 'bold'
                        }
                    }
                }
            }
        }
    });
}

// select a question
function cbQuestions_SelectionChanged(event, session) {
    const selectedIndex = event.target.selectedIndex;
    document.getElementById('lblQuestion').textContent = session.questions[selectedIndex].question;
    const votes = CountVotes(session.results);
    FillDiagramm(selectedIndex, session, votes);
}
