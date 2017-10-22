from bitcoinrpc.authproxy import AuthServiceProxy, JSONRPCException
import time, threading
from firebase import firebase

global firebase
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com', None)

#init RPC conn
rpc_user='multichainrpc'
rpc_password='DRFKmgtYZcjYw3unKrkMwTkZ2XqiH3e2hbDHqvzPRgDf'
port = '4346'

blockchain = AuthServiceProxy("http://%s:%s@localhost:%s"%(rpc_user, rpc_password, port))

server_address = blockchain.getaddresses()[0] 
client_address = '1QqdDcTwjUjhP2kEcyaxvM56ddJMAnDHHuUBZn'

print server_address

def checkoutGetter():
	return firebase.get('/checkout',None)['value'] == 1
			
def checkoutSetter(val):
	firebase.put('/','checkout',{'value':val})
	return
		
def itemsGetter():
	temp=firebase.get('/items',None)
	return temp.values()

def itemsKeys():
	temp=firebase.get('/items',None)
	return temp.keys()

def proceedSetter(val):
	firebase.put('/','proceed',{'value':val})
	return
	
def resetDb():
	items=itemsKeys()
	for i in items:
		firebase.delete('/items',i)
	proceedSetter(1)
	checkoutSetter(0)


