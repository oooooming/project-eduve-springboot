package tricode.eduve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tricode.eduve.dto.JoinDTO;
import tricode.eduve.service.StudentJoinService;


@Controller
@ResponseBody
public class StudentJoinController {

    private final StudentJoinService studentJoinService;

    public StudentJoinController(StudentJoinService studentJoinService) {

        this.studentJoinService = studentJoinService;
    }

    @PostMapping("/join/student")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        studentJoinService.joinProcess(joinDTO);

        return "ok";
    }
}
