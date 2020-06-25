import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Course {

    private static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<Integer> coursesID = new ArrayList<>();
    private ArrayList<Integer> studentsID = new ArrayList<>();

    private double average;
    private int courseID;
    private int lecturerID;
    private int unit;
    private int capacity = 15;
    private int numberOfRegisteredStudents;
    private double score;

    Course(int courseID, int unit) {
        if (!coursesID.contains(courseID)) {
            setCourseID(courseID);
            setUnit(unit);
            setNumberOfRegisteredStudents(0);
            coursesID.add(courseID);
        }
    }

    Course(int courseID) {
        setCourseID(courseID);
    }


    void addStudentID(int studentID, int indx) {
        courses.get(indx).studentsID.add(studentID);
    }

    void deleteStudentID(int studentID, int indx) {
        for (int i = 0; i < Course.getCourses().get(indx).studentsID.size(); i++) {
            if (courses.get(indx).studentsID.get(i) == studentID) {
                courses.get(indx).studentsID.remove(i);
                return;
            }
        }
    }

    int getLecturerID() {
        return lecturerID;
    }

    void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    static boolean containCourse(Course course) {
        for (int i = 0; i < Course.courses.size(); i++) {
            if (courses.get(i).getCourseID() == course.getCourseID()) {
                return true;
            }
        }
        return false;
    }

    void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public static void addNewCourse(Course course) {
        if (!Course.containCourse(course)) {
            courses.add(course);
        }
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public int getNumberOfRegisteredStudents() {
        return numberOfRegisteredStudents;
    }

    public void setNumberOfRegisteredStudents(int number) {
        numberOfRegisteredStudents = number;
    }

    public void incrementNumberOfRegisteredStudents() {
        numberOfRegisteredStudents++;
    }

    public void decrementNumberOfRegisteredStudents() {
        numberOfRegisteredStudents--;
    }

    public int getCourseID() {
        return courseID;
    }

    private void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return unit;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void addCapacity(int number) {
        this.capacity += number;
    }

    static double getAverage(int index) {
        return courses.get(index).average;
    }

    static void setAverage(int courseID, int indexOfCourse) {
        int number = 0;
        double sum = 0;
        for (int i = 0; i < Student.getStudents().size(); i++) {
            for (int j = 0; j < Student.getStudents().get(i).getcIDs().size(); j++) {
                if (Student.getStudents().get(i).getcIDs().get(j) == courseID) {
                    sum += Student.getMarks(i).get(j);
                    number++;
                    break;
                }
            }
        }
        if (number != 0)
            courses.get(indexOfCourse).average = sum / number;
    }

    public static void showCourse(int courseID, String string) {
        for (int i = 0; i < Course.getCourses().size(); i++) {
            if (courses.get(i).getCourseID() == courseID) {

                if (string.equals("average")) {
                    Main.printDouble(getAverage(i));
                    return;
                }
                if (string.equals("capacity")) {
                    System.out.println(courses.get(i).getCapacity());
                    return;
                }
                if (string.equals("lecturer")) {
                    System.out.println(courses.get(i).getLecturerID());
                    return;
                }
                if (string.equals("students")) {
                    Collections.sort(courses.get(i).studentsID);
                    int j;
                    for (j = 0; j < courses.get(i).studentsID.size() - 1; j++) {
                        System.out.printf("%d ", courses.get(i).studentsID.get(j));
                    }
                    System.out.println(courses.get(i).studentsID.get(j));
                    return;
                }

                break;
            }
        }
        System.out.println("shoma daneshjoo nistid");
    }
}

class Student {
    private static ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Course> studentCourses = new ArrayList<>();
    private ArrayList<Double> marks = new ArrayList<>();
    private ArrayList<Integer> cIDs = new ArrayList<>();
    private ArrayList<Integer> units = new ArrayList<>();

    private int studentID;
    private double average;
    private int numOfUnit;
    private int numberOfWUnits;
    private boolean permissionToW = true;


    Student(int studentID) {
        setStudentID(studentID);
    }

    static ArrayList<Double> getMarks(int index) {
        return students.get(index).marks;
    }


    ArrayList<Integer> getcIDs() {
        return cIDs;
    }

    public static void setAverages(int index) {
        double sum = 0;
        int numOfUnits = 0;
        for (int i = 0; i < students.get(index).units.size(); i++) {
            sum += Student.getMarks(index).get(i) * students.get(index).units.get(i);
            numOfUnits += students.get(index).units.get(i);
        }
        if (numOfUnits != 0)
            students.get(index).average = (sum / numOfUnits);
    }

    int getNumOfUnit() {
        return numOfUnit;
    }


    void decreaseOfNumOfUnits(int number) {
        numOfUnit -= number;
    }

    private int getNumberOfWUnits() {
        return numberOfWUnits;
    }

    public double getAverage() {
        return average;
    }

    static boolean containStudent(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID() == student.getStudentID()) {
                return true;
            }
        }
        return false;
    }

    public static void addNewStudent(Student student) {
        if (!Student.containStudent(student)) {
            students.add(student);
        }
    }

    public static void registerCourse(int studentID, int courseID) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID() == studentID) {
                for (int j = 0; j < Course.getCourses().size(); j++) {
                    if (Course.getCourses().get(j).getCourseID() == courseID && Course.getCourses().get(j)
                            .getNumberOfRegisteredStudents() < Course.getCourses().get(j).getCapacity()) {

                        Course.getCourses().get(j).incrementNumberOfRegisteredStudents();
                        Course.getCourses().get(j).addStudentID(studentID, j);
                        students.get(i).numOfUnit += Course.getCourses().get(j).getUnit();
                        students.get(i).addCourse(Course.getCourses().get(j));

                    }
                }
                break;
            }
        }
    }

    public void addCourse(Course course) {
        studentCourses.add(course);
    }

    public static void deleteCourse(int studentID, int courseID) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID() == studentID) {
                if (!Student.getStudents().get(i).permissionToW || students.get(i).getNumberOfWUnits() >= 3) {
                    return;
                }
                for (int j = students.get(i).getStudentCourses().size() - 1; j >= 0; j--) {

                    if (students.get(i).getStudentCourses().get(j).getCourseID() == courseID) {

                        students.get(i).numberOfWUnits += students.get(i).getStudentCourses().get(j).getUnit();
                        if (students.get(i).numberOfWUnits > 2) {
                            Student.getStudents().get(i).permissionToW = false;
                        }
                        students.get(i).numOfUnit -= students.get(i).getStudentCourses().get(j).getUnit();
                        students.get(i).getStudentCourses().get(j).decrementNumberOfRegisteredStudents();

                        for (int k = 0; k < students.get(i).getcIDs().size(); k++) {
                            if (students.get(i).cIDs.get(k) == courseID) {  //delete mark from marksOfStudent
                                students.get(i).cIDs.remove(k);
                                students.get(i).marks.remove(k);
                                students.get(i).units.remove(k);
                                break;
                            }
                        }
                        students.get(i).getStudentCourses().remove(j);   //delete for student
                        break;
                    }
                }
                break;
            }
        }
    }

    void addMark(double mark, int indexOfStudent, int indexOfCourse) {
        Student.getMarks(indexOfStudent).add(mark);
        students.get(indexOfStudent).units.add(Course.getCourses().get(indexOfCourse).getUnit());
        students.get(indexOfStudent).cIDs.add(Course.getCourses().get(indexOfCourse).getCourseID());
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public static void showRanks() {
        ArrayList<Double> average = new ArrayList<>();
        ArrayList<Integer> studentsID = new ArrayList<>();
        ArrayList<Integer> rankFirst = new ArrayList<>();
        int numberOfFirst = 0;
        ArrayList<Integer> rankSecond = new ArrayList<>();
        int numberOfSecond = 0;
        ArrayList<Integer> rankThird = new ArrayList<>();
        int numberOfThird = 0;
        int numberOfRanks = 0;
        double first = 0;
        double second = 0;
        double third = 0;
        for (int i = 0; i < students.size(); i++) {
            studentsID.add(students.get(i).getStudentID());
            average.add(students.get(i).getAverage());
        }
        for (int i = 0; i < students.size(); i++) {
            if (average.get(i) >= first) {
                third = second;
                second = first;
                first = average.get(i);
            } else if (average.get(i) >= second) {
                third = second;
                second = average.get(i);
            } else if (average.get(i) >= third) {
                third = average.get(i);
            }
        }
        for (int i = 0; i < students.size(); i++) {
            if (first == average.get(i)) {
                rankFirst.add(studentsID.get(i));
                numberOfFirst++;
            }
            if (second == average.get(i)) {
                rankSecond.add(studentsID.get(i));
                numberOfSecond++;
            }
            if (third == average.get(i)) {
                rankThird.add(studentsID.get(i));
                numberOfThird++;
            }
        }
        printRespectivelyRanks(rankFirst, numberOfFirst, rankSecond, numberOfSecond,
                rankThird, numberOfThird, numberOfRanks);
    }

    public ArrayList<Course> getStudentCourses() {
        return studentCourses;
    }

    public static void showAverage(int studentID) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID() == studentID) {
                Main.printDouble(Student.getStudents().get(i).getAverage());
                return;
            }
        }
        System.out.println("shoma daneshjoo nistid");
    }

    private static void printRespectivelyRanks(ArrayList<Integer> rankFirst, int numberOfFirst, ArrayList<Integer>
            rankSecond, int numberOfSecond, ArrayList<Integer> rankThird, int numberOfThird, int numberOfRanks) {
        Collections.sort(rankFirst);
        Collections.sort(rankSecond);
        Collections.sort(rankThird);
        numberOfRanks = printRanksAndChangeNumberOfAllRanks(rankFirst, numberOfFirst, numberOfRanks);
        numberOfRanks = printRanksAndChangeNumberOfAllRanks(rankSecond, numberOfSecond, numberOfRanks);
        numberOfRanks = printRanksAndChangeNumberOfAllRanks(rankThird, numberOfThird, numberOfRanks);
        System.out.println();
    }

    private static int printRanksAndChangeNumberOfAllRanks(ArrayList<Integer> ranks, int numberOfRanks, int numberOfAllRanks) {
        for (int i = 0; i < numberOfRanks && numberOfAllRanks < 3; i++) {
            System.out.printf("%d ", ranks.get(i));
            numberOfAllRanks++;
        }
        return numberOfAllRanks;
    }

    public static void showRanks(int courseID) {
        ArrayList<Double> marks = new ArrayList<>();
        ArrayList<Integer> studentsID = new ArrayList<>();
        double max = 0.0;
        double mid = 0.0;
        double min = 0.0;
        ArrayList<Integer> firstRanks = new ArrayList<>();
        ArrayList<Integer> secondRanks = new ArrayList<>();
        ArrayList<Integer> thirdRanks = new ArrayList<>();
        int numberOfMarks = 0;
        int numberOfRanks = 0;
        int numberOfFirst = 0;
        int numberOfSecond = 0;
        int numberOfThird = 0;

        for (int i = 0; i < Course.getCourses().size(); i++) {
            if (Course.getCourses().get(i).getCourseID() == courseID) {
                for (int j = 0; j < students.size(); j++) {
                    for (int k = 0; k < students.get(j).getcIDs().size(); k++) {
                        if (Student.getStudents().get(j).getcIDs().get(k) == courseID) {
                            marks.add(Student.getMarks(j).get(k));
                            studentsID.add(Student.getStudents().get(j).getStudentID());
                            numberOfMarks++;
                            if (Student.getMarks(j).get(k) >= max) {
                                min = mid;
                                mid = max;
                                max = Student.getMarks(j).get(k);
                            } else if (Student.getMarks(j).get(k) >= mid) {
                                min = mid;
                                mid = Student.getMarks(j).get(k);
                            } else if (Student.getMarks(j).get(k) >= min) {
                                min = Student.getMarks(j).get(k);
                            }
                            break;
                        }
                    }
                }
                break;
            }
        }
        for (int i = 0; i < numberOfMarks; i++) {
            if (marks.get(i) == max) {
                firstRanks.add(studentsID.get(i));
                numberOfFirst++;
            } else if (marks.get(i) == mid) {
                secondRanks.add(studentsID.get(i));
                numberOfSecond++;
            } else if (marks.get(i) == min) {
                thirdRanks.add(studentsID.get(i));
                numberOfThird++;
            }
        }
        printRespectivelyRanks(firstRanks, numberOfFirst, secondRanks,
                numberOfSecond, thirdRanks, numberOfThird, numberOfRanks);
    }
}


