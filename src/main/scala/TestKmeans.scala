import affichage.{Fen, GraphiqueChamp}
import kmeans.Kmeans

object TestKmeans {

  def main(args: Array[String]): Unit = {

    val k = new Kmeans(0,1,3,1000)
    k.init()
    val fen =new Fen(k)
    val graph = new GraphiqueChamp(k)
    fen.ajouterPanneau("Kmeans",graph)


    println("### DATA ###")
    println(k.getMatrixString)
    println("### CENTERS ###")
    println(k.getCentersString)
    println("### ASSIGNEMENT ###")
    println(k.getAssignement)
    println("####CORRELATION ###")
    println(k.correlation(0,1))
    println(k.correlation(0,2))
    println(k.correlation(0,3))
    println(k.correlation(1,2))
    println(k.correlation(1,3))
    println(k.correlation(2,3))

  }
}
