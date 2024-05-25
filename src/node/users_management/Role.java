package node.users_management;

import java.io.Serial;
import java.io.Serializable;

public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String role;
    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
