package com.aoke.plane;

import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * 游戏主窗口
 */
public class GameFrame extends Frame {

    Image planeimg = GameUtil.getImage("image/我方飞机.png");
    Image bg = GameUtil.getImage("image/背景.png");

    Plane p1 = new Plane(planeimg, 200, 200, 7);

    Shell[] shells = new Shell[100];

    Date start = new Date();    //游戏开始时间
    Date end;  //游戏结束时间
    long period = 0;    //游戏玩了多少秒

    private Image offScreenImage = null;
    public void update(Graphics g){
        if(offScreenImage == null){//这是游戏窗口的宽度和高度
            offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
        }

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0,0, null);
    }

    public void paint(Graphics g){//g当做是一支画笔


        g.drawImage(bg,0,0,500,500,null);

        drawTime(g);

        p1.drawMyself(g);

        //画炮弹
        for(int i = 0; i < shells.length; i++){
            shells[i].drawMyself(g);

            //实现碰撞检测
            boolean clash = shells[i].getRect().intersects(p1.getRect());
            if(clash){
                System.out.println("飞机被击中了！！");
                p1.live = false;
            }
        }
    }

    public void drawTime(Graphics g){
        Color c = g.getColor();
        Font f = g.getFont();

        g.setColor(Color.green);
        if(p1.live){
            period = (System.currentTimeMillis() - start.getTime())/1000;
            g.drawString("坚持了"+ period +"秒",200,45);
        }else{
            if(end == null){
                end = new Date();
                period = (end.getTime() - start.getTime())/1000;
            }
            g.setColor(Color.red);
            g.setFont(new Font("楷体", Font.BOLD, 50));
            g.drawString("最终时间：" + period, 100,200);

        }
        g.setFont(f);
        g.setColor(c);
    }

    public void launchFrame(){
        this.setTitle("飞机大战");
        setVisible(true);   //窗口是否可见

        setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);  //窗口大小

        setLocation(600, 300);  //窗口打开的位置

        //增加关闭窗口的操作
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }); //正常退出程序

        new PaintThread().start();  //启动线程重画
        this.addKeyListener(new KeyMonitor());  //启动键盘监听

        //初始化创建x个炮弹对象
        for(int i = 0; i < shells.length; i++){
            shells[i] = new Shell();
        }
    }

    /**
     * 定义了一个重画窗口的线程类
     * 定义成内部类是为了方便直接使用窗口类的相关方法
     */
    class PaintThread extends Thread{
        @Override
        public void run() {
            while(true){
                repaint();  //内部类可以直接调用外部类的成员

                try {
                    Thread.sleep(20);   //一秒画20次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class KeyMonitor extends KeyAdapter{//键盘监听器

        @Override
        public void keyPressed(KeyEvent e) {
//            System.out.println("按下：" + e.getKeyCode());
            p1.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            System.out.println("抬起：" + e.getKeyCode());
            p1.minusDirection(e);
        }
    }

    public static void main(String[] args) {
        GameFrame mainframe = new GameFrame();
        mainframe.launchFrame();
    }

}
