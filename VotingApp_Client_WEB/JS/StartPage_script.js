import { RequestType, HttpRequestHandler } from '/HttpRequestHandler.js';

// Globale Variable für die Sitzungsliste
var sessions = [];

// Retrieve user object from localStorage
var user = JSON.parse(localStorage.getItem('user'));
document.getElementById('tbUser').textContent = "User: " + user.name;

// Function to fetch user sessions
async function fetchUserSessions() {
    try {
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.FetchSessions, JSON.stringify(user), "");
        if (response.ok) {
            sessions = await response.json();
            sessions.forEach(function(vs) {
                var listItem = document.createElement('li');
                if(vs.title == '' || vs.title == null)
                    listItem.textContent = "...";
                else
                    listItem.textContent = vs.title;
                listItem.style.fontSize = '16px'; // Increase font size
                listItem.style.padding = '5px 0'; // Add padding
                document.getElementById('lbSessions').appendChild(listItem);
            });
        } else {
            showErrorMessage("Error! Statuscode: " + response.status);
        }
    } catch (error) {
        showErrorMessage(error.message);
    }
}

// Function to handle button click to create a session
function createSessionClick() {
    window.location.href = 'CreateSessionPage.html';
}

// Function to handle button click to join a session
async function joinSessionClick() {
    var sessionID = document.getElementById('tbSessionCode').value;
    document.getElementById('btnJoinSession').disabled = true;
    console.log(sessionID);
    try {
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.FetchSession, "", sessionID);
        if (response.ok) {
            localStorage.setItem("votingSession", await response.text());
            localStorage.setItem("sessionId", sessionID)
            window.location.href = 'sessionFormPage.html?sessionID=' + sessionID.toLowerCase();
        } else {
            document.getElementById('btnJoinSession').disabled = false;
            showErrorMessage("Error! Statuscode: " + response.status);
        }
    } catch (error) {
        document.getElementById('btnJoinSession').disabled = false;
        console.log(error);
        showErrorMessage("An error occurred while processing your request.");
    }
}

// Function to handle text change in session code textbox
function sessionCodeChanged() {
    var sessionCode = document.getElementById('tbSessionCode').value;
    document.getElementById('btnJoinSession').disabled = sessionCode === '';
}

// Function to handle mouse double click on list item
function listItemDoubleClick(event) {
    var selectedIndex = Array.from(event.target.parentNode.children).indexOf(event.target);
    if (selectedIndex !== -1) {
        var session = sessions[selectedIndex];
        localStorage.setItem('session', JSON.stringify(session));
        window.location.href = 'updateSessionPage.html?sessionID=' + session.ID;
    }
}

function showErrorMessage(error) {
    alert(error);
}

// Event listeners
document.getElementById('btnCreateSession').addEventListener('click', createSessionClick);
document.getElementById('btnJoinSession').addEventListener('click', joinSessionClick);
document.getElementById('tbSessionCode').addEventListener('input', sessionCodeChanged);
document.getElementById('lbSessions').addEventListener('dblclick', listItemDoubleClick);

// Fetch user sessions on page load
fetchUserSessions();
