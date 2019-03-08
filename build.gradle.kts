import org.spongepowered.eventimplgen.EventImplGenTask
import org.spongepowered.gradle.task.TaskSortClassMembers

buildscript {
    repositories {
        maven(Repo.sponge)
        mavenLocal()
    }
    dependencies {
        classpath("org.spongepowered:event-impl-gen:6.0.2-SNAPSHOT")
    }
}

plugins {
    `java-library`
    `maven-publish`
    // These need to be declared in whatever root build script is being used so that the
    // versions are resolved for the plugins, otherwise when attempting to apply the
    // sponge buildscript, it will gloriously fail due to the lack of version support.
    id(Plugins.licenser) version Versions.licenser apply false
    id(Plugins.shadow) version Versions.shadow apply false
    id(Plugins.spongeGradleId) version Versions.spongegradle apply false
    id(Plugins.sponge)
}

apply(plugin = Plugins.spongegradle)
apply(plugin = Plugins.spongemeta)
apply(plugin = Plugins.`event-impl-gen`)

val ap by sourceSets.registering {
    compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.main.get().output
}

// Project dependencies
dependencies {
    // Logging
    compileOnly(Libs.slf4j)

    // Dependency tied to Minecraft implementation
    compileOnly(Libs.guava)
    compileOnly(Libs.error_prone)
    compileOnly(Libs.gson)
    compileOnly(Libs.apache_commons)

    // Only in server
    compileOnly(Libs.jsr305)

    // Dependency injection
    compileOnly(Libs.guice)

    // High performing cache + guava
    compileOnly(Libs.caffeine)
    compileOnly(Libs.caffeine_guava) {
        exclude(group = Deps.Groups.guava, module = Deps.Modules.guava)
    }

    // Plugin meta
    compileOnly(Libs.plugin_meta)

    // Configurate
    compileOnly(Libs.configurate_hocon)
    compileOnly(Libs.configurate_gson)
    compileOnly(Libs.configurate_yaml)

    // Math + Noise for world gen
    compileOnly(Libs.flow_math)
    compileOnly(Libs.flow_noise)

    // Asm for class generation (mostly event generation, and dummy object providers
    compileOnly(Libs.asm)
}

