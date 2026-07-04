package attribution

import org.snakeyaml.engine.v2.api.Load
import org.snakeyaml.engine.v2.api.LoadSettings

import java.nio.file.FileSystems
import java.nio.file.Path

class AttributionManifest {
    static String credits(File yamlFile) {
        def data = loadYaml(yamlFile)
        return buildCredits(data.rules)
    }

    static void verify(File yamlFile, File resourcesDir) {
        def data = loadYaml(yamlFile)

        def rules = compileRules(data.rules)
        def root = resourcesDir.toPath()

        def missing = []

        resourcesDir.eachFileRecurse { file ->
            if (!file.isFile()) return

            def rel = root.relativize(file.toPath())

            if (!matchesAny(rel, rules)) {
                missing << rel.toString().replace('\\', '/')
            }
        }

        if (!missing.isEmpty()) {
            throw new RuntimeException(
                "Missing attribution:\n" +
                missing.sort().collect { "  $it" }.join("\n")
            )
        }
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

    private static boolean matchesAny(Path path, List rules) {
        return rules.any { it.matcher.matches(path) }
    }

    private static String buildCredits(List rules) {

        def list = rules
            .findAll { !it.generated && it.source != "Self" }
            .unique { it.source }
            .collect { "${it.source} (${it.license})" }

        if (list.isEmpty()) {
            return ""
        }

        return "Contains third-party assets by ${list.join(', ')}. " +
               "See THIRD_PARTY_NOTICES.txt for full attribution."
    }
}