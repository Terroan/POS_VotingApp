// Import des HttpRequestHandler
import { HttpRequestHandler, RequestType } from '/HttpRequestHandler.js';
import { VotingPost } from '/Models/VotingPost.js';

window.onload = function() {
    var session = JSON.parse(localStorage.getItem('votingSession'));
    const sessionId = localStorage.getItem('sessionId');
    if (!session || !sessionId) {
        alert('Session data or session ID is missing!');
        return;
    }

    const surveyTakerInput = document.getElementById('tbSurveyTaker');
    const finishButton = document.getElementById('btnFinish');
    const questionsList = document.getElementById('lvQuestions');
    const votingPost = new VotingPost();

    // Populate session information
    document.getElementById('lblCreatorName').textContent = "Creator: " + session.creator;
    document.getElementById('lblSessionName').textContent = "Survey: " + session.title;

    // Set max-height and overflow-y to make the question list scrollable
    questionsList.style.maxHeight = '300px'; // Adjust height as needed
    questionsList.style.overflowY = 'auto';

    // Function to handle checkbox change
    function handleCheckboxChange(questionIndex, optionIndex) {
        const checkboxes = document.querySelectorAll(`#lvQuestions input[name="question_${questionIndex}"]`);
        checkboxes.forEach((checkbox, index) => {
            if (index !== optionIndex) {
                checkbox.checked = false;
            }
        });

        if (checkboxes[optionIndex].checked) {
            votingPost.Votes[questionIndex] = optionIndex;
        } else {
            delete votingPost.Votes[questionIndex];
        }
    }

    // Populate initial questions
    session.questions.forEach((question, questionIndex) => {
        const questionLabel = document.createElement('label');
        questionLabel.textContent = question.question || "...";
        questionLabel.style.display = 'block'; // Anzeigen der Fragen untereinander
        questionsList.appendChild(questionLabel);

        const optionsContainer = document.createElement('div'); // Container für die Optionen
        question.options.forEach((option, optionIndex) => {
            const optionContainer = document.createElement('div'); // Container für jede Option
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.name = `question_${questionIndex}`; // Eindeutiger Name für jede Frage
            checkbox.value = optionIndex;
            checkbox.style.marginRight = '5px';
            checkbox.addEventListener('change', function() {
                handleCheckboxChange(questionIndex, optionIndex);
            });
            optionContainer.appendChild(checkbox);

            const optionLabel = document.createElement('label');
            optionLabel.textContent = option;
            optionContainer.appendChild(optionLabel);

            optionsContainer.appendChild(optionContainer); // Hinzufügen der Option zum Container für die Optionen
        });
        questionsList.appendChild(optionsContainer); // Hinzufügen des Containers für die Optionen zum Fragen-Container
    });

    // Go back to start page
    document.getElementById('btnGoBackToStart').addEventListener('click', function() {
        window.location.href = 'StartPage.html';
    });

    // Send answers
    finishButton.addEventListener('click', async function() {
        const surveyTaker = surveyTakerInput.value;
        if (!surveyTaker) {
            alert('Please fill in survey taker data');
            return;
        }

        if (Object.keys(votingPost.Votes).length === 0) {
            alert('No answers selected');
            return;
        }

        try {
            votingPost.Voter = surveyTaker;
            const response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.PostResults, JSON.stringify(votingPost), sessionId);
            if (response.ok) {
                alert('Your answers have been submitted');
                window.location.href = 'StartPage.html';
            } else {
                alert('Error! Status code: ' + response.status);
            }
        } catch (error) {
            alert('Error: ' + error.message);
        }
    });
};
