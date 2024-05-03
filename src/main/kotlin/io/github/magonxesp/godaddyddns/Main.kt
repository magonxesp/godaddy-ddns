package io.github.magonxesp.godaddyddns

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt

class ConfigureCommand : CliktCommand(
    name = "configure",
    help = "Create a new configuration"
) {
    val apiKey by option().prompt("Godaddy API key")
    val apiSecret by option().prompt("Godady API secret")
    val domain by option().prompt("Domain")
    val hostNames by option().prompt("Host names will be updated (separated by comma)")

    override fun run() {
        val configuration = Configuration(
            apiKey = apiKey,
            apiSecret = apiSecret,
            domain = domain,
            hostNames = hostNames.trim().split(",").map { it.trim() }
        )

        configuration.save()
        echo("\u2728 Done! godaddyddns command is now configured! \u2728")
        echo("Saved in ${Configuration.configFile.absolutePath}")
    }
}

class SyncCommand : CliktCommand(
    name = "sync",
    help = "Update the current public IP address to the configured GoDaddy's hostnames"
) {
    override fun run() {
        val configuration = Configuration.read()
        val ipifyClient = IpifyClient()
        val godaddyClient = GodaddyClient(configuration.domain, configuration.apiKey, configuration.apiSecret)

        echo("Updating hostnames ip address")

        val currentIpAddress = ipifyClient.fetchCurrentPublicIpAddress()
        echo("Current ip address: $currentIpAddress")
        echo("⚠\uFE0F Hostnames will be updated to ip address $currentIpAddress ⚠\uFE0F")

        for (hostname in configuration.hostNames) {
            godaddyClient.updateGodaddyHostName(hostname, currentIpAddress)
            echo("\uD83D\uDFE2 Hostname $hostname updated!")
        }

        echo("\u2728 Done! All hostnames are updated to godaddy! \u2728")
    }
}

class MainCommand : CliktCommand(
    help = "Update IP address to dns records of GoDaddy"
) {
    init {
        subcommands(ConfigureCommand(), SyncCommand())
    }

    override fun run() { }
}

fun main(args: Array<String>) = MainCommand().main(args)
