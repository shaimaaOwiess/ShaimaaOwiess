package com.example.data.datasource

import com.example.data.model.GrammarQuestion
import com.example.data.model.GrammarSkill
import com.example.data.model.GrammarZone
import com.example.data.model.MiniGameQuestion
import com.example.data.model.QuestionType
import com.example.data.model.SentenceBuildTask

object StoneAgeContent {

    // Teacher encouragement messages for correct answers
    val correctEncouragements = listOf(
        "Fantastic! 🌟 You got it!\nI'm so proud of you!\n— Shaimaa Owiess 💜",
        "Brilliant work! 🌟 You are learning more and more every step!\n— Shaimaa Owiess 💜",
        "Wow! You found the answer! 🥳🪨 Keep going, superstar!\n— Shaimaa Owiess ⭐",
        "Excellent! 🎉 I knew you could do it!\n— Shaimaa Owiess 💜",
        "Your English brain is getting stronger! 🧠✨ Keep shining!\n— Shaimaa Owiess 🌟",
        "Super stone age grammar skills! 🦕💜 You are a true champion!\n— Shaimaa Owiess ⭐"
    )

    // Teacher gentle supportive messages for mistakes
    val supportiveMistakeMessages = listOf(
        "Oops! That's okay! 💜 Mistakes help our brains learn. Let's look at it together.\n— Shaimaa Owiess 🌷",
        "Don't worry, my lovely learner! 🌸 You are learning, and learning takes practice. I'm right here with you!\n— Shaimaa Owiess 💜",
        "Almost there! 🥺✨ Let's learn from this one and try again. I believe you can do it!\n— Shaimaa Owiess 🌟",
        "One little mistake cannot stop a great learner! 🪨💪 Let's keep going together!\n— Shaimaa Owiess 💜",
        "Every mistake is a secret clue that helps you grow smarter! Let's examine it together.\n— Shaimaa Owiess 💜"
    )

    // 6 Zone Lesson Storyboards
    data class ZoneLesson(
        val zone: GrammarZone,
        val teacherIntro: String,
        val characterDialogue: String,
        val keyRules: List<String>,
        val interactiveExamples: List<String>,
        val checkpointQuestions: List<GrammarQuestion>
    )

