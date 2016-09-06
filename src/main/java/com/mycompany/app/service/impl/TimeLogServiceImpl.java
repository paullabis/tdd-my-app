package com.mycompany.app.service.impl;

import com.google.inject.Inject;

import com.beust.jcommander.internal.Lists;
import com.mycompany.app.dao.TimeLogDao;
import com.mycompany.app.model.TimeLog;
import com.mycompany.app.service.TimeLogService;
import java.util.List;

/**
 * @author paul.labis.
 */
public class TimeLogServiceImpl implements TimeLogService {

  @Inject
  private TimeLogDao timeLogDao;

  public List<TimeLog> findUserTimeLogs(long... ids) {
    final List<TimeLog> timeLogs = Lists.newArrayList();
    for (long id : ids) {
      try {
        timeLogs.addAll(timeLogDao.findUserTimeLog(id));
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
    return timeLogs;
  }
}
