package book.store.onlinebookstore.enums;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    COMPLETED(1),
    PENDING(2),
    DELIVERED(3);

    private static final Map<Integer, Status> statusMap = new HashMap<>();
    private int value;

    Status(int value) {
        this.value = value;
    }

    static {
        for (Status status : Status.values()) {
            statusMap.put(status.value, status);
        }
    }

    public static Status valueOf(int value) {
        return statusMap.get(value);
    }

    public int getValue() {
        return value;
    }
}
