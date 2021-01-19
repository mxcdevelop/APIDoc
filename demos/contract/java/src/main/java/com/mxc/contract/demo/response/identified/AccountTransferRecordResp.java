package com.mxc.contract.demo.response.identified;

import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class AccountTransferRecordResp extends PageRespDTO {

    private List<TransferRecordDTO> resultList;

    @NoArgsConstructor
    @Data
    public static class TransferRecordDTO {
        private Integer id;

        //流水号
        private String txid;
        private String currency;

        //转账金额
        private BigDecimal amount;

        //类型:IN 、OUT
        private String type;

        //状态:WAIT 、SUCCESS 、FAILED
        private String state;
        private Long createTime;
        private Long updateTime;
    }
}
