package io.github.mcengine.extension.example.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

class ExtensionGradlePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.pluginManager.apply('java')
        project.pluginManager.apply('maven-publish')

        String buildNum = System.getenv('BUILD_NUMBER')
        String devRelease = System.getenv('DEV_RELEASE_VERSION')
        String releaseTag = System.getenv('RELEASE_VERSION')
        boolean isDevBuild = (buildNum != null && !buildNum.isEmpty())
        boolean isDevRealeaseBuild = (devRelease != null && !devRelease.isEmpty())

        String calculatedVersion = 'unspecified'

        if (releaseTag != null && !releaseTag.isEmpty()) {
            calculatedVersion = releaseTag.replace('v', '')
        } else if (project.hasProperty('project-version')) {
            def baseVersion = project.property('project-version')
            def iteration = project.findProperty('project-iteration') ?: '1'

            if (isDevBuild || isDevRealeaseBuild) {
                calculatedVersion = "${baseVersion}-build.${buildNum}"
            } else {
                calculatedVersion = "${baseVersion}-${iteration}"
            }
        }

        project.version = calculatedVersion
        project.group = project.property('project-group')

        project.repositories.mavenCentral()
        project.repositories.maven {
            url = project.uri('https://maven.pkg.github.com/MCEngine/mcextension')
            credentials {
                username = project.findProperty('gpr.user') ?: System.getenv('USER_GITHUB_NAME') ?: System.getenv('GITHUB_ACTOR')
                password = project.findProperty('gpr.key') ?: System.getenv('USER_GITHUB_TOKEN') ?: System.getenv('GITHUB_TOKEN')
            }
        }
        project.repositories.maven {
            url = 'https://repo.papermc.io/repository/maven-public/'
        }
        project.repositories.maven {
            name = 'sonatype'
            url = 'https://oss.sonatype.org/content/groups/public/'
        }

        project.dependencies.add('compileOnly', 'io.github.mcengine:mcextension:2026.0.3-1-SNAPSHOT')
        project.dependencies.add('compileOnly', 'io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT')

        def gitOwner = project.property('git-org-name')
        def gitRepo = project.property('git-org-repository')
        def gitUrl = project.property('project-artifact-url')

        project.publishing {
            publications {
                create('mavenJava', MavenPublication) {
                    artifactId = project.property('project-artifact-id')
                    from project.components.java

                    pom {
                        name = project.property('project-artifact-name')
                        description = project.property('project-artifact-description')
                        url = gitUrl

                        developers {
                            developer {
                                id = 'jetsadawijit'
                                url = 'https://jetsadawijit.github.io'
                            }
                        }

                        scm {
                            connection = "scm:git:git://github.com/${gitOwner}/${gitRepo}.git"
                            developerConnection = "scm:git:ssh://github.com/${gitOwner}/${gitRepo}.git"
                            url = gitUrl
                        }
                    }
                }
            }

            repositories {
                maven {
                    name = 'GitHubPackages'
                    url = project.uri("https://maven.pkg.github.com/${gitOwner}/${gitRepo}")
                    credentials {
                        username = System.getenv('USER_GITHUB_NAME')
                        password = System.getenv('USER_GITHUB_TOKEN')
                    }
                }
            }
        }
    }
}
