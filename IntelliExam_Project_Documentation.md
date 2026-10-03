# IntelliExam --- Complete Project Documentation

## 1. Project Overview

**IntelliExam** is an intelligent timetable-generation system designed
to automate the creation of academic timetables using a **Genetic
Algorithm (GA)**.

The project began with a focus on examination timetables but is
intentionally being generalized so the same scheduling engine can
support broader academic timetable use cases such as lectures,
practicals, labs, and examinations.

The core problem is that timetable generation becomes difficult when
many constraints must be satisfied simultaneously:

-   Multiple departments
-   Multiple semesters/divisions
-   Subjects with different types
-   Faculty availability
-   Room availability
-   Time-slot restrictions
-   Elective subjects
-   Practical/theoretical subjects
-   Faculty workload
-   Student conflicts
-   Institutional scheduling rules

The system will use a **Genetic Algorithm-based optimization process**
to generate timetables that satisfy hard constraints and optimize soft
constraints.

------------------------------------------------------------------------

# 2. Main Objective

The primary objective is:

> **To automatically generate an optimized timetable while satisfying
> institutional constraints and minimizing scheduling conflicts.**

The system should:

1.  Store academic structure.
2.  Store departments, users, faculty, students and subjects.
3.  Define timetable-generation requirements.
4.  Identify entities participating in a timetable.
5.  Generate possible timetable solutions.
6.  Evaluate each solution using a fitness function.
7.  Improve solutions using Genetic Algorithm operations.
8.  Produce an optimized timetable.
9.  Allow authorized users to view and manage the generated timetable.

------------------------------------------------------------------------

# 3. Project Scope

The system should support two broad categories.

### Academic Timetable

Examples:

-   Lecture schedules
-   Practical schedules
-   Lab schedules
-   Faculty schedules

### Examination Timetable

Examples:

-   Semester examinations
-   Internal examinations
-   Practical examinations

The scheduling engine should therefore be as generalized as practical
instead of being tightly coupled to examination-specific terminology.

------------------------------------------------------------------------

# 4. Technology Stack

## Backend

-   Python
-   Django
-   Django REST Framework
-   PostgreSQL

## Frontend

-   React
-   Vite
-   JavaScript
-   CSS/Tailwind where appropriate

## Optimization

-   Genetic Algorithm

## Architecture

REST API architecture:

``` text
React Frontend
      |
      | REST API
      v
Django REST Framework
      |
      +---- PostgreSQL
      |
      +---- Scheduling Services
                |
                +---- Constraint Engine
                |
                +---- Genetic Algorithm
```

------------------------------------------------------------------------

# 5. User Roles

The application uses role-based access control.

Main roles:

-   Admin
-   Coordinator
-   Faculty
-   Student

## Admin

Responsible for master data and system configuration.

Typical responsibilities:

-   Manage roles
-   Manage departments
-   Manage users
-   Manage faculty
-   Manage students
-   Manage subjects
-   Manage scheduling configuration
-   View system logs
-   Activate/deactivate records

## Coordinator

Responsible for timetable generation.

Typical responsibilities:

-   Create generation requests
-   Select scheduling scope
-   Configure date/time requirements
-   Start timetable generation
-   Review generated timetable
-   Regenerate when necessary
-   Publish/finalize timetable

## Faculty

May:

-   View timetable
-   View assigned subjects/sessions
-   Provide availability where supported

## Student

May:

-   View timetable
-   Filter timetable
-   View subject schedules
-   View examination schedules

------------------------------------------------------------------------

# 6. Database Design Philosophy

The database follows these principles:

### Separate master data

Examples:

-   Roles
-   Departments
-   Users
-   Faculty
-   Students
-   Subjects

### Avoid storing derived data

For example, **year of study is not stored** in Students.

Instead:

``` text
Semester 1 / 2 -> FE
Semester 3 / 4 -> SE
Semester 5 / 6 -> TE
Semester 7 / 8 -> BE
```

### Avoid unnecessary duplication

The system should not create permanent records for information that can
be derived or represented compactly.

------------------------------------------------------------------------

