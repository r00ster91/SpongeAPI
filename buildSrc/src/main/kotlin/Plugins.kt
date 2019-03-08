object Plugins {
    const val spongeGradleId = "${Deps.Groups.sponge}.plugin"

    object FG2_3 {
        const val classpath = "net.minecraftforge.gradle:ForgeGradle:${Versions.fg23}"
        const val extensionName = "minecraft"
        const val id = "net.minecraftforge.gradle.forge"
    }

    const val licenser = "net.minecrell.licenser"
    const val shadow = "com.github.johnrengelman.shadow"
    /**
     * Basically the sponge.gradle.kts script that applies the defaults, such as
     * licensor, checkstyle, javadoc, etc.
     */
    const val sponge = "sponge"
    const val spongegradle = "org.spongepowered.gradle"
    const val spongemeta = "org.spongepowered.meta"
    const val `event-impl-gen` = "org.spongepowered.event-impl-gen"
}
