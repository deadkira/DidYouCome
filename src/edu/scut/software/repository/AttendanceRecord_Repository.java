package edu.scut.software.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.scut.software.entity.AttendanceRecord;

public interface AttendanceRecord_Repository extends JpaRepository<AttendanceRecord, Integer> {

	List<AttendanceRecord> getByCourseIdAndAttendingTimeGreaterThanEqualAndAndAttendingTimeLessThanEqual(
			Integer courseId, Date attendingTime, Date attendingTime2);

	@Query(value = "select * from AttendanceRecord a where a.courseId=?1 and a.studentId=?2 and a.attendingTime>?3 and a.attendingTime<?4")
	AttendanceRecord getRecordsBy(Integer courseId, Integer studentId, Date startOfDay, Date endOfDay);
}
