package com.student.ust.controller;

import com.student.ust.entity.Student;
import com.student.ust.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> get(@PathVariable Integer id) {
            try {

                Student student = studentService.getStudentByID(id);
                return new ResponseEntity<Student>(student, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
            }
    }
    @GetMapping("/students")
    public ResponseEntity<List<Student>> get2() {
        try {

            List<Student> studentList=studentService.getStudentAll();
            return new ResponseEntity<List<Student>>(studentList, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Student>>(HttpStatus.NOT_FOUND);
        }
    }




    @PostMapping("/Students")
    public void add
            (@RequestBody Student student){
             studentService.saveStudent(student);
    }

    @DeleteMapping("/Students/{id}")
    public void delete(@PathVariable Integer id){
        studentService.deleteStudent(id);
    }

@PutMapping("/Students")
public ResponseEntity<Student>update(@RequestBody Student student) {
    try {
        Student updatedStudent = studentService.updateStudent(student);
        return new ResponseEntity<Student>(updatedStudent, HttpStatus.OK);
    } catch (NoSuchElementException e) {
        return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);

    }
}
        }

