/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.server.DownloadStream;
import com.vaadin.server.FileResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author Сергей
 */
public class FileDownloadResource extends FileResource {

        public FileDownloadResource(File sourceFile) {
            super(sourceFile);
        }

        public DownloadStream getStream() {
            try {
                final DownloadStream ds = new DownloadStream(
                        new FileInputStream(getSourceFile()), getMIMEType(),
                        getFilename());
                ds.setParameter("Content-Disposition", "attachment; filename=" +getFilename());
                ds.setCacheTime(getCacheTime());
                return ds;
            } catch (final FileNotFoundException e) {
                // No logging for non-existing files at this level.
                return null;
            }
        }
    }
