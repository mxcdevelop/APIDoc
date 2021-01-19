package com.mxc.contract.demo.request.open;

import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;

@Data
public class ContractHistoryReq extends PageReqDTO {
    private String symbol;
}
