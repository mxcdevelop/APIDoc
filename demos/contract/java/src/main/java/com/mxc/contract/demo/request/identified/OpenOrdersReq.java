package com.mxc.contract.demo.request.identified;

import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.annotation.Nullable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OpenOrdersReq extends PageReqDTO {
    @Nullable
    private String symbol;
}
