import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.*

val LOG: Logger = LoggerFactory.getLogger("ktor-app")
val portArgName = "--server.port"
val defaultPort = 8080

fun main(args: Array<String>) {
    println("Server Main Called.")
    for (strin in args) {
        println("$strin")
    }
    val portConfigured = args.isNotEmpty() && args[0].startsWith(portArgName)
    val port = if (portConfigured) {
        println("Custom port configured: ${args[0]}")
        args[0].split("=").last().trim().toInt()
    } else defaultPort
    embeddedServer(Netty, port, module = Application::main).start(wait = true)
}
