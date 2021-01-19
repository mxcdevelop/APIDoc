package com.mxc.contract.demo.response;

import lombok.Data;

@Data
public class PageRespDTO {
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;
    private Integer currentPage;
}