# 7. Current Core Database Schema

## 7.1 ROLES

``` text
id              PK
name
is_active
created_at
updated_at
```

Example:

``` text
1 | Admin
2 | Coordinator
3 | Faculty
4 | Student
```

------------------------------------------------------------------------

## 7.2 DEPARTMENTS

``` text
id              PK
code
name
is_active
created_at
updated_at
```

Example:

``` text
1 | COMP | Computer Engineering
2 | IT   | Information Technology
3 | AIDS | Artificial Intelligence & Data Science
```

------------------------------------------------------------------------

## 7.3 USERS

``` text
id              PK
first_name
middle_name
last_name
email
password_hash
role             FK -> Roles
department       FK -> Departments
is_active
created_at
updated_at
```

The Users table is responsible for authentication and application
identity.

------------------------------------------------------------------------

## 7.4 PASSWORD RESET OTPS

``` text
id
user              FK -> Users
otp_hash
expires_at
is_used
attempt_count
created_at
```

The OTP itself should not be stored as plain text.

Flow:

``` text
OTP
 |
Hash
 |
Database
```

------------------------------------------------------------------------

## 7.5 STUDENTS

``` text
id
first_name
middle_name
last_name
university_pattern
department          FK -> Departments
semester            1..8
division
roll_number
is_active
created_at
updated_at
```

There is deliberately no `year_of_study` field because it can be derived
from semester.

------------------------------------------------------------------------

## 7.6 FACULTY

``` text
id
first_name
middle_name
last_name
email
department          FK -> Departments
designation
is_active
created_at
updated_at
```

Faculty members are academic resources for timetable generation.

------------------------------------------------------------------------

## 7.7 SUBJECTS

``` text
id
code
name
university_pattern
department          FK -> Departments
semester            1..8
subject_type        theoretical / practical
subject_category    regular / elective
elective_group      FK
created_at
updated_at
```

### Subject Type

-   theoretical
-   practical

### Subject Category

-   regular
-   elective

Elective subjects can belong to an elective group.

Example:

``` text
Elective Group A
    - Cloud Computing
    - Machine Learning
    - Cyber Security
```

------------------------------------------------------------------------

# 8. Student-Subject Enrollment

Student-subject enrollment is required because not every student
necessarily takes every subject.

This is especially important for:

-   Electives
-   Honours
-   Backlogs
-   Additional subjects
-   Different academic patterns

Example:

``` text
Student A -> Machine Learning
Student B -> Cloud Computing
Student C -> Cyber Security
```

Enrollment data is important when calculating student timetable
conflicts.

------------------------------------------------------------------------

# 9. Examination / Timetable Configuration

The project considered examination-specific entities such as:

## EXAM_TYPES

Potential fields:

``` text
id
name
duration
is_active
created_at
updated_at
```

Examples:

-   Regular Examination
-   Internal Examination
-   Practical Examination

Because IntelliExam is being generalized beyond examinations,
examination-specific concepts should not unnecessarily constrain the
core scheduling architecture.

------------------------------------------------------------------------

# 10. EXAMS

An examination entity was considered with fields such as:

``` text
id
subject
exam_type
required_invigilators
created_at
updated_at
```

However, the project questioned whether every individual exam should be
permanently stored.

The architecture is moving toward using **generation requests and
scopes** to define what needs to be scheduled instead of creating
unnecessary data.

------------------------------------------------------------------------

# 11. TIME SLOTS

The time-slot table is a reusable scheduling component.

``` text
id
name
start_time
end_time
created_at
updated_at
```

Example:

``` text
Slot 1
09:00 - 10:00

Slot 2
10:15 - 11:15

Slot 3
11:30 - 12:30
```

------------------------------------------------------------------------

# 12. Timetable Generation Request

A generation request represents a request to generate a timetable for a
particular scope.

Potential fields:

``` text
id
coordinator          FK -> Users
exam_type            FK
start_date
end_date
created_at
updated_at
```

As the system becomes generalized, the naming and relationships may be
adjusted so the concept is not unnecessarily exam-specific.

The important concept is:

