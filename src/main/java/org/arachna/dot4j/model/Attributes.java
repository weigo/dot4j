/**
 *
 */
package org.arachna.dot4j.model;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.arachna.dot4j.model.Attributes.KeyValuePair;

/**
 * @author Dirk Weigenand
 */
public class Attributes implements Iterable<KeyValuePair> {
    /**
     *
     */
    private final Map<String, String> attributes = new TreeMap<String, String>();

    public void setAttribute(final String key, final String value) {
        this.attributes.put(key, value);
    }

    public String getAttribute(final String key, final String value) {
        return this.attributes.get(key);
    }

    public boolean isEmpty() {
        return this.attributes.isEmpty();
    }

    public Iterator<KeyValuePair> iterator() {
        return new AttributeIterator(attributes);
    }

    public final class KeyValuePair {
        private final String key;

        private final String value;

        protected KeyValuePair(final String key, final String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }

    private class AttributeIterator implements Iterator<KeyValuePair> {
        Iterator<Map.Entry<String, String>> pairs;

        AttributeIterator(final Map<String, String> attributes) {
            pairs = attributes.entrySet().iterator();
        }

        public boolean hasNext() {
            return pairs.hasNext();
        }

        public KeyValuePair next() {
            final Map.Entry<String, String> entry = pairs.next();

            return new KeyValuePair(entry.getKey(), entry.getValue());
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
