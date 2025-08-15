package org.endpointe.employee.exception.repository;

import org.endpointe.employee.exception.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog,Long>{
}
