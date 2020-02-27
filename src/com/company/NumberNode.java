package com.company;

public class NumberNode {
    private int value;
    private boolean alive;

    public NumberNode(int value)
    {
        this.value = value;
        alive = true;
    }

    public void kill()
    {
        alive = false;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public int getValue()
    {
        return value;
    }
}
