// Part 1
class PromptBuilder(val prompt: StringBuilder) {
    fun append(text: String) = prompt.append(text)
    fun appendLine(text: String) = prompt.appendLine(text)
    operator fun String.unaryPlus() = appendLine(this)
}

@JvmInline
value class Prompt(val value: String)

fun prompt(block: PromptBuilder.() -> Unit): Prompt {
    val prompt = StringBuilder()
    PromptBuilder(prompt).block()
    return Prompt(prompt.toString())
}

// Part 2
class MarkdownBuilder(val markdown: StringBuilder) {
    fun header(number: Int, text: String) =
        markdown.appendLine("${"#".repeat(number)} $text")

    fun text(text: String) = markdown.appendLine(text)
}

fun PromptBuilder.markdown(block: MarkdownBuilder.() -> Unit): Unit =
    MarkdownBuilder(prompt).block()

// Part 3
class ItemsBuilder(private val items: StringBuilder) {
    fun item(text: String) = items.appendLine(" - $text")
}

fun MarkdownBuilder.items(block: ItemsBuilder.() -> Unit): Unit =
    ItemsBuilder(markdown).block()

fun main() {
    // part 1
    prompt {
        +"Hello, world!"
        +"Second line"
    }.also(::println)

    // part 2
    prompt {
        markdown {
            header(1, "Objective")
            text("Build a typed DSL for prompting")
        }
    }.also(::println)

    // part 3
    prompt {
        markdown {
            header(1, "Exercise 1 (10min)")
            items {
                item("Create a DSL receiver type (i.e. `PromptBuilder`)")
                item("Create a DSL function (i.e. `prompt`) which returns the prompt string")
            }
            header(1, "Exercise 2 (10min)")
            items {
                item("Extend the `PromptBuilder` dsl with a `markdown` DSL.")
                item("Create `header`, `text` functions to build the markdown string")
                item("Create a nested `fun MarkdownBuilder.items(block: ItemsBuilder.() -> Unit)` function to build the items list")
            }
        }
    }.also(::println)
}