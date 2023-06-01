package com.cy.store.commom.copyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 要复制文件夹中的一个特定文件，并将其根目录一起复制到目标位置
 */
public class FileCopy {
    public static void copyFileWithRootDirectory(File sourceFile, File targetDir) throws IOException {
        if (sourceFile.isFile()) {
            // 获取源文件所在的根目录
            File rootDir = sourceFile.getParentFile();

            // 构建目标文件的路径，保留源文件的根目录结构
            String relativePath = rootDir.toURI().relativize(sourceFile.toURI()).getPath();
            File targetFile = new File(targetDir, relativePath);

            // 创建目标文件所在的目录
            File targetParentDir = targetFile.getParentFile();
            if (!targetParentDir.exists()) {
                targetParentDir.mkdirs();
            }

            // 复制文件到目标位置
            Path sourcePath = sourceFile.toPath();
            Path targetPath = targetFile.toPath();
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void main(String[] args) {
        // 示例用法
        // 修改路径即可实现  sourceFile是源文件所在的文件夹路径  targetDir目标文件夹的路径
        File sourceFile = new File("E:\\lckj\\IdeaProjects\\src\\main\\java\\com\\yykj\\app\\lckj\\prodSecond\\service\\impl\\WzGysHsZzVerifyServiceImpl.java");
        File targetDir = new File("E:\\test\\lckj\\IdeaProjects\\src\\main\\java\\com\\yykj\\app\\lckj\\prodSecond\\service\\impl");

        try {
            copyFileWithRootDirectory(sourceFile, targetDir);
            System.out.println("文件复制完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


