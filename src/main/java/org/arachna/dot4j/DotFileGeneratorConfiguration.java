package org.arachna.dot4j;

/**
 * Configuration for <code>.dot</code> file generation.
 *
 * @author Dirk Weigenand
 */
public final class DotFileGeneratorConfiguration {
    /**
     * rank direction for graph generation. Default is 'LR'.
     */
    private String rankDirection = "TB";

    /**
     * control the generation of clusters. Default is 'global'.
     */
    private String clusterMode = "global";

    /**
     * the font size to use. Default is 12.
     */
    private int fontSize = 12;

    /**
     * @return the rankDirection
     */
    public String getRankDirection() {
        return rankDirection;
    }

    /**
     * @param rankDirection the rankDirection to set
     */
    public void setRankDirection(final String rankDirection) {
        this.rankDirection = rankDirection;
    }

    /**
     * @return the clusterMode
     */
    public String getClusterMode() {
        return clusterMode;
    }

    /**
     * @param clusterMode the clusterMode to set
     */
    public void setClusterMode(final String clusterMode) {
        this.clusterMode = clusterMode;
    }

    /**
     * @return the fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }
}
