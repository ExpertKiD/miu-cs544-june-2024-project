package edu.miu.attendance.seeder;

import edu.miu.attendance.domain.*;
import edu.miu.attendance.domain.enums.CourseOfferingType;
import edu.miu.attendance.domain.enums.GenderType;
import edu.miu.attendance.domain.enums.LocationType;
import edu.miu.attendance.domain.enums.RoleType;
import edu.miu.attendance.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Profile("dev")
@Slf4j
public class SeederHelper {
    private static final LocalTime MORNING_START = LocalTime.of(10, 0);
    private static final LocalTime MORNING_END = LocalTime.of(12, 30);
    private static final LocalTime AFTERNOON_START = LocalTime.of(13, 30);
    private static final LocalTime AFTERNOON_END = LocalTime.of(15, 30);
    private final CourseOfferingRepository courseOfferingRepository;
    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;
    private final LocationTypeRepository locationTypeRepository;
    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    private final Random random = new Random(42);
    private final HashMap<CourseOffering, Location> locationHashMap =
            new HashMap<>();

    public SeederHelper(
            RoleRepository roleRepository,
            LocationRepository locationRepository,
            LocationTypeRepository locationTypeRepository,
            CourseRepository courseRepository,
            FacultyRepository facultyRepository,
            CourseOfferingRepository courseOfferingRepository,
            StudentRepository studentRepository) {
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
        this.locationTypeRepository = locationTypeRepository;
        this.courseRepository = courseRepository;
        this.facultyRepository = facultyRepository;
        this.courseOfferingRepository = courseOfferingRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void seedDatabase() {
        log.info("\nSeeding to the database");

        log.info("Seeding Location types");
        addLocationTypes();

        log.info("Seeding Roles");
        addRoles();

        log.info("Seeding Locations");
        // Add Locations
        addLocations();

        log.info("Seeding Courses");
        addCourses();

        log.info("Seeding Faculties");
        addFaculties();

        log.info("Seeding Course Offerings");
        addCourseOfferings();

        log.info("Seeding Students");
        addStudents();
    }

    private void addLocationTypes() {
        // Add Location types to database
        List<LocationType> locationTypes = new ArrayList<>(
                List.of(
                        new LocationType(null, "Online"),
                        new LocationType(null, "OnCampus")
                )
        );

        locationTypeRepository.saveAll(locationTypes);
    }

    private void addLocations() {
        // Get location types

        var locationTypes = locationTypeRepository.findAll();

        List<Location> locations = new ArrayList<>();

        Location l1 = new Location();

        l1.setCapacity(40);
        l1.setLocationType(locationTypes.get(1));
        l1.setName("McLaughin Building");

        Location l2 = new Location();

        l2.setCapacity(40);
        l2.setLocationType(locationTypes.get(1));
        l2.setName("Veril Hall");

        Location l3 = new Location();

        l3.setCapacity(100);
        l3.setLocationType(locationTypes.get(1));
        l3.setName("Argiro");

        Location l4 = new Location();

        l4.setCapacity(200);
        l4.setLocationType(locationTypes.get(0));
        l4.setName("Online");

        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);

        locationRepository.saveAll(locations);
    }

