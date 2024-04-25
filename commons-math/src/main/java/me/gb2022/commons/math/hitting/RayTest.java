package me.gb2022.commons.math.hitting;

import me.gb2022.commons.math.AABB;
import me.gb2022.commons.math.LinearInterpolation;
import me.gb2022.commons.math.MathHelper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * raw and aabb intersection
 * <p>I can`t explain how it works,but it does written by me.</p>
 *
 * @author GrassBlock2022
 */
public interface RayTest {
    double STEP = 0.002;

    static byte getIntersectionFacing(AABB aabb, org.joml.Vector3d from, org.joml.Vector3d destination) {
        org.joml.Vector3d hitPos = test(from, destination, aabb);
        if (hitPos != null) {
            if (hitPos.y >= aabb.y1 && aabb.positionInBoundXZ(hitPos.x, hitPos.z)) {
                return 0;
            }
            if (hitPos.y <= aabb.y0 && aabb.positionInBoundXZ(hitPos.x, hitPos.z)) {
                return 1;
            }
            if (hitPos.z >= aabb.z1 && aabb.positionInBoundXY(hitPos.x, hitPos.y)) {
                return 2;
            }
            if (hitPos.z <= aabb.z0 && aabb.positionInBoundXY(hitPos.x, hitPos.y)) {
                return 3;
            }
            if (hitPos.x >= aabb.x1 && aabb.positionInBoundYZ(hitPos.y, hitPos.z)) {
                return 4;
            }
            if (hitPos.x <= aabb.x0 && aabb.positionInBoundYZ(hitPos.y, hitPos.z)) {
                return 5;
            }

        }
        return -1;
    }

    static HitResult trace(Collection<Hittable> objects, org.joml.Vector3d from, org.joml.Vector3d dest) {
        var results = new ArrayList<HitResult>();
        for (Hittable obj : objects) {
            if (obj == null) {
                continue;
            }
            Collection<HitBox> boxes = obj.getHitBox();
            for (HitBox box : boxes) {
                org.joml.Vector3d vec = test(from, dest, box);
                if (vec == null) {
                    continue;
                }
                results.add(new HitResult(obj, box, getIntersectionFacing(box, from, dest)));
            }
        }

        if (results.isEmpty()) {
            return null;
        }

        results.sort(new ResultComparator(from));
        return results.get(0);
    }

    static org.joml.Vector3d test(org.joml.Vector3d from, org.joml.Vector3d dest, AABB aabb) {
        final double distMin = aabb.distanceMin(from),//min dist to aabb
                distMax = aabb.distanceMax(from),//max dist to aabb
                distAll = MathHelper.dist(from, dest);//dist from a to b

        if (distMin > distAll) {
            return null;
        }

        //do sample here.
        for (
                double sampleDist = distMin - aabb.getMaxWidth() / 1.414/*pow(2)*/;
                sampleDist < distMax + aabb.getMaxWidth() / 1.414;
                sampleDist += STEP
        ) {
            double t = sampleDist / distAll;
            double t1 = (sampleDist + STEP) / distAll;
            if (aabb.isVectorInside(LinearInterpolation.do3(from, dest, t1))) {
                //collided...return position
                return LinearInterpolation.do3(from, dest, t);
            }
        }
        //no search result...return null :(
        return null;
    }

}
