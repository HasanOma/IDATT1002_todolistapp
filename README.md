# TodoListApp 
![TodoListApp logo](src/main/resources/edu/ntnu/idatt1002/k2g10/todolistapp/img/icon.png)

TodoListApp is an app made to help you structure your everyday tasks with ease. The app lets you add information about
your tasks, like priority, description and start and end dates. Tasks can be added to your own custom categories, and 
filtered and sorted to your liking. Always on top of things from now on!

## Getting started
To test out our project or to get started with further development you can follow these steps.

### Prerequisites
* [Git (optional)](https://git-scm.com/)
* [Java SE JDK 11 or newer](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [IntelliJ IDEA or other IDE with Maven support](https://www.jetbrains.com/idea/)

### Getting the source code
There are two ways to retrieve the source code for this project:

**With Git** please run the following command in the desired directory:
```
git clone https://gitlab.stud.iie.ntnu.no/idatx1002_2021_k2-10/idatx1002_2021_k2-10.git
```

**Without Git** please press the download button on the front page of the repo and select the desired file format.

### Building and running the project
Once the source code is open in a code editor, run the maven commands `compiler:compile` and `javafx:run`. In IntelliJ
IDEA this can be done by pressing the following:
```
View → Tool Windows → Maven
TodoListApp → Plugins → compiler → compiler:compile
TodoListApp → Plugins → javafx → javafx:run
```

### Database connection
The project is configured to use Apache Derby for a local database connection. This can easily be changed to another kind
of database in the file `persistence.xml`. For further information on how to do this, refer to the database docs linked
in the file `pom.xml`.

## Documentation
All relevant documentation for the project can be found in one of these locations:
* Our wiki, which can be read [here](https://gitlab.stud.iie.ntnu.no/idatx1002_2021_k2-10/idatx1002_2021_k2-10/-/wikis/home).
* Our javadoc, which can be read [here](http://idatx1002_2021_k2-10.pages.stud.idi.ntnu.no/idatx1002_2021_k2-10/javadoc/).

If you have further questions about the project, feel free to send an email to one of the group members by clicking one
of the names below. We will be happy to help!

## Authors
This application was developed during the class _Software Development 1_ at the NTNU by group K2-10:

- [Anders Tellefsen](mailto:andetel@stud.ntnu.no)

- [Brage Minge](mailto:bragemi@stud.ntnu.no)

- [Christel Mari Ossletten](mailto:chrisoss@stud.ntnu.no)

- [Hasan Rehman Omarzae](mailto:hasanro@stud.ntnu.no)

- [Jonathan Hansen Løseth](mailto:jonathhl@stud.ntnu.no)

- [Tobias Rødahl Thingnes](mailto:tobiasth@stud.ntnu.no)
