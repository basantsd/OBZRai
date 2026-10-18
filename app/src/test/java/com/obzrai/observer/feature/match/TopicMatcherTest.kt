package com.obzrai.observer.feature.match

import com.obzrai.observer.core.model.MatchSensitivity
import com.obzrai.observer.core.storage.entity.InterestTopicEntity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TopicMatcherTest {
    private val matcher = TopicMatcher()

    @Test
    fun matchesKeywordInOcr() {
        val topics =
            listOf(
                InterestTopicEntity(id = "gst", name = "GST Tally tax", enabled = true),
            )
        val result =
            matcher.match(
                ocrText = "File GST return in Tally",
                title = "Screen",
                topics = topics,
                sensitivity = MatchSensitivity.BALANCED,
            )
        assertTrue(result.matched)
        assertTrue(result.matchedTopicIds.contains("gst"))
    }

    @Test
    fun noTopics_doesNotAutoMatch() {
        val result =
            matcher.match(
                ocrText = "anything",
                title = "x",
                topics = emptyList(),
            )
        assertFalse(result.matched)
    }
}
