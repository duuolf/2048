import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
//主函数
public class game2048{
    public static void main(String[] args){
    Graphics g;
        new win();
    }
}
//窗口
class win extends JFrame{
    public static final int WW = 514;//窗口的宽         画布的宽为500
    public static final int WH = 837;//窗口的高         画布的高为800
    win(){
        setTitle("2048");                                       //设置窗口名称
        setSize(WW,WH);                                         //设置窗口大小
        setVisible(true);                                       //设置窗口可见
        setLocationRelativeTo(null);                            //设置窗口居中
        setResizable(false);                                    //设置窗口大小不可调整
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         //退出程序
        key k=new key();
        addKeyListener(k);
        chess c=new chess();
        add(c);
        mymain.start();

    }

//棋盘
class chess extends JPanel{
//常量
    public static final int KL = 100;//块的边长
//变量
    int num;
//方法
    public void num(Graphics2D g,FontMetrics fm,Color c,Font f,int j,int k,int i){//画格子里数字的方法   参数表：组件，颜色，字体，行，列，数字
        String s=String.valueOf(i);
        g.setColor(c);
        g.setFont(f);
        g.drawString(s,j*110+24+10+(KL-fm.stringWidth(s))/2,k*110+250+10+KL/2+(int)(fm.getHeight()/3.4));
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font nf = new Font("微软雅黑", Font.BOLD, 40); // 创建字体对象      数字字体
        Font sf = new Font("楷体", Font.BOLD, 45); // 创建字体对象      分数字体
        FontMetrics fm = g.getFontMetrics(nf);
        Graphics2D gx=(Graphics2D) g;
        gx.setColor(new Color(250,248,239)); // 设置背景的颜色
        gx.fillRect(0, 0, 500, 800); // 绘制矩形，参数分别为x坐标、y坐标、宽度和高度
        gx.setColor(new Color(187,173,160)); // 设置棋盘的颜色
        gx.fillRoundRect(24, 250, 4*KL+5*10, 4*KL+5*10,6,6); // 绘制矩形，参数分别为x坐标、y坐标、宽度和高度
        //左上角显示分数
        gx.setColor(Color.black);
        gx.setFont(sf);
        gx.drawString("分数："+mymain.scores,0,50);
        //右上角显示操作说明
        gx.drawString("w/↑向上移动",200,100);
        gx.drawString("s/↓向下移动",200,145);
        gx.drawString("a/←向左移动",200,190);
        gx.drawString("d/→向右移动",200,235);
        //下方显示提示
        gx.drawString("点击ESC键开始新游戏",25,770);
        for(int j=0;j<4;j++){
            for(int k=0;k<4;k++){
                num=mymain.num[k][j];
                switch(num){
                case 0:
                    gx.setColor(new Color(205,193,180)); // 设置格子的颜色
                    break;
                case 2:
                    gx.setColor(new Color(238,228,218)); // 设置格子的颜色
                    break;
                case 4:
                    gx.setColor(new Color(237,224,200)); // 设置格子的颜色
                case 8:
                    gx.setColor(new Color(242,177,121)); // 设置格子的颜色
                    break;
                case 16:
                    gx.setColor(new Color(245,149,99)); // 设置格子的颜色
                    break;
                case 32:
                    gx.setColor(new Color(246,124,95)); // 设置格子的颜色
                    break;
                case 64:
                    gx.setColor(new Color(246,94,59)); // 设置格子的颜色
                    break;
                case 128:
                    gx.setColor(new Color(237,204,97)); // 设置格子的颜色
                    break;
                case 256:
                    gx.setColor(new Color(231,196,91)); // 设置格子的颜色
                    break;
                case 512:
                    gx.setColor(new Color(237,200,80)); // 设置格子的颜色
                    break;
                case 1024:
                    gx.setColor(new Color(237,197,63)); // 设置格子的颜色
                    break;
                default :
                    gx.setColor(new Color(237,194,46)); // 设置格子的颜色
                }
                gx.fillRoundRect(j*110+24+10, k*110+250+10, KL, KL,3,3);
                if(num!=0)num(gx,fm,new Color(115,106,98),nf,j,k,num);
            }
        }
        if(mymain.ifend()==1){
        FontMetrics fm1 = g.getFontMetrics(sf);
        gx.setColor(Color.black);
        String s1="游戏结束";
        String s2="你的分数是："+mymain.scores;
        gx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        gx.fillRoundRect(24, 250, 4*KL+5*10, 4*KL+5*10,6,6);
        gx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        gx.setColor(Color.white);
        gx.drawString(s1,24+(4*KL+5*10-fm1.stringWidth(s1))/2,250+2*KL+5*5);
        gx.drawString(s2,24+(4*KL+5*10-fm1.stringWidth(s2))/2,250+2*KL+5*5+40);
        return;
        }
    }
}
//监听
class key extends KeyAdapter{
    KeyEvent e;
    public void keyPressed(KeyEvent e) {
            boolean moved = false;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    mymain.start();
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    mymain.left();
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    mymain.right();
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    mymain.up();
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    mymain.down();
                    break;
                default:
                    break;}
            repaint();
        }
}
//核心算法
public class mymain{
//常量
    public static final int SIZE = 4;//定义数组的长宽
//变量
    public static int[][] num=new int [SIZE][SIZE];
    static Random rand = new Random();//创建随机数对象
    static int scores=0;
//方法
    static void start(){//初始化棋盘
        for(int j=0;j<4;j++){
            for(int k=0;k<4;k++){
                num[j][k]=0;
            }
        }
        scores=0;
        add();
        add();
    }
    static void add(){//添加新数字    2/4
        int j,k;
        while(true){
            j=rand.nextInt(4);
            k=rand.nextInt(4);
            if(num[j][k]==0)break;
        }
        int n=rand.nextInt(5);
        num[j][k]=n==0?4:2;
    }
    static boolean up(){//向上移动
        boolean move=false;
        int now=0;
        int temp=1;
        int i,j,n;
        for (j=0;j<4;j++)
        {
            now=0;
            temp=1;
            while ((now<4)&&(temp<4))
            {
                if (num[now][j]==0)
                {
                    if (num[temp][j]!=0)
                    {
                        move=true;
                        num[now][j]=num[temp][j];
                        num[temp][j]=0;
                        now++;
                        temp=now+1;
                    }
                    else
                    {
                        temp++;
                    }
                }
                else
                {
                    now++;
                    temp=now+1;
                }
            }
            for (i=0;i<3;i++)
            {
                if (num[i][j]==num[i+1][j]&&num[i][j]!=0)
                {
                    move=true;
                    num[i][j]*=2;
                    scores+=num[i][j];
                    for (n=i+1;n<3;n++)
                        num[n][j]=num[n+1][j];
                    num[3][j]=0;
                }

            }
        }
        if(move==true)add();
        return move;
    }
    static boolean down(){//向下移动
        boolean move=false;
        int now=0;
        int temp=1;
        int i,j,n;
        for (j=0;j<4;j++)
        {
            now=3;
            temp=2;
            while ((now>-1)&&(temp>-1))
            {
                if (num[now][j]==0)
                {
                    if (num[temp][j]!=0)
                    {
                        move=true;
                        num[now][j]=num[temp][j];
                        num[temp][j]=0;
                        now--;
                        temp=now-1;
                    }
                    else
                    {
                        temp--;
                    }
                }
                else
                {
                    now--;
                    temp=now-1;
                }
            }
            for (i=3;i>0;i--)
            {
                if (num[i][j]==num[i-1][j]&&num[i][j]!=0)
                {
                    move=true;
                    num[i][j]*=2;
                    scores+=num[i][j];
                    for (n=i-1;n>0;n--)
                        num[n][j]=num[n-1][j];
                    num[0][j]=0;
                }
            }
        }
        if(move==true)add();
        return move;
    }
    static boolean left(){//向左移动
        boolean move=false;
        int now=0;
        int temp=1;
        int i,j,n;
        for (i=0;i<4;i++)
        {
            now=0;
            temp=1;
            while ((now<4)&&(temp<4))
            {
                if (num[i][now]==0)
                {
                    if (num[i][temp]!=0)
                    {
                        move=true;
                        num[i][now]=num[i][temp];
                        num[i][temp]=0;
                        now++;
                        temp=now+1;
                    }
                    else
                    {
                        temp++;
                    }
                }
                else
                {
                    now++;
                    temp=now+1;
                }
            }
            for (j=0;j<3;j++)
            {
                if (num[i][j]==num[i][j+1]&&num[i][j]!=0)
                {
                    move=true;
                    num[i][j]*=2;
                    scores+=num[i][j];
                    for (n=j+1;n<3;n++)
                        num[i][n]=num[i][n+1];
                    num[i][3]=0;
                }
            }
        }
        if(move==true)add();
        return move;
    }
    static boolean right(){//向右移动
        boolean move=false;
        int now=0;
        int temp=1;
        int i,j,n;
        for (i=0;i<4;i++)
        {
            now=3;
            temp=2;
            while ((now>-1)&&(temp>-1))
            {
                if (num[i][now]==0)
                {
                    if (num[i][temp]!=0)
                    {
                        move=true;
                        num[i][now]=num[i][temp];
                        num[i][temp]=0;
                        now--;
                        temp=now-1;
                    }
                    else
                    {
                        temp--;
                    }
                }
                else
                {
                    now--;
                    temp=now-1;
                }
            }
            for (j=3;j>0;j--)
            {
                if (num[i][j]==num[i][j-1]&&num[i][j]!=0)
                {
                    move=true;
                    num[i][j]*=2;
                    scores+=num[i][j];
                    for (n=j-1;n>0;n--)
                        num[i][n]=num[i][n-1];
                    num[i][0]=0;
                }
            }
        }
        if(move==true)add();
        return move;
    }
    static int ifend(){//判断游戏是否可以继续
        for(int j=0;j<4;j++){
            for(int k=0;k<4;k++){
                if(num[j][k]==0)return 0;
            }
        }
        for(int j=0;j<4;j++){
            for(int k=0;k<3;k++){
                if(num[j][k]==num[j][k+1])return 0;
            }
        }
        for(int j=0;j<3;j++){
            for(int k=0;k<4;k++){
                if(num[j][k]==num[j+1][k])return 0;
            }
        }
        return 1;
    }
}
}
