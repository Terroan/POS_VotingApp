// Imports
import { VotingQuestion } from '/Models/VotingQuestion.js';
import { RequestType, HttpRequestHandler } from '/HttpRequestHandler.js';
import { HttpPostRequest } from '/Models/HttpPostRequest.js';

// Retrieving session and user data from local storage
var session = JSON.parse(localStorage.getItem('session'));
var user = JSON.parse(localStorage.getItem('user'));

// Logging session and user data
console.log(session);
console.log(user);

// Event handler for window.onload event
window.onload = function () {
    // Checking if session or user data is missing
    if (!session || !user) {
        alert('Session or user data is missing!');
        return;
    }

    // Initializing page elements
    initializePage();

    // Adding event listeners to buttons
    document.getElementById('btnUpdateSession').addEventListener('click', btnUpdateSession_Click);
    document.getElementById('btnStartSession').addEventListener('click', btnStartSession_Click);
    document.getElementById('btnAddQuestion').addEventListener('click', btnAddQuestion_Click);
    document.getElementById('btnDeleteQuestion').addEventListener('click', btnDeleteQuestion_Click);
    document.getElementById('lvQuestions').addEventListener('change', lvQuestions_SelectionChanged);
    document.getElementById('btnAddOption').addEventListener('click', btnAddOption_Click);
    document.getElementById('btnDeleteOption').addEventListener('click', btnDeleteOption_Click);
    document.getElementById('lvOptions').addEventListener('change', lvOptions_SelectionChanged);
    document.getElementById('btnGoBackToStart').addEventListener('click', btnGoBackToStart_Click);
    document.getElementById('btnDeleteSession').addEventListener('click', btnDeleteSession_Click);
    document.getElementById('lvQuestions').addEventListener('dblclick', function(event) {
        var selectedIndex = Array.from(event.target.parentNode.children).indexOf(event.target);
        if (selectedIndex !== -1) {
            ShowOptions(selectedIndex);
        }
    });
};

// Function to initialize the page
function initializePage() {
    // Setting values for session creator name and session name
    document.getElementById('tbCreatorName').value = session.creator || '';
    document.getElementById('tbSessionName').value = session.title || '';

    // Adding question input fields based on session data
    session.questions.forEach((vq) => {
        var tmp = document.createElement('input');
        tmp.type = 'text';
        applyInputStyles(tmp);
        tmp.value = vq.question || '';
        tmp.addEventListener('focus', tbQuestion_GotFocus);
        tmp.addEventListener('blur', tbQuestion_LostFocus);
        document.getElementById('lvQuestions').appendChild(tmp);
    });

    // Updating buttons state
    updateButtonsState();
}

// Event handler for btnUpdateSession click event
async function btnUpdateSession_Click() {
    // Retrieving session and creator name from input fields
    var sessionName = document.getElementById('tbSessionName').value;
    var creatorName = document.getElementById('tbCreatorName').value;

    // Checking if input fields are empty
    if (sessionName === '' || creatorName === '') {
        alert('Please fill out all forms!');
        return;
    }

    // Disabling update session button
    document.getElementById('btnUpdateSession').disabled = true;
    try {
        // Updating session title and creator
        session.title = sessionName;
        session.creator = creatorName;

        // Sending HTTP request to update session
        var postRequest = new HttpPostRequest(session, user);
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.UpdateSession, JSON.stringify(postRequest), session.id);

        // Handling response
        if (response.ok) {
            alert('Session was updated!');
            window.location.href = 'StartPage.html';
        } else {
            alert('Error! Statuscode: ' + response.status);
            document.getElementById('btnUpdateSession').disabled = false;
        }
    } catch (error) {
        alert('An error occurred: ' + error.message);
        document.getElementById('btnUpdateSession').disabled = false;
    }
}

