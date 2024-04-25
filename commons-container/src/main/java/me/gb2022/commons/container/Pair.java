package me.gb2022.commons.container;

public final class Pair <T,T2>{
    private T left;
    private T2 right;


    public Pair(T left, T2 right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public T2 getRight() {
        return right;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public void setRight(T2 right) {
        this.right = right;
    }
}
