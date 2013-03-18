/**
 * 
 */
package org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities;

import java.io.File;
import java.io.FileFilter;

/**
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class RdfFileFilter implements FileFilter {

  @Override
  /*
   * (non-Javadoc)
   * 
   * @see java.io.FileFilter#accept(java.io.File)
   */
  public boolean accept(final File pathname) {
    return pathname.toString().toLowerCase().endsWith(".rdf");
  }

}
