/**
 * Abstract class to hold a card
 * object, used for compatibility
 * in case special cards want to
 * be added
 * 
 * @author Souren A., Ken J.
 * @since May 26th, 2019
 * @version 2.49
 */

abstract class Card {
    private String name;
    private String description;
    public Card(String n, String d) {
        name = n;
        description = d;
    }
    
    /**
     * Gets name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets image
     * @return image
     */
    public String getDes() {
        return description;
    }
}