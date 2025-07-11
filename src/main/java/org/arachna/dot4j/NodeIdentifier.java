package org.arachna.dot4j;

/**
 * This interface should be implemented by business objects/adapters in order
 * to supply unique identifiers based on the given business object instance.
 */
public interface NodeIdentifier {
    String getNodeName();
}
