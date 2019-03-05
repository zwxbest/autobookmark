package com.nizouba.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangweixiao
 */
@Getter
@Setter
public class BodySizeMode {

    private float bodySize;

    private BodySizeEnum bodySizeEnum;

    @Getter
    @AllArgsConstructor
    public enum  BodySizeEnum{

        /**
         *
         */
        AI(0,"智能检测"),
        CUSTOM(1,"自定义");

        private int index;
        private String desc;
    }
}
