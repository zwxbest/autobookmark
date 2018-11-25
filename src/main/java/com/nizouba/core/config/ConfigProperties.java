package com.nizouba.core.config;

import com.nizouba.core.BodySizeMode;
import com.nizouba.core.ExtractRule;
import com.nizouba.core.LevelMode;
import java.io.File;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangweixiao
 */
@Getter
@Setter
public class ConfigProperties {
    public File pdfFile;
    public BodySizeMode bodySizeMode;
    public LevelMode levelMode;
    public ExtractRule extractRule;
    public  int compareSelect;
}