tasks {
    val genEventImpl by existing(EventImplGenTask::class) {
        outputFactory = "org.spongepowered.api.event.SpongeEventFactory"
        include("org/spongepowered/api/util/annotation/eventgen/")
        include("org/spongepowered/api/event/*/**/*")
        exclude("org/spongepowered/api/event/cause/")
        exclude("org/spongepowered/api/event/filter/")
        exclude("org/spongepowered/api/event/impl/")
    }
    findByName("setupDecompWorkspace")?.apply {
        dependsOn(genEventImpl.get())
    }
    compileJava {
        dependsOn(genEventImpl.get())
    }
    jar {
        from(ap.get().output)
        manifest {
            attributes("Main-Class" to "org.spongepowered.api.util.InformativeMain")
        }
    }
    val sourceJar by registering(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
        from(sourceSets["ap"].allSource)
    }
    val javadocJar by registering(Jar::class) {
        dependsOn(javadoc)
        archiveClassifier.set("javadoc")
        from(javadoc.get().destinationDir)
    }
    shadowJar {
        archiveClassifier.set("shaded")
    }
    val sortClassFields by existing(TaskSortClassMembers::class) {
        add(sourceSets.main.get(), "org.spongepowered.api.CatalogTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.advancement.AdvancementTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.advancement.criteria.trigger.Triggers")
        add(sourceSets.main.get(), "org.spongepowered.api.boss.BossBarColors")
        add(sourceSets.main.get(), "org.spongepowered.api.boss.BossBarOverlays")
        add(sourceSets.main.get(), "org.spongepowered.api.data.key.Keys")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.ArmorTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.Arts")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.BannerPatternShapes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.BigMushroomTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.BodyParts")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.BrickTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.Careers")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.CoalTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.ComparatorTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.CookedFishes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.DirtTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.DisguisedBlockTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.DoublePlantTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.DyeColors")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.Fishes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.GoldenApples")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.Hinges")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.HorseColors")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.HorseStyles")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.InstrumentTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.LogAxes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.NotePitches")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.OcelotTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.ParrotVariants")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.PickupRules")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.PistonTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.PlantTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.PortionTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.PrismarineTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.Professions")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.QuartzTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.RabbitTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.RailDirections")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.SandstoneTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.SandTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.ShrubTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.SkinParts")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.SkullTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.SlabTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.StairShapes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.StoneTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.ToolTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.TreeTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.WallTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.data.type.WireAttachmentTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.block.BlockTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.block.tileentity.TileEntityTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.block.trait.BooleanTraits")
        add(sourceSets.main.get(), "org.spongepowered.api.block.trait.EnumTraits")
        add(sourceSets.main.get(), "org.spongepowered.api.block.trait.IntegerTraits")
        add(sourceSets.main.get(), "org.spongepowered.api.block.trait.EnumTraits")
        add(sourceSets.main.get(), "org.spongepowered.api.data.persistence.DataFormats")
        add(sourceSets.main.get(), "org.spongepowered.api.effect.particle.ParticleOptions")
        add(sourceSets.main.get(), "org.spongepowered.api.effect.particle.ParticleTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.effect.potion.PotionEffectTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.effect.sound.SoundTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.entity.EntityTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.entity.ai.task.AITaskTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.entity.ai.GoalTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.entity.living.complex.dragon.phase.EnderDragonPhaseTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.entity.living.player.gamemode.GameModes")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.EventContextKeys")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.damage.DamageModifierTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.damage.DamageTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.damage.source.DamageSources")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.dismount.DismountTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.health.HealingTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.health.HealthModifierTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.health.source.HealingSources")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.spawn.SpawnTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.event.cause.entity.teleport.TeleportTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.extra.fluid.FluidTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.item.enchantment.EnchantmentTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.item.FireworkShapes")
        add(sourceSets.main.get(), "org.spongepowered.api.item.ItemTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.item.inventory.equipment.EquipmentTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.item.inventory.query.QueryOperationTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.item.potion.PotionTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.scoreboard.CollisionRules")
        add(sourceSets.main.get(), "org.spongepowered.api.scoreboard.Visibilities")
        add(sourceSets.main.get(), "org.spongepowered.api.scoreboard.critieria.Criteria")
        add(sourceSets.main.get(), "org.spongepowered.api.scoreboard.displayslot.DisplaySlots")
        add(sourceSets.main.get(), "org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes")
        add(sourceSets.main.get(), "org.spongepowered.api.service.economy.transaction.TransactionTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.statistic.Statistics")
        add(sourceSets.main.get(), "org.spongepowered.api.statistic.StatisticTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.text.chat.ChatTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.text.chat.ChatVisibilities")
        add(sourceSets.main.get(), "org.spongepowered.api.text.format.TextColors")
        add(sourceSets.main.get(), "org.spongepowered.api.text.format.TextStyles")
        add(sourceSets.main.get(), "org.spongepowered.api.text.selector.SelectorTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.util.TypeTokens")
        add(sourceSets.main.get(), "org.spongepowered.api.util.ban.BanTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.util.rotation.Rotations")
        add(sourceSets.main.get(), "org.spongepowered.api.world.biome.BiomeTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.world.BlockChangeFlags")
        add(sourceSets.main.get(), "org.spongepowered.api.world.DimensionTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.world.GeneratorTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.world.difficulty.Difficulties")
        add(sourceSets.main.get(), "org.spongepowered.api.world.gen.type.BiomeTreeTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.world.gen.type.MushroomTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.world.gen.PopulatorObjects")
        add(sourceSets.main.get(), "org.spongepowered.api.world.gen.PopulatorTypes")
        add(sourceSets.main.get(), "org.spongepowered.api.world.gen.WorldGeneratorModifiers")
        add(sourceSets.main.get(), "org.spongepowered.api.world.teleport.TeleportHelperFilters")
        add(sourceSets.main.get(), "org.spongepowered.api.world.weather.Weathers")
        add(sourceSets.main.get(), "org.spongepowered.api.util.TypeTokens")
        add(sourceSets.main.get(), "org.spongepowered.api.item.recipe.crafting.CraftingRecipes")
    }
    artifacts {
        afterEvaluate {
            archives(sourceJar)
            archives(shadowJar)
        }
    }
}