    val zoneLessons: List<ZoneLesson> = listOf(
        ZoneLesson(
            zone = GrammarZone.VILLAGE,
            teacherIntro = "Hi, my amazing learner! 💜\nWelcome to our Stone Age adventure!\nToday we are going to discover the Present Simple together.\nAre you ready? Let's go!",
            characterDialogue = "👦 Adam says: \"Welcome to my stone village! Every single day:\n• I wake up.\n• I eat breakfast.\n• I play with stones.\n• I sleep in my warm hut.\"",
            keyRules = listOf(
                "We use the Present Simple to talk about habits and daily routines.",
                "We use it for things that happen regularly (every day, every morning).",
                "We also use it for general facts that are always true!"
            ),
            interactiveExamples = listOf(
                "I wake up at sunrise. 🌅",
                "Adam plays every day. 🪨",
                "Birds sing in the prehistoric morning. 🐦"
            ),
            checkpointQuestions = listOf(
                GrammarQuestion(
                    id = 101,
                    zone = GrammarZone.VILLAGE,
                    skill = GrammarSkill.CONCEPT_ROUTINES,
                    storyContext = "Adam is describing his routine activities every day in the village.",
                    characterAvatar = "👦 Adam",
                    questionText = "What does Adam do every day?",
                    options = listOf("He plays.", "He played.", "He is playing."),
                    correctOptionIndex = 0,
                    explanation = "Adam plays every day! We use 'plays' because Adam is one person (He), and it describes his regular daily routine."
                ),
                GrammarQuestion(
                    id = 102,
                    zone = GrammarZone.VILLAGE,
                    skill = GrammarSkill.CONCEPT_ROUTINES,
                    storyContext = "Teacher Shaimaa points at the prehistoric sunrise.",
                    characterAvatar = "⭐ Teacher Shaimaa",
                    questionText = "The sun ___ in the morning.",
                    options = listOf("rises", "rise", "is rising"),
                    correctOptionIndex = 0,
                    explanation = "We say 'The sun rises' because the sun is 'IT' (one thing), and this is a general fact that happens every morning!"
                )
            )
        ),
        ZoneLesson(
            zone = GrammarZone.CAMPFIRE,
            teacherIntro = "Welcome to the warm campfire! 🔥\nNotice how Sara and her friends talk about actions they do together.\nWith I, You, We, and They, verbs stay simple and easy!",
            characterDialogue = "👧 Sara says: \"Come sit by our fire! We eat berries. They walk in the forest. You play games with us. I sleep under the stars!\"",
            keyRules = listOf(
                "I + base verb (I eat, I play)",
                "You + base verb (You run, You sing)",
                "We + base verb (We walk, We laugh)",
                "They + base verb (They sleep, They hunt fruits)",
                "Rule: Never add -s when the subject is I, You, We, or They!"
            ),
            interactiveExamples = listOf(
                "I eat sweet wild berries. 🍓",
                "You play the wooden flute. 🎵",
                "We walk together to the river. 🚶‍♂️",
                "They sleep beside the glowing embers. 🔥"
            ),
            checkpointQuestions = listOf(
                GrammarQuestion(
                    id = 201,
                    zone = GrammarZone.CAMPFIRE,
                    skill = GrammarSkill.BASE_VERBS,
                    storyContext = "Sara points to the village children running together.",
                    characterAvatar = "👧 Sara",
                    questionText = "Complete the sentence:\nThey ___ every day.",
                    options = listOf("play", "plays", "playing"),
                    correctOptionIndex = 0,
                    explanation = "We say 'They play.' With THEY, the verb stays in its base form without adding -s!"
                ),
                GrammarQuestion(
                    id = 202,
                    zone = GrammarZone.CAMPFIRE,
                    skill = GrammarSkill.BASE_VERBS,
                    storyContext = "You and Adam are sharing campfire bread.",
                    characterAvatar = "👦 Adam",
                    questionText = "\"We ___ dinner together every evening.\"",
                    options = listOf("eats", "eat", "eating"),
                    correctOptionIndex = 1,
                    explanation = "We say 'We eat dinner.' Remember: Team WE leaves the verb clean and base!"
                )
            )
        ),
        ZoneLesson(
            zone = GrammarZone.CAVE,
            teacherIntro = "Step into the glowing Secret Cave! 🪨✨\nLook at the ancient cave wall paintings.\nTeacher Shaimaa reveals our big Stone Age secret: HE, SHE, and IT usually need S!",
            characterDialogue = "🪨 Ancient Wall Inscription:\n• Adam plays with rocks.\n• Sara reads symbols.\n• The baby dino sleeps peacefully.\nSee that sparkling letter 'S' at the end of each verb?",
            keyRules = listOf(
                "He + verb + S (He runs, He speaks)",
                "She + verb + S (She cooks, She reads)",
                "It + verb + S (The cat sleeps, The rain falls)",
                "Spelling Rule 1: Verbs ending in -ch, -sh, -ss, -x, -o add -ES (watches, washes, goes)",
                "Spelling Rule 2: Consonant + y changes to -IES (flies, carries)"
            ),
            interactiveExamples = listOf(
                "Sara catches the shiny pebble. (catch -> catches) 💎",
                "Adam washes his hands in the cave pool. (wash -> washes) 🧼",
                "The eagle flies high above the cave. (fly -> flies) 🦅"
            ),
            checkpointQuestions = listOf(
                GrammarQuestion(
                    id = 301,
                    zone = GrammarZone.CAVE,
                    skill = GrammarSkill.THIRD_PERSON_S,
                    storyContext = "Sara is looking at a prehistoric scroll in the cave.",
                    characterAvatar = "⭐ Teacher Shaimaa",
                    questionText = "Sara ___ an exciting story every night.",
                    options = listOf("reads", "read", "reading"),
                    correctOptionIndex = 0,
                    explanation = "Sara is one person (SHE), so we add -S to read -> 'Sara reads'!"
                ),
                GrammarQuestion(
                    id = 302,
                    zone = GrammarZone.CAVE,
                    skill = GrammarSkill.SPELLING_RULES,
                    storyContext = "Adam observes the friendly cave owls.",
                    characterAvatar = "👦 Adam",
                    questionText = "Adam ___ the owls in the cave. (watch)",
                    options = listOf("watches", "watchs", "watch"),
                    correctOptionIndex = 0,
                    explanation = "'Watch' ends in -ch, so we must add -ES to make 'watches'!"
                )
            )
        ),
        ZoneLesson(
            zone = GrammarZone.FOREST,
            teacherIntro = "Welcome to the mysterious Prehistoric Forest! 🌳\nBeware of the cheeky Grammar Monster 👾!\nHe loves trying to put extra S letters after DOESN'T!\nLet's teach him the correct grammar rules!",
            characterDialogue = "👾 Grammar Monster says: \"Haha! I wrote: 'He doesn't plays!'\"\n⭐ Teacher Shaimaa says: \"Stop right there, Monster! After DOESN'T, the verb goes back to its base form: 'He doesn't play!'\"",
            keyRules = listOf(
                "I / You / We / They + DON'T + base verb",
                "He / She / It + DOESN'T + base verb",
                "CRITICAL RULE: Never put -s after doesn't! 'Doesn't' already has the 'es'!",
                "Example: 'She doesn't like milk.' (NOT: likes)"
            ),
            interactiveExamples = listOf(
                "I don't eat sour roots. 🥔",
                "They don't swim in cold weather. ❄️",
                "He doesn't climb dangerous cliffs. 🧗‍♂️",
                "The baby dino doesn't bite friends. 🦕"
            ),
            checkpointQuestions = listOf(
                GrammarQuestion(
                    id = 401,
                    zone = GrammarZone.FOREST,
                    skill = GrammarSkill.NEGATIVES_DONT_DOESNT,
                    storyContext = "Help fix the Grammar Monster's sentence!",
                    characterAvatar = "👾 Grammar Monster",
                    questionText = "Which sentence is 100% correct?",
                    options = listOf("He doesn't play.", "He doesn't plays.", "He don't play."),
                    correctOptionIndex = 0,
                    explanation = "We say 'He doesn't play.' Because after doesn't, the main verb MUST return to its base form!"
                ),
                GrammarQuestion(
                    id = 402,
                    zone = GrammarZone.FOREST,
                    skill = GrammarSkill.NEGATIVES_DONT_DOESNT,
                    storyContext = "Sara describes her friends' food preferences.",
                    characterAvatar = "👧 Sara",
                    questionText = "They ___ eat meat; they eat sweet berries.",
                    options = listOf("don't", "doesn't", "not"),
                    correctOptionIndex = 0,
                    explanation = "With THEY, we always use 'don't' ('They don't eat'). We use 'doesn't' only with he, she, or it!"
                )
            )
        ),
        ZoneLesson(
            zone = GrammarZone.RIVER,
            teacherIntro = "Listen to the gentle flowing Crystal River! 🌊\nFriendly Dino is waiting on the stepping stones.\nTo cross, we ask and answer with DO and DOES!",
            characterDialogue = "🦕 Dino asks: \"Do you like swimming in the river?\"\n👧 You answer: \"Yes, I do!\"\n🦕 Dino asks: \"Does Adam eat fish?\"\n👦 Sara answers: \"No, he doesn't. He eats berries!\"",
            keyRules = listOf(
                "Do + I / you / we / they + base verb?",
                "Does + he / she / it + base verb?",
                "Short Answers for DO: 'Yes, I do.' / 'No, I don't.'",
                "Short Answers for DOES: 'Yes, he does.' / 'No, he doesn't.'"
            ),
            interactiveExamples = listOf(
                "\"Do you like apples?\" -> \"Yes, I do!\" 🍎",
                "\"Does she sing songs?\" -> \"Yes, she does!\" 🎶",
                "\"Do they walk fast?\" -> \"No, they don't.\" 🐢",
                "\"Does the river freeze in summer?\" -> \"No, it doesn't.\" ☀️"
            ),
            checkpointQuestions = listOf(
                GrammarQuestion(
                    id = 501,
                    zone = GrammarZone.RIVER,
                    skill = GrammarSkill.QUESTIONS_DO_DOES,
                    storyContext = "Dino turns to you with a cheerful smile.",
                    characterAvatar = "🦕 Dino",
                    questionText = "\"___ you like the crystal river?\"",
                    options = listOf("Do", "Does", "Are"),
                    correctOptionIndex = 0,
                    explanation = "We ask 'Do you like...' because YOU takes the helper verb DO!"
                ),
                GrammarQuestion(
                    id = 502,
                    zone = GrammarZone.RIVER,
                    skill = GrammarSkill.SHORT_ANSWERS,
                    storyContext = "Adam asks about Sara's daily routine.",
                    characterAvatar = "👦 Adam",
                    questionText = "\"Does Sara read books in the cave?\"\nChoose the correct short answer:",
                    options = listOf("Yes, she does.", "Yes, she do.", "Yes, she reads."),
                    correctOptionIndex = 0,
                    explanation = "The question starts with 'Does Sara...', so the short answer is 'Yes, she does.'!"
                )
            )
        )
    )

