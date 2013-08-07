/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jafarkhq.reshelper.models;

import javax.swing.AbstractListModel;

/**
 *
 * @author mahdihijazi
 */
public class DrawablesListModel extends AbstractListModel {

    private Resources mResources;

    public DrawablesListModel(Resources resources) {
        if (resources == null) {
            resources = new Resources();

        }

        this.mResources = resources;

        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public int getSize() {
        return mResources.getDrawablesCount();

    }

    @Override
    public Object getElementAt(int i) {
        return mResources.getDrawable(i);

    }
}
