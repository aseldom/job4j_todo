package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static ru.job4j.todo.util.ConvertTimeZone.covertTimeZone;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
@ThreadSafe
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping()
    public String getAll(Model model, HttpSession session) {
        model.addAttribute("tasks",
                covertTimeZone(taskService.findAll(), (User) session.getAttribute("user"))
        );
        return "tasks/list";
    }

    @GetMapping("/new_tasks")
    public String getNew(Model model, HttpSession session) {
        model.addAttribute("tasks", covertTimeZone(taskService.findCompleteNew(false), (User) session.getAttribute("user"))
        );
        return "tasks/list";
    }

    @GetMapping("/complete")
    public String getComplete(Model model, HttpSession session) {
        model.addAttribute("tasks",
                covertTimeZone(taskService.findCompleteNew(true), (User) session.getAttribute("user"))
        );
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpSession session) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task",
                covertTimeZone(List.of(taskOptional.get()), (User) session.getAttribute("user"))
                        .iterator().next()
        );
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
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Task task, @RequestParam List<Integer> categoriesId) {
        task.setCategories(categoryService.findAllById(categoriesId));
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @RequestParam List<Integer> categoriesId, HttpSession session) {
        task.setCategories(categoryService.findAllById(categoriesId));
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        taskService.add(task);
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