    public void addCourses() {
        List<Course> courses = new ArrayList<>();

        // Initialize courses with descriptions and credits
        Course mpp = new Course();
        mpp.setName("Modern Programming Practices");
        mpp.setCode("MPP");
        mpp.setDepartment("ComPro");
        mpp.setDescription("Introduction to modern programming practices and methodologies.");
        mpp.setCredits(4.0);

        Course fpp = new Course();
        fpp.setName("Fundamental Programming Practices");
        fpp.setCode("FPP");
        fpp.setDepartment("ComPro");
        fpp.setDescription("Basic principles and fundamentals of programming.");
        fpp.setCredits(4.0);

        Course waa = new Course();
        waa.setName("Web Application Architecture");
        waa.setCode("WAA");
        waa.setDepartment("ComPro");
        waa.setDescription("Study of architectural patterns and best practices for web applications.");
        waa.setCredits(4.0);

        Course mwa = new Course();
        mwa.setName("Modern Web Applications");
        mwa.setCode("MWA");
        mwa.setDepartment("ComPro");
        mwa.setDescription("Advanced techniques and frameworks for developing modern web applications.");
        mwa.setCredits(4.0);

        Course ml = new Course();
        ml.setName("Machine Learning");
        ml.setCode("ML");
        ml.setDepartment("ComPro");
        ml.setDescription("Introduction to machine learning algorithms and applications.");
        ml.setCredits(4.0);

        Course dbms = new Course();
        dbms.setName("Database Management System");
        dbms.setCode("DBMS");
        dbms.setDepartment("ComPro");
        dbms.setDescription("Fundamentals of database design, management, and optimization.");
        dbms.setCredits(4.0);

        Course wap = new Course();
        wap.setName("Web Applications Programming");
        wap.setCode("WAP");
        wap.setDepartment("ComPro");
        wap.setDescription("Hands-on programming skills for web applications using modern frameworks.");
        wap.setCredits(4.0);

        Course ea = new Course();
        ea.setName("Enterprise Architecture");
        ea.setCode("EA");
        ea.setDepartment("ComPro");
        ea.setDescription("Design and implementation of enterprise-level software systems.");
        ea.setCredits(4.0);

        // Set prerequisites for each course
        mpp.getPrerequisites().add(fpp);  // MPP requires FPP
        waa.getPrerequisites().addAll(List.of(mpp, wap));  // WAA requires MPP and WAP
        mwa.getPrerequisites().addAll(List.of(mpp, wap));  // MWA requires MPP and WAP
        ml.getPrerequisites().add(mpp);  // ML requires MPP
        dbms.getPrerequisites().add(mpp);  // DBMS requires MPP
        wap.getPrerequisites().add(mpp);  // WAP requires MPP
        ea.getPrerequisites().add(mpp);  // EA requires MPP

        // Add courses to the list
        courses.add(mpp);
        courses.add(fpp);
        courses.add(waa);
        courses.add(mwa);
        courses.add(ml);
        courses.add(dbms);
        courses.add(wap);
        courses.add(ea);

        // Save all courses
        courseRepository.saveAll(courses);
    }

    void addFaculties() {
        Map<RoleType, Role> rolesMap = roleRepository.findAll().stream()
                .filter(role -> role.getRoleType() == RoleType.FACULTY || role.getRoleType() == RoleType.ADMIN)
                .collect(Collectors.toMap(Role::getRoleType, role -> role));

        // Initialize faculties
        Faculty f1 = new Faculty();
        f1.setUsername("pcoraza");
        f1.setPassword("123456");
        f1.setFirstName("Paul");
        f1.setLastName("Coraza");
        f1.setPosition("Professor");
        f1.setBirthDate(LocalDate.of(1980, 4, 1));
        f1.setHobbies(List.of("Reading", "Writing", "Teaching"));
        f1.setEmailAddress("pcoraza@miu.edu");
        f1.setGenderType(GenderType.MALE);
        f1.getRoles().add(rolesMap.get(RoleType.FACULTY));

        Faculty f2 = new Faculty();
        f2.setUsername("aochirbat");
        f2.setPassword("abcdef");
        f2.setFirstName("Ankhtuya");
        f2.setLastName("Ochirbat");
        f2.setPosition("Associate Professor");
        f2.setBirthDate(LocalDate.of(1985, 8, 15));
        f2.setHobbies(List.of("Traveling", "Cooking", "Music"));
        f2.setEmailAddress("aochirbat@miu.edu");
        f2.setGenderType(GenderType.FEMALE);
        f2.getRoles().add(rolesMap.get(RoleType.FACULTY));

        Faculty f3 = new Faculty();
        f3.setUsername("psalek");
        f3.setPassword("qwerty");
        f3.setFirstName("Payman");
        f3.setLastName("Salek");
        f3.setPosition("Professor");
        f3.setBirthDate(LocalDate.of(1982, 3, 20));
        f3.setHobbies(List.of("Soccer", "Gardening", "Movies", "TM"));
        f3.setEmailAddress("psalek@miu.edu");
        f3.setGenderType(GenderType.MALE);
        f3.getRoles().add(rolesMap.get(RoleType.FACULTY));
        f3.getRoles().add(rolesMap.get(RoleType.ADMIN));

        Faculty f4 = new Faculty();
        f4.setUsername("nnajeeb");
        f4.setPassword("xyz123");
        f4.setFirstName("Najeeb");
        f4.setLastName("Najeeb");
        f4.setPosition("Professor");
        f4.setBirthDate(LocalDate.of(1978, 12, 10));
        f4.setHobbies(List.of("Reading", "Swimming", "Photography"));
        f4.setEmailAddress("nnajeeb@miu.edu");
        f4.setGenderType(GenderType.MALE);
        f4.getRoles().add(rolesMap.get(RoleType.FACULTY));

        Faculty f5 = new Faculty();
        f5.setUsername("tiumur");
        f5.setPassword("password");
        f5.setFirstName("Tacettin Umur");
        f5.setLastName("Inan");
        f5.setPosition("Professor");
        f5.setBirthDate(LocalDate.of(1970, 5, 5));
        f5.setHobbies(List.of("Chess", "Traveling", "History"));
        f5.setEmailAddress("tiumur@miu.edu");
        f5.setGenderType(GenderType.MALE);
        f5.getRoles().add(rolesMap.get(RoleType.FACULTY));

        Faculty f6 = new Faculty();
        f6.setUsername("saburas");
        f6.setPassword("abcdefg");
        f6.setFirstName("Sanad");
        f6.setLastName("Aburass");
        f6.setPosition("Associate Professor");
        f6.setBirthDate(LocalDate.of(1988, 9, 25));
        f6.setHobbies(List.of("Reading", "Writing", "Research"));
        f6.setEmailAddress("saburas@miu.edu");
        f6.setGenderType(GenderType.MALE);
        f6.getRoles().add(rolesMap.get(RoleType.FACULTY));

        // Save faculties
        facultyRepository.saveAll(List.of(f1, f2, f3, f4, f5, f6));
    }

