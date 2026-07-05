package attribution

import org.snakeyaml.engine.v2.api.Load
import org.snakeyaml.engine.v2.api.LoadSettings

import java.nio.file.FileSystems
import java.nio.file.Path

class AttributionManifest {
    public static String credits(File yamlFile) {
        def data = loadYaml(yamlFile)
        def list = data.rules
            .findAll { !it.generated && it.source != "Self" }
            .unique { it.author }
            .collect { "${it.author} (${it.license})" }

        if (list.isEmpty()) {
            return ""
        }

        return "Contains third-party assets by ${list.join(', ')}. " +
               "See THIRD_PARTY_NOTICES.txt for full attribution."
    }

    private static def loadYaml(File file) {
        def yaml = new Load(LoadSettings.builder().build())
        return yaml.loadFromInputStream(file.newInputStream())
    }

    private static List compileRules(List rules) {
        return rules.collect { rule ->
            [
                matcher: FileSystems.default.getPathMatcher("glob:${rule.glob}"),
                raw    : rule
            ]
        }
    }

    private static Map findRule(Path path, List rules) {
        return rules.find { it.matcher.matches(path) }
    }

    public static String thirdPartyNotices(
        File yamlFile,
        File resourcesDir
    ) {
        def data = loadYaml(yamlFile)
        def rules = compileRules(data.rules)
        def root = resourcesDir.toPath()

        def grouped = [:].withDefault { [] }

        resourcesDir.eachFileRecurse { file ->
            if (!file.isFile()) {
                return
            }

            def rel = root.relativize(file.toPath())
            def match = findRule(rel, rules)

            if (match == null || match.raw.generated) {
                return
            }

            grouped[match.raw] << rel.toString().replace('\\', '/')
        }

        def stringWriter = new StringWriter()

        stringWriter.withPrintWriter { out ->

            out.println("THIRD PARTY NOTICES")
            out.println("===================")
            out.println()

            grouped.keySet()
                .sort { it.source }
                .each { rule ->

                    out.println(rule.source)
                    out.println("-" * rule.source.length())

                    if (rule.author) {
                        out.println("Author: ${rule.author}")
                    }

                    if (rule.copyright) {
                        out.println("Copyright: ${rule.copyright}")
                    }

                    out.println("License: ${rule.license}")

                    if (rule.url) {
                        out.println("Source: ${rule.url}")
                    }

                    if (rule.attribution) {
                        out.println()
                        out.println("Required Attribution:")
                        out.println(rule.attribution.trim())
                    }

                    out.println()
                    out.println("Files:")

                    grouped[rule]
                        .sort()
                        .each {
                            out.println("  $it")
                        }

                    out.println()
                }
        }

        return stringWriter.toString()
    }
}