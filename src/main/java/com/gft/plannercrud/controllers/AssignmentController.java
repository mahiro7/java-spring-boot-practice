package com.gft.plannercrud.controllers;

import com.gft.plannercrud.entities.Assignment;
import com.gft.plannercrud.entities.Course;
import com.gft.plannercrud.entities.Tag;
import com.gft.plannercrud.services.AssignmentService;
import com.gft.plannercrud.services.CourseService;
import com.gft.plannercrud.services.TagService;
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
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TagService tagService;

    @RequestMapping
    public ModelAndView listAssignments() {
        ModelAndView mv = new ModelAndView("assignment/list.html");

        mv.addObject("assignments", assignmentService.listAssignments());

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newAssignment() {
        ModelAndView mv = new ModelAndView("assignment/form.html");

        mv.addObject("assignment", new Assignment());
        mv.addObject("tag", new Tag());
        mv.addObject("courses", courseService.listCourses());
        mv.addObject("tags", tagService.listTag());
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");

        return mv;
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public ModelAndView saveAssignment(@Valid Assignment assignment, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("assignment/form.html");
        mv.addObject("tag", new Tag());

        boolean newAssignment = true;


        if (assignment.getId() != null) {
            newAssignment = false;
        }

        if (bindingResult.hasErrors()) {
            mv.addObject("assignment", assignment);

            if (newAssignment) {
                mv.addObject("page", " - Cadastro");
                mv.addObject("button", "Cadastrar");
            } else {
                mv.addObject("page", " - Edição");
                mv.addObject("button", "Salvar");
            }

            return mv;
        }


        if (newAssignment) {
            assignmentService.saveAssignment(assignment);
            mv.addObject("assignment", new Assignment());
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
            mv.addObject("message", "Tarefa cadastrada com sucesso!");
        } else {
            mv.addObject("assignment", assignmentService.saveAssignment(assignment));
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
            mv.addObject("message", "Tarefa salva com sucesso!");
        }

        return mv;
    }


    @RequestMapping("/delete")
    public ModelAndView deleteAssignment(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/assignment");

        try {
            assignmentService.deleteAssignment(id);
            redirectAttributes.addFlashAttribute("message", "Tarefa excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Tarefa excluída com sucesso!");
        }

        return mv;
    }
}
