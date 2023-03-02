package com.example.demo.controllers;
import com.example.demo.models.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import com.example.demo.repo.PostRepository;
import java.util.ArrayList;
import java.util.Optional;

//import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class BlogController {

    private final PostRepository postRepository;
    @Autowired
    public BlogController(PostRepository postRepository){
        this.postRepository = postRepository;
    }


    @GetMapping("/")
    public String home(Model model, String name) {
        model.addAttribute("home", name);
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model, String name) {
        model.addAttribute("name", name);
        return "about";
    }


    @GetMapping("/blogmain")
    public String blogmain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blogmain";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value="id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value="id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(
            @PathVariable(value="id") long id,
            @PathVariable(value="title") String title,
            @PathVariable(value="anons") String anons,
            @PathVariable(value="full_text") String full_text,
            Model model) {
        Post post = postRepository.findById(id).orElseThrow(() -> new MultipartException("404"));
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(
            @PathVariable(value="id") long id,
            Model model) {
        Post post = postRepository.findById(id).orElseThrow(() -> new MultipartException("404"));
        postRepository.delete(post);
        return "redirect:/blog";
    }
}
