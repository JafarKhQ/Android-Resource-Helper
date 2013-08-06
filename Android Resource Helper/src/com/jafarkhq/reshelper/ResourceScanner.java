package com.jafarkhq.reshelper;

import com.jafarkhq.reshelper.models.FileInfo;
import com.jafarkhq.reshelper.models.Resources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jafar
 */
public class ResourceScanner {

    private static final String RES_DIRECTORY = "res";
    private static final String DRAWABLE_DIRECTORY = "drawable";
    private File resRootDir;
    private Resources resourcesTree;

    public ResourceScanner(File projectDir) {
        resourcesTree = new Resources();
        resRootDir = new File(projectDir, RES_DIRECTORY);
    }

    public void startScanner() {
        if (null == resRootDir) {
            return;
        }

        if (false == resRootDir.exists()) {
            return;
        }

        if (false == resRootDir.isDirectory()) {
            return;
        }

        if (false == resRootDir.canWrite()) {
            return;
        }


        File listFiles[] = resRootDir.listFiles();
        if (null == listFiles) {
            return;
        }

        List<File> drawableDirs = new ArrayList<File>();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isDirectory()) {
                String dirName = listFiles[i].getName();
                if (dirName.startsWith(DRAWABLE_DIRECTORY)) {
                    drawableDirs.add(listFiles[i]);
                }
            }
        }

        if (drawableDirs.size() <= 0) {
            return;
        }

        for (int i = 0; i < drawableDirs.size(); i++) {
            resourcesTree.addFilesList(scaneDir(drawableDirs.get(i)));
        }
    }

    public Resources getResourcesTree(){
        return resourcesTree;
    }

    private List<FileInfo> scaneDir(File dir) {
        List<FileInfo> files = new ArrayList<FileInfo>();

        File listFiles[] = dir.listFiles();
        if (null == listFiles) {
            return files;
        }

        for (int i = 0; i < listFiles.length; i++) {
            if (false == listFiles[i].isFile()) {
                continue;
            }

            FileInfo info = new FileInfo(listFiles[i]);
            if (info.isValidAndroidRes()) {
                files.add(info);
            }
        }

        return files;
    }
}
