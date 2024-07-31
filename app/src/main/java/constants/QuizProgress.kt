package constants

data class QuizProgress(
    val quizName: String,
    val questionList: ArrayList<Question>,
    val incorrectQuestionList: ArrayList<Question>,
    val index: Int,
    val questionOverFlag: Boolean,
    val endOfQuizFlag: Boolean,
    val hundredPercentFlag: Boolean
)
