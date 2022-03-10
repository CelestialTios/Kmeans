package interfaces

trait Vue {
  def notifierChangement(): Unit

  def setModele(modele: Modele): Unit
}