// Event handler for starting a session
async function btnStartSession_Click() {
    // Retrieving session name and creator name from input fields
    var sessionName = document.getElementById('tbSessionName').value;
    var creatorName = document.getElementById('tbCreatorName').value;

    // Checking if session name or creator name is empty
    if (sessionName === '' || creatorName === '') {
        alert('Please fill out all forms!'); // Alerting user to fill out all forms
        return; // Exiting function
    }

    // Disabling start session button
    document.getElementById('btnStartSession').disabled = true;

    try {
        // Updating session title and creator
        session.title = sessionName; // Updating session title
        session.creator = creatorName; // Updating session creator

        // Sending HTTP request to start session
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.StartSession, JSON.stringify(user), session.id);

        // Checking if response is successful
        if (response.ok) {
            // Retrieving session code
            const sessionCode = await response.text(); // Retrieving session code from response
            localStorage.setItem("sessionCode", sessionCode); // Storing session code in local storage
            window.location.href = 'SessionCodePage.html?sessionCode=' + sessionCode + '&sessionId=' + session.id; // Redirecting to session code page with session code and ID
        } else {
            alert('Error! Statuscode: ' + response.status); // Alerting user about error with status code
            document.getElementById('btnStartSession').disabled = false; // Enabling start session button
        }
    } catch (error) {
        alert('An error occurred: ' + error.message); // Alerting user about error
        document.getElementById('btnStartSession').disabled = false; // Enabling start session button
    }
}

// Function to handle adding a new question
function btnAddQuestion_Click() {
    // Adding new question to session questions array
    session.questions.push(new VotingQuestion());
    // Creating new input element
    var tmp = document.createElement('input');
    tmp.type = 'text'; // Setting input type to text
    applyInputStyles(tmp); // Applying styles to input element
    tmp.addEventListener('focus', tbQuestion_GotFocus); // Adding focus event listener
    tmp.addEventListener('blur', tbQuestion_LostFocus); // Adding blur event listener
    document.getElementById('lvQuestions').appendChild(tmp); // Appending input element to questions list
    updateButtonsState(); // Updating buttons state
}

// Function to handle deleting a question
function btnDeleteQuestion_Click() {
    var selectedIndex = document.getElementById('lvQuestions').selectedIndex; // Getting selected question index
    if (selectedIndex !== -1) {
        // Removing question from session questions array
        session.questions.splice(selectedIndex, 1);
        // Removing question element from list
        document.getElementById('lvQuestions').removeChild(document.getElementById('lvQuestions').children[selectedIndex]);
        updateButtonsState(); // Updating buttons state
    }
}

// Function to handle selection change in questions list
function lvQuestions_SelectionChanged() {
    updateButtonsState(); // Updating buttons state
}

// Function to handle adding a new option
function btnAddOption_Click() {
    var selectedIndex = document.getElementById('lvQuestions').selectedIndex; // Getting selected question index
    if (selectedIndex !== -1) {
        if(session.questions[selectedIndex].options == null)
            session.questions[selectedIndex].options = []; // Initializing options array if null
        // Adding new option to options array
        session.questions[selectedIndex].options.push("");
        // Creating new input element
        var tmp = document.createElement('input');
        tmp.type = 'text'; // Setting input type to text
        applyInputStyles(tmp); // Applying styles to input element
        tmp.addEventListener('focus', tbOption_GotFocus); // Adding focus event listener
        tmp.addEventListener('blur', tbOption_LostFocus); // Adding blur event listener
        document.getElementById('lvOptions').appendChild(tmp); // Appending input element to options list
        updateButtonsState(); // Updating buttons state
    }
}

// Function to handle deleting an option
function btnDeleteOption_Click() {
    var selectedIndex = document.getElementById('lvOptions').selectedIndex; // Getting selected option index
    var questionIndex = document.getElementById('lvQuestions').selectedIndex; // Getting selected question index
    if (selectedIndex !== -1 && questionIndex !== -1) {
        // Removing option from options array
        session.questions[questionIndex].options.splice(selectedIndex, 1);
        // Removing option element from list
        document.getElementById('lvOptions').removeChild(document.getElementById('lvOptions').children[selectedIndex]);
        updateButtonsState(); // Updating buttons state
    }
}

// Function to handle selection change in options list
function lvOptions_SelectionChanged() {
    updateButtonsState(); // Updating buttons state
}

