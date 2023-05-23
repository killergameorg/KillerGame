package events;

/**
 * The GetPowerUp class represents an event where a visual object gets a
 * power-up.
 * It extends the {@link Event} class.
 */
public class GetPowerUp extends Event {

    /**
     * Creates a new instance of the GetPowerUp class with the specified visual
     * objects.
     *
     * @param firstObject  The first visual object involved in the event (the
     *                     power-up object).
     * @param secondObject The second visual object involved in the event (the
     *                     object
     *                     receiving the power-up).
     */
    public GetPowerUp(VisualObject firstObject, VisualObject secondObject) {
        super(firstObject, secondObject);
    }
}
