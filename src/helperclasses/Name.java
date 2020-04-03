package helperclasses;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class Name implements SQLData {
    public String first_name;
    public String middle_name;
    public String last_name;
    private String sql_type;


    public Name(){
        first_name = "";
        middle_name = "";
        last_name = "";
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;
        first_name = stream.readString();
        middle_name = stream.readString();
        last_name = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(first_name);
        stream.writeString(middle_name);
        stream.writeString(last_name);
    }
}