> A generation request defines what scheduling operation the system
> needs to perform.

------------------------------------------------------------------------

# 13. Generation Scope

A generation request needs to specify exactly what academic data
participates in generation.

Example:

``` text
Department:
Computer Engineering

Semester:
5

Division:
A

Subjects:
DBMS
Computer Networks
AI
Software Engineering

Date Range:
01/10/2026 - 15/10/2026
```

A scope mechanism such as `GENERATION_REQUEST_SCOPES` can associate a
request with relevant:

-   Departments
-   Semesters
-   Divisions
-   Subjects
-   Faculty
-   Other scheduling resources

------------------------------------------------------------------------

# 14. Why Generation Scope Matters

Suppose the database contains:

``` text
10 departments
8 semesters
100+ subjects
5000 students
200 faculty
```

A coordinator may only want:

``` text
Computer Engineering
Semester 5
Division A
```

The GA should operate only on the required dataset.

Flow:

``` text
Complete Database
      |
Generation Request
      |
Generation Scope
      |
Relevant Scheduling Data
      |
Genetic Algorithm
```

This improves correctness and performance.

------------------------------------------------------------------------

# 15. Genetic Algorithm

The Genetic Algorithm is the core intelligence of IntelliExam.

A GA is an optimization technique inspired by biological evolution.

Instead of directly calculating one timetable, the system creates a
population of candidate timetables and progressively improves them.

------------------------------------------------------------------------

# 16. Basic GA Process

``` text
1. Define constraints
        |
2. Generate initial population
        |
3. Evaluate fitness
        |
4. Select better candidates
        |
5. Crossover
        |
6. Mutation
        |
7. Evaluate again
        |
8. Repeat
        |
9. Return optimized timetable
```

------------------------------------------------------------------------

# 17. Chromosome

A chromosome represents one complete timetable solution.

For example:

``` text
Subject A -> Monday 09:00
Subject B -> Monday 10:00
Subject C -> Tuesday 09:00
Subject D -> Wednesday 11:00
```

Depending on the final model, a gene may represent:

``` text
Subject
+
Date
+
Time Slot
+
Room
+
Faculty
```

For an examination timetable:

``` text
Exam
+
Date
+
Time Slot
+
Room
+
Invigilator
```

The exact chromosome representation must be finalized after the
scheduling domain is finalized.

------------------------------------------------------------------------

# 18. Population

The algorithm creates many candidate timetables.

``` text
Population

Timetable 1
Timetable 2
Timetable 3
...
Timetable 100
```

Each candidate is evaluated.

------------------------------------------------------------------------

# 19. Fitness Function

The fitness function measures the quality of a candidate timetable.

Constraints are divided into:

## Hard Constraints

These should not be violated.

Examples:

-   Same room cannot host two simultaneous sessions.
-   Same faculty cannot be assigned to two simultaneous sessions.
-   A student cannot have conflicting examinations.
-   Room capacity cannot be exceeded.
-   Required resources must exist.
-   Activities must use valid time slots.

## Soft Constraints

These are desirable but may be violated if necessary.

Examples:

-   Avoid faculty consecutive sessions.
-   Spread examinations across days.
-   Avoid undesirable time slots.
-   Minimize gaps.
-   Balance workload.
-   Prefer certain rooms.

------------------------------------------------------------------------

# 20. Example Fitness Calculation

Suppose:

``` text
Hard constraint violations = 2
Soft constraint violations = 5
```

A penalty-based fitness function could be:

``` text
Fitness Penalty =
    HardPenalty * HardViolations
    +
    SoftPenalty * SoftViolations
```

Example:

``` text
Hard penalty = 1000
Soft penalty = 10

Penalty =
1000 * 2 + 10 * 5
= 2050
```

The algorithm attempts to minimize the penalty.

The exact fitness function will be finalized during implementation and
testing.

------------------------------------------------------------------------

# 21. Selection

Selection chooses better candidates for reproduction.

Possible techniques:

-   Tournament selection
-   Roulette-wheel selection
-   Rank selection

The final method should be selected based on the fitness representation
and experimental results.

