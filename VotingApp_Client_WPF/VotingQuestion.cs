using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VotingApp_Client_WPF
{
    // container class for question data
    public class VotingQuestion
    {
        private string? _question = "";
        private List<string?>? _options = new();

        public VotingQuestion() { }

        [JsonPropertyName("question")]
        public string? Question { get { return _question; } set { _question = value; } }
        [JsonPropertyName("options")]
        public List<string?>? Options { get {  return _options; } set { _options = value; } }
    }
}
