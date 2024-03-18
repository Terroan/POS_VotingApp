using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{
    class Voter
    {
        private string? _name;

        [JsonPropertyName("name")]
        public string? Name { get { return _name; } set {  _name = value; } }
    }
}
