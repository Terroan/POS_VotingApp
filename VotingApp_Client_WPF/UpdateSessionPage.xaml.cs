using System;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaktionslogik für UpdateSessionPage.xaml
    /// </summary>
    public partial class UpdateSessionPage : Page
    {
        private VotingSessionIngress _session;
        private VoterEgress _user;

        public UpdateSessionPage(VotingSessionIngress session, VoterEgress user)
        {
            InitializeComponent();
            _session = session;
            _user = user;
            ShowSession();
        }

        // show the selected question
        private void ShowSession()
        {
            tbCreatorName.Text = _session.Creator;
            tbSessionName.Text = _session.Title;
            foreach(VotingQuestion? vq in _session.Questions)
            {
                TextBox tmp = new();
                tmp.Text = vq.Question;
                tmp.Width = 300;
                tmp.Height = 25;
                tmp.GotFocus += tbQuestion_GotFocus;
                tmp.LostFocus += tbQuestion_LostFocus;
                lvQuestions.Items.Add(tmp);
            }
        }

        // update session
        private async void btnUpdateSession_Click(object sender, RoutedEventArgs e)
        {

            if (tbSessionName.Text == string.Empty || tbCreatorName.Text == string.Empty)
            {
                ShowInformationMessage("Please fill out all forms!");
                return;
            }

            btnUpdateSession.IsEnabled = false; //click only once
            try
            {
                _session.Title = tbSessionName.Text;
                _session.Creator = tbCreatorName.Text;

                // send post request
                VotingSessionEgress tmp = new();
                tmp.SessionTitle = _session.Title;
                tmp.Creator = _session.Creator;
                tmp.Questions = _session.Questions;
                HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.UpdateSession, JsonSerializer.Serialize(new HttpPostRequest(tmp, _user)), _session.ObjectID);

                // check for success
                if (response.IsSuccessStatusCode)
                {
                    ShowInformationMessage("Session was updated!");
                    MainFrame.Navigate(new StartPage(_user));
                }
                else
                {
                    ShowErrorMessage("Error! Statuscode: " + response.StatusCode);
                    btnUpdateSession.IsEnabled = true;
                }
            }
            catch (HttpRequestException)
            {
                ShowErrorMessage("Server is not reachable!");
                btnUpdateSession.IsEnabled = true;
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
                btnUpdateSession.IsEnabled = true;
            }
        } // HTTP-REQUEST

        // start selected session
        private async void btnStartSession_Click(object sender, RoutedEventArgs e)
        {
            if (tbSessionName.Text == string.Empty || tbCreatorName.Text == string.Empty)
            {
                ShowInformationMessage("Please fill out all forms!");
                return;
            }

            btnStartSession.IsEnabled = false; //click only once
            try
            {
                _session.Title = tbSessionName.Text;
                _session.Creator = tbCreatorName.Text;

                // send post request
                HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.StartSession, JsonSerializer.Serialize(_user), _session.ObjectID);

                // check for success
                if (response.IsSuccessStatusCode)
                {
                    MainFrame.Navigate(new SessionCodePage(response.Content.ReadAsStringAsync().Result, _session.ObjectID,  _user));
                }
                else
                {
                    ShowErrorMessage("Error! Statuscode: " + response.StatusCode);
                    btnStartSession.IsEnabled = true;
                }
            }
            catch (HttpRequestException)
            {
                ShowErrorMessage("Server is not reachable!");
                btnStartSession.IsEnabled = true;
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
                btnStartSession.IsEnabled = true;
            }
        } // HTTP-REQUEST

        // add question
        private void btnAddQuestion_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?.Add(new VotingQuestion());
            TextBox tmp = new();
            tmp.Width = 300;
            tmp.Height = 25;
            tmp.GotFocus += tbQuestion_GotFocus;
            tmp.LostFocus += tbQuestion_LostFocus;
            lvQuestions.Items.Add(tmp);
        }

        // remove question
        private void btnDeleteQuestion_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?.RemoveAt(lvQuestions.SelectedIndex);
            lvQuestions.Items.RemoveAt(lvQuestions.SelectedIndex);
        }

        // select question
        private void lvQuestions_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lvQuestions.SelectedIndex != -1)
            {
                btnDeleteQuestion.IsEnabled = true;
            }
            else
            {
                btnDeleteQuestion.IsEnabled = false;
            }
        }

        // add option to selected question
        private void btnAddOption_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?[lvQuestions.SelectedIndex]?.Options?.Add("");
            TextBox tmp = new();
            tmp.Width = 300;
            tmp.Height = 25;
            tmp.GotFocus += tbOption_GotFocus;
            tmp.LostFocus += tbOption_LostFocus;
            lvOptions.Items.Add(tmp);
        }

        // remove option from selected question
        private void btnDeleteOption_Click(object sender, RoutedEventArgs e)
        {
            _session?.Questions?[lvQuestions.SelectedIndex].Options.RemoveAt(lvOptions.SelectedIndex);
            lvOptions.Items.RemoveAt(lvOptions.SelectedIndex);
        }

        // select option
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

        // focus for question and option tbs -> select if focus 
        private void tbQuestion_LostFocus(object sender, RoutedEventArgs e)
        {
            TextBox question = (TextBox)sender;
            if (question.Text == null)
                return;
            _session.Questions[lvQuestions.SelectedIndex].Question = question.Text;
        }

        private void tbQuestion_GotFocus(object sender, RoutedEventArgs e)
        {
            TextBox question = (TextBox)sender;
            lvQuestions.SelectedIndex = lvQuestions.Items.IndexOf(question);
        }

        private void tbOption_LostFocus(object sender, RoutedEventArgs e)
        {
            TextBox option = (TextBox)sender;
            if (option.Text == null)
                return;
            _session.Questions[lvQuestions.SelectedIndex].Options[lvOptions.SelectedIndex] = option.Text;
        }

        private void tbOption_GotFocus(object sender, RoutedEventArgs e)
        {
            TextBox option = (TextBox)sender;
            lvOptions.SelectedIndex = lvOptions.Items.IndexOf(option);
        }

        private void ShowOptions(int questionIndex)
        {
            lvOptions.Items.Clear();
            foreach (string? s in _session?.Questions?[questionIndex]?.Options)
            {
                TextBox tmp = new();
                tmp.Text = s;
                tmp.Width = 300;
                tmp.Height = 25;
                tmp.LostFocus += tbOption_LostFocus;
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

        private void ShowInformationMessage(string msg)
        {
            MessageBox.Show(msg, System.Reflection.Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Information);
        }

        private void ShowErrorMessage(string msg)
        {
            MessageBox.Show(msg, System.Reflection.Assembly.GetEntryAssembly().GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
        }

        private void ListViewItem_DoubleClick(object sender, MouseEventArgs e)
        {
            gbQuestions.Visibility = Visibility.Hidden;
            gbOptions.Visibility = Visibility.Visible;
            ShowOptions(lvQuestions.SelectedIndex);
        }

        private async void btnDeleteSession_Click(object sender, RoutedEventArgs e)
        {
            btnDeleteSession.IsEnabled = false; //click only once
            try
            {
                _session.Title = tbSessionName.Text;
                _session.Creator = tbCreatorName.Text;

                HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.DeleteSession, JsonSerializer.Serialize(_user), _session.ObjectID);
                // Überprüfen der Antwort auf Erfolg
                if (response.IsSuccessStatusCode)
                {
                    ShowInformationMessage("Session was deleted!");
                    MainFrame.Navigate(new StartPage(_user));
                }
                else
                {
                    ShowErrorMessage("Fehler! Statuscode: " + response.StatusCode);
                    btnDeleteSession.IsEnabled = true;
                }
 
            }
            catch (HttpRequestException hre)
            {
                ShowErrorMessage(hre.Message);
                ShowErrorMessage("Server nicht erreichbar!");
                btnDeleteSession.IsEnabled = true;
            }
            catch (Exception ex)
            {
                ShowErrorMessage(ex.Message);
                btnDeleteSession.IsEnabled = true;
            }
        }
    }
}
