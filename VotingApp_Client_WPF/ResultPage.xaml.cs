using LiveCharts;
using LiveCharts.Wpf;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;

namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaktionslogik für ResultPage.xaml
    /// </summary>
    public partial class ResultPage : Page
    {
        private VotingSessionIngress _session;
        private Dictionary<string, Dictionary<int, int>> _votes = new();
        private VoterEgress _user;

        public ResultPage(VotingSessionIngress session, VoterEgress user)
        {
            InitializeComponent();

            //set session info
            _user = user;
            _session = session;
            lblCreator.Content = "Creator: " + _session.Creator;
            lblSurvey.Content = "Survey: " + _session.Title;
            
            // set initial question and count votes if not null or empty
            if(_session.Questions != null)
            {
                if(_session.Questions.Count > 0 )
                    lblQuestion.Content = _session.Questions[0]?.Question;
            }
            if(_session.Results != null)
            {
                if(_session.Results.Count > 0)
                    _votes = CountVotes(_session.Results);
            }

            // populate combobox with questions
            PopulateCombobox();
        }

        // count votes for each option for each question
        private Dictionary<string, Dictionary<int, int>> CountVotes(List<VotingPost> posts)
        {
            Dictionary<string, Dictionary<int, int>> voteSum = new();
            foreach (VotingPost post in posts)
            {
                if(post.Votes == null)
                    continue;

                foreach (var entry in post.Votes)
                {   
                    // add question if not already contained
                    if (!voteSum.ContainsKey(entry.Key))
                    {
                        voteSum.Add(entry.Key, new Dictionary<int, int>());
                    }

                    // add option value
                    if (!voteSum[entry.Key].ContainsKey(entry.Value))
                        voteSum[entry.Key].Add(entry.Value, 1);
                    else
                        voteSum[entry.Key][entry.Value]++;
                }
            }
            return voteSum;
        }

        // populate combobox with questions
        private void PopulateCombobox()
        {
            int x = 1;
            foreach (var question in _session.Questions)
                cbQuestions.Items.Add("Question " + (x++));
        }

        // fill diagramm with x and y axis
        private void FillDiagramm(int question)
        {
            if (_votes == null || _votes.Count == 0)
                return;

            //lvcChart.Width = 800; // width
            //lvcChart.Height = 600; // height

            // clear diagramm
            lvcChart.Series.Clear();
            lvcChart.AxisX.Clear();
            lvcChart.AxisY.Clear();

            SeriesCollection seriesCollection = new SeriesCollection();
            ChartValues<int> chartValues = new ChartValues<int>();
            List<string> labels = new List<string>();

            // set chartvalues
            foreach (var dict in _votes)
            {
                if(Convert.ToInt32(dict.Key) == question)
                {
                    int x = 0;
                    foreach(var option in _session.Questions[question].Options)
                    {
                        if (!dict.Value.ContainsKey(x))
                            chartValues.Add(0);
                        else
                            chartValues.Add(dict.Value[x]);
                        labels.Add(_session.Questions[question].Options[x]); // add label to option
                        x++;
                    }
                }
            }

            // set axis label with shortened text
            var xAxis = new Axis
            {
                Labels = labels.Select(l => l.Length > 20 ? l.Substring(0, 20) + "..." : l).ToList(),
                FontSize = 10,
                FontWeight = FontWeights.Bold,
                Foreground = Brushes.Black,
                LabelsRotation = 45 // rotate label by 45 degrees
            };

            lvcChart.AxisX.Add(xAxis);

            // set y axis
            var yAxis = new Axis
            {
                LabelFormatter = value => value.ToString("N0"), // show only no-comma-itegers
                FontSize = 20,
                FontWeight = FontWeights.Bold,
                Foreground = Brushes.Black,
                Separator = new LiveCharts.Wpf.Separator // seperator for y axis
                {
                    Step = 1 // steps between numbers
                }
            };
            yAxis.FontSize = 16; // y axis font size
            lvcChart.AxisY.Add(yAxis);

            // set the max value of the y axis to the max number that is found in chartValues
            if(chartValues.Count > 0)
            {
                int maxCount = chartValues.Max();
                lvcChart.AxisY[0].MaxValue = maxCount;
            }

            // add columnseries to diagramm to show answers
            seriesCollection.Add(new ColumnSeries
            {
                Title = "Answers",
                Values = chartValues,
                FontSize = 20,
                FontWeight = FontWeights.Bold,
                Foreground = Brushes.Black
            });

            lvcChart.Series = seriesCollection;
        }

        // fill diagramm if question was selected
        private void cbQuestions_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            lblQuestion.Content = _session?.Questions?[cbQuestions.SelectedIndex]?.Question;
            FillDiagramm(cbQuestions.SelectedIndex);
        }

        // go back to start page
        private void btnGoBackToStart_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new StartPage(_user));
        }
    }
}
