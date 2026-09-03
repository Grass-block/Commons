package me.gb2022.commons.container;

public record Vector3<V>(V x, V y, V z) {
    public Vector3(V x, V y, V z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public V x() {
        return this.x;
    }

    public V y() {
        return this.y;
    }

    public V z() {
        return this.z;
    }
}
