   M S B A M L   `   `   `  ����O  KVotingApp_Client_WPF, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null;"clr-namespace:VotingApp_Client_WPFVotingApp_Client_WPF  5      U QLiveCharts.Wpf, Version=0.9.7.0, Culture=neutral, PublicKeyToken=0bc1f845d1ebb8dfG4clr-namespace:LiveCharts.Wpf;assembly=LiveCharts.WpfLiveCharts.Wpf 5      %    VotingApp_Client_WPF.ResultPage   5      W SPresentationCore, Version=7.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=7.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=7.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=7.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5      R NSystem.Xaml, Version=7.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5      4local"clr-namespace:VotingApp_Client_WPF     5      Dlvc4clr-namespace:LiveCharts.Wpf;assembly=LiveCharts.Wpf     5        N� Title$  
Statistics��5       N� 
Background �	#FFD27EB0 N� 
FontFamily$ Showcard Gothic)�5       N� FontSize$ 20(�5	      .��5      � $��Center=�6   $��Center=�5       � RowDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $��* �6   �� 5      $��* �6    � ColumnDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $��* �6   .+�5   
   �� -   5         ��Name$ 
lblCreator��5      $��Creator:��6$   $��0��67   $��0��6G   $
��Left=�6T   $	��Top=�6o   $��10q�6�   $	��100��6�   �� 5   
   -   5        $ 	lblSurvey��5      $��Survey:��6#   $��0��65   $��0��6E   $
��Left=�6R   $��Center=�6m   $��10q�6�   $	��100��6�   �� 5   
   -   5         ��Name$ cbQuestions��5       �� SelectedIndex$ 0��6(   $��0��6:   $��0��6G   $��10,75,10,10q�6W   6l   �� 5   
   -   5        $ lblQuestion��5      $*��$What is the equivalent of happiness?��6%   $��2��6T   $��0��6d   $��Bottom=�6q   $��10q�6�   �� 5!   
   -   5        	 ��Name$	 btnGoBackToStart��5!      $��Go back��5"   	   $��Right=�5#   	   $��
0,10,10,58q�5$   	   $��0��5%   	   $��1��6   ���	#FFBDAEBC$��93��5&   ?   #  LiveCharts.Wpf.CartesianChart  5(   
   -   5        
  Name$
 lvcChart��5(      $	��500��6/   $	��250��6;   $��1��6I   $��2��6V   $��2��6j   $��10q�6{    � 5)   
   -   5          �Name$ 	MainFrame��5)        � NavigationUIVisibility$ Hidden=�6#   $y�Visible=�6C   $��3��6X   $��2��6i   5*      5+      