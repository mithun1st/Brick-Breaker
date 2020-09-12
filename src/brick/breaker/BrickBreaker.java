
package brick.breaker;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.JFrame;


public class BrickBreaker {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        
        Image i=Toolkit.getDefaultToolkit().getImage("//BrickBreaker/ic.png");
        f.setIconImage(i);
        
        playClass c=new playClass();
        
     
        f.setTitle("Brick Breaker Develop by Mahadi Hassan MITHUN");
        f.setBounds(0, 0, 900, 600);
        f.setLocationRelativeTo(f);
        f.setResizable(false);
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
        
        f.add(c);
        
        
    }

}