class Lecturer {
    private static ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();

    private int lecturerID;
    private int numOfCourses;
    static int numberOfLecturers;

    Lecturer(int lecturerID, String[] strings) {

        setLecturerID(lecturerID);
        for (int i = 2; i < strings.length; i++) {

            if (Course.coursesID.contains(Main.toInt(strings[i]))) {

                for (int j = 0; j < Course.getCourses().size(); j++) {

                    if (Course.getCourses().get(j).getCourseID() == Main.toInt(strings[i])) {

                        if (!Lecturer.containCourseOfLecturer(lecturerID, Main.toInt(strings[i]))) {
                            addCourse(Main.toInt(strings[i]));
                            Course.getCourses().get(j).setLecturerID(lecturerID);
                        }
                        break;
                    }
                }
            }
        }
    }

    static boolean containLecturer(int lecturerID) {
        for (int i = 0; i < numberOfLecturers; i++) {
            if (lecturers.get(i).getLecturerID() == lecturerID) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public static void addNewLecturer(Lecturer lecturer) {
        if (!Lecturer.containLecturer(lecturer.getLecturerID())) {
            lecturers.add(lecturer);
            numberOfLecturers++;
        }
    }

    static boolean containCourseOfLecturer(int lecturerID, int courseID) {

        for (int i = 0; i < Lecturer.numberOfLecturers; i++) {

            if (lecturers.get(i).getLecturerID() == lecturerID) {

                for (int j = 0; j < Lecturer.getLecturers().get(i).numOfCourses; j++) {

                    if (Lecturer.getLecturers().get(i).getCourses().get(j).getCourseID() == courseID) {
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    public void addCourse(int courseID) {
        Course newCourse = new Course(courseID);
        courses.add(newCourse);
        numOfCourses++;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public static void addCapacity(int lecturerID, int courseID, int number) {
        for (int i = 0; i < Lecturer.numberOfLecturers; i++) {

            if (lecturers.get(i).lecturerID == lecturerID) {

                for (int j = 0; j < lecturers.get(i).numOfCourses; j++) {

                    if (lecturers.get(i).getCourses().get(j).getCourseID() == courseID) {//add capacity

                        lecturers.get(i).getCourses().get(j).addCapacity(number);//for lecturer
                        for (int k = 0; k < Course.getCourses().size(); k++) {
                            if (Course.getCourses().get(k).getCourseID() == courseID) {
                                Course.getCourses().get(k).addCapacity(number);    //for course
                                break;
                            }
                        }

                        break;
                    }
                }
                break;
            }
        }
    }

    int getNumOfCourses() {
        return numOfCourses;
    }

    void decreaseNumOfCourses() {
        numOfCourses--;
    }

    public static void setMark(int lecturerID, int courseID, double mark, int studentID) {
        for (int i = 0; i < Lecturer.numberOfLecturers; i++) {

            if (lecturers.get(i).getLecturerID() == lecturerID) {

                for (int j = 0; j < Course.getCourses().size(); j++) {

                    if (Course.getCourses().get(j).getCourseID() == courseID) {

                        if (Course.getCourses().get(j).getLecturerID() == lecturerID) {

                            for (int k = 0; k < Student.getStudents().size(); k++) {

                                if (Student.getStudents().get(k).getStudentID() == studentID) {
                                    findCourseIDAndSetMarkOfStudent(courseID, mark, j, k);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    private static void findCourseIDAndSetMarkOfStudent(int courseID, double mark, int indexOfCourse, int indexOfStudent) {

        for (int t = 0; t < Student.getStudents().get(indexOfStudent).getStudentCourses().size(); t++) {

            if (Student.getStudents().get(indexOfStudent).getStudentCourses().get(t).getCourseID() == courseID) {

                Student.getStudents().get(indexOfStudent).addMark(mark, indexOfStudent, indexOfCourse);
                break;

            }
        }
    }

    static void setAllMark(int lecturerID, int courseID, double mark) {

        for (int i = 0; i < Course.getCourses().size(); i++) {

            if (Course.getCourses().get(i).getCourseID() == courseID) {

                if (Course.getCourses().get(i).getLecturerID() == lecturerID) {

                    for (int j = 0; j < Student.getStudents().size(); j++) {

                        findCourseIDAndSetMarkOfStudent(courseID, mark, i, j);
                    }
                }
                break;
            }
        }
    }
}

public class Main {

    static void adding(Scanner scanner) {
        String string = scanner.nextLine();

        if (string.equals("start semester")) {
            return;
        }

        String[] parts = string.split("(\\s)+");

        Pattern patternOfAddCourse = Pattern.compile("(addCourse)([\\s]+)(\\d{5})([\\s]+)(\\d+)([\\s])*");
        Matcher matcherOfAddCourse = patternOfAddCourse.matcher(string);

        Pattern patternOfAddStudent = Pattern.compile("(addStudent)([\\s]+)(\\d{5})([\\s]*)");
        Matcher matcherOfAddStudent = patternOfAddStudent.matcher(string);

        Pattern patternOfAddLecturer = Pattern.compile("(addLecturer)([\\s]+)(\\d{5})(([\\s]+)(\\d{5}))*");
        Matcher matcherOfAddLecturer = patternOfAddLecturer.matcher(string);
        if (matcherOfAddCourse.find()) {
            Course course = new Course(toInt(parts[1]), toInt(parts[2]));
            Course.addNewCourse(course);
        } else if (matcherOfAddStudent.find()) {
            Student student = new Student(toInt(parts[1]));
            Student.addNewStudent(student);
        } else if (matcherOfAddLecturer.find()) {
            Lecturer lecturer = new Lecturer(toInt(parts[1]), parts);
            Lecturer.addNewLecturer(lecturer);
        }

        adding(scanner);
    }

    static void registration(Scanner scanner) {
        String string = scanner.nextLine();
        if (string.equals("end registration")) {
            return;
        }

        String[] parts = string.split("(\\s)+");

        Pattern patternOfRegister = Pattern.compile("(\\d{5})([\\s]+)(register)(([\\s]+)(\\d{5}))+");
        Matcher matcherOfRegistration = patternOfRegister.matcher(string);

        Pattern patternOfAddCapacity = Pattern.compile("(\\d{5})(\\s+)capacitate(\\s+)(\\d{5})(\\s+)(\\d+)");
        Matcher matcherOfAddCapacity = patternOfAddCapacity.matcher(string);

        if (matcherOfRegistration.find()) {
            for (int i = 2; i < parts.length; i++) {
                Student.registerCourse(toInt(parts[0]), toInt(parts[i]));
            }

        } else if (matcherOfAddCapacity.find()) {
            Lecturer.addCapacity(Integer.parseInt(parts[0]), toInt(parts[2]), toInt(parts[3]));

        }

        registration(scanner);
    }

    static void checkCourses() {

        for (int i = Course.getCourses().size() - 1; i >= 0; i--) {

            if (Course.getCourses().get(i).getNumberOfRegisteredStudents() < 3) {

                Course.getCourses().get(i).setLecturerID(0);

                for (int j = 0; j < Lecturer.numberOfLecturers; j++) { //delete course for lecturer


                    for (int k = Lecturer.getLecturers().get(j).getNumOfCourses() - 1; k >= 0; k--) {
                        if (Lecturer.getLecturers().get(j).getCourses().get(k).getCourseID()
                                == Course.getCourses().get(i).getCourseID()) {
                            Lecturer.getLecturers().get(j).decreaseNumOfCourses();
                            Lecturer.getLecturers().get(j).getCourses().remove(k);
                            break;
                        }
                    }
                }

                for (int j = 0; j < Student.getStudents().size(); j++) { //delete for students

                    for (int k = Student.getStudents().get(j).getStudentCourses().size() - 1; k >= 0; k--) {

                        if (Student.getStudents().get(j).getStudentCourses().get(k).getCourseID()
                                == Course.getCourses().get(i).getCourseID()) {
                            Student.getStudents().get(j).decreaseOfNumOfUnits(Course.getCourses().get(i).getUnit());
                            Student.getStudents().get(j).getStudentCourses().remove(k);
                            break;
                        }
                    }
                }
                Course.getCourses().remove(i);
            }
        }
    }

    static void checkUnitsOfStudents() {

        for (int i = (Student.getStudents().size() - 1); i >= 0; i--) {

            if (Student.getStudents().get(i).getNumOfUnit() < 12) {

                for (int j = 0; j < Student.getStudents().get(i).getStudentCourses().size(); j++) {

                    for (int k = 0; k < Course.getCourses().size(); k++) {

                        if (Course.getCourses().get(k).getCourseID() ==
                                Student.getStudents().get(i).getStudentCourses().get(j).getCourseID()) {

                            Course.getCourses().get(k).deleteStudentID(Student.getStudents().get(i).getStudentID(), k);
                            Course.getCourses().get(k).decrementNumberOfRegisteredStudents();
                            break;

                        }
                    }
                }
                Student.getStudents().remove(i);
            }
        }
    }

    static void grading(Scanner scanner) {
        String string = scanner.nextLine();

        if (string.equals("end semester")) {
            return;
        }
        String[] parts = string.split("(\\s)+");

        Pattern patternOfGrading = Pattern.compile("(\\d{5})([\\s]+)(mark)([\\s]+)(\\d{5})(([\\s]+)" +
                "(\\d{5})([\\s]+)(((\\d){1,2})((\\.)?(\\d)*)))+");
        Matcher matcherOfGrading = patternOfGrading.matcher(string);

        Pattern patternOfGradingAll = Pattern.compile("(\\d{5})([\\s]+)(mark)([\\s]+)(\\d{5})(([\\s]+)" +
                "(((\\d){1,2})((\\.)?(\\d)*)))([\\s]+)-all");
        Matcher matcherOfGradingAll = patternOfGradingAll.matcher(string);

        Pattern patternOfW = Pattern.compile("W[\\s]+(\\d){5}[\\s]+(\\d){5}[\\s]*");
        Matcher matcherOfW = patternOfW.matcher(string);
        if (matcherOfGrading.find()) {
            for (int i = 3; i + 1 < parts.length; i += 2) {
                Lecturer.setMark(toInt(parts[0]), toInt(parts[2]), Double.parseDouble(parts[i + 1]), toInt(parts[i]));
            }
        } else if (matcherOfGradingAll.find()) {
            Lecturer.setAllMark(toInt(parts[0]), toInt(parts[2]), Double.parseDouble(parts[3]));
        } else if (matcherOfW.find()) {
            Student.deleteCourse(toInt(parts[2]), toInt(parts[1]));
        }

        grading(scanner);
    }

    private static void setAllAverageOfStudents() {
        for (int i = 0; i < Student.getStudents().size(); i++) {
            Student.setAverages(i);
        }
    }

    private static void setAllAvergaeOfCourses() {
        for (int i = 0; i < Course.getCourses().size(); i++) {
            Course.setAverage(Course.getCourses().get(i).getCourseID(), i);
        }
    }

    private static void showing(Scanner scanner) {
        String string = scanner.nextLine();
        if (string.equals("endShow")) {
            return;
        }
        if (string.equals("showRanks -all")) {
            Student.showRanks();
            showing(scanner);
            return;
        }
        String[] parts = string.split("(\\s)+");

        Pattern patternOfShowAverage = Pattern.compile("showAverage(\\s+)(\\d{5})");
        Matcher matcherOfShowAverage = patternOfShowAverage.matcher(string);

        Pattern patternOfShowRanks = Pattern.compile("showRanks(\\s+)(\\d{5})");
        Matcher matcherOfShowRanks = patternOfShowRanks.matcher(string);

        Pattern patternOfShowCourse = Pattern.compile("(showCourse)(\\s+)(\\d{5})(\\s+)(\\w)+");
        Matcher matcherOfShowCourse = patternOfShowCourse.matcher(string);

        if (matcherOfShowAverage.find()) {
            Student.showAverage(toInt(parts[1]));
        } else if (matcherOfShowRanks.find()) {
            Student.showRanks(toInt(parts[1]));
        } else if (matcherOfShowCourse.find()) {
            String[] strings = string.split("(showCourse)(\\s+)(\\d{5})(\\s+)");
            Course.showCourse(toInt(parts[1]), strings[1]);
        }
        showing(scanner);
    }

    static boolean hasZeroDecimal(double x) {
        double y = x * 10;
        if (y % 10 == 0)
            return true;
        return false;
    }

    static double accuracyOfDouble(double x) {
        x *= 10;
        x = Math.round(x);
        return (x / 10);
    }

    public static void printDouble(double x) {
        if (hasZeroDecimal(accuracyOfDouble(x)))
            System.out.println((int) accuracyOfDouble(x));
        else
            System.out.println(accuracyOfDouble(x));
    }

    public static int toInt(String string) {
        return Integer.parseInt(string);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        adding(scanner);
        registration(scanner);
        checkCourses();
        checkUnitsOfStudents();
        grading(scanner);
        setAllAverageOfStudents();
        setAllAvergaeOfCourses();
        showing(scanner);
    }
}
