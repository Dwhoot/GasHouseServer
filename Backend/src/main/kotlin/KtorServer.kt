import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.*

val LOG: Logger = LoggerFactory.getLogger("ktor-app")
val portArgName = "--server.port"
val defaultPort = 5000

fun main(args: Array<String>) {
    println("Server Main Called.")
    for (strin in args) {
        println("$strin")
    }
    val energy = System.getenv().get("PORT") as? Int
    val port = if (energy != null) {
        energy!!
    } else defaultPort
    embeddedServer(Netty, port, module = Application::main).start(wait = true)
}
