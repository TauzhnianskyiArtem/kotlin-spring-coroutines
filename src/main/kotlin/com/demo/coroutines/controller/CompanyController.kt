package com.demo.coroutines.controller

import com.demo.coroutines.dto.CompanyRequest
import com.demo.coroutines.dto.CompanyResponse
import com.demo.coroutines.extension.toModel
import com.demo.coroutines.service.CompanyService
import com.demo.coroutines.service.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/companies")
class CompanyController(
    private val companyService: CompanyService,
    private val userService: UserService
) {

    @PostMapping
    suspend fun createUser(@RequestBody companyRequest: CompanyRequest): CompanyResponse {
        return companyService.saveCompany(companyRequest.toModel()).toResponse()
    }

    @GetMapping
    suspend fun findAllCompanies(
        @RequestParam("name", required = false) name: String?
    ): Flow<CompanyResponse> {
        val companies = name?.let {
            companyService.findByNameLike(it)
        } ?: companyService.findAll()
        return companies.map { it.toResponse() }
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable("id") id: Long): CompanyResponse {
        return companyService.findById(id).toResponse(
            userService.findByCompanyId(id).toList()
        )
    }

    @PutMapping("/{id}")
    suspend fun updateById(
        @PathVariable("id") id: Long,
        @RequestBody companyRequest: CompanyRequest
    ) {
        companyService.updateById(id, companyRequest.toModel())
    }

    @DeleteMapping("/{id}")
    suspend fun deleteByID(@PathVariable("id") id: Long) {
        companyService.deleteById(id)
    }

}