package com.jafarkhq.reshelper.frames;

import com.jafarkhq.reshelper.ResourceScanner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author jafar
 */
public class MainFrame extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(MainFrame.class
            .getName());
    private javax.swing.JScrollPane mLeftSidePanel;
    private JScrollPane mRightSidePanel;
    private javax.swing.JTree mFilesTree;
    
    private JTextField mSearchTextField;
    // http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-JTree.html
    // http://docs.oracle.com/javase/tutorial/uiswing/components/tree.html
    // http://www.java2s.com/Tutorial/Java/0240__Swing/1120__JTree.htm

    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        initOptionsMenu();
        
        initLeftSidePanel();
        initRightSidePanel();
        
        initMainAppLayout();

    }

    private void initLeftSidePanel() {
        mLeftSidePanel = new javax.swing.JScrollPane();
        mFilesTree = new javax.swing.JTree();
        
        Container dummy = new Container();
        BorderLayout borderLayout = new BorderLayout();
        dummy.setLayout(borderLayout);
        
        mSearchTextField = new JTextField();
        dummy.add(mSearchTextField, BorderLayout.NORTH);
        
        mLeftSidePanel.setViewportView(dummy);
    }

    private void initRightSidePanel() {
        mRightSidePanel = new JScrollPane();
        mRightSidePanel.setBackground(Color.BLACK);

    }

    private void initMainAppLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addComponent(mLeftSidePanel, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
                .addComponent(mRightSidePanel));

        layout.setVerticalGroup(
                layout.createParallelGroup()
                .addComponent(mLeftSidePanel)
                .addComponent(mRightSidePanel));

        pack();

    }

    private void initOptionsMenu() {
        JMenuItem openMenuItem = new JMenuItem();
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });


        JMenuItem exitMenuItem = new JMenuItem();
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        JPopupMenu.Separator menuSeparator = new JPopupMenu.Separator();
        JMenu menu = new JMenu();
        menu.setText("File");
        menu.add(openMenuItem);
        menu.add(menuSeparator);
        menu.add(exitMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

    }

    private void initLeftPanel() {
    }

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retVal = chooser.showOpenDialog(MainFrame.this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File projectDir = chooser.getSelectedFile();
            LOGGER.info(projectDir.getAbsolutePath());
            ResourceScanner resourceScanner = new ResourceScanner(projectDir);
            resourceScanner.startScanner();
            //TODO:


        }
    }

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        dispose();

        System.exit(0);
    }
}
