
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object WindowExample {

  def main(args: Array[String]) {


    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val source = env.socketTextStream("192.168.152.128",9000)

    //word map

    val values = source.flatMap(value => value.split("\\s+")).map(value => (value,1))

    val keyValue = values.keyBy(0)

    //tumbling window : Calculate wordcount for each 15 seconds

    val tumblingWindow = keyValue.timeWindow(Time.seconds(15))
    // sliding window : Calculate wordcount for last 5 seconds
    val slidingWindow = keyValue.timeWindow(Time.seconds(15),Time.seconds(5))
    //count window : Calculate for every 5 records
    val countWindow = keyValue.countWindow(5)
    val cntSlideWindow = keyValue.countWindow(5,3)


    //tumblingWindow.sum(1).name("tumblingwindow").print()
    //slidingWindow.sum(1).name("slidingwindow").print()
    //countWindow.sum(1).name("count window").print()
    //countWindow.sum(1).print()
    println("==================")
    cntSlideWindow.sum(1).name("count slide window").print()

    env.execute()

  }

}