    // Zone 6: The Great Grammar Challenge (20 comprehensive questions)
    val challengeQuestions: List<GrammarQuestion> = listOf(
        GrammarQuestion(
            id = 601,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.CONCEPT_ROUTINES,
            storyContext = "Daily Morning Habit in the Stone Age Village",
            characterAvatar = "👦 Adam",
            questionText = "Adam ___ his teeth with a clean pine twig every morning.",
            options = listOf("cleans", "clean", "cleaned"),
            correctOptionIndex = 0,
            explanation = "Adam is HE (singular), and this is his morning routine, so we say 'cleans'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 602,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.BASE_VERBS,
            storyContext = "Prehistoric Campfire Gathering",
            characterAvatar = "👧 Sara",
            questionText = "We ___ funny stories around the campfire every night.",
            options = listOf("tell", "tells", "telling"),
            correctOptionIndex = 0,
            explanation = "With WE, the verb remains in base form without -s: 'We tell'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 603,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.THIRD_PERSON_S,
            storyContext = "Cave Life Observations",
            characterAvatar = "⭐ Teacher Shaimaa",
            questionText = "Sara ___ very fast through the tall green grass.",
            options = listOf("runs", "run", "running"),
            correctOptionIndex = 0,
            explanation = "Sara is SHE, so we must add -S: 'Sara runs'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 604,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.NEGATIVES_DONT_DOESNT,
            storyContext = "Forest Monster Alert",
            characterAvatar = "👾 Grammar Monster",
            questionText = "Spot the correct sentence:",
            options = listOf("He doesn't play in the dark forest.", "He doesn't plays in the dark forest.", "He don't plays in the dark forest."),
            correctOptionIndex = 0,
            explanation = "After DOESN'T, the verb is always in base form: 'He doesn't play'!",
            questionType = QuestionType.ERROR_CORRECTION
        ),
        GrammarQuestion(
            id = 605,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.QUESTIONS_DO_DOES,
            storyContext = "River Dialogue with Dino",
            characterAvatar = "🦕 Dino",
            questionText = "\"___ your brother play with the wooden toy?\"",
            options = listOf("Does", "Do", "Is"),
            correctOptionIndex = 0,
            explanation = "'Your brother' is one boy (HE), so the question starts with 'Does'!",
            questionType = QuestionType.MULTIPLE_CHOICE
        ),
        GrammarQuestion(
            id = 606,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.SHORT_ANSWERS,
            storyContext = "Stepping Stone Challenge",
            characterAvatar = "👦 Adam",
            questionText = "\"Do they live in a stone house?\"\nChoose the correct short response:",
            options = listOf("Yes, they do.", "Yes, they does.", "Yes, they live."),
            correctOptionIndex = 0,
            explanation = "The question asks 'Do they...?', so the short answer is 'Yes, they do.'!",
            questionType = QuestionType.DIALOGUE_RESPONSE
        ),
        GrammarQuestion(
            id = 607,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.SPELLING_RULES,
            storyContext = "Cave Painting Action",
            characterAvatar = "⭐ Teacher Shaimaa",
            questionText = "Adam ___ his hands carefully after painting the cave wall.",
            options = listOf("washes", "washs", "wash"),
            correctOptionIndex = 0,
            explanation = "Because 'wash' ends with -sh, we add -ES: 'washes'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 608,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.SPELLING_RULES,
            storyContext = "Sky Watcher",
            characterAvatar = "👧 Sara",
            questionText = "The prehistoric bird ___ high above the mountain peak. (fly)",
            options = listOf("flies", "flys", "flyes"),
            correctOptionIndex = 0,
            explanation = "Consonant 'l' + 'y' changes to -IES: 'fly' becomes 'flies'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 609,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.FREQUENCY_ADVERBS,
            storyContext = "Stone Age Habits",
            characterAvatar = "👦 Adam",
            questionText = "Where does the word 'always' belong?",
            options = listOf("Dino always smiles at children.", "Dino smiles always at children.", "Dino smiles at always children."),
            correctOptionIndex = 0,
            explanation = "Frequency adverbs (always, usually, never) sit right before the main action verb: 'always smiles'!",
            questionType = QuestionType.MULTIPLE_CHOICE
        ),
        GrammarQuestion(
            id = 610,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.BASE_VERBS,
            storyContext = "Prehistoric Village Life",
            characterAvatar = "👧 Sara",
            questionText = "I ___ breakfast before sunrise every single day.",
            options = listOf("eat", "eats", "eating"),
            correctOptionIndex = 0,
            explanation = "With I, the verb stays in its base form: 'I eat'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 611,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.THIRD_PERSON_S,
            storyContext = "Caring for the Baby Dino",
            characterAvatar = "🦕 Dino",
            questionText = "The baby dinosaur ___ fresh green ferns.",
            options = listOf("likes", "like", "liking"),
            correctOptionIndex = 0,
            explanation = "The baby dinosaur is IT (singular), so we add -S: 'likes'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 612,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.NEGATIVES_DONT_DOESNT,
            storyContext = "Campfire Rules",
            characterAvatar = "⭐ Teacher Shaimaa",
            questionText = "Sara ___ drink dirty river water.",
            options = listOf("doesn't", "don't", "not"),
            correctOptionIndex = 0,
            explanation = "Sara is SHE, so we use 'doesn't' + base verb: 'doesn't drink'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 613,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.QUESTIONS_DO_DOES,
            storyContext = "Exploration Dialogue",
            characterAvatar = "👦 Adam",
            questionText = "\"___ dinosaurs sleep during the day?\"",
            options = listOf("Do", "Does", "Are"),
            correctOptionIndex = 0,
            explanation = "'Dinosaurs' is plural (THEY), so we use 'Do'!",
            questionType = QuestionType.MULTIPLE_CHOICE
        ),
        GrammarQuestion(
            id = 614,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.SHORT_ANSWERS,
            storyContext = "Cave Discovery",
            characterAvatar = "👧 Sara",
            questionText = "\"Does the campfire keep us warm?\"\nChoose the correct short answer:",
            options = listOf("Yes, it does.", "Yes, it do.", "Yes, it is."),
            correctOptionIndex = 0,
            explanation = "The question asks 'Does the campfire (it)...?', so we answer 'Yes, it does.'!",
            questionType = QuestionType.DIALOGUE_RESPONSE
        ),
        GrammarQuestion(
            id = 615,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.CONCEPT_ROUTINES,
            storyContext = "Universal Truth & Facts",
            characterAvatar = "⭐ Teacher Shaimaa",
            questionText = "True or False:\n\"Water boils when it gets very hot.\"",
            options = listOf("True (It is a general scientific fact)", "False (We never use Present Simple for facts)"),
            correctOptionIndex = 0,
            explanation = "True! We use the Present Simple for facts and universal truths.",
            questionType = QuestionType.TRUE_FALSE
        ),
        GrammarQuestion(
            id = 616,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.BASE_VERBS,
            storyContext = "True or False Grammar Rule",
            characterAvatar = "⭐ Teacher Shaimaa",
            questionText = "True or False:\n\"We add -S to the verb when the subject is THEY.\"",
            options = listOf("False (THEY takes the base verb without -s)", "True (THEY always takes -s)"),
            correctOptionIndex = 0,
            explanation = "False! With THEY (and I, You, We), the verb stays base form without -S: 'They play', NOT 'They plays'!",
            questionType = QuestionType.TRUE_FALSE
        ),
        GrammarQuestion(
            id = 617,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.SPELLING_RULES,
            storyContext = "Village Routine",
            characterAvatar = "👦 Adam",
            questionText = "Adam ___ to the village school every morning. (go)",
            options = listOf("goes", "gos", "go"),
            correctOptionIndex = 0,
            explanation = "'Go' ends in the vowel 'o', so we add -ES: 'goes'!",
            questionType = QuestionType.SENTENCE_COMPLETION
        ),
        GrammarQuestion(
            id = 618,
            zone = GrammarZone.CHALLENGE,
            skill = GrammarSkill.FREQUENCY_ADVERBS,
            storyContext = "Safe Forest Exploration",
            characterAvatar = "👧 Sara",
            questionText = "We ___ touch wild thorns in the deep forest.",
            options = listOf("never", "always", "sometimes"),
            correctOptionIndex = 0,
            explanation = "'Never' describes an action that happens 0% of the time: 'We never touch wild thorns' keeps us safe!",
            questionType = QuestionType.MULTIPLE_CHOICE
        )
    )

