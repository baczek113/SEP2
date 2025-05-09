package Model;

import java.io.Serializable;

public class Request implements Serializable {

  private final String entity; // "project", "sprint", "task"
  private final String action; // "create", "update", "delete", "retrieve", "list"
  private final Object data;
  private final String parentId;

  public Request(String entity, String action, Object data, String parentId) {
    this.entity = entity;
    this.action = action;
    this.data = data; //can be null for actions like "list" or "delete"
    this.parentId = parentId; // also can be null
  }

  public String getEntity() { return entity; }
  public String getAction() { return action; }
  public Object getData() { return data; }
  public String getParentId() { return parentId; }
}