------------------------------------------------------------------------

# 22. Crossover

Crossover combines two parent timetables.

Example:

``` text
Parent A:
A B C D E F

Parent B:
G H I J K L

Child:
A B C J K L
```

Timetable crossover must be designed carefully because naive crossover
can introduce invalid schedules.

------------------------------------------------------------------------

# 23. Mutation

Mutation randomly changes part of a candidate timetable.

Example:

``` text
Before:
DBMS -> Monday 09:00

After:
DBMS -> Tuesday 11:00
```

Mutation prevents the population from becoming too similar and helps
explore alternative solutions.

------------------------------------------------------------------------

# 24. Constraint Handling

The algorithm must distinguish between:

``` text
Hard Constraints
      |
Must be satisfied

Soft Constraints
      |
Optimized where possible
```

Hard constraints should dominate the fitness calculation so that a
candidate with a fundamental conflict is not preferred merely because it
has fewer soft violations.

------------------------------------------------------------------------

# 25. Major Scheduling Conflicts

## Student Conflict

A student cannot attend two activities simultaneously.

``` text
Student 101

09:00 -> DBMS
09:00 -> CN

INVALID
```

## Faculty Conflict

A faculty member cannot be assigned to two simultaneous sessions.

``` text
Faculty X

09:00 -> Room 101
09:00 -> Room 202

INVALID
```

## Room Conflict

A room cannot host multiple simultaneous sessions.

``` text
Room 101

09:00 -> DBMS
09:00 -> AI

INVALID
```

## Room Capacity

``` text
Room capacity = 60
Students = 80

INVALID
```

## Availability Conflict

A resource should not be assigned outside its available time.

------------------------------------------------------------------------

# 26. Seating / Room Allocation

For examination scheduling, the project considered avoiding a database
record for every individual seat.

Instead, seating can be represented using:

-   Room
-   Capacity
-   Student range
-   Division/group
-   Allocation metadata

Example:

``` text
Room A
Capacity: 60
Roll Numbers: 1-60
```

This is more efficient than storing a permanent record for every
physical seat unless detailed seat-level tracking is actually required.

------------------------------------------------------------------------

# 27. Backend Structure

The backend uses Django.

The project initially uses one primary Django app:

``` text
api
```

The project should be modularized **logically**, rather than creating a
separate Django app for every model.

A suggested structure:

``` text
backend/
|
├── manage.py
|
├── config/
|   ├── settings.py
|   ├── urls.py
|   ├── wsgi.py
|   └── asgi.py
|
├── api/
|   |
|   ├── models/
|   |   ├── __init__.py
|   |   ├── user.py
|   |   ├── academic.py
|   |   ├── timetable.py
|   |   └── ...
|   |
|   ├── serializers/
|   |   ├── auth.py
|   |   ├── academic.py
|   |   ├── timetable.py
|   |   └── ...
|   |
|   ├── views/
|   |   ├── auth.py
|   |   ├── users.py
|   |   ├── academic.py
|   |   ├── timetable.py
|   |   ├── generation.py
|   |   └── admin.py
|   |
|   ├── services/
|   |   ├── timetable/
|   |   ├── genetic_algorithm/
|   |   └── ...
|   |
|   ├── urls.py
|   └── ...
|
├── requirements.txt
└── .env
```

The exact structure can evolve.

The key principle is:

> **Modularize by domain/functionality, not blindly by model.**

------------------------------------------------------------------------

# 28. Important Backend Principle

The Genetic Algorithm should not be implemented directly inside Django
views.

Avoid:

``` python
def generate_timetable(request):
    # hundreds of lines of GA code
```

Prefer:

``` text
Django View
    |
Generation Service
    |
GA Engine
    |
Fitness / Constraints
    |
Generated Solution
    |
Database
```

The view handles HTTP/API concerns.

The service handles business logic.

The GA engine handles optimization.

------------------------------------------------------------------------

# 29. API Architecture

General request flow:

``` text
React
  |
HTTP Request
  |
Django REST Framework
  |
View
  |
Serializer
  |
Service / Model
  |
PostgreSQL
```

