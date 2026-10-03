# IntelliExam — Frontend Requirements


## 1. User Roles & Access

   ### 1.1 Admin

   The Admin has system-level access.
   The Admin is not restricted to a single department.

   **Responsibilities:**
   - Manage departments.
   - Create and manage Exam Coordinator accounts.
   - View system-wide timetable information.

   ----

   ### 1.2 Exam Coordinator

   The Exam Coordinator is assigned to a specific department.
   An Exam Coordinator can only access and manage data associated with their assigned department.

   **Responsibilities:**
   - Manage students belonging to their department.
   - Manage faculty belonging to their department.
   - Manage subjects and academic information for their department.
   - Manage elective groups and student subject enrollments.
   - Manage exam types.
   - Manage rooms and available time slots.
   - Create timetable generation requests.
   - Select the required academic cohorts for timetable generation.
   - View and manage generated timetables.
   - Manage room/student allocations.
   - Manage invigilation assignments.

   ----

   Students and faculty are **not system login roles** in the current scope.
   
   ----



## 2. Application Navigation

   ### 2.1 Admin Menu

   - Dashboard
   - Departments
   - Exam Coordinators
   - Exam Types
   - Rooms
   - Time Slots
   - Timetables
   - Profile
   - Logout

   ----

   ### 2.2 Exam Coordinator Menu

   - Dashboard
   - Students
   - Faculty
   - Subjects
     - Elective Groups
   - Generate Timetable
   - Timetables
   - Profile
   - Logout

   ----



## 3. Authentication

   ### 3.1 Login

   **Fields:**
   - Email
   - Password

   **Actions:**
   - Login
   - Forgot Password

   ----

   ### 3.2 Forgot Password

   **Flow:**
   → Enter Email
   → Send OTP
   → Enter OTP
   → Set New Password
   → Confirm Password
   → Password Reset Successful
   → Return to Login
   
   ----



## 4. Pages & Features

   ### 4.1 Admin

   - Dashboard
   - Department Management
   - Exam Coordinator Management
   - Exam Type Management
   - Room Management
   - Time Slot Management
   - Timetable Management
   - Profile

   ----

   ### 4.2 Exam Coordinator

   - Dashboard
   - Student Management
   - Faculty Management
   - Subject Management
   - Timetable Generation
   - Timetable Management
   - Profile

   ----



