/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jafarkhq.reshelper.models;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author mahdihijazi
 */
public class DrawablesListModel extends AbstractListModel {

    public static interface Filter {

        boolean accept(Object element);
    }
    private Filter mFilter;
    private final ArrayList<Integer> mIndecies = new ArrayList<Integer>();
    private Resources mResources;

    public DrawablesListModel(Resources resources) {
        if (resources == null) {
            resources = new Resources();

        }

        this.mResources = resources;

        addListDataListener(contentsChangedListener);
    }

    @Override
    public int getSize() {
        return (mFilter != null) ? mIndecies.size() : mResources.getDrawablesCount();

    }

    @Override
    public Object getElementAt(int i) {
        return (mFilter != null) ? mResources.getDrawable(mIndecies.get(i)) : mResources.getDrawable(i);

    }

    public void setFilter(Filter f) {
        mFilter = f;
        doFilter();
    }

    private void doFilter() {
        mIndecies.clear();

        Filter f = mFilter;
        if (f != null) {
            int count = mResources.getDrawablesCount();
            for (int i = 0; i < count; i++) {
                Object element = mResources.getDrawable(i);
                if (f.accept(element)) {
                    mIndecies.add(i);
                }
            }

        }

        // avoid infinite recursive calls
        removeListDataListener(contentsChangedListener);
        fireContentsChanged(this, 0, getSize() - 1);
        addListDataListener(contentsChangedListener);
    }
    
    private ListDataListener contentsChangedListener = new ListDataListener() {
        @Override
        public void intervalAdded(ListDataEvent lde) {
            doFilter();
        }

        @Override
        public void intervalRemoved(ListDataEvent lde) {
            doFilter();
        }

        @Override
        public void contentsChanged(ListDataEvent lde) {
            doFilter();
        }
    };
}
