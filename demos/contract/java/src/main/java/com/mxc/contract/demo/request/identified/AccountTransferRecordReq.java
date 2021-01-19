package com.mxc.contract.demo.request.identified;

import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AccountTransferRecordReq extends PageReqDTO {
    @Nullable
    private String currency;

    //状态:WAIT 、SUCCESS 、FAILED
    @Nullable
    private String state;

    //类型:IN 、OUT
    @Nullable
    private String type;

}
