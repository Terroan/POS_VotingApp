using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Reflection;
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
    /// Interaktionslogik für SessionFormPage.xaml
    /// </summary>
    public partial class SessionFormPage : Page
    {
        VontingSessionIngress _session;
        VotingPost _answers = new();

        public SessionFormPage(VontingSessionIngress session)
        {
            InitializeComponent();
            _session = session;
            PopulateQuestions();
        }

        private void ShowOptions()
        {
            foreach (var item in _session.Questions[lvQuestions.SelectedIndex].Options)
            {
                Label option = new();
                option.Width = 300;
                option.Height = 25;
                option.Content = item;

                CheckBox checkbox = new CheckBox();
                checkbox.Checked += cbOption_Checked;

                StackPanel tmp = new();
                tmp.Orientation = Orientation.Horizontal;
                tmp.Children.Add(checkbox);
                tmp.Children.Add(option);

                lvOptions.Items.Add(tmp);

                if (_answers.Votes[_session.Questions[lvQuestions.SelectedIndex].Options.IndexOf(item) + ""] == lvOptions.Items.Count)
                {
                    lvOptions.SelectedIndex = lvOptions.Items.Count;
                    checkbox.IsChecked = true;
                }
            }
        }

        private void PopulateQuestions()
        {
            foreach(var item in _session.Questions)
            {
                Label question = new();
                question.Width = 300;
                question.Height = 25;
                question.Content = item.Question;
                lvQuestions.Items.Add(question);
            }
        }

        private void cbOption_Checked(object sender, RoutedEventArgs e)
        {
            lvOptions.SelectedIndex = lvOptions.Items.IndexOf(sender);
            foreach(var item in lvOptions.Items)
            {
                if (item != sender)
                    ((item as StackPanel).Children[0] as CheckBox).IsChecked = false;
            }

            if (_answers.Votes.ContainsKey(lvQuestions.SelectedIndex + ""))
                _answers.Votes[lvQuestions.SelectedIndex + ""] = lvOptions.SelectedIndex;
            else
                _answers.Votes.Add(lvQuestions.SelectedIndex + "", lvOptions.SelectedIndex);
        }

        private void btnGoBackToStart_Click(object sender, RoutedEventArgs e)
        {
            this.Visibility = Visibility.Collapsed;
        }

        private void btnOptionGoBack_Click(object sender, RoutedEventArgs e)
        {
            gbQuestions.Visibility = Visibility.Visible;
            gbOptions.Visibility = Visibility.Hidden;
        }

        private async void btnFinish_Click(object sender, RoutedEventArgs e)
        {
            if(tbSurveyTaker.Text == string.Empty)
            {
                ShowInformationMessage("Please fill in survey taker data");
                return;
            }

            Voter voter = new();
            voter.Name = tbSurveyTaker.Text;
            _answers.Voter = voter;
       
            try
            {
                // Erstellen einer HttpClient-Instanz
                using (HttpClient client = new HttpClient())
                {
                    // Senden der POST-Anfrage
                    HttpResponseMessage response = await client.PostAsync("http://localhost:5000/api/session/"+_session.SessionID+"/results", new StringContent(JsonSerializer.Serialize(_answers)));

                    // Überprüfen der Antwort auf Erfolg
                    if (response.IsSuccessStatusCode)
                    {
                        ShowInformationMessage("Your answers have been admitted");
                        this.Visibility = Visibility.Collapsed;
                    }
                    else
                    {
                        ShowErrorMessage("Fehler! Statuscode: " + response.StatusCode);
                    }
                }
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
            }
        }

        private void ListViewItem_DoubleClick(object sender, MouseEventArgs e)
        {
            gbQuestions.Visibility = Visibility.Hidden;
            gbOptions.Visibility = Visibility.Visible;
            ShowOptions();
        }

        private void ShowInformationMessage(string msg)
        {
            MessageBox.Show(msg, System.Reflection.Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Information);
        }

        private void ShowErrorMessage(string msg)
        {
            MessageBox.Show(msg, System.Reflection.Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
        }

        private void lvQuestions_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            ShowOptions();
        }
    }
}
