using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Reflection;
using System.Text;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaktionslogik für StartPage.xaml
    /// </summary>
    public partial class StartPage : Page
    {
        private VoterEgress _user;
        private List<VotingSessionIngress?>? _sessions;
        private bool isClick = false;
        public StartPage(VoterEgress user)
        {
            InitializeComponent();
            _user = user;
            tbUser.Text = "User: " + _user.Name;
            FetchUserSessions();
        }

        // get all sessions for the user
        private async void FetchUserSessions()
        {
            try
            {
                HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.FetchSessions, JsonSerializer.Serialize(_user), "");
                if (response.IsSuccessStatusCode)
                {
                    _sessions = JsonSerializer.Deserialize<List<VotingSessionIngress?>?>(response.Content.ReadAsStream());
                    foreach(VotingSessionIngress? vs in _sessions)
                    {   
                        lbSessions.Items.Add(vs.Title == string.Empty || vs.Title == null ? "..." : vs.Title);
                    }
                }
                else
                {
                    ShowErrorMessage("Error! Statuscode: " + response.StatusCode);
                }
            }
            catch (HttpRequestException hre)
            {
                ShowErrorMessage("Server is not reachable!");
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
            }
        } // HTTP-REQUEST

        // Switch to createSessionPage
        private void btnCreateSession_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new CreateSessionPage(_user));
        }

        private void tbSessionCode_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (tbSessionCode.Text != string.Empty && !isClick)
                btnJoinSession.IsEnabled = true;
            else
                btnJoinSession.IsEnabled = false;
        }

        private async void btnJoinSession_Click(object sender, RoutedEventArgs e)
        {
            btnJoinSession.IsEnabled = false;
            isClick = true;
            try
            {
                string sessionID = tbSessionCode.Text.ToLower();
                HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.FetchSession, "", sessionID);

                if (response.IsSuccessStatusCode)
                {
                    MainFrame.Navigate(new SessionFormPage(JsonSerializer.Deserialize<VotingSessionIngress>(response.Content.ReadAsStream()), sessionID.ToLower()));
                }
                else
                {
                    isClick = false;
                    btnCreateSession.IsEnabled = true;
                    btnJoinSession.IsEnabled = true;
                    ShowErrorMessage("Error! Statuscode: " + response.StatusCode);
                }
            }
            catch (HttpRequestException)
            {
                ShowErrorMessage("Server is not reachable!");
                isClick = false;
                btnJoinSession.IsEnabled = true;
                btnCreateSession.IsEnabled = true;
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
                isClick = false;
                btnJoinSession.IsEnabled = true;
                btnCreateSession.IsEnabled = true;
            }
            finally
            {
                isClick = false;
                btnJoinSession.IsEnabled = true;
                btnCreateSession.IsEnabled = true;
            }
        } // HTTP-REQUEST

        private void ShowErrorMessage(string msg)
        {
            MessageBox.Show(msg, Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
        }

        // switch to update session page
        private void ListBoxItem_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            MainFrame.Navigate(new UpdateSessionPage(_sessions[lbSessions.SelectedIndex], _user));
        }
    }
}
