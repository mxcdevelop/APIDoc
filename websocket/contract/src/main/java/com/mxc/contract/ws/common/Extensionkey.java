package com.mxc.contract.ws.common;

import com.mxc.contract.ws.event.MessageEvent;
import org.apache.commons.lang3.EnumUtils;

import java.util.Optional;

/**
 * key for {@link MessageEvent#extensionOf(Extensionkey)}
 */
public enum Extensionkey {
    SYMBOL("symbol");
    String key;

    Extensionkey(String key) {
        this.key = key;
    }

    public static Optional<Extensionkey> of(String key) {
        return EnumUtils.getEnumList(Extensionkey.class)
                .stream()
                .filter(type -> type.key.equals(key))
                .findFirst();

    }

}
