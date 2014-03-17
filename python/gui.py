# -*- coding: utf-8 -*-


"""Fenêtre principale de l'application, avec le menu"""

import sys
from PyQt4.QtCore import *
from PyQt4.QtGui import *

class GUI(QApplication):
    def __init__(self, args):
        """Création de la fenêtre"""
        QApplication.__init__(self,args)
        self.main = QMainWindow()
        self.main.setWindowTitle("Gestion des vols")
        self.main.resize(1000,600)

        #widget central : QTextEdit
        self.edit = QTextEdit("editor", self.main)
        self.edit.setFocus()
        self.main.setCentralWidget(self.edit)

        # ajout facile d'éléments dans la barre de menu
        # NOTE : crée la barre s'il n'existe pas
        self.__creerMenu()

        self.main.show()
        self.connect(self,SIGNAL("lastWindowClosed()"),self,SLOT("quit()"))
        self.exec_()

    def __creerMenu(self):
        self.main.menuBar().addAction("Lire un fichier vols")
        self.main.menuBar().addAction("Enregistrer les vols")
        self.main.menuBar().addAction("Quitter")

        # changement du message de status
        # NOTE : crée la zone de status si inexistante
        self.main.statusBar().showMessage("Gestion des vols", 10000)


if __name__ == "__main__" :
    app=GUI(sys.argv)
