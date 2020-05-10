

## Environment 

JDK: Oralce or Open JDK 1.8+

Maven: version 3.3+

#### H2 embedded database

This example runs H2 db in embedded mode:
* add h2 maven dependency and use the JDBC driver class: `org.h2.Driver`
* The database URL `jdbc:h2:./testDb` opens the database `testDb` in project root directory

By default, H2 will create the `testDb` if not exist when application connects to this jdbc url.
   
## Hibernate configuration

In the jdbc URL `MODE=MYSQL` is used to mimic mysql DDL and DML, and Hibernate is configured to use Mysql dialect.

Typically for vanilla Java application Hibernate is configured by either xml or programmatically. 

- jdbc settings
- pool settings if connection pooling is needed
- hibernate ORM settings



