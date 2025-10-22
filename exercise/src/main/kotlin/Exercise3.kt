@file:OptIn(ExperimentalTime::class)

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Author(val id: Long, val handle: String)

data class Content(
    val id: Long,
    val content: String,
    val author: Author,
    val createdAt: Instant
)

context(builder: PromptBuilder)
operator fun Author.unaryPlus(): Unit {
    builder.appendLine("Author {")
    builder.appendLine("    id: $id")
    builder.appendLine("    handle: $handle")
    builder.append("}")
}

context(builder: PromptBuilder)
operator fun Content.unaryPlus(): Unit {
    builder.appendLine("Content {")
    builder.appendLine("    id: $id")
    builder.appendLine("    content: $content")
    builder.appendLine("    author: ")
    builder.appendLine(prompt { +author }.value.prependIndent("    "))
    builder.appendLine("    createdAt: $createdAt")
    builder.append("}")
}

@JvmInline
value class Feed(val list: List<Content>)

context(builder: PromptBuilder)
operator fun Feed.unaryPlus(): Unit {
    builder.appendLine("Feed {")
    list.forEach { content ->
        +content
        builder.appendLine("")
    }
    builder.append("}")
}

fun main(): Unit {
    prompt {
        +Content(
            id = 1,
            content = "Hello, world!",
            author = Author(id = 1, handle = "@johndoe"),
            createdAt = Instant.parse("2023-01-01T00:00:00Z")
        )
    }
    prompt {
        +Feed(
            listOf(
                Content(
                    id = 1,
                    content = "Hello, world!",
                    author = Author(id = 1, handle = "@johndoe"),
                    createdAt = Instant.parse("2023-01-01T00:00:00Z")
                ),
                Content(
                    id = 2,
                    content = "Hello, world 2!",
                    author = Author(id = 2, handle = "@janedoe"),
                    createdAt = Instant.parse("2023-01-01T00:00:00Z")
                )
            )
        )
    }.also(::println)
}