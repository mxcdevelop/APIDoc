package com.mxc.contract.demo.response.identified;

import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OpenOrdersResp extends PageRespDTO {
    private List<OrderRespDTO> resultList;
}
