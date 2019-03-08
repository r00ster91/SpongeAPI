repositories {
    jcenter()
}

plugins {
    `java-library`
    checkstyle
    eclipse
    idea
    // super hack, because the plugins are declared and defined in the project that
    // is applying this plugin, we can reference them by id here, otherwise, if the
    // project is not declaring them (with versions), they will not be resolvable in
    // this buildscript.
    id(Plugins.licenser)
    id(Plugins.shadow)
    id(Plugins.spongeGradleId)
}

group = SpongeAPI.group

repositories {
    mavenCentral()
    maven(Repo.sponge)
    
}

dependencies {
    "testImplementation"(Libs.Test.junit)
    "testImplementation"(Libs.Test.hamcrest)
    "testImplementation"(Libs.Test.mockito)
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}