/**
 *
 */
package org.arachna.dot4j.model;

/**
 * @author Dirk Weigenand
 * 
 */
public final class Id {
    private final long id;

    Id(final long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (int)(31 * this.id) << 32;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Id other = (Id)obj;

        return id == other.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Long.toString(id);
    }

}
