# Java OOP Modeling Lab

A collection of four small Java projects focused on object-oriented modeling, domain relationships, inheritance patterns and persistence.

The repository presents a progression from basic class and attribute modeling to associations, inheritance and relational persistence with JPA/Hibernate.

## Projects

| Module | Focus | Main concepts |
|---|---|---|
| [01 - Patient Attributes](01-Patient-Attributes/) | Patient domain model | classes, object extents, complex/optional/multivalued/derived attributes |
| [02 - Clinic Associations](02-Clinic-Associations/) | Clinic domain relationships | associations, association attributes, qualified associations, composition, multiplicity |
| [03 - Inheritance Patterns](03-Inheritance-Patterns/) | Inheritance modeling patterns | abstraction, polymorphism, overlapping, multiple inheritance patterns, multi-aspect and dynamic inheritance |
| [04 - Clinic Persistence JPA](04-Clinic-Persistence-JPA/) | Persistent clinic model | JPA, Hibernate ORM, H2, repository pattern, relational mapping |

## Repository Structure

```text
Java-OOP-Modeling-Lab/
├── 01-Patient-Attributes/
├── 02-Clinic-Associations/
├── 03-Inheritance-Patterns/
└── 04-Clinic-Persistence-JPA/
```

Each module is an independent Maven project.

## Technologies

- Java
- Maven
- JUnit
- JPA / Hibernate ORM
- H2 Database
- Jakarta Validation
- Lombok
- UML class diagrams

> Individual modules use the Java version defined in their own `pom.xml`.

## What this repository demonstrates

The projects focus on practical implementation of object-oriented modeling concepts rather than a single large application. Together they show how a domain model evolves from classes and attributes through relationships and inheritance to ORM-based persistence.

## Author

Michał Sebastianowicz
