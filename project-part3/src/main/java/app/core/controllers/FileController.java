package app.core.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/images/")
public class FileController {

//	@Autowired
//	private FileStorageService fileStorageService;
//
//	@PostMapping("uploadFile")
//	@ResponseStatus(HttpStatus.CREATED)
//	public String uploadFile(@RequestParam MultipartFile file) {
//		return this.fileStorageService.storeFile(file);
//	}
//
//	@GetMapping("downloadFile/{fileName}")
//	@ResponseStatus(HttpStatus.OK)
//	public Resource downloadFile(@PathVariable String fileName) {
//		Resource resource = this.fileStorageService.loadFileAsResource(fileName);
//		System.out.println(resource);
//		return resource;
//	}

}