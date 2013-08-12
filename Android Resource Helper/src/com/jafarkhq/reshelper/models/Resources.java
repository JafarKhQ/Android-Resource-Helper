package com.jafarkhq.reshelper.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jafar
 * Date: 4/5/13
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Resources {

    private List<ResourceInfo> drawables;

    public void addFilesList(List<FileInfo> files) {
        if (null == files) {
            return;
        }

        for (int i = 0; i < files.size(); i++) {
            addFile(files.get(i));
        }
    }

    public void addFile(FileInfo fileInfo) {
        if (null == fileInfo) {
            return;
        }
            addDrawable(fileInfo);
    }

    public void addDrawable(FileInfo fileInfo) {
        if (null == fileInfo) {
            return;
        }

        if (null == drawables) {
            drawables = new ArrayList<ResourceInfo>();
        }

        ResourceInfo resourceInfo = null;
        for (int i = 0; i < drawables.size(); i++) {
            if (drawables.get(i).getName().equals(fileInfo.getName())) {
                resourceInfo = drawables.get(i);
                break;
            }
        }

        if (null == resourceInfo) {
            resourceInfo = new ResourceInfo();
            resourceInfo.setName(fileInfo.getName());
            drawables.add(resourceInfo);
        }

        resourceInfo.addFile(fileInfo);
    }



    public int getDrawablesCount() {
        if (null == drawables) {
            return 0;
        }

        return drawables.size();
    }

    public ResourceInfo getDrawable(int position) {
        if (null == drawables) {
            return null;
        }

        return drawables.get(position);
    }


}
