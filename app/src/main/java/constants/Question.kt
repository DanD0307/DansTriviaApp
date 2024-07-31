package constants

data class Question(
    var questionText: String,
    var options: List<String>,
    var correctAnswer: Int
)
