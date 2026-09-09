package com.example.data.model

enum class RemediationGameType(
    val title: String,
    val icon: String,
    val subtitle: String,
    val description: String
) {
    BUILD_SENTENCE(
        title = "Build the Sentence",
        icon = "🪨",
        subtitle = "Tap stone blocks in the right order",
        description = "Arrange the prehistoric stone word blocks to build a perfect Present Simple sentence!"
    ),
    CAMPFIRE_CHALLENGE(
        title = "Campfire Challenge",
        icon = "🔥",
        subtitle = "Pick the verb to feed the flame",
        description = "Choose the correct verb form before the warm campfire embers cool down!"
    ),
    FEED_DINOSAUR(
        title = "Feed the Friendly Dinosaur",
        icon = "🦕",
        subtitle = "Give Dino the correct sentence treat",
        description = "Baby Dino is hungry! Pick the grammatically correct berry snack for Dino to munch on."
    ),
    HIT_TARGET(
        title = "Hit the Grammar Target",
        icon = "🏹",
        subtitle = "Aim at the right grammar target",
        description = "Hit the bullseye by launching a stone arrow at the correct Present Simple choice!"
    ),
    FOREST_HUNT(
        title = "Forest Word Hunt",
        icon = "🌳",
        subtitle = "Find the missing Present Simple word",
        description = "Explore prehistoric leaves and trees to uncover the correct missing verb!"
    ),
    GRAMMAR_TREASURE(
        title = "Grammar Treasure",
        icon = "💎",
        subtitle = "Unlock glowing cave crystals",
        description = "Tap the true Present Simple fact to open the glittering ancient treasure chest!"
    )
}

data class SentenceBuildTask(
    val targetSentence: String,
    val scrambledWords: List<String>,
    val skill: GrammarSkill,
    val hint: String
)

data class MiniGameQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)
