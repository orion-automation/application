package com.eorion.bo.enhancement.application.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;

public class TypeEnumsPackageScanner implements MybatisPlusPropertiesCustomizer {
    @Override
    public void customize(MybatisPlusProperties properties) {
        properties.setMapperLocations(new String[]{"classpath:application-mapper/*.xml"});
    }
}