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
    /// Interaktionslogik für CreateSessionPage.xaml
    /// </summary>
    public partial class CreateSessionPage : Page
    {
        private VotingSession? _session = new();

        public CreateSessionPage()
        {
            InitializeComponent();
        }

        private void btnAddQuestion_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?.Add(new VotingQuestion());
            TextBox tmp = new();
            tmp.TextChanged += tbQuestion_TextChanged;
            tmp.GotFocus += tbQuestion_GotFocus;
            lvQuestions.Items.Add(tmp);
        }

        private void btnDeleteQuestion_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?.RemoveAt(lvQuestions.SelectedIndex);
            lvQuestions.Items.RemoveAt(lvQuestions.SelectedIndex);
        }

        private void lvQuestions_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lvQuestions.SelectedIndex != -1)
            {
                btnDeleteQuestion.IsEnabled = true;
                gbQuestions.Visibility = Visibility.Hidden;
                gbOptions.Visibility = Visibility.Visible;
                ShowOptions(lvQuestions.SelectedIndex);
            }
            else
            {
                btnDeleteQuestion.IsEnabled = false;
                gbQuestions.Visibility = Visibility.Visible;
                gbOptions.Visibility = Visibility.Hidden;
            }
        }

        private void btnAddOption_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?[lvQuestions.SelectedIndex]?.Options?.Add("");
            TextBox tmp = new();
            tmp.TextChanged += tbOption_TextChanged;
            tmp.GotFocus += tbOption_GotFocus;
            lvOptions.Items.Add(tmp);
        }

        private void btnDeleteOption_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?[lvQuestions.SelectedIndex].Options.RemoveAt(lvOptions.SelectedIndex);
            lvOptions.Items.RemoveAt(lvOptions.SelectedIndex);
        }

        private void lvOptions_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lvOptions.SelectedIndex != -1)
            {
                btnDeleteOption.IsEnabled = true;

            }
            else
            {
                btnDeleteOption.IsEnabled = false;
            }
        }

        private void tbQuestion_TextChanged(object sender, TextChangedEventArgs e)
        {
            TextBox question = (TextBox)sender;
            if (question.Text == null)
                return;
            _session.Questions[lvQuestions.SelectedIndex].Question = question.Text;
        }

        private void tbOption_TextChanged(object sender, TextChangedEventArgs e)
        {
            TextBox option = (TextBox)sender;
            if (option.Text == null)
                return;
            _session.Questions[lvQuestions.SelectedIndex].Options[lvOptions.SelectedIndex] = option.Text;
        }

        private void tbQuestion_GotFocus(object sender, RoutedEventArgs e)
        {
            lvQuestions.SelectedIndex = lvQuestions.Items.IndexOf((TextBox)sender);
        }

        private void tbOption_GotFocus(object sender, RoutedEventArgs e)
        {
            lvOptions.SelectedIndex = lvOptions.Items.IndexOf((TextBox)sender);
        }
        
        private void ShowOptions(int questionIndex)
        {
            lvOptions.Items.Clear();
            foreach(string? s in _session?.Questions?[questionIndex]?.Options)
            {
                TextBox tmp = new();
                tmp.Text = s;
                tmp.TextChanged += tbOption_TextChanged;
                tmp.GotFocus += tbOption_GotFocus;
                lvOptions.Items.Add(tmp);
            }
        }

        private void btnOptionGoBack_Click(object sender, RoutedEventArgs e)
        {
             gbQuestions.Visibility = Visibility.Visible;
             gbOptions.Visibility = Visibility.Hidden;
        }

        private void btnGoBackToStart_Click(object sender, RoutedEventArgs e)
        {
            this.Visibility = Visibility.Collapsed;
        }

        private async void btnCreateSession_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                _session.SessionTitle = tbSessionName.Text;
                _session.Password = tbSessionPassword.Text;
                _session.Creator.Name = tbCreatorName.Text;

                // Erstellen einer HttpClient-Instanz
                using (HttpClient client = new HttpClient())
                {
                    MessageBox.Show(JsonSerializer.Serialize(_session));
                    // Senden der POST-Anfrage
                    HttpResponseMessage response = await client.PostAsync("http://localhost:5000/api/session", new StringContent(JsonSerializer.Serialize(_session), Encoding.UTF8, "application/json"));

                    // Überprüfen der Antwort auf Erfolg
                    if (response.IsSuccessStatusCode)
                    {
                        MessageBox.Show("Session erstellt");
                        VontingSessionIngress? tmp = JsonSerializer.Deserialize<VontingSessionIngress?>(response.Content.ReadAsStream());
                        MessageBox.Show(tmp.SessionID);
                        //load waiting page
                    }
                    else
                    {
                        MessageBox.Show("Fehler! Statuscode: " + response.StatusCode);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Fehler: " + ex.Message);
            }
        }
    }
}
