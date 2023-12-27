package de.muenchen.mobidam.domain.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum SchnittstellenStatus {
    ACTIVATED("aktiviert"),
    DEACTIVATED("deaktiviert");

    private final String text;
}
