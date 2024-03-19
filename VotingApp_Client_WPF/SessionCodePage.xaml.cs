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
    /// Interaktionslogik für SessionCodePage.xaml
    /// </summary>
    public partial class SessionCodePage : Page
    {
        private string _sessionID;
        public SessionCodePage(string sessionID)
        {
            InitializeComponent();
            lblSessionID.Content = sessionID;
            _sessionID = sessionID;
        }

        private async void btnEndSession_Click(object sender, RoutedEventArgs e)
        {
            // Erstellen einer HttpClient-Instanz
            using (HttpClient client = new HttpClient())
            {
                // Senden der POST-Anfrage
                HttpResponseMessage response = await client.GetAsync("http://localhost:5000/api/session/"+_sessionID);

                // Überprüfen der Antwort auf Erfolg
                if (response.IsSuccessStatusCode)
                {
                    MainFrame.Navigate(new ResultPage(response.Content.ReadAsStream()));
                }
                else
                {
                    MessageBox.Show("Fehler! Statuscode: " + response.StatusCode);
                }
            }
        }
    }
}