For generation:

``` text
React
  |
POST /api/timetable/generate/
  |
Generation View
  |
Generation Service
  |
Genetic Algorithm
  |
Generated Timetable
  |
Response
```

------------------------------------------------------------------------

# 30. Authentication

Authentication is handled by the Django backend.

The backend must enforce authorization.

Hiding an Admin button in React is not sufficient.

The backend should verify:

``` text
Authenticated?
Active?
Correct role?
Authorized for this operation?
```

------------------------------------------------------------------------

# 31. Password Reset

Password reset uses OTP.

Flow:

``` text
User enters email
       |
Backend checks registered user
       |
Generate OTP
       |
Hash OTP
       |
Store OTP record
       |
Send OTP by email
       |
User enters OTP
       |
Verify hash
       |
Check expiry
       |
Check attempts
       |
Allow password reset
```

The backend should verify that the email is registered before sending a
password-reset OTP.

------------------------------------------------------------------------

# 32. Email

The current backend work uses **Brevo transactional email services** for
email operations such as OTP delivery.

Credentials/API keys must remain in `.env`.

Never commit secrets to GitHub.

------------------------------------------------------------------------

# 33. Environment Configuration

Typical `.env` values include:

``` text
SECRET_KEY
DEBUG
DATABASE_NAME
DATABASE_USER
DATABASE_PASSWORD
DATABASE_HOST
DATABASE_PORT
BREVO_API_KEY
```

The exact variables depend on implementation.

Use `.env.example` for sharing required variable names without secrets.

------------------------------------------------------------------------

# 34. Database Migration Workflow

After model changes:

``` bash
python manage.py makemigrations
```

Then:

``` bash
python manage.py migrate
```

Create the administrator:

``` bash
python manage.py createsuperuser
```

Run the backend:

``` bash
python manage.py runserver
```

------------------------------------------------------------------------

# 35. Frontend Setup

Install dependencies:

``` bash
npm install
```

Run development server:

``` bash
npm run dev
```

The frontend communicates with the Django REST API.

------------------------------------------------------------------------

# 36. Backend Setup

Create virtual environment:

``` bash
python -m venv .venv
```

Windows:

``` bash
.venv\Scripts\activate
```

Install dependencies:

``` bash
pip install -r requirements.txt
```

Configure `.env`.

Run:

``` bash
python manage.py migrate
```

Create admin:

``` bash
python manage.py createsuperuser
```

Start server:

``` bash
python manage.py runserver
```

------------------------------------------------------------------------

# 37. Development Order

Recommended development order:

``` text
1. Project setup
2. Database models
3. Migrations
4. Authentication
5. Roles / Departments / Users
6. Faculty
7. Subjects
8. Students
9. Student-subject enrollment
10. Timetable configuration
11. Generation request
12. Generation scope
13. Constraint engine
14. Genetic Algorithm
15. Generated timetable persistence
16. Frontend integration
```

Base academic entities should be stable before implementing the GA.

------------------------------------------------------------------------

# 38. Current API Development Progress

The backend is currently in the academic master-data/API development
phase.

Work has been progressing through:

``` text
Authentication
Roles
Departments
Users
Faculty
Subjects
Students
Student-Subject Enrollment
```

The API views are being grouped logically instead of creating a separate
file for every model.

The next major domain is the timetable/generation system.

------------------------------------------------------------------------

# 39. Admin Logs

The project includes system logging.

The admin logs endpoint was changed to return log data so that the
frontend can render it with pagination.

This keeps pagination concerns clear between backend data delivery and
frontend presentation.

------------------------------------------------------------------------

# 40. Active / Inactive Records

Master entities use:

``` text
is_active
```

rather than relying on deletion.

Example:

``` text
Faculty X
is_active = false
```

This means the faculty member should not participate in new scheduling
operations while historical data can remain intact.

------------------------------------------------------------------------

# 41. Timetable Generation Lifecycle

The intended workflow:

