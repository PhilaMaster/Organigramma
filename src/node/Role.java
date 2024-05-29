package node;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String role;
    private final boolean extend;
    public Role(String role, boolean extend) {
        this.role = role;
        this.extend = extend;
    }

    public String getRole() {
        return role;
    }

    public boolean isExtend() {
        return extend;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(role, extend);
    }
}
