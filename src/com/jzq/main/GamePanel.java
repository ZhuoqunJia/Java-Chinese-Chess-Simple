package com.jzq.main;

/**
 * @Author: JZQ
 * @Date: 2023/11/8 11:37
 * @Description:
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class GamePanel extends JPanel {
    //定义一个保存所有棋子的成员变量，变量值类型为数组
    private Chess[] chesses = new Chess[32]; //保存所有的棋子
    //当前选中的棋子
    private Chess selectedChess;
    //记住当前的阵营
    //默认红方先走
    private int currentPlayer = 0;

    //构造方法
    //无参构造方法：权限修饰符 类名(){}
    //构造方法，可以让我们自定义创建对象时，做一些必要的操作
    public GamePanel(){
        //super(); //调用父类构造方法，每个类的构造方法中，都隐藏有这一行代码，且必须是第一行
        System.out.println("调用GamePanel的无参构造方法！");
//        super(); 必须在构造方法的第一行
        this.createChesses();
        /**
         * 如何操作棋子
         *      1、点击棋盘
         *      2、如何判断点击的地方是否有棋子
         *      3、如何区分第一次选择，重新选择，移动，吃子
         * 棋盘规则
         *      1、红方不可以操作黑方棋子
         *      2、一方走完结束，另一方才能走
         */
        //添加点击事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                System.out.println("===================================以下为一个棋子对象的操作===================================");
                System.out.println("点击棋盘的坐标为：x=" + e.getX() + ",y=" + e.getY());
                Point p = Chess.getPointFromXY(e.getX(), e.getY());
                System.out.println("点击棋子对象的棋盘的网格坐标对象为：p===" + p);

                if(null == selectedChess){
                    //第一次选择
                    System.out.println("第一次选择");
                    selectedChess = getChessByP(p);
                    if(GamePanel.this.selectedChess != null && GamePanel.this.selectedChess.getPlayer() != GamePanel.this.currentPlayer){
                        //说明此时选择不是己方阵营的棋子，将已选择的棋子置为null
                        GamePanel.this.selectedChess = null;
                    }
                }else {
                    //重新选择，移动，吃子
                    Chess c = getChessByP(p);
                    if(null != c){
                        //第n次点击的时候有棋子
                        //重新选择，吃子
                        if(c.getPlayer() == selectedChess.getPlayer()){
                            //重新选择
                            System.out.println("重新选择");
                            GamePanel.this.selectedChess = c;
                        }else {
                            //吃子
//                            if(selectedChess.isAbleMove(p, GamePanel.this)){
//                                GamePanel.this.selectedChess.setP(p);
//                                GamePanel.this.getChessByP(p).setP(null);
//                            }
                            System.out.println("吃子状态");
                            if(GamePanel.this.selectedChess.isAbleMove(p, GamePanel.this)){
                                /**
                                 * 1、从数组中删除被吃掉的棋子
                                 * 2、修改要移动的棋子坐标
                                 */
                                System.out.println("吃子");
                                GamePanel.this.chesses[c.getIndex()] = null;
                                GamePanel.this.selectedChess.setP(p);
                                //回合结束
                                GamePanel.this.overMyTurn();
                            }
                        }
                    }else {
                        //第n次点击的时候没有棋子，点的是空白地方
                        //移动
                        System.out.println("移动状态");
                        if(selectedChess.isAbleMove(p, GamePanel.this)){ //特殊写法
                            System.out.println("移动");
                            selectedChess.setP(p);
                            //回合结束
                            //要写在if判断中，才是真正的移动了
                            GamePanel.this.overMyTurn();
                        }
                    }
                }
                System.out.println("点击的棋子对象为：selectedChess===" + selectedChess);
                System.out.println("===================================以上为一个棋子对象的操作===================================");
                //刷新棋盘，即重新执行paint方法
                repaint();
            }
        });
    }

    /**
     * 结束当前回合
     */
    private void overMyTurn(){
        this.currentPlayer = this.currentPlayer == 0 ? 1 : 0;
        this.selectedChess = null;
    }

    /**
     * 根据网格坐标p对象查找棋子对象
     * @param p
     * @return
     */
    public Chess getChessByP(Point p){
        for (Chess item:
             chesses) {
//            System.out.println(item.getP());
            if(item != null && item.getP().equals(p)){
                return item; //因为return关键字是结束方法的，所以也会导致循环提前终止
            }
        }
        return null;
    }

    /**
     * 根据网格对象p对象查找棋子索引
     * @param p
     * @return
     */
    public int getChessIndexByP(Point p){
        for (int i = 0; i < this.chesses.length; i++) {
            if(this.chesses[i].getP().equals(p)){
                return i; //因为return关键字是结束方法的，所以也会导致循环提前终止
            }
        }
        return -1;
    }


    /**
     * 创建所有棋子
     */
    private void createChesses(){
        String[] names = new String[]{"che", "ma", "xiang", "shi", "boss", "shi", "xiang", "ma", "che",
                "pao", "pao",
                "bing", "bing", "bing", "bing", "bing"};
        Point[] ps = {
                new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(4, 1), new Point(5, 1), new Point(6, 1), new Point(7, 1), new Point(8, 1), new Point(9, 1),
                new Point(2, 3), new Point(8, 3),
                new Point(1, 4), new Point(3, 4), new Point(5, 4), new Point(7, 4), new Point(9, 4)
        };

        for (int i = 0; i < ps.length; i++) {
            Chess c = new Chess(names[i], ps[i], 0); //创建棋子对象
//            c.setName(names[i]); //指定棋子名称
//            c.setP(ps[i]); //指定棋子的网格坐标
//            c.setPlayer(0); //这是棋子阵营
            c.setIndex(i); //设置棋子的索引
            this.chesses[i] = c; //将棋子保存到数组中
        }

        for (int i = 0; i < ps.length; i++) {
            Chess c = new Chess(); //创建棋子对象
            c.setName(names[i]); //指定棋子名称
            c.setP(ps[i]); //指定棋子的网格坐标
            c.reserve(); //反转网络坐标
            c.setPlayer(1); //这是棋子阵营
            c.setIndex(i + 16); //设置棋子的索引
            this.chesses[c.getIndex()] = c; //将棋子保存到数组中
        }
    }

    /**
     * 绘制所有棋子
     * @param g
     */
    private void drawChesses(Graphics g){
        for (Chess item:
             this.chesses) {
            if(null != item){
                item.draw(g, this);
            }
        }
    }

    /**
     * 有一个问题，paint方法又是创建，绘制，保存数据到数组，一共做了三个事情
     * paint方法正常来说应该只做绘制棋子这一件事情
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override //重写注解
    public void paint(Graphics g) {
        //super调用父类中的方法
//        super.paint(g); //清除原来的痕迹
        System.out.println("paint方法执行");
        String backGroundPicture = "picture" + File.separator + "qipan.jpg";
//        System.out.println("每拖动一次窗口，就会在画板上重新画一次");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image bgImg = toolkit.getImage(backGroundPicture);
        g.drawImage(bgImg, 0, 0, this);

        this.drawChesses(g);

        if(null != this.selectedChess){
            this.selectedChess.drawRect(g);
        }
    }
}
