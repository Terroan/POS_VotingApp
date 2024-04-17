using LiveCharts;
using LiveCharts.Wpf;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Reflection.Emit;
using System.Text.Json;
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
        private VontingSessionIngress session;
        private Dictionary<string, Dictionary<int, int>> votes;

        public ResultPage(System.IO.Stream httpResponse)
        {
            InitializeComponent();

            session = JsonSerializer.Deserialize<VontingSessionIngress>(httpResponse);
            lblCreator.Content = "Creator: " + session.Creator.Name;
            lblSurvey.Content = "Survey: " + session.Title;
            lblQuestion.Content = session.Questions[0].Question;
            votes = CountVotes(session.Results);
            PopulateCombobox();
        }

        private Dictionary<string, Dictionary<int, int>> CountVotes(List<VotingPost> posts)
        {
            Dictionary<string, Dictionary<int, int>> voteSum = new();
            foreach (VotingPost post in posts)
            {
                foreach (var entry in post.Votes)
                {   
                    //Add question if not already contained
                    if (!voteSum.ContainsKey(entry.Key))
                    {
                        voteSum.Add(entry.Key, new Dictionary<int, int>());
                    }

                    //Add option value
                    if (!voteSum[entry.Key].ContainsKey(entry.Value))
                        voteSum[entry.Key].Add(entry.Value, 1);
                    else
                        voteSum[entry.Key][entry.Value]++;

                }
            }
            return voteSum;
        }

        private void PopulateCombobox()
        {
            int x = 1;
            foreach (var question in session.Questions)
                cbQuestions.Items.Add("Question " + (x++));
        }

        private void FillDiagramm(int question)
        {
           // lvcChart.Width = 800; // Breite des Diagramms
           // lvcChart.Height = 600; // Höhe des Diagramms

            lvcChart.Series.Clear();
            lvcChart.AxisX.Clear();
            lvcChart.AxisY.Clear();

            SeriesCollection seriesCollection = new SeriesCollection();
            ChartValues<int> chartValues = new ChartValues<int>();
            List<string> labels = new List<string>();

            foreach (var dict in votes)
            {
                if(Convert.ToInt32(dict.Key) == question)
                {
                    int x = 0;
                    foreach(var option in session.Questions[question].Options)
                    {
                        if (!dict.Value.ContainsKey(x))
                            chartValues.Add(0);
                        else
                            chartValues.Add(dict.Value[x]);
                        labels.Add(session.Questions[question].Options[x]);
                        x++;// Fügen Sie den Label für die Option hinzu
                    }
                }
            }

            // Setzen Sie die Achsenbeschriftungen
            var xAxis = new Axis
            {
                Labels = labels, // Optionen als Beschriftungen verwenden
                FontSize = 20,
                FontWeight = FontWeights.Bold,
                Foreground = Brushes.Black
            };
            lvcChart.AxisX.Add(xAxis);

            // Setzen Sie die Titel für die Y-Achse
            var yAxis = new Axis
            {
                LabelFormatter = value => value.ToString("N0"), // Nur Ganzzahlen anzeigen
                FontSize = 20,
                FontWeight = FontWeights.Bold,
                Foreground = Brushes.Black,
                Separator = new LiveCharts.Wpf.Separator // Separator für die Y-Achse
                {
                    Step = 1 // Schrittgröße für die Intervalle zwischen den Werten
                }
            };
            yAxis.FontSize = 16; // Schriftgröße für die Zahlen erhöhen
            lvcChart.AxisY.Add(yAxis);

            // Bestimmen Sie die maximale Y-Achsenwert für die Skalierung
            int maxCount = chartValues.Max();
            lvcChart.AxisY[0].MaxValue = maxCount;

            // Fügen Sie eine ColumnSeries hinzu
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

        private void cbQuestions_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            lblQuestion.Content = session.Questions[cbQuestions.SelectedIndex].Question;
            FillDiagramm(cbQuestions.SelectedIndex);
        }

        private void btnGoBackToStart_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new StartPage());
        }
    }
}
