package com.springboot.soap.webservices.springbootsoap.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.soap.webservices.springbootsoap.soap.bean.Course;

@Component
public class CourseDetailsService {
  public enum Status {
    SUCCESS, FAILURE;
  }

  private static List<Course> courses = new ArrayList<Course>();

  static {
    Course course1 = new Course(1, "Course A", "Description A");
    courses.add(course1);

    Course course2 = new Course(2, "Course B", "Description B");
    courses.add(course2);

    Course course3 = new Course(3, "Course C", "Description C");
    courses.add(course3);

    Course course4 = new Course(4, "Course D", "Description D");
    courses.add(course4);
  }

  // course - 1
  public Course findById(int id) {
    for (Course course : courses) {
      if (course.getId() == id)
        return course;
    }
    return null;
  }

  // courses
  public List<Course> findAll() {
    return courses;
  }

  public Status deleteById(int id) {
    Iterator<Course> iterator = courses.iterator();
    while (iterator.hasNext()) {
      Course course = iterator.next();
      if (course.getId() == id) {
        iterator.remove();
        return Status.SUCCESS;
      }
    }
    return Status.FAILURE;
  }

  // updating course & new course
}