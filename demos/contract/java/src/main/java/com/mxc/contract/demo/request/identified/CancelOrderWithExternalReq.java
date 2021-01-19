package com.mxc.contract.demo.request.identified;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CancelOrderWithExternalReq {
    private String symbol;
    private String externalOid;
}
