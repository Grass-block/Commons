package me.gb2022.commons.math.hitting;

import me.gb2022.commons.math.AABB;

public final class HitBox extends AABB {
    private final String name;

    public HitBox(String name,AABB aabb) {
        super(aabb);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
