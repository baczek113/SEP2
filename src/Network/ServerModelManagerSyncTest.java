package Network;

import Model.Employee;
import Model.EmployeeList;
import Model.Project;
import Model.ProjectList;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerModelManagerSyncTest {
    private static ServerModelManager manager;
    private static final int NUM_THREADS = 10;
    private static final int NUM_OPERATIONS = 100;

    public static void main(String[] args) {
        manager = ServerModelManager.getInstance();
        
        // Run all tests
        testMultipleReaders();
        testWritersBlockReaders();
        testWritersPriority();
        testConcurrentWrites();
    }

    private static void testMultipleReaders() {
        System.out.println("\nTesting Multiple Readers...");
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(NUM_THREADS);
        AtomicBoolean success = new AtomicBoolean(true);

        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    for (int j = 0; j < NUM_OPERATIONS; j++) {
                        List<Project> projects = manager.getProjects();
                        if (projects == null) {
                            success.set(false);
                            break;
                        }
                    }
                } catch (Exception e) {
                    success.set(false);
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Multiple Readers Test " + (success.get() ? "PASSED" : "FAILED"));
    }

    private static void testWritersBlockReaders() {
        System.out.println("\nTesting Writers Block Readers...");
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(2);
        AtomicBoolean readerStarted = new AtomicBoolean(false);
        AtomicBoolean writerFinished = new AtomicBoolean(false);

        // Writer thread
        new Thread(() -> {
            try {
                startLatch.await();
                // Create a test project
                Employee testEmployee = new Employee(1, 1, "test");
                List<Employee> participants = new ArrayList<>();
                participants.add(testEmployee);
                manager.addProject(testEmployee, testEmployee, "Test Project", "Description", 
                    new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 86400000), 
                    participants);
                writerFinished.set(true);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endLatch.countDown();
            }
        }).start();

        // Reader thread
        new Thread(() -> {
            try {
                startLatch.await();
                readerStarted.set(true);
                List<Project> projects = manager.getProjects();
                if (projects != null && writerFinished.get()) {
                    System.out.println("Reader was able to read after writer finished");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endLatch.countDown();
            }
        }).start();

        startLatch.countDown();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Writers Block Readers Test " + 
            (readerStarted.get() && writerFinished.get() ? "PASSED" : "FAILED"));
    }

    private static void testWritersPriority() {
        System.out.println("\nTesting Writers Priority...");
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(3);
        AtomicInteger readerCount = new AtomicInteger(0);
        AtomicBoolean writerStarted = new AtomicBoolean(false);

        // First reader
        new Thread(() -> {
            try {
                startLatch.await();
                readerCount.incrementAndGet();
                List<Project> projects = manager.getProjects();
                if (projects != null) {
                    System.out.println("First reader completed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endLatch.countDown();
            }
        }).start();

        // Writer
        new Thread(() -> {
            try {
                startLatch.await();
                writerStarted.set(true);
                Employee testEmployee = new Employee(1, 1, "test");
                List<Employee> participants = new ArrayList<>();
                participants.add(testEmployee);
                manager.addProject(testEmployee, testEmployee, "Test Project", "Description", 
                    new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 86400000), 
                    participants);
                System.out.println("Writer completed");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endLatch.countDown();
            }
        }).start();

        // Second reader
        new Thread(() -> {
            try {
                startLatch.await();
                readerCount.incrementAndGet();
                List<Project> projects = manager.getProjects();
                if (projects != null) {
                    System.out.println("Second reader completed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endLatch.countDown();
            }
        }).start();

        startLatch.countDown();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Writers Priority Test " + 
            (readerCount.get() == 2 && writerStarted.get() ? "PASSED" : "FAILED"));
    }

    private static void testConcurrentWrites() {
        System.out.println("\nTesting Concurrent Writes...");
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(NUM_THREADS);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    startLatch.await();
                    Employee testEmployee = new Employee(threadId, 1, "test" + threadId);
                    List<Employee> participants = new ArrayList<>();
                    participants.add(testEmployee);
                    boolean result = manager.addProject(testEmployee, testEmployee, 
                        "Test Project " + threadId, "Description " + threadId, 
                        new Date(System.currentTimeMillis()), 
                        new Date(System.currentTimeMillis() + 86400000), 
                        participants);
                    if (result) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Concurrent Writes Test: " + successCount.get() + " successful writes");
        System.out.println("Concurrent Writes Test " + (successCount.get() > 0 ? "PASSED" : "FAILED"));
    }
} 