## 5. Page-wise Functional Requirements

   ### 5.1 Admin

   #### 5.1.1 Admin Dashboard

      **Information to display:**
      - Total Departments
      - Total Exam Coordinators
      - Total Students
      - Total Faculty
      - Total Subjects
      - Total Rooms
      - Total Generated Timetables

      **Quick Actions:**
      - Add Department
      - Add Exam Coordinator
      - View Timetables

      **Recent Activity / Information:**
      - Recently generated timetables
      - Recently created coordinator accounts


   #### 5.1.2 Department Management

      **Features:**
      - View all departments.
      - Add a new department.
      - Edit department details.
      - Activate/deactivate a department.

      **Information to display:**
      - Department Code
      - Department Name
      - Status
      - Created Date

      **User Actions:**
      - Add Department
      - Edit Department
      - Activate/Deactivate Department
      - Search departments


   #### 5.1.3 Exam Coordinator Management

      **Features:**
      - View all Exam Coordinators.
      - Create a new Exam Coordinator account.
      - Edit coordinator details.
      - Activate/deactivate coordinator accounts.
      - View the department assigned to each coordinator.

      **Information to display:**
      - Name
      - Email
      - Department
      - Status
      - Created Date

      **Create/Edit Coordinator fields:**
      - First Name
      - Middle Name
      - Last Name
      - Email
      - Department

      **User Actions:**
      - Add Exam Coordinator
      - Edit Exam Coordinator
      - Activate/Deactivate Coordinator
      - Search Coordinators
      - Filter by Department


   #### 5.1.4 Exam Type Management

      **Features:**
      - View all exam types.
      - Add a new exam type.
      - Edit exam type details.
      - Activate/deactivate an exam type.

      **Information to display:**
      - Exam Type Name
      - Duration
      - Status
      - Created Date

      **Add/Edit Exam Type fields:**
      - Exam Type Name
      - Duration

      **User Actions:**
      - Add Exam Type
      - Edit Exam Type
      - Activate/Deactivate Exam Type
      - Search Exam Types


   #### 5.1.5 Room Management

      **Features:**
      - View all rooms.
      - Add a new room.
      - Edit room details.
      - Activate/deactivate a room.

      **Information to display:**
      - Room Number
      - Building
      - Floor
      - Room Type
      - Capacity
      - Status

      **Add/Edit Room fields:**
      - Room Number
      - Building
      - Floor
      - Room Type [Classroom, Lab, Seminar]
      - Capacity

      **User Actions:**
      - Add Room
      - Edit Room
      - Activate/Deactivate Room
      - Search Rooms
      - Filter by Building
      - Filter by Room Type


   #### 5.1.6 Time Slot Management

      **Features:**
      - View all time slots.
      - Add a new time slot.
      - Edit time slot details.
      - Activate/deactivate a time slot.

      **Information to display:**
      - Time Slot Name
      - Start Time
      - End Time
      - Status

      **Add/Edit Time Slot fields:**
      - Time Slot Name
      - Start Time
      - End Time

      **User Actions:**
      - Add Time Slot
      - Edit Time Slot
      - Activate/Deactivate Time Slot
      - Search Time Slots


   #### 5.1.7 Timetable Management

      **Features:**
      - View all generated timetables.
      - View timetable details.
      - Search timetables.
      - Filter timetables.

      **Information to display:**
      - Timetable ID
      - Exam Type
      - Time Slot
      - Date Range
      - Generation Date

      **User Actions:**
      - View Timetable
      - Search
      - Filter


   #### 5.1.8 Admin Profile

      **Information to display:**
      - First Name
      - Middle Name
      - Last Name
      - Email
      - Role

      **User Actions:**
      - Edit profile information
      - Change password
      - Logout

   ----


   ### 5.2 Exam Coordinator

   #### 5.2.1 Exam Coordinator Dashboard

      **Information to display:**
      - Total Students
      - Total Faculty
      - Total Subjects
      - Total Generated Timetables
      - Upcoming Examinations
      - Recently Generated Timetables

      **Quick Actions:**
      - Add Student
      - Add Faculty
      - Add Subject
      - Generate Timetable


   #### 5.2.2 Student Management

      **Features:**
      - View all students.
      - Add a new student.
      - Edit student details.
      - Activate/deactivate a student.
      - Search students.
      - Filter students.
      - View student academic information.

      **Information to display:**
      - Name
      - Email
      - Phone
      - Gender
      - Year of Study
      - University Pattern
      - Semester
      - Division
      - Roll Number
      - Status

      **Add/Edit Student fields:**
      - First Name
      - Middle Name
      - Last Name
      - Email
      - Phone
      - Gender
      - Year of Study [FE, SE, TE, BE]
      - University Pattern
      - Semester [1, 2, 3, 4, 5, 6, 7, 8]
      - Division
      - Roll Number

      **User Actions:**
      - Add Student
      - Edit Student
      - Activate/Deactivate Student
      - Search Students
      - Filter Students
      - View Student Details


   #### 5.2.3 Faculty Management

      **Features:**
      - View all faculty members.
      - Add a new faculty member.
      - Edit faculty details.
      - Activate/deactivate a faculty member.
      - Search faculty members.
      - Filter faculty members.
      - View faculty details.

      **Information to display:**
      - Name
      - Email
      - Phone
      - Gender
      - Designation
      - Status

      **Add/Edit Faculty fields:**
      - First Name
      - Middle Name
      - Last Name
      - Email
      - Phone
      - Gender
      - Designation

      **User Actions:**
      - Add Faculty
      - Edit Faculty
      - Activate/Deactivate Faculty
      - Search Faculty
      - Filter Faculty
      - View Faculty Details


   #### 5.2.4 Subject Management

      **Features:**
      - View all subjects.
      - Add a new subject.
      - Edit subject details.
      - Search subjects.
      - Filter subjects.
      - Manage elective groups for elective subjects.

      **Information to display:**
      - Subject Code
      - Subject Name
      - Year of Study
      - University Pattern
      - Semester
      - Subject Type
      - Subject Category
      - Elective Group (if applicable)

      **Add/Edit Subject fields:**
      - Subject Code
      - Subject Name
      - Year of Study [FE, SE, TE, BE]
      - University Pattern
      - Semester [1, 2, 3, 4, 5, 6, 7, 8]
      - Subject Type [Theorotical, Practical]
      - Subject Category [Regular, Elective]
      - Elective Group (for elective subjects)

      **User Actions:**
      - Add Subject
      - Edit Subject
      - Search Subjects
      - Filter Subjects
      - View Subject Details

      **Elective Groups:**

      For an elective subject, the coordinator should be able to associate the subject with an existing elective group or create/manage an elective group.


   #### 5.2.5 Timetable Generation

      **Generation Form:**
      - Exam Type
      - Start Date
      - End Date

      The coordinator does not manually select:
      - Academic cohorts
      - Subjects
      - Time slots

      The system automatically determines the applicable academic cohorts and subjects based on the coordinator's department and the available academic data.

      **Generation Process:**
      1. Coordinator selects the exam type.
      2. Coordinator specifies the start and end dates.
      3. Coordinator submits the generation request.
      4. The backend automatically determines the applicable academic cohorts and subjects.
      5. The backend prepares the required data and sends it to the Genetic Algorithm.
      6. The Genetic Algorithm generates the timetable.
      7. The generated timetable is returned to the backend and stored.
      8. The frontend displays the generated timetable.

      **GA Output:**
      The Genetic Algorithm determines:
      - One common time slot for the generated timetable.
      - Exam date for each subject.
      - Room allocation.
      - Student allocation.
      - Invigilation assignment.

      The frontend should display an appropriate loading/progress state while timetable generation is in progress.
      After successful generation, the coordinator should be able to view the generated timetable.


   #### 5.2.6 Timetable Management

      **Features:**
      - View generated timetables.
      - View timetable details.
      - Search timetables.
      - Filter timetables.
      - View exam schedule.
      - View room allocation.
      - View student allocation.
      - View invigilation assignments.

      **Information to display in the timetable list:**
      - Timetable ID
      - Exam Type
      - Common Time Slot
      - Date Range
      - Generation Date


      **Timetable Details:**

      The timetable details page should display:
      - Exam Type
      - Common Time Slot
      - Date Range
      - Academic Cohorts
      - Subject-wise examination schedule
      - Room allocation
      - Student/room allocation
      - Invigilation assignments


      **Subject-wise Schedule:**

      Each timetable entry should display:
      - Subject
      - Exam Date
      - Common Time Slot


      **Room Allocation:**

      The coordinator should be able to view the rooms assigned to each examination.


      **Student Allocation:**

      The coordinator should be able to view how students are distributed among the allocated rooms, including:
      - Group/range-based allocations
      - Individual student allocations where applicable


      **Invigilation:**
      The coordinator should be able to view the faculty members assigned as invigilators for each examination/room.
      The timetable and its allocations are generated by the system. The frontend should primarily provide views for the generated results.


   #### 5.2.7 Exam Coordinator Profile

      **Information to display:**
      - First Name
      - Middle Name
      - Last Name
      - Email
      - Department
      - Role

      **User Actions:**
      - Edit profile information
      - Change password
      - Logout

   ----



## 6. Validation & Form Behaviour

   ### 6.1 General Validation

   - Required fields should be clearly indicated.
   - Forms should validate user input before submission.
   - Validation errors should be displayed next to the relevant field.
   - The form should not be submitted when required or invalid fields are present.
   - User-entered data should be preserved when a submission fails.

   ----

   ### 6.2 Create & Edit Forms

   - Create and Edit should use appropriate forms for each entity.
   - Edit forms should be pre-filled with the existing data.
   - Successful submission should close the form/modal where applicable and refresh the relevant data.

   ----

   ### 6.3 Conditional Fields

   Fields whose availability depends on another selection should be shown or hidden dynamically.

   Example:

   **Subject Category:**
   - Regular → Elective Group is not required.
   - Elective → Elective Group should be available/required.

   ----

   ### 6.4 Date Validation

   For timetable generation:

   - Start Date must be provided.
   - End Date must be provided.
   - End Date should not be earlier than Start Date.

   ----
