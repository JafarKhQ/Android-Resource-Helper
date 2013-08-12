package com.jafarkhq.reshelper.models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jafar
 * Date: 4/5/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceInfo {
    public static final String KEY_RESOURCE_INFO = "com.jafarkhq.reshelper.models.ResourceInfo";

    private String name;
    private List<FileInfo> files;
    private Map<String, FileInfo> resQualifierMap;
    private BufferedImage mThumbnail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilesCount() {
        if (null == files) {
            return 0;
        }

        return files.size();
    }

    public void addFile(FileInfo fileInfo) {
        if (null == fileInfo) {
            return;
        }

        if (null == files || null==resQualifierMap) {
            files = new ArrayList<FileInfo>();
            resQualifierMap = new HashMap<String, FileInfo>();
        }

        files.add(fileInfo);
        resQualifierMap.put(fileInfo.getQualifierName(), fileInfo);
    }

    public FileInfo getFile(int position) {
        if (null == files) {
            return null;
        }

        return files.get(position);
    }

    public FileInfo getFileByQualifier(String qualifier) {
        if (null == resQualifierMap) {
            return null;
        }

        return resQualifierMap.get(qualifier);
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    //public void setFiles(List<FileInfo> files) {
    //     this.files = files;
    //   }

    @Override
    public String toString() {
        return getName();


    }

    public void setResourceThumbnail(BufferedImage thumbnail) {
        this.mThumbnail = thumbnail;

    }

    public BufferedImage getResourceThumbnail() {
        return this.mThumbnail;

    }

}
