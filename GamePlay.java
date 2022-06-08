import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.awt.Font;
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean GameOn = false;
    private int Score = 0;
    private int TotalBricks = 21;
    private Timer timer;
    private int Delay = 8;
    private int PadelLocation = 310;
    private int XballPos = 120;
    private int YballPos = 350;
    private int BallPosDirX = -2;
    private int BallPosDirY = -3;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(Delay, this);
        timer.start();

    }

    public void paint(Graphics graphics) {
        // BackGround
        graphics.setColor(Color.CYAN);
        graphics.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D)graphics);
        // Borders
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);
        // Padel
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(PadelLocation, 550, 100, 8);
        // Ball
        graphics.setColor(Color.blue);
        graphics.fillOval(XballPos, YballPos, 20, 20);

        graphics.setColor(Color.black);
        graphics.setFont(new Font("serif",Font.BOLD,25));
        graphics.drawString(""+Score,590,30);

        if(TotalBricks<=0)
        {

            BallPosDirX=0;
            BallPosDirY=0;
            GameOn=false;
            graphics.setColor(Color.GREEN);
            graphics.setFont(new Font("serif",Font.BOLD,30));
            graphics.drawString("You've Won , Score : "+Score,190,300);

            graphics.setFont(new Font("serif",Font.BOLD,20));
            graphics.drawString("Press Enter to Restart!",230,350);
        }

        if(YballPos > 570)
        {
            GameOn=false;
            BallPosDirX=0;
            BallPosDirY=0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif",Font.BOLD,30));
            graphics.drawString("Game Over, Score : "+Score,190,300);

            graphics.setFont(new Font("serif",Font.BOLD,20));
            graphics.drawString("Press Enter to Restart.",230,350);
        }
        graphics.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        // Ball-Pedal Interaction
        if(GameOn)
        {
            if(new Rectangle(XballPos,YballPos,20,30).intersects(new Rectangle(PadelLocation,550,100,8)))
            {
                BallPosDirY= -BallPosDirY;
            }
            for (int i=0;i<map.map.length;i++)
            {
                for(int j=0;j<map.map[0].length;j++)
                {
                    if(map.map[i][j]>0)
                    {
                        int Xbrick=j*map.brickWidth+80;
                        int Ybrick=i*map.brickHeight+50;
                        int brickWidth=map.brickWidth;
                        int brickHeight=map.brickHeight;

                        Rectangle rect=new Rectangle(Xbrick,Ybrick,brickWidth,brickHeight);
                        Rectangle ballrect=new Rectangle(XballPos,YballPos,20,20);
                        Rectangle brickRect=rect;

                        if(ballrect.intersects(brickRect))
                        {
                            map.SetBrickValue(0,i,j);
                            TotalBricks--;
                            Score+=5;
                            if(XballPos+19<=brickRect.x||XballPos+1>=brickRect.x+brickRect.width)
                            {
                                BallPosDirX=-BallPosDirX;
                            }
                            else
                            {
                                BallPosDirY=-BallPosDirY;
                            }
                        }
                    }
                }
            }
            XballPos+=BallPosDirX;
            YballPos+=BallPosDirY;
            if(XballPos<0)
            {
                BallPosDirX= -BallPosDirX;
            }
            if(YballPos<0)
            {
                BallPosDirY= -BallPosDirY;
            }
            if(XballPos>670)
            {
                BallPosDirX= -BallPosDirX;
            }

        }
      repaint();
}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            if(PadelLocation>=600)
            {
                PadelLocation=600;
            }
            else
            {
                MoveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
        {
            if(PadelLocation<10)
            {
                PadelLocation=10;
            }
            else
            {
                MoveLeft();
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_ENTER)
        {
            if(!GameOn)
            {
                GameOn=true;
                XballPos=120;
                YballPos=350;
                BallPosDirX=-1;
                BallPosDirY=-2;
                Score=0;
                TotalBricks=21;
                map =new MapGenerator(3,7);
                repaint();
            }
        }
    }
    public void MoveRight()
    {
        GameOn=true;
        PadelLocation+=20;
    }
    public void MoveLeft()
    {
        GameOn=true;
        PadelLocation-=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
