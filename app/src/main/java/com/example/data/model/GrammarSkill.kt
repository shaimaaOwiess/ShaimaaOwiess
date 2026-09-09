package com.example.data.model

enum class GrammarSkill(
    val code: String,
    val displayName: String,
    val description: String,
    val ruleSummary: String,
    val commonMistake: String,
    val teacherTip: String
) {
    CONCEPT_ROUTINES(
        code = "CONCEPT_ROUTINES",
        displayName = "Present Simple Concept & Routines",
        description = "Using Present Simple for daily habits, routines, and general facts",
        ruleSummary = "We use the Present Simple to talk about habits, daily routines, and things that are generally true.",
        commonMistake = "Using past tense or '-ing' for habitual actions.",
        teacherTip = "Whenever you talk about what you do every day or every morning, use the Present Simple! 💜"
    ),
    BASE_VERBS(
        code = "BASE_VERBS",
        displayName = "I / You / We / They + Base Verb",
        description = "Affirmative sentences with plural subjects and first/second person",
        ruleSummary = "With I, You, We, and They, we use the simple base form of the verb without adding -s.",
        commonMistake = "Adding unnecessary '-s' to plural subjects (e.g. 'They plays').",
        teacherTip = "Remember: Team I, You, We, They leaves the verb happy and clean without -s! 🌟"
    ),
    THIRD_PERSON_S(
        code = "THIRD_PERSON_S",
        displayName = "He / She / It + Verb + S",
        description = "Third-person singular affirmative verb conjugation",
        ruleSummary = "With He, She, and It (or one person/animal/thing), we add -s or -es to the verb.",
        commonMistake = "Omitting '-s' with singular third person (e.g. 'He play').",
        teacherTip = "Stop for one second and ask: 'Is it He, She, or It?' If yes, give the verb its special -S! 🪨✨"
    ),
    NEGATIVES_DONT_DOESNT(
        code = "NEGATIVES_DONT_DOESNT",
        displayName = "Don't / Doesn't Negatives",
        description = "Negative sentences using don't and doesn't with base verbs",
        ruleSummary = "Use 'don't' for I/You/We/They and 'doesn't' for He/She/It. After doesn't, the main verb is ALWAYS base form!",
        commonMistake = "Keeping '-s' after doesn't (e.g. 'He doesn't plays').",
        teacherTip = "Doesn't already stole the -S! So the main verb returns to its base form: 'He doesn't play.' 👾"
    ),
    QUESTIONS_DO_DOES(
        code = "QUESTIONS_DO_DOES",
        displayName = "Do / Does Questions",
        description = "Forming questions using auxiliary verbs Do and Does",
        ruleSummary = "Start with DO for I/You/We/They and DOES for He/She/It. The main verb stays in base form.",
        commonMistake = "Using 'Do' with He/She/It or conjugating main verb in question.",
        teacherTip = "Look at who you are asking about: He/She/It takes DOES! I/You/We/They takes DO! 🦕"
    ),
    SHORT_ANSWERS(
        code = "SHORT_ANSWERS",
        displayName = "Short Answers",
        description = "Correct responses: Yes, I do / No, I don't / Yes, he does / No, he doesn't",
        ruleSummary = "Match the helper verb: 'Do you...?' -> 'Yes, I do / No, I don't.' 'Does he...?' -> 'Yes, he does / No, he doesn't.'",
        commonMistake = "Answering with just yes/no or using wrong pronoun/auxiliary.",
        teacherTip = "Echo the question! If the question starts with Does, your answer ends with does or doesn't! 🌊"
    ),
    FREQUENCY_ADVERBS(
        code = "FREQUENCY_ADVERBS",
        displayName = "Frequency Words (Always, Usually, Never)",
        description = "Using frequency adverbs before the main verb",
        ruleSummary = "Words like always, usually, often, sometimes, and never come BEFORE the main action verb.",
        commonMistake = "Placing frequency words after the main verb.",
        teacherTip = "Frequency words are polite: they wait right before the action verb! 'Adam always eats breakfast.' ⭐"
    ),
    SPELLING_RULES(
        code = "SPELLING_RULES",
        displayName = "Spelling Rules (-s, -es, -ies)",
        description = "Spelling changes for verbs ending in -ch, -sh, -x, -ss, -o, or consonant+y",
        ruleSummary = "Verbs ending in ch, sh, ss, x, o take -es (watches, washes, goes). Consonant + y changes to -ies (flies, studies).",
        commonMistake = "Just adding 's' to watch -> 'watchs' or fly -> 'flys'.",
        teacherTip = "If it ends with a hissing sound (-ch, -sh, -x, -ss) or -o, add -ES! If consonant + y, trade y for -IES! 💎"
    );

    companion object {
        fun fromCode(code: String): GrammarSkill = entries.firstOrNull { it.code == code } ?: CONCEPT_ROUTINES
    }
}
