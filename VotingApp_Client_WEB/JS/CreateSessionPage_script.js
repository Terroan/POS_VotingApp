import { RequestType, HttpRequestHandler } from '/HttpRequestHandler.js';
import { HttpPostRequest } from '/Models/HttpPostRequest.js'; 
import { VotingQuestion } from '/Models/VotingQuestion.js'; 
import { VotingSessionEgress } from '/Models/VotingSessionEgress.js'; 


var session = new VotingSessionEgress();
var user = JSON.parse(localStorage.getItem('user'));
var postRequest = new HttpPostRequest(session, user);

function btnAddQuestion_Click() {
    postRequest.VotingSession?.Questions?.push(new VotingQuestion());
    var tmp = document.createElement('input');
    tmp.type = 'text';
    tmp.style.width = '300px';
    tmp.style.height = '25px';
    tmp.addEventListener('focus', tbQuestion_GotFocus);
    tmp.addEventListener('blur', tbQuestion_LostFocus);
    document.getElementById('lvQuestions').appendChild(tmp);
}

function btnDeleteQuestion_Click() {
    var selectedIndex = document.getElementById('lvQuestions').selectedIndex;
    if (selectedIndex !== -1) {
        postRequest.VotingSession.Questions.splice(selectedIndex, 1);
        document.getElementById('lvQuestions').removeChild(document.getElementById('lvQuestions').children[selectedIndex]);
    }
}

function lvQuestions_SelectionChanged() {
    var selectedIndex = document.getElementById('lvQuestions').selectedIndex;
    document.getElementById('btnDeleteQuestion').disabled = selectedIndex === -1;
}

function btnAddOption_Click() {
    var selectedIndex = document.getElementById('lvQuestions').selectedIndex;
    if (selectedIndex !== -1) {
        postRequest.VotingSession.Questions[selectedIndex]?.Options?.push("");
        var tmp = document.createElement('input');
        tmp.type = 'text';
        tmp.style.width = '300px';
        tmp.style.height = '25px';
        tmp.addEventListener('focus', tbOption_GotFocus);
        tmp.addEventListener('blur', tbOption_LostFocus);
        document.getElementById('lvOptions').appendChild(tmp);
    }
    updateOptionButtonsState(); // Enable/disable option buttons after adding an option
}


function lvOptions_SelectionChanged() {
    var selectedIndex = document.getElementById('lvOptions').selectedIndex;
    document.getElementById('btnDeleteOption').disabled = selectedIndex === -1;
}

function updateOptionButtonsState() {
    var questionIndex = document.getElementById('lvQuestions').selectedIndex;
    var optionIndex = document.getElementById('lvOptions').selectedIndex;
    var btnAddOption = document.getElementById('btnAddOption');
    var btnDeleteOption = document.getElementById('btnDeleteOption');
    var btnDeleteQuestion = document.getElementById('btnDeleteQuestion');

    btnAddOption.disabled = questionIndex === -1; // Disable add option if no question is selected
    btnDeleteOption.disabled = optionIndex === -1 || questionIndex === -1; // Disable delete option if no option or question is selected
    btnDeleteQuestion.disabled = questionIndex === -1; // Disable delete question if no question is selected
}

function btnDeleteOption_Click() {
    var selectedIndex = document.getElementById('lvOptions').selectedIndex;
    var questionIndex = document.getElementById('lvQuestions').selectedIndex;
    if (selectedIndex !== -1 && questionIndex !== -1) {
        postRequest.VotingSession.Questions[questionIndex]?.Options?.splice(selectedIndex, 1);
        document.getElementById('lvOptions').removeChild(document.getElementById('lvOptions').children[selectedIndex]);
    }
    updateOptionButtonsState(); // Enable/disable option buttons after removing an option
}

function tbQuestion_LostFocus(event) {
    var question = event.target;
    if (question.value == null || postRequest.VotingSession == null)
        return;

    postRequest.VotingSession.Questions[document.getElementById('lvQuestions').selectedIndex].Question = question.value;
}

function tbQuestion_GotFocus(event) {
    var question = event.target;
    document.getElementById('lvQuestions').selectedIndex = Array.from(question.parentNode.children).indexOf(question);
}

function tbOption_LostFocus(event) {
    var option = event.target;
    if (option.value == null)
        return;

    postRequest.VotingSession.Questions[document.getElementById('lvQuestions').selectedIndex].Options[document.getElementById('lvOptions').selectedIndex] = option.value;
}

function tbOption_GotFocus(event) {
    var option = event.target;
    document.getElementById('lvOptions').selectedIndex = Array.from(option.parentNode.children).indexOf(option);
}

function ShowOptions(questionIndex) {
    var lvOptions = document.getElementById('lvOptions');
    lvOptions.innerHTML = ''; // Liste leeren

    postRequest.VotingSession.Questions[questionIndex]?.Options?.forEach(option => {
        var optionElement = document.createElement('input');
        optionElement.type = 'text';
        optionElement.value = option;
        optionElement.style.width = '300px';
        optionElement.style.height = '25px';
        optionElement.addEventListener('focus', tbOption_GotFocus);
        optionElement.addEventListener('blur', tbOption_LostFocus);
        lvOptions.appendChild(optionElement);
    });
}


function btnGoBackToStart_Click() {
    window.location.href = 'StartPage.html';
}

async function btnCreateSession_Click() {
    var sessionName = document.getElementById('tbSessionName').value;
    var creatorName = document.getElementById('tbCreatorName').value;

    if (sessionName === '' || creatorName === '') {
        alert('Please fill out all forms!');
        return;
    }

    btnCreateSession.disabled = true; 
    try {
        postRequest.VotingSession.SessionTitle = sessionName;
        postRequest.VotingSession.Creator = creatorName;

        console.log(postRequest);
        alert(JSON.stringify(postRequest));
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.CreateSession, JSON.stringify(postRequest), "");
        
        if (response.ok) {
            window.location.href = 'StartPage.html'; 
        } else {
            alert('Error! Statuscode: ' + response.status);
        }
    } catch (error) {
        alert('An error occurred: ' + error.message);
    }
    finally {
        btnCreateSession.disabled = false;
    }
}

window.onload = function () {
    document.getElementById('btnAddQuestion').addEventListener('click', btnAddQuestion_Click);
    document.getElementById('btnDeleteQuestion').addEventListener('click', btnDeleteQuestion_Click);
    document.getElementById('lvQuestions').addEventListener('change', lvQuestions_SelectionChanged);
    document.getElementById('btnAddOption').addEventListener('click', btnAddOption_Click);
    document.getElementById('btnDeleteOption').addEventListener('click', btnDeleteOption_Click);
    document.getElementById('lvOptions').addEventListener('change', lvOptions_SelectionChanged);
    document.getElementById('btnGoBackToStart').addEventListener('click', btnGoBackToStart_Click);
    document.getElementById('btnCreateSession').addEventListener('click', btnCreateSession_Click);
    document.getElementById('lvQuestions').addEventListener('dblclick', function(event) {
        var selectedIndex = Array.from(event.target.parentNode.children).indexOf(event.target);
    if (selectedIndex !== -1) {
        ShowOptions(selectedIndex);
    }   
    });
    updateOptionButtonsState();
};