    // Remediation Tasks for Weak Students
    val sentenceBuilderTasks = listOf(
        SentenceBuildTask(
            targetSentence = "Adam plays every day",
            scrambledWords = listOf("every", "plays", "day", "Adam"),
            skill = GrammarSkill.THIRD_PERSON_S,
            hint = "Start with the person: Adam!"
        ),
        SentenceBuildTask(
            targetSentence = "He doesn't play inside",
            scrambledWords = listOf("doesn't", "inside", "He", "play"),
            skill = GrammarSkill.NEGATIVES_DONT_DOESNT,
            hint = "Remember: He + doesn't + base verb!"
        ),
        SentenceBuildTask(
            targetSentence = "Do they eat berries",
            scrambledWords = listOf("berries", "Do", "eat", "they"),
            skill = GrammarSkill.QUESTIONS_DO_DOES,
            hint = "Questions with 'they' start with Do!"
        ),
        SentenceBuildTask(
            targetSentence = "Sara watches the owls",
            scrambledWords = listOf("watches", "Sara", "owls", "the"),
            skill = GrammarSkill.SPELLING_RULES,
            hint = "Watch ends in -ch, so add -es!"
        )
    )

    val campfireTasks = listOf(
        MiniGameQuestion(
            prompt = "Quick! Feed the flame with the right verb:\nSara ___ by the fire.",
            options = listOf("sits", "sit", "sitting"),
            correctIndex = 0,
            explanation = "Sara is SHE -> 'sits'!"
        ),
        MiniGameQuestion(
            prompt = "Feed the flame!\nWe ___ warm herbal tea.",
            options = listOf("drink", "drinks", "drinking"),
            correctIndex = 0,
            explanation = "Team WE -> base verb 'drink'!"
        ),
        MiniGameQuestion(
            prompt = "Don't let the fire dim!\nHe ___ warm logs.",
            options = listOf("carries", "carrys", "carry"),
            correctIndex = 0,
            explanation = "Carry changes y to -ies -> 'carries'!"
        )
    )

    val dinoFoodTasks = listOf(
        MiniGameQuestion(
            prompt = "Which sentence treat can Baby Dino safely eat?",
            options = listOf("The baby dino sleeps softly.", "The baby dino sleep softly.", "The baby dino is sleep softly."),
            correctIndex = 0,
            explanation = "Yum! 'The baby dino sleeps' is 100% grammatically correct!"
        ),
        MiniGameQuestion(
            prompt = "Dino wants a negative sentence snack:",
            options = listOf("Dino doesn't bite friends.", "Dino doesn't bites friends.", "Dino don't bite friends."),
            correctIndex = 0,
            explanation = "Delicious! 'doesn't bite' has the base verb after doesn't!"
        ),
        MiniGameQuestion(
            prompt = "Dino asks for a question treat:",
            options = listOf("Does Dino like sweet berries?", "Do Dino like sweet berries?", "Does Dino likes sweet berries?"),
            correctIndex = 0,
            explanation = "Perfection! 'Does Dino like...?' is the correct question form!"
        )
    )
}
