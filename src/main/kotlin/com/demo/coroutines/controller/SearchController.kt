package com.demo.coroutines.controller

import com.demo.coroutines.dto.IdNameTypeResponse
import com.demo.coroutines.service.SearchingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlinx.coroutines.flow.Flow

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    private val searchingService: SearchingService
) {

    @GetMapping
    suspend fun searchByName(@RequestParam("name", required = false) name: String?): Flow<IdNameTypeResponse> {
        return searchingService.search(name)
    }

    /**
     * /sync and /async endpoints are
     * for testing and comparison purposes
     * between synchronous and asynchronous execution
     * using Kotlin Coroutines
     * */
    @GetMapping("/sync")
    suspend fun delayedSearchByName(@RequestParam("name", required = false) name: String?): Flow<IdNameTypeResponse> {
        return searchingService.delayedSyncSearch(name)
    }

    @GetMapping("/async")
    suspend fun asyncDelayedSearchByName(@RequestParam("name", required = false) name: String?): Flow<IdNameTypeResponse> {
        return searchingService.delayedASyncSearch(name)
    }

}