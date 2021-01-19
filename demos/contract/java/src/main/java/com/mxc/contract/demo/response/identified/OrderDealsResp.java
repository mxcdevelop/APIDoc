package com.mxc.contract.demo.response.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderDealsResp extends PageRespDTO {
    private List<DealDetailDTO> resultList;
}
