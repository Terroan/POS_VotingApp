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
        /// Interaktionslogik für CreateSessionPage.xaml
        /// </summary>
        public partial class CreateSessionPage : Page
        {
            private VotingSessionEgress _session = new();
            private VoterEgress? _user;

            public CreateSessionPage(VoterEgress user)
            {
                InitializeComponent();
                _user = user;   
            }

            // add question to session
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

            // delete question from session
            private void btnDeleteQuestion_Click(object sender, RoutedEventArgs e)
            {
                _session?.Questions?.RemoveAt(lvQuestions.SelectedIndex);
                lvQuestions.Items.RemoveAt(lvQuestions.SelectedIndex);
            }

            // select question from list
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

            // select option from selected question
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

            // remove option from selected question
            private void btnDeleteOption_Click(object sender, RoutedEventArgs e)
            {
                _session?.Questions?[lvQuestions.SelectedIndex]?.Options?.RemoveAt(lvOptions.SelectedIndex);
                lvOptions.Items.RemoveAt(lvOptions.SelectedIndex);
            }

            // set question if textboxed is left 
            private void tbQuestion_LostFocus(object sender, RoutedEventArgs e)
            {
                TextBox question = (TextBox)sender;
                if (question.Text == null || _session == null)
                    return;

                _session.Questions[lvQuestions.SelectedIndex].Question = question.Text;
            }

            // select question if textbox is clicked
            private void tbQuestion_GotFocus(object sender, RoutedEventArgs e)
            {
                TextBox question = (TextBox)sender;
                lvQuestions.SelectedIndex = lvQuestions.Items.IndexOf(question);
            }

            // set option if textbox is left
            private void tbOption_LostFocus(object sender, RoutedEventArgs e)
            {
                TextBox option = (TextBox)sender;
                if (option.Text == null)
                    return;

                _session.Questions[lvQuestions.SelectedIndex].Options[lvOptions.SelectedIndex] = option.Text;
            }

            // select option if textbox is clicked
            private void tbOption_GotFocus(object sender, RoutedEventArgs e)
            {
                TextBox option = (TextBox)sender;
                lvOptions.SelectedIndex = lvOptions.Items.IndexOf(option);
            }

            // show options for selected question
            private void ShowOptions(int questionIndex)
            {
                lvOptions.Items.Clear();

                foreach(string? s in _session.Questions[questionIndex].Options)
                {
                    TextBox tmp = new();
                    tmp.Text = s;
                    tmp.Width = 100;
                    tmp.LostFocus += tbOption_LostFocus;
                    lvOptions.Items.Add(tmp);
                }
            }

            // go back from options to questions
            private void btnOptionGoBack_Click(object sender, RoutedEventArgs e)
            {
                 gbQuestions.Visibility = Visibility.Visible;
                 gbOptions.Visibility = Visibility.Hidden;
            }

            // go back to start page
            private void btnGoBackToStart_Click(object sender, RoutedEventArgs e)
            {
                this.Visibility = Visibility.Collapsed;
            }

            // create the voting session
            private async void btnCreateSession_Click(object sender, RoutedEventArgs e)
            {
                // check if session information was filled out
                if (tbSessionName.Text == string.Empty || tbCreatorName.Text == string.Empty)
                {
                    ShowInformationMessage("Please fill out all forms!");
                    return;
                }

                btnCreateSession.IsEnabled = false; // create session only once
                try
                {
                    _session.SessionTitle = tbSessionName.Text;
                    _session.Creator = tbCreatorName.Text;

                    HttpResponseMessage response = await HttpRequestHandler.SendHttpRequestAsync(RequestType.CreateSession, JsonSerializer.Serialize(new HttpPostRequest(_session, _user)), "");
                
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
                    btnCreateSession.IsEnabled = true;
                }
            } // HTTP - REQUEST

            // show info message
            private void ShowInformationMessage(string msg)
            {
                MessageBox.Show(msg, Assembly.GetEntryAssembly()?.GetName().Name, MessageBoxButton.OK, MessageBoxImage.Information);
            }

            // show error message
            private void ShowErrorMessage(string msg)
            {
                MessageBox.Show(msg, Assembly.GetEntryAssembly()?.GetName().Name, MessageBoxButton.OK, MessageBoxImage.Error);
            }

            // show options if question is double clicked
            private void ListViewItem_DoubleClick(object sender, MouseEventArgs e)
            {
                gbQuestions.Visibility = Visibility.Hidden;
                gbOptions.Visibility = Visibility.Visible;
                ShowOptions(lvQuestions.SelectedIndex);
            }
        }
    }
