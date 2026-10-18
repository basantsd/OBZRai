package com.obzrai.observer.feature.match

import com.obzrai.observer.core.model.MatchSensitivity
import com.obzrai.observer.core.storage.entity.InterestTopicEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FR-009 — lightweight keyword topic classifier (precision target validated later on 200-item set).
 */
@Singleton
class TopicMatcher @Inject constructor() {
    data class MatchResult(
        val matchedTopicIds: List<String>,
        val matched: Boolean,
        val score: Double,
    )

    fun match(
        ocrText: String,
        title: String,
        topics: List<InterestTopicEntity>,
        sensitivity: MatchSensitivity = MatchSensitivity.BALANCED,
    ): MatchResult {
        val enabled = topics.filter { it.enabled }
        if (enabled.isEmpty()) {
            // No topics → allow manual saves; auto-save suppressed.
            return MatchResult(emptyList(), matched = false, score = 0.0)
        }
        val hay = (ocrText + "\n" + title).lowercase()
        val hits = mutableListOf<Pair<String, Double>>()
        for (topic in enabled) {
            val keys =
                topic.name
                    .lowercase()
                    .split(Regex("[\\s,;/|]+"))
                    .map { it.trim() }
                    .filter { it.length >= 2 }
            var score = 0.0
            for (k in keys) {
                if (hay.contains(k)) score += 1.0
            }
            if (score > 0) hits += topic.id to score
        }
        val threshold =
            when (sensitivity) {
                MatchSensitivity.CONSERVATIVE -> 2.0
                MatchSensitivity.BALANCED -> 1.0
                MatchSensitivity.BROAD -> 1.0
            }
        val matchedIds = hits.filter { it.second >= threshold }.map { it.first }
        val best = hits.maxOfOrNull { it.second } ?: 0.0
        return MatchResult(
            matchedTopicIds = matchedIds,
            matched = matchedIds.isNotEmpty(),
            score = best,
        )
    }
}
