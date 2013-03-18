/**
 * 
 */
package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * General util class for GUI stuff.
 * 
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class GuiUtils {
  /**
   * Specify the standard directory for the file chooser things here.
   */
  private static final String STANDARD_DIR = System.getProperty("user.home");
  private static final String SAVE_FILE_NAME = "settings.ser";

  /**
   * Show a file chooser for an OPEN dialog.
   * 
   * @param startdir
   *          an (optional) start dir. If null, STANDARD_DIR will be used.
   * @param filter
   *          an {@link FileFilter}. If null, no filter will be used.
   * @param allowDirectories
   *          set to true if you want to accept directories
   * 
   * 
   * @return the chosen File or null if the selection was aborted.
   */
  public static File showOpenFileChooser(final String startdir, final FileFilter filter, final boolean allowDirectories) {
    final JFileChooser chooser;
    if (startdir != null) {
      chooser = new JFileChooser(startdir);
    } else {
      chooser = new JFileChooser(STANDARD_DIR);
    }
    if (filter != null) {
      chooser.setFileFilter(filter);
    }
    if (allowDirectories) {
      chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }
    final int chooserReturn = chooser.showOpenDialog(FixerGui.getInstance());

    if (chooserReturn == JFileChooser.APPROVE_OPTION) {
      return chooser.getSelectedFile();
    }
    return null;
  }

  /**
   * Save settings to a target file.
   * 
   * @param targetDir
   * @param record
   */
  public static void saveSettings(final File targetDir, final SelectionRecord record) {
    if (record.selectionPerformed()) {
      if (!targetDir.exists()) {
        targetDir.mkdir();
      }
      FileOutputStream outputStream;
      try {
        final File targetFile = new File(targetDir, SAVE_FILE_NAME);
        if (!targetFile.exists()) {
          targetFile.createNewFile();
        }
        outputStream = new FileOutputStream(targetFile);
        final ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(record);
        os.flush();
        os.close();
        outputStream.close();
      } catch (final IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Load settings from a target file.
   * 
   * @param targetDir
   * @return the {@link SelectionRecord} or null
   */
  public static SelectionRecord loadSettings(final File targetDir) {
    final File targetFile = new File(targetDir, SAVE_FILE_NAME);
    if (targetFile.exists()) {
      FileInputStream inputStream;
      try {
        inputStream = new FileInputStream(targetFile);
        final ObjectInputStream is = new ObjectInputStream(inputStream);
        final Object readObject = is.readObject();
        SelectionRecord returnRecord = null;
        if (readObject instanceof SelectionRecord) {
          returnRecord = (SelectionRecord) readObject;
        }
        is.close();
        inputStream.close();

        return returnRecord;
      } catch (final IOException | ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return null;
  }
}
