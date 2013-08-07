/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jafarkhq.reshelper.background;

import com.jafarkhq.reshelper.models.ResourceInfo;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author mahdihijazi
 */
public class LoadImageRunnable implements Runnable {

    private JLabel mImageLabel;
    private ResourceInfo mResourceInfo;
    private static final int PREFERD_IMAGE_SIZE = 40;

    public LoadImageRunnable(JLabel imageLabel, ResourceInfo resourceInfo) {
        this.mImageLabel = imageLabel;
        this.mResourceInfo = resourceInfo;

    }

    @Override
    public void run() {
        try {
            BufferedImage img = null;
            img = ImageIO.read(new File(mResourceInfo.getFile(0).getPath()));
            img = resize(img, PREFERD_IMAGE_SIZE, PREFERD_IMAGE_SIZE);
            mResourceInfo.setResourceThumbnail(img);
            synchronized (mImageLabel) {
                ResourceInfo resourceInfo = (ResourceInfo) mImageLabel.getClientProperty(ResourceInfo.KEY_RESOURCE_INFO);
                if (resourceInfo.getName().equals(mResourceInfo.getName())) {
                    final ImageIcon icon = new ImageIcon(img);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            mImageLabel.setIcon(icon);

                        }
                    });
                }
            }


        } catch (IOException e) {
            System.err.println(e.toString());
        }

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
