package edu.miu.attendance.seeder;

import edu.miu.attendance.domain.Course;
import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.LocationType;
import edu.miu.attendance.repository.CourseRepository;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.repository.LocationTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@Transactional
@Slf4j
public class Seeder {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Autowired
    private CourseRepository courseRepository;


    @PostConstruct
    public void seedDatabase(){
        log.info("\nSeeding to the database");

        log.info("Seeding Location types");
        addLocationTypes();

        log.info("Seeding Locations");
        // Add Locations
        addLocations();

        log.info("Seeding Courses");
        addCourses();
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

    public void addCourses(){
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

}