// Function to handle blur event on question input
function tbQuestion_LostFocus(event) {
    var question = event.target; // Getting target question element
    var selectedIndex = Array.from(document.getElementById('lvQuestions').children).indexOf(question); // Getting selected question index
    if (question.value !== null) {
        // Updating question value in session
        session.questions[selectedIndex].question = question.value;
    }
}

// Function to handle focus event on question input
function tbQuestion_GotFocus(event) {
    var question = event.target; // Getting target question element
    // Updating selected question index
    document.getElementById('lvQuestions').selectedIndex = Array.from(question.parentNode.children).indexOf(question);
}

// Function to handle blur event on option input
function tbOption_LostFocus(event) {
    var option = event.target; // Getting target option element
    var questionIndex = document.getElementById('lvQuestions').selectedIndex; // Getting selected question index
    var optionIndex = Array.from(document.getElementById('lvOptions').children).indexOf(option); // Getting selected option index
    if (option.value !== null) {
        // Updating option value in session
        session.questions[questionIndex].options[optionIndex] = option.value;
    }
}

// Function to handle focus event on option input
function tbOption_GotFocus(event) {
    var option = event.target; // Getting target option element
    // Updating selected option index
    document.getElementById('lvOptions').selectedIndex = Array.from(option.parentNode.children).indexOf(option);
}

// Function to display options for a selected question
function ShowOptions(questionIndex) {
    var lvOptions = document.getElementById('lvOptions');
    lvOptions.innerHTML = ''; // Clearing options list

    console.log(session.questions[questionIndex]);
    session.questions[questionIndex].options.forEach(option => {
        // Creating input element for each option
        var optionElement = document.createElement('input');
        optionElement.type = 'text'; // Setting input type to text
        applyInputStyles(optionElement); // Applying styles to input element
        optionElement.value = option; // Setting option value
        optionElement.addEventListener('focus', tbOption_GotFocus); // Adding focus event listener
        optionElement.addEventListener('blur', tbOption_LostFocus); // Adding blur event listener
        lvOptions.appendChild(optionElement); // Appending input element to options list
    });
}

// Function to navigate back to the start page
function btnGoBackToStart_Click() {
    window.location.href = 'StartPage.html'; // Redirecting to StartPage.html
}

// Function to handle deleting a session
async function btnDeleteSession_Click() {
    document.getElementById('btnDeleteSession').disabled = true; // Disabling delete session button
    try {
        // Sending HTTP request to delete session
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.DeleteSession, JSON.stringify(user), session.id);

        // Checking if response is successful
        if (response.ok) {
            alert('Session was deleted!'); // Alerting user that session was deleted
            window.location.href = 'StartPage.html'; // Redirecting to StartPage.html
        } else {
            alert('Error! Statuscode: ' + response.status); // Alerting user about error with status code
            document.getElementById('btnDeleteSession').disabled = false; // Enabling delete session button
        }
    } catch (error) {
        alert('An error occurred: ' + error.message); // Alerting user about error
        document.getElementById('btnDeleteSession').disabled = false; // Enabling delete session button
    }
}

// Function to update the state of buttons
function updateButtonsState() {
    var questionIndex = document.getElementById('lvQuestions').selectedIndex; // Getting selected question index
    var optionIndex = document.getElementById('lvOptions').selectedIndex; // Getting selected option index

    // Disabling or enabling delete question button based on selection
    document.getElementById('btnDeleteQuestion').disabled = questionIndex === -1;
    // Disabling or enabling add option button based on selection
    document.getElementById('btnAddOption').disabled = questionIndex === -1;
    // Disabling or enabling delete option button based on selection
    document.getElementById('btnDeleteOption').disabled = optionIndex === -1;
}

// Function to apply styles to input fields
function applyInputStyles(inputElement) {
    // Applying styles to input fields
    inputElement.style.width = '300px';
    inputElement.style.height = '25px';
    inputElement.style.padding = '10px';
    inputElement.style.border = '1px solid #333';
    inputElement.style.backgroundColor = '#ffebcc';
    inputElement.style.color = '#000';
    inputElement.style.fontFamily = '"Showcard Gothic", sans-serif';
    inputElement.style.borderRadius = '5px';
    inputElement.style.boxShadow = '2px 2px 5px rgba(0, 0, 0, 0.1)';
}