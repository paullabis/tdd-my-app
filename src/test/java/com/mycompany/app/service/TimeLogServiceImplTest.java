package com.mycompany.app.service;

import com.google.common.collect.Lists;

import com.mycompany.app.dao.TimeLogDao;
import com.mycompany.app.model.TimeLog;
import com.mycompany.app.service.impl.TimeLogServiceImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author paul.labis.
 */
public class TimeLogServiceImplTest {

  @Mock private TimeLogDao timeLogDao;

  @InjectMocks private TimeLogService timeLogService;

  @BeforeMethod
  public void setupBeforeMethod() {
    timeLogService = new TimeLogServiceImpl();

    MockitoAnnotations.initMocks(this);
  }

  @Test
  public final void testFindUserTimeLog_ShouldHandleAnyException()
      throws Exception {

    given(timeLogDao.findUserTimeLog(anyLong()))
        .willThrow(new Exception("Test"));
    final long user1 = 1L;
    final long user2 = 2L;

    final List<TimeLog> actualTimeLogs = timeLogService
        .findUserTimeLogs(user1, user2);

    verify(timeLogDao, times(2)).findUserTimeLog(anyLong());
    assertNotNull(actualTimeLogs);
    assertTrue(actualTimeLogs.isEmpty());

  }

  @Test
  public final void testFindUserTimeLog_DaoFoundEmptyLogForEachUser()
      throws Exception {

    given(timeLogDao.findUserTimeLog(anyLong()))
        .willReturn(Collections.<TimeLog>emptyList());
    final long user1 = 1L;
    final long user2 = 2L;

    final List<TimeLog> actualTimeLogs = timeLogService
        .findUserTimeLogs(user1, user2);

    verify(timeLogDao, times(2)).findUserTimeLog(anyLong());
    assertNotNull(actualTimeLogs);
    assertTrue(actualTimeLogs.isEmpty());

  }

  @Test
  public final void testFindUserTimeLog_OnlyUser2HasTimeLog()
      throws Exception {
    final long user1 = 1L;
    final long user2 = 2L;

    given(timeLogDao.findUserTimeLog(eq(user1)))
        .willReturn(Collections.<TimeLog>emptyList());
    given(timeLogDao.findUserTimeLog(eq(user2)))
        .willReturn(Lists.newArrayList(new TimeLog("Hello")));

    final List<TimeLog> actualTimeLogs = timeLogService
        .findUserTimeLogs(user1, user2);

    verify(timeLogDao, times(2)).findUserTimeLog(anyLong());
    assertNotNull(actualTimeLogs);
    assertFalse(actualTimeLogs.isEmpty());
    assertEquals(actualTimeLogs.size(), 1);
    assertEquals(actualTimeLogs.get(0).getComment(), "Hello");

  }

  @Test
  public final void testFindUserTimeLog_OnlyUser1HasTimeLogExceptionOnUser2()
      throws Exception {
    final long user1 = 1L;
    final long user2 = 2L;

    given(timeLogDao.findUserTimeLog(eq(user1)))
        .willReturn(Lists.newArrayList(new TimeLog("Hello")));
    given(timeLogDao.findUserTimeLog(eq(user2)))
        .willThrow(new Exception("Error"));

    final List<TimeLog> actualTimeLogs = timeLogService
        .findUserTimeLogs(user1, user2);

    verify(timeLogDao, times(2)).findUserTimeLog(anyLong());
    assertNotNull(actualTimeLogs);
    assertFalse(actualTimeLogs.isEmpty());
    assertEquals(actualTimeLogs.size(), 1);
    assertEquals(actualTimeLogs.get(0).getComment(), "Hello");

  }

  @Test
  public final void testFindUserTimeLog_ContinueFindingTimeLogForOtherUsersEvenException()
      throws Exception {
    final long user1 = 1L;
    final long user2 = 2L;

    given(timeLogDao.findUserTimeLog(eq(user1)))
        .willThrow(new Exception("Error"));
    given(timeLogDao.findUserTimeLog(eq(user2)))
        .willReturn(Lists.newArrayList(new TimeLog("Hello")));

    final List<TimeLog> actualTimeLogs = timeLogService
        .findUserTimeLogs(user1, user2);

    verify(timeLogDao, times(2)).findUserTimeLog(anyLong());
    assertNotNull(actualTimeLogs);
    assertFalse(actualTimeLogs.isEmpty());
    assertEquals(actualTimeLogs.size(), 1);
    assertEquals(actualTimeLogs.get(0).getComment(), "Hello");
  }

}
