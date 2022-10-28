package svj.svjlib.svjlibs.obj;

import svj.svjlib.tools.Utils;

/**
 * <BR/>
 */
public class Author implements Comparable<Author>{

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

    public String getSimple() {
        StringBuilder sb = new StringBuilder(128);

        if (getLastName() != null) {
            sb.append(getLastName());
            sb.append(" ");
        }
        if (getFirstName() != null) {
            sb.append(getFirstName());
            sb.append(" ");
        }
        if (getMiddleName() != null) {
            sb.append(getMiddleName());
        }

        return sb.toString();
    }

    @Override
    public int compareTo(Author author) {

        int iLastName, iMiddleName;

        if ( author == null )  return 1;

        // 0 - если оба null
        iLastName   = Utils.compareToWithNull ( getLastName(), author.getLastName() );

        if ( iLastName == 0 )
        {
            iMiddleName   = Utils.compareToWithNull ( getMiddleName(), author.getMiddleName() );
            if ( iMiddleName == 0 )
                return Utils.compareToWithNull ( getFirstName(), author.getFirstName() );
            else
                return iMiddleName;
        }
        else
            return iLastName;

    }

    public boolean equals(Object obj) {
        if ( obj == null )  return false;
        if ( obj instanceof Author )
        {
            Author author = (Author) obj;
            return compareTo ( author ) == 0;
        }
        return false;
    }

}
