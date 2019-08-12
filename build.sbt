name := "SparkScala"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= {
  val sparkVersion = "2.2.1"
  Seq("org.apache.spark" % "spark-core_2.11" % sparkVersion)
  Seq("org.apache.spark" % "spark-sql_2.11" % sparkVersion)
}
