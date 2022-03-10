package affichage

import java.awt.{Color, Graphics, Graphics2D}

import javax.swing.JPanel
import kmeans.{Kmeans, Point}

/**
 * Create a graphic with all point using the information of the modele
 * @param modele class contains 3 arrays, the matrix of 2 axe, another for centers and a last for assignement of centers to point
 */
class GraphiqueChamp(private var modele:Kmeans) extends JPanel{

  /**
   * Default function to draw, the painting can be show on each step if var was set on true
   * @param g
   */
  override def paintComponent(g: Graphics): Unit = {
    if(modele.ShowEachIter){
      modele.InitAlgo()
      for(_ <- 1 until modele.iter) {
        modele.BoucleCenter()
        Draw(g)
        Thread.sleep(100L)
      }
    }else{
      modele.startAlgo()
      Draw(g)
    }
  }

  /**
   *  Function to draw the information of data into the panel
   * @param g Graphic use to draw on component
   */
  def Draw(g:Graphics): Unit = {
    val partMatrix: Array[Point] = modele.getMatrix
    val partCenters: Array[Point] = modele.getCenters
    val partAssignement: Array[Int] = modele.getAssignements

    super.paintComponent(g)

    val g2d = g.create.asInstanceOf[Graphics2D]
    g2d.scale(1, -1)
    g2d.translate(0, -this.getHeight)

    for (i <- partMatrix.indices) {
      val point: Point = partMatrix(i)

      partAssignement(i) match {
        case 0 => g2d.setColor(Color.BLUE)
        case 1 => g2d.setColor(Color.RED)
        case 2 => g2d.setColor(Color.GREEN)
        case 3 => g2d.setColor(Color.CYAN)
        case 4 => g2d.setColor(Color.PINK)
        case 5 => g2d.setColor(Color.ORANGE)
        case 6 => g2d.setColor(Color.YELLOW)
        case 7 => g2d.setColor(Color.DARK_GRAY)
        case 8 => g2d.setColor(Color.LIGHT_GRAY)
        case _ => g2d.setColor(Color.MAGENTA)
      }
      g2d.fillOval((point.getX * 100).toInt, (point.getY * 100).toInt, 7, 7)

    }

    for (i <- partCenters.indices) {
      val point: Point = partCenters(i)
      g2d.setColor(Color.BLACK)
      g2d.fillOval((point.x * 100).toInt, (point.y * 100).toInt, 7, 7)
    }
  }
}
