import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by ACHU on 1/30/2017.
  */
object SparkOddCount {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","C:\\Users\\ACHU\\Downloads\\Winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    val input=sc.textFile("input.txt")

    val fil_input = input.flatMap(line=>{line.split(" ").filter(_.nonEmpty)})

    val main_filter = fil_input.filter(e => e.stripPrefix(",").stripSuffix(",").trim.length%2!=0)

    //this strips of the comma or period appended/prepended to the output

    val stripped_data = main_filter.map(e => e.stripPrefix(",").stripSuffix(",").stripPrefix(".").stripSuffix(".").trim)

    //in order to view the content of the RDD, in other words to output on the console we use collect()

    val console_data = stripped_data.collect()

    //this saves the output in the output folder in part files.

    stripped_data.saveAsTextFile("output")

    //printing the even length words to the console

    println(console_data.mkString("\n"))

  }

}
