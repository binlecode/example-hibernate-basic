
# Example of Hibernate Native Bootstrap

Typically for vanilla Java application Hibernate is bootstrapped in a so-called native mode.

The configuration is either by xml or programmatically. 

- jdbc settings
- pool settings if connection pooling is needed
- hibernate ORM settings

## Bootstrap steps
There are three main steps in bootstrapping Hibernate in native way:

#### 1. building ServiceRegistry

ServiceRegistry holds the services Hibernate will need during bootstrapping and at run time.

Under the hood, we are concerned with building 2 different ServiceRegistries. 
First is the org.hibernate.boot.registry.BootstrapServiceRegistry. 
The BootstrapServiceRegistry is intended to hold services that Hibernate needs at both bootstrap and run time.
If you are ok with the default behavior of Hibernate in regards to these BootstrapServiceRegistry services,
(which is quite often the case, especially in stand-alone environments), 
then building the BootstrapServiceRegistry can be skipped.

The second ServiceRegistry is the `org.hibernate.boot.registry.StandardServiceRegistry`. 
You will almost always need to configure the StandardServiceRegistry, 
which is done through `org.hibernate.boot.registry.StandardServiceRegistryBuilder`.

#### 2. building Metadata

Second step is the building of an `org.hibernate.boot.Metadata` object containing the parsed 
representations of an application domain model and its mapping to a database. 
We need to provide the source information to be parsed (annotated classes, hbm.xml files, orm.xml files). 
This is the purpose of `org.hibernate.boot.MetadataSources`.

#### 3. building SessionFactory

Final step in native bootstrapping is to build the SessionFactory.

## Connection provider and custom connection pool

Hibernate connects to database by connection provider or custom connection pool configuration.

The ConnectionProvider to use is defined by the `hibernate.connection.provider_class` property,
which is usually not customized. 
If not set, by default Hibernate will use its built-in connection pool (not recommended for production).
This example uses Hibernate built-in pool.

For production, we should use high-performance connection pools, such as `c3p0`, `hikari`, etc. 
They are supported by Hibernate with supporting libraries such as 
[`hibernate-c3p0`](https://mvnrepository.com/artifact/org.hibernate/hibernate-c3p0), 
[`hibernate-hikaricp`](https://mvnrepository.com/artifact/org.hibernate/hibernate-hikaricp), etc.

<pre>
Note that it is error prone to directly set up a datasource in hibernate service registry,
in such case Hibernate doesn't know of the pool setting within the dataSource object. 
</pre>

For example, for Hikari pool, we should set properties like `hibernate.hikari.xxx` to let Hibernate setup 
connection pool.

See Hibernate [official doc](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#database-connectionprovider)
for detailed configuration for different pool settings.

## Exception handling

As a rule of thumb, no exception thrown by Hibernate can be treated as recoverable. 
Ensure that the Session will be closed by calling the close() method in a finally block.

## DoWithSession

For error handling and transaction control, there are different session management coded in this example:
- very-basic transaction and session control in nested try-catch blocks, 
see [`CompanyDataService`](./src/main/java/example/binle/hbm/CompanyDataService.java) class.
- try-with-resource block and functional interface callback for in-session database logic,
see [`EmployeeDataService`](./src/main/java/example/binle/hbm/EmployeeDataService.java) class.


## HibernateUtil classes

There are multiple util classes in this example showing different ways of getting sessionFactory.

- `HibernateUtilWithCfgXml.java`: configuring sessionFactory by cfg xml
- `HibernateUtilNoCfgXml.java`: configuring sessionFactory programmatically
- `HibernateUtilCustomPool.java`: configuring sessionFactory programmatically and with custom connection pool

All util classes can load external configuration from properties file, thanks to Hibernate's 
nice `StandardServiceRegistryBuilder#loadProperties()` API.

## Environment 

JDK: Oralce or Open JDK 1.8+

Maven: version 3.3+

#### H2 embedded database

This example runs H2 db in embedded mode:
* add h2 maven dependency and use the JDBC driver class: `org.h2.Driver`
* The database URL `jdbc:h2:./testDb` opens the database `testDb` in project root directory

By default, H2 will create the `testDb` if not exist when application connects to this jdbc url.

If in jdbc URL `MODE=MYSQL` is set to mimic mysql DDL and DML, then Hibernate is configured to use Mysql dialect.

