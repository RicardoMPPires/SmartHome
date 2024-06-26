package smarthome.domain.vo;

public interface ValueObject<T> {
    /**
     * @return Returns the primitive value that is encapsulated in the Value object. The "T" stands for a generic
     * return, which means, the return type will dynamically change based on context.
     */
    T getValue();
}
