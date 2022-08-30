package com.matt.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.matt.modal.Employee;
import com.matt.service.EmployeeServiceImpl;
import com.matt.service.EmployeeServices;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeServices service;

	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@GetMapping("/")
	public String viewHomePage1(Model model, String keyword) {
		
		model.addAttribute("employees", employeeServiceImpl.getAllEmployee());
		return "index";
		
	}
		
	

	@GetMapping("/employees")
	public String ListEmployee(Model model, String keyword) {
		
		if (keyword != null) {
			model.addAttribute("employees", employeeServiceImpl.findByKeyword(keyword));
			
		}else {
		model.addAttribute("employees", employeeServiceImpl.getAllEmployee());
		}
		//model.addAttribute("keyword", keyword);
		return "index";
	}

	@GetMapping("/addnew")
	public String addNewEmployee(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "newemployee";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee , BindingResult result, @RequestParam("file") MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		employee.setProfilePicture(fileName);
		employee.setContent(file.getBytes());
		employee.setSize(file.getSize());
		 		
		service.save(employee);
		employeeServiceImpl.save(employee);
		return "redirect:/";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String updateForm(@PathVariable(value = "id") long id, Model model) {
		Employee employee = employeeServiceImpl.getById(id);
		model.addAttribute("employee", employee);
		return "update";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteThroughId(@PathVariable(value = "id") long id) {
		employeeServiceImpl.deleteViaId(id);
		return "redirect:/";

	}

	@PostMapping("/upload")
	public String fileUpload(@RequestParam("file") MultipartFile file, Model model, HttpServletResponse response)
			throws IOException {
		Employee employee = new Employee();
		String fileName = file.getOriginalFilename();
		employee.setProfilePicture(fileName);
		employee.setContent(file.getBytes());
		employee.setSize(file.getSize());
		service.save(employee);
		model.addAttribute("success", "File Uploaded Successfully!!!");
		response.setHeader("Refresh", "1; url = /");
		return "redirect:/addnew";
	}

	@GetMapping("/image")
	public void showImage(@Param("id") Long id, HttpServletResponse response, Optional<Employee> employee)
			throws ServletException, IOException {

		employee = Optional.of(employeeServiceImpl.getById(id));
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
		response.getOutputStream().write(employee.get().getContent());
		response.getOutputStream().close();
	}

}
