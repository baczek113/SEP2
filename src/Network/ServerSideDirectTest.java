package Network;

import ClientModel.Requests.*;
import Model.*;
import Network.Response.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ServerSideDirectTest {

    private static ServerModelManager serverModelManager;
    private static Employee createdTestDeveloperByAdmin;
    private static Project createdProjectByPO;
    private static Task createdTaskByPO;
    private static Sprint createdSprintBySM;

    public static void main(String[] args) throws Exception {
        System.out.println("--- Starting Server-Side Direct Test --- " + new java.util.Date());
        serverModelManager = ServerModelManager.getInstance();

        testAdminServerOperations();
        Thread.sleep(500);

        testProductOwnerServerOperations();
        Thread.sleep(500);

        testScrumMasterServerOperations();
        Thread.sleep(500);

        testDeveloperServerOperations();
        Thread.sleep(2000);

        testUserProjectListRetrieval();

        System.out.println("\n--- Server-Side Direct Test Completed --- " + new java.util.Date());
    }

    private static Employee performLoginAs(String username, String password) {
        System.out.println("\nAttempting server-side login for: " + username);
        LoginRequest loginReq = new LoginRequest("login", null, username, password);
        Response response = serverModelManager.processLogin(loginReq);

        if (response instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) response;
            if ("loginSuccess".equals(loginResponse.getMessage()) && loginResponse.getEmployee() != null) {
                System.out.println("Login successful for: " + loginResponse.getEmployee().getUsername() +
                        " (Role: " + loginResponse.getEmployee().getRole().getRole_name() + ")");
                return loginResponse.getEmployee();
            } else {
                System.err.println("Login failed for " + username + ". Reason: " + loginResponse.getMessage());
            }
        } else {
            System.err.println("Unexpected response type during login for " + username + ". Got: " + (response != null ? response.getClass().getName() : "null"));
        }
        return null;
    }

    private static void testAdminServerOperations() throws SQLException {
        System.out.println("\n--- Testing Admin Operations (Server-Side) ---");
        Employee admin = performLoginAs("admin", "admin");
        if (admin == null) {
            System.err.println("Admin login failed. Aborting admin tests.");
            return;
        }

        String newDevUsername = "serverSideDev" + System.currentTimeMillis();
        System.out.println("Admin: Attempting to create new developer '" + newDevUsername + "'.");
        CreateEmployeeRequest createEmpReq = new CreateEmployeeRequest(admin, newDevUsername, "devPass123", 4);
        serverModelManager.processRequest(createEmpReq);

        createdTestDeveloperByAdmin = serverModelManager.getEmployees().stream()
                .filter(e -> newDevUsername.equals(e.getUsername()))
                .findFirst().orElse(null);

        if (createdTestDeveloperByAdmin != null) {
            System.out.println("Admin: Successfully created developer: " + createdTestDeveloperByAdmin.getUsername() +
                    " (ID: " + createdTestDeveloperByAdmin.getEmployee_id() + ")");
        } else {
            System.err.println("Admin: Failed to create or find new developer '" + newDevUsername + "' in ServerModelManager's list.");
        }

        if (createdTestDeveloperByAdmin != null) {
            System.out.println("Admin: Attempting to change role for " + createdTestDeveloperByAdmin.getUsername() + " to Scrum Master (Role 3).");
            Employee devWithNewRole = new Employee(
                    createdTestDeveloperByAdmin.getEmployee_id(),
                    3,
                    createdTestDeveloperByAdmin.getUsername()
            );
            EmployeeRequest updateRoleReq = new EmployeeRequest("updateEmployee", admin, devWithNewRole);
            serverModelManager.processRequest(updateRoleReq);

            Employee updatedDev = serverModelManager.getEmployees().stream()
                    .filter(e -> e.getEmployee_id() == createdTestDeveloperByAdmin.getEmployee_id())
                    .findFirst().orElse(null);
            if (updatedDev != null && updatedDev.getRole().getRole_id() == 3) {
                System.out.println("Admin: Successfully updated " + updatedDev.getUsername() + "'s role to " + updatedDev.getRole().getRole_name());
                createdTestDeveloperByAdmin = updatedDev;
            } else {
                System.err.println("Admin: Failed to update role for " + createdTestDeveloperByAdmin.getUsername() +
                        " or role not changed to Scrum Master. Current role ID: " + (updatedDev != null ? updatedDev.getRole().getRole_id() : "not found"));
            }
        }

        if (createdTestDeveloperByAdmin != null) {
            System.out.println("Admin: Attempting to deactivate user: " + createdTestDeveloperByAdmin.getUsername());
            EmployeeRequest deactivateReq = new EmployeeRequest("deactivateEmployee", admin, createdTestDeveloperByAdmin);
            serverModelManager.processRequest(deactivateReq);

            Employee deactivatedDev = serverModelManager.getEmployees().stream()
                    .filter(e -> e.getEmployee_id() == createdTestDeveloperByAdmin.getEmployee_id())
                    .findFirst().orElse(null);
            if (deactivatedDev != null && "inactive".equals(deactivatedDev.getStatus())) {
                System.out.println("Admin: Successfully deactivated " + deactivatedDev.getUsername() + ".");
            } else {
                System.err.println("Admin: Failed to deactivate " + createdTestDeveloperByAdmin.getUsername() +
                        " or status not 'inactive'. Current status: " + (deactivatedDev != null ? deactivatedDev.getStatus() : "not found"));
            }
        }
        System.out.println("Admin operations test calls finished.");
    }

    private static void testProductOwnerServerOperations() throws SQLException {
        System.out.println("\n--- Testing Product Owner Operations (Server-Side) ---");
        Employee productOwner = performLoginAs("jcob23", "1234");
        if (productOwner == null) {
            System.err.println("Product Owner login failed. Aborting PO tests.");
            return;
        }

        String newProjectName = "PSS_Project_" + System.currentTimeMillis();
        Employee scrumMasterForProject = serverModelManager.getEmployees().stream()
                .filter(e -> e.getRole().getRole_id() == 3).findFirst()
                .orElse(new Employee(3, 3, "tymek12"));
        List<Employee> participants = new ArrayList<>();
        if (createdTestDeveloperByAdmin != null && "active".equals(createdTestDeveloperByAdmin.getStatus())) {
            participants.add(createdTestDeveloperByAdmin);
        } else {
            Optional<Employee> anyActiveDev = serverModelManager.getEmployees().stream()
                    .filter(e -> e.getRole().getRole_id() == 4 && "active".equals(e.getStatus()))
                    .findFirst();
            anyActiveDev.ifPresent(participants::add);
        }

        System.out.println("PO (" + productOwner.getUsername() + "): Attempting to create project '" + newProjectName + "'.");
        AddProjectRequest createProjectReq = new AddProjectRequest(
                productOwner,
                productOwner,
                newProjectName,
                "Server-side test project description.",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90)),
                participants
        );
        serverModelManager.processRequest(createProjectReq);

        createdProjectByPO = serverModelManager.getProjects().stream()
                .filter(p -> newProjectName.equals(p.getName()))
                .findFirst().orElse(null);

        if (createdProjectByPO != null) {
            System.out.println("PO: Successfully created project: " + createdProjectByPO.getName() + " (ID: " + createdProjectByPO.getProject_id() + ")");
        } else {
            System.err.println("PO: Failed to create or find project '" + newProjectName + "'.");
            createdProjectByPO = serverModelManager.getProjects().stream().filter(p -> p.getProject_id() == 1).findFirst().orElse(null);
            if(createdProjectByPO != null) System.out.println("PO: Using existing project 'Project Apollo' (ID: 1) for subsequent tests.");
            else {
                System.err.println("PO: Could not find fallback project. Aborting further PO project-dependent tests.");
                return;
            }
        }

        if (createdProjectByPO != null && createdTestDeveloperByAdmin != null) {
            boolean alreadyParticipant = createdProjectByPO.getEmployees().stream().anyMatch(e -> e.getEmployee_id() == createdTestDeveloperByAdmin.getEmployee_id());
            if (!alreadyParticipant) {
                System.out.println("PO: Assigning " + createdTestDeveloperByAdmin.getUsername() + " to project " + createdProjectByPO.getName());
                ProjectEmployeeRequest assignEmpReq = new ProjectEmployeeRequest("addEmployeeToProject", productOwner, createdProjectByPO, createdTestDeveloperByAdmin);
                serverModelManager.processRequest(assignEmpReq);
                Project updatedProject = serverModelManager.getProjects().stream().filter(p -> p.getProject_id() == createdProjectByPO.getProject_id()).findFirst().orElse(null);
                if(updatedProject != null && updatedProject.getEmployees().stream().anyMatch(e -> e.getEmployee_id() == createdTestDeveloperByAdmin.getEmployee_id())){
                    System.out.println("PO: Successfully assigned " + createdTestDeveloperByAdmin.getUsername() + " to " + updatedProject.getName());
                    createdProjectByPO = updatedProject;
                } else {
                    System.err.println("PO: Failed to assign " + createdTestDeveloperByAdmin.getUsername() + " to project.");
                }
            } else {
                System.out.println("PO: " + createdTestDeveloperByAdmin.getUsername() + " is already a participant in " + createdProjectByPO.getName());
            }
        }

        if (createdProjectByPO != null) {
            String taskTitle = "POTask_" + System.currentTimeMillis();
            System.out.println("PO: Adding task '" + taskTitle + "' to backlog of project " + createdProjectByPO.getName());
            AddTaskRequest addTaskReq = new AddTaskRequest(productOwner,createdProjectByPO, null, taskTitle, "Task added by PO directly to backlog.", 3);
            serverModelManager.processRequest(addTaskReq);

            createdTaskByPO = createdProjectByPO.getBacklog().stream()
                    .filter(t -> taskTitle.equals(t.getTitle())).findFirst().orElse(null);
            Project projectAfterTaskAdd = serverModelManager.getProjects().stream().filter(p -> p.getProject_id() == createdProjectByPO.getProject_id()).findFirst().orElse(null);
            if(projectAfterTaskAdd != null) {
                createdProjectByPO = projectAfterTaskAdd;
                createdTaskByPO = createdProjectByPO.getBacklog().stream()
                        .filter(t -> taskTitle.equals(t.getTitle())).findFirst().orElse(null);
            }

            if (createdTaskByPO != null) {
                System.out.println("PO: Successfully added task to backlog: " + createdTaskByPO.getTitle() + " (ID: " + createdTaskByPO.getTask_id() + ")");
            } else {
                System.err.println("PO: Failed to add or find task '" + taskTitle + "' in backlog of " + createdProjectByPO.getName());
            }
        }

        if (createdTaskByPO != null) {
            System.out.println("PO: Assigning priority 5 to task: " + createdTaskByPO.getTitle());
            AssignPriorityRequest priorityReq = new AssignPriorityRequest(productOwner, createdTaskByPO, 5);
            serverModelManager.processRequest(priorityReq);
            if(createdTaskByPO.getPriority() == 5) System.out.println("PO: Priority for task " + createdTaskByPO.getTitle() + " updated to 5 (client-side model).");
        }

        Task taskToApprove = serverModelManager.getProjects().stream()
                .flatMap(p -> p.getBacklog().stream())
                .filter(t -> t.getTask_id() == 1 && "done".equals(t.getStatus()))
                .findFirst().orElse(null);

        if (taskToApprove != null) {
            System.out.println("PO: Attempting to approve task '" + taskToApprove.getTitle() + "' (ID: " + taskToApprove.getTask_id() + ")");
            ChangeTaskStatusRequest approveReq = new ChangeTaskStatusRequest(productOwner, taskToApprove, "done-and-approved");
            serverModelManager.processRequest(approveReq);
            Task approvedTask = serverModelManager.getProjects().stream()
                    .flatMap(p -> p.getBacklog().stream())
                    .filter(t -> t.getTask_id() == taskToApprove.getTask_id()).findFirst().orElse(null);
            if(approvedTask != null && "done-and-approved".equals(approvedTask.getStatus())){
                System.out.println("PO: Task " + approvedTask.getTitle() + " status is now 'done-and-approved'.");
            } else {
                System.err.println("PO: Failed to approve task " + taskToApprove.getTitle() + " or status not updated.");
            }
        } else {
            System.out.println("PO: No task found with ID 1 and status 'done' to approve, or task not found.");
        }

        if (createdProjectByPO != null && createdProjectByPO.getProject_id() != 1 && createdProjectByPO.getProject_id() != 2) {
            System.out.println("PO: Attempting to end project: " + createdProjectByPO.getName());
            ProjectRequest endProjectReq = new ProjectRequest("endProject", productOwner, createdProjectByPO);
            serverModelManager.processRequest(endProjectReq);
            Project endedProject = serverModelManager.getProjects().stream().filter(p -> p.getProject_id() == createdProjectByPO.getProject_id()).findFirst().orElse(null);
            if(endedProject != null && "finished".equals(endedProject.getStatus())){
                System.out.println("PO: Project " + endedProject.getName() + " status is now 'finished'.");
            } else {
                System.err.println("PO: Failed to end project " + createdProjectByPO.getName() + " or status not updated.");
            }
        }
        System.out.println("Product Owner operations test calls finished.");
    }

    private static void testScrumMasterServerOperations() throws SQLException {
        System.out.println("\n--- Testing Scrum Master Operations (Server-Side) ---");
        Employee scrumMaster = performLoginAs("tymek12", "23189");
        if (scrumMaster == null) {
            System.err.println("Scrum Master login failed. Aborting SM tests.");
            return;
        }

        Project projectForSMOps = createdProjectByPO;
        if (projectForSMOps == null || "finished".equals(projectForSMOps.getStatus())) {
            projectForSMOps = serverModelManager.getProjects().stream()
                    .filter(p -> "ongoing".equals(p.getStatus()) || "pending".equals(p.getStatus()))
                    .findFirst().orElse(null);
            if (projectForSMOps == null) {
                System.err.println("SM: No suitable active project found for operations. Aborting SM tests.");
                return;
            }
            System.out.println("SM: Using project '" + projectForSMOps.getName() + "' for operations.");
        }

        String sprintName = "SMSprint_" + System.currentTimeMillis();
        System.out.println("SM (" + scrumMaster.getUsername() + "): Attempting to create sprint '" + sprintName + "' for project " + projectForSMOps.getName());
        AddSprintRequest createSprintReq = new AddSprintRequest(
                scrumMaster,
                projectForSMOps,
                sprintName,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14))
        );
        serverModelManager.processRequest(createSprintReq);

        Project finalProjectForSMOps = projectForSMOps;
        Project projectAfterSprintAdd = serverModelManager.getProjects().stream().filter(p -> p.getProject_id() == finalProjectForSMOps.getProject_id()).findFirst().orElse(null);
        if(projectAfterSprintAdd != null){
            createdSprintBySM = projectAfterSprintAdd.getSprints().stream()
                    .filter(s -> sprintName.equals(s.getName()))
                    .findFirst().orElse(null);
            if (createdSprintBySM != null) {
                System.out.println("SM: Successfully created sprint: " + createdSprintBySM.getName() + " (ID: " + createdSprintBySM.getSprint_id() + ")");
                projectForSMOps = projectAfterSprintAdd;
            } else {
                System.err.println("SM: Failed to create or find sprint '" + sprintName + "' in project " + projectForSMOps.getName());
            }
        }

        Task taskForSprint = null;
        if (taskForSprint == null) {
            Optional<Task> backlogTask = projectForSMOps.getBacklog().stream()
                    .filter(t -> t.getSprint_id() == 0 || t.getSprint() == 0)
                    .findFirst();
            if (backlogTask.isPresent()){
                taskForSprint = backlogTask.get();
                System.out.println("SM: Using task from backlog: " + taskForSprint.getTitle());
            } else {
                taskForSprint = createdTaskByPO;
            }
        } else {
            taskForSprint = createdTaskByPO;
        }

        if (createdSprintBySM != null && taskForSprint != null) {
            System.out.println("SM: Assigning task '" + taskForSprint.getTitle() + "' to sprint '" + createdSprintBySM.getName() + "'.");
            TaskSprintRequest assignTaskToSprintReq = new TaskSprintRequest("addTaskToSprint", scrumMaster, taskForSprint, createdSprintBySM);
            serverModelManager.processRequest(assignTaskToSprintReq);
            Project finalProjectForSMOps1 = projectForSMOps;
            Sprint sprintAfterTaskAssign = serverModelManager.getProjects().stream()
                    .filter(p -> p.getProject_id() == finalProjectForSMOps1.getProject_id()).findFirst().orElse(null)
                    .getSprints().stream().filter(s -> s.getSprint_id() == createdSprintBySM.getSprint_id()).findFirst().orElse(null);

            Task finalTaskForSprint = taskForSprint;
            if(sprintAfterTaskAssign != null && sprintAfterTaskAssign.getTasks().stream().anyMatch(t -> t.getTask_id() == finalTaskForSprint.getTask_id())){
                System.out.println("SM: Successfully assigned task " + taskForSprint.getTitle() + " to sprint " + sprintAfterTaskAssign.getName());
                taskForSprint.setSprint_id(createdSprintBySM.getSprint_id());
            } else {
                System.err.println("SM: Failed to assign task " + taskForSprint.getTitle() + " to sprint " + createdSprintBySM.getName());
            }

        } else {
            System.out.println("SM: Cannot assign task to sprint. Sprint or suitable backlog task not available.");
            if(createdSprintBySM == null) System.out.println("Reason: createdSprintBySM is null");
            if(taskForSprint == null) System.out.println("Reason: taskForSprint is null");
        }
        System.out.println("Scrum Master operations test calls finished.");
    }

    private static void testDeveloperServerOperations() throws SQLException {
        System.out.println("\n--- Testing Developer Operations (Server-Side) ---");
        Employee developer;
        if (createdTestDeveloperByAdmin != null && "active".equals(createdTestDeveloperByAdmin.getStatus())) {
            developer = performLoginAs(createdTestDeveloperByAdmin.getUsername(), "devPass123");
        } else {
            Optional<Employee> activeDamian = serverModelManager.getEmployees().stream()
                    .filter(e -> "damianczina".equals(e.getUsername()) && "active".equals(e.getStatus()))
                    .findFirst();
            if(activeDamian.isPresent()){
                developer = performLoginAs("damianczina", "2137");
            } else {
                System.err.println("Developer 'damianczina' is not active or not found. Activating for test or skipping.");
                developer = null;
            }
        }

        if (developer == null) {
            System.err.println("Developer login failed. Aborting developer tests.");
            return;
        }

        Task devTask = null;
        Project devProject = null;

        List<Project> ongoingProjects = serverModelManager.getProjects().stream()
                .filter(p -> "ongoing".equals(p.getStatus())).toList();

        for (Project project : ongoingProjects) {
            Optional<Task> taskOpt = project.getBacklog().stream()
                    .filter(t -> "to-do".equals(t.getStatus()))
                    .findFirst();
            if (taskOpt.isPresent()) {
                devTask = taskOpt.get();
                devProject = project;
                break;
            }
        }
        if(createdSprintBySM != null && createdTaskByPO != null && createdTaskByPO.getSprint_id() == createdSprintBySM.getSprint_id()){
            devTask = createdTaskByPO;
            Task finalDevTask = devTask;
            devProject = serverModelManager.getProjects().stream().filter(p -> p.getProject_id() == finalDevTask.getProject_id()).findFirst().orElse(null);
            System.out.println("Developer: Found task '" + devTask.getTitle() + "' from SM assignment.");
        }

        if (devTask == null) {
            System.out.println("Developer: No suitable 'to-do' task found for operations. Test might be limited.");
            devTask = serverModelManager.getProjects().stream()
                    .flatMap(p -> p.getBacklog().stream())
                    .filter(t -> t.getTask_id() == 3).findFirst().orElse(null);
            if (devTask != null) {
                Task finalDevTask1 = devTask;
                devProject = serverModelManager.getProjects().stream().filter(p -> p.getProject_id() == finalDevTask1.getProject_id()).findFirst().orElse(null);
            }
        }
        if (devTask == null) {
            System.err.println("Developer: Still no task found. Aborting dev task operations.");
            return;
        }

        System.out.println("Developer (" + developer.getUsername() + "): Attempting to self-assign task '" + devTask.getTitle() + "' (ID: " + devTask.getTask_id() + ")");
        AssignTaskRequest selfAssignReq = new AssignTaskRequest("assignTask", developer, devTask);
        serverModelManager.processRequest(selfAssignReq);
        Task taskAfterSelfAssign = findTaskInModel(devTask.getTask_id());
        if(taskAfterSelfAssign != null && taskAfterSelfAssign.getAssignedTo().stream().anyMatch(e -> e.getEmployee_id() == developer.getEmployee_id())){
            System.out.println("Developer: Successfully self-assigned task '" + taskAfterSelfAssign.getTitle() + "'.");
            devTask = taskAfterSelfAssign;
        } else {
            System.err.println("Developer: Failed to self-assign task '" + devTask.getTitle() + "'.");
        }

        if (devTask != null && "to-do".equals(devTask.getStatus())) {
            System.out.println("Developer: Setting task '" + devTask.getTitle() + "' to 'doing'.");
            ChangeTaskStatusRequest statusToDoingReq = new ChangeTaskStatusRequest(developer, devTask, "doing");
            serverModelManager.processRequest(statusToDoingReq);
            Task taskAfterDoing = findTaskInModel(devTask.getTask_id());
            if(taskAfterDoing != null && "doing".equals(taskAfterDoing.getStatus())){
                System.out.println("Developer: Task '" + taskAfterDoing.getTitle() + "' status is now 'doing'.");
                devTask = taskAfterDoing;
            } else {
                System.err.println("Developer: Failed to set task '" + devTask.getTitle() + "' to 'doing'.");
            }
        }

        if (devTask != null && "doing".equals(devTask.getStatus())) {
            System.out.println("Developer: Setting task '" + devTask.getTitle() + "' to 'done'.");
            ChangeTaskStatusRequest statusToDoneReq = new ChangeTaskStatusRequest(developer, devTask, "done");
            serverModelManager.processRequest(statusToDoneReq);
            Task taskAfterDone = findTaskInModel(devTask.getTask_id());
            if(taskAfterDone != null && "done".equals(taskAfterDone.getStatus())){
                System.out.println("Developer: Task '" + taskAfterDone.getTitle() + "' status is now 'done'.");
                devTask = taskAfterDone;
            } else {
                System.err.println("Developer: Failed to set task '" + devTask.getTitle() + "' to 'done'.");
            }
        }
        System.out.println("Developer operations test calls finished.");
    }

    private static Task findTaskInModel(int taskId) {
        if (serverModelManager == null) return null;
        for (Project project : serverModelManager.getProjects()) {
            for (Task task : project.getBacklog()) {
                if (task.getTask_id() == taskId) {
                    return task;
                }
            }
        }
        return null;
    }
    private static void testUserProjectListRetrieval() throws SQLException {
        System.out.println("\n--- Testing User Project List Retrieval (Server-Side) ---");

        Employee damian = performLoginAs("damianczina", "2137");
        if (damian != null) {
            List<Project> damianProjects = serverModelManager.getProjects(damian.getEmployee_id());
            System.out.println("Projects retrieved for user: " + damian.getUsername() + " (ID: " + damian.getEmployee_id() + ")");
            if (damianProjects != null) {
                System.out.println("Number of projects found: " + damianProjects.size());
                for (Project p : damianProjects) {
                    System.out.println("  -> Project: " + p.getName() + " (ID: " + p.getProject_id() + ")");
                }
            } else {
                System.err.println("VERIFICATION FAILED for " + damian.getUsername() + ": Project list is null.");
            }
        } else {
            System.err.println("Could not test project list retrieval for damianczina due to login failure.");
        }
        System.out.println("---");

        Employee jcob = performLoginAs("jcob23", "1234");
        if (jcob != null) {
            List<Project> jcobProjects = serverModelManager.getProjects(jcob.getEmployee_id());
            System.out.println("Projects retrieved for user: " + jcob.getUsername() + " (ID: " + jcob.getEmployee_id() + ")");
            if (jcobProjects != null) {
                System.out.println("Number of projects found: " + jcobProjects.size());
                boolean foundApollo = false;
                boolean foundHermes = false;
                int otherProjectCount = 0;
                for (Project p : jcobProjects) {
                    System.out.println("  -> Project: " + p.getName() + " (ID: " + p.getProject_id() + ")");
                    if (p.getProject_id() == 1) {
                        foundApollo = true;
                    } else if (p.getProject_id() == 2) {
                        foundHermes = true;
                    } else {
                        otherProjectCount++;
                    }
                }
                if (foundApollo && foundHermes && otherProjectCount == 0 && jcobProjects.size() == 2) {
                    System.out.println("VERIFICATION PASSED for " + jcob.getUsername() + ": Correctly sees Project Apollo and Project Hermes.");
                } else {
                    System.err.println("VERIFICATION FAILED for " + jcob.getUsername() +
                            ": Expected 2 projects (Apollo & Hermes). Actual count: " + jcobProjects.size() +
                            ", Found Apollo: " + foundApollo + ", Found Hermes: " + foundHermes +
                            ", Other unexpected projects: " + otherProjectCount);
                }
            } else {
                System.err.println("VERIFICATION FAILED for " + jcob.getUsername() + ": Project list is null.");
            }
        } else {
            System.err.println("Could not test project list retrieval for jcob23 due to login failure.");
        }
        System.out.println("---");

        Employee admin = performLoginAs("admin", "admin");
        if (admin != null) {
            List<Project> adminProjects = serverModelManager.getProjects(admin.getEmployee_id());
            System.out.println("Projects retrieved for user: " + admin.getUsername() + " (ID: " + admin.getEmployee_id() + ")");
            if (adminProjects != null) {
                System.out.println("Number of projects found: " + adminProjects.size());
                if (adminProjects.size() == 0) {
                    System.out.println("VERIFICATION PASSED for " + admin.getUsername() +
                            ": Correctly sees 0 directly assigned projects (as per current getProjects(id) logic).");
                } else {
                    System.err.println("VERIFICATION FAILED for " + admin.getUsername() +
                            ": Expected 0 directly assigned projects. Actual count: " + adminProjects.size());
                    for (Project p : adminProjects) {
                        System.out.println("  -> Unexpectedly sees project: " + p.getName() + " (ID: " + p.getProject_id() + ")");
                    }
                }
            } else {
                System.err.println("VERIFICATION FAILED for " + admin.getUsername() + ": Project list is null.");
            }
        } else {
            System.err.println("Could not test project list retrieval for admin due to login failure.");
        }

        System.out.println("\n--- User Project List Retrieval Test Finished ---");
    }
}