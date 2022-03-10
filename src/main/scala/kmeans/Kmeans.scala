package kmeans

import scala.io.Source


class Kmeans(var x1: Int, var x2: Int, var numberK : Int, var iter:Int){

  private val path : String = "C:\\Users\\gerau\\OneDrive\\Bureau\\DUT_INFO\\S4\\Scala\\Session3\\iris.data"

  private var data: Array[Array[String]] = _
  private var matrix: Array[Point] = _
  private var centers : Array[Point] = _
  private var assignement: Array[Int] = _

  var ShowEachIter: Boolean = false

  //Initialisation
  def init() : Unit ={
    data = createTab
    matrix = createMatrix(x1,x2)
    centers = createCenter(0)
    assignement = assign()
  }

  def startAlgo() :Unit ={
    InitAlgo()
    for(_ <- 1 until iter){
      BoucleCenter()
    }
  }

  def InitAlgo(): Unit ={
    centers = createCenter(numberK)
    assignement = assign()
  }

  def BoucleCenter(): Unit ={
    for(j <- centers.indices) {
      val point:Point = newCenter(j)
      if (point != centers(j)) {
        centers(j) = newCenter(j)
      }
    }
    assignement = assign()
  }

  def UpdateParameter(newX1:Int, newX2:Int, newK: Int, newIter:Int) : Unit ={
    x1 = newX1
    x2 = newX2
    numberK = newK
    iter = newIter
  }

  /**
   * Create a new center for a index
   * @param numb index of center selected
   * @return new point
   */
  def newCenter(numb:Int): Point={
    var valuesX:Double = 0
    var valuesY:Double = 0
    var count = 0
    for(i <- assignement.indices){
      if( assignement(i) == numb){
        count += 1
        valuesX += matrix(i).getX
        valuesY += matrix(i).getY
      }
    }
    if(count!=0) new Point(valuesX/count,valuesY/count)
    else centers(numb)
  }


  /**
   * Calcul average of a column
   * @param colonne index of the column
   * @return value of the average
   */
  def moyenne(colonne:Int) : Double ={
    var count : Double = 0
    for(i<- data.indices){
      count = count + data(i)(colonne).toDouble
    }
    count/data.length
  }

  /**
   * Calculate standard deviation of a column
   * @param colonne index of the column
   * @return value of standard deviation
   */
  def ecartType(colonne:Int) : Double = {
    Math.sqrt(variance(colonne))
  }

  /**
   * Calculate variance of a column
   * @param colonne index of the column
   * @return value of variance
   */
  def variance (colonne :Int) : Double = {
    var value: Double = 0
    val average : Double = moyenne(colonne)
    for(i <- data.indices){
      value += Math.pow(data(i)(colonne).toDouble - average,2)
    }
    value/data.length
  }

  /**
   * Calculate average for multiple column
   * @param indices an array of column
   * @return an array of value
   */
  def moyennes(indices:Array[Int]) : Array[Double] ={
    val averages: Array[Double] = new Array[Double](indices.length)
    for(i <- indices) {
      averages(i) = moyenne(i)
    }
    averages
  }

  /**
   * Calculate standard deviation for multiple column
   * @param indices an array of column
   * @return an array of value
   */
  def ecartTypes(indices:Array[Int]) : Array[Double] = {
    val ecarts : Array[Double] = new Array[Double](indices.length)
    for(i <- indices){
      ecarts(i) = ecartType(i)
    }
    ecarts
  }

  /**
   * Calculate variance for multiple column
   * @param indices an array of column
   * @return an array of value
   */
  def variances(indices:Array[Int]) : Array[Double] = {
    val variances : Array[Double] = new Array[Double](indices.length)
    for (i <- indices){
      variances(i) = variance(i)
    }
    variances
  }

  /**
   * Calculate the covariance between 2 index
   * @param colonne1 first index
   * @param colonne2 second index
   * @return value as Double
   */
  def covariance(colonne1:Int,colonne2:Int):Double={
    var count:Double=0
    val average1 =moyenne(colonne1)
    val average2 = moyenne(colonne2)
    for(i<-data.indices){
      count = count + ((data(i)(colonne1).toDouble-average1)*(data(i)(colonne2).toDouble-average2))
    }
    count/matrix.length
  }

  /**
   * Calculate the correlation between 2 index
   * @param colonne1 first index
   * @param colonne2 second index
   * @return value as Double
   */
  def correlation(colonne1:Int,colonne2:Int):Double={
    covariance(colonne1,colonne2)/math.sqrt(variance(colonne1)*variance(colonne2))
  }

  /**
   * Generate an array for the file
   * @return an array of string
   */
  def createTab: Array[Array[String]] = {
    val source = Source.fromFile(path)
    val data: Array[Array[String]]= Array.ofDim[String](150,5)
    var i = 0
    for(line <- source.getLines()){
      if(line != "") data(i) = line.split(",")
      i+=1
    }
    data
  }

  /**
   * Generate a array of 150 points compose of two column by the array of the file
   * @param colonne1 First column selected
   * @param colonne2 Second column selected
   * @return an array of Point
   */
  def createMatrix(colonne1:Int,colonne2:Int): Array[Point] ={
    val matrix = Array.ofDim[Point](data.length)
    for ( i <- data.indices){
      matrix(i) =new Point(data(i)(colonne1).toDouble,data(i)(colonne2).toDouble)
    }
    matrix
  }

  /**
   * Generate k cluster by using random value of the matrix array
   * @param k number of cluster
   * @return an array of Point
   */
  def createCenter(k:Int): Array[Point] ={
    val array = Array.ofDim[Point](k)
    val r = new scala.util.Random
    for(i <- 0 until k ){
      array(i) = new Point(matrix(r.nextInt(matrix.length)).getX,matrix(r.nextInt(matrix.length)).getY)
    }
    array
  }

  /**
   * Assign each Point to a center by using an array
   * @return an array of a length equal to the matrix
   */
  def assign():Array[Int] ={
    val assignTab = Array.ofDim[Int](matrix.length)
    for(i <- matrix.indices){
      assignTab(i)= matrix(i).compare(centers)
    }
    assignTab
  }



  /**
   * Getter
   * @return
   */

  def getTab: Array[Array[String]] ={
    data
  }

  def getMatrix: Array[Point]={
    matrix
  }

  def getCenters:Array[Point]={
    centers
  }

  def getAssignements:Array[Int]={
    assignement
  }

  def getMatrixString: String ={
    matrix.mkString("\n")
  }

  def getAssignement: String ={
    assignement.mkString(", ")
  }

  def getCentersString: String ={
    centers.mkString("\n")
  }

  /*
  FUNCTION FOR PATTERN MVC

  private def notifierToutesVues(): Unit = {
    vues forEach{ vue =>
      vue.notifierChangement()
    }
  }

  def enregistrer(vue: Vue): Unit = {
    vue.setModele(this)
    this.vues.add(vue)
  }*/
}
