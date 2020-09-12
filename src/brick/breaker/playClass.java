
package brick.breaker;


import about.personal;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class playClass extends JPanel implements KeyListener,ActionListener{


    private int player=380;
    private int delay=10;
    private final Timer t;
    private int dx=-3,dy=-2,ballX=430,ballY=500;    
    private boolean playState=false,gameOver=false,restart=false;
    private boolean[][] brick=new boolean [3][7];
    private int brickX,brickY;
    private int point=0,life=1,score=0,bonus=0;
    
    
    public void bonus(){
        if(bonus>=3){
            life++;
        }
        bonus=0;
    }
    
    public void ballLeft(){
        if(dx>0){
            dx*=-1;
        }
    }
    public void ballRight(){
        if(dx<0){
            dx*=-1;
        }
    }
    
    private void reset(){
        ballX=430;
        ballY=500;
        player=380;
        bonus=0;
    }
    
    private void resetGame(){
        ballX=430;
        ballY=500;
        player=380;
        score=0;
        point=0;
        life=1;
        bonus=0;
    }
    
    public void ins(){
        String i1="1. LEFT/RIGHT Arrow Key-     adjusting paddle&ball whet ball stop on the paddle";
        String i2="\n2. SPACE Key-                          to start the game when ready";
        String i3="\n3. LEFT/RIGHT Arrow Key-     move the paddel while ball runs on";
        String i4="\n4. ENTER Key-                          for restarting game when games over";
        String i5="\n\n(if player break 3 or more brick between two bouns of ball, then get a free life)";
        String i6="\n(last press of arrow key declare the ball direction, while adjusting paddel & ball)";
        JOptionPane.showMessageDialog(null, i1+i2+i3+i4+i5+i6, "Instruction", JOptionPane.DEFAULT_OPTION);
    }
    
    public void brickReset(boolean bl){
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[0].length; j++) {
                brick[i][j]=bl;
            }
        }
    }
    
    public void ballColor(Graphics g){
        g.setColor(Color.GREEN);
        g.setFont(new Font(Font.SERIF,Font.BOLD,20));
        g.drawString("(+1)", 180, 27);
        g.setColor(Color.MAGENTA);
    }
    
    public void over(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(230,100, 450,80);
        g.setColor(Color.CYAN);
        g.setFont(new Font(Font.DIALOG,Font.BOLD,70));
        g.drawString("Game Over !!",240,160);
        g.setColor(Color.RED);
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,120));
        g.drawString("You LOSE:(", 110,340);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF,Font.BOLD,20));
        g.drawString("(Press ENTER To Restart)", 330,420);
    }
    
    public void won(Graphics g){
        g.setColor(Color.CYAN);
        g.setFont(new Font(Font.DIALOG,Font.BOLD,70));
        g.drawString("Game Over !!",240,150);
        g.setColor(Color.GREEN);
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,120));
        g.drawString("You WIN:)", 170,300);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF,Font.BOLD,20));
        g.drawString("(Press ENTER To Restart)", 330,400);

    }
    
    public void bricks(Graphics g){
        brickX=110;
        brickY=60;
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[0].length; j++) {
                if(brick[i][j]){
                    g.setColor(Color.ORANGE);
                    g.fillRect(brickX, brickY, 90, 50);
                    //System.out.print(brickX+"-"+brickY+"\t");
                }
                brickX+=100;
            }
            //System.out.println("");
            brickX=110;
            brickY+=60;
        }
    }

    public playClass(){
        ins();
        brickReset(true);
        
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        
        t=new Timer(delay,this);
        t.start();
    }


    @Override
    public void paint (Graphics g){
        //background  
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 900, 600);
      
        //border
        g.setColor(Color.black);
        g.fillRect(0, 0, 900, 10);
        g.fillRect(0, 0, 10, 600);
        g.fillRect(885, 0, 10, 600);
                
        //bricks
        bricks((Graphics) g);
        
        //paddel
        g.setColor(Color.BLUE);
        g.fillRect(player, 540, 140,20);
              
        //ball
        if(bonus>=3){
            ballColor((Graphics)g);
        }
        else{
            g.setColor(Color.RED);
        }
        g.fillOval(ballX, ballY, 40, 40);    
        
        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,16));
        g.drawString("Life: "+life, 100, 25);
        g.drawString("Point: "+point, 90, 45);
        g.setColor(Color.white);
        g.setFont(new Font(Font.SERIF,Font.BOLD,18));
        g.drawString("Score ("+life+" x "+point+") : "+(score), 700, 35);
        
        if(point==brick.length*brick[0].length*5){
            won((Graphics)g);
        }
        if(life<=0){
            over((Graphics)g);
        }
        
    }

        
    @Override
    public void actionPerformed(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //t.start();
        
        if(playState){
            ballX+=dx;
            ballY+=dy;
            if(ballX<10){
                ballRight();
                System.out.println("Side Wall Touch");
            }
            if(ballX>850){
                ballLeft();
                System.out.println("Side Wall Touch");
            }
            if(ballY<10 ){
                dy*=-1;
                System.out.println("Top Wall Touch");
            }
            if(ballY>525){
                playState=false;
                restart=true;
                life--;
                t.stop();
                System.out.println("Miss the Paddle");                
            }
            if(new Rectangle(ballX,ballY,40,40).intersects(new Rectangle(player, 540, 40,10))){//140
                if(dx>0){
                    dx=5;
                }else{
                    dx=-5;
                }
                dy*=-1;
                bonus();
                System.out.println("Left Bouns ^");
            }
            else if(new Rectangle(ballX,ballY,40,40).intersects(new Rectangle(player+40, 540, 60,10))){//140
                if(dx>0){
                    dx=3;
                }else{
                    dx=-3;
                }
                dy*=-1;
                bonus();
                System.out.println("Bouns ^");
            }
            else if(new Rectangle(ballX,ballY,40,40).intersects(new Rectangle(player+100, 540, 40,10))){//140
                if(dx>0){
                    dx=5;
                }else{
                    dx=-5;
                }
                dy*=-1;
                bonus();
                System.out.println("Right Bouns ^");
            }
            
            for (int i = 1; i < brick.length+1; i++) {
                a:for (int j = 1; j < brick[0].length+1; j++) {
                    if(brick[i-1][j-1]){
                        if((new Rectangle(j*100+10, i*60, 5,50).intersects(new Rectangle(ballX,ballY,40,40)))){
                            dx*=-1;
                            point+=5;
                            bonus++;
                            brick[i-1][j-1]=false;
                            break a;
                        }
                        else if((new Rectangle(j*100+10+5, i*60, 80,50).intersects(new Rectangle(ballX,ballY,40,40)))){
                            dy*=-1;
                            point+=5;
                            bonus++;
                            brick[i-1][j-1]=false;
                            break a;
                        }
                        else if((new Rectangle(j*100+10+85, i*60, 5,50).intersects(new Rectangle(ballX,ballY,40,40)))){
                            dx*=-1;
                            point+=5;
                            bonus++;
                            brick[i-1][j-1]=false;
                            break a;
                        }
                    }
                }
            }
            //System.out.println("num of brick break"+bonus);
        }
        
        if(life>=1&&!playState&&restart){
            reset();
            t.start();
            System.out.println("U hv a Life");
        }
        
        else if(life<=0){
            gameOver=true;
            t.stop();

        }
        else if(point>=brick.length*brick[0].length*5){
            playState=false;
            gameOver=true;
            t.stop();
            //--------------------------------
            repaint();
            JOptionPane.showMessageDialog(null, "\n\nLife   x   Point   =     Score\n   "+life +"     x       "+ point+"     =         "+score+"\n\n      Total Score   =   "+score+"\n\n\n", "You Win", JOptionPane.INFORMATION_MESSAGE);
            new personal().setVisible(true);
            
        }
        restart=false;
        score=life*point;
        repaint();
    }

    
    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(ke.getKeyCode()==KeyEvent.VK_SPACE && !playState){
            playState=true;
        }
        if(ke.getKeyCode()==KeyEvent.VK_ENTER && gameOver){
            playState=false;
            gameOver=false;
            resetGame();
            brickReset(true);
            t.start();
            repaint();
        }
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if(ke.getKeyCode()==KeyEvent.VK_RIGHT && player<740 && playState){
            player+=20;
        }
        if(ke.getKeyCode()==KeyEvent.VK_LEFT && player>20 && playState){
            player-=20;
        }
        
        if(ke.getKeyCode()==KeyEvent.VK_RIGHT && player<740 && !playState && !gameOver){
            dx=3;
            dy=2;
            player+=20;
            ballX+=20;
        }
        if(ke.getKeyCode()==KeyEvent.VK_LEFT && player>20 && !playState && !gameOver){
            dx=-3;
            dy=-2;
            player-=20;
            ballX-=20;
        }
        
        System.out.println("Player X= "+player);
    }
    
}
