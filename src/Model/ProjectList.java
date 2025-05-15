package Model;

import java.io.Serializable;
import java.util.*;

public class ProjectList implements List<Project>, Serializable {
    private ArrayList<Project> projects;

    public ProjectList() {
        projects = new ArrayList<Project>();
    }

    public int size() {
        return projects.size();
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<Project> iterator() {
        return projects.iterator();
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T> T[] toArray(T[] a) {
        return null;
    }

    public boolean add(Project project) {
        return projects.add(project);
    }

    public boolean remove(Object o) {
        return projects.remove((Project) o);
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends Project> c) {
        return false;
    }

    public boolean addAll(int index, Collection<? extends Project> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {

    }

    public Project get(int id) {
        for(Project project : projects)
        {
            if(project.getProject_id() == id)
            {
                return project;
            }
        }

        return null;
    }

    public Project set(int id, Project element) {
        int index = 0;
        for(Project project : projects)
        {
            if(project.getProject_id() == id)
            {
                projects.set(index, element);
                return project;
            }
            index++;
        }
        return null;
    }

    public void add(int index, Project element) {

    }

    public Project remove(int id) {
        for(Project project : projects)
        {
            if(project.getProject_id() == id)
            {
                projects.remove(project);
                return project;
            }
        }
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<Project> listIterator() {
        return projects.listIterator();
    }

    public ListIterator<Project> listIterator(int index) {
        return projects.listIterator(index);
    }

    public List<Project> subList(int fromIndex, int toIndex) {
        return List.of();
    }

    public void remove(Project project) {
        projects.remove(project);
    }
}
