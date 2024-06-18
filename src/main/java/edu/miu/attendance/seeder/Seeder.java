package edu.miu.attendance.seeder;

import edu.miu.attendance.domain.*;
import edu.miu.attendance.enumeration.GenderType;
import edu.miu.attendance.enumeration.RoleType;
import edu.miu.attendance.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Profile("dev")
@Transactional
@Slf4j
public class Seeder {

    private final boolean seed;

    private final RoleRepository roleRepository;

    private final LocationRepository locationRepository;

    private final LocationTypeRepository locationTypeRepository;

    private final CourseRepository courseRepository;

    private final FacultyRepository facultyRepository;

    public Seeder(@Value("${spring.application.seed:false}") boolean seed,
                  RoleRepository roleRepository,
                  LocationRepository locationRepository, LocationTypeRepository locationTypeRepository, CourseRepository courseRepository, FacultyRepository facultyRepository) {
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
        this.locationTypeRepository = locationTypeRepository;
        this.courseRepository = courseRepository;
        this.facultyRepository = facultyRepository;
        this.seed = seed;
    }

    @PostConstruct
    public void seedDatabase() {
        if (!seed) {
            return;
        }

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

}
