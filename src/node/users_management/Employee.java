package node.users_management;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String surname;
    private final Role role;

    public Employee(String name, String surname, Role role) {
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname) && Objects.equals(role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, role);
    }


    public String toString() {
        return name + " " + surname + ", " + role;
    }

}
