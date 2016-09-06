package com.mycompany.app.model;

/**
 * @author paul.labis.
 */
public class TimeLog {

  private String comment;

  public TimeLog(String comment) {
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
