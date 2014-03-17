# -*- coding: utf-8 -*-


"""Fenêtre principale de l'application, avec le menu"""

import sys
from PyQt4.QtCore import *
from PyQt4.QtGui import *
from creationvol import CreationVol

class GUI(QMainWindow):
    def __init__(self, args):
        """Création de la fenêtre"""
        super(GUI, self).__init__()
        self.setWindowTitle("Gestion des vols")
        self.resize(1000,600)

        #Centrer la fenêtre
        self.move(QApplication.desktop().screen().rect().center()- self.rect().center())

        #widget central : QTextEdit
#        self.edit = QTextEdit("editor", self)
#        self.vbox = QVBoxLayout()
#        self.top = CreationVol()
#        self.vbox.addWidget(self.top)
        #on ajoute l'éditeur
#        self.vbox.addWidget(self.edit)


        self.vol = CreationVol()
        self.main = QStackedWidget()
        self.main.addWidget(self.vol)
        self.stuff = QLabel(u"STUFF")
        self.main.addWidget(self.stuff)

        self.setCentralWidget(self.main)


        # ajout facile d'éléments dans la barre de menu
        # NOTE : crée la barre s'il n'existe pas
        self.__creerMenu()
        self.show()

    def __creerMenu(self):
        """Crée les menus et les events pour faire qqch"""
        menuBar = self.menuBar()
        fichier = menuBar.addMenu("&Fichier")
        fichier.addAction("Lire un fichier vols")
        fichier.addAction("Enregistrer les vols")

        #Action pour quitter l'application
        quitter = QAction("&Quitter", self)
        quitter.setShortcut('Ctrl+Q')
        quitter.triggered.connect(qApp.quit)
        quitter.setStatusTip("Quitter l'application")

        #Test pour changer le widget
        creerVol = QAction(u"Créer un vol", self)
        creerVol.triggered.connect(self.creerVol)

        supprimerVol = QAction(u"Supprimer un vol", self)
        supprimerVol.triggered.connect(self.supprimerVol)

        fichier.addAction(quitter)

        menuVol = menuBar.addMenu("&Vols")
        menuVol.addAction(creerVol)
        menuVol.addAction(supprimerVol)
        menuVol.addAction(u"Afficher les détails d'un vol")
        menuVol.addAction("Afficher tous les vols")

        menuPass = menuBar.addMenu("&Passagers")
        menuPass.addAction(u"Réserver un siège")
        menuPass.addAction(u"Effacer une réservation")
        menuPass.addAction("Liste des passagers")

        # changement du message de status
        # NOTE : crée la zone de status si inexistante
        self.statusBar().showMessage("Gestion des vols", 10000)


    def creerVol(self):
        """On change le widget"""
        self.main.setCurrentWidget(self.vol)

    def supprimerVol(self):
        """On change le widget"""
        self.main.setCurrentWidget(self.stuff)



def main():
    app = QApplication(sys.argv)
    gui = GUI(sys.argv)
    sys.exit(app.exec_())

if __name__ == "__main__" :
    main()
