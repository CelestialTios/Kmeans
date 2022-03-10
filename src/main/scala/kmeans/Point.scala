package kmeans


class Point(val x: Double, val y:Double) {

  def getX:Double ={
    this.x
  }

  def getY:Double ={
    this.y
  }

  /**
   * Calcule la distance entre 2 point sur une dimension 2
   * Choix de 2 colonnes arbitrairement
   * @return
   */
  def mesureDistance(p:Point):Double ={
    Math.sqrt(Math.pow(this.x-p.x,2) + Math.pow(this.y-p.y,2))
  }

  def compare(array: Array[Point]): Int = {
    var distance:Double = 1000
    var tmp:Double = 0
    var classe:Int = 0
    for (i <- array.indices){
      tmp = this.mesureDistance(array(i))
      if(tmp < distance){
        classe = i
        distance = tmp
      }
    }
    classe
  }

  override def toString: String = s"($x,$y)"
}
