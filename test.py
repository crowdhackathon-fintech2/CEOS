#!/usr/bin/python
from firebase import firebase
import threading
import sys, time
global firebase
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com', None)
from glob import glob

global mode
mode = 'pi'

class Locker:
	
	def __init__(self):
		self.stdin_path = '/dev/null'
		self.stdout_path = '/dev/tty'
		self.stderr_path = '/dev/tty'
		self.pidfile_path =  '/tmp/test.pid'
		self.pidfile_timeout = 5
		self.running = True
		if mode == 'arduino':
			import serial
			self.ser = serial.Serial(glob('/dev/ttyACM*')[0])		
		elif mode == 'pi':
			import RPi.GPIO as GPIO
			GPIO.setmode(GPIO.BCM)
			GPIO.setup(4, GPIO.OUT)
			self.pwm = GPIO.PWM(4 , 50) # GPIO 4 @50Hz
			self.pwm.start(5)
			self.toangle = lambda x : float(x) / 10.0 + 2.5 # tune for servo
		self.lock()

		
	def lock(self):
		if mode == 'arduino':
			self.ser.write('120')
		elif mode == 'pi':
			self.pwm.ChangeDutyCycle(self.toangle(60))
		print 'Locked mechanism'	
	
	def unlock(self):
		if mode == 'arduino':
			self.ser.write('20')	
		elif mode == 'pi':
			self.pwm.ChangeDutyCycle(self.toangle(40))
		print 'Unlocked mechanism'		
	
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
		while 1 == 1:
			while self.running:
				while not self.unlock():
					print 'Antitheft is locked'
				print 'Antitheft toggled unlocked'
				self.unlock()	
				print 'Cleaning db'
				self.resetDb()
				print 'Cleaned db'
	
				while self.unlock():
					print 'Antitheft is unlocked'
				print 'Locked again'
				self.lock()
				time.sleep(0.5)

	def itemsKeys(self):
		temp=firebase.get('/items',None)
		return temp.keys()
	
	
	def checkoutSetter(self, val):
		firebase.put('/','checkout',{'value':val})
		return
	
	def proceedSetter(self, val):
		firebase.put('/','proceed',{'value':val})
		return
		
	def resetDb(self):
		try:
			items=self.itemsKeys()
			for i in items:
				firebase.delete('/items',i)
		except:
			pass
		self.proceedSetter(1)
		self.checkoutSetter(0)
	
	def unlock(self):
		return firebase.get('/unlock',None)['value'] == 1
		
	def stop(self):
		self.running = False
		
	def resume(self):
		self.running = True

daemonized = False

print 'Mode is {}'.format(mode)
locker = Locker()
if daemonized:
	from daemon import runner
	print 'Started as daemon'
	daemon_runner = runner.DaemonRunner(locker)
	daemon_runner.do_action()
else:
	locker.run()
