using System;
using System.Net.Http;
using System.Reflection;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;


namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaktionslogik für LoginPage.xaml
    /// </summary>
    public partial class LoginPage : Page
    {
        private VoterEgress _user = new();
        private bool isClick;
        public LoginPage()
        {
            InitializeComponent();
        }

        // toggle buttons if username text is empty
        private void tbUsername_TextChanged(object sender, TextChangedEventArgs e)
        {
            if(tbUsername.Text.Length > 0 && pwbPassword.Password.Length > 0 && !isClick) { btnLogin.IsEnabled = true; btnSignIn.IsEnabled = true; }
            else { btnLogin.IsEnabled = false; btnSignIn.IsEnabled = false; }
        }

        // toggle buttons if password text is empty
        private void pwbPassword_PasswordChanged(object sender, RoutedEventArgs e)
        {
            if (tbUsername.Text.Length > 0 && pwbPassword.Password.Length > 0 && !isClick) { btnLogin.IsEnabled = true; btnSignIn.IsEnabled = true; }
            else { btnLogin.IsEnabled = false; btnSignIn.IsEnabled = false; }
        }

        // create new user 
        private async void btnSignIn_Click(object sender, RoutedEventArgs e)
        {
            if (tbUsername.Text.Length > 0 && pwbPassword.Password.Length > 0)
            {
                btnLogin.IsEnabled = false;
                btnSignIn.IsEnabled = false;
                isClick = true; // click button only once

                // sett uname and password
                _user.Name = tbUsername.Text;
                _user.Password = pwbPassword.Password;
                try
                {
                    // send post request
                    HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.SignIn, JsonSerializer.Serialize(_user), "");

                    // check if request was successful
                    if (response.IsSuccessStatusCode)
                    {
                        MainFrame.Navigate(new StartPage(_user)); //pass user
                    }
                    else
                    {
                        isClick = false;
                        btnSignIn.IsEnabled = true;
                        btnLogin.IsEnabled = true;
                        ShowErrorMessage("Error! Statuscode: " + response.StatusCode);
                    }
                }
                catch (HttpRequestException)
                {
                    ShowErrorMessage("Server not reachable!");
                }
                catch (Exception ex)
                {
                    ShowErrorMessage(ex.Message);
                }
                finally
                {
                    isClick = false;
                    btnLogin.IsEnabled = true;
                    btnSignIn.IsEnabled = true;
                }
            }
            else { ShowInformationMessage("Please fill in all forms"); }
        } // HTTP-REQUEST

        // login with existing user
        private async void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            if (tbUsername.Text.Length > 0 && pwbPassword.Password.Length > 0)
            {
                btnLogin.IsEnabled = false;
                btnSignIn.IsEnabled = false;
                isClick = true; // click only once

                // set uname and password
                _user.Name = tbUsername.Text;
                _user.Password = pwbPassword.Password;
                try
                {
                    // send post request
                    HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.LogIn, JsonSerializer.Serialize(_user), "");

                    // check if request was successful
                    if (response.IsSuccessStatusCode)
                    {
                        MainFrame.Navigate(new StartPage(_user));  // navigate to start page
                    }
                    else
                    {
                        ShowErrorMessage("Error! Statuscode: " + response.StatusCode);
                    }  
                }
                catch (HttpRequestException)
                {
                    ShowErrorMessage("Server not reachable!");
                }
                catch (Exception ex)
                {
                    ShowErrorMessage(ex.Message);
                }
                finally
                {
                    isClick = false;
                    btnSignIn.IsEnabled = true;
                    btnLogin.IsEnabled = true;
                }
            }
            else { ShowInformationMessage("Please fill in all forms"); }
        } // HTTP-REQUEST

        //show info message
        private void ShowInformationMessage(string msg)
        {
            MessageBox.Show(msg, Assembly.GetEntryAssembly()?.GetName().Name, MessageBoxButton.OK, MessageBoxImage.Information);
        }

        //show error message
        private void ShowErrorMessage(string msg)
        {
            MessageBox.Show(msg, Assembly.GetEntryAssembly()?.GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}
