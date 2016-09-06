package com.mycompany.app.service;

import com.mycompany.app.model.TimeLog;

import java.util.List;

/**
 * @author paul.labis.
 */
public interface TimeLogService {
  List<TimeLog> findUserTimeLogs(long... ids);
}
