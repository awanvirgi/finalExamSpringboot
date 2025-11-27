package com.finalexam.trabea.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private PaginationResponse pagination;

    public PageResponse(Page<T> page){
        PaginationResponse response = new PaginationResponse();
        response.setPage(page.getTotalPages());
        response.setSize(page.getSize());
        response.setTotalData(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        this.content = page.getContent();
        this.pagination = response;
    }
}
