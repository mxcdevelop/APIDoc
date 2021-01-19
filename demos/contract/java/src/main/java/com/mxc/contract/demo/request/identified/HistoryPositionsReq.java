package com.mxc.contract.demo.request.identified;

import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class HistoryPositionsReq extends PageReqDTO {
    @Nullable
    private String symbol;

    //仓位类型， 1多 2空
    @Nullable
    private Integer type;
}