``` text
Coordinator logs in
        |
Creates generation request
        |
Selects academic scope
        |
Selects date/time range
        |
System validates input
        |
System loads scheduling data
        |
System builds constraints
        |
GA creates initial population
        |
Fitness evaluation
        |
Selection
        |
Crossover
        |
Mutation
        |
Constraint evaluation
        |
Termination condition
        |
Best valid/optimized solution
        |
Save generated timetable
        |
Coordinator reviews
        |
Publish/finalize
```

------------------------------------------------------------------------

# 42. GA Termination Conditions

Possible termination conditions:

### Maximum generations

``` text
generation >= MAX_GENERATIONS
```

### Target fitness

``` text
fitness >= TARGET
```

### Zero hard constraints

``` text
hard_violations == 0
```

combined with an acceptable soft-constraint threshold.

### No improvement

Stop if fitness has not improved for a defined number of generations.

------------------------------------------------------------------------

# 43. Performance Considerations

The GA can become computationally expensive.

Complexity increases with:

``` text
Number of subjects
x Number of students
x Number of faculty
x Number of rooms
x Number of time slots
x Population size
x Number of generations
```

Therefore, avoid repeated PostgreSQL queries inside every fitness
calculation.

Prefer:

``` text
Database
   |
Load scoped data once
   |
In-memory scheduling structures
   |
GA evaluation
```

------------------------------------------------------------------------

# 44. Data Flow During Generation

Preferred architecture:

``` text
PostgreSQL
     |
Generation Service
     |
Load scoped data
     |
Build in-memory structures
     |
Constraint Engine
     |
Genetic Algorithm
     |
Best Candidate
     |
Validation
     |
Database
```

The database should not become the bottleneck inside the GA loop.

------------------------------------------------------------------------

# 45. Validation Before Generation

Before starting the GA, the system should check whether the requested
scheduling problem is obviously feasible.

Example:

``` text
Required exams = 40
Available slots = 5
```

If every exam requires a separate slot, generation may be impossible.

Another example:

``` text
Required room capacity = 500
Available room capacity = 300
```

The system should detect obvious infeasibility before running an
expensive optimization process.

------------------------------------------------------------------------

# 46. General Timetable vs Examination Timetable

The scheduling engine should be separated from the specific timetable
type.

Use generalized concepts such as:

``` text
Activity
Resource
Time Slot
Location
Constraint
Assignment
```

An examination can be one type of activity.

A lecture can be another.

A practical session can be another.

This makes the engine extensible.

------------------------------------------------------------------------

# 47. Possible Generalized Model

A future generalized representation could be:

``` text
Activity
    |
    +-- Subject
    +-- Faculty
    +-- Student Group
    +-- Duration
    +-- Requirements

Assignment
    |
    +-- Activity
    +-- Date
    +-- Time Slot
    +-- Room
    +-- Resources
```

The GA optimizes these assignments.

------------------------------------------------------------------------

# 48. Frontend Responsibilities

React should primarily handle:

-   Authentication UI
-   Dashboards
-   Forms
-   Tables
-   Filters
-   Search
-   Pagination
-   Timetable visualization
-   Generation configuration
-   Generated timetable preview
-   Error/success messages

The frontend should not contain authoritative scheduling logic.

------------------------------------------------------------------------

# 49. Backend Responsibilities

Django should handle:

-   Authentication
-   Authorization
-   Validation
-   Database operations
-   Business rules
-   Generation requests
-   Scheduling constraints
-   Genetic Algorithm execution
-   Timetable persistence
-   Logging
-   API responses

------------------------------------------------------------------------

# 50. Example API Domain Structure

A logical API structure could be:

``` text
/api/auth/
/api/users/
/api/departments/
/api/faculty/
/api/students/
/api/subjects/
/api/enrollments/

/api/timetable/
/api/timetable/generation/
/api/timetable/scopes/
/api/timetable/generated/
```

Exact URL names can evolve.

The important principle is to organize APIs around business domains.

------------------------------------------------------------------------

# 51. CRUD Pattern

For master data such as subjects:

``` text
GET     /api/subjects/
POST    /api/subjects/
GET     /api/subjects/{id}/
PATCH   /api/subjects/{id}/
DELETE  /api/subjects/{id}/
```

