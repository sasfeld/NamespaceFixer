/**
 * <p>Instances of this class offers two methods to fix incoming rdf files.</p>
 * 
 * <p>It uses the {@link NamespacesStore} objects.</p>
 * 
 * @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamespaceFixer extends Observable {
  private final NamespacesStore namespacesStore;
  private File skipToFile;

  public NamespaceFixer() {
    namespacesStore = new NamespacesStore();
  }

  /**
   * Fix a single file.
   * 
   * @param inputFile
   *          the input file.
   * @param outputFile
   *          the output file.
   * @param forceOverride
   *          if you want to force the override of the existing output file.
   * @throws InvalidSelectionException
   *           if the inputFile doesn't exist or the output file can't be written.
   * @throws NeedToOverrideException
   *           if the output file already exists. You can then choose to set the forceOption to true.
   */
  public void fixFile(final File inputFile, final File outputFile, final boolean forceOverride) throws InvalidSelectionException, NeedToOverrideException {
    if (!inputFile.exists()) {
      throw new InvalidSelectionException("Please enter an existing file. Your input: " + inputFile.toString());
    }
    Scanner inputReader = null;
    try {
      setChanged();
      this.notifyObservers("Trying to fix file " + inputFile);

      inputReader = new Scanner(new FileReader(inputFile));
      final Map<String, String> invalidNamespaces = fetchInvalidNs(inputReader);
      inputReader.close();

      final Scanner outputReader = new Scanner(new FileReader(inputFile));
      final String outputString = replaceInvalidNs(outputReader, invalidNamespaces);

      if (!outputFile.exists() || forceOverride) { // true if the file doesn't exist or you force the override
        writeOutputFile(outputFile, outputString);
      } else {
        throw new NeedToOverrideException("Override existing file " + outputFile.getAbsolutePath() + "?", "Override file", outputFile, inputFile, false);
      }

      inputReader.close();
      outputReader.close();
      setChanged();
      this.notifyObservers("Fixed file " + inputFile);
    } catch (final FileNotFoundException e) {
      throw new InvalidSelectionException("Please enter an existing file. Your input: " + inputFile.toString());
    }

  }

  /**
   * Write an output file.
   * 
   * @param outputFile
   * @param outputString
   * @throws InvalidSelectionException
   *           if the file can't get written.
   */
  private void writeOutputFile(final File outputFile, final String outputString) throws InvalidSelectionException {
    FileWriter writer = null;
    try {
      writer = new FileWriter(outputFile);
      writer.write(outputString);
      writer.flush();
      writer.close();
    } catch (final IOException e) {
      throw new InvalidSelectionException("Can't write output file " + outputFile + "! You don't have the OS rights.");
    }
  }

  /**
   * Replace invalid namespaces.
   * 
   * @param inputScanner
   *          the (new) scanner instance.
   * @param invalidNamespaces
   *          a {@link Map} of the form: key: invalid prefix; value: namespace url
   * @return
   */
  private String replaceInvalidNs(final Scanner inputScanner, final Map<String, String> invalidNamespaces) {
    final Set<String> invalidNamespacesSet = invalidNamespaces.keySet();
    final StringBuilder outputBuilder = new StringBuilder();
    while (inputScanner.hasNext()) { // scanner should be at first child now
      final String actLine = inputScanner.nextLine();
      String outputLine = actLine;
      for (final String prefix : invalidNamespacesSet) {
        if (actLine.contains(prefix)) {
          /*
           * ################### namespace declarations ###################
           */
          final Pattern invalidNsHeadPattern = Pattern.compile("(?i)xmlns:(j.[0-9]+)=\"(.*?)\"(?i)");
          for (final Matcher m = invalidNsHeadPattern.matcher(actLine); m.find();) {
            final String nsToReplace = m.group(1); // matchedf invalid namespace
            final String nsUrl = invalidNamespaces.get(nsToReplace); // the url of the invalid namespace
            final String newNs = namespacesStore.getPrefix(nsUrl); // look for the correct prefix
            if (newNs != null) {
              outputLine = outputLine.replace(nsToReplace, newNs);
            }
          }
          /*
           * ################### ---------------------- ###################
           */

          /*
           * ################### namespace usage ###################
           */
          final Pattern invalidNsPattern = Pattern.compile("(?i)</?(j.[0-9]+):\\w+?(?i)");
          for (final Matcher m = invalidNsPattern.matcher(actLine); m.find();) {
            final String nsToReplace = m.group(1); // matchedf invalid namespace
            final String nsUrl = invalidNamespaces.get(nsToReplace); // the url of the invalid namespace
            final String newNs = namespacesStore.getPrefix(nsUrl); // look for the correct prefix
            if (newNs != null) {
              outputLine = outputLine.replace(nsToReplace, newNs);
            }
          }
          /*
           * ################### ---------------- ###################
           */
        }
      }
      outputBuilder.append(outputLine + "\n");
    }
    return outputBuilder.toString();
  }

  /**
   * Scan the xml base element for invalid namespaces.
   * 
   * @param inputScanner
   * @return the {@link Map}. May be empty (e.g. if the special damages weren't found.
   */
  private Map<String, String> fetchInvalidNs(final Scanner inputScanner) {
    // key: invalid ns prefix; value: ns url
    final HashMap<String, String> invalidNs = new HashMap<String, String>();

    boolean isRdf = false;
    while (inputScanner.hasNext()) {
      final String actLine = inputScanner.next();

      if (actLine.toLowerCase().contains("rdf:rdf")) { // start: xml base element
        isRdf = true; // now the pattern can be used
      } else if (actLine.toLowerCase().contains(">")) { // end: xml base element
        isRdf = false;
      } else if (isRdf) {
        /*
         * Invalid namespace pattern:
         * 
         * e.g. xmlns:j.0="http://www.openarchives.org/ore/terms/"
         * 
         * (?i) means: don't care whether the characters are upper or lower case
         */
        final Pattern invalidNsPattern = Pattern.compile("(?i)xmlns:(j.[0-9]+)=\"(.*?)\"(?i)");
        for (final Matcher m = invalidNsPattern.matcher(actLine); m.find();) {
          final String badPrefix = m.group(1);
          final String goodNamespaceUrl = m.group(2);
          invalidNs.put(badPrefix, goodNamespaceUrl);
        }
      }
    }
    return invalidNs;
  }

  /**
   * Fix all rdf files in a dir.
   * 
   * @param inputDir
   *          the input {@link File}. All files in the upper level will be fixed. The {@link RdfFileFilter} is used to filter children.
   * @param outputDir
   *          the output {@link File}. Will cause an {@link InvalidSelectionException} if forceOverride is false. *
   * @param forceOverride
   *          set to true if you want to override the output files.
   * @throws InvalidSelectionException
   *           if the output files can't be created
   * @throws NeedToOverrideException
   *           if you try to override and forceOverride is set to false.
   */
  public void fixDir(final File inputDir, final File outputDir, final boolean forceOverride) throws InvalidSelectionException, NeedToOverrideException {
    File skipTo = null;
    if (skipToFile != null) {
      skipTo = skipToFile;
      skipToFile = null;
    }
    for (final File child : inputDir.listFiles(new RdfFileFilter())) {
      if (skipTo != null && !child.equals(skipTo)) {
        continue;
      } else if (skipTo != null && child.equals(skipTo)) {
        skipTo = null;
        continue;
      }
      // continue here if skipTo = null
      final File outputFile = new File(outputDir, child.getName());
      if (!outputFile.exists()) {
        try {
          if (!outputDir.exists()) {
            outputDir.mkdir();
          }
          outputFile.createNewFile();
          fixFile(child, outputFile, true);
        } catch (final IOException e) {
          throw new InvalidSelectionException("Can't create file " + outputFile + "! You don't have the OS rights.");
        }
      } else {
        throw new NeedToOverrideException("Override existing file " + outputFile.getAbsolutePath() + "?", "Override file", outputFile, child, true);
      }
    }
  }

  /**
   * Skip to a file.
   * 
   * @param inputFile
   */
  public void skipTo(final File inputFile) {
    skipToFile = inputFile;
  }
}
