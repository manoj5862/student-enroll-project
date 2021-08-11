package com.xworkz.StudentEnrollApp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.xworkz.StudentEnrollApp.dto.StudentEnrollDTO;
import com.xworkz.StudentEnrollApp.entity.StudentEnrollEntity;
import com.xworkz.StudentEnrollApp.repository.StudentEnrollRepository;
import com.xworkz.StudentEnrollApp.util.MailTrigger;
import com.xworkz.StudentEnrollApp.util.PasswordEncryptor;
import com.xworkz.StudentEnrollApp.util.RandomPasswordGenerator;

@Service
public class StudentEnrollServiceImpl implements StudentEnrollService {

	@Autowired
	private StudentEnrollRepository enrollRepository;

	@Autowired
	private MailTrigger mailTrigger;

	public StudentEnrollServiceImpl() {
		System.out.println("Created " + this.getClass().getSimpleName());
	}

	@Override
	public boolean saveStudentDetails(StudentEnrollDTO studentEnrollDTO) {
		System.out.println("Invoked SaveDetailsOf Service method");
		StudentEnrollEntity enrollEntity = new StudentEnrollEntity();
		String randomPassword = RandomPasswordGenerator.generateRandomPassword(6);
		String encryptedPassword = PasswordEncryptor.passwordEncrypt(randomPassword);
		enrollEntity.setPassword(encryptedPassword);
		BeanUtils.copyProperties(studentEnrollDTO, enrollEntity);
		boolean outcome = this.enrollRepository.SaveToDb(enrollEntity);
		if (outcome) {
			System.out.println("saved the details send a mail");
			this.mailTrigger.mailTrigger(studentEnrollDTO.getEmail(), "Student Enrollment", "Student Successsfully Registered");				
			return true;
		} else {
			System.out.println("Details not saved dont send the mail");
			return false;
		}

	}

}
