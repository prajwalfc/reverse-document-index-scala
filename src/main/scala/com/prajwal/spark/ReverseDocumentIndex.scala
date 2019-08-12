package com.prajwal.spark


import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SparkSession
import org.apache.log4j._
import org.apache.spark.rdd.RDD

class ReverseDocumentIndex(sc:SparkContext){

  def run(path:String):RDD[((String,List[Int]),Long)]= {
    val fileName_document = sc.wholeTextFiles(path)
    val fileId_docArray = fileName_document.mapPartitions(file_doc_partition_func)
    val word_fileId_index = fileId_docArray.flatMap(id_string).reduceByKey((x,y)=>x++y).zipWithIndex.cache()
    word_fileId_index
  }


  /*--------------------------------------HOFS------------------------------------------------------------
  * file_doc_partition_func is defined with val but not def is because file_doc_partition_func needs to be serialzed to distribute to
  * the worker nodes.
  * def  is not serializable but val is.
  *
  * */
  /*         HOF 1                   */
  val file_doc_partition_func= (file_doc:Iterator[(String,String)])=>{
    var iter_id_strLst = for (row <- file_doc)
      yield {
        val (id, doc) = row
        var pared_id = id.split("/").last.toInt
        var pared_doc = doc.split("[^\\w]+")
        (pared_id, pared_doc)
      }
    iter_id_strLst.toIterator
  }
  /*         HOF 2                   */
  val id_string = (id_docArray:(Int,Array[String]))=>{
    val (id, docArray) = id_docArray
    var iter_id_str = for (word <- docArray) yield(word,List(id))
    iter_id_str.toIterator
  }
}

object ReverseDocumentIndex  {

  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "ExampleMain")
    val path = "hdfs://localhost:9000/user/prajwal/data/*"
    val job = new ReverseDocumentIndex(sc)
    val word_fileId_index = job.run(path)
    word_fileId_index.map(row=>(row._1._1,row._2)).saveAsTextFile("word_index")
    word_fileId_index.map(row=>(row._2,row._1._2 mkString("[",",","]"))).saveAsTextFile("index_fileList")

  }
}