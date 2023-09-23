package com.example.msdealer.dto.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class PageSizeDto {
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    private int pageNumber = 0;

    @Min(value = 1, message = "Page size must be greater than or equal to 1")
    @Max(value = 100, message = "Page size must be less than or equal to 100")
    private int pageSize = 10;

    @NotNull(message = "Sort direction cannot be null")
    private Sort.Direction sortDirection = Sort.Direction.ASC;

    @Pattern(regexp = "^(creationDate|anotherAttribute)$", message = "Invalid sortBy value")
    private String sortBy = "creationDate";

    public PageSizeDto(int pageNumber, int pageSize, Sort.Direction sortDirection, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
