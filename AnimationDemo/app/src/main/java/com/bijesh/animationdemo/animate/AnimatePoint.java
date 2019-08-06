package com.bijesh.animationdemo.animate;

/**
 * Created by Bijesh C J on 06,August,2019
 */
public class AnimatePoint {
    public float x,y;
    public AnimatePoint(float x,float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return " x: "+x+" ,y: "+y;
    }
}
