from firebase import firebase
import threading
import sys, time
global firebase
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com', None)
from glob import glob
import RPi.GPIO as GPIO

global mode
mode = 'pi'

class Locker:
	
	def __init__(self):
		if mode == 'arduino':
			import serial
			self.ser = serial.Serial(glob('/dev/ttyACM*')[0])		
		elif mode == 'pi':
			GPIO.setmode(GPIO.BCM)
			GPIO.setup(4, GPIO.OUT)
			self.pwm = GPIO.PWM(4 , 50) # GPIO 4 @50Hz
			self.pwm.start(5)
			self.toangle = lambda x : float(x) / 10.0 + 2.5 # tune for servo
		else:
			raise Error('Unknown mode, please select arduino or pi')
		self.lock()
		print 'ok'		

	def lock(self):
		if mode == 'arduino':
			self.ser.write('120')
		elif mode == 'pi':
			self.pwm.ChangeDutyCycle(self.toangle(60))
		print 'locked'	
	
	def unlock(self):
		if mode == 'arduino':
			self.ser.write('20')	
		elif mode == 'pi':
			self.pwm.ChangeDutyCycle(self.toangle(40))
		print 'unlocked'		
	
	def __del__(self):
		if mode == 'arduino':
			self.ser.close()
		elif mode == 'pi':
			self.pwm.stop()
			try:
				GPIO.cleanup()		
			except:
				pass
	def run(self):
		pass

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

locker = Locker()
while not unlock():
	print 'locked'
	time.sleep(0.5)
	
print 'unlocked'

time.sleep(1)
locker.unlock()	
del locker

resetDb()


