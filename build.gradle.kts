import net.fabricmc.loom.task.RemapJarTask

plugins {
	id("maven-publish")
	id("fabric-loom") version "1.9-SNAPSHOT"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

repositories {
	maven("https://maven.fabricmc.net/")
	maven("https://oss.sonatype.org/content/repositories/snapshots")
	maven("https://maven.shedaniel.me/")
	maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
	minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_api_version")}")

	modApi("com.terraformersmc:modmenu:${project.property("modmenu_version")}")
	modApi("me.shedaniel.cloth:cloth-config-fabric:${project.property("cloth_config_version")}") {
		exclude(group = "net.fabricmc.fabric-api")
	}
}

//loom.clientOnlyMinecraftJar()

fabricApi {
	configureDataGeneration()
}

tasks.named<RemapJarTask>("remapJar") {
	archiveVersion.set("${project.version}-for-MC-${project.property("minecraft_version")}")
}

tasks.named<Jar>("jar") {
	from("LICENSE")
}

tasks.processResources {
	inputs.property("version", project.version)
	inputs.property("minecraft_version", project.property("minecraft_version"))
	inputs.property("loader_version", project.property("loader_version"))
	inputs.property("fabric_api_version", project.property("fabric_api_version"))
	inputs.property("modmenu_version", project.property("modmenu_version"))
	inputs.property("cloth_config_version", project.property("cloth_config_version"))
	filteringCharset = "UTF-8"

	filesMatching("fabric.mod.json") {
		expand("version" to project.version,
			"minecraft_version" to project.property("minecraft_version"),
			"loader_version" to project.property("loader_version"),
			"fabric_api_version" to project.property("fabric_api_version"),
			"modmenu_version" to project.property("modmenu_version"),
			"cloth_config_version" to project.property("cloth_config_version"))
	}

	// Do not export Paint.NET files
	filesMatching("**/*.pdn") {
		exclude()
	}
}