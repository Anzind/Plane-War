package com.aoke.plane;

import java.awt.*;

/**
 * 炮弹类
 */
public class Shell extends GameObject{

    double degree;  //角度。炮弹沿着指定的角度飞行

    public Shell(){
        x = 100;
        y = 100;

        degree = Math.random() * Math.PI * 2;

        width = 6;
        height = 6;

        speed = 2;
    }

    @Override
    public void drawMyself(Graphics g) {
        Color c = g.getColor();

        g.setColor(Color.yellow);
        g.fillOval((int)x, (int)y, width, height);

        g.setColor(c);

        //根据自己的算法指定移动的路径
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        //碰到边界改方向
        if(y > Constant.GAME_HEIGHT- (this.height*2) || y < 30) {
            degree = -degree;
        }
        if(x < this.width || x > Constant.GAME_WIDTH - (this.width*2)) {
            degree = Math.PI - degree;
        }
    }
}
