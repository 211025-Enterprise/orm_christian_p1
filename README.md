# RevORM

## Project Description

Something like: A java based ORM for simplifying connecting to and from an SQL database without the need for SQL or connection management.

## Technologies Used

- PostgreSQL - version 42.2.12
- Java - version 8.0
- Apache commons - version 2.1
- JUnit

## Features

List of features ready and TODOs for future development

- Easy to use and straightforward user API.
- No need for SQL, HQL, or any databse specific language.
- Straightforward and simple Annotation based for ease of use.
- etc...

## Getting Started

Currently project must be included as local dependency. to do so:

```shell
  git clone https://github.com/210726-Enterprise/webapp_christian_p1.git
  cd webapp_christian_p1
  mvn install
```

Next, place the following inside your project pom.xml file:

```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>webapp_christian_p1</artifactId>
    <version>1.0</version>
  </dependency>

```

Finally, inside your project structure you need a application.properties file.
(typically located src/main/resources/)

```
 url=path/to/database
 admin-usr=username/of/database
 admin-pw=password/of/database
```

## Usage

### Annotating classes

All classes which represent objects in database must be annotated.

- #### @PrimaryKey
  - Indicates that the annotated field is the primary key for the table.
- #### @Unique
  - Indicates that the annotated field is unqiue.
- #### @NotNull
  - Indicates that the annotated field is not null.
- #### @Setter
  - Indicates that the anotated method is a setter for a column.
- #### @Getter
  - Indicates that the anotated method is a getter for a column.

### User API

-### ORM
- #### `public static String createTable(Class<?> clazz)`
  - Creates a sql string to create a table for a class.
- #### `public static String createRecord(Class<?> clazz)`
  - Creates a sql string to create a record for a table.
- #### `public static String readRecord(Class<?> clazz)`
  - Creates a sql string to read a record from a table.
- #### `public static String readAll(Class<?> clazz)`
  - Creates a sql string to read all records from a table.
- #### `public static String updateRecord(Class<?> clazz, String property)`
  - Creates a sql string to update a table.
- #### `public static String deleteRecord(Class<?> clazz)`
  - Creates a sql string to delete a record from a table.

-### Dao
- #### `public void createTable(String sql)`
  - Executes a sql statement to create a table for a class.
- #### `public Object createRecord(String sql, Object o)`
  - Executes a sql statement to create a record for a class.
- #### `public Object readRecord(String sql, Object o)`
  - Executes a sql statement to read a record from a table.
- #### `public ArrayList readAll(String sql, Object o)`
  - Executes a sql statement to read all records from a table.
- #### `public boolean updateRecord(String sql, Object Change, Object primary_key)`
  - Executes a sql statement to update a record in a table.
- #### `public boolean deleteById(String sql, Object primary_key)`
  - Executes a sql statement to delete a record in a table.
- #### `public static Object getInstance(java.lang.Class<?> clazz)`
  - Creates an instance of an object given a class type.


## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
