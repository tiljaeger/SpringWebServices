package com.springboot.soap.webservices.springbootsoap.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soabwebservives.courses.CourseDetails;
import com.soabwebservives.courses.DeleteCourseDetailsRequest;
import com.soabwebservives.courses.DeleteCourseDetailsResponse;
import com.soabwebservives.courses.GetAllCourseDetailsRequest;
import com.soabwebservives.courses.GetAllCourseDetailsResponse;
import com.soabwebservives.courses.GetCourseDetailsRequest;
import com.soabwebservives.courses.GetCourseDetailsResponse;
import com.springboot.soap.webservices.springbootsoap.soap.bean.Course;
import com.springboot.soap.webservices.springbootsoap.soap.exception.CourseNotFoundException;
import com.springboot.soap.webservices.springbootsoap.soap.service.CourseDetailsService;
import com.springboot.soap.webservices.springbootsoap.soap.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

  @Autowired
  CourseDetailsService service;

  // methode
  // input GetCourseDetailsRequest with namespace http://soabwebservives.com/courses
  // output GetCourseDetailsResponse
  @PayloadRoot(namespace = "http://soabwebservives.com/courses", localPart = "GetCourseDetailsRequest")
  @ResponsePayload
  public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

    Course course = service.findById(request.getId());
    
    if(course == null) {
//      throw new RuntimeErrorException(null, "Invalid course id " + request.getId());
      throw new CourseNotFoundException("Invalid course id " + request.getId());
    }

    return mapCourseDetails(course);
  }

  @PayloadRoot(namespace = "http://soabwebservives.com/courses", localPart = "DeleteCourseDetailsRequest")
  @ResponsePayload
  public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

    Status status = service.deleteById(request.getId());
    DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
    response.setStatus(mapStatus(status));
    return response;
  }

  private GetCourseDetailsResponse mapCourseDetails(Course course) {
    GetCourseDetailsResponse response = new GetCourseDetailsResponse();
    response.setCourseDetails(mapCourse(course));
    return response;
  }

  private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
    GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
    for (Course course : courses) {
      CourseDetails mapCourse = mapCourse(course);
      response.getCourseDetails().add(mapCourse);
    }
    return response;
  }

  private com.soabwebservives.courses.Status mapStatus(Status status) {
    if (status == Status.FAILURE) {
      return com.soabwebservives.courses.Status.FAILURE;
    }
    return com.soabwebservives.courses.Status.SUCCESS;
  }

  private CourseDetails mapCourse(Course course) {
    CourseDetails courseDetails = new CourseDetails();

    courseDetails.setId(course.getId());

    courseDetails.setName(course.getName());

    courseDetails.setDescription(course.getDescription());
    return courseDetails;
  }

  @PayloadRoot(namespace = "http://soabwebservives.com/courses", localPart = "GetAllCourseDetailsRequest")
  @ResponsePayload
  public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
      @RequestPayload GetAllCourseDetailsRequest request) {

    List<Course> courses = service.findAll();

    return mapAllCourseDetails(courses);
  }

}
