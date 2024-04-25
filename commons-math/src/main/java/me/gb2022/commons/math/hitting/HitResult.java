package me.gb2022.commons.math.hitting;

public final class HitResult {
    private final Hittable obj;
    private final HitBox box;
    private final int face;

    public HitResult(Hittable obj, HitBox box, int face) {
        this.face = face;
        this.obj = obj;
        this.box = box;
    }

    public double distanceTo(org.joml.Vector3d vec) {
        return box.distanceMin(vec);
    }

    public int getHitFace() {
        return face;
    }

    public HitBox getHitBox() {
        return box;
    }

    public <T extends Hittable> T getObject(Class<T> type) {
        return type.cast(this.obj);
    }

    public boolean instanceOf(Class<? extends Hittable> type) {
        return type.isInstance(this.obj);
    }
}
