package com.oxipro.bedwars.ratio.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BedwarsMode {
    BEDWARS("BedWars2023"), PROXY("BWProxy2023");

    private final String name;

    public String getPath() {
        return "plugins/" + name + "/Addons/Ratio";
    }
}
