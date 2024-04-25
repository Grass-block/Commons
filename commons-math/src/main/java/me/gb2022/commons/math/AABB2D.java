package me.gb2022.commons.math;

/**
 * simple axis-aligned bounding box 2d
 * <p>modified from{@link AABB}</p>
 *
 * @author GrassBlock2022
 */
public class AABB2D {
    private double x0;
    private double y0;
    private double x1;
    private double y1;

    public AABB2D(double x0, double y0, double x1, double y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    public AABB2D(AABB2D aabb) {
        this.x0 = aabb.x0;
        this.y0 = aabb.y0;
        this.x1 = aabb.x1;
        this.y1 = aabb.y1;
    }

    /**
     * check if this intersects another box.
     *
     * @param c another box
     * @return intersection
     */
    public boolean intersect(AABB2D c) {
        if (c.x1 <= this.x0 || c.x0 >= this.x1) {
            return false;
        }
        return !(c.y1 <= this.y0 || c.y0 >= this.y1);
    }

    /**
     * move
     *
     * @param x x offset
     * @param y y offset
     * @return this(modified)
     */
    public AABB2D move(double x, double y) {
        this.x0 += x;
        this.y0 += y;
        this.x1 += x;
        this.y1 += y;
        return this;
    }

    /**
     * increase size in axis
     *
     * @param width  w
     * @param height h
     * @return this(operated)
     */
    public AABB2D increase(double width, double height) {
        this.x0 -= width;
        this.x1 += width;
        this.y0 -= height;
        this.y1 += height;
        return this;
    }

    /**
     * get exact width
     *
     * @return width
     */
    public double getWidth() {
        return x1 - x0;
    }

    public void setWidth(double width) {
        this.x1 = x0 + width;
    }

    /**
     * get exact height
     *
     * @return height
     */
    public double getHeight() {
        return y1 - y0;
    }

    public void setHeight(double height) {
        this.y1 = y0 + height;
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }
}
