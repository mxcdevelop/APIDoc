package com.mxc.contract.demo.response.identified;

import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HistoryOrdersResp extends PageRespDTO {
    private List<OrderRespDTO> resultList;
}
