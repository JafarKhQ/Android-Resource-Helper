package com.jafarkhq.reshelper.frames;

import com.jafarkhq.reshelper.ResourceScanner;

import java.io.File;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;

/**
 *
 * @author jafar
 */
public class MainFrame extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(MainFrame.class
            .getName());

    private javax.swing.JScrollPane mScrollPane;
    private javax.swing.JTree mFilesTree;
    // http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-JTree.html
    // http://docs.oracle.com/javase/tutorial/uiswing/components/tree.html
    // http://www.java2s.com/Tutorial/Java/0240__Swing/1120__JTree.htm

    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        mScrollPane = new javax.swing.JScrollPane();
        mFilesTree = new javax.swing.JTree();
        mScrollPane.setViewportView(mFilesTree);

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

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mScrollPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(765, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mScrollPane, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addContainerGap()));

        pack();
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
