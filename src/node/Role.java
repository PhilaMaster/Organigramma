package node;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record Role(String role, boolean extend) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role1)) return false;
        return extend == role1.extend && Objects.equals(role, role1.role);
    }
}
