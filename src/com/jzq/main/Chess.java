package com.jzq.main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @Author: JZQ
 * @Date: 2023/11/20 16:43
 * @Description:
 */
public class Chess {
    //定义一个常量，只能在定义时或代码块中修改值，其他不允许修改
    //棋子大小
    private static final int SIZE = 30;
    //棋盘外边距
    private static final int MARGIN = 20;
    //棋子间距
    private static final int SPACE = 40;
    //棋子名称
    private String name; //若为public修饰，则违反了java面向对象三大特性之一的封装性，使用set和get方法

    //棋子图片后缀
    private static final String SUFFIX = ".png";
    //棋子阵营，0：红，1：黑
    private int player;
    //棋子绘制时的实际坐标位置
    private int x, y;
    //棋子的网格坐标
    private Point p;
    //棋子的网格坐标，初始位置，不可改变
    private Point initP;
    //保存每个棋子的索引位置
    private int index;

    public String getName() {
        return name;
    }

    /**
     * 类的方法：
     *      分类：
     *          实例方法：
     *              权限修饰符 返回值类型 方法名(形参列表){方法体}
     *              调用：必须通过实例.方法名(实参);
     *                  返回值类型：
     *                      void: 方法不需要强制写return语句
     *                      数据类型：必须是与之有关系的值或变量。
     *                      比如，是基本数据类型，是什么类型就返回相应类型的值或变量。
     *                      如果是引用数据类型，可以返回的是子类的对象或相同类型的值
     *           静态方法（类方法）
     *              权限修饰符 static 返回值类型 方法名(形参列表){方法体}
     *              调用：可以通过实例.方法名(实参) 或 类名.方法名(实参);
     *           构造方法
     *              权限修饰符 类名(形参列表) {
     *                  super(); //默认调用父类中的无参构造方法
     *              }
     *              作用：当构造对象，必须要做的事情，可以写在构造方法中，且在创建对象时只执行一次
     *              注意：每个类，默认都有一个无参的构造方法，如果显示定义了一个有参的构造方法，则不再提供默认的无参构造方法，需要显示的定义无参构造方法。
     */

    /**
     * 方法重载：
     *      1、方法名必须相同，且形参列表必须不同
     *      2、与返回值类型无关
     *      3、与形参名无关，只与形参类型有关
     */
    public Chess(){}

    public Chess(String name, int player, Point p){
        this.name = name;
        this.player = player;
        this.setP(p);
    }

