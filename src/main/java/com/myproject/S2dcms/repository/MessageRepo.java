package com.myproject.S2dcms.repository;

import com.myproject.S2dcms.model.Message;
import com.myproject.S2dcms.model.Student;
import com.myproject.S2dcms.model.Department;
import com.myproject.S2dcms.model.Message.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageRepo extends JpaRepository<Message, Long> {

    // Student queries (paginated for safety)

    // Fetch all messages for a student, paginated
    Page<Message> findByStudent(Student student, Pageable pageable);

    // Fetch messages by student and status, paginated
    Page<Message> findByStudentAndStatus(Student student, Status status, Pageable pageable);

    // Department queries (paginated)

    // Fetch all messages for a department, paginated
    Page<Message> findByDepartment(Department department, Pageable pageable);

    // Fetch messages by department and status, paginated
    Page<Message> findByDepartmentAndStatus(Department department, Status status, Pageable pageable);
}
