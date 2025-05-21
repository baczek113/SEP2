package DataModel;

import java.io.Serializable;
import java.util.*;

public class EmployeeList implements List<Employee>, Serializable {
    private ArrayList<Employee> employees;

    public EmployeeList() {
        employees = new ArrayList<Employee>();
    }

    public int size() {
        return employees.size();
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<Employee> iterator() {
        return employees.iterator();
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T> T[] toArray(T[] a) {
        return null;
    }

    public boolean add(Employee employee) {
        return employees.add(employee);
    }

    public boolean remove(Object o) {
        return employees.remove((Employee) o);
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends Employee> c) {
        return false;
    }

    public boolean addAll(int index, Collection<? extends Employee> c) {
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

    public Employee get(int id) {
        for(Employee employee : employees)
        {
            if(employee.getEmployee_id() == id)
            {
                return employee;
            }
        }

        return null;
    }

    public Employee set(int index, Employee element) {
        return null;
    }

    public void add(int index, Employee element) {

    }

    public Employee remove(int id) {
        for(Employee employee : employees)
        {
            if(employee.getEmployee_id() == id)
            {
                employees.remove(employee);
                return employee;
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

    public ListIterator<Employee> listIterator() {
        return employees.listIterator();
    }

    public ListIterator<Employee> listIterator(int index) {
        return employees.listIterator(index);
    }

    public List<Employee> subList(int fromIndex, int toIndex) {
        return List.of();
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }
}
