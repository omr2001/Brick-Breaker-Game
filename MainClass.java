import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        JFrame Jobject=new JFrame();
        GamePlay GP=new GamePlay();
        Jobject.setBounds(10,10,700,600);
        Jobject.setTitle("Brick Breaker Game");
        Jobject.setResizable(false);
        Jobject.setVisible(true);
        Jobject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Jobject.add(GP);

    }
}
