package org.greengin.nquireit.logic.data;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class FileManagerBean implements InitializingBean {

    @Autowired
    private ServletContext context;

    @Getter
    File path;

    @Getter
    File internalPath;

    @Setter
    String basePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        path = new File(this.context.getRealPath("") + "/" + basePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        internalPath = new File(path, "internal");
        if (!internalPath.exists()) {
            internalPath.mkdirs();
        }

    }


    public String uploadFile(String context, String filename, InputStream input) throws IOException {
        File folder = new File(path, context);

        if (folder.exists() || folder.mkdirs()) {

            String name = null;
            String extension = null;
            filename = filename.replaceAll(" ", "_");
            int extSepPos = filename.lastIndexOf('.');
            if (extSepPos > 0) {
                name = filename.substring(0, extSepPos);
                extension = filename.substring(extSepPos);
            }

            String testFilename = null;
            File f = null;
            for (int i = 0; ; i++) {
                testFilename = i > 0 ? String.format("%s_%d%s", name, i, extension) : filename;
                f = new File(folder, testFilename);
                if (!f.exists()) {
                    break;
                }
            }

            FileOutputStream output = new FileOutputStream(f);
            IOUtils.copy(input, output);
            output.close();

            return String.format("%s/%s", context, testFilename);
        } else {
            return null;
        }
    }

    public File get(String filename) {
        return new File(path, filename);
    }


    public File getThumb(String thumb) {
        String filename;
        String contextPath;
        String ext;
        int sepPos = thumb.lastIndexOf('/') + 1;
        if (sepPos > 0) {
            filename = thumb.substring(sepPos);
            contextPath = thumb.substring(0, sepPos);
        } else {
            filename = thumb;
            contextPath = "/";
        }

        int extSepPos = filename.lastIndexOf('.');
        if (extSepPos > 0) {
            ext = filename.substring(extSepPos + 1).toLowerCase();
        } else {
            ext = "";
        }

        File original = get(thumb);
        if (original.exists()) {
            File folder = new File(this.internalPath, "/thumbs" + contextPath);

            if (folder.exists() || folder.mkdirs()) {

                File file = new File(folder, filename);
                if (file.exists()) {
                    return file;
                }

                try {
                    BufferedImage img = ImageIO.read(original);
                    BufferedImage scaledImg = Scalr.resize(img, 200);
                    ImageIO.write(scaledImg, ext, file);
                    return file;
                } catch (IOException e) {
                    return null;
                }
            }
        }

        return null;
    }
}
