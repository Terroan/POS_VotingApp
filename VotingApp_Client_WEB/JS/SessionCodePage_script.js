import { RequestType, HttpRequestHandler } from '/HttpRequestHandler.js';

window.onload = function () {
    const sessionCode = localStorage.getItem('sessionCode');
    const objectId = JSON.parse(localStorage.getItem('session')).id;
    const user = JSON.parse(localStorage.getItem('user'));

    if (!sessionCode || !objectId || !user) {
        alert('Session code, Object ID, or user data is missing!');
        return;
    }

    initializePage(sessionCode, objectId, user);

    document.getElementById('btnEndSession').addEventListener('click', function () {
        btnEndSession_Click(objectId, user);
    });
};

function initializePage(sessionCode, objectId, user) {
    document.getElementById('lblSessionID').textContent = sessionCode;
}

async function btnEndSession_Click(objectId, user) {
    const btnEndSession = document.getElementById('btnEndSession');
    btnEndSession.disabled = true; //click only once

    try {
        const response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.EndSession, JSON.stringify(user), objectId);

        if (response.ok) {
            const votingSession = await response.json();
            localStorage.setItem('votingSession', JSON.stringify(votingSession));
            window.location.href = 'ResultPage.html';
        } else {
            showErrorMessage('Error! Status code: ' + response.status);
        }
    } catch (error) {
        showErrorMessage('An error occurred: ' + error.message);
    } finally {
        btnEndSession.disabled = false;
    }
}

function showErrorMessage(msg) {
    alert(msg);
}
