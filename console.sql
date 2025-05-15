CREATE SCHEMA IF NOT EXISTS SEP_DATABASE;

SET SCHEMA 'sep_database';

CREATE TABLE role (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE employee (
    employee_id SERIAL PRIMARY KEY,
    role_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(10) CHECK (status IN ('active', 'inactive')),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE project (
    project_id SERIAL PRIMARY KEY,
    created_by INTEGER NOT NULL,
    scrum_master INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    status VARCHAR(10) CHECK (status IN ('pending', 'ongoing', 'finished')),
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (created_by) REFERENCES employee(employee_id),
    FOREIGN KEY (scrum_master) REFERENCES employee(employee_id)
);

CREATE TABLE sprint (
    sprint_id SERIAL PRIMARY KEY,
    project_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE task (
    task_id SERIAL PRIMARY KEY,
    sprint_id INTEGER,
    project_id INTEGER NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(50) CHECK (status IN ('to-do', 'doing', 'done', 'done-and-approved')),
    priority INTEGER CHECK (priority >= 1 AND priority <= 5),
    FOREIGN KEY (sprint_id) REFERENCES sprint(sprint_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE task_assignment (
    employee_id INTEGER NOT NULL,
    task_id INTEGER NOT NULL,
    PRIMARY KEY (employee_id, task_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
    FOREIGN KEY (task_id) REFERENCES task(task_id) ON DELETE CASCADE
);

CREATE TABLE project_assignment (
    employee_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL,
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

INSERT INTO role (role_name) VALUES ('admin');
INSERT INTO role (role_name) VALUES ('product_owner');
INSERT INTO role (role_name) VALUES ('scrum_master');
INSERT INTO role (role_name) VALUES ('developer');

-- Insert sample data
INSERT INTO employee(role_id, username, password, status) VALUES
(1, 'admin', 'admin', 'active'),
(2, 'jcob23', '1234', 'active'),
(3, 'tymek12', '23189', 'active'),
(4, 'damianczina', '2137', 'active'),
(2, 'oliwer2004', '2137', 'active'),
(2, 'catlynn', '3', 'active'),
(2, 'matix', 'gigakoks1', 'active');


INSERT INTO project (created_by, name, description, status, start_date, end_date, scrum_master) VALUES
(2, 'Project Apollo', 'A platform to manage lunar operations.', 'ongoing', '2025-01-01', '2025-12-31', 3),
(2, 'Project Hermes', 'A fast delivery system.', 'pending', '2025-06-01', '2025-12-31', 3);

INSERT INTO project_assignment (employee_id, project_id) VALUES
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(2, 2),
(3, 2);

INSERT INTO sprint (project_id, name, start_date, end_date) VALUES
(1, 'Sprint 1 - Apollo', '2025-01-01', '2025-01-15'),
(1, 'Sprint 2 - Apollo', '2025-01-16', '2025-01-31'),
(2, 'Sprint 1 - Hermes', '2025-06-01', '2025-06-15');

INSERT INTO task (sprint_id, project_id, title, description, status, priority) VALUES
(1, 1, 'Setup development environment', 'Install tools and set up workspace.', 'done', 2),
(1, 1, 'Implement login API', 'Create secure login endpoint.', 'doing', 4),
(1, 1, 'Write test cases', 'QA will write functional test cases.', 'to-do', 3),
(2, 1, 'Refactor codebase', 'Improve code readability and structure.', 'to-do', 3),
(3, 2, 'Design initial UI', 'Create Figma mockups for frontend.', 'to-do', 5);

INSERT INTO task_assignment (employee_id, task_id) VALUES
(4, 1),
(5, 2),
(6, 3),
(7, 4),
(3, 5),
(3, 4);

