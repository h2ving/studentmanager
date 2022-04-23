package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Grade;
import sda.studentmanagement.studentmanager.services.GradeServiceImplementation;
import sda.studentmanagement.studentmanager.services.SessionService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class GradeController {
    private final GradeServiceImplementation gradeService;

    //WARNING: THIS MAY CRASH LATER IF MORE GRADE DATA IN DB, RETURNS A LIST OF ALL GRADES IN DB
    @GetMapping("/grades/all")
    public ResponseEntity<List<Grade>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getGrades());
    }

    //SAVE NEW GRADE
    @PostMapping("/grade/save")
    public ResponseEntity<Grade> saveNewGrade(@RequestBody Grade grade) {
        return ResponseEntity.ok(gradeService.saveGrade(grade));
    }

    @GetMapping("/grades/{email}")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable("email") String email) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(email));
    }

    @GetMapping("/grade/{id}")
    public ResponseEntity<Grade> getGradeByGradeId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(gradeService.getGrade(id));
    }
}
