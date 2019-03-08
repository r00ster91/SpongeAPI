plugins {
    `kotlin-dsl`

}
repositories {
    mavenCentral()
    jcenter()
    maven("https://files.minecraftforge.net/maven")

}

dependencies {
    compileOnly("net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}