package com.mycompany.app.dao;

import com.mycompany.app.model.TimeLog;

import java.util.List;

/**
 * @author paul.labis.
 */
public interface TimeLogDao {
  List<TimeLog> findUserTimeLog(long userId) throws Exception;
}
