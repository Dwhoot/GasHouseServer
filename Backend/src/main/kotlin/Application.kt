import io.ktor.application.*
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.*
import io.ktor.pipeline.*
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.response.*
import io.ktor.routing.*
import java.time.Duration
import java.text.DateFormat
import org.slf4j.*

fun Application.main() {

    var leaderboard = Leaderboard()

    install(DefaultHeaders)
    install(CORS) {
        maxAge = Duration.ofDays(1)
    }

    println("Ready For Routing.")
    routing {
        get("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Parameter id not found")
            println("Get entity with Id=$id")
            val responseText = leaderboard.getCurrentLeaderboard()
            call.respondText("$responseText", ContentType.Application.Json)
        }
        get("/") {
            println("Get all entities")
            call.respondText("Hello, world!", ContentType.Text.Html)
        }
        get("/leaderboard") {
            println("Get Leaderboard")
            val responseText = leaderboard.getCurrentLeaderboard()
            call.respondText("$responseText", ContentType.Application.Json)
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Parameter id not found")
            println("Delete entity with Id=$id")
        }
        delete("/leaderboard") {
            println("Delete all entities")
//                PersonRepo.clear()
        }
        post("/leaderboard") {
            println("Received Post Request")
            val record = call.receiveParameters()
            if (record.isEmpty() || !record.contains("name") && !record.contains("score")) {
                println("Early return. Missing values")
                call.respond(HttpStatusCode.BadRequest, "Bad Request")
                return@post
            }
            val contentDescription = record.toString()
            println("$contentDescription")

            val nameVal: String = record.get("name") ?: "None"
            val scoreVal: String = record.get("score") ?: "None"
            val returnScore = Score(nameVal, scoreVal)

            leaderboard.addNewScore(returnScore)
            val returnMessage = leaderboard.getCurrentLeaderboard()
            call.respond(HttpStatusCode(201, "Success"), "$returnMessage")
        }
    }
}