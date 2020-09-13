package firstweb_todo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService todoService;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, false));
	}
	
	@RequestMapping(value = "/listTodo", method=RequestMethod.GET)
	//@ResponseBody
	public String showLoginPage(ModelMap model) {
		model.get("name");
		model.addAttribute("todos", todoService.retrieveTodos(retrievedLoggedinUser()));
		return "listTodo";
	}
	
	private String retrievedLoggedinUser() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}
	
	
	@RequestMapping(value = "/add-todo", method=RequestMethod.GET)
	//@ResponseBody
	public String showTodoPage(ModelMap model) {
//		model.get("name");
//		model.addAttribute("todos", todo.retrieveTodos("in28Minutes"));
		model.addAttribute("todo", new Todo(0,retrievedLoggedinUser(),"",new Date(), false));
		return "todo";
	}
	
	@RequestMapping(value = "/add-todo", method=RequestMethod.POST)
	//@ResponseBody
	public String addTodo(ModelMap model,@Valid Todo todo, BindingResult
			bindingResult) {
		if(bindingResult.hasErrors()) {
			return "todo";
		}
		todoService.addTodo(retrievedLoggedinUser(), todo.getDescription(), new Date(), false);
		model.clear();
		return "redirect:/listTodo";
	}
	
	@RequestMapping(value = "/delete-todo", method=RequestMethod.GET)
	//@ResponseBody
	public String deleteTodo(ModelMap model, @RequestParam int id) {
		
		todoService.deleteTodo(id);
		model.clear();
		return "redirect:/listTodo";
	}

	
	@RequestMapping(value = "/update-todo", method=RequestMethod.GET)
	public String updateTodo(ModelMap model, @RequestParam int id) {
		
		Todo todo = todoService.retrieveTodo(id);
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value = "/update-todo", method=RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors())
			return "todo";
		todoService.updateTodo(todo);
		return "redirect:/listTodo";
	}
	
}
