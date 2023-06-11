import java.sql.*;


//class which will be connted to DB(connection, statement, prepared statement)
public class StudentRepository {

    private Connection con;
    private Statement statement;
    private PreparedStatement prs;

    //step-3 method to create connection
    private void getConnection(){
        try {
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db", "dev_user", "password");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //step-4 method to create statement
    private void getStatement(){
        try {
            this.statement = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //step-5; method to create preparedstatment
    private void getPreparedStatement(String query){
        try {
            this.prs = con.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //step -6; method to create table
    public void createTable(){
        getConnection(); //create connection
        getStatement(); //to be able to run queries

        try {
            String query = "CREATE TABLE IF NOT EXISTS tbl_student (id SERIAL, name VARCHAR(50), lastname VARCHAR(50), city VARCHAR (50), age INT)";
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                statement.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    //step-10. method to persist data into database

    public void save(Student newStudent) {
        getConnection();
        String query = "INSERT INTO tbl_student (name, lastname,city, age) VALUES (?,?, ?, ?)";
        getPreparedStatement(query);
        try {
            prs.setString(1, newStudent.getName());
            prs.setString(2, newStudent.getLastname());
            prs.setString(3, newStudent.getCity());
            prs.setInt(4, newStudent.getAge());
            prs.executeUpdate(); //to persist / store values into table/DB
            System.out.println("Student registered successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //step-12. method to bring all students from database
    public void findAll() {
        getConnection();
        String query = "SELECT * FROM tbl_student";
        getStatement();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                System.out.print("id:"+ resultSet.getInt("id"));
                System.out.print(" Name:"+ resultSet.getString("name"));
                System.out.print(" Lastname:"+ resultSet.getString("lastname"));
                System.out.print(" City:"+ resultSet.getString("city"));
                System.out.print(" Age:"+ resultSet.getInt("age"));
                System.out.println(); //to start from new line for info about next student
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                statement.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //step-14, method to find student from table by provided id
    public Student findStudentById(int id) {
        getConnection();
        Student student=null;
        String query = "SELECT * FROM tbl_student WHERE id = ?";

        try {
            getPreparedStatement(query);
            prs.setInt(1, id);
            //data type coming from table is ResultSet
            ResultSet resultSet =  prs.executeQuery();
            //if resultset has some value(there is student information)
            // then we need map result set to student
            while(resultSet.next()){
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setLastname(resultSet.getString("lastname"));
                student.setCity(resultSet.getString("city"));
                student.setAge(resultSet.getInt("age"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return student;
    }

    //step-16, method that will run query to delete student from table
    public void delete(int id) {
        getConnection();
        String query = "DELETE FROM tbl_student WHERE id = ?";
        getPreparedStatement(query);
        try {
            prs.setInt(1, id);
            int deletedRows =  prs.executeUpdate(); //number of deleted rows
            if(deletedRows>0){
                System.out.println("Student with id: "+id+" is deleted successfully");
            }else {
                System.out.println("Student with id: "+id+" could not be found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //step-18. method to run update query to update student information
    public void update(Student newSutentInfo) {
        getConnection();
        String query = "UPDATE tbl_student SET name=?, lastname=?, city=?, age=? WHERE id=?";
        getPreparedStatement(query);
        try {
            prs.setString(1, newSutentInfo.getName() );
            prs.setString(2, newSutentInfo.getLastname());
            prs.setString(3, newSutentInfo.getCity());
            prs.setInt(4, newSutentInfo.getAge());
            prs.setInt(5, newSutentInfo.getId());
            int updated = prs.executeUpdate();
            if(updated>0){
                System.out.println("Student updated successfully");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