    public Chess(String name, Point p, int player){
        this.name = name;
        this.player = player;
        this.setP(p);
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setP(Point p) {

        //克隆一个新的Point对象
        this.p = (Point) p.clone();
        if (this.initP == null) {
            this.initP = this.p;
        }
        this.calXY();
    }

    public Point getP() {
        return this.p;
    }

    /**
     * 判断棋子是否可以被移动到指定的位置
     * @param tp
     * @return 返回true，表示可移动，返回false，表示不可移动
     */
    public boolean isAbleMove(Point tp, GamePanel gamePanel){
        if("boss".equals(this.name)){
//            //判断是否在王宫范围内
//            if(tp.x < 4 || tp.x > 6){
//                return false;
//            }
//            //上面和下面
//            if(this.initP.y < 6){
//                //上面
//                if(tp.y > 3 || tp.y < 1){
//                    return false;
//                }
//            }else {
//                //下面
//                if(tp.y < 8 || tp.y > 10){
//                    return false;
//                }
//            }
//            //判断是否走直线且只能走一步
//            //x轴直线还是y轴直线
//            if(p.y == tp.y){
//                //x轴直线
//                if (Math.abs(p.x - tp.x) == 1){
//                    //走一步
//                    return true;
//                }
//            } else if (p.x == tp.x) {
//                //y轴直线
//                if (Math.abs(p.y - tp.y) == 1){
//                    //走一步
//                    return true;
//                }
//            }
            if(this.isHome(tp)){
                if(this.line(tp) > 1){
                    if(this.getStep(tp) == 1){
                        return true;
                    }
                }
            }

        } else if ("shi".equals(this.name)) {
//            //判断是否在王宫范围内
//            if(tp.x < 4 || tp.x > 6){
//                return false;
//            }
//            //上面和下面
//            if(this.initP.y < 6){
//                //上面
//                if(tp.y > 3 || tp.y < 1){
//                    return false;
//                }
//            }else {
//                //下面
//                if(tp.y < 8 || tp.y > 10){
//                    return false;
//                }
//            }
//
//            //走正斜线，且只能走一步
//            if(Math.abs(tp.x - p.x) == Math.abs(tp.y - p.y) && Math.abs(tp.y - p.y) == 1){
//                return true;
//            }

            return isHome(tp) && line(tp) == 1 && getStep(tp) == 1;
        }else if ("xiang".equals(this.name)) {
            return this.line(tp) == 1 && this.getStep(tp) == 2 && !this.isBieJiao(tp, gamePanel) && !this.isOverRiver(tp);
        }else if ("ma".equals(this.name)) {
            return (this.line(tp) == -1 || this.line(tp) == 0) && !this.isBieJiao(tp, gamePanel);
        }else if ("che".equals(this.name)) {
            return this.line(tp) > 1 && this.getCountFromOriginToTarget(tp, gamePanel) == 0;
        }else if ("pao".equals(this.name)) {
            Chess c = gamePanel.getChessByP(tp);
            if(null != c){
//                if(c.getPlayer() != this.player){
                    //吃子
                    return this.line(tp) > 1 && this.getCountFromOriginToTarget(tp, gamePanel) == 1;
//                }
            }else {
                //移动
                return this.line(tp) > 1 && this.getCountFromOriginToTarget(tp, gamePanel) == 0;
            }
        }else if ("bing".equals(this.name)) {
            //兵移动规则：不能后退，只能走直线，只能走一步
            if(this.line(tp) < 2 || this.getStep(tp) != 1 || this.isBack(tp)){
                return false;
            }
            //没过河只能前进，过了河既可以前进也可以左右走
            boolean overRiver = this.isOverRiver(this.p);
            if(overRiver){
                return this.line(tp) > 1;
            }else {
                return this.line(tp) == 2;
            }
        }
        return false;
    }

    /**
     * 判断棋子的网格坐标初始是在上半边还是下半边
     * @return 1：上，2： 下
     */
    public int isUpOrDown(){
        //上面和下面
        if(this.initP.y < 6){
            //上面
            return 1;
        }else {
            //下面
            return 2;
        }
    }

    /**
     * 判断棋子是否在王宫范围内
     * @param tp
     * @return true：在王宫范围内，false：不在王宫范围内
     */
    public boolean isHome(Point tp){
        if (tp.x < 4 || tp.x > 6){
            return false;
        }
        int upOrDown = this.isUpOrDown();
        if(upOrDown == 1){
            //上面
            if(tp.y > 3 || tp.y < 1){
                return false;
            }
        }else {
            //下面
            if(tp.y < 8 || tp.y > 10){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断棋子走直线还是正斜线或者都不是
     * @param tp 目标坐标
     * @return -2：都不是，-1：y轴日子，0：x轴日子，1：正斜线，2：y轴直线，3：x轴直线
     */
    public int line(Point tp){
        if(p.y == tp.y){
            //x轴直线
            return 3;
        } else if (p.x == tp.x) {
            //y轴直线
            return 2;
        } else if (Math.abs(tp.y - p.y) == Math.abs(tp.x - p.x)) {
            //正斜线
            return 1;
        } else {
            if(Math.abs(tp.x - p.x) == 1 && Math.abs(tp.y - p.y) == 2){
                //y形日子
                return -1;
            }else if (Math.abs(tp.x - p.x) == 2 && Math.abs(tp.y - p.y) == 1){
                //x形日子
                return 0;
            }
        }
        return -2;
    }

    /**
     * 计算起点到目标点之间的步数
     * @param tp
     * @return
     */
    public int getStep(Point tp){
        int line = this.line(tp);
        if(line == 3){
            //x直线
            return Math.abs(tp.x - p.x);
        } else if (line == 2 || line == 1) {
            //y直线 或 正斜线
            return Math.abs(tp.y - p.y);
        } else {
            return 0;
        }
    }

    /**
     * 判断相 或 马是否蹩脚
     * @param tp
     * @param gamePanel
     * @return true：表示蹩脚，false：表示不蹩脚
     */
    public boolean isBieJiao(Point tp, GamePanel gamePanel){
        Point center = new Point();//中心点
        if("xiang".equals(this.name)){
            center.x = (this.p.x + tp.x) / 2;
            center.y = (this.p.y + tp.y) / 2;
            return gamePanel.getChessByP(center) != null;
        } else if ("ma".equals(this.name)) {
            int line = this.line(tp);
            if(line == 0){
                //x轴日子
                center.x = (this.p.x + tp.x) / 2;
                center.y = this.p.y;
            } else if (line == -1) {
                //y轴日子
                center.x = this.p.x;
                center.y = (this.p.y + tp.y) / 2;
            }
            return gamePanel.getChessByP(center) != null;
        }
        return false;
    }

    /**
     * 判断目标点是否过河
     * @param tp
     * @return true:表示过河，false:表示没有过河
     */
    public boolean isOverRiver(Point tp){
        int upOrDown = this.isUpOrDown();
        if(upOrDown == 1){
            //上面
            return tp.y > 5;
        }else {
            //下面
            return  tp.y < 6;
        }
    }

    /**
     * 计算起点到目标点之间的棋子数量，不计算起点和目标点上的位置
     * @param tp
     * @return
     */
    public int getCountFromOriginToTarget(Point tp, GamePanel gamePanel){
        int start = 0;
        int end = 0;
        int count = 0; //计数器，保存统计棋子数量
        int line = this.line(tp);
        Point newP = new Point();
        if(line == 2){
            //y轴直线
            newP.x = tp.x;
            if(tp.y > this.p.y){
                //从上往下
                start = this.p.y + 1;
                end = tp.y;
            }else {
                //从下往上
//               start = this.p.y -1;
//               end = tp.y;
                //保证start是小的值，end是大的值
                start = tp.y + 1;
                end = this.p.y;
            }
            for(; start < end; start ++){
                newP.y = start;
                if(gamePanel.getChessByP(newP) != null){
                    count++;
                }
            }
        } else if (line == 3) {
            //x轴直线
            newP.y = tp.y;
            if(tp.x > this.p.x){
                //从左到右
                start = this.p.x + 1;
                end = tp.x;
            }else {
                //从右到左
                start = tp.x + 1;
                end = this.p.x;
            }
        }
        for (int i = start; i < end; i++) {
            newP.x = i;
            if(gamePanel.getChessByP(newP) != null){
                count++;
            }
        }
        System.out.println("棋子数量===========" + count);
        return count;
    }

    /**
     * 判断是否后退
     * @param tp
     * @return true：表示后退，false：表示前进
     */
    public boolean isBack(Point tp){
        int upOrDown = this.isUpOrDown();
        if(upOrDown == 1){
            //上面
            if(tp.y < this.p.y){
                return true;
            }
        }else {
            //下面
            if(tp.y > this.p.y){
                return true;
            }
        }
        return false;
    }

    /**
     * 棋子的绘制方法
     * @param g
     * @param panel
     */
    public void draw(Graphics g, JPanel panel){
        String path = "picture" + File.separator + this.name + this.player + Chess.SUFFIX;
        Image img = Toolkit.getDefaultToolkit().getImage(path);
        //画图片的时候，是以图片的左上角为基准变进行绘制的
        g.drawImage(img, this.x, this.y, Chess.SIZE, Chess.SIZE, panel);
    }

    /**
     * 绘制棋子的边框
     * @param g
     */
    public void drawRect(Graphics g){
        g.drawRect(this.x, this.y, Chess.SIZE, Chess.SIZE);
    }

    /**
     * 计算xy的绘制坐标
     */
    public void calXY(){
        this.x = (Chess.MARGIN - Chess.SIZE / 2) + Chess.SPACE * (this.p.x - 1);
        this.y = (Chess.MARGIN - Chess.SIZE / 2) + Chess.SPACE * (this.p.y - 1);
    }

    /**
     * 根据xy坐标计算网格坐标对象
     * @param x
     * @param y
     *
     * static：静态关键字
     *      修饰方法：称为类方法或静态方法
     *          如何调用
     *              实例.方法()或类名.方法()
     *          注意：类方法只能使用类属性
     *      修饰属性：称为类属性或静态属性
     *          如何调用
     *              实例.属性名 或 类名.属性名
     *          注意：静态属性只有一个共用的内存地址，所以不管有多少个对象，只需要修改一次，其他对象都会受影响
     */
    public static Point getPointFromXY(int x, int y){
        Point p = new Point();
        p.x = (x - (Chess.MARGIN - Chess.SIZE / 2)) / Chess.SPACE + 1;
        p.y = (y - (Chess.MARGIN - Chess.SIZE / 2)) / Chess.SPACE + 1;
        if(p.x < 1 || p.x > 9 || p.y < 1 || p.y > 10){
            return null;
        }
        return p;
//        return new Point((x - (this.MARGIN - this.SIZE / 2)) / this.SPACE + 1, (y - (this.MARGIN - this.SIZE / 2)) / this.SPACE + 1);

    }

    /**
     *反转网格坐标
     */
    public void reserve(){
        this.p.x = 10 - this.p.x;
        this.p.y = 11 - this.p.y;
        this.initP = this.p; //不需要加条件，因为reserve()方法在一个棋子对象中只能运行一次
        this.calXY();
    }

    @Override
    public String toString() {
        return "Chess{" +
                "name='" + name + '\'' +
                ", player=" + player +
                ", x=" + x +
                ", y=" + y +
                ", p=" + p +
                ", initP=" + initP +
                '}';
    }

    public static void main(String[] args) {
        Point p = new Point();
        p = null;
        System.out.println(p);
        p.clone();

    }
}
