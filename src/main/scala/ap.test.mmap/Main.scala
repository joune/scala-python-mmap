package ap.test.mmap

import java.nio._
import java.io._
import java.nio.channels.FileChannel.MapMode._

object Main extends App with Utils
{
  val file = "/tmp/mapped.txt"
  val enc = "utf-8"
  val data = "hello world\n".getBytes(enc)

  val mem = new RandomAccessFile(file, "rw").getChannel().map(READ_WRITE, 0, data.length)
  mem.put(data)

  val (ret, out) = exec(".", "python", List("src/main/python/script.py", file))

  val transformed = 
    if (ret == 0)
    {
      //script outputs the number of bytes written
      val size = out.next.toInt
      val bytes = (0 to size -1).foldLeft(Array[Byte]()) { (array, i) => array :+ mem.get(i) }
      new String(bytes, enc)
    }
    else "ERROR"

  println(transformed)

}

