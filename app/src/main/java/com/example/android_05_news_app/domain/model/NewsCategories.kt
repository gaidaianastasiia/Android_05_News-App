package com.example.android_05_news_app.domain.model

private const val GENERAL_API_KEY = "general"
private const val BUSINESS_API_KEY = "business"
private const val ENTERTAINMENT_API_KEY = "entertainment"
private const val HEALTH_API_KEY = "health"
private const val SCIENCE_API_KEY = "science"
private const val SPORTS_API_KEY = "sports"
private const val TECHNOLOGY_API_KEY = "technology"

enum class NewsCategories(val categoryApiKey: String) {
    GENERAL(GENERAL_API_KEY),
    BUSINESS(BUSINESS_API_KEY),
    ENTERTAINMENT(ENTERTAINMENT_API_KEY),
    HEALTH(HEALTH_API_KEY),
    SCIENCE(SCIENCE_API_KEY),
    SPORTS(SPORTS_API_KEY),
    TECHNOLOGY(TECHNOLOGY_API_KEY),
}