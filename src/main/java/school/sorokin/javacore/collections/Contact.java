package school.sorokin.javacore.collections;

public record Contact(String name, String phone, String email, String group) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;
        return name.equals(contact.name) && phone.equals(contact.phone);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "'" + name + '\'' +
                " | '" + phone + '\'' +
                " | '" + email + '\'';
    }
}
