package com.demoss.newsline.data.remote.api.requests

import com.demoss.newsline.data.remote.api.NewsApiConstants
import com.demoss.newsline.data.remote.api.getBaseRequest

class NewsApiTopNewsRequestBuilder {

    var page: Int? = null
    var query: String? = null
    var sources: String? = null
    var category: String? = null
    var country: String? = null

    fun build(): Map<String, String?> = getBaseRequest() + mapOf<String, String?>(
        NewsApiConstants.PAGE to page.toString(),
        NewsApiConstants.QUERY to query,
        NewsApiConstants.SOURCES to sources, // source preferred
        NewsApiConstants.CATEGORY to category,
        NewsApiConstants.COUNTRY to country.takeIf { sources == null }
    )
}