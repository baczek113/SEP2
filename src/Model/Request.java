package Model;

import java.io.Serializable;

public class Request implements Serializable {

  private final String entity; // "project", "sprint", "task"
  private final String action; // "add", "edit", "delete"
  private final Object data; //specific object we want to "add" or "edit"
  private final String parentId; // to track sprints and tasks

  public Request(String entity, String action, Object data, String parentId) {
    this.entity = entity;
    this.action = action;
    this.data = data; // null for "delete"
    this.parentId = parentId; // null for projects
  }

  public String getEntity() { return entity; }
  public String getAction() { return action; }
  public Object getData() { return data; }
  public String getParentId() { return parentId; }
}
