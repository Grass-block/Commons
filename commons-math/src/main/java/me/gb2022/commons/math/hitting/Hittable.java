package me.gb2022.commons.math.hitting;

import java.util.Collection;

public interface Hittable {
    Collection<HitBox> getHitBox();
}
