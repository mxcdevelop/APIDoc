package com.mxc.contract.ws.event;

import com.mxc.contract.ws.common.Channel;
import com.mxc.contract.ws.common.Extensionkey;
import com.mxc.contract.ws.dto.message.Dto;
import lombok.Data;

import java.io.Serializable;
import java.util.EnumMap;

@Data
public class MessageEvent implements Serializable {
    /**
     * wrapped data from channel
     */
    private Dto data;
    private Channel type;
    /**
     * timestamp
     */
    private long ts;
    /**
     * extension data for some channels
     */
    private EnumMap<Extensionkey, Object> extension = new EnumMap(Extensionkey.class);

    public <DTO extends Dto> DTO getData() {
        return (DTO) data;
    }

    public void putExtension(Extensionkey key, Object value) {
        extension.put(key, value);
    }

    public <T> T extensionOf(Extensionkey key) {
        return (T) extension.get(key);
    }
}
