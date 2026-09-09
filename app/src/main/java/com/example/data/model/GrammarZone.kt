package com.example.data.model

enum class GrammarZone(
    val id: Int,
    val emoji: String,
    val title: String,
    val subtitle: String,
    val themeColorHex: Long,
    val character: String,
    val description: String
) {
    VILLAGE(
        id = 1,
        emoji = "🏕️",
        title = "Stone Age Village",
        subtitle = "Discover Present Simple",
        themeColorHex = 0xFF8D6E63,
        character = "Adam 👦",
        description = "Meet Adam and learn how we talk about daily routines, habits, and things that are always true!"
    ),
    CAMPFIRE(
        id = 2,
        emoji = "🔥",
        title = "The Campfire",
        subtitle = "I / You / We / They + Base Verb",
        themeColorHex = 0xFFFF8F00,
        character = "Sara & Friends 👧👦",
        description = "Gather around the warm campfire to practice actions we do together every day!"
    ),
    CAVE(
        id = 3,
        emoji = "🪨",
        title = "The Secret Cave",
        subtitle = "He / She / It + Verb + S",
        themeColorHex = 0xFF7E57C2,
        character = "The Wise Cave Wall 🪨✨",
        description = "Discover the ancient Stone Age secret: He, She, and It love to add the letter -S!"
    ),
    FOREST(
        id = 4,
        emoji = "🌳",
        title = "The Prehistoric Forest",
        subtitle = "Don't & Doesn't",
        themeColorHex = 0xFF2E7D32,
        character = "Grammar Monster 👾",
        description = "Rescue the verbs from the Grammar Monster who tries to put extra S letters after doesn't!"
    ),
    RIVER(
        id = 5,
        emoji = "🌊",
        title = "The Crystal River",
        subtitle = "Do & Does Questions",
        themeColorHex = 0xFF0288D1,
        character = "Dino the Explorer 🦕",
        description = "Cross the river stepping stones by asking and answering with Do and Does!"
    ),
    CHALLENGE(
        id = 6,
        emoji = "🏆",
        title = "The Great Grammar Challenge",
        subtitle = "Master Present Simple Assessment",
        themeColorHex = 0xFFFFB300,
        character = "Teacher Shaimaa Owiess 💜",
        description = "Show everything you have discovered and earn your official Stone Age Grammar Certificate & Sticker!"
    );

    companion object {
        fun fromId(id: Int): GrammarZone = entries.firstOrNull { it.id == id } ?: VILLAGE
    }
}
