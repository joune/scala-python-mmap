package ap.test.mmap

import scala.collection.JavaConversions._
import scala.io.Source
import java.io.File

trait Utils
{
  def using[T <: { def close(): Unit }, R](closable: T)(block: T => R) = {
    try { block(closable) }
    finally { closable.close() }
  }

  def exec(workDir: String, prog: String, args:List[String] = Nil) :(Int,Iterator[String]) = {
    val p = new ProcessBuilder(prog::args).directory(new File(workDir)).redirectErrorStream(true).start
      // return the code and output
      (p.waitFor, Source.fromInputStream(p.getInputStream).getLines)
  }
}
