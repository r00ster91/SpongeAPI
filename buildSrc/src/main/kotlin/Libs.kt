object Libs {

    const val slf4j = "${Deps.Groups.slf4j}:${Deps.Modules.slf4j}:${Versions.slf4j}"
    const val guava = "${Deps.Groups.guava}:${Deps.Modules.guava}:${Versions.guava}"
    const val error_prone = "${Deps.Groups.errorProne}:${Deps.Modules.errorProne}:${Versions.errorprone}"
    const val gson = "${Deps.Groups.gson}:${Deps.Modules.gson}:${Versions.gson}"
    const val apache_commons = "${Deps.Groups.apache_commons}:${Deps.Modules.apache_commons}:${Versions.apache_commons}"
    const val jsr305 = "${Deps.Groups.findBugs}:${Deps.Modules.jsr305}:${Versions.jsr305}"
    const val guice = "${Deps.Groups.guice}:${Deps.Modules.guice}:${Versions.guice}"
    const val caffeine = "${Deps.Groups.caffeine}:${Deps.Modules.caffeine}:${Versions.caffeine}"
    const val caffeine_guava = "${Deps.Groups.caffeine}:${Deps.Modules.guava}:${Versions.caffeine}"
    const val plugin_meta = "${Deps.Groups.sponge}:${Deps.Modules.plugin_meta}:${Versions.plugin_meta}"
    const val configurate_hocon = "${Deps.Groups.sponge}:${Deps.Modules.configurate_hocon}:${Versions.configurate}"
    const val configurate_gson = "${Deps.Groups.sponge}:${Deps.Modules.configurate_gson}:${Versions.configurate}"
    const val configurate_yaml = "${Deps.Groups.sponge}:${Deps.Modules.configurate_yaml}:${Versions.configurate}"
    const val flow_math = "${Deps.Groups.flowpowered}:${Deps.Modules.flow_math}:${Versions.`flow-math`}"
    const val flow_noise = "${Deps.Groups.flowpowered}:${Deps.Modules.flow_noise}:${Versions.`flow-noise`}"
    const val asm = "${Deps.Groups.asm}:${Deps.Modules.asm}:${Versions.asm}"

    object Test {
        const val junit = "${Deps.Groups.junit}:${Deps.Modules.junit}:${Versions.Test.junit}"
        const val hamcrest = "${Deps.Groups.hamcrest}:${Deps.Modules.hamcrest}:${Versions.Test.hamcrest}"
        const val mockito = "${Deps.Groups.mockito}:${Deps.Modules.mockito}:${Versions.Test.mockito}"
    }
}