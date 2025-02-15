package tricode.eduve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tricode.eduve.dto.JoinDTO;
import tricode.eduve.service.TeacherJoinService;

@Controller
@ResponseBody
public class TeacherJoinController {

    private final TeacherJoinService teacherJoinService;

    public TeacherJoinController(TeacherJoinService teacherJoinService) {

        this.teacherJoinService = teacherJoinService;
    }

    @PostMapping("/join/teacher")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        teacherJoinService.joinProcess(joinDTO);

        return "ok";
    }
}