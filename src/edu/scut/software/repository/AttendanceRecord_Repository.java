package edu.scut.software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.scut.software.entity.AttendanceRecord;

public interface AttendanceRecord_Repository extends JpaRepository<AttendanceRecord, Integer> {
	AttendanceRecord getByLessonIdAndStudentId(Integer lessonId, Integer studentId);

	@Query(value = "select count(*) from AttendanceRecord a where a.studentId=?1 and a.lessonId in (select l.id from Lesson l where l.courseId=?2)")
	Integer getAttendingTimes(Integer studentId, Integer courseId);
}
