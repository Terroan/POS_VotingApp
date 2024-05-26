import { VotingQuestion } from '/Models/VotingQuestion.js';
import { RequestType, HttpRequestHandler } from '/HttpRequestHandler.js';
import { HttpPostRequest } from '/Models/HttpPostRequest.js';

var session = JSON.parse(localStorage.getItem('session'));
var user = JSON.parse(localStorage.getItem('user'));

console.log(session);
console.log(user);

window.onload = function () {
    if (!session || !user) {
        alert('Session or user data is missing!');
        return;
    }

    initializePage();

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

function initializePage() {
    document.getElementById('tbCreatorName').value = session.creator || '';
    document.getElementById('tbSessionName').value = session.title || '';

    console.log(session.questions);
    if (!session.questions || !Array.isArray(session.questions)) {
        console.error('Session questions are missing or not an array:', session.questions);
        return;
    }

    session.questions.forEach((vq) => {
        var tmp = document.createElement('input');
        tmp.type = 'text';
        applyInputStyles(tmp);
        tmp.value = vq.question || '';
        tmp.addEventListener('focus', tbQuestion_GotFocus);
        tmp.addEventListener('blur', tbQuestion_LostFocus);
        document.getElementById('lvQuestions').appendChild(tmp);
    });

    updateButtonsState();
}

async function btnUpdateSession_Click() {
    var sessionName = document.getElementById('tbSessionName').value;
    var creatorName = document.getElementById('tbCreatorName').value;

    if (sessionName === '' || creatorName === '') {
        alert('Please fill out all forms!');
        return;
    }

    document.getElementById('btnUpdateSession').disabled = true;
    try {
        session.title = sessionName;
        session.creator = creatorName;

        var postRequest = new HttpPostRequest(session, user);
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.UpdateSession, JSON.stringify(postRequest), session.id);

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

async function btnStartSession_Click() {
    var sessionName = document.getElementById('tbSessionName').value;
    var creatorName = document.getElementById('tbCreatorName').value;

    if (sessionName === '' || creatorName === '') {
        alert('Please fill out all forms!');
        return;
    }

    document.getElementById('btnStartSession').disabled = true;
    try {
        session.title = sessionName;
        session.creator = creatorName;

        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.StartSession, JSON.stringify(user), session.id);

        if (response.ok) {
            const sessionCode = await response.text()
            localStorage.setItem("sessionCode", sessionCode)
            window.location.href = 'SessionCodePage.html?sessionCode=' + sessionCode + '&sessionId=' + session.id;
        } else {
            alert('Error! Statuscode: ' + response.status);
            document.getElementById('btnStartSession').disabled = false;
        }
    } catch (error) {
        alert('An error occurred: ' + error.message);
        document.getElementById('btnStartSession').disabled = false;
    }
}

function btnAddQuestion_Click() {
    session.questions.push(new VotingQuestion());
    var tmp = document.createElement('input');
    tmp.type = 'text';
    applyInputStyles(tmp);
    tmp.addEventListener('focus', tbQuestion_GotFocus);
    tmp.addEventListener('blur', tbQuestion_LostFocus);
    document.getElementById('lvQuestions').appendChild(tmp);
    updateButtonsState();
}

function btnDeleteQuestion_Click() {
    var selectedIndex = document.getElementById('lvQuestions').selectedIndex;
    if (selectedIndex !== -1) {
        session.questions.splice(selectedIndex, 1);
        document.getElementById('lvQuestions').removeChild(document.getElementById('lvQuestions').children[selectedIndex]);
        updateButtonsState();
    }
}

function lvQuestions_SelectionChanged() {
    updateButtonsState();
}

function btnAddOption_Click() {
    var selectedIndex = document.getElementById('lvQuestions').selectedIndex;
    if (selectedIndex !== -1) {
        if(session.questions[selectedIndex].options == null)
            session.questions[selectedIndex].options = [];
        session.questions[selectedIndex].options.push("");
        var tmp = document.createElement('input');
        tmp.type = 'text';
        applyInputStyles(tmp);
        tmp.addEventListener('focus', tbOption_GotFocus);
        tmp.addEventListener('blur', tbOption_LostFocus);
        document.getElementById('lvOptions').appendChild(tmp);
        updateButtonsState();
    }
}

function btnDeleteOption_Click() {
    var selectedIndex = document.getElementById('lvOptions').selectedIndex;
    var questionIndex = document.getElementById('lvQuestions').selectedIndex;
    if (selectedIndex !== -1 && questionIndex !== -1) {
        session.questions[questionIndex].options.splice(selectedIndex, 1);
        document.getElementById('lvOptions').removeChild(document.getElementById('lvOptions').children[selectedIndex]);
        updateButtonsState();
    }
}

function lvOptions_SelectionChanged() {
    updateButtonsState();
}

function tbQuestion_LostFocus(event) {
    var question = event.target;
    var selectedIndex = Array.from(document.getElementById('lvQuestions').children).indexOf(question);
    if (question.value !== null) {
        session.questions[selectedIndex].question = question.value;
    }
}

function tbQuestion_GotFocus(event) {
    var question = event.target;
    document.getElementById('lvQuestions').selectedIndex = Array.from(question.parentNode.children).indexOf(question);
}

function tbOption_LostFocus(event) {
    var option = event.target;
    var questionIndex = document.getElementById('lvQuestions').selectedIndex;
    var optionIndex = Array.from(document.getElementById('lvOptions').children).indexOf(option);
    if (option.value !== null) {
        session.questions[questionIndex].options[optionIndex] = option.value;
    }
}

function tbOption_GotFocus(event) {
    var option = event.target;
    document.getElementById('lvOptions').selectedIndex = Array.from(option.parentNode.children).indexOf(option);
}

function ShowOptions(questionIndex) {
    var lvOptions = document.getElementById('lvOptions');
    lvOptions.innerHTML = ''; // Clear list

    console.log(session.questions[questionIndex]);
    session.questions[questionIndex].options.forEach(option => {
        var optionElement = document.createElement('input');
        optionElement.type = 'text';
        applyInputStyles(optionElement);
        optionElement.value = option;
        optionElement.addEventListener('focus', tbOption_GotFocus);
        optionElement.addEventListener('blur', tbOption_LostFocus);
        lvOptions.appendChild(optionElement);
    });
}

function btnGoBackToStart_Click() {
    window.location.href = 'StartPage.html';
}

async function btnDeleteSession_Click() {
    document.getElementById('btnDeleteSession').disabled = true;
    try {
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.DeleteSession, JSON.stringify(user), session.id);

        if (response.ok) {
            alert('Session was deleted!');
            window.location.href = 'StartPage.html';
        } else {
            alert('Error! Statuscode: ' + response.status);
            document.getElementById('btnDeleteSession').disabled = false;
        }
    } catch (error) {
        alert('An error occurred: ' + error.message);
        document.getElementById('btnDeleteSession').disabled = false;
    }
}

function updateButtonsState() {
    var questionIndex = document.getElementById('lvQuestions').selectedIndex;
    var optionIndex = document.getElementById('lvOptions').selectedIndex;

    document.getElementById('btnDeleteQuestion').disabled = questionIndex === -1;
    document.getElementById('btnAddOption').disabled = questionIndex === -1;
    document.getElementById('btnDeleteOption').disabled = optionIndex === -1;
}

// Funktion zum Anwenden von Stilen auf die Input-Felder
function applyInputStyles(inputElement) {
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
