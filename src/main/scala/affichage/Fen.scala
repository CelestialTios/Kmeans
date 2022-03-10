package affichage

import java.awt.event.{ActionEvent, ActionListener}
import java.awt._

import interfaces.{Controleur, Modele}
import javax.swing._
import kmeans.Kmeans

class Fen(data:Kmeans) extends JFrame with Controleur {

  private val aTK :Toolkit= Toolkit.getDefaultToolkit
  private val dim :Dimension = aTK.getScreenSize

  private var tbpVues :JTabbedPane= _
  private var txtK :JTextField= _
  private var txtIteration :JTextField= _
  private var bt : JButton= _

  private var showEachIter = false

  this.initiate()
  this.initComponent()

  /**
   * Set all option of the frame
   */
  def initiate():Unit={
    this.setTitle("Algorithme Kmeans")
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    this.setBounds(0,0,dim.width,dim.height)
    this.setVisible(true)
  }

  /**
   * Initiate primary JPanel and sub JPanel
   */
  def initComponent():Unit={
    val panPrincipal:JPanel = new JPanel()
    panPrincipal.setLayout(new BorderLayout())

    panPrincipal.add(this.buildPanelCenter,BorderLayout.CENTER)
    panPrincipal.add(this.buildPanelEnd,BorderLayout.PAGE_END)
    this.add(panPrincipal)
  }

  /**
   * Build the JPanel at center space of frame with the graphic
   * @return
   */
  def buildPanelCenter: JPanel = {
    val panel = new JPanel
    panel.setLayout(new BorderLayout) // Permet d'avoir une JTextArea qui occupe tout l'espace.

    this.tbpVues = new JTabbedPane
    panel.add(this.tbpVues, BorderLayout.CENTER)
    panel.setBackground(Color.LIGHT_GRAY)
    panel.setBorder(BorderFactory.createEtchedBorder) // Ajout d'une bordure.

    panel
  }

  /**
   * Build the JPanel at lower space of frame with different component to add interaction
   * @return a JPanel with component
   */
  def buildPanelEnd : JPanel = {
    val panel :JPanel = new JPanel
    val grid:GridLayout = new GridLayout(1,5)
    grid.setHgap(dim.width/10)
    panel.setLayout(grid)
    this.txtK = new JTextField(4)
    this.txtK.setHorizontalAlignment(SwingConstants.RIGHT)
    this.txtIteration = new JTextField(5)
    this.txtIteration.setHorizontalAlignment(SwingConstants.RIGHT)

    panel.add(new JLabel("usable !"))

    val panK:JPanel = new JPanel()
    panK.add(new JLabel("K :"))
    panK.add(txtK)
    panel.add(panK)

    val panI:JPanel = new JPanel()
    panI.add(new JLabel("Itération :"))
    panI.add(txtIteration)
    panel.add(panI)

    bt = new JButton("Lancer l'algorithme !")
    bt.setBorder(BorderFactory.createEtchedBorder)
    bt.addActionListener(e => {
      var k = txtK.getText()
      var iter = txtIteration.getText()
      if(k == null || k.length <= 0) k = "4"
      if(iter == null || iter.length <= 0) iter = "1000"
      data.UpdateParameter(1,2,k.toInt, iter.toInt)
      data.init()
      data.startAlgo()
      this.tbpVues.repaint()
    })
    panel.setBorder(BorderFactory.createEtchedBorder)
    panel.add(bt)

    val box: JCheckBox = new JCheckBox("Affichage de chaque itération")
    box.addActionListener((_: ActionEvent) => {
      data.ShowEachIter = box.isSelected
    })
    panel.add(box)

    panel
  }

  /**
   * Add a new Panel on the center area
   * @param title of the panel
   * @param component a JPanel with information
   */
  def ajouterPanneau(title: String, component: JComponent): Unit = {
    this.tbpVues.addTab(title, component)
  }

}
