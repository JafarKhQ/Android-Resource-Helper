package com.jafarkhq.reshelper.models;

import com.jafarkhq.reshelper.utils.ListUtils;
import com.jafarkhq.reshelper.utils.TextUtils;
import com.sun.xml.internal.ws.util.StringUtils;

import java.awt.image.BufferedImage;
import java.util.*;

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

        boolean firstItem = false;
        if (null == files || null==resQualifierMap) {
            firstItem = true;
            files = new ArrayList<FileInfo>();
            resQualifierMap = new HashMap<String, FileInfo>();
        }

        if(true==firstItem){
            name =  fileInfo.getName();
        } else if(false==name.equals(fileInfo.getName())){
            throw new IllegalArgumentException("This file \"" + fileInfo.getName() + "\" doesn't belong to \"" + name +"\" list");
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

    public boolean renameResource(String newName){
        if(TextUtils.isEmpty(newName)){
            return false;
        }

        boolean res = false;
        Iterator<FileInfo> iterator = files.iterator();
        while (iterator.hasNext()){
            final FileInfo info = iterator.next();
            res = info.renameFile(newName);
            if(false==res){
                break;
            }
        }

        if(true==res){
            name= findResName();
        }

        return res;
    }

    private String findResName(){
        if(ListUtils.isEmpty(files)){
            return "";
        }

        return files.get(0).getName();
    }

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
