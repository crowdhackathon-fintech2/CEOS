from firebase import firebase

global firebase
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com', None)
from glob import glob
import serial


def itemsKeys():
	temp=firebase.get('/items',None)
	return temp.keys()


def checkoutSetter(val):
	firebase.put('/','checkout',{'value':val})
	return

def proceedSetter(val):
	firebase.put('/','proceed',{'value':val})
	return
	
def resetDb():
	try:
		items=itemsKeys()
		for i in items:
			firebase.delete('/items',i)
	except:
		pass
	proceedSetter(1)
	checkoutSetter(0)

def unlock():
	return firebase.get('/unlock',None)['value'] == 1

ser = serial.Serial(glob('/dev/ttyACM*')[0])
ser.write('120')

while not unlock():
	print 'locked'
	
print 'unlocked'
ser.write('0')	
ser.close()

resetDb()


