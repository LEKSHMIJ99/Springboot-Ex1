package com.student.ust.service;
import com.student.ust.DTO.StudentDTO;
import com.student.ust.entity.Student;
import com.student.ust.exception.InvalidEmailException;
import com.student.ust.repository.StudentRepository;
import com.student.ust.utils.USTutil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {
    /**
     * The Student service.
     */
    @Autowired

    StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public StudentDTO converttoDTO(Student student) {
        return modelMapper.map(student,StudentDTO.class);
    }

    public Student getStudentByID(int id) {
        return studentRepository.findById(id).orElse(null);

    }

    public void saveStudent(Student student) {
        student.setCreateDate(LocalDateTime.now());
        student.setModifyDate(student.getCreateDate());
        String email = student.getEmail();
        String regex = "^([A-Za-z0-9+_.-]+ ) @([a-z]+)\\.([a-z]+)$";
        String regexPassword =  "^(?=(?:.*\\d){3,})(?=\\S+$)(?=.*[@#$%^&+=])(?=(?:.*[A-Za-z]){3,})(?=.*[A-Z]).{8,}$";
        Pattern pattern1=Pattern.compile(regexPassword);
        Matcher matcher1=pattern1.matcher(student.getPassword());
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() && matcher1.matches() ) {
            try {
                student.setPassword(USTutil.toHexString(USTutil.getSHA(student.getPassword())));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            studentRepository.save(student);
        } else {
            throw new InvalidEmailException();
        }
        studentRepository.save(student);
    }


    public List<Student> getStudentAll() {
        System.out.println((studentRepository.findByName("krithika")));
        return studentRepository.findAll();
    }

    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Student student) {
        Student updateStudent = studentRepository
                .findById(student.getStudentId()).orElseThrow(() -> new NoSuchElementException());
        updateStudent.setName(student.getName());
        updateStudent.setAge(student.getAge());
        updateStudent.setModifyDate(LocalDateTime.now());
        studentRepository.save(updateStudent);
        return updateStudent;
    }
}






