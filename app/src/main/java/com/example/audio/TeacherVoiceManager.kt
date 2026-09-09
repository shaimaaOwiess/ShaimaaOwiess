package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.sin

class TeacherVoiceManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    val isMuted = MutableStateFlow(false)

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            configureFemaleEnglishVoice()
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                }

                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                }
            })
        }
    }

    private fun configureFemaleEnglishVoice() {
        tts?.let { engine ->
            // Try UK English first for refined teacher tone, fallback to US English
            val ukLocale = Locale.UK
            val usLocale = Locale.US

            val langResult = engine.setLanguage(ukLocale)
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                engine.setLanguage(usLocale)
            }

            // Find a female voice from available engine voices
            try {
                val voices = engine.voices
                if (voices != null) {
                    val femaleVoice = voices.firstOrNull { voice ->
                        val name = voice.name.lowercase()
                        (voice.locale.language == "en") &&
                                (name.contains("female") || name.contains("en-gb-x") || name.contains("en-us-x") || name.contains("woman"))
                    } ?: voices.firstOrNull { it.locale.language == "en" }

                    if (femaleVoice != null) {
                        engine.voice = femaleVoice
                    }
                }
            } catch (_: Exception) {
                // Ignore and use default configured language
            }

            // Friendly, warm female pitch & comfortable child-friendly pace
            engine.setPitch(1.15f)
            engine.setSpeechRate(0.88f)
        }
    }

    fun speakTeacher(text: String, isExcited: Boolean = false, isGentleSupport: Boolean = false) {
        if (isMuted.value || !isTtsReady) return

        tts?.let { engine ->
            val cleanText = text
                .replace("— Shaimaa Owiess", "From your teacher, Shaimaa Owiess")
                .replace("💜", "")
                .replace("⭐", "")
                .replace("✨", "")
                .replace("🪨", "")
                .replace("🦕", "")
                .replace("🔥", "")
                .replace("🌸", "")
                .replace("🌷", "")
                .replace("🧠", "")
                .replace("🥳", "")
                .replace("🎉", "")
                .replace("•", ". ")

            when {
                isExcited -> {
                    engine.setPitch(1.22f)
                    engine.setSpeechRate(0.95f)
                }
                isGentleSupport -> {
                    engine.setPitch(1.08f)
                    engine.setSpeechRate(0.84f)
                }
                else -> {
                    engine.setPitch(1.15f)
                    engine.setSpeechRate(0.88f)
                }
            }

            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "teacher_${System.currentTimeMillis()}")
            engine.speak(cleanText, TextToSpeech.QUEUE_FLUSH, params, "teacher_${System.currentTimeMillis()}")
        }
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun toggleMute(): Boolean {
        val next = !isMuted.value
        isMuted.value = next
        if (next) {
            stop()
        }
        return next
    }

    // Synthesized Sound Effects (zero external audio files needed, immediate & offline!)
    fun playSuccessChime() {
        if (isMuted.value) return
        CoroutineScope(Dispatchers.Default).launch {
            try {
                // Arpeggio C5 (523Hz), E5 (659Hz), G5 (784Hz), C6 (1046Hz)
                val sampleRate = 22050
                val totalDurationSec = 0.55
                val numSamples = (sampleRate * totalDurationSec).toInt()
                val samples = ShortArray(numSamples)

                val notes = listOf(523.25, 659.25, 783.99, 1046.50)
                val noteDuration = numSamples / notes.size

                for (nIdx in notes.indices) {
                    val freq = notes[nIdx]
                    val start = nIdx * noteDuration
                    val end = if (nIdx == notes.lastIndex) numSamples else (nIdx + 1) * noteDuration
                    for (i in start until end) {
                        val t = (i - start).toDouble() / sampleRate
                        val decay = 1.0 - ((i - start).toDouble() / (end - start))
                        val wave = sin(2.0 * Math.PI * freq * t)
                        samples[i] = (wave * 28000 * decay).toInt().toShort()
                    }
                }
                playPcm(samples, sampleRate)
            } catch (_: Exception) {}
        }
    }

    fun playGentleSupportChime() {
        if (isMuted.value) return
        CoroutineScope(Dispatchers.Default).launch {
            try {
                // Soft warm chord E4 (329.63Hz) and G4 (392.00Hz)
                val sampleRate = 22050
                val totalDurationSec = 0.45
                val numSamples = (sampleRate * totalDurationSec).toInt()
                val samples = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    val envelope = 1.0 - (i.toDouble() / numSamples)
                    val wave = 0.5 * sin(2.0 * Math.PI * 329.63 * t) + 0.5 * sin(2.0 * Math.PI * 392.00 * t)
                    samples[i] = (wave * 20000 * envelope).toInt().toShort()
                }
                playPcm(samples, sampleRate)
            } catch (_: Exception) {}
        }
    }

    fun playStoneTap() {
        if (isMuted.value) return
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val sampleRate = 22050
                val numSamples = (sampleRate * 0.08).toInt()
                val samples = ShortArray(numSamples)
                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    val decay = 1.0 - (i.toDouble() / numSamples)
                    val wave = sin(2.0 * Math.PI * 220.0 * t)
                    samples[i] = (wave * 24000 * decay).toInt().toShort()
                }
                playPcm(samples, sampleRate)
            } catch (_: Exception) {}
        }
    }

    private fun playPcm(samples: ShortArray, sampleRate: Int) {
        val minBufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val track = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(minBufferSize.coerceAtLeast(samples.size * 2))
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        track.write(samples, 0, samples.size)
        track.play()
        track.release()
    }

    fun destroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
