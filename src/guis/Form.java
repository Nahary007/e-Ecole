package guis;

import constants.CommonConstants;

import javax.swing.*;

public class Form extends  JFrame{
    //create constructor
    public Form(String title) {

        //set the title for the title bar
        super(title);

        setSize(520, 680);

        //configure GUI to end process after closing
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);
    }
}
