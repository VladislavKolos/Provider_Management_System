package org.example.controller

import jakarta.validation.constraints.NotNull
import org.example.dto.responsedto.PlanResponseDto
import org.example.mapper.extension.toResponseDto
import org.example.mapper.extension.toResponseDtoPage
import org.example.service.PlanService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Validated
@RestController
@RequestMapping("/api/plans")
class PlanController(

    private val planService: PlanService

) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/{id}")
    fun getPlanById(@NotNull @PathVariable id: UUID): ResponseEntity<PlanResponseDto> {

        val response = planService.getPlanById(id).toResponseDto()
        logger.info("Request received to get plan details for Client with ID: $id")

        return ResponseEntity.ok(response)
    }

    @GetMapping()
    fun getAllPlans(
        @PageableDefault(
            sort = ["name"],
            direction = Sort.Direction.ASC,
            value = 5
        ) pageable: Pageable
    ): ResponseEntity<Page<PlanResponseDto>> {

        val response = planService.getAllPlans(pageable).toResponseDtoPage()
        logger.info(
            "Fetching plans for Client. Page: {}, Size: {}, Sort: {}",
            pageable.pageNumber, pageable.pageSize, pageable.sort
        )

        return ResponseEntity.ok(response)
    }
}