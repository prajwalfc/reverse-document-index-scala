## Spark-scala-template
A barebone spark-scala project to get straight started upon



# Instruction for setting up project.
- Download the project from this link. This is a barebone project using sbt as build tools.
- Change the build.sbt file according to your scala version and compatible version of spark core.
- Add external library from your spark installation directory from inside the jars folder.

- P.S. This setup is for Linux Ubuntu
- Good luck if you are windows user. <br/>


- For running the project as spark job:

- open a terminal window and go to diecrtory containing build.sbt
- run  sbt compile
- run sbt run
- run sbt package

- Now according to this set up the snapshot jar is located at<br/>
- {ProjectName}/target/scala-version.xx/sparkscala_version.xx-0.1.jar <br/>

-open the terminal window <br/>
-bin/spark-submit --class package.name.ClassName path/to/{Name of your jar}-1.0-SNAPSHOT.jar {source file} {destination}
