package com.jafarkhq.reshelper.models;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jafar
 */
public class FileInfo {

    private static final String NINE_PATCH = ".9.png";
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_PNG = 1;
    public static final int TYPE_JPEG = 2;
    public static final int TYPE_GIF = 3;
    private int type;
    private long size;
    private int width;
    private int height;
    private String name;
    private String extension;
    private String fullName;
    private String path;
    private String qualifierName;
    private boolean is9patch;

    public FileInfo() {
    }

    public FileInfo(File file) {
        size = file.length();
        fullName = file.getName();
        qualifierName = file.getParentFile().getName();
        try {
            path = file.getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(FileInfo.class.getName()).log(Level.SEVERE, null, ex);
            path = file.getAbsolutePath();
        }

        if (fullName.toLowerCase(Locale.ENGLISH).endsWith(NINE_PATCH)) {
            is9patch = true;
        }

        extension = getFileExtension();
        name = getFileName();
        type = getFileType();
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the is9patch
     */
    public boolean is9patch() {
        return is9patch;
    }

    /**
     * @param is9patch the is9patch to set
     */
    public void setIs9patch(boolean is9patch) {
        this.is9patch = is9patch;
    }

    /**
     * @return is the file in valid android resource file
     */
    public boolean isValidAndroidRes() {
        if (TYPE_UNKNOWN == type) {
            return false;
        }

        return true;
    }

    public String getQualifierName(){
        return qualifierName;
    }

    private String getFileExtension() {
        if (null == fullName) {
            return null;
        }

        final char dot = '.';
        int pos = fullName.lastIndexOf(dot);
        if (pos == -1 || pos == 0) {
            return "";
        }

        return fullName.substring(pos + 1);
    }

    private String getFileName() {
        if (null == fullName) {
            return null;
        }

        final char dot = '.';
        int pos = fullName.lastIndexOf(dot);
        if (pos == -1 || pos == 0) {
            return fullName;
        }

        String fileName = fullName.substring(0, pos);
        if (is9patch) {
            pos = fileName.lastIndexOf(dot);
            if (pos == -1 || pos == 0) {
                is9patch = false;
                return fileName;
            }

            fileName = fullName.substring(0, pos);
        }

        return fileName;
    }

    private int getFileType() {
        final String lowerExtension = extension.toLowerCase(Locale.ENGLISH);
        if ("png".equals(lowerExtension)) {
            return TYPE_PNG;
        } else if ("jpeg".equals(lowerExtension) || "jpg".equals(lowerExtension)) {
            return TYPE_JPEG;
        } else if ("gif".equals(lowerExtension)) {
            return TYPE_GIF;
        }

        return TYPE_UNKNOWN;
    }

    public boolean renameFile(String newName){
        final File oldFile = new File(path);
        final File newFile = new File(oldFile.getParent(), newName);

        if(newFile.exists()){
            return false;
        }

        return oldFile.renameTo(newFile);
    }
}
