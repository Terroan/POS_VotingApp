using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaktionslogik für StartPage.xaml
    /// </summary>
    public partial class StartPage : Page
    {
        public StartPage()
        {
            InitializeComponent();
        }

        //Switch to createSessionPage
        private void btnCreateSession_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new CreateSessionPage());
        }

        private void tbSessionCode_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (tbSessionCode.Text != string.Empty)
                btnJoinSession.IsEnabled = true;
            else
                btnJoinSession.IsEnabled = false;

        }

        private async void btnJoinSession_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                // Erstellen einer HttpClient-Instanz
                using (HttpClient client = new HttpClient())
                {
                    // Senden der POST-Anfrage
                    HttpResponseMessage response = await client.GetAsync("http://localhost:5000/api/session");

                    // Überprüfen der Antwort auf Erfolg
                    if (response.IsSuccessStatusCode)
                    {
                        MainFrame.Navigate(new SessionFormPage(JsonSerializer.Deserialize<VontingSessionIngress?>(response.Content.ReadAsStream())));
                    }
                    else
                    {
                        ShowErrorMessage("Fehler! Statuscode: " + response.StatusCode);
                    }
                }
            }
            catch (HttpRequestException hre)
            {
                ShowErrorMessage("Server nicht erreichbar!");
                btnCreateSession.IsEnabled = true;
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
                btnCreateSession.IsEnabled = true;
            }
        }

        private void ShowErrorMessage(string msg)
        {
            MessageBox.Show(msg, System.Reflection.Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}
