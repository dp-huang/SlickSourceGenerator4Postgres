package generator

import slick.codegen.SourceCodeGenerator
import slick.driver.JdbcProfile
import slick.model.Model

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/**
  */
object SourceCodeGenerator {

  def main(args: Array[String]) = {
    val args = Array(
      "slick.driver.PostgresDriver",
      "org.postgresql.Driver",
      "jdbc:postgresql://10.62.88.24:5432/test",
      "src/main/scala/generator",
      "",
      "testUser",
      "testPass",
      "testSchema"
    )

    run(args(0), args(1), args(2), args(3), args(4), Some(args(5)), Some(args(6)), args(7).split(","))
  }

  def run(slickDriver: String, jdbcDriver: String, url: String, outputDir: String, pkg: String, user: Option[String], password: Option[String], schemas: Seq[String]) = {
    val driver: JdbcProfile =
      Class.forName(slickDriver + "$").getField("MODULE$").get(null).asInstanceOf[JdbcProfile]
    val dbFactory = driver.api.Database
    val db = dbFactory.forURL(url, driver = jdbcDriver,
      user = user.getOrElse(null), password = password.getOrElse(null), keepAliveConnection = true)
    try {
      val m = Await.result(db.run(driver.createModel(None, false).withPinnedSession), Duration.Inf)
      val customizedSchema = new Model(m.tables.filter(a => schemas.contains(a.name.schema.getOrElse(""))), m.options)
      new SourceCodeGenerator(customizedSchema).writeToFile(slickDriver, outputDir, pkg)
      print("done")
    } finally db.close
  }
}
