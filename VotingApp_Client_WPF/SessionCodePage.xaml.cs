using System;
using System.Net.Http;
using System.Reflection;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;

namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaktionslogik für SessionCodePage.xaml
    /// </summary>
    public partial class SessionCodePage : Page
    {
        private string _objectId;
        private VoterEgress _user;
        public SessionCodePage(string sessionID, string objectId, VoterEgress user)
        {
            InitializeComponent();
            _user = user;
            _objectId = objectId;

            // show session id in label
            lblSessionID.Content = sessionID;
        }

        // end session 
        private async void btnEndSession_Click(object sender, RoutedEventArgs e)
        {
            btnEndSession.IsEnabled = false; //click only once

            try
            {
                // send post request
                HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.EndSession, JsonSerializer.Serialize(_user), _objectId);

                // check if request was successful
                if (response.IsSuccessStatusCode)
                {
                    var stream = await response.Content.ReadAsStreamAsync();
                    var votingSession = await JsonSerializer.DeserializeAsync<VotingSessionIngress>(stream);
                    MainFrame.Navigate(new ResultPage(votingSession, _user));
                }
                else
                {
                    ShowErrorMessage("Fehler! Statuscode: " + response.StatusCode);
                }
            }
            catch (HttpRequestException hre)
            {
                ShowErrorMessage("Server nicht erreichbar!");
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
            }
            finally
            {
                btnEndSession.IsEnabled = true;
            }    
        }

        // show error message
        private void ShowErrorMessage(string msg)
        {
            MessageBox.Show(msg, Assembly.GetEntryAssembly()?.GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}