Where appropriate, deactivation can be preferred over deletion:

``` http
PATCH /api/subjects/{id}/
```

``` json
{
    "is_active": false
}
```

------------------------------------------------------------------------

# 52. Example Generation API

Conceptually:

``` text
POST /api/timetable/generation/
```

Possible request:

``` json
{
    "start_date": "2026-10-01",
    "end_date": "2026-10-15",
    "scope": {
        "department": 1,
        "semester": 5,
        "division": "A"
    }
}
```

Backend flow:

``` text
Validate
   |
Create generation request
   |
Resolve scope
   |
Load scheduling entities
   |
Run GA
   |
Validate solution
   |
Return result
```

The final request/response schema should be finalized after the
timetable models are finalized.

------------------------------------------------------------------------

# 53. Important Design Decisions

### Decision 1

Use:

``` text
Django + Django REST Framework
```

rather than FastAPI.

### Decision 2

Use:

``` text
PostgreSQL
```

as the database.

### Decision 3

Use one main Django app:

``` text
api
```

rather than creating an app for every model.

### Decision 4

Group views logically instead of creating dozens of view files.

### Decision 5

Do not store year of study because it is derivable from semester.

### Decision 6

Use `is_active` for master-data lifecycle.

### Decision 7

Deployment-related tables are not part of IntelliExam's current scope.

### Decision 8

Avoid unnecessary individual seat records.

### Decision 9

Mentor/project-management functionality from other projects should not
be mixed into IntelliExam.

------------------------------------------------------------------------

# 54. What Is NOT the Core of IntelliExam

These concepts belong to other projects/workflows and should not be
mixed into IntelliExam:

-   DevOps pipelines
-   Jenkins
-   SonarQube
-   ATS/recruitment
-   Deployment tracking
-   Student project-management workflows
-   Team-management workflows

The central problem is:

> **Constraint-aware timetable generation using Genetic Algorithm
> optimization.**

------------------------------------------------------------------------

# 55. Testing Strategy

## Model Tests

Test:

-   Required fields
-   Foreign keys
-   Choices
-   Unique constraints
-   Validation

## API Tests

Test:

-   Authentication
-   Authorization
-   CRUD
-   Validation
-   Error handling

## Constraint Tests

Test each scheduling constraint independently.

Example:

``` text
Two exams assigned to same room
-> Must fail
```

``` text
Same student assigned to two simultaneous exams
-> Must fail
```

## GA Tests

Verify:

-   Population generation
-   Chromosome validity
-   Crossover
-   Mutation
-   Fitness evaluation
-   Reduction of hard violations
-   Termination

## Integration Tests

Test:

``` text
API
 |
Generation Service
 |
GA
 |
Database
```

as one complete workflow.

------------------------------------------------------------------------

# 56. Major Risks

## Risk 1 --- Overcomplicated Database

Do not create a table for every small concept without a clear business
need.

## Risk 2 --- Implementing GA Too Early

First define:

-   What is being scheduled?
-   What are the resources?
-   What are hard constraints?
-   What are soft constraints?
-   What is the chromosome?
-   What does fitness mean?

## Risk 3 --- GA Inside Views

Keep GA logic in a dedicated service/module.

## Risk 4 --- Database Queries Inside Fitness Evaluation

Load data into memory before running the GA.

## Risk 5 --- Treating All Constraints Equally

Hard constraints and soft constraints require different priorities.

## Risk 6 --- Assuming a Solution Always Exists

The system must be able to identify infeasible scheduling
configurations.

------------------------------------------------------------------------

# 57. Current Mental Model

A developer should think of IntelliExam as four major layers:

``` text
                 INTELLIEXAM
                      |
       +--------------+--------------+
       |              |              |
       v              v              v
   ACADEMIC       SCHEDULING      OPTIMIZATION
     DATA           DATA              ENGINE
       |              |              |
     Users          Time Slots      Genetic
     Faculty        Rooms           Algorithm
     Students       Requests        Fitness
     Subjects       Scope           Constraints
     Enrollment                    Mutation
                                   Crossover
                                   Selection
```

