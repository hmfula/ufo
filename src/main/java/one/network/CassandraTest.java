package one.network;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.Iterator;

/**
 * Created by harry on 19.3.2017.
 */
public class CassandraTest {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect("mykeyspace");

        //Example make sure to created a partition on user_id
        //insert into table
        session.execute("INSERT INTO mykeyspace.users"+ "(user_id,fname,lname)" +"values(110, 'rein','inventor')");
        //update table
        session.execute( "update mykeyspace.users set fname='my_changed_fname' where user_id=1200");

        //delete table
        session.execute( "delete from mykeyspace.users where user_id=1000");

        //select table
        ResultSet results = session.execute("SELECT * FROM users");
        StringBuilder line = new StringBuilder();

        for (Iterator<Row> iterator = results.iterator(); iterator.hasNext();) {
            Row row = iterator.next();
            line.delete(0, line.length());
            line.append("FirstName = ").
                    append(row.getString("fname")).
                    append(",").append(" ").
                    append("LastName = ").
                    append(row.getString("lname"));
            System.out.println(line.toString());
        }
    }
}
