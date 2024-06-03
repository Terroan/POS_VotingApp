using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{ 
    // container class for user data outgoing 
    public class VoterEgress
    {
        private string? _name;
        private string? _password;

        public VoterEgress() { }

        [JsonPropertyName("name")]
        public string? Name { get { return _name; } set { _name = value; } }

        [JsonPropertyName("password")]
        public string? Password { get { return _password; } set { _password = value; } }
    }
}
