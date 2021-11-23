package com.gft.plannercrud.controllers;

import com.gft.plannercrud.entities.Course;
import com.gft.plannercrud.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/new")
    public ModelAndView newCourse() {
        ModelAndView mv = new ModelAndView("course/form.html");
        mv.addObject("course", new Course());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");

        return mv;
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public ModelAndView saveCourse(@Valid Course course, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("course/form.html");

        boolean newCourse = true;


        if (course.getId() != null) {
            newCourse = false;
        }

        if (bindingResult.hasErrors()) {
            mv.addObject("course", course);
            if (newCourse) {
                mv.addObject("page", " - Cadastro");
                mv.addObject("button", "Cadastrar");
            } else {
                mv.addObject("page", " - Edição");
                mv.addObject("button", "Salvar");
            }
            return mv;
        }


        if (newCourse) {
            // verifying if field 'code' is unique...
            if (courseService.courseCodeExists(course.getCode())) {
                //error
                mv.addObject("course", course);
                mv.addObject("codeError", "Código da disciplina já foi cadastrada!");
                mv.addObject("page", " - Cadastro");
                mv.addObject("button", "Cadastrar");
                return mv;
            }
            courseService.saveCourse(course);
            mv.addObject("course", new Course());
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
            mv.addObject("message", "Disciplina cadastrada com sucesso!");
        } else {
            mv.addObject("course", courseService.saveCourse(course));
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
            mv.addObject("message", "Disciplina salva com sucesso!");
        }

        return mv;
    }

    @RequestMapping
    public ModelAndView listCourse() {
        ModelAndView mv = new ModelAndView("course/list.html");

        mv.addObject("courses", courseService.listCourses());

        return mv;
    }

    @RequestMapping("/edit")
    public ModelAndView editCourse(@RequestParam Long id) throws Exception {
        ModelAndView mv = new ModelAndView("course/form.html");
        mv.addObject("page", " - Edição");
        mv.addObject("button", "Salvar");

        try {
            Course course = courseService.findById(id);
            mv.addObject("course", course);
        } catch (Exception e) {
            mv.addObject("course", new Course());
            mv.addObject("error", e.getMessage());
        }

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteCourse(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/course");

        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("message", "Disciplina excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Disciplina excluída com sucesso!");
        }

        return mv;
    }
}
