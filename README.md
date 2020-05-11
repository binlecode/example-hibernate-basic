
# Example of Hibernate Native Bootstrap


## Bootstrap steps
There are three main steps in bootstrapping Hibernate in native way:

#### 1. building of a ServiceRegistry

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

#### 2. building of an org.hibernate.boot.Metadata

Second step is the building of an `org.hibernate.boot.Metadata` object containing the parsed 
representations of an application domain model and its mapping to a database. 
We need to provide the source information to be parsed (annotated classes, hbm.xml files, orm.xml files). 
This is the purpose of `org.hibernate.boot.MetadataSources`.

#### 3. build sessionFactory

Final step in native bootstrapping is to build the SessionFactory.

## Connection provider

Hibernate connects to database by either connection provider or dataSource.

The ConnectionProvider to use is defined by the `hibernate.connection.provider_class` property,
which is usually not customized. 
If not set, by default Hibernate will use its built-in connection pool (not recommended for production).
This example uses Hibernate built-in pool.

To set dataSource instead, use `hibernate.connection.datasource` property.
Do remember to set `hibernate.connection.driver_class` to map to related driver class name.

## Exception handling

A note of exception handling: as a rule of thumb, no exception thrown by Hibernate can be treated as recoverable. 
Ensure that the Session will be closed by calling the close() method in a finally block.

## Environment 

JDK: Oralce or Open JDK 1.8+

Maven: version 3.3+

#### H2 embedded database

This example runs H2 db in embedded mode:
* add h2 maven dependency and use the JDBC driver class: `org.h2.Driver`
* The database URL `jdbc:h2:./testDb` opens the database `testDb` in project root directory

By default, H2 will create the `testDb` if not exist when application connects to this jdbc url.
   
## Hibernate configuration

If in jdbc URL `MODE=MYSQL` is set to mimic mysql DDL and DML, then Hibernate is configured to use Mysql dialect.

Typically for vanilla Java application Hibernate is configured by either xml or programmatically. 

- jdbc settings
- pool settings if connection pooling is needed
- hibernate ORM settings

Entity and field mapping can be set by either xml or annotation. 
If set by xml, then the class has to be registered in `hibernate.cfg.xml`. 
If set by annotation, then the annotated class can either be registered in `hibernate.cfg.xml` or be added to Hibernate registry programmatically.

## Hibernate util class

There are two util classes in this example showing two ways of getting sessionFactory.

- `HibernateUtilWithCfgXml.java`: configuring sessionFactory by cfg xml
- `HibernateUtilNoCfgXml.java`: configuring sessionFactory programmatically

Both util classes can load external configuration from properties file, thanks to Hibernate's nice `StandardServiceRegistryBuilder#loadProperties()` API.

