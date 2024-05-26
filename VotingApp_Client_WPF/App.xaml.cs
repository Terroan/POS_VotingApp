using System;
using System.Windows;

namespace VotingApp_Client_WPF
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {
        [STAThread]
        public static void Main()
        {
            //Start 2 windows for test use
            MainWindow mainWindow = new MainWindow();
            mainWindow.Height = 700;
            mainWindow.Show();

            MainWindow mainWindow2 = new MainWindow();
            mainWindow2.Height = 700;
            mainWindow2.Show();

            // run the program until last window is closed
            var application = new App();
            application.Run();
        }
    }
}
