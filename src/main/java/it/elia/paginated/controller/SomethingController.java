package it.elia.paginated.controller;

import it.elia.paginated.entity.Something;
import it.elia.paginated.service.SomethingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;

@Controller
@Validated
public class SomethingController {

    public static final String PAGE = "page";
    public static final String ERROR = "error";
    @Autowired
    private SomethingService service;


    @ExceptionHandler({IllegalArgumentException.class})
    public String handleIllegalArgumentException(Model model, IllegalArgumentException e) {
        model.addAttribute(ERROR, e.getMessage());
        return PAGE;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public String handleConstraintViolationException(Model model, ConstraintViolationException e) {
        model.addAttribute(ERROR, e.getConstraintViolations().stream().findFirst().map(ConstraintViolation::getMessage));
        return PAGE;
    }

    @GetMapping(path = PAGE)
    public String page(Model model,
                       @RequestParam(name = "size", defaultValue = "5")
                       @Max(value = 100, message = "Page size not must be greater than 100")  int size,
                       @RequestParam(name = PAGE, defaultValue = "0")
                       int page) {
        Page<Something> all = service.findAll(page, size);
        if (page > all.getTotalPages())
            throw new IllegalArgumentException("Page index is over the total pages " + all.getTotalPages());
        model.addAttribute("somethings", all);
        return PAGE;
    }
}
