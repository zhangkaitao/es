package com.sishuok.maven.plugins.codegen;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JMenuBar jJMenuBar = null;
    private JMenu jMenu = null;
    private JMenuItem userListMenuItem = null;
    private JMenuItem userAddMenuItem = null;
    private JMenuItem userQueryMenuItem = null;
    private JMenu bookJMenu1 = null;
    private JMenuItem bookListMenuItem = null;
    private JMenuItem bookAddMenuItem = null;
    private JMenuItem bookQueryMenuItem = null;
    private JMenu stockMenu1 = null;
    private JMenuItem stockListMenuItem = null;
    private JMenuItem stockAddMenuItem = null;
    private JMenu inBookMenu1 = null;
    private JMenuItem inBookListMenuItem = null;
    private JMenuItem inBookAddMenuItem = null;
    private JMenu logoutjMenu;  //  @jve:decl-index=0:
    private JMenuItem logoutMenuItem;

    /**
     * This method initializes jJMenuBar	
     *
     * @return javax.swing.JMenuBar
     */
    public JMenuBar getJJMenuBar() {
        //永真
        jJMenuBar = new JMenuBar();
        jJMenuBar.setPreferredSize(new Dimension(0, 30));
        jJMenuBar.add(getLogoutJMenu());

        return jJMenuBar;
    }

    /**
     * This method initializes jMenu    
     *
     * @return javax.swing.JMenu
     */
    private JMenu getLogoutJMenu() {
        if (logoutjMenu == null) {
            logoutjMenu = new JMenu();
            logoutjMenu.setSize(new Dimension(50, 10));
            logoutjMenu.setText("退出管理");
            logoutjMenu.setPreferredSize(new Dimension(70, 10));
            logoutjMenu.add(getLogoutMenuItem());
        }
        return logoutjMenu;
    }



    private JMenuItem getLogoutMenuItem() {
        if (logoutMenuItem == null) {
            logoutMenuItem = new JMenuItem();
            logoutMenuItem.setText("退出");

        }
        return logoutMenuItem;
    }



    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Application thisClass = new Application();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * This is the default constructor
     */
    public Application() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(650, 550);

        this.setContentPane(getJContentPane());
        this.setTitle("JFrame");
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
        }



        return jContentPane;
    }

}  //  @jve:decl-index=0:visual-constraint="156,47"
