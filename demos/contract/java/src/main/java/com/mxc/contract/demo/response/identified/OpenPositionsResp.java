package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OpenPositionsResp extends PositionDTO {
}