    void addRoles() {
        Role r1 = new Role();
        r1.setRoleType(RoleType.ADMIN);

        Role r2 = new Role();
        r2.setRoleType(RoleType.STUDENT);

        Role r3 = new Role();
        r3.setRoleType(RoleType.STAFF);

        Role r4 = new Role();
        r4.setRoleType(RoleType.FACULTY);

        roleRepository.saveAll(List.of(r1, r2, r3, r4));
    }

    @Transactional
    void addCourseOfferings() {
        // Get courses
        List<Course> courses = courseRepository.findAll();
        List<Faculty> faculties = facultyRepository.findAll();

        List<Integer> months = new ArrayList<>(List.of(5, 6, 7));

        List<CourseOffering> courseOfferings = new ArrayList<>();


        var rooms = new ArrayList<>(List.of("VR17", "Dalby Hall", "MH12",
                "VR12", "VR13"));

        // for each course
        for (Course course : courses) {
            // for each month
            for (var month : months) {
                var faculty = faculties.get(random.nextInt(faculties.size()));
                var room = rooms.get(random.nextInt(rooms.size()));

                CourseOffering courseOffering = new CourseOffering();

                courseOffering.setSessions(getSessions(2024, month));
                courseOffering.setCourse(course);
                courseOffering.setCapacity(random.nextInt(24, 51));
                courseOffering.setFaculty(faculty);
                courseOffering.setRoom(room);
                courseOffering.setCourseOfferingType(CourseOfferingType.ON_CAMPUS);

                courseOfferings.add(courseOffering);
            }
        }

        courseOfferingRepository.saveAll(courseOfferings);


    }

    private List<Session> getSessions(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Session> sessions = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek().getValue() == 7) { // Skip Sundays
                continue;
            }

            // Morning session
            Session morningSession = new Session();
            morningSession.setSessionDate(date);
            morningSession.setStartTime(MORNING_START);
            morningSession.setEndTime(MORNING_END);
            sessions.add(morningSession);

