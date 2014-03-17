# -*- coding: utf-8 -*-

from PyQt4.QtCore import pyqtSlot as Slot
from PyQt4 import QtCore
from PyQt4 import QtGui

class CreationVol(QtGui.QWidget):
    """Widget de création d'un vol"""
    def __init__(self):
        QtGui.QWidget.__init__(self)

        #Ajout du titre

        titre = QtGui.QLabel(u"Création d'un vol")
        font = QtGui.QFont()
        font.setPointSize(20)
        titre.setFont(font)
        titre.setAlignment(QtCore.Qt.AlignCenter)

        vbox = QtGui.QVBoxLayout()
        vbox.addWidget(titre)
        vbox.addLayout(self.creerFormulaire())
        creer = QtGui.QPushButton(u"Créer le vol")
        vbox.addWidget(creer)

        self.setLayout(vbox)

    def creerFormulaire(self):
        """On créer les éléments du formulaire"""

        formulaire = QtGui.QFormLayout()
        depart = QtGui.QLineEdit()
        destination = QtGui.QLineEdit()
        date = QtGui.QCalendarWidget()
        ranges = QtGui.QComboBox(self)

        for i in range(10, 31):
            ranges.addItem(str(i))

        sieges = QtGui.QComboBox(self)

        for i in range(2, 7):
            sieges.addItem(str(i))

        #On ajoute dans un formLayout

        formulaire.addRow(u"Départ", depart)
        formulaire.addRow(u"Destination", destination)
        formulaire.addRow(u"Date", date)
        formulaire.addRow(u"Rangés", ranges)
        formulaire.addRow(u"Sièges", sieges)

        return formulaire







