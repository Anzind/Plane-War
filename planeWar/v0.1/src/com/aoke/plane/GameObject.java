package com.aoke.plane;

import org.w3c.dom.css.Rect;

import java.awt.*;

public class GameObject {

    Image img;          //图片
    double x, y;        //物体的坐标
    int speed;          //物体移动的速度
    int width, height;  //物体长宽

    public GameObject(){}

    public GameObject(Image img, double x, double y, int speed, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public GameObject(Image img, double x, double y, int speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    public void drawMyself(Graphics g){
        g.drawImage(img, (int)x, (int)y, width, height, null);
    }

    public Rectangle getRect(){//所有的物体都是矩形，当你获得对应的矩形的时候，我们就可以做一些相关的判断操作
        return new Rectangle((int)x, (int)y, width, height);
    }
}
