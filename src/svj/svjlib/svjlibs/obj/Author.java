package svj.svjlib.svjlibs.obj;

/**
 * <BR/>
 */
public class Author {

    private String firstName;
    private String middleName;
    private String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setLastName(String value) {
        lastName = value;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getAsXml() {
        StringBuilder sb = new StringBuilder(128);

        sb.append("<author ");
        if (getFirstName() != null) {
            sb.append("first='");
            sb.append(getFirstName());
            sb.append("'");
        }
        if (getMiddleName() != null) {
            sb.append(" middle='");
            sb.append(getMiddleName());
            sb.append("'");
        }
        if (getLastName() != null) {
            sb.append(" last='");
            sb.append(getLastName());
            sb.append("'");
        }
        sb.append(" />\n");

        return sb.toString();
    }

}
