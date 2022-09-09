//package gui;
//
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//public class KeyListenerTester extends JFrame implements KeyListener {
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println("Right key typed");
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key typed");
//        }
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println("Right key pressed");
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key pressed");
//        }
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println("Right key Released");
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key Released");
//        }
//    }
//}