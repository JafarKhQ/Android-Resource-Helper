/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jafarkhq.reshelper.renderers;

import com.jafarkhq.reshelper.models.ResourceInfo;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author mahdihijazi
 */
public class DrawablesListRenderer extends JPanel implements ListCellRenderer {

    private static final int PREFERD_IMAGE_SIZE = 40;
    private JLabel drawableImage;
    private JLabel drawableName;

    public DrawablesListRenderer() {
        super();
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        drawableImage = new JLabel();

        add(drawableImage);

        drawableName = new JLabel();
        add(drawableName);



    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ResourceInfo resourceInfo = (ResourceInfo) value;
        drawableName.setText(resourceInfo.getName());

        try {
            BufferedImage img = null;
            img = ImageIO.read(new File(resourceInfo.getFile(0).getPath()));
            img = resize(img, PREFERD_IMAGE_SIZE, PREFERD_IMAGE_SIZE);
            ImageIcon icon = new ImageIcon(img);
            drawableImage.setIcon(icon);
        } catch (IOException e) {
        }

        return this;

    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
}
