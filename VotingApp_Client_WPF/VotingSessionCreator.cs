using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{
    public class VotingSessionCreator
    {
        private string? _name = "";

        public VotingSessionCreator() { }

        [JsonPropertyName("name")]
        public string? Name { get { return _name; } set { _name = value; } }
    }
}
