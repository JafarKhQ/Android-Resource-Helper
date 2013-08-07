package com.jafarkhq.reshelper.frames;

import com.jafarkhq.reshelper.ResourceScanner;
import com.jafarkhq.reshelper.models.DrawablesListModel;
import com.jafarkhq.reshelper.models.ResourceInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author jafar
 */
public class MainFrame extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(MainFrame.class
            .getName());
    private javax.swing.JScrollPane mLeftSidePanel;
    private JScrollPane mRightSidePanel;
    private JTextField mSearchTextField;
    private JList mDrawablesList;
    private DrawablesListModel mDrawablesListModel;
    private JProgressBar scannerProgressBar;

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

        Container dummy = new Container();
        BorderLayout borderLayout = new BorderLayout();
        dummy.setLayout(borderLayout);

        mSearchTextField = new JTextField();
        mSearchTextField.getDocument().addDocumentListener(searchTextFieldListener);
        dummy.add(mSearchTextField, BorderLayout.NORTH);

        mDrawablesList = new JList();
        mDrawablesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dummy.add(mDrawablesList, BorderLayout.CENTER);

        scannerProgressBar = new JProgressBar();
        scannerProgressBar.setIndeterminate(true);
        scannerProgressBar.setVisible(false);
        dummy.add(scannerProgressBar, BorderLayout.SOUTH);

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

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retVal = chooser.showOpenDialog(MainFrame.this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File projectDir = chooser.getSelectedFile();
            LOGGER.info(projectDir.getAbsolutePath());

            new ScannerWorker(projectDir).start();;

        }
    }

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        dispose();

        System.exit(0);
    }

    private void setDrawablesListModelFilter(final String filter) {
        if (mDrawablesListModel != null) {
            if (filter != null && filter.trim().length() > 0) {
                mDrawablesListModel.setFilter(new DrawablesListModel.Filter() {
                    @Override
                    public boolean accept(Object element) {
                        ResourceInfo resourceInfo = (ResourceInfo) element;
                        return resourceInfo.getName().contains(filter);

                    }
                });

            } else {
                mDrawablesListModel.setFilter(null);
            }
        }

    }

    class ScannerWorker extends Thread {

        private File mProjectDir;

        public ScannerWorker(File projectDir) {
            this.mProjectDir = projectDir;

        }

        @Override
        public void run() {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    scannerProgressBar.setVisible(true);
                }
            });

            ResourceScanner resourceScanner = new ResourceScanner(mProjectDir);
            resourceScanner.startScanner();
            mDrawablesListModel = new DrawablesListModel(resourceScanner.getResourcesTree());
            mDrawablesList.setModel(mDrawablesListModel);

            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    scannerProgressBar.setVisible(false);

                }
            });

        }
    }
    private DocumentListener searchTextFieldListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent de) {
            setDrawablesListModelFilter(mSearchTextField.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            setDrawablesListModelFilter(mSearchTextField.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            setDrawablesListModelFilter(mSearchTextField.getText());
        }
    };
}
