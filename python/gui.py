# -*- coding: utf-8 -*-

try:
    from PySide import QtCore
    from PySide import QtGui
except:
    from PyQt4.QtCore import pyqtSlot as Slot
    from PyQt4 import QtCore
    from PyQt4 import QtGui

class GUI(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)
        pass
