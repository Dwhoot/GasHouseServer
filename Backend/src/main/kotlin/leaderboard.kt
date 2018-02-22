
class Leaderboard {
    val score = Score("Josh", "2500")
    var scores = arrayOf(score)

    fun addNewScore(score: Score) {
        if (scores.count() > 9) {
            scores.dropLast(1)
            scores = arrayOf(score) + scores
        } else {
            scores = arrayOf(score) + scores
        }
    }

    fun getCurrentLeaderboard(): String {
        var scoreString = "["
        for (score in scores) {
            val json = score.getJSONScore()
            if (scores.indexOf(score) != 0) {
                scoreString += ","
            }
            scoreString += json
        }
        scoreString += "]"
        return scoreString
    }
}

class Score( name: String, score: String) {
    val name: String = name
    val score: String = score

    fun getJSONScore(): String {
        return "{\"name\":\"$name\",\"score\":\"$score\"}"
    }
}