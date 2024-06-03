﻿#pragma checksum "..\..\..\ResultPage.xaml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "A9EDCC8F54B583625AECDE2DB411AF87D8CBB005"
//------------------------------------------------------------------------------
// <auto-generated>
//     Dieser Code wurde von einem Tool generiert.
//     Laufzeitversion:4.0.30319.42000
//
//     Änderungen an dieser Datei können falsches Verhalten verursachen und gehen verloren, wenn
//     der Code erneut generiert wird.
// </auto-generated>
//------------------------------------------------------------------------------

using LiveCharts.Wpf;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Controls.Ribbon;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;
using VotingApp_Client_WPF;


namespace VotingApp_Client_WPF {
    
    
    /// <summary>
    /// ResultPage
    /// </summary>
    public partial class ResultPage : System.Windows.Controls.Page, System.Windows.Markup.IComponentConnector {
        
        
        #line 25 "..\..\..\ResultPage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Label lblCreator;
        
        #line default
        #line hidden
        
        
        #line 27 "..\..\..\ResultPage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Label lblSurvey;
        
        #line default
        #line hidden
        
        
        #line 29 "..\..\..\ResultPage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.ComboBox cbQuestions;
        
        #line default
        #line hidden
        
        
        #line 31 "..\..\..\ResultPage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Label lblQuestion;
        
        #line default
        #line hidden
        
        
        #line 33 "..\..\..\ResultPage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnGoBackToStart;
        
        #line default
        #line hidden
        
        
        #line 40 "..\..\..\ResultPage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal LiveCharts.Wpf.CartesianChart lvcChart;
        
        #line default
        #line hidden
        
        
        #line 41 "..\..\..\ResultPage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Frame MainFrame;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "7.0.11.0")]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/VotingApp_Client_WPF;component/resultpage.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\..\ResultPage.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "7.0.11.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.lblCreator = ((System.Windows.Controls.Label)(target));
            return;
            case 2:
            this.lblSurvey = ((System.Windows.Controls.Label)(target));
            return;
            case 3:
            this.cbQuestions = ((System.Windows.Controls.ComboBox)(target));
            
            #line 29 "..\..\..\ResultPage.xaml"
            this.cbQuestions.SelectionChanged += new System.Windows.Controls.SelectionChangedEventHandler(this.cbQuestions_SelectionChanged);
            
            #line default
            #line hidden
            return;
            case 4:
            this.lblQuestion = ((System.Windows.Controls.Label)(target));
            return;
            case 5:
            this.btnGoBackToStart = ((System.Windows.Controls.Button)(target));
            
            #line 38 "..\..\..\ResultPage.xaml"
            this.btnGoBackToStart.Click += new System.Windows.RoutedEventHandler(this.btnGoBackToStart_Click);
            
            #line default
            #line hidden
            return;
            case 6:
            this.lvcChart = ((LiveCharts.Wpf.CartesianChart)(target));
            return;
            case 7:
            this.MainFrame = ((System.Windows.Controls.Frame)(target));
            return;
            }
            this._contentLoaded = true;
        }
    }
}

