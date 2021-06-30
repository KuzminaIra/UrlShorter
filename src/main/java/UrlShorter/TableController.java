package UrlShorter;

import java.io.*;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


class validator {
    public static UrlValidator urlValidator = new UrlValidator();
    public static boolean isValid(String url) {
        return urlValidator.isValid(url);
    }
}

@Controller
public class TableController {

    @Autowired
    TableRepository tableRepository;

    @GetMapping("/short")
    public String shortForm(Model model) {
        model.addAttribute("newUrl", new NewUrl(""));
        model.addAttribute("resultUrl", new NewUrl(""));
        model.addAttribute("div", false);
        model.addAttribute("divResult", false);
        return "short";
    }

    @PostMapping("/short")
    public String shortAdd(@ModelAttribute NewUrl newUrl, Model model) {
        if (validator.isValid(newUrl.getUrl())) {
            List<TableEntity> result = tableRepository.findByUrl(newUrl.getUrl());
            if (result.isEmpty()) {
                TableEntity url = new TableEntity(newUrl.getUrl());
                tableRepository.save(url);
                String[] truncProtocol = url.getUrl().split("//");
                String[] TakeFirstPart = truncProtocol[1].split("/");
                String shortedUrl = String.valueOf(TakeFirstPart[0].hashCode() + url.getId());
                model.addAttribute("resultUrl", new NewUrl("http://localhost/" + shortedUrl));
            } else {
                String[] truncProtocol = result.get(0).getUrl().split("//");
                String[] TakeFirstPart = truncProtocol[1].split("/");
                String shortedUrl = String.valueOf(TakeFirstPart[0].hashCode() + result.get(0).getId());
                model.addAttribute("resultUrl", new NewUrl("http://localhost/" + shortedUrl));
            }
        } else {
            model.addAttribute("resultUrl", new NewUrl(""));
        }

        model.addAttribute("newUrl", new NewUrl(""));
        model.addAttribute("div", validator.isValid(newUrl.getUrl()));
        model.addAttribute("divResult", true);
        return "short";
    }

    @RequestMapping("/short/res")
    public String TryResult(@RequestParam(value = "id", required = false, defaultValue = "0") long id, Model model) {
        try {
            TableEntity result = tableRepository.findById(id);
            return "redirect:" + result.getUrl();
        } catch (Exception e) { }
        return "result";
    }
}