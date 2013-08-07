/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jafarkhq.reshelper.renderers;

import com.jafarkhq.reshelper.background.LoadImageRunnable;
import com.jafarkhq.reshelper.background.LoadImagesWorker;
import com.jafarkhq.reshelper.models.ResourceInfo;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
    private BufferedImage mDefaultImage;

    public DrawablesListRenderer() {
        super();
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        drawableImage = new JLabel();

        add(drawableImage);

        drawableName = new JLabel();
        add(drawableName);

        mDefaultImage = new BufferedImage(PREFERD_IMAGE_SIZE, PREFERD_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics g = mDefaultImage.createGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, PREFERD_IMAGE_SIZE, PREFERD_IMAGE_SIZE);

    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        synchronized (drawableImage) {
            ResourceInfo resourceInfo = (ResourceInfo) value;
            drawableName.setText(resourceInfo.getName());
            drawableImage.putClientProperty(ResourceInfo.KEY_RESOURCE_INFO, resourceInfo);

            if (resourceInfo.getResourceThumbnail() != null) {
                final ImageIcon icon = new ImageIcon(resourceInfo.getResourceThumbnail());
                drawableImage.setIcon(icon);
            } else {
                final ImageIcon icon = new ImageIcon(mDefaultImage);
                drawableImage.setIcon(icon);
                LoadImagesWorker.getInstance().loadImage(new LoadImageRunnable(drawableImage, resourceInfo));
            }
        }

        return this;

    }
}
