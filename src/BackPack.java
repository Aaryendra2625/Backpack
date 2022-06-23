import java.util.*;

public class BackPack {
    private static final ArrayList<User> list_of_Instructor = new ArrayList<>();
    private static final ArrayList<User> list_of_Students = new ArrayList<>();
    private static final ArrayList<Material> list_of_materials = new ArrayList<>();
    private static final ArrayList<Assessment> list_of_assessments = new ArrayList<>();
    private static final ArrayList<Submission> list_of_submissions = new ArrayList<>();
    private static final ArrayList<Comments> list_of_comments = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        User I0 = new Instructor(sc,"Instructor 1");
        User I1 = new Instructor(sc, "Instructor 2");
        list_of_Instructor.add(I0);
        list_of_Instructor.add(I1);
        User S0 = new Student(sc,"Student 1");
        User S1 = new Student(sc,"Student 2");
        User S2 = new Student(sc,"Student 3");

        list_of_Students.add(S0);
        list_of_Students.add(S1);
        list_of_Students.add(S2);
        while(true) {
            System.out.println("-----------");
            System.out.println("""
                    Welcome to Backpack
                    1. Enter as instructor
                    2. Enter as student
                    3. Exit""");
            System.out.println("-----------");
            int choice = sc.nextInt();
            User user;
            if (choice == 1) {
                System.out.println("-----------");
                System.out.println("Instructors:");
                for (int i = 0; i < list_of_Instructor.size(); i++) {
                    System.out.println(i + " - " + list_of_Instructor.get(i).getName());
                }
                System.out.println("-----------");
                System.out.print("Choose id: ");
                choice = sc.nextInt();
                user = list_of_Instructor.get(choice);
                System.out.println("-----------");
                System.out.println("Welcome " + user.getName());
                System.out.println("-----------");
                while (true) {
                    user.print_menu();
                    int query_choice = sc.nextInt();
                    if(query_choice==1){
                        System.out.println("-----------");
                        System.out.println("1. Add Lecture Slide\n" + "2. Add Lecture Video");
                        System.out.println("-----------");
                        int choice1 = sc.nextInt();
                        if(choice1 ==1){
                            sc.nextLine();
                            System.out.println("-----------");
                            System.out.print("Enter topic of slides: ");
                            String lecture_topic = sc.nextLine();
                            System.out.print("Enter number of slides: ");
                            int num_of_slides = sc.nextInt();
                            System.out.println("Enter content of slides:");
                            sc.nextLine();
                            ArrayList<String> slide_content = new ArrayList<>();
                            for(int i = 0;i<num_of_slides;i++){
                                System.out.printf("Content of slide %d: ",i+1);
                                String curr_slide_content = sc.nextLine();
                                slide_content.add(curr_slide_content);
                            }
                            Material new_lecture = new Lecture_slides((Instructor) user,sc,lecture_topic,slide_content);
                            list_of_materials.add(new_lecture);
                            System.out.println("-----------");
                            System.out.println("Welcome "+ user.getName());
                            System.out.println("-----------");
                        }
                        else if(choice1 == 2){
                            sc.nextLine();
                            System.out.println("-----------");
                            System.out.print("Enter topic of video: ");
                            String lecture_topic = sc.nextLine();
                            System.out.print("Enter filename of video: ");
                            String fileName = sc.nextLine();
                            if(fileName.endsWith(".mp4")){
                                Material new_lecture = new Lecture_videos((Instructor) user,sc,lecture_topic,fileName);
                                list_of_materials.add(new_lecture);
                                System.out.println("-----------");
                                System.out.println("Welcome "+ user.getName());
                                System.out.println("-----------");
                            }
                            else{
                                System.out.println("ERROR: Enter a .mp4 file");
                                System.out.println("-----------");
                            }
                        }
                        else{
                            System.out.println("Wrong choice");
                            System.out.println("-----------");
                        }
                    }
                    else if(query_choice==2){
                        System.out.println("-----------");
                        System.out.println("1. Add Assignment\n" + "2. Add Quiz");
                        int choice1 = sc.nextInt();
                        if(choice1==1){
                            sc.nextLine();
                            System.out.println("-----------");
                            System.out.print("Enter problem statement: ");
                            String problem_statement = sc.nextLine();
                            System.out.print("Enter max marks: ");
                            int marks = sc.nextInt();
                            Assessment new_assignment = new Assignment(problem_statement,marks);

                            list_of_assessments.add(new_assignment);
                            System.out.println("-----------");
                            System.out.println("Welcome "+ user.getName());
                            System.out.println("-----------");
                        }
                        else if(choice1==2){
                            sc.nextLine();
                            System.out.println("-----------");
                            System.out.print("Enter quiz question: ");
                            String quiz_question = sc.nextLine();
                            Assessment new_quiz = new Quiz(quiz_question);
                            list_of_assessments.add(new_quiz);
                            System.out.println("-----------");
                            System.out.println("Welcome "+ user.getName());
                            System.out.println("-----------");
                        }
                    }
                    else if(query_choice==3){
                        for(Material material: list_of_materials){
                            material.printDetails();
                            System.out.println("");
                        }
                        System.out.println("-----------");
                        System.out.println("Welcome "+ user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==4){
                        int idx = 0;
                        for(Assessment assessment: list_of_assessments){
                            idx++;
                            System.out.print("ID: " + idx);
                            assessment.print_details();
                            System.out.println("-----------");
                        }
                        System.out.println("Welcome "+ user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==5){
                        System.out.println("-----------");
                        int idx = 0;
                        for(Assessment assessment: list_of_assessments){
                            idx++;
                            System.out.print("ID: " + idx);
                            assessment.print_details();
                            System.out.println("-----------");
                        }
                        System.out.print("Enter ID of assessment to view submissions: ");
                        int id = sc.nextInt();
                        if(id>list_of_assessments.size()){
                            System.out.println("Not a valid choice.");
                        }
                        else{
                            Assessment curr_assessment = list_of_assessments.get(id-1);
                            if(curr_assessment.no_of_submissions_for_this_assessment()==0){
                                System.out.println("No ungraded assessments available.");
                            }
                            else{
                                System.out.println("Choose ID from these ungraded submissions: ");
                                curr_assessment.print_students_with_this_submitted_assessment();
                                int choice_1 = sc.nextInt();
                                Submission curr_submission = curr_assessment.get_i_th_student(choice_1);
                                if(curr_submission!=null){
                                    System.out.println("Submission:");
                                    System.out.println("Submission: "+curr_submission.getAns());
                                    System.out.println("-----------");
                                    System.out.println("Max marks: "+curr_assessment.getMarks());
                                    System.out.print("Marks scored: ");
                                    int marks = sc.nextInt();
                                    curr_submission.setGraded();
                                    curr_submission.setMarks(marks);
                                    curr_submission.setInstructor((Instructor) user);
                                }
                            }
                        }
                        System.out.println("-----------");
                        System.out.println("Welcome "+ user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==6){
                        System.out.println("List of Open Assignments:");
                        int idx = 0;
                        for(Assessment assessment: list_of_assessments){
                            if(!assessment.getClosed()){
                                idx++;
                                System.out.print("ID: " + idx);
                                assessment.print_details();
                                System.out.println("-----------");
                            }
                        }
                        if(idx==0){
                            System.out.println("No assignments available to close.");
                        }
                        else{
                            System.out.print("Enter id of assignment to close: ");
                            int id = sc.nextInt();
                            if(id>idx){
                                System.out.println("Not a valid choice.");
                            }
                            else{
                                idx = 0;
                                for(Assessment assessment: list_of_assessments){
                                    if(!assessment.getClosed()){
                                        idx++;
                                        if(id==idx) assessment.setClosed();
                                    }
                                }
                            }
                            System.out.println("-----------");
                            System.out.println("Welcome "+ user.getName());
                            System.out.println("-----------");
                        }

                    }
                    else if(query_choice==7){
                        for(Comments comments:list_of_comments){
                            comments.view_comment();
                            System.out.println("");
                        }
                        System.out.println("-----------");
                        System.out.println("Welcome "+ user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==8){
                        System.out.print("Enter comment: ");
                        sc.nextLine();
                        String comment = sc.nextLine();
                        Comments new_comment = new Instructor(sc, user.getName());
                        new_comment.add_comment(comment);
                        list_of_comments.add(new_comment);
                        System.out.println("-----------");
                        System.out.println("Welcome "+ user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==9){
                        break;
                    }
                }
            }
            else if(choice==2){
                Student student;
                System.out.println("-----------");
                System.out.println("Students:");
                for(int i = 0;i<list_of_Students.size();i++){
                    System.out.println(i+" - "+list_of_Students.get(i).getName());
                }
                System.out.println("-----------");
                System.out.print("Choose id: ");
                int choice1 = sc.nextInt();
                user = list_of_Students.get(choice1);
                student = (Student) user;
                System.out.println("-----------");
                System.out.println("Welcome "+user.getName());
                System.out.println("-----------");
                while(true) {
                    student.print_menu();
                    int query_choice = sc.nextInt();
                    if(query_choice==1){
                        for(Material material: list_of_materials){
                            material.printDetails();
                            System.out.println("");
                        }
                        System.out.println("-----------");
                        System.out.println("Welcome "+user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==2){
                        int idx = 0;
                        for(Assessment assessment: list_of_assessments){
                            idx++;
                            System.out.print("ID: " + idx);
                            assessment.print_details();
                            System.out.println("-----------");

                        }
                        System.out.println("Welcome "+user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==3){
                        System.out.println("-----------");
                        System.out.println("Pending assessments:");
                        int idx = 0;
                        for(Assessment assessment: list_of_assessments){
                            if(!assessment.getClosed() && ! student.hasThis(assessment)){
                                idx++;
                                System.out.print("ID: " + idx);
                                assessment.print_details();
                                System.out.println("-----------");
                            }
                        }
                        if(idx==0){
                            System.out.println("No assessments available");
                            System.out.println("-----------");
                        }
                        else{
                            System.out.print("Enter ID of assessment: ");
                            int choice2 = sc.nextInt();
                            int i1 = 0;
                            Assessment curr_assessment= null;
                            for(Assessment assessment: list_of_assessments){
                                if(!assessment.getClosed() && !student.hasThis(assessment)){
                                    i1++;
                                    if(i1==choice2){
                                        curr_assessment = assessment;
                                        break;
                                    }
                                }
                            }
                            if(curr_assessment==null){
                                System.out.println("Not a valid choice");
                            }
                            else{
                                String question = curr_assessment.getQuestion();
                                System.out.print(question+ " ");
                                sc.nextLine();
                                String answer = sc.nextLine();
                                if(question.equals("Enter filename of assignment:")){
                                    if (answer.endsWith(".zip")) {
                                        Submission new_submission = new Submission(student, curr_assessment, answer);
                                        list_of_submissions.add(new_submission);
                                        student.add_new_submission(new_submission);
                                        curr_assessment.add_submission(new_submission);
                                        System.out.println("-----------");
                                        System.out.println("Welcome " + user.getName());
                                    } else {
                                        System.out.println("File should always be in the zip format.");
                                    }
                                    System.out.println("-----------");
                                }
                                else{
                                    if (answer.contains(" ")) {
                                        System.out.println("Answer should be one word only.");
                                    } else {
                                        Submission new_submission = new Submission(student, curr_assessment, answer);
                                        list_of_submissions.add(new_submission);
                                        student.add_new_submission(new_submission);
                                        curr_assessment.add_submission(new_submission);
                                        System.out.println("-----------");
                                        System.out.println("Welcome " + user.getName());
                                    }
                                    System.out.println("-----------");
                                }
                            }
                        }
                    }
                    else if(query_choice==4){
                        System.out.println("-----------");
                        System.out.println("Graded Submissions:");
                        student.view_grades();
                        System.out.println("-----------");
                        System.out.println("Welcome "+user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==5){
                        for(Comments comments:list_of_comments){
                            comments.view_comment();
                            System.out.println("");
                        }
                        System.out.println("-----------");
                        System.out.println("Welcome "+user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==6){
                        System.out.print("Enter comment: ");
                        sc.nextLine();
                        String comment = sc.nextLine();
                        Comments new_comment = new Student(sc,user.getName());
                        new_comment.add_comment(comment);
                        list_of_comments.add(new_comment);
                        System.out.println("-----------");
                        System.out.println("Welcome "+user.getName());
                        System.out.println("-----------");
                    }
                    else if(query_choice==7){
                        break;
                    }
                    else{
                        System.out.println("Wrong choice");
                    }
                }
            }
            else{
                break;
            }
        }
    }
}

class Student implements Comments,User {
    private final Scanner sc;
    private final String name;
    private final ArrayList<Submission> submissions;
    private Date date;
    private String comment;
    public Student(Scanner sc,String name){
        this.sc = sc;
        this.name = name;
        submissions = new ArrayList<>();
    }
    public String getName(){
        return this.name;
    }
    public void add_new_submission(Submission submission){
        submissions.add(submission);
    }
    public boolean hasThis(Assessment assessment){
        for(Submission submission:submissions){
            if(submission.getAssessment()==assessment){
                return true;
            }
        }
        return false;
    }

    public void view_grades(){
        for(Submission sub : submissions){
            if(sub.isGraded()){
                System.out.println("Submission: "+sub.getAns());
                System.out.println("Marks scored: "+sub.getMarks());
                System.out.println("Graded by: "+sub.getInstructorName());
                System.out.println("-----------");
            }
        }
        System.out.println("-----------\n");
        System.out.println("Ungraded Submissions:");
        for(Submission sub : submissions){
            if(!sub.isGraded()){
                System.out.println("Submission: "+sub.getAns());
            }
        }
    }
    public void add_comment(String comment){
        this.comment = comment;
        this.date = new Date();
    }
    public void view_comment(){
        System.out.println(this.comment+" - "+this.getName());
        System.out.println(date);
    }
    public void print_menu(){
        System.out.println("""
                STUDENT MENU
                -----------
                1. View lecture materials
                2. View assessments
                3. Submit assessment
                4. View grades
                5. View comments
                6. Add comments
                7. Logout""");
    }
}

class Instructor implements Comments,User {
    private final Scanner sc;
    private final String name;
    private Date date;
    private String comment;

    public Instructor(Scanner sc,String name){
        this.sc = sc;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void add_comment(String comment){
        this.comment = comment;
        this.date = new Date();
    }
    public void view_comment(){
        System.out.println(this.comment+" - "+this.getName());
        System.out.println(date);
    }
    public void print_menu(){
        System.out.println("""
                INSTRUCTOR MENU
                -----------
                1. Add class material
                2. Add assessments
                3. View lecture materials
                4. View assessments
                5. Grade assessments
                6. Close assessment
                7. View comments
                8. Add comments
                9. Logout""");
    }
}
class Assignment implements Assessment{
    private final String problem_statement;
    private final int marks;
    private boolean closed = false;
    private final ArrayList<Submission> list_of_submissions;
    public Assignment(String problem_statement, int marks){
        this.problem_statement = problem_statement;
        this.marks = marks;
        this.list_of_submissions = new ArrayList<>();
    }
    @Override
    public boolean getClosed(){
        return closed;
    }
    @Override
    public int getMarks(){
        return this.marks;
    }
    @Override
    public void setClosed(){
        closed = true;
    }
    @Override
    public String getQuestion(){
        return "Enter filename of assignment:";
    }
    @Override
    public int no_of_submissions_for_this_assessment(){
        int id = 0;
        for(Submission submission:list_of_submissions){
            if(!submission.isGraded()){
                id++;
            }
        }
        return id;
    }

    @Override
    public void add_submission(Submission submission){
        list_of_submissions.add(submission);
    }
    @Override
    public void print_students_with_this_submitted_assessment(){
        int id = 0;
        for(Submission submission:list_of_submissions){
            if(!submission.isGraded()){
                id++;
                System.out.print(id+" - ");
                System.out.println(submission.getStudent().getName());
            }
        }
    }
    @Override
    public Submission get_i_th_student(int i){
        int id = 0;
        for(Submission submission:list_of_submissions){
            if(!submission.isGraded()){
                id++;
                if(i==id) return submission;
            }
        }
        System.out.println("Not a valid choice.");
        return null;
    }
    @Override
    public void print_details(){
        System.out.println(" Assignment: "+this.problem_statement);
        System.out.println("      Max Marks: "+marks);
    }
}

class Quiz implements Assessment {
    private final String quiz_question;
    private final int marks = 1;
    private boolean closed = false;
    private final ArrayList<Submission> list_of_submissions;
    public Quiz(String quiz_question){
        this.quiz_question = quiz_question;
        this.list_of_submissions = new ArrayList<>();
    }
    @Override
    public int getMarks(){
        return this.marks;
    }
    @Override
    public boolean getClosed(){
        return this.closed;
    }
    @Override
    public String getQuestion(){
        return this.quiz_question;
    }
    @Override
    public void setClosed(){
        this.closed = true;
    }
    @Override
    public void add_submission(Submission submission){
        list_of_submissions.add(submission);
    }
    @Override
    public int no_of_submissions_for_this_assessment(){
        int id = 0;
        for(Submission submission:list_of_submissions){
            if(!submission.isGraded()){
                id++;
            }
        }
        return id;
    }
    @Override
    public void print_students_with_this_submitted_assessment(){
        int id = 0;
        for(Submission submission:list_of_submissions){
            id++;
            System.out.print(id+" - ");
            System.out.println(submission.getStudent().getName());
        }
    }
    @Override
    public Submission get_i_th_student(int i){
        if(i>list_of_submissions.size()){
            System.out.println("Not a valid ID.");
            return null;
        }
        else{
            return list_of_submissions.get(i-1);
        }
    }
    @Override
    public void print_details(){
        System.out.println(" Question: "+this.quiz_question);
        System.out.println("      Max Marks: "+marks);
    }
}

class Lecture_videos implements Material {
    private final Scanner sc;
    private final String lecture_topic;
    private final Instructor instructor;
    private final String fileName;
    private final Date date;

    public Lecture_videos(Instructor instructor, Scanner sc, String lecture_topic, String fileName){
        this.sc = sc;
        this.instructor = instructor;
        this.lecture_topic = lecture_topic;
        this.fileName = fileName;
        date = new Date();
    }
    @Override
    public void printDetails(){
        System.out.println("Title: " + lecture_topic);
        System.out.println("Video file: " + fileName);
        System.out.println("Date of upload: " + date);
        System.out.println("Uploaded by: " + instructor.getName());
    }
}

class Lecture_slides implements Material {
    private final Scanner sc;
    private final String lecture_topic;
    private final int num_of_slides;
    private final Instructor instructor;
    private final ArrayList<String> slide_content;
    private final Date date;

    public Lecture_slides(Instructor instructor, Scanner sc,String lecture_topic,ArrayList<String> slide_content){
        this.instructor = instructor;
        this.sc = sc;
        this.lecture_topic = lecture_topic;
        this.slide_content = slide_content;
        this.num_of_slides = slide_content.size();
        date = new Date();
    }
    @Override
    public void printDetails(){
        System.out.println("Title: "+lecture_topic);
        for(int i = 0;i<slide_content.size();i++){
            System.out.println("Slide "+ (i+1) +": "+slide_content.get(i));
        }
        System.out.println("Number of slides: "+ num_of_slides);
        System.out.println("Date of upload: "+date);
        System.out.println("Uploaded by: "+instructor.getName());
    }
}

class Submission {

    private final Assessment assessment;
    private final String ans;
    private boolean graded = false;
    private final Student student;
    private int marks;
    private Instructor instructor;

    public Submission(Student student,Assessment assessment,String ans){
        this.assessment = assessment;
        this.student = student;
        this.ans = ans;
    }

    public Assessment getAssessment(){
        return this.assessment;
    }

    public Student getStudent(){
        return this.student;
    }
    public void setGraded(){
        this.graded = true;
    }
    public void setMarks(int marks){
        this.marks = marks;
    }
    public void setInstructor(Instructor instructor){
        this.instructor = instructor;
    }

    public boolean isGraded(){
        return this.graded;
    }
    public String getAns(){
        return this.ans;
    }
    public int getMarks(){
        return this.marks;
    }
    public String getInstructorName(){
        return this.instructor.getName();
    }
}

// interfaces

interface Assessment {
    boolean getClosed();
    int getMarks();
    String getQuestion();
    void print_details();
    void setClosed();
    void add_submission(Submission submission);
    int no_of_submissions_for_this_assessment();
    void print_students_with_this_submitted_assessment();
    Submission get_i_th_student(int i);
}

interface Material {
    void printDetails();
}

interface Comments {
    void add_comment(String comment);
    void view_comment();
}

interface User {
    void print_menu();
    String getName();
}