The application layer sits above these:

``` text
React
  |
Django REST API
  |
Services
  |
Database + Scheduling Engine
```

------------------------------------------------------------------------

# 58. Final Architecture Goal

``` text
                         +-----------------------+
                         |    React Frontend     |
                         |                       |
                         | Admin                 |
                         | Coordinator           |
                         | Faculty               |
                         | Student               |
                         +-----------+-----------+
                                     |
                                  REST API
                                     |
                         +-----------v-----------+
                         |    Django REST API    |
                         +-----------------------+
                         | Authentication        |
                         | Authorization         |
                         | Validation            |
                         | Academic APIs         |
                         | Timetable APIs        |
                         +-----------+-----------+
                                     |
                         +-----------v-----------+
                         |   Business Services   |
                         +-----------------------+
                         | Enrollment Service    |
                         | Generation Service    |
                         | Timetable Service     |
                         +-----------+-----------+
                                     |
                    +----------------+----------------+
                    |                                 |
          +---------v---------+             +---------v---------+
          | Constraint Engine|             | Genetic Algorithm |
          +------------------+             +-------------------+
          | Hard Constraints |             | Population        |
          | Soft Constraints |             | Selection         |
          | Conflict Checks  |             | Crossover         |
          | Fitness          |             | Mutation          |
          +---------+--------+             | Termination       |
                    |                      +---------+---------+
                    +----------------+---------------+
                                     |
                         +-----------v-----------+
                         |      PostgreSQL       |
                         +-----------------------+
                         | Users                 |
                         | Departments           |
                         | Students              |
                         | Faculty               |
                         | Subjects              |
                         | Enrollment            |
                         | Time Slots            |
                         | Generation Requests   |
                         | Generated Timetables  |
                         +-----------------------+
```

------------------------------------------------------------------------

# 59. Current Project Status

The project is currently in the **backend/API development stage**.

Broad progress:

``` text
Project planning                  DONE
Database schema design            DONE
Core Django setup                 DONE
Base models                       DONE
Migration setup                   DONE
Authentication                   IN PROGRESS / MODULE-WISE
Academic APIs                     IN PROGRESS
Faculty API                       IN PROGRESS / MODULE-WISE
Subject API                       IN PROGRESS / MODULE-WISE
Student API                       CURRENT
Enrollment API                    CURRENT / NEXT
Timetable models                  UPCOMING
Generation request                UPCOMING
Generation scope                  UPCOMING
Constraint engine                 UPCOMING
Genetic Algorithm                 UPCOMING
Generated timetable persistence   UPCOMING
Frontend integration              UPCOMING
```

The immediate focus should remain on stabilizing academic master data
and APIs before implementing the Genetic Algorithm.

------------------------------------------------------------------------

# 60. Golden Rule for Future Development

Before adding a new model, API, or feature, ask:

1.  What real-world entity does this represent?
2.  Is the information already derivable?
3.  Does it need to be stored permanently?
4.  Is it required for timetable generation?
5.  Is it a hard constraint or soft constraint?
6.  Which layer should own this logic?

Possible layers:

``` text
UI
API
Service
Constraint Engine
Genetic Algorithm
Database
```

This prevents unnecessary complexity.

------------------------------------------------------------------------

# 61. One-Sentence Project Definition

> **IntelliExam is a Django- and React-based intelligent
> timetable-generation system that uses a Genetic Algorithm to produce
> constraint-aware academic schedules while minimizing conflicts and
> optimizing institutional scheduling requirements.**

------------------------------------------------------------------------

# 62. Most Important Next Step

The next major design task is **not immediately writing the Genetic
Algorithm**.

Before implementing it, finalize the scheduling domain:

``` text
What exactly is an Activity?
What is a Resource?
What is an Assignment?
What resources can be allocated?
What are the Hard Constraints?
What are the Soft Constraints?
What should one Chromosome represent?
What should the Fitness Function measure?
```

Once these are finalized, the timetable models, constraint engine, and
GA can be implemented around a stable design instead of being rewritten
later.
