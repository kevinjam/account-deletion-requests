package com.codecraftwithkevin.account_deletion_requests;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootApplication
public class AccountDeletionRequestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountDeletionRequestsApplication.class, args);
	}

}

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
class DeletionRequestController{

	private final DeletionRequestRepository deletionRequestRepository;

	@GetMapping("/delete-request")
	public String showForm(@RequestParam(name = "success", required = false) String success, Model model) {
		model.addAttribute("success", success);
		return "delete-request"; // Thymeleaf template name
	}

	@PostMapping("/delete-request")
	public String handleDeletionRequest(
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String reason) {
		DeletionRequest request = new DeletionRequest(UUID.randomUUID().toString(),name, email, reason);
		deletionRequestRepository.save(request);
		return "redirect:/api/delete-request?success=true";
	}

	@GetMapping("/view-requests")
	public String viewAllDeletionRequests(Model model){
		List<DeletionRequest> deletionRequests = deletionRequestRepository.findAll();
		model.addAttribute("deletionRequests", deletionRequests);
		return "view-requests";
	}

//	@GetMapping("/delete-requests")
//	@ResponseBody
//	public List<DeletionRequest> getAllDeletionRequests() {
//		return deletionRequestRepository.findAll();
//	}
}

@RequiredArgsConstructor
@Repository
class DeletionRequestRepository{

	private static final String KEY = "DeletionRequest";

	private final RedisTemplate<String, Object> redisTemplate;

	public void save(DeletionRequest request) {
		String id = UUID.randomUUID().toString();
		redisTemplate.opsForHash().put(KEY, id, request);
	}

	public DeletionRequest findById(String id) {
		return (DeletionRequest) redisTemplate.opsForHash().get(KEY, id);
	}

	public List<DeletionRequest> findAll(){
		return redisTemplate.opsForHash().values(KEY)
				.stream()
				.map(object->(DeletionRequest) object)
				.collect(Collectors.toList());
	}


}