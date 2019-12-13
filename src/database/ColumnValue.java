package database;

public class ColumnValue<T> {
    private T value;

    public ColumnValue(T value) {
        this.value = value;
    }

    T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
