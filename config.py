from bitcoinrpc.authproxy import AuthServiceProxy, JSONRPCException
import time, threading
from firebase import firebase

global firebase
firebase = firebase.FirebaseApplication('https://shop-n-out.firebaseio.com', None)

#init RPC conn
rpc_user='multichainrpc'
rpc_password='EreNfQzTtSBVr3M3R4Mnmk7LogtK9CLLkDtBesBjF3eS'
port = '4352'

rpc_connection = AuthServiceProxy("http://%s:%s@localhost:%s"%(rpc_user, rpc_password, port))

server_address = rpc_connection.getaddresses()[0] 
client_address = '1KG161HAdg9jXihRcq64cyZgJWfpZPEPJBbqTg'

