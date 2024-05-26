using System;
using System.Net.Http;
using System.Reflection;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaktionslogik für SessionFormPage.xaml
    /// </summary>
    public partial class SessionFormPage : Page
    {
        VotingSessionIngress _session;
        string? _sessionID;
        VotingPost _answers = new();

        public SessionFormPage(VotingSessionIngress session, string? sessionID)
        {
            InitializeComponent();
            _session = session;
            _sessionID = sessionID;
            lblCreatorName.Content = session.Creator;
            lblSessionName.Content = session.Title;

            // populate initial questions
            _answers.Votes = new();
            PopulateQuestions();
        }
        
        // show options for selected question
        private void ShowOptions()
        {
            lbOptions.Items.Clear();
            var options = _session.Questions[lvQuestions.SelectedIndex].Options;

            foreach (   var item in options)
            {
                CheckBox cb = new CheckBox();
                cb.Content = item;
                cb.Checked += cbOption_Checked;
                lbOptions.Items.Add(cb);
                if (_answers.Votes.ContainsKey(lvQuestions.SelectedIndex + ""))
                {
                    if (_answers.Votes[lvQuestions.SelectedIndex + ""] == options.IndexOf(item))
                    {
                        lbOptions.SelectedIndex = options.IndexOf(item);
                        cb.IsChecked = true;
                    }
                }
            }
        }

        // populate question listbox
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

        // select option if checkbox is checked
        private void cbOption_Checked(object sender, RoutedEventArgs e)
        {
            lbOptions.SelectedIndex = lbOptions.Items.IndexOf(sender);
            foreach(var item in lbOptions.Items)
            {
                if (item != sender)
                    (item as CheckBox).IsChecked = false;
            }

            if (_answers.Votes.ContainsKey(lvQuestions.SelectedIndex + ""))
                _answers.Votes[lvQuestions.SelectedIndex + ""] = lbOptions.SelectedIndex;
            else
                _answers.Votes.Add(lvQuestions.SelectedIndex + "", lbOptions.SelectedIndex);
        }

        // go back to start page
        private void btnGoBackToStart_Click(object sender, RoutedEventArgs e)
        {
            this.Visibility = Visibility.Collapsed;
        }

        // close options
        private void btnOptionGoBack_Click(object sender, RoutedEventArgs e)
        {
            gbQuestions.Visibility = Visibility.Visible;
            gbOptions.Visibility = Visibility.Hidden;
        }

        // send answers
        private async void btnFinish_Click(object sender, RoutedEventArgs e)
        {
            if(tbSurveyTaker.Text == string.Empty)
            {
                ShowInformationMessage("Please fill in survey taker data");
                return;
            }
            _answers.Voter = tbSurveyTaker.Text;
       
            try
            {
                // Senden der POST-Anfrage
                HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.PostResults, JsonSerializer.Serialize(_answers), _sessionID);

                // Überprüfen der Antwort auf Erfolg
                if (response.IsSuccessStatusCode)
                {
                    ShowInformationMessage("Your answers have been admitted");
                    this.Visibility = Visibility.Collapsed;
                }
                else
                {
                    ShowErrorMessage("Fehler! Statuscode: " + response.StatusCode+response.ReasonPhrase);
                }
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
            }
        } // HTTP-REQUEST
        
        // switch to options if question double clicked
        private void ListViewItem_DoubleClick(object sender, MouseEventArgs e)
        {
            gbQuestions.Visibility = Visibility.Hidden;
            gbOptions.Visibility = Visibility.Visible;
            ShowOptions();
        }

        private void ShowInformationMessage(string msg)
        {
            MessageBox.Show(msg, Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Information);
        }

        private void ShowErrorMessage(string msg)
        {
            MessageBox.Show(msg, Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
        }

        private void lvQuestions_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            ShowOptions();
        }
    }
}
