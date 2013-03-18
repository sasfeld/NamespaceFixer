/**
 * 
 */
package org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities;

/**
 * <p>
 * This exception class stores all exceptions which shall be presented to the user.
 * </p>
 * 
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class InvalidSelectionException extends Exception {

  private static final long serialVersionUID = 2985265627055408872L;

  public InvalidSelectionException(final String errorMsg) {
    super(errorMsg);
  }
}
