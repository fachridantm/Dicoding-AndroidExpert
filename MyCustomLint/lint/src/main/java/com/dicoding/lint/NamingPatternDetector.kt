package com.dicoding.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass

@Suppress("UnstableApiUsage")
class NamingPatternDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes() = listOf(UClass::class.java)
    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitClass(node: UClass) {
                if (node.name?.isDefinedCamelCase() == false) {
                    context.report(
                        issue = ISSUE_NAMING_PATTERN,
                        location = context.getNameLocation(node),
                        message = "Penulisan nama class harus menggunakan CamelCase."
                    )
                }
            }
        }
    }

    companion object {
        val ISSUE_NAMING_PATTERN: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "NamingPattern",
            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            briefDescription = "Penulisan nama class harus menggunakan CamelCase.",
            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            explanation = """
                Tulis nama class dengan menggunakan CamelCase.
                Lihat contoh yang benar di https://google.github.github.io/styleguide/javaguide.html#s5.3-camel-case
            """, // no need to .trimIndent(), lint does that automatically
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                NamingPatternDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}

private fun String.isDefinedCamelCase(): Boolean {
    val charArray = toCharArray()
    return charArray
        .mapIndexed { index, current ->
            current to charArray.getOrNull(index + 1)
        }
        .none {
            it.first.isUpperCase() && it.second?.isUpperCase() ?: false
        }
}