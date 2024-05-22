// Import the RequestType enum and HttpRequestHandler class
import { RequestType, HttpRequestHandler } from '/HttpRequestHandler.js';
import { VoterEgress } from '/Models/VoterEgress.js';

// Function to handle sign-in button click
async function btnSignIn_Click() {
    var username = document.getElementById('tbUsername').value;
    var password = document.getElementById('pwbPassword').value;
    var user = new VoterEgress();
    user.Name = username;
    user.Password = password;

    try {
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.SignIn, JSON.stringify(user), '');
        if (response.ok) {
            localStorage.setItem('user', JSON.stringify(user));
            window.location.href = '/StartPage.html'; // Navigate to start page
        } else {
            alert('Sign-in failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while processing your request.');
    }
}

// Function to handle login button click
async function btnLogin_Click() {
    var username = document.getElementById('tbUsername').value;
    var password = document.getElementById('pwbPassword').value;
    var user = new VoterEgress();
    user.Name = username;
    user.Password = password;

    try {
        var response = await HttpRequestHandler.sendHttpRequestAsync(RequestType.LogIn, JSON.stringify(user), '');
        if (response.ok) {
            localStorage.setItem('user', JSON.stringify(user));
            window.location.href = '/StartPage.html'; // Navigate to start page
        } else {
            alert('Error! Statuscode: ' + response.status);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while processing your request.');
    }
}

// Event listener for button clicks
document.getElementById('btnSignIn').addEventListener('click', btnSignIn_Click);
document.getElementById('btnLogin').addEventListener('click', btnLogin_Click);

// Function to handle input change for username
function tbUsername_TextChanged() {
    const tbUsername = document.getElementById('tbUsername').value;
    const pwbPassword = document.getElementById('pwbPassword').value;
    
    // Toggle button state based on input fields
    const btnLogin = document.getElementById('btnLogin');
    const btnSignIn = document.getElementById('btnSignIn');
    if (tbUsername.length > 0 && pwbPassword.length > 0) {
        btnLogin.disabled = false;
        btnSignIn.disabled = false;
    } else {
        btnLogin.disabled = true;
        btnSignIn.disabled = true;
    }
}

// Function to handle input change for password
function pwbPassword_PasswordChanged() {
    const tbUsername = document.getElementById('tbUsername').value;
    const pwbPassword = document.getElementById('pwbPassword').value;
    
    // Toggle button state based on input fields
    const btnLogin = document.getElementById('btnLogin');
    const btnSignIn = document.getElementById('btnSignIn');
    if (tbUsername.length > 0 && pwbPassword.length > 0) {
        btnLogin.disabled = false;
        btnSignIn.disabled = false;
    } else {
        btnLogin.disabled = true;
        btnSignIn.disabled = true;
    }
}

// Event listener for username input change
document.getElementById('tbUsername').addEventListener('input', tbUsername_TextChanged);
// Event listener for password input change
document.getElementById('pwbPassword').addEventListener('input', pwbPassword_PasswordChanged);
