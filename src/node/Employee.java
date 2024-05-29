package node;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record Employee(String name, String surname, Role role) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname) && Objects.equals(role, employee.role);
    }
    public String toString() {
        return name + " " + surname + ", " + role;
    }
}
