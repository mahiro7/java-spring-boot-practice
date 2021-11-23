package com.gft.plannercrud.controllers;

import com.gft.plannercrud.entities.Assignment;
import com.gft.plannercrud.entities.Course;
import com.gft.plannercrud.entities.Tag;
import com.gft.plannercrud.services.AssignmentService;
import com.gft.plannercrud.services.CourseService;
import com.gft.plannercrud.services.TagService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CourseService courseService;


    @RequestMapping("/")
    public ModelAndView index() throws JSONException, ParseException {
        ModelAndView mv = new ModelAndView("index.html");

        boolean hasAssignment = assignmentService.listAssignments().isEmpty();
        boolean hasTag = tagService.listTag().isEmpty();
        boolean hasCourse = courseService.listCourses().isEmpty();

        if (hasAssignment && hasCourse && hasTag) {
            Course course1 = new Course();
            Course course2 = new Course();
            Tag tag1 = new Tag();
            Tag tag2 = new Tag();
            Assignment assignment = new Assignment();

            course1.setCode("DS121");
            course1.setName("Linguagem de Programação I");

            course2.setCode("DS133");
            course2.setName("Modelagem de Sistemas");

            tag1.setName("Importante");
            tag1.setColor("ff8599");

            tag2.setName("Recuperação");
            tag2.setColor("ffa3ff");

            assignment.setName("Trabalho Individual");
            assignment.setCourse(course1);
            assignment.setTag(tag1);
            DateFormat f = new SimpleDateFormat("yyyy/MM/dd");
            assignment.setAssignmentDate(f.parse("2021/11/21"));
            assignment.setDeadlineDate(f.parse("2021/11/27"));

            courseService.saveCourse(course1);
            courseService.saveCourse(course2);
            tagService.saveTag(tag1);
            tagService.saveTag(tag2);
            assignmentService.saveAssignment(assignment);
        }


        List<Assignment> assignments = assignmentService.listAssignments();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray dates = new JSONArray();
        JSONObject assignmentObject = new JSONObject();

        for (Assignment assignment: assignments) {
            assignmentObject.put("title", assignment.getName());
            assignmentObject.put("start", dateFormat.format(assignment.getAssignmentDate()));
            assignmentObject.put("end", dateFormat.format(assignment.getDeadlineDate()));
            dates.put(assignmentObject);
        }

        Date currentDate = new Date();

        mv.addObject("currentDate", dateFormat.format(currentDate));
        mv.addObject("json", dates.toString());

        return mv;
    }
}
