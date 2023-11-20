package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@AllArgsConstructor
@ThreadSafe
public class TaskController {

    private final TaskService taskService;

    @GetMapping({"/", "/index", "/tasks"})
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/new_tasks")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findNew());
        return "tasks/list";
    }

    @GetMapping("/complete")
    public String getComplete(Model model) {
        model.addAttribute("tasks", taskService.findComplete());
        return "tasks/list";
    }

    @GetMapping("/tasks/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("tasks/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/edit";
    }

    @PostMapping("/tasks/edit")
    public String edit(@ModelAttribute Task task, Model model) {
        try {
            taskService.update(task);
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/tasks/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/tasks/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.add(task);
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/tasks/done/{id}")
    public String updateDone(Model model, @PathVariable int id) {
        try {
            if (!taskService.updateDone(id)) {
                model.addAttribute("message", "Задание с указанным идентификатором не найдено");
                return "errors/404";
            }
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/tasks/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        try {
            if (!taskService.deleteById(id)) {
                model.addAttribute("message", "Задание с указанным идентификатором не найдено");
                return "errors/404";
            }
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

}