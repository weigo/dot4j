/**
 *
 */
package org.arachna.dot4j.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.arachna.dot4j.model.Attributes.Attribute;

/**
 * Attributes for a edges, nodes and graphs.
 * 
 * @author Dirk Weigenand
 */
public class Attributes implements Iterable<Attribute> {
    /**
     * attribute storage.
     */
    private final Map<String, Attribute> attributes = new TreeMap<String, Attribute>();

    /**
     * Set an attribute value.
     * 
     * @param name
     *            attribute name
     * @param value
     *            attribute value
     */
    public void setAttribute(final String name, final String value) {
        this.attributes.put(name, new Attribute(name, value));
    }

    /**
     * Return the value for the given attribute name.
     * 
     * @param name
     *            name of the attribute.
     * @return the attribute for the given attribute name, <code>null</code> if
     *         there was no attribute registered with this name
     */
    public Attribute getAttribute(final String name) {
        return this.attributes.get(name);
    }

    /**
     * Query whether there are any attributes.
     * 
     * @return <code>true</code> when there are attributes registered with this
     *         attribute container, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return this.attributes.isEmpty();
    }

    /**
     * Return an {@link Iterator} over the registered attributes.
     * 
     * @return an Iterator over the registered attributes.
     */
    public Iterator<Attribute> iterator() {
        return new AttributeIterator(attributes.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Attributes [attributes=" + attributes + "]";
    }

    /**
     * An attribute with a name and a corresponding value.
     * 
     * @author Dirk Weigenand
     */
    public final class Attribute {
        /**
         * the name of this attribute.
         */
        private final String name;

        /**
         * the value of this attribute.
         */
        private final String value;

        /**
         * Create a new attribute using the given name and value.
         * 
         * @param name
         *            attribute name
         * @param value
         *            attribute value
         */
        protected Attribute(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Return the name of this attribute.
         * 
         * @return the name of this attribute.
         */
        public String getName() {
            return name;
        }

        /**
         * Return the value of this attribute.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Attribute [name=" + name + ", value=" + value + "]";
        }
    }

    /**
     * An iterator over attributes.
     * 
     * @author Dirk Weigenand
     * 
     */
    private class AttributeIterator implements Iterator<Attribute> {
        /**
         * attribute iterator.
         */
        Iterator<Attribute> attributes;

        /**
         * Create an attribute iterator for the given collection of attributes.
         * 
         * @param attributes
         *            attributes to iterate over.
         */
        AttributeIterator(final Collection<Attribute> attributes) {
            this.attributes = attributes.iterator();
        }

        /**
         * Return whether there are any attributes left.
         * 
         * @return <code>true</code> when there are attributes left to iterate
         *         over, <code>false</code> otherwise.
         */
        public boolean hasNext() {
            return attributes.hasNext();
        }

        /**
         * Return the next attribute if there is an attribute left in the
         * iterator.
         * 
         * {@inheritDoc}
         */
        public Attribute next() {
            return attributes.next();
        }

        /**
         * This operation is not supported.
         * 
         * @throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
