package com.example.android_05_news_app.domain.model

object CategoriesApiKey {
    const val GENERAL_API_KEY = "general"
    const val BUSINESS_API_KEY = "business"
    const val ENTERTAINMENT_API_KEY = "entertainment"
    const val HEALTH_API_KEY = "health"
    const val SCIENCE_API_KEY = "science"
    const val SPORTS_API_KEY = "sports"
    const val TECHNOLOGY_API_KEY = "technology"
}


enum class NewsCategories(val value: String) {
    GENERAL(CategoriesApiKey.GENERAL_API_KEY),
    BUSINESS(CategoriesApiKey.BUSINESS_API_KEY),
    ENTERTAINMENT(CategoriesApiKey.ENTERTAINMENT_API_KEY),
    HEALTH(CategoriesApiKey.HEALTH_API_KEY),
    SCIENCE(CategoriesApiKey.SCIENCE_API_KEY),
    SPORTS(CategoriesApiKey.SPORTS_API_KEY),
    TECHNOLOGY(CategoriesApiKey.TECHNOLOGY_API_KEY),
}