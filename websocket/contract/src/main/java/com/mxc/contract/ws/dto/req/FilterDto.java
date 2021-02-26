package com.mxc.contract.ws.dto.req;

import com.mxc.contract.ws.common.FilterEventEnum;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
public class FilterDto {
    /**
     * {@link FilterEventEnum#getValue()}
     */
    private String filter;
    /**
     * the set of contract names
     */
    private Set<String> rules;

    public FilterDto(FilterEventEnum filterType, Set<String> rules) {
        this.filter = filterType.getValue();
        this.rules = rules;
    }
}
