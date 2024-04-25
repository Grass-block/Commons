package me.gb2022.commons.math.hitting;

import java.util.Comparator;

public final class ResultComparator implements Comparator<HitResult> {
    private final org.joml.Vector3d viewPos;

    public ResultComparator(org.joml.Vector3d viewPos) {
        this.viewPos = viewPos;
    }

    @Override
    public int compare(HitResult o1, HitResult o2) {
        if (o1 == o2 && o1 == null) {
            return 0;
        }

        if (o1 == null) {
            return -1;
        }

        if (o2 == null) {
            return 1;
        }

        if (o1.equals(o2)) {
            return 0;
        }

        return o1.distanceTo(viewPos) > o2.distanceTo(viewPos) ? 1 : -1;
    }
}
