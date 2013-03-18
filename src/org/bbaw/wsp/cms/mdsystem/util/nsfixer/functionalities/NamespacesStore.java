/**
 * <p>An instance of this {@link Map} contains all known namespaces.</p>
 * 
 * <p>You can use it like a normal {@link HashMap}:</p>
 * 
 * <ul>
 *      <li>Key: the namespace url (NOT the prefix!) -> caused by the founding problem</li>
 *      <li>Value: the namespace prefix</li>
 * </ul>
 * 
 * @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities;

import java.util.HashMap;

public class NamespacesStore extends HashMap<String, String> {
  private static final long serialVersionUID = 7392760553506941113L;

  /*
   * ######### Define prefixes here #########
   * 
   * feature for the future: options tab where the user can enter those himself?
   */
  private static final String OWL_URL = "http://www.w3.org/2002/07/owl#";
  private static final String OWL_PREFIX = "owl";
  private static final String DC_URL = "http://purl.org/dc/elements/1.1/";
  private static final String DC_PREFIX = "dc";
  private static final String ORE_PREFIX = "ore";
  private static final String ORE_URL = "http://www.openarchives.org/ore/terms/";
  private static final String DCTERMS_PREFIX = "dcterms";
  private static final String DCTERMS_URL = "http://purl.org/dc/terms/";
  private static final String GND_PREFIX = "gnd";
  private static final String GND_URL = "http://d-nb.info/standards/elementset/gnd#";
  private static final String XSD_URL = "http://www.w3.org/2001/XMLSchema#";
  private static final String XSD_PREFIX = "xsd";
  private static final String RDFS_PREFIX = "rdfs";
  private static final String RDFS_URL = "http://www.w3.org/2000/01/rdf-schema#";
  private static final String RDF_PREFIX = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
  private static final String RDF_URL = "rdf";
  private static final String FOAF_URL = "http://xmlns.com/foaf/0.1/";
  private static final String FOAF_PREFIX = "foaf";

  /*
   * ######### --------------------- #########
   */

  /**
   * Create a new namespaces store. All the known namespaces will be added.
   */
  public NamespacesStore() {
    initializeMap();
  }

  /**
   * Fill the map (the NamespacesStores instance itself). Use the URL as key and the prefix as value.
   */
  private void initializeMap() {
    super.put(OWL_URL, OWL_PREFIX);
    super.put(DC_URL, DC_PREFIX);
    super.put(ORE_URL, ORE_PREFIX);
    super.put(DCTERMS_URL, DCTERMS_PREFIX);
    super.put(GND_URL, GND_PREFIX);
    super.put(XSD_URL, XSD_PREFIX);
    super.put(RDFS_URL, RDFS_PREFIX);
    super.put(RDF_URL, RDF_PREFIX);
    super.put(FOAF_URL, FOAF_PREFIX);
  }

  /**
   * Get the namespace prefix to an input url.
   * 
   * @param inputNsUrl
   *          a String
   * @return the prefix as String in lower case, e.g. "dc" for input "http://purl.org/dc/elements/1.1/"
   */
  public String getPrefix(final String inputNsUrl) {
    return super.get(inputNsUrl);
  }
}
