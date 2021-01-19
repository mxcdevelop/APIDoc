package com.mxc.contract.demo.request.identified;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SubmitOrderReq extends OrderReqDTO {

}
