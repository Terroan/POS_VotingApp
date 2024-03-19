using LiveCharts;
using LiveCharts.Wpf;
using System;
using System.Collections.Generic;
using System.Linq;
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
    /// Interaktionslogik für ResultPage.xaml
    /// </summary>
    public partial class ResultPage : Page
    {
        public ResultPage(System.IO.Stream httpResponse)
        {
            InitializeComponent();

            VontingSessionIngress session = JsonSerializer.Deserialize<VontingSessionIngress>(httpResponse);

            Dictionary<string, int> voteSum = new();
            foreach(VotingPost post in session.Results)
            {
                foreach(var entry in post.Votes)
                {
                    if (!voteSum.ContainsKey(entry.Key))
                        voteSum.Add(entry.Key, entry.Value);
                    else
                        voteSum[entry.Key]++;
                }
            }

            SeriesCollection seriesCollection = new SeriesCollection();
            ChartValues<int> chartValues = new ChartValues<int>();

            foreach (var sum in voteSum)
                chartValues.Add(sum.Value);

            seriesCollection.Add(new ColumnSeries
            {
                Title = "Answers",
                Values = chartValues
            });

            lvcChart.Series = seriesCollection;
        }
    }
}
