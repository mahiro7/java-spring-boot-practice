package com.gft.plannercrud.controllers;

import com.gft.plannercrud.entities.Assignment;
import com.gft.plannercrud.entities.Course;
import com.gft.plannercrud.entities.Tag;
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
import java.awt.*;

@Controller
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @RequestMapping
    public ModelAndView listTags() {
        ModelAndView mv = new ModelAndView("tag/list.html");
        mv.addObject("tags", tagService.listTag());
        return mv;
    }

    @RequestMapping("/new")
    public ModelAndView newTag() {
        ModelAndView mv = new ModelAndView("tag/form.html");
        mv.addObject("page", " - Cadastro");
        mv.addObject("button", "Cadastrar");
        mv.addObject("tag", new Tag());
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView saveTag(@Valid Tag tag, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("tag/form.html");

        boolean newTag = true;

        if (tag.getId() != null) {
            newTag = false;
        }

        if (bindingResult.hasErrors()) {
            mv.addObject("tag", tag);

            if (newTag) {
                mv.addObject("page", " - Cadastro");
                mv.addObject("button", "Cadastrar");
            } else {
                mv.addObject("page", " - Edição");
                mv.addObject("button", "Salvar");
            }
            return mv;
        }


        if (newTag) {
            tagService.saveTag(tag);
            mv.addObject("assignment", new Assignment());
            mv.addObject("page", " - Cadastro");
            mv.addObject("button", "Cadastrar");
            mv.addObject("message", "Tarefa cadastrada com sucesso!");
        } else {
            mv.addObject("assignment", tagService.saveTag(tag));
            mv.addObject("page", " - Edição");
            mv.addObject("button", "Salvar");
            mv.addObject("message", "Tarefa salva com sucesso!");
        }
        return mv;
    }

    @RequestMapping("/edit")
    public ModelAndView editTag(@RequestParam Long id) throws Exception {
        ModelAndView mv = new ModelAndView("tag/form.html");
        mv.addObject("page", " - Edição");
        mv.addObject("button", "Salvar");

        try {
            Tag tag = tagService.getTagById(id);
            mv.addObject("tag", tag);
        } catch (Exception e) {
            mv.addObject("tag", new Tag());
            mv.addObject("error", e.getMessage());
        }

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteTag(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/tag");

        try {
            tagService.deleteTag(id);
            redirectAttributes.addFlashAttribute("message", "Tag excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Tag excluída com sucesso!");
        }

        return mv;
    }
}
