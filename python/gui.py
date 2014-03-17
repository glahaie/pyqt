# -*- coding: utf-8 -*-


"""Fenêtre principale de l'application, avec le menu"""

import sys
from PyQt4.QtCore import *
from PyQt4.QtGui import *

class GUI(QMainWindow):
    def __init__(self, args):
        """Création de la fenêtre"""
        super(GUI, self).__init__()
        self.setWindowTitle("Gestion des vols")
        self.resize(1000,600)

        #widget central : QTextEdit
#        self.edit = QTextEdit("editor", self.main)
#        self.edit.setFocus()
#        self.main.setCentralWidget(self.edit)

        # ajout facile d'éléments dans la barre de menu
        # NOTE : crée la barre s'il n'existe pas
        self.__creerMenu()

        self.show()
#        self.connect(self,SIGNAL("lastWindowClosed()"),self,SLOT("quit()"))
#        self.exec_()

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

        fichier.addAction(quitter)

        menuVol = menuBar.addMenu("&Vols")
        menuVol.addAction(u"Créer un vol")
        menuVol.addAction("Supprimer un vol")
        menuVol.addAction(u"Afficher les détails d'un vol")
        menuVol.addAction("Afficher tous les vols")

        menuPass = menuBar.addMenu("&Passagers")
        menuPass.addAction(u"Réserver un siège")
        menuPass.addAction(u"Effacer une réservation")
        menuPass.addAction("Liste des passagers")

        # changement du message de status
        # NOTE : crée la zone de status si inexistante
        self.statusBar().showMessage("Gestion des vols", 10000)


def main():
    app = QApplication(sys.argv)
    gui = GUI(sys.argv)
    sys.exit(app.exec_())

if __name__ == "__main__" :
    main()
