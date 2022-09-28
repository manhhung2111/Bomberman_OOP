package Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int num_code = e.getKeyCode();
        if(num_code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(num_code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(num_code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (num_code == KeyEvent.VK_D){
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int num_code = e.getKeyCode();
        if(num_code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(num_code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(num_code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (num_code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
