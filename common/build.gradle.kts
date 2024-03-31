architectury {
    val enabledPlatforms: String by rootProject
    common(enabledPlatforms.split(","))
}

dependencies {
    modCompileOnly(group = "tech.thatgravyboat", name = "commonats", version = "2.0")
    compileOnly(group = "me.shedaniel", name = "REIPluginCompatibilities-forge-annotations", version = "12.0.93")
}
