package com.finalexam.trabea.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
    private Integer size;
    private Integer page;
    private Long totalData;
    private Integer totalPage;
}