            // Afternoon session, except for the last day of the month
            if (date.getDayOfMonth() != endDate.getDayOfMonth()) {
                Session afternoonSession = new Session();
                afternoonSession.setSessionDate(date);
                afternoonSession.setStartTime(AFTERNOON_START);
                afternoonSession.setEndTime(AFTERNOON_END);
                sessions.add(afternoonSession);
            }
        }

        return sessions;
    }

    void addStudents() {
        List<Student> students = new ArrayList<>();

        Map<RoleType, Role> studentRolesMap = roleRepository.findAll().stream()
                .filter(role -> role.getRoleType() == RoleType.STUDENT)
                .collect(Collectors.toMap(Role::getRoleType, role -> role));

        List<Faculty> faculties = facultyRepository.findAll();

        List<CourseOffering> courseOfferings =
                courseOfferingRepository.findAll();

        List<CourseOffering> mayCourseOfferings =
                courseOfferings.stream().filter(
                                courseOffering -> courseOffering
                                        .getSessions()
                                        .stream()
                                        .anyMatch(session -> session.getSessionDate().getMonth() == Month.MAY)
                        )
                        .toList();

        List<CourseOffering> juneCourseOfferings =
                courseOfferings.stream().filter(
                                courseOffering -> courseOffering
                                        .getSessions()
                                        .stream()
                                        .anyMatch(session -> session.getSessionDate().getMonth() == Month.JUNE)
                        )
                        .toList();

        List<CourseOffering> julyCourseOfferings =
                courseOfferings.stream().filter(
                                courseOffering -> courseOffering
                                        .getSessions()
                                        .stream()
                                        .anyMatch(session -> session.getSessionDate().getMonth() == Month.JULY)
                        )
                        .toList();


        Student student1 = new Student();
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setGenderType(GenderType.MALE);
        student1.setBirthDate(LocalDate.of(2000, 1, 1));
        student1.setEmailAddress("john.doe@miu.edu");
        student1.setUsername("johndoe");
        student1.setPassword("password123");
        student1.setEntry("Winter 2023");
        student1.setAlternateID("A1001");
        student1.setApplicantId("APP1001");
        student1.setStudentId("ST1001");
        student1.getRoles().add(studentRolesMap.get(RoleType.STUDENT));
        student1.setFacultyAdviser(faculties.get(random.nextInt(faculties.size())));
        student1.getCoursesRegistrations().addAll(
                List.of(
                        mayCourseOfferings.get(random.nextInt(mayCourseOfferings.size())),
                        juneCourseOfferings.get(random.nextInt(juneCourseOfferings.size())),
                        julyCourseOfferings.get(random.nextInt(julyCourseOfferings.size()))
                )
        );
        student1.getAttendanceRecords().addAll(getAttendanceRecords(student1));


        Student student2 = new Student();
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setGenderType(GenderType.FEMALE);
        student2.setBirthDate(LocalDate.of(1999, 5, 15));
        student2.setEmailAddress("jane.smith@miu.edu");
        student2.setUsername("janesmith");
        student2.setPassword("password123");
        student2.setEntry("Spring 2023");
        student2.setAlternateID("A1002");
        student2.setApplicantId("APP1002");
        student2.setStudentId("ST1002");
        student2.getRoles().add(studentRolesMap.get(RoleType.STUDENT));
        student2.setFacultyAdviser(faculties.get(random.nextInt(faculties.size())));
        student2.getCoursesRegistrations().addAll(
                List.of(
                        mayCourseOfferings.get(random.nextInt(mayCourseOfferings.size())),
                        juneCourseOfferings.get(random.nextInt(juneCourseOfferings.size())),
                        julyCourseOfferings.get(random.nextInt(julyCourseOfferings.size()))
                )
        );
        student2.getAttendanceRecords().addAll(getAttendanceRecords(student2));

        Student student3 = new Student();
        student3.setFirstName("Michael");
        student3.setLastName("Johnson");
        student3.setGenderType(GenderType.MALE);
        student3.setBirthDate(LocalDate.of(1998, 8, 21));
        student3.setEmailAddress("michael.johnson@miu.edu");
        student3.setUsername("michaeljohnson");
        student3.setPassword("password123");
        student3.setEntry("Summer 2023");
        student3.setAlternateID("A1003");
        student3.setApplicantId("APP1003");
        student3.setStudentId("ST1003");
        student3.getRoles().add(studentRolesMap.get(RoleType.STUDENT));
        student3.setFacultyAdviser(faculties.get(random.nextInt(faculties.size())));
        student3.getCoursesRegistrations().addAll(
                List.of(
                        mayCourseOfferings.get(random.nextInt(mayCourseOfferings.size())),
                        juneCourseOfferings.get(random.nextInt(juneCourseOfferings.size())),
                        julyCourseOfferings.get(random.nextInt(julyCourseOfferings.size()))
                )
        );
        student3.getAttendanceRecords().addAll(getAttendanceRecords(student3));


        Student student4 = new Student();
        student4.setFirstName("Emily");
        student4.setLastName("Davis");
        student4.setGenderType(GenderType.FEMALE);
        student4.setBirthDate(LocalDate.of(2001, 12, 10));
        student4.setEmailAddress("emily.davis@miu.edu");
        student4.setUsername("emilydavis");
        student4.setPassword("password123");
        student4.setEntry("Fall 2023");
        student4.setAlternateID("A1004");
        student4.setApplicantId("APP1004");
        student4.setStudentId("ST1004");
        student4.getRoles().add(studentRolesMap.get(RoleType.STUDENT));
        student4.setFacultyAdviser(faculties.get(random.nextInt(faculties.size())));
        student4.getCoursesRegistrations().addAll(
                List.of(
                        mayCourseOfferings.get(random.nextInt(mayCourseOfferings.size())),
                        juneCourseOfferings.get(random.nextInt(juneCourseOfferings.size())),
                        julyCourseOfferings.get(random.nextInt(julyCourseOfferings.size()))
                )
        );
        student4.getAttendanceRecords().addAll(getAttendanceRecords(student4));

        Student student5 = new Student();
        student5.setFirstName("David");
        student5.setLastName("Brown");
        student5.setGenderType(GenderType.MALE);
        student5.setBirthDate(LocalDate.of(2002, 3, 30));
        student5.setEmailAddress("david.brown@miu.edu");
        student5.setUsername("davidbrown");
        student5.setPassword("password123");
        student5.setEntry("Spring 2024");
        student5.setAlternateID("A1005");
        student5.setApplicantId("APP1005");
        student5.setStudentId("ST1005");
        student5.getRoles().add(studentRolesMap.get(RoleType.STUDENT));
        student5.setFacultyAdviser(faculties.get(random.nextInt(faculties.size())));
        student5.getCoursesRegistrations().addAll(
                List.of(
                        mayCourseOfferings.get(random.nextInt(mayCourseOfferings.size())),
                        juneCourseOfferings.get(random.nextInt(juneCourseOfferings.size())),
                        julyCourseOfferings.get(random.nextInt(julyCourseOfferings.size()))
                )
        );
        student5.getAttendanceRecords().addAll(getAttendanceRecords(student5));

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);

        studentRepository.saveAll(students);
    }

    List<AttendanceRecord> getAttendanceRecords(Student student) {
        List<AttendanceRecord> records = new ArrayList<>();

        student.getCoursesRegistrations().forEach(
                courseOffering -> {
                    courseOffering.getSessions().stream()
                            .filter(session -> session.getSessionDate()
                                    .isBefore(LocalDate.now().minusDays(1))
                            ).forEach(
                                    session -> {
                                        records.add(getAttendanceRecord(student,
                                                session, courseOffering));
                                    }
                            );
                }
        );

        return records;
    }

    private AttendanceRecord getAttendanceRecord(Student student, Session session,
                                                 CourseOffering courseOffering) {


        List<Location> locations = locationRepository.findAll();
        courseOfferingRepository.findAll().stream()
                .forEach(courseOffering1 -> {
                    locationHashMap.putIfAbsent(courseOffering1,
                            locations.get(random.nextInt(locations.size())));
                });

        AttendanceRecord mRecord = new AttendanceRecord();

        mRecord.setStudent(student);
        mRecord.setSession(session);

        LocalDateTime sessionDateTime =
                session.getSessionDate().atTime(session.getStartTime());

        LocalDateTime attendanceDateTime =
                sessionDateTime.plusMinutes(random.nextInt(-10,
                        20));

        mRecord.setScanDateTime(attendanceDateTime);
        mRecord.setCourseOffering(courseOffering);
        mRecord.setLocation(locationHashMap.get(courseOffering));

        return mRecord;
    }

}
