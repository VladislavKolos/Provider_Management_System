package org.example.controller

import jakarta.validation.constraints.NotNull
import org.example.dto.responsedto.TariffResponseDto
import org.example.mapper.extension.toResponseDto
import org.example.mapper.extension.toResponseDtoPage
import org.example.service.TariffService
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
@RequestMapping("/api/tariffs")
class TariffController(

    private val tariffService: TariffService

) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/{id}")
    fun getTariffById(@NotNull @PathVariable id: UUID): ResponseEntity<TariffResponseDto> {

        val response = tariffService.getTariffById(id).toResponseDto()
        logger.info("Request received to get tariff details for Client with ID: $id")

        return ResponseEntity.ok(response)
    }

    @GetMapping()
    fun getAllTariffs(
        @PageableDefault(
            sort = ["name"],
            direction = Sort.Direction.ASC,
            value = 5
        ) pageable: Pageable
    ): ResponseEntity<Page<TariffResponseDto>> {

        val response = tariffService.getAllTariffs(pageable).toResponseDtoPage()
        logger.info(
            "Fetching tariffs for Client. Page: {}, Size: {}, Sort: {}",
            pageable.pageNumber, pageable.pageSize, pageable.sort
        )

        return ResponseEntity.ok(response)
    }
}