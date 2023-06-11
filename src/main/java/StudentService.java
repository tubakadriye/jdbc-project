import java.util.Scanner;

//Service layer is to handle business logic
public class StudentService {

    //to get access to methods in repository class
    StudentRepository repository = new StudentRepository();
    Scanner input = new Scanner(System.in);

    //step 7. method to call createTable method from Repository Layer
    public void createTable(){
        repository.createTable();
    }

    //step -9: method to register student
    public void saveStudent(){
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Last Name: ");
        String lastName = input.nextLine();
        System.out.println("City: ");
        String city = input.nextLine();
        System.out.println("Age: ");
        int age = input.nextInt();
        input.nextLine(); //to call next enter/new line

        //after getting all data from user, we create new student obj using info enter by user
        Student newStudent = new Student(name, lastName, city, age);
        //new student should be saved in our DB
        repository.save(newStudent);
    }

    //step -11. method to display students in a list
    public void getAllStudents(){
        repository.findAll();
    }

    //step -13, method to find student by ID
    public Student getStudentById(int id){
        Student student =  repository.findStudentById(id);
        return student;
    }

    //step -15, method to delete student by ID
    public void deleteStudent(int id){
        //we can handle error message if there is no student found with id
        repository.delete(id);
    }

    //step -17 method to udpate student information
    public void updateStudent(int id){

        //find student from table
        Student existingStudent =  getStudentById(id);
        //check if user exist in Table or not
        if(existingStudent ==null){
            System.out.println("Student with id "+id+" not found");
        }else{//we have found one student and we can start updating
            //ask user to enter new data to be updated
            System.out.println("Name: ");
            String name = input.nextLine();
            System.out.println("Last Name: ");
            String lastName = input.nextLine();
            System.out.println("City: ");
            String city = input.nextLine();
            System.out.println("Age: ");
            int age = input.nextInt();
            input.nextLine(); //to call next enter/new line

            //start update fields of existing student data
            existingStudent.setName(name);
            existingStudent.setLastname(lastName);
            existingStudent.setCity(city);
            existingStudent.setAge(age);
            //existingStudent.setId(); // id should remain the same

            repository.update(existingStudent);

        }

    }

}
