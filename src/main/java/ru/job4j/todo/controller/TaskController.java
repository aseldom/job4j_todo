package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
@ThreadSafe
public class TaskController {

    private final TaskService taskService;

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/new_tasks")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findNewComplete(true));
        return "tasks/list";
    }

    @GetMapping("/complete")
    public String getComplete(Model model) {
        model.addAttribute("tasks", taskService.findNewComplete(false));
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Task task, Model model) {
        if (!taskService.update(task)) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        if (taskService.add(task) == null) {
            model.addAttribute("message", "Создать задачу не удалось");
            return "errors/404";

        }
        return "redirect:/tasks";
    }

    @GetMapping("/done/{id}")
    public String updateDone(Model model, @PathVariable int id) {
        if (!taskService.updateDone(id)) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if (!taskService.deleteById(id)) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

}